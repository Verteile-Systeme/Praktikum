package de.hrw.verteiltesystemepraktikum.product;

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

@WebMvcTest(controllers = ProductController.class)
class ProductControllerIntegrationTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    Product productInput = new Product(
            "testname",
            "testbrand",
            555,
            111
    );

    Product testProduct1 = new Product(
            1L,
            "testname",
            "testbrand",
            555,
            111
    );

    Product testProduct2 = new Product(
            2L,
            "testname",
            "testbrand",
            555,
            111
    );

    List<Product> productList = Arrays.asList(testProduct1, testProduct2);

    @Test
    void addProductShouldCreateANewProduct() throws Exception {
        String request = String.format(
                "{\n" +
                "    \"name\": \"%s\",\n" +
                "    \"brand\": \"%s\"\n" +
                "}",productInput.getName(), productInput.getBrand());
        when(productService
                .saveProduct(productInput))
                .thenReturn("Created successfully");
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo("")));
//        verify(productService).saveProduct(productInput);
    }

    @Test
    void getAllProducts() throws Exception {
        when(productService
                .getAllProducts())
                .thenReturn(productList);
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testProduct1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(testProduct1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand").value(testProduct1.getBrand()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].newPrice").value(testProduct1.getNewPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].oldPrice").value(testProduct1.getOldPrice()));
        verify(productService).getAllProducts();
    }

    @Test
    void getProductById() throws Exception {
        when(productService
                .findProductById(1L))
                .thenReturn(Optional.of(testProduct1));
        mockMvc.perform(get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testProduct1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testProduct1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value(testProduct1.getBrand()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.newPrice").value(testProduct1.getNewPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.oldPrice").value(testProduct1.getOldPrice()));
        verify(productService).findProductById(1L);
    }

    @Test
    void deleteAllProducts() throws Exception {
        when(productService
                .deleteAllProducts())
                .thenReturn("Deleted successfully");
        mockMvc.perform(delete("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(productService).deleteAllProducts();
    }

    @Test
    void deleteProductById() throws Exception {
        when(productService
                .deleteProductById(1L))
                .thenReturn("Deleted successfully");
        mockMvc.perform(delete("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Deleted successfully")));
        verify(productService).deleteProductById(1L);
    }

    @Test
    void updateProductById() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"brand\": \"%s\"\n" +
                        "}",productInput.getName(), productInput.getBrand());
        long id = 1;
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/products/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(request);

        when(productService
                .updateProductById(any(),any()))
                .thenReturn("Updated successfully");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
//        verify(productService).updateProductById(productInput, 1L);
    }

    @Test
    void updateAllProducts() throws Exception {
        String request = String.format(
                "{\n" +
                        "    \"name\": \"%s\",\n" +
                        "    \"brand\": \"%s\"\n" +
                        "}",productInput.getName(), productInput.getBrand());
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/products")
                        .contentType(APPLICATION_JSON)
                        .content(request);

        when(productService
                .updateAllProducts(any()))
                .thenReturn("Updated successfully");
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));
//        verify(productService).updateProductById(productInput, 1L);
    }

    @Test
    void addReviewToProduct() {
    }

    @Test
    void showReviewsToProduct() {
    }

    @Test
    void deleteReviewsToProduct() {
    }

    @Test
    void updateAllReviewsToProduct() {
    }

    @Test
    void showRewviewWithId() {
    }

    @Test
    void updateReviewWithId() {
    }
}