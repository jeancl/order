package com.example.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "OrderProductRequestDTO", description = "Product order")
public class OrderProductRequestDTO {
	
	@NotNull
	@Schema(name = "id", example = "ff07fc0d-686d-4a39-9961-ec687c98ad47", description = "Product unique id", requiredMode = Schema.RequiredMode.REQUIRED)
	private String id;
	
	@Min(1)
	@NotNull
	@Schema(name = "amount", example = "5", description = "Ordered product amount", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer amount; 

	@NotNull
	@Schema(name = "name", example = "Pepsi 2L", description = "Ordered Product name", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;
}
