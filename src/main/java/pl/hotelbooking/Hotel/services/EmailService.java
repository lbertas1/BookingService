package pl.hotelbooking.Hotel.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.hotelbooking.Hotel.exceptions.EmailServiceException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailService {

    private static final String EMAIL_ADDRESS = "lukaszprogramowanie94@gmail.com";
    private static final String EMAIL_PASSWORD = "prosteHaslo";

    public static void send(String to, String title, String html) throws EmailServiceException {
        try {
            System.out.println("Sending email ...");
            Session session = createSession();
            MimeMessage mimeMessage = new MimeMessage(session);
            prepareEmailMessage(mimeMessage, to, title, html);
            Transport.send(mimeMessage);
            System.out.println("Email has been sent.");
        } catch (Exception e) {
            throw new EmailServiceException(e.getMessage());
        }
    }

    private static void prepareEmailMessage(MimeMessage mimeMessage, String to, String title, String html) throws EmailServiceException {
        try {
            mimeMessage.setContent(html, "text/html; charset=utf-8");
            mimeMessage.setFrom(new InternetAddress(EMAIL_ADDRESS));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(title);
        } catch (Exception e) {
            throw new EmailServiceException(e.getMessage());
        }
    }

    private static Session createSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ADDRESS, EMAIL_PASSWORD);
            }
        });
    }
}
