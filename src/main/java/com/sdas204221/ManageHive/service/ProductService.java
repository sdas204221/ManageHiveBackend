package com.sdas204221.ManageHive.service;

import com.sdas204221.ManageHive.exception.ResourceNotFoundException;
import com.sdas204221.ManageHive.model.Product;
import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long id, User user) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getUser().getUsername().equals(user.getUsername())) {
            throw new ResourceNotFoundException("Product not found for this user");
        }

        return product;
    }

    public List<Product> getAllProducts(User user) {
        return productRepository.findByUser(user);
    }

    public Product createProduct(Product product, User user) {
        product.setUser(user);

        // Ensure tax defaults to 0 if not provided
        if (product.getTax() == null) {
            product.setTax(0.0);
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct, User user) {
        Product existing = getProductById(id, user);

        if (updatedProduct.getProductName() != null) existing.setProductName(updatedProduct.getProductName());
        if (updatedProduct.getPrice() != null) existing.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getTax() != null) existing.setTax(updatedProduct.getTax());

        return productRepository.save(existing);
    }

    public void deleteProduct(Long id, User user) {
        Product existing = getProductById(id, user);
        productRepository.delete(existing);
    }
}
