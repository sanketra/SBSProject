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
import com.onlinebanking.dao.RequestsHome;
import com.onlinebanking.dao.UserHome;
import com.onlinebanking.dao.UserPublicKeyHome;
import com.onlinebanking.helpers.CryptoHelper;
import com.onlinebanking.helpers.PKI;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.models.Account;
import com.onlinebanking.models.RequestStatus;
import com.onlinebanking.models.Requests;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserPublicKey;

@Service
public class UserServiceImpl implements UserService {
	
	private UserHome userHome;
	private AccountHome accountHome;
	private UserPublicKeyHome userPublicKeyHome;
	private RequestsHome requestsHome;

	public void setUserHome(UserHome userDAO) {
		this.userHome = userDAO;
	}
	
	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}
	
	public void setUserPublicKeyHome(UserPublicKeyHome userPublicKeyHome) {
		this.userPublicKeyHome = userPublicKeyHome;
	}
	
	
	public void setRequestsHome(RequestsHome requestsHome) {
		this.requestsHome = requestsHome;
	}

	@Override
	@Transactional
	public User getAdmin() {
		return this.userHome.getAdmin();
	}
	
	@Override
	@Transactional
	public int verifyUserUniquness(User p) {
		return this.userHome.isUserUnique(p); 
	}
	
	@Override
	@Transactional
	public Response addUser(User p) {
		
		if (this.verifyUserUniquness(p) > 0) {
			return new Response("error", "User already registered with same Email or SSN or Phoneno!!");
		}
		
		try {
			p.setPassword(CryptoHelper.getEncryptedString(p.getPassword()));
			this.userHome.persist(p);
			Account a = new Account();
			a.setAccountType("Checking");
			a.setAmount(1000);
			a.setUser(p);
			this.accountHome.persist(a);
		} catch (RuntimeException e) {
			return new Response("error", "Failed to register user!!");
		}
		
		try {
			this.generatePublicPrivateKeyForUser(p);
		} catch (Exception e) {
			return new Response("error", "User registered, but failed to generate PKI.");
		}
		
		return new Response("success", "User registered successfully!!");
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
	public Response updateNewAccountRequest(String id, String status) {
		Requests t = this.requestsHome.findById(id);
		User u = getUserById(t.getFromUser());
		Response response;
		
		if (status.equals("approve")) {
			response = createAccount(u);
			if (response.getStatus().contentEquals("success")) {
				t.setStatus(RequestStatus.APPROVED);
				this.requestsHome.merge(t);
				return new Response("success", "Request approved! - New Savings Account Created");
			}
			else {
				return new Response("error", "New Account creation failed!");
			}
		} else {
			t.setStatus(RequestStatus.DECLINED);
			this.requestsHome.merge(t);
			return new Response("success", "Request declined!");
		}
	}
	
	@Override
	@Transactional
	public Response createAccount(User p) {
		try {
			Account a = new Account();
			a.setAccountType("Savings");
			a.setAmount(1000);
			a.setUser(p);
			this.accountHome.persist(a);
			return new Response("success", "New account created successfully!!");
		} catch (Exception e) {
			return new Response("error", "Failed to create New Account for User!!");
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
	public Response removeUser(String id) {
		if (id!=null){
			this.userHome.delete(this.userHome.findById(id));
		if(this.userHome.findById(id)!=null) {
			return new Response("error", "User not deleted!");
		}
		
		return new Response("success", "User deleted successfully!");
		}
		else {
			return new Response("error", "Select the user to be deleted");
		}
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
	public Response sendUniquePassword(String otp, String emailId) {
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
			return new Response("error", "Email not sent!");
		}
		
		return new Response("success", "Email Sent!");
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
