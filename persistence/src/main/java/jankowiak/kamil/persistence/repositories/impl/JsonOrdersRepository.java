package jankowiak.kamil.persistence.repositories.impl;

import jankowiak.kamil.persistence.model.Order;
import jankowiak.kamil.persistence.repositories.OrdersRepository;
import jankowiak.kamil.persistence.repositories.converters.OrdersConverter;

import java.util.Collections;
import java.util.List;

public class JsonOrdersRepository implements OrdersRepository {

    @Override
    public void save(String jsonFilename, List<Order> orders) {
        OrdersConverter ordersConverter = new OrdersConverter(jsonFilename);
        ordersConverter.toJson(orders);
    }

    @Override
    public List<Order> findAll(String jsonFilename) {
        return new OrdersConverter(jsonFilename)
                .fromJson()
                .orElse(Collections.emptyList());
    }


}
