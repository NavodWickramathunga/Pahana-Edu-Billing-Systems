// File: src/main/java/com/pahanaedu/billingsystem/controller/ProductController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Product;
import com.pahanaedu.billingsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this as a REST controller
@RequestMapping("/api/products") // Base path for all product-related endpoints
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET all products: GET http://localhost:8080/api/products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET product by ID: GET http://localhost:8080/api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // CREATE a new product: POST http://localhost:8080/api/products
    // Body (JSON): { "name": "Electricity Unit", "description": "Per kWh charge", "unitPrice": 15.50 }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.createProduct(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // e.g., if product name already exists
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request with empty body
        }
    }

    // UPDATE an existing product: PUT http://localhost:8080/api/products/{id}
    // Body (JSON): { "name": "Electricity Unit", "description": "Updated per kWh charge", "unitPrice": 16.00 }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if product not found
        }
    }

    // DELETE a product: DELETE http://localhost:8080/api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String id) {
        // Optional: Check if product exists before attempting to delete
        if (productService.getProductById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
