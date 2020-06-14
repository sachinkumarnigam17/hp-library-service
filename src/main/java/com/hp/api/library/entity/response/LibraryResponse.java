package com.hp.api.library.entity.response;

import java.util.List;

import com.hp.api.library.entity.model.InventoryBookModel;
import com.hp.api.library.entity.model.IssuedBooksModel;
import com.hp.api.library.entity.model.LibraryUserModel;
import com.hp.api.library.entity.model.ReturnBookModel;
import com.hp.api.library.entity.model.SchoolLibraryPolicyModel;
import com.hp.api.library.persistence.mongo.jpa.model.InventoryBookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.IssuedBookDocument;
import com.hp.entity.response.base.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse extends BaseResponse {

	LibraryUserModel libraryUserModel;

	SchoolLibraryPolicyModel schoolLibraryPolicyModel;

	InventoryBookDocument inventoryBookDocument;

	List<InventoryBookDocument> inventoryBookDocuments;

	List<IssuedBookDocument> issuedBookDocuments;

	IssuedBooksModel issuedBooksModel;

	List<InventoryBookModel> inventoryBookModels;

	ReturnBookModel returnBookModel;

}
