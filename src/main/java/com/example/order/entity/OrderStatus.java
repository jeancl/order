package com.example.order.entity;

import java.time.LocalDateTime;

import com.example.order.enums.OrderStatusTypes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderStatus", description = "Order status")
public class OrderStatus {

	@Schema(name = "status", example = "RECEIVED", description = "Description of the order status")
	private OrderStatusTypes status;
	
	@Schema(name = "lastUpdatedDate", description = "Last updated date of the order status")
	private LocalDateTime lastUpdatedDate;
}
