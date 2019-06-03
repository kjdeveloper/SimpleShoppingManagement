package jankowiak.kamil.service.service;


import jankowiak.kamil.persistence.model.Order;
import jankowiak.kamil.persistence.repositories.DataGenerator;
import jankowiak.kamil.persistence.repositories.converters.OrdersConverter;

import java.util.Arrays;
import java.util.List;

public class DataGeneratorService {

    private final List<Order> orderList;
    private OrdersConverter ordersConverter;

    public DataGeneratorService() {
        this.orderList = generateOrders();
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    private List<Order> generateOrders(){
        DataGenerator dataGenerator = new DataGenerator();

        return Arrays.asList(
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder(),
                dataGenerator.createOrder()
        );
    }

    public void saveToFile(String filename){
        ordersConverter = new OrdersConverter(filename);
        ordersConverter.toJson(orderList);
    }


}
