package email;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;


public class SendEmail {
	
	public void sendEmail(String emailReceiver,String messageText) throws MessagingException {
		
		String host ="smtp.googlemail.com" ;
        String user = "youremail@gmail.com";
        String pass = "password";
        
        String from = "resiliencenitj@gmail.com";
        String subject = "Library Book Issued.";
        boolean sessionDebug = false;

        Properties props = System.getProperties();

        props.put("mail.smtp.ssl.trust", "smtp.googlemail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
//
//        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(emailReceiver)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject); msg.setSentDate(new Date());
        msg.setText(messageText);

       Transport transport=mailSession.getTransport("smtp");
       transport.connect(host, user, pass);
       transport.sendMessage(msg, msg.getAllRecipients());
       transport.close();
       System.out.println("message send successfully");
	}
	
	

}
