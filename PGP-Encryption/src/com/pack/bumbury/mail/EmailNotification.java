package com.pack.bumbury.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.pack.bumbury.LogFile.LogFileLocationFinder;


public class EmailNotification {
	static final Logger logger=Logger.getLogger(EmailNotification.class);
	static String sender = com.pack.bumbury.property.ReadPropFile.sender;
	static String reciver = com.pack.bumbury.property.ReadPropFile.reciever;
	static String senderpass = com.pack.bumbury.property.ReadPropFile.senderpass;
	
	static String logFileName=LogFileLocationFinder.fileNameWithPath;
	public static void sendMail(String recipient, String subject , String body) 
	{
	
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(sender, senderpass);
					}
				});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(sender));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(reciver));

			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart
					.setText(body);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			File f=new File(logFileName);
			DataSource source = new FileDataSource(f);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(logFileName);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			logger.info("Email sent successfully...");
			logger.info(" ");
			logger.info(" ");
		} catch (MessagingException e) {
					logger.error("Email not sent due to : "+e);
		}

	}
}
