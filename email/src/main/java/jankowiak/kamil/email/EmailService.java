package jankowiak.kamil.email;

import j2html.tags.Tag;
import jankowiak.kamil.persistence.model.Customer;
import jankowiak.kamil.persistence.model.Order;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static j2html.TagCreator.input;

public class EmailService {

    private static final String emailAddress = "siwy247@gmail.com";
    private static final String emailPassword = "aneczka247";

    private void send(String to, String title, Customer customer, List<Order> html) throws MessagingException {
        System.out.println("SENDING EMAIL ...");
        Session session = createSession();
        MimeMessage mimeMessage = new MimeMessage(session);
        prepareEmailMessage(mimeMessage, to, title, createOrderListLikeHtml(customer, html));
        Transport.send(mimeMessage);
        System.out.println("EMAIL SENT");
    }

    private Tag createOrderListLikeHtml(Customer customer, List<Order> orderList) {
        return input()
                .withName(customer.getName())
                .withType(String.valueOf(orderList
                        .stream()
                        .filter(customerX -> customerX.equals(customer))
                        .collect(Collectors.toList())));
    }

    private void prepareEmailMessage(MimeMessage mimeMessage, String to, String title, Tag html) throws MessagingException {
        mimeMessage.setContent(html, "text/html; charset=utf-8");
        mimeMessage.setFrom(new InternetAddress(emailAddress));
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        mimeMessage.setSubject(title);
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });
    }

    public void sendEmail(Customer customer, List<Order> orderList) {

        try {
            send("kamiljankowiak247@gmail.com", "Order", customer, orderList);
        } catch (
                MessagingException e) {
            e.printStackTrace();
        }
    }

}
