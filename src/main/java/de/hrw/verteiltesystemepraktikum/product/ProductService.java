package de.hrw.verteiltesystemepraktikum.product;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveProduct(@Valid Product product);

    void deleteProductById(Long id) throws ProductNotFoundException;

    void deleteAllProducts();

    Product updateProductById(@Valid Product product, Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Optional<Product> findProductById(Long id);

}
