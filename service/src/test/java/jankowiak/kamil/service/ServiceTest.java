package jankowiak.kamil.service;

import jankowiak.kamil.service.service.SimpleShoppingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ServiceTest {

    //usefull to create test files
    //private JsonOrdersRepository jsonOrdersRepository = new JsonOrdersRepository();

    @Test
    @DisplayName("Find all orders")
    public void test1() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTests.json";

        var orders1 = new SimpleShoppingService(jsonFilename).findAll();

        assertEquals(3, orders1.size(), "TEST 1 FAILED");
    }

    @Test
    @DisplayName("Find biggest spender")
    public void test2() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTests.json";

        var customer = new SimpleShoppingService(jsonFilename).biggestSpender().getName();

        assertEquals("KAQE", customer, "TEST 2 FAILED");
    }

    @Test
    @DisplayName("Find avg price between earlier is before later date")
    public void test3() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest3.json";

        var throwable = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new SimpleShoppingService(jsonFilename).averageOfAllProductsPriceBetweenTwoDates("2021-01-09", "2021-01-06")
        );

        assertEquals(throwable.getMessage(), "INCORRECT DATES ENTERED", "TEST 3 FAILED");
    }

    @Test
    @DisplayName("Find avg price between two dates")
    public void test4() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest3.json";

        var avg = new SimpleShoppingService(jsonFilename).averageOfAllProductsPriceBetweenTwoDates("2021-01-01", "2021-01-06");

        assertEquals(13, avg.intValue(), "TEST 4 FAILED");
    }

    @Test
    @DisplayName("Largest number of orders by date")
    public void test6() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest6_7.json";

        var localDate = new SimpleShoppingService(jsonFilename).largestNumberOfOrders();


        assertEquals(LocalDate.of(2021, 01, 02), localDate, "TEST 6 FAILED");
    }

    @Test
    @DisplayName("Smallest number of orders by date")
    public void test7() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest6_7.json";

        var localDate = new SimpleShoppingService(jsonFilename).smallestNumberOfOrders();

        assertEquals(LocalDate.of(2021, 01, 05), localDate, "TEST 7 FAILED");
    }

    @Test
    @DisplayName("Find customer with minimun quantity")
    public void test10() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest10.json";

        var ordersTest = new SimpleShoppingService(jsonFilename).customersWithMinimunQuantity(3);

        assertEquals(2, ordersTest, "TEST 10 FAILED");
    }

    @Test
    @DisplayName("Most buyed category")
    public void test11() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest11.json";

        var category = new SimpleShoppingService(jsonFilename).mostBuyedCategory();

        assertEquals("B", category.getName(), "TEST 11 FAILED");
    }


    @Test
    @DisplayName("Orders counter by month")
    public void test12() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest12.json";

        var map = new SimpleShoppingService(jsonFilename).ordersCounterByMonth();


        assertEquals("JANUARY", map.keySet().toArray()[0].toString(), "TEST 12 FAILED");
    }

    @Test
    @DisplayName("Category counter by month")
    public void test13() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTest13.json";

        var map = new SimpleShoppingService(jsonFilename).ordersByMostBuyedCategoryInMonth();

        assertEquals("C", map.values().toArray()[0].toString(), "TEST 13 FAILED");
    }

    @Test
    @DisplayName("String filename is null")
    public void test15() {

        var jsonFilename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\service\\src\\test\\java\\jankowiak\\kamil\\service\\resources\\ordersForTests.json";

        var throwable = Assertions.assertThrows(
                NullPointerException.class,
                () -> new SimpleShoppingService(jsonFilename).generateNewListWithNewOrders(null)
        );

        assertEquals( "FILENAME IS NULL", throwable.getMessage(), "Test 15 failed");
    }
}
