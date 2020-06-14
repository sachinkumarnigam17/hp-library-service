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
@Document(collection = "inventorybook")
public class InventoryBookDocument extends AuditEntity {

	@Field("ShelfRackPosition")
	private String shelfRackPosition;

	@Field("Cost")
	private String cost;

	@Field("YearOfPublication")
	private String yearOfPublication;

	@Field("Edition")
	private String edition;

	@Field("PurchasingDate")
	private Date purchasing_Date;

	@Field("Pages")
	private String pages;

	@Field("DeletedAt")
	private Date deletedAt;

	@Field("IsDelete")
	private Boolean IsDelete;

	@Field("DDC")
	private String dDC;

	@Field("ISBN")
	private String iSBN;

	@Field("IsIssue")
	private Boolean IsIssue;

	@Field("BookDocument")
	private BookDocument bookDocument;
}
