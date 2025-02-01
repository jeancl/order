package com.example.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "Error", description = "Error description object")
public class Error {

	@JsonProperty("ReasonCode")
	@Schema(name = "reasonCode", example = "invalid.value.amount", description = "Error reason code")
	private String reasonCode;
	
	@JsonProperty("Description")
	@Schema(name = "description", example = "Field invalid data", description = "Error description")
	private String description;
	
	@JsonProperty("Details")
	@Schema(name = "details", example = "must be greater than or equal to 1", description = "Error details")
	private String details;
	
	@JsonProperty("Recoverable")
	@Schema(name = "recoverable", example = "false", description = "Indicates if the error is self recoverable")
	private boolean recoverable;
}
