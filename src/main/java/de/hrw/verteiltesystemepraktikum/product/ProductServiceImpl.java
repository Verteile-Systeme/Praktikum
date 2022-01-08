package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.review.Review;
import de.hrw.verteiltesystemepraktikum.review.ReviewRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    public final ProductRepository productRepository;

    public final ReviewRepository reviewRepository;

    public ProductServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public String saveProduct(Product product) {
        productRepository.save(product);
        return "Created successfully";
    }

    @Override
    public String deleteProductById(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        productRepository.deleteById(id);
        return "Deleted successfully";
    }

    @Override
    public String deleteAllProducts() {
        productRepository.deleteAll();
        return "Deleted successfully";
    }

    @Override
    public String updateProductById(Product updatedProduct, Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setBrand(updatedProduct.getBrand());
                    product.setOldPrice(updatedProduct.getOldPrice());
                    product.setNewPrice(updatedProduct.getNewPrice());
                    return productRepository.save(product);
                }).orElseGet(() -> productRepository.save(updatedProduct));
        return "Updated successfully";
    }

    @Override
    public String updateAllProducts(Product updatedProduct) {
        List<Product> allProducts = this.getAllProducts();
        if (!allProducts.isEmpty()) {
            for (Product temp :
                    allProducts) {
                updateProductById(updatedProduct, temp.getId());
            }
            return "Updated successfully";
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    public Review addReviewToProduct(Long productId, Review review) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.findProductById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            review.assignProduct(product);
            return reviewRepository.save(review);
        }
        String errorString = "The specified id <" + productId + "> does not exists.";
        throw new ProductNotFoundException(errorString);
    }

    @Override
    public Set<Review> getAllReviewsToProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.findProductById(productId);
        if (optionalProduct.isPresent()) {
            return new HashSet<>(reviewRepository.findByProductId(optionalProduct.get().getId()));
        }
        String errorString = "The specified id <" + productId + "> does not exists.";
        throw new ProductNotFoundException(errorString);
    }

    @Override
    public void deleteAllReviewsToProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = this.findProductById(productId);
        if (optionalProduct.isEmpty()) {
            String errorString = "The specified id <" + productId + "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        List<Review> reviewList = reviewRepository.findByProductId(productId);
        reviewList.forEach(review -> {
            this.deleteReviewById(review.getId());
        });
    }

    @Override
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Optional<Review> getSpecificReviewToProduct(Long productId, Long reviewId) throws ProductNotFoundException, ReviewNotFoundException {
        Optional<Product> optionalProduct = this.findProductById(productId);
        if (optionalProduct.isEmpty()) {
            String errorString = "The specified Product-ID <" + productId + "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()) {
            String errorString = "The specified Review-ID <" + reviewId + "> does not exists.";
            throw new ReviewNotFoundException(errorString);
        }
        List<Review> reviewList = reviewRepository.findByProductId(productId);
        return Optional.ofNullable(reviewList
                .stream()
                .filter(review -> Objects.equals(review.getId(), reviewId))
                .findAny()
                .orElseThrow(() -> {
                    String errorString =
                            "The specified Product-ID " + productId + " with does not feature the Review-ID " + reviewId + ".";
                    return new ReviewNotFoundException(errorString);
                }));
    }

    @Override
    public Review updateSpecificReviewToProduct(Long productId, Long reviewId, Review updatedReview) throws ProductNotFoundException {
        Optional<Review> optional = getSpecificReviewToProduct(productId, reviewId);
        return optional.map(value -> reviewRepository.findById(value.getId())
                .map(review -> {
                    review.setPublisher(updatedReview.getPublisher());
                    review.setStarRating(updatedReview.getStarRating());
                    review.setText(updatedReview.getText());
                    review.setProduct(review.getProduct());
                    return reviewRepository.save(review);
                })
                .orElseGet(() -> reviewRepository.save(updatedReview))).orElse(null);
    }

    @Override
    public List<Review> updateAllReviewsToProduct(Long productId, Review updatedReview) throws ProductNotFoundException {
        List<Review> updatedReviews = new ArrayList<>();
        Set<Review> allReviews = this.getAllReviewsToProduct(productId);

        if (!allReviews.isEmpty()) {
            for (Review temp :
                    allReviews) {
                updatedReviews.add(updateSpecificReviewToProduct(productId, temp.getId(), updatedReview));
            }
            return updatedReviews;
        }
        return null;
    }


}
