package com.hp.api.library.entity.model;

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
public class BookModel {
	@NotNull(message = "title can't be empty")
	@JsonProperty("title")
	private String title;

	@JsonProperty("remarks")
	private String remarks;

	@NotNull(message = "author can't be empty")
	@JsonProperty("author")
	private String author;

	@NotNull(message = "publication can't be empty")
	@JsonProperty("publication")
	private String Publication;

	@NotNull(message = "subject can't be empty")
	@JsonProperty("subject")
	private String subject;

	@NotNull(message = "category can't be empty")
	@JsonProperty("category")
	private String category;

	@JsonProperty("keywords")
	private String keywords;
}
