package com.hp.api.library.entity.request;

import java.util.Date;

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
public class IssueBookRequest extends BaseRequest {

	@JsonProperty("id")
	private String id;

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("student_id")
	private String studentId;

	@JsonProperty("inventory_book_id")
	private String inventoryBookId;

	@JsonIgnore
	private Date lastDate;

	@JsonIgnore
	private int maxAllowedBook;

	@JsonIgnore
	private float fine;

	@JsonIgnore
	private float finePerDay;

	@JsonIgnore
	private Boolean IsReturn;

}
