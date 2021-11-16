package de.hrw.verteiltesystemepraktikum.product;

import de.hrw.verteiltesystemepraktikum.appuser.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

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
        if(!productRepository.existsById(id)) {
            String errorString = "The specified id <" +  id +  "> does not exists.";
            throw new ProductNotFoundException(errorString);
        }
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    @Override
    public Product updateProductById(Product newProduct, Long id) throws ProductNotFoundException {
        if(productRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new UserNotFoundException(errorString);
        }
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setBrand(newProduct.getBrand());
                    product.setOldPrice(newProduct.getOldPrice());
                    product.setNewPrice(newProduct.getNewPrice());
                    return productRepository.save(product);
                }).orElseGet(() -> {
                    return productRepository.save(newProduct);
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
}
