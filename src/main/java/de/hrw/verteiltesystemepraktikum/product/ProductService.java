package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.review.Review;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    String saveProduct(Product product);

    String deleteProductById(Long id) throws ProductNotFoundException;

    String deleteAllProducts();

    String updateProductById(Product product, Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Optional<Product> findProductById(Long id);

    String updateAllProducts(Product updatedProduct);

    //Reviews

    Review addReviewToProduct(Long productId, Review review) throws ProductNotFoundException;

    Set<Review> getAllReviewsToProduct(Long productId) throws ProductNotFoundException;

    void deleteAllReviewsToProduct(Long productId) throws ProductNotFoundException;

    void deleteReviewById(Long id);

    Optional<Review> getSpecificReviewToProduct(Long productId, Long reviewId) throws ProductNotFoundException, ReviewNotFoundException;

    Review updateSpecificReviewToProduct(Long productId, Long reviewId, Review updatedReview) throws ProductNotFoundException;

    List<Review> updateAllReviewsToProduct(Long productId, Review updatedReview) throws ProductNotFoundException;
}
