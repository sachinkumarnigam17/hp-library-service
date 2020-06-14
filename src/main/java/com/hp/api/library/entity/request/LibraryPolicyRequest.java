package com.hp.api.library.entity.request;

import java.util.List;

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
public class LibraryPolicyRequest extends BaseRequest {

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

	@JsonProperty("last_date_of_return")
	private List<Integer> lastDateOfReturn;

	@JsonProperty("fine_per_day")
	private List<Integer> finePerDay;
}
