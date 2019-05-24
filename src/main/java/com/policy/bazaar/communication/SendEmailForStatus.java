package com.policy.bazaar.communication;

import java.util.Base64;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component
@PropertySource("classpath:auth.properties")
public class SendEmailForStatus {
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	@Value("${user.username}")
	String username = "{user.username}";

	@Value("${password}")
	String password = "{password}";

	public void sendEmail(String to, String name, String Status) throws Exception {

		Base64.Decoder decoder = Base64.getDecoder();
		String decodedpassword = new String(decoder.decode(password));

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "true");
		properties.put("mail.store.protocol", "pop3");
		properties.put("mail.transport.protocol", "smtp");

		try {
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, decodedpassword);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Policy Update!!!");

			message.setText(
					"<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\"><title>Mail Template</title></head><body> <h2> Hi, "+ name+" your Policy is "+Status+" </h2> <p> All the best.</p></body></html>",
					"UTF-8", "html");

			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (Exception mex) {
			throw mex;
		}

	}

}
