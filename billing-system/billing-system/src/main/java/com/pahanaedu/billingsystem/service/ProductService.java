// File: src/main/java/com/pahanaedu/billingsystem/service/ProductService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Product;
import com.pahanaedu.billingsystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        // Business logic: Prevent duplicate product names (optional, but good practice)
        if (product.getName() != null && productRepository.findByName(product.getName()).isPresent()) {
            throw new RuntimeException("Product with name '" + product.getName() + "' already exists.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        // Update fields
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setUnitPrice(productDetails.getUnitPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        // Optional: Check if product exists before attempting to delete,
        // or let the repository handle it (deleteById usually doesn't throw if not found)
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }
}
