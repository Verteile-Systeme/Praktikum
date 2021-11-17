package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.review.Review;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    Product saveProduct( Product product);

    void deleteProductById(Long id) throws ProductNotFoundException;

    Long deleteAllProducts();

    Product updateProductById( Product product, Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Optional<Product> findProductById(Long id);

    List<Product> updateAllProducts(Product updatedProduct);

    Review addReviewToProduct(Long productId, Review review) throws ProductNotFoundException;

    Set<Review> showReviewsToProduct(Long productId) throws ProductNotFoundException;
}
