package com.hp.api.library.entity.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolLibraryPolicyModel {

	@JsonProperty("id")
	private String id;

	@JsonProperty("user_ids")
	private List<String> userIds;

	@JsonProperty("class_ids")
	private List<String> classIds;

	@JsonProperty("student_ids")
	private List<String> studentIds;

	@JsonProperty("max_allowed_books")
	private List<Integer> maxAllowBook;

	@JsonProperty("fine_per_day")
	private List<Integer> finePerDay;

	@JsonProperty("last_date_of_return")
	private List<Integer> lastDateOfReturn;

}
