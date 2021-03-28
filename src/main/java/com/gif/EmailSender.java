package com.gif;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class EmailSender {
    private static final String FROM_ADDRESS = "sauron@gmail.com";
    private static final Properties PROPS = new Properties();

    private final String username;
    private final String password;
    private final String toAddress;

    static {
        PROPS.put("mail.smtp.host", "smtp.gmail.com");
        PROPS.put("mail.smtp.port", "587");
        PROPS.put("mail.smtp.auth", "true");
        PROPS.put("mail.smtp.starttls.enable", "true");
    }

    private EmailSender(String username, String password, String toAddress) {
        this.username = username;
        this.password = password;
        this.toAddress = toAddress;
    }

    public static EmailSender of(String username, String password, String toAddress) {
        return new EmailSender(username, password, toAddress);
    }

    public void sendEmail(String subject, String text) {
        try {
            Session session = createSession();
            Message message = createMessage(toAddress, subject, text, session);
            Transport.send(message);
            Logger.log("Email sent.");
        } catch (MessagingException e) {
            Logger.log("An error occurred.");
            e.printStackTrace();
        }
    }

    private Session createSession() {
        return Session.getInstance(PROPS,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Message createMessage(String toAddress, String subject, String text, Session session) throws MessagingException {
        var message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_ADDRESS));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
