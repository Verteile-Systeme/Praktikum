package de.hrw.verteiltesystemepraktikum.orders;

import de.hrw.verteiltesystemepraktikum.product.Product;

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
