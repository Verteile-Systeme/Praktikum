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
    public Order saveOrder(Order order) {
        Order orderToSave = new Order();
        order.setDate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exist.";
            throw new OrderNotFoundException(errorString);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Long deleteAllOrders() {
        Long entities = orderRepository.count();
        orderRepository.deleteAll();
        return entities;
    }

    @Override
    public Order updateOrderById(Order updatedOrder, Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exist.";
            throw new OrderNotFoundException(errorString);
        }
        return orderRepository.findById(id)
                .map(order -> {
                    order.setProducts(updatedOrder.getProducts());
                    order.setQuantity(updatedOrder.getQuantity());
                    order.setTotal(updatedOrder.getTotal());
                    order.setDate(updatedOrder.getDate());
                    return orderRepository.save(order);
                }).orElseGet(() -> orderRepository.save(updatedOrder));
    }

    @Override
    public List<Order> updateAllOrders(Order updatedOrder) {
        List<Order> updatedOrders = new ArrayList<>();
        List<Order> allOrders = this.getAllOrders();

        if (!allOrders.isEmpty()) {
            for (Order temp : allOrders) {
                updatedOrders.add(updateOrderById(updatedOrder, temp.getId()));
            }
            return updatedOrders;
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


