package com.hp.api.library.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class LibraryUserRequest extends BaseRequest {

	@JsonProperty("id")
	private String id;

	@JsonIgnore
	private String userId;

	@JsonIgnore
	private String name;

	@JsonProperty("image")
	private String image;

	@JsonProperty("type")
	private Boolean type;

	@JsonProperty("max_book_allow")
	private Integer maxBookAllow;

}
