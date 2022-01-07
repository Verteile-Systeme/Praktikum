package de.hrw.verteiltesystemepraktikum.product;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductControllerUnitTest {

    ProductService productService = Mockito.mock(ProductService.class);
    ProductController productController = new ProductController(productService);

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
    void addProduct() {
        when(productService.saveProduct(productInput)).thenReturn("Created successfully");
        assertEquals("Created successfully", productController.addProduct(productInput));
    }

    @Test
    void getAllProducts() {
        when(productService.getAllProducts()).thenReturn(productList);
        assertEquals(productList, productController.getAllProducts());
    }

    @Test
    void getProductById() {
        when(productService.findProductById(1L)).thenReturn(Optional.of(testProduct1));
        assertEquals(Optional.of(testProduct1), productController.getProductById(1L));
    }

    @Test
    void deleteAllProducts() {
        when(productService.deleteAllProducts()).thenReturn("Deleted successfully");
        assertEquals("Deleted successfully", productController.deleteAllProducts());
    }

    @Test
    void deleteProductById() {
        when(productService.deleteProductById(1L)).thenReturn("Deleted successfully");
        assertEquals("Deleted successfully", productController.deleteProductById(1L));
    }

    @Test
    void updateProductById() {
        when(productService.updateProductById(testProduct1, 1L)).thenReturn("Updated successfully");
        assertEquals("Updated successfully", productController.updateProductById(1L, testProduct1));
    }

    @Test
    void updateAllProducts() {
        when(productService.updateAllProducts(testProduct1)).thenReturn("Updated successfully");
        assertEquals("Updated successfully", productController.updateAllProducts( testProduct1));
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