package com.example.mobile_ecommerce.controller;

import com.example.mobile_ecommerce.model.Product;
import com.example.mobile_ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Get all products.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Get a single product by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if product not found
        }
    }

    /**
     * Add a new product.
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@Validated @RequestBody Product product) {
        // UUID will be generated only when adding a new product
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED); // Return 201 Created with the product
    }

    /**
     * Update an existing product by ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if product to update is not found
        }
    }

    /**
     * Delete a product by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 if deleted successfully
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if product not found
        }
    }
}
