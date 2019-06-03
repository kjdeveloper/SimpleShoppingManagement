package jankowiak.kamil.validation.impl;


import jankowiak.kamil.persistence.model.Customer;
import jankowiak.kamil.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class CustomerValidator implements Validator<Customer> {

    private static final String NAME_REGEX = "[A-Z ]+";
    private static final String EMAIL_REGEX = "[\\w\\.]+@(gmail|onet|yahoo|wp|hotmail)\\.(com|pl)";

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Customer customer) {
        errors.clear();

        if (customer == null) {
            errors.put("customer", "null");
        }

        if (!isEmailValid(customer)){
            errors.put("email", "email is not valid: " + customer.getEmail());
        }

        if (!isAgeValid(customer)){
            errors.put("age", "age is not valid: " + customer.getAge());
        }

        if (!isSurnameValid(customer)){
            errors.put("surname", "surname is not valid: " + customer.getSurname());
        }

        if (!isNameValid(customer)){
            errors.put("name", "name is not valid: " + customer.getName());
        }

        return errors;
    }

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isEmailValid(Customer customer) {
        return customer.getEmail().matches(EMAIL_REGEX);
    }

    private boolean isAgeValid(Customer customer) {
        return customer.getAge() >= 18;
    }

    private boolean isSurnameValid(Customer customer) {
        return customer.getSurname().matches(NAME_REGEX);
    }

    private boolean isNameValid(Customer customer) {
        return customer.getName().matches(NAME_REGEX);

    }
}
