package com.dao.wethemany.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class Others_Services {
	
	@Value("${carbonconstant}")
	private int carbonconstant;

	@Value("${companyemission}")
	private int companyemission;
	
	@Value("${spring.mail.username}")
	private String adminusername;
	
	@Value("${spring.mail.password}")
	private String adminpassword;


	
	public double calculateC02Emission(double productvalue) {
		
		double absosluteEmission=(productvalue/100);
		
		double c02EmisisionValue=((productvalue*carbonconstant*absosluteEmission)/100);
		return c02EmisisionValue;
		
	}
	
	
	public boolean sendMail(String emeail, String receiptLink) {
		
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.port", "587");  
		
		// Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(adminusername, adminpassword);
	            }
		});

	      try {
	            // Create a default MimeMessage object.
	            Message message = new MimeMessage(session);

	   	   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(adminusername));

		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	              InternetAddress.parse(emeail));

		   // Set Subject: header field
		   message.setSubject("WeMany App Product Purchase");
		   
		   String messageOne="<div style=\"background-color: #282A35;font-family: 'Source Sans Pro', sans-serif\">\n"
		   		+ "        <h1 style=\"color: #FFFFFF;font-size: 50px;font-weight: 700;margin: 2px 0 -20px 0 !important;\">\n"
		   		+ "            WeManyApp Product Purchase\n"
		   		+ "        </h1>\n"
		   		+ "        <h1 style=\"color: #FFFFFF;font-size: 26px;font-weight: 700;margin-top: 20px;\">\n"
		   		+ "            "+emeail+",\n"
		   		+ "        </h1>\n"
		   		+ "        <p style=\"font-size: 18px;color:#FFFFFF;line-height: 1.5;\">\n"
		   		+ "            We are From WeManyApp and You Have Purchased the Product From our <br/>\n"
		   		+ "            Platform. Please check the  App For More Detail and Check the Receipt   <br/>\n"
		   		+ "            Details For More Info or Even You can Contact Our Official to Know More  <br/>\n"
		   		+ "        </p>\n"
		   		+ "        <h4 style=\"color: #FFFFFF;font-size: 20px;\">Please Check the Following Receipt Of Your Payment: </h4>\n"
		   		+ "        <a href=\""+receiptLink+"\n"
		   		+ "         target=\"_blank\" style=\"background-color: white;font-size: 18px;margin: auto;display: block; width: 200px;border-radius: 25px;color: #000;border: none;padding-top:10px;padding-bottom: 10px;width: 120px;\"\n"
		   		+ "                  \n"
		   		+ "         >  Receipt Link </a>\n"
		   		+ "        <br/><br/>\n"
		   		+ "    </div>";

		   // Send the actual HTML message, as big as you like
		   message.setContent(messageOne,
	             "text/html");

		   // Send message
		   Transport.send(message);

		   System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
	      }
		

	    return true;
	}

}
