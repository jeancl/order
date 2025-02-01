package com.example.order.entity;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderProduct", description = "Ordered products")
public class OrderProduct {

	@Schema(name = "id", example = "ff07fc0d-686d-4a39-9961-ec687c98ad47", description = "Product unique id")
	private String id;
	
	@Schema(name = "name", example = "Pepsi 2L", description = "Ordered Product name")
	private String name;
	
	@Schema(name = "amount", example = "5", description = "Ordered product amount")
	private Integer amount;
	
	@Schema(name = "price", example = "8.50", description = "Ordered product price")
	private BigDecimal price;
	
}
