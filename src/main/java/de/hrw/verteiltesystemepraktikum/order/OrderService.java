package de.hrw.verteiltesystemepraktikum.order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    String saveOrder(Order order);

    String deleteOrderById(Long id) throws OrderNotFoundException;

    String deleteAllOrders();

    String updateOrderById(Order order, Long id) throws OrderNotFoundException;

    List<Order> getAllOrders();

    Optional<Order> findOrderById(Long id);

    String updateAllOrders(Order updatedOrders);

}
