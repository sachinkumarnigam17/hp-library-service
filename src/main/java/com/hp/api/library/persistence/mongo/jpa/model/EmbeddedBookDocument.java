package com.hp.api.library.persistence.mongo.jpa.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedBookDocument {

	@Field("LibraryBookId")
	private ObjectId id;

	@Field("ShelfRackPosition")
	private String shelfRackPosition;

	@Field("DDC")
	private String dDC;

	@Field("ISBN")
	private String iSBN;

	@Field("IsIssue")
	private Boolean IsIssue;

}
