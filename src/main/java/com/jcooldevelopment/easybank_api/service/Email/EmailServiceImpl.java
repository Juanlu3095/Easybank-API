package com.jcooldevelopment.easybank_api.service.Email;

import java.util.Properties;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jcooldevelopment.easybank_api.exception.EmailCouldNotBeSend;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

// https://mailtrap.io/blog/jakarta-mail-tutorial/
// https://docs.oracle.com/es-ww/iaas/Content/Email/Reference/javamail.htm
// https://github.com/DanielEspanadero/java-mail/
// https://www.baeldung.com/thymeleaf-in-spring-mvc
@Service
public class EmailServiceImpl implements EmailService {

    private final Environment env;
    private final Properties props;
    private final String from;
    private final String username;
    private final String password;
    private final String domain;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl (Environment environment, TemplateEngine templateEngine) {
        this.env = environment;
        this.templateEngine = templateEngine;

        props = new Properties();
        props.put("mail.smtp.auth", env.getProperty("MAIL.SMTP.AUTH"));
        props.put("mail.smtp.starttls.enable", env.getProperty("MAIL.SMTP.STARTTLS.ENABLE"));
        props.put("mail.smtp.host", env.getProperty("MAIL.SMTP.HOST"));
        props.put("mail.smtp.port", env.getProperty("MAIL.SMTP.PORT"));
        from = env.getProperty("MAIL.FROM");
        username = env.getProperty("MAIL.USERNAME");
        password = env.getProperty("MAIL.PASSWORD");
        domain = env.getProperty("DOMAIN_URL");
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
            message.setSubject("Activate your EasyBank account");
            
            // Process Thymeleaf template
            Context context = new Context();
            context.setVariable("domain", domain);
            context.setVariable("code", activationCode);
            context.setVariable("usercode", usercode);
            String html = templateEngine.process("activationEmail", context);
            message.setContent(html, "text/html;charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException exception) {
            throw new EmailCouldNotBeSend("Email for user activation could not be sent.");
        }
        
    }

}
