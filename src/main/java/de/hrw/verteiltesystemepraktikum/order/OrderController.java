package de.hrw.verteiltesystemepraktikum.order;

import de.hrw.verteiltesystemepraktikum.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addOrder(@Valid @RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() { return orderService.getAllOrders(); }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @DeleteMapping
    public String deleteAllOrders() {
        return orderService.deleteAllOrders();
    }

    @DeleteMapping("/{id}")
    public String deleteOrderById(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

    @PutMapping("/{id}")
    public String updateOrderById(@PathVariable Long id, @Valid @RequestBody Order order) {
        return orderService.updateOrderById(order, id);
    }

    @PutMapping
    public String updateAllOrders(@Valid @RequestBody Order updatedOrder) {
        return orderService.updateAllOrders(updatedOrder);
    }

}
