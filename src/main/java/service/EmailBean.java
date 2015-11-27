package service;

import java.util.Date;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Session Bean implementation class EmailBean
 */
@Stateless
@LocalBean
public class EmailBean {
	
	// TODO sender details
	private String from = "";
	private boolean auth = true;
	private String username = "";
	private String password = "";
	private boolean debug = false;

    public void sendEmail(String to, String subject, String body) {
    	Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
    	
    	Authenticator authenticator = null;
    	if (auth) {
    	    props.put("mail.smtp.auth", true);
    	    authenticator = new Authenticator() {
    	        private PasswordAuthentication pa = new PasswordAuthentication(username, password);
    	        @Override
    	        public PasswordAuthentication getPasswordAuthentication() {
    	            return pa;
    	        }
    	    };
    	}
    	
    	Session session = Session.getInstance(props, authenticator);
    	session.setDebug(debug);
    	
    	MimeMessage message = new MimeMessage(session);
    	try {
    	    message.setFrom(new InternetAddress(from));
    	    InternetAddress[] address = {new InternetAddress(to)};
    	    message.setRecipients(Message.RecipientType.TO, address);
    	    message.setSubject(subject);
    	    message.setSentDate(new Date());
    	    message.setText(body);
    	    Transport.send(message);
    	} catch (MessagingException ex) {
    	    ex.printStackTrace();
    	}
    	
    	
    }

}
