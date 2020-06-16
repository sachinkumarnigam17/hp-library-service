package com.hp.api.library.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.hp.api.framework.service.core.controller.AppController;
import com.hp.api.library.entity.request.AddBookRequest;
import com.hp.api.library.entity.request.EditBookRequest;
import com.hp.api.library.entity.request.GetInventoryBookRequest;
import com.hp.api.library.entity.request.InventorySortingRequest;
import com.hp.api.library.entity.request.IssueBookRequest;
import com.hp.api.library.entity.request.LibraryPolicyRequest;
import com.hp.api.library.entity.response.LibraryResponse;
import com.hp.api.library.service.api.LibraryService;
import com.hp.entity.request.base.BaseRequest;

@RestController
public class LibraryController extends AppController {

	@Autowired
	private LibraryService libraryService;

	@RequestMapping(value = "/add_book", method = RequestMethod.POST)
	public LibraryResponse addBook(@RequestPart("addBookRequest") @Valid AddBookRequest addBookRequest) {
		addBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.addBook(addBookRequest);
	}

	@RequestMapping(value = "/edit_book", method = RequestMethod.POST)
	public LibraryResponse editBook(@RequestBody @Valid EditBookRequest editBookRequest) {
		editBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.editBook(editBookRequest);
	}

	@RequestMapping(value = "/get_book", method = RequestMethod.POST)
	public LibraryResponse getBook(@RequestBody @Valid GetInventoryBookRequest getInventoryBookRequest) {
		getInventoryBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getBook(getInventoryBookRequest);
	}

	@RequestMapping(value = "/delete_book", method = RequestMethod.POST)
	public LibraryResponse deleteBook(@RequestParam("bookId") @Valid String bookId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.deleteBook(bookId, baseRequest);
	}

	@RequestMapping(value = "/add_library_policy", method = RequestMethod.POST)
	public LibraryResponse addLibraryPolicy(@RequestBody @Valid LibraryPolicyRequest libraryPolicyRequest) {
		libraryPolicyRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.addLibraryPolicy(libraryPolicyRequest);
	}

	@RequestMapping(value = "/edit_library_policy", method = RequestMethod.POST)
	public LibraryResponse editLibraryPolicy(@RequestBody @Valid LibraryPolicyRequest libraryPolicyRequest) {
		libraryPolicyRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.editLibraryPolicy(libraryPolicyRequest);
	}

	@RequestMapping(value = "/get_library_policy", method = RequestMethod.POST)
	public LibraryResponse getLibraryPolicy() {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getLibraryPolicy(baseRequest);
	}

	@RequestMapping(value = "/delete_library_policy", method = RequestMethod.POST)
	public LibraryResponse deleteLibraryPolicy() {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.deleteLibraryPolicy(baseRequest);
	}

	@RequestMapping(value = "/barcode_scan", method = RequestMethod.POST)
	public LibraryResponse barcodeScan(@RequestParam("iSBN") @Valid String iSBN) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.barcodeScan(iSBN);
	}

	@RequestMapping(value = "/issue_book", method = RequestMethod.POST)
	public LibraryResponse issueBook(@RequestBody @Valid IssueBookRequest issueBookRequest) {
		issueBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.issueBook(issueBookRequest);
	}

	@RequestMapping(value = "/delete_issue_book", method = RequestMethod.POST)
	public LibraryResponse deleteIssuedBook(@RequestParam("issueBookId") @Valid String issueBookId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.deleteIssuedBook(issueBookId, baseRequest);
	}

	@RequestMapping(value = "/return_issue_book", method = RequestMethod.POST)
	public LibraryResponse returnIssuedBook(@RequestParam("inventoryId") @Valid String inventoryId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.returnIssuedBook(inventoryId, baseRequest);
	}

	@RequestMapping(value = "/get_issued_book_list", method = RequestMethod.POST)
	public LibraryResponse getissueBookList() {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getissueBookList(baseRequest);
	}

	@RequestMapping(value = "/get_user_issued_book_list", method = RequestMethod.POST)
	public LibraryResponse getUserIssuedBookList(@RequestParam("userId") @Valid String userId,
			@RequestParam("IsUser") @Valid Boolean IsUser) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getUserIssuedBookList(userId, IsUser, baseRequest);
	}

	@RequestMapping(value = "/sort_inventory_models", method = RequestMethod.POST)
	public LibraryResponse sortInventoryModels(@RequestBody @Valid InventorySortingRequest inventorySortingRequest) {
		inventorySortingRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.sortInventoryModels(inventorySortingRequest);
	}

}
