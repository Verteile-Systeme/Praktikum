package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.appuser.MailAlreadyExistsException;
import de.hrw.verteiltesystemepraktikum.review.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addProduct(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @DeleteMapping
    public String deleteAllProducts() {
        return productService.deleteAllProducts();
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id) {
            return productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    public String updateProductById(@PathVariable Long id,
                                               @Valid @RequestBody Product product) {
        return productService.updateProductById(product, id);
    }

    @PutMapping
    public String updateAllProducts(@Valid @RequestBody Product updatedProduct) {
        return productService.updateAllProducts(updatedProduct);
    }

    //Review

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

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handleException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }



}
