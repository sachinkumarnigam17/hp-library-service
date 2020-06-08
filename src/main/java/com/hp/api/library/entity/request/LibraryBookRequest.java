package com.hp.api.library.entity.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.entity.request.base.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookRequest extends BaseRequest {

	@JsonProperty("id")
	private String id;

	@NonNull
	@JsonProperty("title")
	private String title;

	@JsonProperty("image")
	private String image;

	@JsonProperty("remarks")
	private String remarks;

	@NonNull
	@JsonProperty("author")
	private String author;

	@JsonIgnore
	private String quantity;

	@NonNull
	@JsonProperty("publication")
	private String Publication;// drop down(cos of mispelling)

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("category")
	private String category;

	@JsonProperty("keywords")
	private String keywords;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("year_of_publication")
	private String yearOfPublication;

	@JsonProperty("edition")
	private String edition;

	@JsonProperty("purchasing_date")
	private Date purchasing_Date;

	@NonNull
	@JsonProperty("pages")
	private String pages;

	@JsonProperty("embeddedBooks")
	private List<EmbeddedBookRequest> embeddedBooks;

}
