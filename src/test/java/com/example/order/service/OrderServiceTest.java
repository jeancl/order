package com.example.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.order.dto.OrderProductRequestDTO;
import com.example.order.dto.OrderRequestDTO;
import com.example.order.entity.Order;
import com.example.order.entity.Product;
import com.example.order.exception.BusinessValidationException;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;
	
	@Mock
	private OrderRepository orderRepositoryMock;
	
	@Mock
	private ProductService productServiceMock;
	
	@Test
	void getOrdersPageableTest() {
		given(orderRepositoryMock.findAll(any(Pageable.class))).willReturn(new PageImpl<>(Collections.<Order>emptyList()));
		
		Page<Order> result = orderService.getOrdersPageable(mock(Pageable.class));
		
		assertNotNull(result);
		assertEquals(0, result.getSize());
		assertEquals(0, result.getTotalElements());
		assertEquals(1, result.getTotalPages());
		assertTrue(result.getContent().isEmpty());
	}
	
	@Test
	void createOrderSuccessTest() {
    	OrderRequestDTO orderDTO = new OrderRequestDTO();
		List<OrderProductRequestDTO> orderProductList = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO();
		orderProductRequestDTO.setId("ff07fc0d-686d-4a39-9961-ec687c98ad47");
		orderProductRequestDTO.setName("Guaraná 2L");
		orderProductRequestDTO.setAmount(5);
		orderProductList.add(orderProductRequestDTO);
		orderDTO.setOrderedProducts(orderProductList);
		orderDTO.setAddress("test");
		
		Product product = new Product("ff07fc0d-686d-4a39-9961-ec687c98ad47", "Guaraná 2L", new BigDecimal("6.50"));
		
		given(productServiceMock.findProductById(anyString())).willReturn(Optional.of(product));
		given(orderRepositoryMock.save(any())).willReturn(new Order());
		
		Order result = orderService.createOrder(orderDTO);
		
		assertNotNull(result);
		verify(orderRepositoryMock, times(1)).save(any());
	}
	
	@Test
	void createOrderDuplicatedValidationTest() {
    	OrderRequestDTO orderDTO = new OrderRequestDTO();
		List<OrderProductRequestDTO> orderProductList = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO();
		orderProductRequestDTO.setId("ff07fc0d-686d-4a39-9961-ec687c98ad47");
		orderProductRequestDTO.setName("Guaraná 2L");
		orderProductRequestDTO.setAmount(5);
		orderProductList.add(orderProductRequestDTO);
		orderDTO.setOrderedProducts(orderProductList);
		orderDTO.setAddress("test");
		
		Product product = new Product("ff07fc0d-686d-4a39-9961-ec687c98ad47", "Guaraná 2L", new BigDecimal("6.50"));
		
		List<Order> duplicatedList = new ArrayList<>();
		duplicatedList.add(new Order());
		
		given(productServiceMock.findProductById(anyString())).willReturn(Optional.of(product));
		given(orderRepositoryMock.findByProductsContainingAllProductsAndAddress(any(), any())).willReturn(duplicatedList);
		
		assertThrows(BusinessValidationException.class, () -> orderService.createOrder(orderDTO));
		verify(orderRepositoryMock, times(0)).save(any());
	}
	
	@Test
	void createOrderProductNotFoundTest() {
    	OrderRequestDTO orderDTO = new OrderRequestDTO();
		List<OrderProductRequestDTO> orderProductList = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO();
		orderProductRequestDTO.setId("ff07fc0d-686d-4a39-9961-ec687c98ad47");
		orderProductRequestDTO.setName("Guaraná 2L");
		orderProductRequestDTO.setAmount(5);
		orderProductList.add(orderProductRequestDTO);
		orderDTO.setOrderedProducts(orderProductList);
		orderDTO.setAddress("test");
		
		List<Order> duplicatedList = new ArrayList<>();
		duplicatedList.add(new Order());
		
		given(productServiceMock.findProductById(anyString())).willReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderDTO));
		verify(orderRepositoryMock, times(0)).save(any());
	}
	
	@Test
	void calculateTotalOrderValueTest() {
		BigDecimal result = orderService.calculateTotalOrderValue(new BigDecimal("12.50"), new BigDecimal("6.50"), 2);
		assertEquals(new BigDecimal("25.50"), result);
	}
}
