package com.hp.api.library.persistence.mongo.jpa.model;

import java.util.Date;

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
@Document(collection = "issuedbookdocument")
public class IssuedBookDocument extends AuditEntity {

	@Field("UserId")
	private String userId;

	@Field("StudentId")
	private String studentId;

	@Field("InventoryId")
	private String inventoryId;

	@Field("LastDate")
	private Date lastDate;

	@Field("Return")
	private Boolean IsReturn;

	@Field("Fine")
	private float fine;

	@Field("FinePerDay")
	private float finePerDay;

	@Field("FineReason")
	private String fineReason;

	@Field("SchoolId")
	private String schoolId;

	@Field("DeletedAt")
	private Date deletedAt;

	@Field("IsDeleted")
	private Boolean IsDelete;
}
