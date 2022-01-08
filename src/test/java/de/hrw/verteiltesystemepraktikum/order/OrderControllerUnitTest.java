package de.hrw.verteiltesystemepraktikum.order;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderControllerUnitTest {

    OrderService orderService = Mockito.mock(OrderService.class);
    OrderController orderController = new OrderController(orderService);

    Order testOrder1 = new Order(
            1L,
            "100, 101",
            "1,2",
            5215,
            new Date()
    );

    Order testOrder2 = new Order(
            2L,
            "100, 101",
            "1,2",
            5215,
            new Date()
    );

    List<Order> orderList = Arrays.asList(testOrder1, testOrder2);


    @Test
    void addOrder() {
        when(orderService.saveOrder(testOrder1)).thenReturn("Created successfully");
        assertEquals("Created successfully", orderController.addOrder(testOrder1));
    }

    @Test
    void getAllOrders() {
        when(orderService.getAllOrders()).thenReturn(orderList);
        assertEquals(orderList, orderController.getAllOrders());
    }

    @Test
    void getOrderById() {
        when(orderService.findOrderById(1L)).thenReturn(Optional.of(testOrder1));
        assertEquals(Optional.of(testOrder1), orderController.getOrderById(1L));
    }

    @Test
    void deleteAllOrders() {
        when(orderService.deleteAllOrders()).thenReturn("Deleted successfully");
        assertEquals("Deleted successfully", orderController.deleteAllOrders());
    }

    @Test
    void deleteOrderById() {
        when(orderService.deleteOrderById(1L)).thenReturn("Deleted successfully");
        assertEquals("Deleted successfully", orderController.deleteOrderById(1L));
    }

    @Test
    void updateOrderById() {
        when(orderService.updateOrderById(testOrder1,1L)).thenReturn("Updated successfully");
        assertEquals("Updated successfully", orderController.updateOrderById(1L, testOrder1));
    }

    @Test
    void updateAllOrders() {
        when(orderService.updateAllOrders(testOrder1)).thenReturn("Updated successfully");
        assertEquals("Updated successfully", orderController.updateAllOrders(testOrder1));
    }
}