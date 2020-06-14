package com.hp.api.library.entity.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookModel {

	@JsonProperty("inventory_id")
	private String inventoryId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonProperty("last_date")
	private Date lastDate;

	@JsonProperty("is_return")
	private Boolean IsReturn;

	@JsonProperty("fine")
	private float fine;

	@JsonProperty("fine_reason")
	private String fineReason;

}
