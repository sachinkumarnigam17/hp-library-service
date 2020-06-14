package com.hp.api.library.persistence.mongo.jpa.model;
//package com.hp.api.library.persistence.mongo.jpa.model;
//
//import java.util.Date;
//import java.util.List;
//
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
//@Document(collection = "librarybook")
//public class LibraryBookDocument extends AuditEntity {
//
//	@Field("Title")
//	private String title;
//
//	@Field("Image")
//	private String image;
//
//	@Field("Remarks")
//	private String remarks;
//
//	@Field("Author")
//	private String author;
//
//	@Field("Quantity")
//	private Integer quantity;
//
//	@Field("Publication")
//	private String Publication;// drop down(cos of mispelling)
//
//	@Field("Subject")
//	private String subject;
//
//	@Field("Category")
//	private String category;
//
//	@Field("Keywords")
//	private String keywords;
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
//	@Field("DeletedAt")
//	private Date deletedAt;
//
//	@Field("EmbeddedBooks")
//	private List<EmbeddedBookDocument> embeddedBooks;
//
//}
