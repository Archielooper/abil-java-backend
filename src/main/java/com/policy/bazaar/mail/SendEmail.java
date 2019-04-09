package com.policy.bazaar.mail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

	public void sendEmail(String to) {

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
		    MimeMultipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            
            //Set key values
            Map<String, String> input = new HashMap<String, String>();
               input.put("Archit", "policyApp.com");
               input.put("Topic", "Set Password");
               input.put("Content In", "English");
             
            //HTML mail content
            String htmlText = readEmailFromHtml("C:/Users/manand/Downloads/bazaar/src/main/java/com/policy/bazaar/mail/mailTemplate.html",input);
            messageBodyPart.setContent(htmlText, "text/html");
            
            multipart.addBodyPart(messageBodyPart); 
            message.setContent(multipart);
 

			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (Exception mex) {
			mex.printStackTrace();
		}

	}
	
	protected String readEmailFromHtml(String filePath, Map<String, String> input)
	{
	    String msg = readContentFromFile(filePath);
	    try
	    {
	    Set<Entry<String, String>> entries = input.entrySet();
	    for(Map.Entry<String, String> entry : entries) {
	        msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
	    }
	    }
	    catch(Exception exception)
	    {
	        exception.printStackTrace();
	    }
	    return msg;
	}
	//Method to read HTML file as a String 
	private String readContentFromFile(String fileName)
	{
	    StringBuffer contents = new StringBuffer();
	    
	    try {
	      //use buffering, reading one line at a time
	      BufferedReader reader =  new BufferedReader(new FileReader(fileName));
	      try {
	        String line = null; 
	        while (( line = reader.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	          reader.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    return contents.toString();
	}

}
