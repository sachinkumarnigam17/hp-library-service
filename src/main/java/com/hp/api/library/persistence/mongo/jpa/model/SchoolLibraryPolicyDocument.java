package com.hp.api.library.persistence.mongo.jpa.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.hp.persistence.jpa.mongo.model.base.AuditEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schoollibrarypolicy")
public class SchoolLibraryPolicyDocument extends AuditEntity {

	@Field("SchoolId")
	private String schoolId;

	@Field("UserIds")
	private List<String> userIds;

	@Field("ClassIds")
	private List<String> classIds;

	@Field("StudentIds")
	private List<String> studentIds;

	@Field("MaxAllowedBooks")
	private List<Integer> maxAllowedBook;

	@Field("LastDateOfReturn")
	private List<Integer> lastDateOfReturn;

	@Field("FinePerDay")
	private List<Integer> finePerDay;

	@Field("DeletedAt")
	private Date deletedAt;

	@Field("IsDelete")
	private Boolean IsDelete;

}
