package com.lab6.service;

import com.lab6.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product createProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
        if (product.getStockQuantity() == null || product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity must be non-negative");
        }

        Product newProduct = new Product();
        newProduct.setId(idGenerator.getAndIncrement());
        newProduct.setName(product.getName().trim());
        newProduct.setDescription(product.getDescription() != null ? product.getDescription().trim() : "");
        newProduct.setPrice(product.getPrice());
        newProduct.setStockQuantity(product.getStockQuantity() != null ? product.getStockQuantity() : 0);
        newProduct.setCreatedAt(LocalDateTime.now());
        newProduct.setUpdatedAt(LocalDateTime.now());

        products.put(newProduct.getId(), newProduct);
        return newProduct;
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = products.get(id);
        if (existingProduct == null) {
            throw new NoSuchElementException("Product with id " + id + " not found");
        }

        if (product.getName() != null && !product.getName().trim().isEmpty()) {
            existingProduct.setName(product.getName().trim());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription().trim());
        }
        if (product.getPrice() != null) {
            if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Product price must be non-negative");
            }
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getStockQuantity() != null) {
            if (product.getStockQuantity() < 0) {
                throw new IllegalArgumentException("Stock quantity must be non-negative");
            }
            existingProduct.setStockQuantity(product.getStockQuantity());
        }
        existingProduct.setUpdatedAt(LocalDateTime.now());

        return existingProduct;
    }

    public void deleteProduct(Long id) {
        Product removed = products.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("Product with id " + id + " not found");
        }
    }
}

