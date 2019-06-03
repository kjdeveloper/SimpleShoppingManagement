package jankowiak.kamil.persistence.repositories;

import jankowiak.kamil.persistence.model.Customer;
import jankowiak.kamil.persistence.model.Order;
import jankowiak.kamil.persistence.model.Product;
import jankowiak.kamil.persistence.model.enums.Category;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static String randCustomerName() {

        Random rnd = new Random();
        String[] arr = {"KAMIL", "ROBERT", "KRZYSZTOF", "ANNA", "EMANUEL", "LIONEL", "ADA", "ANASTAZJA", "MAGDALENA", "ADAM",
                "MAREK", "IZA", "JOANNA", "BARTOSZ", "ZOFIA", "LUIZA", "SYLWESTER", "AGNIESZKA", "MAJKA", "ALDONA", "IWONA"};

        return arr[rnd.nextInt(arr.length)];
    }

    private static String randCustomerSurname() {
        Random rnd = new Random();
        String[] arr = {"ROBAK", "TRAUMA", "BANAN", "ANNONISZKA", "LUFA", "MESSI", "RAMIREZ", "ZJADLIW", "LENIUCZKO", "MADAL",
                "KILA", "ZALI", "ALALAL", "SZOSA", "RACZKA", "ZAILUM", "RESZTKA", "DOWYRA", "KAJAK", "MILA", "BUBA"};

        return arr[rnd.nextInt(arr.length)];
    }

    private static int randCustomerAge() {
        Random rnd = new Random();
        return rnd.nextInt(80) + 18;
    }

    private static String randCustomerMail(String name, String surname) {
        return name + "" + surname + "@gmail.com";
    }

    private static String randProductName() {
        Random rnd = new Random();
        String[] arr = {"ZELAZKO", "PLYTA", "KOMPUTER", "KLAWIATURA", "MONITOR", "KRZESLO", "TELEWIZOR", "TABLET",
                "SPODNIE", "KURTKA", "DYSK", "TELEFON"};
        return arr[rnd.nextInt(arr.length)];
    }

    private static BigDecimal randProductPrice() {
        Random rnd = new Random();
        return new BigDecimal(rnd.nextInt(1000) + 200);
    }

    private static Category randCate() {
        List<Category> categories = Collections.unmodifiableList(Arrays.asList(Category.values()));
        int size = categories.size();
        Random rnd = new Random();

        return categories.get(rnd.nextInt(size));
    }

    private static int randQuantity() {
        Random rnd = new Random();
        return rnd.nextInt(10) + 2;
    }

    public static String randLocalDate() {
        Random rnd = new Random();
        int year = rnd.nextInt(1) + 2021;
        int month = rnd.nextInt(2) + 10;
        int day = rnd.nextInt(20) + 10;

        return year + "-" + month + "-" + day;
    }

    private static Product createRandomProduct() {
        return Product.builder().name(randProductName()).category(randCate()).price(randProductPrice()).build();
    }

    private static Product product() {
        return createRandomProduct();
    }

    private static Customer createRandomCustomer() {
        String name = randCustomerName();
        String surname = randCustomerSurname();
        int age = randCustomerAge();

        return Customer.builder().name(name).surname(surname).age(age).email(randCustomerMail(name, surname)).build();
    }

    public static Order createOrder() {
        return Order.builder()
                .customer(createRandomCustomer())
                .product(product())
                .quantity(randQuantity())
                .orderDate(randLocalDate())
                .build();
    }

}
