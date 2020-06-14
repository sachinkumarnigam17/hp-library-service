package com.hp.api.library.service.api;

import org.springframework.web.multipart.MultipartFile;

import com.hp.api.library.entity.request.GetInventoryBookRequest;
import com.hp.api.library.entity.request.InventoryBookRequest;
import com.hp.api.library.entity.request.InventorySortingRequest;
import com.hp.api.library.entity.request.IssueBookRequest;
import com.hp.api.library.entity.request.LibraryBookRequest;
import com.hp.api.library.entity.request.LibraryPolicyRequest;
import com.hp.api.library.entity.response.LibraryResponse;
import com.hp.entity.request.base.BaseRequest;

public interface LibraryService {

	LibraryResponse addBook(LibraryBookRequest libraryBookRequest, MultipartFile[] bookImage);

	LibraryResponse editBook(InventoryBookRequest inventoryBookRequest, MultipartFile[] bookImage);

	LibraryResponse deleteBook(String bookId, BaseRequest baseRequest);

	LibraryResponse issueBook(IssueBookRequest issueBookRequest);

	// LibraryResponse editIssueBook(IssueBookRequest issueBookRequest);

	LibraryResponse getissueBookList(BaseRequest baseRequest);

	LibraryResponse deleteIssuedBook(String issueBookId, BaseRequest baseRequest);

	LibraryResponse returnIssuedBook(String inventoryId, BaseRequest baseRequest);

	LibraryResponse addLibraryPolicy(LibraryPolicyRequest libraryPolicyRequest);

	LibraryResponse editLibraryPolicy(LibraryPolicyRequest libraryPolicyRequest);

	LibraryResponse getLibraryPolicy(BaseRequest baseRequest);

	LibraryResponse deleteLibraryPolicy(BaseRequest baseRequest);

	LibraryResponse barcodeScan(String iSBN);

	LibraryResponse getBook(GetInventoryBookRequest getInventoryBookRequest);

	LibraryResponse getUserIssuedBookList(String userId, Boolean IsUser, BaseRequest baseRequest);

	LibraryResponse sortInventoryModels(InventorySortingRequest inventorySortingRequest);

}
