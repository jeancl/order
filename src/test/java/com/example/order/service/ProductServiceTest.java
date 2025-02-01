package com.example.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.order.entity.Product;
import com.example.order.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepositoryMock;
	
	@Test
	void findAllProductsTest() {
		given(productRepositoryMock.findAll(any(Pageable.class))).willReturn(new PageImpl<>(Collections.<Product>emptyList()));
		
		Page<Product> result = productService.findAllProducts(mock(Pageable.class));
		
		assertNotNull(result);
		assertEquals(0, result.getSize());
		assertEquals(0, result.getTotalElements());
		assertEquals(1, result.getTotalPages());
		assertTrue(result.getContent().isEmpty());
	}
	
	@Test
	void findProductByIdTest() {
		given(productRepositoryMock.findById("test")).willReturn(Optional.of(new Product("test", "test", new BigDecimal("99.99"))));
		
		Optional<Product> result = productService.findProductById("test");
		
		assertTrue(result.isPresent());
		Product productResult = result.get();
		assertEquals("test", productResult.getId());
		assertEquals("test", productResult.getName());
		assertEquals(new BigDecimal("99.99"), productResult.getPrice());
	}
}
