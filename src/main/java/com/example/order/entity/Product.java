package com.example.order.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
@Schema(name = "Product", description = "Product available")
public class Product {

	@Id
	@Schema(name = "id", example = "ff07fc0d-686d-4a39-9961-ec687c98ad47", description = "Product unique id")
	private String id;
	
	@Schema(name = "name", example = "Pepsi 2L", description = "Product name")
	private String name;

	@Schema(name = "price", example = "8.50", description = "Product price")
	private BigDecimal price;
	
}
