package jankowiak.kamil.mainmenu.menu;

import jankowiak.kamil.persistence.model.Customer;
import jankowiak.kamil.persistence.model.enums.Category;
import jankowiak.kamil.service.service.SimpleShoppingService;
import jankowiak.kamil.service.service.UserDataService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;


public class MenuService {

    private final String filename;
    private final SimpleShoppingService simpleShoppingService;
    private final UserDataService userDataService;

    public MenuService(String filename) {
        this.filename = filename;
        simpleShoppingService = new SimpleShoppingService(filename);
        userDataService = new UserDataService();
    }

    private void menu() {
        System.out.println("1. Average of all product between two dates");
        System.out.println("2. Biggest price in category");
        System.out.println("3. Email sender with order");
        System.out.println("4. Largest and smallest numbers of orders by date");
        System.out.println("5. Biggest spender customer");
        System.out.println("6. Orders with discount");
        System.out.println("7. Customers with quantity above value as argument");
        System.out.println("8. Most buyed category");
        System.out.println("9. Orders counter by month");
        System.out.println("10. Map month = most buyed category");
        System.out.println("11. Show the order list");
        System.out.println("12. Generate NEW order list");
        System.out.println("13. Add a single order");
        System.out.println("0. Exit");
    }

    public void mainMenu() {

        int option;
        do {
            menu();
            option = userDataService.getInt("Choose option: ");
            switch (option) {
                case 1:
                    final String lc1 = "2021-10-10";
                    final String lc2 = "2021-11-20";
                    BigDecimal averagePrice = option1(lc1, lc2);
                    System.out.println(averagePrice);
                    break;

                case 2:
                    Map<Category, BigDecimal> map = option2();
                    map.forEach((key, value) -> System.out.println(key + " -> " + value));
                    break;

                case 3:
                    Customer cust = Customer.builder().name("EMANUEL").surname("Szosa").age(88).email("EMANUELSZOSA@gmail.com").build();
                    option3(cust);
                    break;

                case 4:
                    LocalDate largestNumberOfOrders = option4a();
                    System.out.println("Largest numbers date: " + largestNumberOfOrders);
                    LocalDate smalestNumberOfOrders = option4b();
                    System.out.println("Smallest numbers date: " + smalestNumberOfOrders);
                    break;

                case 5:
                    Customer biggestSpenders = option5();
                    System.out.println(biggestSpenders);
                    break;

                case 6:
                    BigDecimal priceAfterDiscount = option6();
                    System.out.println(priceAfterDiscount);
                    break;

                case 7:
                    int customersWithMinQuan = option7(10);
                    System.out.println("Numbers of customers above or equal numbers of orders: " + customersWithMinQuan);
                    break;

                case 8:
                    Category mostBuyedCategory = option8();
                    System.out.println("Most buyed category: " + mostBuyedCategory);
                    break;

                case 9:
                    Map<Month, Integer> ordersCounterByMonth = option9();
                    System.out.println(ordersCounterByMonth);
                    break;

                case 10:
                    Map<Month, Category> ordersByMostBuyedCategoryInMonth = option10();
                    System.out.println(ordersByMostBuyedCategoryInMonth);
                    break;

                case 11:
                    option11(filename);
                    break;

                case 12:
                    option12();
                    System.out.println("\n ------> I created new Order list for you :) <------ \n");
                    break;

                case 13:
                    String order = option13();
                    System.out.println("\n ------> " + order  + " added to list <------ \n");
                    break;

                case 0:
                    userDataService.close();
                    System.out.println("APP END");
                    return;

            }
        } while (true);
    }


    private BigDecimal option1(String l1, String l2) {
        return simpleShoppingService.averageOfAllProductsPriceBetweenTwoDates(l1, l2);
    }

    private Map<Category, BigDecimal> option2() {
        return simpleShoppingService.biggestPriceInCategory();
    }

    private void option3(Customer cust) {
        simpleShoppingService.sendEmail(cust);
    }

    private LocalDate option4a() {
        return simpleShoppingService.largestNumberOfOrders();
    }

    private LocalDate option4b() {
        return simpleShoppingService.smallestNumberOfOrders();
    }

    private Customer option5() {
        return simpleShoppingService.biggestSpender();
    }

    private BigDecimal option6() {
        return simpleShoppingService.priceAfterDiscount();
    }

    private int option7(int x) {
        return simpleShoppingService.customersWithMinimunQuantity(x);
    }

    private Category option8() {
        return simpleShoppingService.mostBuyedCategory();
    }

    private Map<Month, Integer> option9() {
        return simpleShoppingService.ordersCounterByMonth();
    }

    private Map<Month, Category> option10() {
        return simpleShoppingService.ordersByMostBuyedCategoryInMonth();
    }

    private void option11(String filename) {
        simpleShoppingService.findAll(filename);
    }

    private void option12(){
        simpleShoppingService.generateNewListWithNewOrders("newOrdersForShoppingManagement.json");
    }

    private String option13(){
        return simpleShoppingService.addOrder();
    }
}
