package com.hp.api.library.entity.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedBookRequest {

	@NotNull(message = "dDC can't be empty")
	@JsonProperty("dDC")
	private String dDC;

	@NotNull(message = "iSBN can't be empty")
	@JsonProperty("iSBN")
	private String iSBN;

	@JsonProperty("shelf_rack_position")
	private String shelfRackPosition;
}
