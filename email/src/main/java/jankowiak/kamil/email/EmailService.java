package jankowiak.kamil.email;

import jankowiak.kamil.persistence.model.Customer;
import jankowiak.kamil.persistence.model.Order;
import jankowiak.kamil.persistence.model.Product;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class EmailService {

    private static final String emailAddress = "";
    private static final String emailPassword = "";
    private static final String emailRecipient = "";

    private void send(String to, String title, Customer customer, List<Order> html) throws MessagingException {
        System.out.println("SENDING EMAIL ...");
        Session session = createSession();
        MimeMessage mimeMessage = new MimeMessage(session);
        prepareEmailMessage(mimeMessage, to, title, createOrderListLikeHtml(customer, html));
        Transport.send(mimeMessage);
        System.out.println("EMAIL SENT");
    }

    private String createOrderListLikeHtml(Customer customer, List<Order> orderList) {

        Map<String, String> productsMap = orderList.stream()
                .filter(customerX -> customerX.getCustomer().getEmail().equalsIgnoreCase(customer.getEmail()))
                .collect(Collectors.toMap(
                        o -> o.getProduct().getName() + ",  price: " + o.getProduct().getPrice() + ", order date: " + o.getOrderDate() + ", category: " + o.getProduct().getCategory(),
                        o -> " Quantity: " + o.getQuantity(),
                        (v1, v2) -> v1 + v2,
                        LinkedHashMap::new
                ));
        return
                html().attr("lang", "en").with(
                        header()
                                .with(h2("Hello " + customer.getName())),
                        body()
                                .with(h4( "There is your order " + customer.getName()))
                                .with(h4(String.valueOf(productsMap)))
                ).render();

    }

    private void prepareEmailMessage(MimeMessage mimeMessage, String to, String title, String html) throws MessagingException {
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

    public void sendEmail(Customer customer, List<Order> orders) {

        try {
            send(emailRecipient, "Order", customer, orders);
        } catch (
                MessagingException e) {
            e.printStackTrace();
        }
    }

}
