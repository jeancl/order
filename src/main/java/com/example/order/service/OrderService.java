package com.example.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.order.dto.OrderRequestDTO;
import com.example.order.dto.OrderProductRequestDTO;
import com.example.order.entity.Order;
import com.example.order.entity.OrderProduct;
import com.example.order.entity.OrderStatus;
import com.example.order.entity.Product;
import com.example.order.enums.OrderStatusTypes;
import com.example.order.exception.BusinessValidationException;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.repository.OrderRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final ProductService productService;
	
	@Transactional
	public Order createOrder(OrderRequestDTO orderDTO) {
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setStatus(new OrderStatus(OrderStatusTypes.RECEIVED, LocalDateTime.now()));
		
		List<OrderProduct> orderProductList = orderDTO.getOrderedProducts().stream().map(productDto -> {	
			Product product = getProductInfoOrElseThrow(productDto);
			
			order.setTotalOrderValue(calculateTotalOrderValue(order.getTotalOrderValue(), product.getPrice(), productDto.getAmount()));

			return new OrderProduct(product.getId(), product.getName(), productDto.getAmount(), product.getPrice());
		}).toList();
		
		order.setProducts(orderProductList);
		order.setAddress(orderDTO.getAddress());
		order.setCreatedDate(LocalDateTime.now());
		
		validateDuplicatedOrder(orderProductList, orderDTO.getAddress());
		
		return orderRepository.save(order);
	}
	
	public Page<Order> getOrdersPageable(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	protected BigDecimal calculateTotalOrderValue(BigDecimal orderTotalValue, BigDecimal productPrice, int amount) {
		return orderTotalValue.add(productPrice.multiply(new BigDecimal(amount)));
	}
	
	private void validateDuplicatedOrder(List<OrderProduct> orderProductList, String address) {
		if(existsOrdersByAllProductsAndAddress(orderProductList, address)) {
			log.warn("Duplicated order found.");
			throw new BusinessValidationException("orderedProducts", "Duplicated order.", "Similiar order with exactly the same products already exists.");
		}
	}

	private boolean existsOrdersByAllProductsAndAddress(List<OrderProduct> orderProductList, String address) {
		return !orderRepository.findByProductsContainingAllProductsAndAddress(orderProductList, address).isEmpty();
	}
	
	private Product getProductInfoOrElseThrow(@Valid OrderProductRequestDTO productDto) {
		return productService.findProductById(productDto.getId()).orElseThrow (() -> new ResourceNotFoundException("product"));
	}
}
