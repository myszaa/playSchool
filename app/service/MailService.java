package service;

import model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
public class MailService {

    public static void sendEmail(User user)
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("playframeworkschool@gmail.com", "playFramework");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("playframeworkschool@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Aktywacja konta na seriwsie playSchool");
            message.setText("Dziękujemy za rejestracje, Aby aktywować konto, kliknij w poniższy link.\n" +
                    " http://localhost:9000/activate?code=" + user.getActivationtoken());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
