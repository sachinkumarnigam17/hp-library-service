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
public class InventoryBookRequest extends BaseRequest {

	@JsonProperty("shelf_rack_position")
	private String shelfRackPosition;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("year_of_publication")
	private String yearOfPublication;

	@NotNull(message = "book edition cannot be empty")
	@JsonProperty("edition")
	private String edition;

	@JsonProperty("purchasing_date")
	private Date purchasing_Date;

	@JsonProperty("pages")
	private String pages;

	@JsonProperty("dDC")
	private String dDC;

	@JsonProperty("apply_changes_in_all_same_edition_books")
	private Boolean applyChangesInAllSameEditionBooks;

	@JsonProperty("iSBN")
	private String iSBN;

	@JsonProperty("book_request")
	private BookRequest bookRequest;

}
