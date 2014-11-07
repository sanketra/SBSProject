package com.onlinebanking.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.crypto.Cipher;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.auth.x500.X500Principal;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;

import com.onlinebanking.models.User;

@SuppressWarnings("deprecation")
public class PKI {
	public static PublicKey generatePublicPrivateKeyPair(User u)
			throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyPairGenerator.initialize(1024, sr);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey pub = keyPair.getPublic();
		PrivateKey pri = keyPair.getPrivate();
		generatePfxFile(pub, pri, u.getFname());
		mailPfx(u);
		File f = new File(u.getFname()+".pfx");
		f.delete();
		return pub;
	}

	private static void mailPfx(User u) throws Exception
	{
		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"pitchforkbank@gmail.com", "softwaresecurity");
					}
				});
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("pitchforkbank@gmail.com"));
			String Mail1 = u.getEmailId();
			if (!Mail1.isEmpty()) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(Mail1));
			}

			message.setSubject("Thank you for choosing PitchForkBank");
			BodyPart messageBodyPart = new MimeBodyPart();

			String body = "Please find attached private key file and jar application that would be required during authentication";

			messageBodyPart.setText(body);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(
					u.getFname()+".pfx")));
			messageBodyPart.setFileName(u.getFname()+".pfx");
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			
			messageBodyPart.setDataHandler(new DataHandler(new FileDataSource("C:\\Encryptor.jar")));
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart.setFileName("encryptor.jar");

			messageBodyPart = new MimeBodyPart();

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("Mail sent!");
	}

	// reference: bouncycastle sample example:
	// https://code.google.com/p/xebia-france/wiki/HowToGenerateaSelfSignedX509CertificateInJava
	private static void generatePfxFile(PublicKey pub, PrivateKey pri,
			String fname) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date expires = cal.getTime();
		X500Principal issuedBy = new X500Principal("CN=PitchFork Bank");
		X500Principal issuedTo = new X500Principal("CN=" + fname);
		X509V1CertificateGenerator certificateGenerator = new X509V1CertificateGenerator();
		certificateGenerator.setSerialNumber(BigInteger.valueOf(System
				.currentTimeMillis()));
		certificateGenerator.setSubjectDN(issuedTo);
		certificateGenerator.setIssuerDN(issuedBy);
		certificateGenerator.setNotBefore(today);
		certificateGenerator.setNotAfter(expires);
		certificateGenerator.setPublicKey(pub);
		certificateGenerator.setSignatureAlgorithm("SHA256WithRSAEncryption");

		X509Certificate cert;
		try {
			cert = certificateGenerator.generate(pri, "BC");
		} catch (Exception e) {
			cert = null;
		}
		Certificate c[] = new Certificate[1];
		c[0] = (Certificate) cert;
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(null, "password".toCharArray());
		ks.setKeyEntry("alias", pri, "password".toCharArray(), c);
		FileOutputStream fo = new FileOutputStream(fname + ".pfx");
		ks.store(fo, "password".toCharArray());
		fo.close();
	}

	public static String generateRandomString() {
		return UUID.randomUUID().toString();
	}

	public static boolean checkByDecrypting(String plaintext,
			String encryptedText, String pub) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
				Base64.decodeBase64(pub));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pubKey = kf.generatePublic(keySpec);

		Cipher rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] decryptedText = rsa.doFinal(Base64.decodeBase64(encryptedText));
		String decrypted = new String(decryptedText);
		return plaintext.equals(decrypted);
	}
}
