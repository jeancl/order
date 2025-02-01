package com.example.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Document(collection = "orders")
@Schema(name = "Order", description = "Order requested")
public class Order {

	@Id
	@Schema(name = "id", example = "ff07fc0d-686d-4a39-9961-ec687c98ad47", description = "Order unique id")
	private String id;
	
	private OrderStatus status;
	
	private List<OrderProduct> products;
	
	@Schema(name = "totalOrderValue", example = "850.35", description = "Calculated total ordered value")
	private BigDecimal totalOrderValue = new BigDecimal(0);

	@Schema(name = "address", example = "R. Alberto Stein, 199 - Velha, Blumenau - SC, 89036-900", description = "Order delivery address")
	private String address;
	
	@Schema(name = "createdDate", description = "Order creation date")
	private LocalDateTime createdDate;
}
