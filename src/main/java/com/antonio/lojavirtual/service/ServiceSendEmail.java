package com.antonio.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiceSendEmail {
	
	private String userName = "foguinho.zonasul@gmail.com";
	
	private String senha = "2102252";
	
	@Async()
	public void enviarEmailHtml(String assunto, String messagem, String emailDestino ) throws UnsupportedEncodingException, MessagingException {
		
		Properties properties = new Properties();
		properties.put("email.smtp.ssl.trust", "*");
		properties.put("email.smtp.auth", "true");
		properties.put("email.smtp.starttls", "false");
		properties.put("email.smtp.host", "smtp.gmail.com");
		properties.put("email.smtp.port", "465");
		properties.put("email.smtp.socketFactory.port", "465");
		properties.put("email.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		
		Session session = Session.getInstance(properties, new Authenticator() {
			
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(userName, senha);
			}
			
			
		});
		
		session.setDebug(true);
		
		Address[] toUser = InternetAddress.parse(emailDestino);
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, "Antonio - do java WEB","UTF-8" ));
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assunto);
		message.setContent(message, "text/html; charset=utf-8");
		
		Transport.send(message);
		
	}
	
	
}
