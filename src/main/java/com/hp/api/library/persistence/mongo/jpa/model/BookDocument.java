package com.hp.api.library.persistence.mongo.jpa.model;

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
@Document(collection = "bookdocument")
public class BookDocument extends AuditEntity {

	@Field("Title")
	private String title;

	@Field("Remarks")
	private String remarks;

	@Field("Author")
	private String author;

	@Field("Publication")
	private String Publication;

	@Field("Subject")
	private String subject;

	@Field("Category")
	private String category;

	@Field("Keywords")
	private String keywords;
}
