package de.hrw.verteiltesystemepraktikum.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerIntegrationTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    Order orderInput = new Order(
            "100, 101",
            "1,2",
            5215,
            new Date()
    );

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
    void addOrder() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"products\": \"%s\",\n" +
                        "    \"quantity\": \"%s\",\n" +
                        "    \"total\": %d\n" +
                        "}", orderInput.getProducts(), orderInput.getQuantity(), orderInput.getTotal()
        );
        when(orderService
                .saveOrder(any()))
                .thenReturn("Created successfully");
        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo("Created successfully")));
//        verify(orderService).saveOrder(orderInput);
    }

    @Test
    void getAllOrders() throws Exception {
        when(orderService
                .getAllOrders())
                .thenReturn(orderList);
        mockMvc.perform(get("/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testOrder1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].products").value(testOrder1.getProducts()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(testOrder1.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].total").value(testOrder1.getTotal()));
        verify(orderService).getAllOrders();
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService
                .findOrderById(1L))
                .thenReturn(Optional.of(testOrder1));
        mockMvc.perform(get("/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testOrder1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products").value(testOrder1.getProducts()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(testOrder1.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(testOrder1.getTotal()));
        verify(orderService).findOrderById(1L);
    }

    @Test
    void deleteAllOrders() throws Exception {
        when(orderService
                .deleteAllOrders())
                .thenReturn("Deleted successfully");
        mockMvc.perform(delete("/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(orderService).deleteAllOrders();
    }

    @Test
    void deleteOrderById() throws Exception {
        when(orderService
                .deleteOrderById(1L))
                .thenReturn("Deleted successfully");
        mockMvc.perform(delete("/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(orderService).deleteOrderById(1L);
    }

    @Test
    void updateOrderById() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"products\": \"%s\",\n" +
                        "    \"quantity\": \"%s\",\n" +
                        "    \"total\": %d\n" +
                        "}", orderInput.getProducts(), orderInput.getQuantity(), orderInput.getTotal()
        );
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/orders/1")
                        .contentType(APPLICATION_JSON)
                        .content(request);
        when(orderService
                .updateOrderById(any(),any()))
                .thenReturn("Updated successfully");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
//        verify(orderService).updateOrderById(orderInput, 1L);
    }

    @Test
    void updateAllOrders() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"products\": \"%s\",\n" +
                        "    \"quantity\": \"%s\",\n" +
                        "    \"total\": %d\n" +
                        "}", orderInput.getProducts(), orderInput.getQuantity(), orderInput.getTotal()
        );
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/orders")
                        .contentType(APPLICATION_JSON)
                        .content(request);
        when(orderService
                .updateAllOrders(any()))
                .thenReturn("Updated successfully");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
    }
}