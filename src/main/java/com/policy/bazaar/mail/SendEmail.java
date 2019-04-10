package com.policy.bazaar.mail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

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
public class SendEmail {

	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	@Value("${user.username}")
	String username = "{user.username}";

	@Value("${password}")
	String password = "{password}";

	public void sendEmail(String to, UUID uuid) {

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
			message.setSubject("Set your password!!!");

			message.setText(
					"<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\"><title>Mail Template</title></head><body> <h2> Your account has been created!!!!! </h2> <p> Click on the following link to set your password:<a href='http://localhost:4200/create-password/"
							+ uuid + "'> Click Here! </a> </p></body></html>",
					"UTF-8", "html");

			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (Exception mex) {
			mex.printStackTrace();
		}

	}

}
