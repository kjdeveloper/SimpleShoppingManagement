package jankowiak.kamil.persistence.repositories;

import jankowiak.kamil.persistence.model.Order;

import java.util.List;

public interface OrdersRepository {

    void save(final String jsonFilename, List<Order> orders);

    List<Order> findAll(String jsonFilename);
}
