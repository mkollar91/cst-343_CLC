package com.example.inventory.service;

import com.example.inventory.domain.Product;
import com.example.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product p) {
        return productRepository.save(p);
    }

    @Transactional
    public Product update(Product p) {
        // save() will trigger @PreUpdate in Product
        return productRepository.save(p);
    }

    // ProductService.java
    @Transactional
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

}
