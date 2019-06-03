package jankowiak.kamil.persistence.model;

import java.util.Objects;

public class Order {
    private Customer customer;
    private Product product;
    private int quantity;
    private String orderDate;

    public Order() {
    }

    public Order(Customer customer, Product product, int quantity, String orderDate) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order2 = (Order) o;
        return quantity == order2.quantity &&
                Objects.equals(customer, order2.customer) &&
                Objects.equals(product, order2.product) &&
                Objects.equals(orderDate, order2.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, product, quantity, orderDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                '}';
    }

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    // -------------------------- BUILDER --------------------------------------------
    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {

        private Customer customer;
        private Product product;
        private int quantity;
        private String orderDate;

        public OrderBuilder customer(Customer customer) {
           this.customer = customer;
            return this;
        }

        public OrderBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderBuilder orderDate(String orderDate) {
            this.orderDate = orderDate;
            return this;
        }


        public Order build() {
            return new Order(customer, product, quantity, orderDate);
        }

    }
}
