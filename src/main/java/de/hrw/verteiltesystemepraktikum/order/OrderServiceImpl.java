package de.hrw.verteiltesystemepraktikum.order;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    public final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    @Override
    public String saveOrder(Order order) {
        orderRepository.save(order);
        return "Created successfully";
    }

    @Override
    public String deleteOrderById(Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exist.";
            throw new OrderNotFoundException(errorString);
        }
        orderRepository.deleteById(id);
        return "Deleted successfully";
    }

    @Override
    public String deleteAllOrders() {
        orderRepository.deleteAll();
        return "Deleted successfully";
    }

    @Override
    public String updateOrderById(Order updatedOrder, Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exist.";
            throw new OrderNotFoundException(errorString);
        }
        orderRepository.findById(id)
                .map(order -> {
                    order.setProducts(updatedOrder.getProducts());
                    order.setQuantity(updatedOrder.getQuantity());
                    order.setTotal(updatedOrder.getTotal());
                    order.setDate(updatedOrder.getDate());
                    return orderRepository.save(order);
                }).orElseGet(() -> orderRepository.save(updatedOrder));
        return "Updated successfully";
    }

    @Override
    public String updateAllOrders(Order updatedOrder) {
        List<Order> allOrders = this.getAllOrders();

        if (!allOrders.isEmpty()) {
            for (Order temp : allOrders) {
                updateOrderById(updatedOrder, temp.getId());
            }
            return "Updated successfully";
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<Order> findOrderById(Long id) { return orderRepository.findById(id); }
}


