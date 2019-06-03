package jankowiak.kamil.validation.impl;

import jankowiak.kamil.persistence.model.Order;
import jankowiak.kamil.validation.Validator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderValidator implements Validator<Order> {

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Order order) {
        errors.clear();

        if (!isOrderDateValid(order)){
            errors.put("order date", "order date is not valid: " + order.getOrderDate());
        }

        if (!isQuantityValid(order)){
            errors.put("quantity", "quantity is not valid: " + order.getQuantity());
        }

        return errors;
    }

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isOrderDateValid(Order order) {
        String regexDate = "\\d{4}-\\d{2}-\\d{2}";
        return order.getOrderDate().matches(regexDate) && checkDate(order);
    }

    private boolean checkDate(Order order) {
        return LocalDate.parse(order.getOrderDate()).isAfter(LocalDate.now()) || LocalDate.parse(order.getOrderDate()).isEqual(LocalDate.now());
    }

    private boolean isQuantityValid(Order order) {
        return order.getQuantity() > 0;
    }
}
