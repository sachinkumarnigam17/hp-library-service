package com.hp.api.library.entity.request;

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

	@JsonProperty("dDC")
	private String dDC;

	@JsonProperty("iSBN")
	private String iSBN;

	@JsonProperty("shelf_rack_position")
	private String shelfRackPosition;
}
