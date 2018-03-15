package com.optimum.pojo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailPassword {
	static User refUser = new User();
	public void sendTempPassword(User refUser) {
		String to = refUser.getEmail();  
	    String from = "OptimumAssignmentTesting@theoptimum.net";  
	    String host = "smtp.theoptimum.net";//or IP address  
	  
	     //Get the session object  
	      Properties properties = System.getProperties();  
	      properties.setProperty("mail.smtp.host", host);  
	      properties.put("mail.smtp.port", 587);
	      Session session = Session.getDefaultInstance(properties);  
	  
	     //compose the message  
	      try{  
	         MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(from));  
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	         message.setSubject("Temp Password");  
	         message.setText("Your temp password is "+ refUser.getPassword());  
	  
	         // Send message  
	         Transport.send(message);  
	         System.out.println("message sent successfully....");  
	  
	      }catch (MessagingException mex) {mex.printStackTrace();}  
	   
	}
}
