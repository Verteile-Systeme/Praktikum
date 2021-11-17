package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.appuser.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    public final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Long deleteAllProducts() {
        Long entities = productRepository.count();
        productRepository.deleteAll();
        return entities;
    }

    @Override
    public Product updateProductById(Product updatedProduct, Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setBrand(updatedProduct.getBrand());
                    product.setOldPrice(updatedProduct.getOldPrice());
                    product.setNewPrice(updatedProduct.getNewPrice());
                    return productRepository.save(product);
                }).orElseGet(() -> {
                    return productRepository.save(updatedProduct);
                });
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
    public List<Product> updateAllProducts(Product updatedProduct) {
        List<Product> updatedProducts = new ArrayList<>();
        List<Product> allProducts = this.getAllProducts();

        if (!allProducts.isEmpty()) {
            for (Product temp :
                    allProducts) {
                updatedProducts.add(updateProductById(updatedProduct, temp.getId()));
            }
            return updatedProducts;
        }
        return null;
    }
}
