package com.hp.api.library.entity.model;

import java.util.Comparator;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBookModel {

	@JsonProperty("shelf_rack_position")
	private String shelfRackPosition;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("year_of_publication")
	private String yearOfPublication;

	@JsonProperty("edition")
	private String edition;

	@JsonProperty("purchasing_date")
	private Date purchasing_Date;

	@JsonProperty("pages")
	private String pages;

	@JsonProperty("deletedAt")
	private Date deletedAt;

	@JsonProperty("isDelete")
	private Boolean IsDelete;

	@JsonProperty("dDC")
	private String dDC;

	@JsonProperty("iSBN")
	private String iSBN;

	@JsonProperty("IsIssue")
	private Boolean IsIssue;

	@NotNull(message = "title can't be empty")
	@JsonProperty("title")
	private String title;

	@JsonProperty("remarks")
	private String remarks;

	@NotNull(message = "author can't be empty")
	@JsonProperty("author")
	private String author;

	@NotNull(message = "publication can't be empty")
	@JsonProperty("publication")
	private String publication;

	@NotNull(message = "subject can't be empty")
	@JsonProperty("subject")
	private String subject;

	@NotNull(message = "category can't be empty")
	@JsonProperty("category")
	private String category;

	@NotNull(message = "keywords can't be empty")
	@JsonProperty("keywords")
	private String keywords;

	public static Comparator<InventoryBookModel> titleComparator = new Comparator<InventoryBookModel>() {

		public int compare(InventoryBookModel s1, InventoryBookModel s2) {
			String title1 = s1.getTitle().toUpperCase();
			String title2 = s2.getTitle().toUpperCase();

			// ascending order
			return title1.compareTo(title2);

		}
	};

	public static Comparator<InventoryBookModel> authorComparator = new Comparator<InventoryBookModel>() {

		public int compare(InventoryBookModel s1, InventoryBookModel s2) {
			String author1 = s1.getAuthor().toUpperCase();
			String author2 = s2.getAuthor().toUpperCase();

			// ascending order
			return author1.compareTo(author2);

		}
	};

	public static Comparator<InventoryBookModel> publicationComparator = new Comparator<InventoryBookModel>() {

		public int compare(InventoryBookModel s1, InventoryBookModel s2) {
			String publication1 = s1.getPublication().toUpperCase();
			String publication2 = s2.getPublication().toUpperCase();

			// ascending order
			return publication1.compareTo(publication2);

		}
	};

	public static Comparator<InventoryBookModel> subjectComparator = new Comparator<InventoryBookModel>() {

		public int compare(InventoryBookModel s1, InventoryBookModel s2) {
			String subject1 = s1.getSubject().toUpperCase();
			String subject2 = s2.getSubject().toUpperCase();

			// ascending order
			return subject1.compareTo(subject2);

		}
	};

	public static Comparator<InventoryBookModel> categoryComparator = new Comparator<InventoryBookModel>() {

		public int compare(InventoryBookModel s1, InventoryBookModel s2) {
			String category1 = s1.getCategory().toUpperCase();
			String category2 = s2.getCategory().toUpperCase();

			// ascending order
			return category1.compareTo(category2);

		}
	};

	public static Comparator<InventoryBookModel> keywordsComparator = new Comparator<InventoryBookModel>() {

		public int compare(InventoryBookModel s1, InventoryBookModel s2) {
			String keywords1 = s1.getTitle().toUpperCase();
			String keywords2 = s2.getTitle().toUpperCase();

			// ascending order
			return keywords1.compareTo(keywords2);

		}
	};
}
