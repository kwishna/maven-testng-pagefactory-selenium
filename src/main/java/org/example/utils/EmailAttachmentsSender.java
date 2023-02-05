package org.example.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailAttachmentsSender {
	public static void sendEmailWithAttachments(String host, String port, final String userName, final String password,
			String[] toAddress, String subject, String message, String... attachFiles)
			throws MessagingException {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(userName));

		InternetAddress[] addressTo = new InternetAddress[toAddress.length];
		for (int i = 0; i < toAddress.length; i++) {
			addressTo[i] = new InternetAddress(toAddress[i].trim());
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		if (attachFiles != null) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();
				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				multipart.addBodyPart(attachPart);
			}
		}

		msg.setContent(multipart);
		Transport.send(msg);
	}
}
