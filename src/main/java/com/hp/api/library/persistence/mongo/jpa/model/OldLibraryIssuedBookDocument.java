package com.hp.api.library.persistence.mongo.jpa.model;
//package com.hp.api.library.persistence.mongo.jpa.model;
//
//import java.util.Date;
//
//import org.bson.types.ObjectId;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import com.hp.persistence.jpa.mongo.model.base.AuditEntity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Document(collection = "libraryissuedbook")
//public class LibraryIssuedBookDocument extends AuditEntity {
//
//	@Field("Type")
//	private Boolean type;
//
//	@Field("PersonId")
//	private String personId;
//
//	@Field("DeletedAt")
//	private Date deletedAt;
//
//	@Field("LastDate")
//	private Date lastDate;
//
//	@Field("IsReturn")
//	private Boolean IsReturn;
//
//	@Field("Fine")
//	private Integer fine;
//
//	@Field("FineReason")
//	private String fineReason;
//
//	@Field("Title")
//	private String title;
//
//	@Field("Remarks")
//	private String remarks;
//
//	@Field("Author")
//	private String author;
//
//	@Field("Publication")
//	private String Publication; // drop down(cos of mispelling)
//
//	@Field("Subject")
//	private String subject;
//
//	@Field("ShelfRackPosition")
//	private String shelfRackPosition;
//
//	@Field("Category")
//	private String category;
//
//	@Field("Keywords")
//	private String keywords;
//
//	@Field("LibraryBookId")
//	private ObjectId libraryBookId;
//
//	@Field("Cost")
//	private String cost;
//
//	@Field("YearOfPublication")
//	private String yearOfPublication;
//
//	@Field("Edition")
//	private String edition;
//
//	@Field("PurchasingDate")
//	private Date purchasing_Date;
//
//	@Field("Pages")
//	private String pages;
//
//	@Field("DDC")
//	private String dDC;
//
//	@Field("ISBN")
//	private String iSBN;
//
//}