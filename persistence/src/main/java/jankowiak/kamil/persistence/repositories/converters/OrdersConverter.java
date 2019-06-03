package jankowiak.kamil.persistence.repositories.converters;

import jankowiak.kamil.persistence.model.Order;

import java.util.List;

public class OrdersConverter extends JsonConverter<List<Order>> {
    public OrdersConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
