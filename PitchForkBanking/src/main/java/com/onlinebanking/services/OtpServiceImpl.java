package com.onlinebanking.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.UserotpHome;
import com.onlinebanking.models.User;
import com.onlinebanking.models.Userotp;

@Service
public class OtpServiceImpl implements OtpService {

	private UserotpHome userotpHome;

	public void setUserotpHome(UserotpHome userotpHome) {
		this.userotpHome = userotpHome;
	}

	private static OtpService otpServiceImpl = null;

	public static OtpService getInstance() {
		if (otpServiceImpl == null) {
			otpServiceImpl = new OtpServiceImpl();
		}
		return otpServiceImpl;
	}

	@Override
	@Transactional
	public void sendOtp(User user, String emailId1) {
		// Check for duplicate record
		String userId = user.getUserId();
		Userotp temp = userotpHome.findById(userId);
		// Take email id from the form
		String emailId = emailId1;
		// Generate OTP
		SecureRandom random = new SecureRandom();
		String otp = new BigInteger(130, random).toString(32);
		otp = otp.substring(0, 5);
		// Set otp for verification
		Userotp userotpObj = new Userotp(user, otp);
		if (temp == null) {
			userotpHome.persist(userotpObj);
		} else {
			userotpHome.delete(temp);
			userotpHome.persist(userotpObj);
		}
		// Send OTP via Email to the requester
		int choice = random.nextInt(10000);
		choice = choice % 3;
		System.out.println(choice);
		if (choice == 0) {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"pitchforkbank@gmail.com",
									"softwaresecurity");
						}
					});
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("pitchforkbank@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(emailId));
				message.setSubject("OTP");
				message.setText("Dear User," + "\n\nYour requested OTP is "
						+ otp);
				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		} else if (choice == 1) {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"pitchforkbank1@gmail.com",
									"softwaresecurity");
						}
					});
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("pitchforkbank1@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(emailId));
				message.setSubject("OTP");
				message.setText("Dear User," + "\n\nYour requested OTP is "
						+ otp);
				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		} else {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"pitchforkbank2@gmail.com",
									"softwaresecurity");
						}
					});
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("pitchforkbank2@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(emailId));
				message.setSubject("OTP");
				message.setText("Dear User," + "\n\nYour requested OTP is "
						+ otp);
				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}

	}

	@Override
	@Transactional
	public boolean verifyOtp(Userotp otpObj, String newOtp) {
		String temp2 = otpObj.getOneTimePassword();
		if (newOtp.equals(temp2)) {
			System.out.println("True!");
			userotpHome.delete(otpObj);
			return true;
		} else {
			System.out.println("False!");
			return false;
		}
	}

	@Override
	@Transactional
	public Userotp getUserotpById(String id) {
		return this.userotpHome.findById(id);
	}

}