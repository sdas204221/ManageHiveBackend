package com.sdas204221.ManageHive.controller;

import com.sdas204221.ManageHive.model.Product;
import com.sdas204221.ManageHive.model.User;
import com.sdas204221.ManageHive.service.ProductService;
import com.sdas204221.ManageHive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    // Get single product
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        return productService.getProductById(id, userService.findById(userDetails.getUsername()));
    }

    // Get all products for authenticated user
    @GetMapping("/products")
    public List<Product> getProducts(@AuthenticationPrincipal UserDetails userDetails) {
        return productService.getAllProducts(userService.findById(userDetails.getUsername()));
    }

    // Create new product
    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return productService.createProduct(product, userService.findById(userDetails.getUsername()));
    }

    // Update product
    @PatchMapping("/product/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return productService.updateProduct(id, product, userService.findById(userDetails.getUsername()));
    }

    // Delete product
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        productService.deleteProduct(id, userService.findById(userDetails.getUsername()));
    }
}
