package com.hp.api.library.entity.request;

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
public class GetInventoryBookRequest extends BaseRequest {

	@JsonProperty("title")
	private String title;

	@JsonProperty("author")
	private String author;

	@JsonProperty("publication")
	private String Publication;

	@JsonProperty("subject")
	private String subject;

	@JsonProperty("category")
	private String category;

	@JsonProperty("keywords")
	private String keywords;
}
