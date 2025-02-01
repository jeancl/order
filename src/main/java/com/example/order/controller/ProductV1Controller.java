package com.example.order.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.entity.Product;
import com.example.order.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Product Controller", description = "Product Lookup")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductV1Controller {

	private final ProductService productService;
	
	@Operation(
			operationId = "getProducts", 
			summary = "Get Products", 
			description = "Get all available products paginated", 
			tags = {"Product Controller"}
			)
	@GetMapping
	public ResponseEntity<Page<Product>> getProducts(Pageable pageable) {
		return new ResponseEntity<>(productService.findAllProducts(pageable), HttpStatus.OK);
	}
}
