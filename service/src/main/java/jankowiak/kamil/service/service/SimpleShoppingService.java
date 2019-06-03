package jankowiak.kamil.service.service;

import jankowiak.kamil.email.EmailService;
import jankowiak.kamil.exceptions.ExceptionCode;
import jankowiak.kamil.exceptions.MyException;
import jankowiak.kamil.persistence.model.Customer;
import jankowiak.kamil.persistence.model.Order;
import jankowiak.kamil.persistence.model.Product;
import jankowiak.kamil.persistence.model.enums.Category;
import jankowiak.kamil.persistence.repositories.impl.JsonOrdersRepository;
import jankowiak.kamil.service.collector.CollectorForShopping;
import jankowiak.kamil.validation.impl.CustomerValidator;
import jankowiak.kamil.validation.impl.OrderValidator;
import jankowiak.kamil.validation.impl.ProductValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class SimpleShoppingService {

    private final Scanner sc = new Scanner(System.in);
    private List<Order> orderList;

    public SimpleShoppingService(String jsonFilename) {
        var atomicInteger = new AtomicInteger(1);
        var orderValidator = new OrderValidator();
        var productValidator = new ProductValidator();
        var customerValidator = new CustomerValidator();

        orderList = new JsonOrdersRepository()
                .findAll(jsonFilename)
                .stream()
                .filter(order -> {
                    Map<String, String> errors = orderValidator.validate(order);
                    Map<String, String> errorsP = productValidator.validate(order.getProduct());
                    Map<String, String> errorsC = customerValidator.validate(order.getCustomer());

                    errors.putAll(errorsP);
                    errors.putAll(errorsC);

                    if (orderValidator.hasErrors()) {
                        System.out.println("ORDER NO: " + atomicInteger.get());
                        System.out.println("----------- VALIDATION ERRORS -----------");
                        errors.forEach((k, v) -> System.out.println(k + " -> " + v));
                        System.out.println("-------------------------------------------");
                    }

                    atomicInteger.incrementAndGet();

                    return !orderValidator.hasErrors();
                })
                .collect(Collectors.toList());
    }

    //1
    public BigDecimal averageOfAllProductsPriceBetweenTwoDates(String earlierDate, String laterDate) {

        if (LocalDate.parse(earlierDate).isAfter(LocalDate.parse(laterDate))) {
            throw new IllegalArgumentException("INCORRECT DATES ENTERED");
        }

        return orderList
                .stream()
                .filter(d -> LocalDate.parse(d.getOrderDate()).isAfter(LocalDate.parse(earlierDate)) && LocalDate.parse(d.getOrderDate()).isBefore(LocalDate.parse(laterDate)))
                .collect(new CollectorForShopping());
    }

    //2
    public Map<Category, BigDecimal> biggestPriceInCategory() {
        return orderList
                .stream()
                .collect(Collectors.groupingBy(o -> o.getProduct().getCategory()
                        , Collectors.mapping(Order::getProduct, Collectors.toList())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        k -> k.getValue()
                                .stream()
                                .map(p -> p.getPrice()).min(Comparator.reverseOrder())
                                .orElseThrow(() -> new MyException(ExceptionCode.CUSTOMER_EMPTY, "CUSTOMER NOT FOUND"))
                ));

    }

    //3
    public void sendEmail(Customer customer) {
        new EmailService().sendEmail(customer, orderList);
    }

    //4a
    public LocalDate largestNumberOfOrders() {
        return LocalDate.parse(orderList.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()))
                .entrySet()
                .stream().min((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .orElseThrow(NullPointerException::new)
                .getKey());
    }

    //4b
    public LocalDate smallestNumberOfOrders() {
        return LocalDate.parse(orderList.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.counting()))
                .entrySet()
                .stream().min(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow(() -> new NullPointerException("NULL"))
                .getKey());
    }

    //5
    public Customer biggestSpender() {
        return orderList.stream()
                .collect(Collectors.toMap(
                        Order::getCustomer,
                        v -> v.getProduct().getPrice().multiply(new BigDecimal(v.getQuantity())),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream().min((v1, v2) -> v2.getValue().compareTo(v1.getValue()))
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    //6
    private BigDecimal orderAfterAgeDiscount() {

        var ageDiscount = new BigDecimal(0.03);

        return orderList.stream()
                .filter(age -> age.getCustomer().getAge() < 25)
                .map(order -> order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                .reduce(BigDecimal::add)
                .get()
                .multiply(ageDiscount);
    }

    private BigDecimal orderAfterDateDiscount() {

        var dateDiscount = new BigDecimal(0.02);

        return orderList.stream()
                .filter(age -> age.getCustomer().getAge() >= 25)
                .filter(day -> LocalDate.parse(day.getOrderDate()).isEqual(LocalDate.of(2021, 11, 14)))
                .map(order -> order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                .reduce(BigDecimal::add)
                .get()
                .multiply(dateDiscount);
    }

    private BigDecimal ordersDiscount() {
        return orderAfterAgeDiscount().add(orderAfterDateDiscount());
    }

    public BigDecimal priceAfterDiscount() {
        return orderList.stream()
                .map(order -> order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                .reduce(BigDecimal::add)
                .get()
                .subtract(ordersDiscount());
    }

    //7
    public Integer customersWithMinimunQuantity(int minQua) {

        if (minQua <= 0) {
            throw new IllegalArgumentException("MINQUA CANNOT BE LESS OR EQUAL 0");
        }

        return orderList.stream()
                .filter(x -> x.getQuantity() >= minQua)
                .collect(Collectors.toList()).size();
    }

    //8
    public Category mostBuyedCategory() {
        return orderList.stream()
                .map(Order::getProduct)
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()))
                .entrySet()
                .stream().min((v1, v2) -> v2.getValue().compareTo(v1.getValue()))
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    //9
    public Map<Month, Integer> ordersCounterByMonth() {

        return orderList
                .stream()
                .collect(Collectors.groupingBy(
                        o -> LocalDate.parse(o.getOrderDate()).getMonth(),
                        Collectors.collectingAndThen(Collectors.groupingBy(Order::getProduct), Map::size)
                        )
                ).entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (k, v) -> k,
                        LinkedHashMap::new
                ));

    }

    //10
    public Map<Month, Category> ordersByMostBuyedCategoryInMonth() {
        return orderList
                .stream()
                .collect(Collectors.groupingBy(
                        o -> LocalDate.parse(o.getOrderDate()).getMonth(),
                        Collectors.collectingAndThen(Collectors.groupingBy(c -> c.getProduct().getCategory(), Collectors.counting()),
                                m -> m.entrySet()
                                        .stream().min((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                                        .orElseThrow(() -> new MyException(ExceptionCode.INVALID_CATEGORY, "CATEGORY IS NULL"))
                                        .getKey())));
    }

    //11
    public List<Order> findAll() {
        return orderList;
    }

    public List<Order> findAll(String filename) {
        orderList.forEach(
                o -> System.out.println(o.getCustomer() + " products " + o.getProduct() + " quantity" + o.getQuantity() + " order date " + o.getOrderDate())
        );
        return new JsonOrdersRepository().findAll(filename);
    }

    //12
    private void generateNewOrdersList(String filename) {

        if (filename == null) {
            throw new NullPointerException("FILENAME IS NULL");
        }

        DataGeneratorService dataGeneratorService = new DataGeneratorService();
        dataGeneratorService.saveToFile(filename);
    }

    public void generateNewListWithNewOrders(String filename) {
        generateNewOrdersList(filename);
        this.orderList = new ArrayList<>(new JsonOrdersRepository().findAll(filename));
    }

    //13
    private Product getProduct() {
        System.out.println("Please give the product name? ");
        String name = sc.next();
        System.out.println("Please give the product price? ");
        BigDecimal price = sc.nextBigDecimal();
        System.out.println("Please give the product category? (A, B, C, D)");
        String cat = sc.next();
        Category category = Category.valueOf(cat);
        return new Product(name, price, category);
    }

    private Customer getCustomer() {
        System.out.println("Please give the customer name");
        String name = sc.next();
        System.out.println("Please give the customer surname");
        String surname = sc.next();
        System.out.println("Please give the customer age");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("Please give the customer email");
        String email = sc.next();
        System.out.println("Now product: ");
        return new Customer(name, surname, age, email);
    }

    private String getOrderDate() {
        System.out.println("Please give the date of order: rrrr-mm-dd");
        return sc.next();
    }

    private int getQuantityFromUser() {
        System.out.println("Please give the product quantity");
        return sc.nextInt();
    }

    private Order getOrder() {
        Customer customer = getCustomer();
        Product product = getProduct();
        String orderDate = getOrderDate();
        int quantity = getQuantityFromUser();

        return new Order(customer, product, quantity, orderDate);
    }

    public String addOrder() {
        var orderValidator = new OrderValidator();
        Order order = getOrder();
        Map<String, String> error = orderValidator.validate(order);

        if (orderValidator.hasErrors()) {
            System.out.println("-----ERRORS-----");
            error.forEach((k, v) -> System.out.println(k + " " + v));
        }
        this.orderList.add(order);
        new JsonOrdersRepository().save("C:\\Programowanie\\ShoppingManagementFinal\\persistence\\src\\main\\java\\jankowiak\\kamil\\persistence\\resources\\shoppingManagementOrderList.json", orderList);
        return order.toString();
    }
}
