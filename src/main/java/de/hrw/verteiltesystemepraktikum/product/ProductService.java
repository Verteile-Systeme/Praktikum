package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.review.Review;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    Product saveProduct(Product product);

    void deleteProductById(Long id) throws ProductNotFoundException;

    Long deleteAllProducts();

    Product updateProductById(Product product, Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Optional<Product> findProductById(Long id);

    List<Product> updateAllProducts(Product updatedProduct);

    //Reviews

    Review addReviewToProduct(Long productId, Review review) throws ProductNotFoundException;

    Set<Review> showReviewsToProduct(Long productId) throws ProductNotFoundException;

    void deleteAllReviewsToProduct(Long productId) throws ProductNotFoundException;

    void deleteReviewById(Long id);

    Optional<Review> getSpecificReviewToProduct(Long productId, Long reviewId) throws ProductNotFoundException, ReviewNotFoundException;

    Review updateSpecificReviewToProduct(Long productId, Long reviewId, Review updatedReview) throws ProductNotFoundException;
}
