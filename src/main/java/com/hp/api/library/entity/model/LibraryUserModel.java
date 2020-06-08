package com.hp.api.library.entity.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUserModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("Image")
	private String image;

	@JsonProperty("Type")
	private Boolean type;

	@JsonProperty("max_book_allow")
	private Integer maxBookAllow;

}
