package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.review.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.saveProduct(product));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.findProductById(id)
                .map(product -> ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product))
                .orElse(
                        new ResponseEntity<>(HttpStatus.NOT_FOUND)
                );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllProducts() {
        long entities = productService.deleteAllProducts();
        String response = String.format("%d Entites deleted.", entities);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id,
                                               @Valid @RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.updateProductById(product, id), HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAllProducts(@Valid @RequestBody Product updatedProduct) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productService.updateAllProducts(updatedProduct));
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<?> addReviewToProduct(
            @RequestBody Review review,
            @PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productService.addReviewToProduct(id, review));
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<?> showReviewsToProduct(
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productService.getAllReviewsToProduct(id));
        } catch (ProductNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}/ratings")
    public ResponseEntity<?> deleteReviewsToProduct(
            @PathVariable Long id
    ) {
        try {
            productService.deleteAllReviewsToProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @PutMapping("/{id}/ratings")
    public ResponseEntity<?> updateAllReviewsToProduct(
            @PathVariable Long id,
            @RequestBody Review updatedReview
    ) {
        try {
            return new
                    ResponseEntity<>(
                            productService.updateAllReviewsToProduct(id, updatedReview),
                    HttpStatus.OK);
        } catch (ProductNotFoundException | ReviewNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/{productId}/ratings/{reviewId}")
    public ResponseEntity<?> showRewviewWithId(
        @PathVariable Long productId,
        @PathVariable Long reviewId
    ) {
        try {
            return new ResponseEntity<>(productService.getSpecificReviewToProduct(productId, reviewId).get(),HttpStatus.OK);
        } catch (ProductNotFoundException | ReviewNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

    @PutMapping("{productId}/ratings/{reviewId}")
    public ResponseEntity<?> updateReviewWithId(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestBody Review updatedReview
    ) {
        try {
            return new ResponseEntity<>(
                    productService.updateSpecificReviewToProduct(productId, reviewId, updatedReview),
                    HttpStatus.OK);
        } catch (ProductNotFoundException | ReviewNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }



}
