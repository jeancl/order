package com.example.order.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderRequestDTO", description = "Order request")
public class OrderRequestDTO {
	
	@Size(min = 1, max = 1000)
	@Schema(name = "orderedProducts", description = "List of ordered products", requiredMode = Schema.RequiredMode.REQUIRED)
	private List<@Valid OrderProductRequestDTO> orderedProducts;

	@NotNull
	@Schema(name = "address", example = "R. Alberto Stein, 199 - Velha, Blumenau - SC, 89036-900", description = "Order delivery address", requiredMode = Schema.RequiredMode.REQUIRED)
	private String address;
}
