package com.example.order.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.order.entity.Product;
import com.example.order.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;
	
	public Page<Product> findAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	@Cacheable("ProductInfo")
	public Optional<Product> findProductById(String id) {
		return productRepository.findById(id);
	}
}
