package com.hp.api.library.entity.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.entity.request.base.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditBookRequest extends BaseRequest {

	@JsonProperty("id")
	private String id;

	@JsonProperty("shelf_rack_position")
	private String shelfRackPosition;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("year_of_publication")
	private String yearOfPublication;

	@NotNull(message = "book edition cannot be empty")
	@JsonProperty("edition")
	private String edition;

	@JsonProperty("edition_old")
	private String editionOld;

	@JsonProperty("purchasing_date")
	private Date purchasing_Date;

	@NotNull(message = "pages can't be empty")
	@JsonProperty("pages")
	private String pages;

	@NotNull(message = "dDC can't be empty")
	@JsonProperty("dDC")
	private String dDC;

	@NotNull(message = "iSBN can't be empty")
	@JsonProperty("iSBN")
	private String iSBN;

	@JsonProperty("book_request")
	private BookRequest bookRequest;

}
