package com.hp.api.library.entity.request;

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
public class BookRequest extends BaseRequest {

	@JsonProperty("id")
	private String id;

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

	@NotNull(message = "keywords can't be empty")
	@JsonProperty("keywords")
	private String keywords;

}
