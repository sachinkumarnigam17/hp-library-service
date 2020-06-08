package com.hp.api.library.entity.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	@JsonProperty("book_id")
	private String bookId;

	@JsonProperty("Type")
	private Boolean type;

	@JsonProperty("PersonId")
	private String personId;

	@NotNull(message = "the return date should not null")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonProperty("LastDate")
	private Date lastDate;

	@JsonProperty("Fine")
	private Integer fine;

	@JsonProperty("FineReason")
	private String fineReason;

}
