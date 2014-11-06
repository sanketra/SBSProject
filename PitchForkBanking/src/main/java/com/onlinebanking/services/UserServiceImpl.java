package com.onlinebanking.services;

import java.security.PublicKey;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.AccountHome;
import com.onlinebanking.dao.UserHome;
import com.onlinebanking.dao.UserPublicKeyHome;
import com.onlinebanking.helpers.CryptoHelper;
import com.onlinebanking.helpers.PKI;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserPublicKey;

@Service
public class UserServiceImpl implements UserService {
	
	private UserHome userHome;
	private AccountHome accountHome;
	private UserPublicKeyHome userPublicKeyHome;

	public void setUserHome(UserHome userDAO) {
		this.userHome = userDAO;
	}
	
	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}
	
	public void setUserPublicKeyHome(UserPublicKeyHome userPublicKeyHome) {
		this.userPublicKeyHome = userPublicKeyHome;
	}

	@Override
	@Transactional
	public User getAdmin() {
		return this.userHome.getAdmin();
	}
	
	@Override
	@Transactional
	public Response addUser(User p) {
		try {
			p.setPassword(CryptoHelper.getEncryptedString(p.getPassword()));
			this.userHome.persist(p);
			Account a = new Account();
			a.setAccountType("Checking");
			a.setAmount(1000);
			a.setUser(p);
			this.accountHome.persist(a);
			this.generatePublicPrivateKeyForUser(p);
			return new Response("success", "User registered successfully!!");
		} catch (Exception e) {
			System.out.println(e);
			return new Response("error", "Failed to register user!!");
		}
	}

	@Override
	@Transactional
	public Response updateUser(User p) {
		try {
			this.userHome.merge(p);
			return new Response("success", "User updated successfully!!");
		} catch (RuntimeException e) {
			return new Response("error", "Failed to update user details!!");
		}
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.userHome.findAll();
	}
	
	@Override
	@Transactional
	public List<User> listNewUsers() {
		return this.userHome.findAllNewRegistrations();
	}
	
	@Override
	@Transactional
	public List<User> listCustomers() {
		return this.userHome.findAllCustomers();
	}
	
	@Override
	@Transactional
	public List<User> listEmployees() {
		return this.userHome.findAllEmployees();
	}

	@Override
	@Transactional
	public User getUserById(String id) {
		return this.userHome.findById(id);
	}

	@Override
	@Transactional
	public void removeUser(String id) {
		this.userHome.delete(this.userHome.findById(id));
	}
	
	@Override
	@Transactional
	public User getUserByEmailId(String emailId) {
		return this.userHome.getUserByEmailId(emailId);
	}

	@Override
	@Transactional
	public Response isValidUserAccount(int accountNo, String userId) {
		Response s = this.isValidAccount(accountNo);
		
		if (s.getStatus().equals("success")) {
			List<Account> list = this.accountHome.getUserAccounts(userId);
			for (Account account : list) {
				if (accountNo == account.getAccountNum()) {
					return new Response("success", "Account is own by current user");
				}
			}
			
			return new Response("error", "Permission denied. Please select an account to proceed!!");
		} else {
			return s;
		}
	}
	
	@Override
	@Transactional
	public Response isValidAccount(int accountNo) {
		try {
			Account a = this.accountHome.findById(accountNo);
			if (a == null) {
				return new Response("error", "Invalid account selection");
			}
		} catch (Exception e) {
			return new Response("error", "Account is Invalid.");
		}
		
		return new Response("success", "Account is valid");
	}

	@Override
	@Transactional
	public String getUserRole(String emailId) {
		// TODO Auto-generated method stub
		User p = userHome.getUserByEmailId(emailId);
		return p.getRole();
	}
	
	@Override
	@Transactional
	public Response updateUserRegistrationFlag(String id, String status) {
		User u = this.userHome.findById(id);

		if (status.equals("approve")) {
			u.setEnabled(1);
			this.userHome.merge(u);
			return new Response("success", "User Registered!");
		} else {
			removeUser(id);
			return new Response("success", "User Declined!");
		}
	}
	
	@Override
	@Transactional
	public void sendUniquePassword(String otp, String emailId) {
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
								"pitchforkbank@gmail.com", "softwaresecurity");
					}
				});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("pitchforkbank@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailId));
			message.setSubject("Test OTP");
			message.setText("Dear New Employee," + "\n\nYour New Password is " + otp);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void generatePublicPrivateKeyForUser(User u) throws Exception
	{
		PublicKey pub = PKI.generatePublicPrivateKeyPair(u);
		String pubKey = Base64.encodeBase64String(pub.getEncoded());
		UserPublicKey upub = new UserPublicKey(u.getUserId(), pubKey);
		userPublicKeyHome.persist(upub);
	}
	
	@Override
	@Transactional
	public boolean verifyByDecrypting(String plainText, String encrypted) throws Exception
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User u = userHome.getUserByEmailId(auth.getName());
		UserPublicKey upub = userPublicKeyHome.findById(u.getUserId());
		if(upub == null)
		{
			return false;
		}
		return PKI.checkByDecrypting(plainText, encrypted, upub.getPublicKey());
	}
}
