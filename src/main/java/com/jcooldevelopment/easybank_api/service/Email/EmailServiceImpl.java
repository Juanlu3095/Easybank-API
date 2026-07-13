package com.jcooldevelopment.easybank_api.service.Email;

import java.util.Properties;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.exception.EmailCouldNotBeSend;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final Environment env;
    private final Properties props;
    private final String from;
    private final String username;
    private final String password;

    public EmailServiceImpl (Environment environment) {
        this.env = environment;

        props = new Properties();
        props.put("mail.smtp.auth", env.getProperty("MAIL.SMTP.AUTH"));
        props.put("mail.smtp.starttls.enable", env.getProperty("MAIL.SMTP.STARTTLS.ENABLE"));
        props.put("mail.smtp.host", env.getProperty("MAIL.SMTP.HOST"));
        props.put("mail.smtp.port", env.getProperty("MAIL.SMTP.PORT"));
        from = env.getProperty("MAIL.FROM");
        username = env.getProperty("MAIL.USERNAME");
        password = env.getProperty("MAIL.PASSWORD");
    }

    private Session createSession() {
        return Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            }
        );
    }

    @Override
    public void sendMailToEnableUser(String usercode, String activationCode, String destination) {
        // Messaging Exception is a checked exception, try/catch or throw are required 
        try {
            Message message = new MimeMessage(createSession());
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            message.setSubject("Hello from the Mailtrap team");
            message.setText("Hi there! This are your credentials:\n usercode: ${usercode}\n password: The one you set\n To activate your account: http:8080/api/activate/${usercode}");

            Transport.send(message);
        } catch (MessagingException exception) {
            throw new EmailCouldNotBeSend("Email for user activation could not be send.");
        }
        
    }

}
