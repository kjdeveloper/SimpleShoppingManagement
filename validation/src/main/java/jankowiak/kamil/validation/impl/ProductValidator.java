package jankowiak.kamil.validation.impl;

import jankowiak.kamil.persistence.model.Product;
import jankowiak.kamil.persistence.model.enums.Category;
import jankowiak.kamil.validation.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProductValidator implements Validator<Product> {

    private Map<String, String> errors = new HashMap<>();


    @Override
    public Map<String, String> validate(Product product) {
        errors.clear();

        if (product == null) {
            errors.put("product", "null");
        }

        if (!isNameValid(product)) {
            errors.put("name", "name is not valid: " + product.getName());
        }

        if (!isCategoryValid(product)){
            errors.put("category", "category is not valid: " + product.getCategory());
        }

        if (!isPriceValid(product)) {
            errors.put("price", "price is not valid: " + product.getPrice());
        }

        return errors;
    }

    private boolean isPriceValid(Product product) {
        return product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isNameValid(Product product) {
        final String NAME_REGEX = "[A-Z ]+";
        return product.getName() != null && product.getName().matches(NAME_REGEX);
    }

    private boolean isCategoryValid(Product product){
        Set<Category> categorySet = new HashSet<>();
        for (Category c : Category.values()) {
            categorySet.add(c);
        }

        return categorySet.contains(product.getCategory());
    }

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
