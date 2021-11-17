package de.hrw.verteiltesystemepraktikum.review;

import de.hrw.verteiltesystemepraktikum.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ProductService productService;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            ProductService productService
    ) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

}
