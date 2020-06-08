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
@Document(collection = "libraryuser")
public class LibraryUserDocument extends AuditEntity {

	@Field("Type")
	private Boolean type;

	@Field("UserId")
	private String userId;

	@Field("Name")
	private String name;

	@Field("Image")
	private String image;

	@Field("DeletedAt")
	private Date deletedAt;

	@Field("MaxAllowedBook")
	private int maxAllowedBook;

	@Field("ActiveIssuedBooks")
	private List<String> activeIssuedBooks;

	@Field("InActiveIssuedBooks")
	private List<String> inActiveIssuedBooks;
	

}