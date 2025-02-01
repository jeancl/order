package com.example.order.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.dto.Error;
import com.example.order.dto.OrderRequestDTO;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order Controller", description = "Order Data Manipulation")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderV1Controller {

	private final OrderService orderService;
	
	@Operation(
			operationId = "createOrder", 
			summary = "Create an Order", 
			description = "Create an Order with provided values and calculates total of Order", 
			tags = {"Order Controller"}, 
			responses = {
					@ApiResponse(responseCode = "201", description = "OK", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))}),
					@ApiResponse(responseCode = "400", description = "Validation errors", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))})}
			)
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequestDTO order) {
		return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
	}
	
	@Operation(
			operationId = "getOrders", 
			summary = "Get Orders", 
			description = "Get all orders paginated", 
			tags = {"Order Controller"}
			)
	@GetMapping
	public ResponseEntity<Page<Order>> getOrders(Pageable pageable) {
		return new ResponseEntity<>(orderService.getOrdersPageable(pageable), HttpStatus.OK);
	}
	
	//NOT IMPLEMENTED CRUD OPERATIONS - NOT NECESSARY AS PART OF REQUIREMENTS (YET)
	@Hidden
	@GetMapping("/{id}")
	public ResponseEntity<Page<Order>> getOrderById(@PathVariable @NotNull String id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Hidden
	@PutMapping("/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable @NotNull String id, @RequestBody @Valid OrderRequestDTO order) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	@Hidden
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable @NotNull String id) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}
}
