package de.hrw.verteiltesystemepraktikum.product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveProduct( Product product);

    void deleteProductById(Long id) throws ProductNotFoundException;

    Long deleteAllProducts();

    Product updateProductById( Product product, Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Optional<Product> findProductById(Long id);

    List<Product> updateAllProducts(Product updatedProduct);

}
