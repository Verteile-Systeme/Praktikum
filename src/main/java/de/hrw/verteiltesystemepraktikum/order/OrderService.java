package de.hrw.verteiltesystemepraktikum.order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order saveOrder(Order order);

    void deleteOrderById(Long id) throws OrderNotFoundException;

    Long deleteAllOrders();

    Order updateOrderById(Order order, Long id) throws OrderNotFoundException;

    List<Order> getAllOrders();

    Optional<Order> findOrderById(Long id);

    List<Order> updateAllOrders(Order updatedOrders);

}
