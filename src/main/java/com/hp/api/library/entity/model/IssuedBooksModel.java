package com.hp.api.library.entity.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.api.library.persistence.mongo.jpa.model.IssuedBookDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssuedBooksModel {

	@JsonProperty("issued_book_models_active")
	private List<IssuedBookDocument> issuedBookModelsActive;

	@JsonProperty("issued_book_models_inactive")
	private List<IssuedBookDocument> issuedBookModelsInActive;
}
