package com.hp.api.library.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hp.api.framework.service.core.controller.AppController;
import com.hp.api.library.entity.request.GetIssueBookRequest;
import com.hp.api.library.entity.request.IssueBookRequest;
import com.hp.api.library.entity.request.LibraryBookRequest;
import com.hp.api.library.entity.request.LibraryUserRequest;
import com.hp.api.library.entity.response.LibraryResponse;
import com.hp.api.library.service.api.LibraryService;
import com.hp.entity.request.base.BaseRequest;

@RestController
public class LibraryController extends AppController {

	@Autowired
	private LibraryService libraryService;

	@RequestMapping(value = "/add_book", method = RequestMethod.POST)
	public LibraryResponse addBook(@RequestPart("libraryBookRequest") @Valid LibraryBookRequest libraryBookRequest,
			@RequestParam("bookImage") MultipartFile[] bookImage) {
		libraryBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.addBook(libraryBookRequest, bookImage);
	}

	@RequestMapping(value = "/edit_book", method = RequestMethod.POST)
	public LibraryResponse editBook(@RequestPart("libraryBookRequest") @Valid LibraryBookRequest libraryBookRequest,
			@RequestParam("bookImage") MultipartFile[] bookImage) {
		libraryBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.editBook(libraryBookRequest, bookImage);
	}

	@RequestMapping(value = "/get_book", method = RequestMethod.POST)
	public LibraryResponse getBook(@RequestParam("title") @Valid String title,
			@RequestParam("author") @Valid String author, @RequestParam("publication") @Valid String publication,
			@RequestParam("category") @Valid String category, @RequestParam("keywords") @Valid String keywords) {
		BaseRequest request = new BaseRequest();
		request.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getBook(title, author, publication, category, keywords);
	}

	@RequestMapping(value = "/delete_book", method = RequestMethod.POST)
	public LibraryResponse deleteBook(@RequestParam("bookId") @Valid String bookId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.deleteBook(bookId, baseRequest);
	}

	@RequestMapping(value = "/register_user", method = RequestMethod.POST)
	public LibraryResponse addLibraryUser(@RequestBody @Valid LibraryUserRequest registerUserRequest) {
		registerUserRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.addLibraryUser(registerUserRequest);
	}

	@RequestMapping(value = "/edit_register_user", method = RequestMethod.POST)
	public LibraryResponse editLibraryUser(@RequestBody @Valid LibraryUserRequest registerUserRequest) {
		registerUserRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.editLibraryUser(registerUserRequest);
	}

	@RequestMapping(value = "/get_register_user", method = RequestMethod.POST)
	public LibraryResponse getLibraryUser(@RequestParam("userId") @Valid String userId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getLibraryUser(userId, baseRequest);
	}

	@RequestMapping(value = "/delete_library_user", method = RequestMethod.POST)
	public LibraryResponse deleteLibraryUser(@RequestParam("libraryUserId") @Valid String libraryUserId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.deleteLibraryUser(libraryUserId, baseRequest);
	}

	@RequestMapping(value = "/issue_book", method = RequestMethod.POST)
	public LibraryResponse issueBook(@RequestBody @Valid IssueBookRequest issueBookRequest) {
		issueBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.issueBook(issueBookRequest);
	}

	@RequestMapping(value = "/edit_issue_book", method = RequestMethod.POST)
	public LibraryResponse editIssueBook(@RequestBody @Valid IssueBookRequest issueBookRequest) {
		issueBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.editIssueBook(issueBookRequest);
	}

	@RequestMapping(value = "/delete_issue_book", method = RequestMethod.POST)
	public LibraryResponse deleteIssuedBook(@RequestParam("issueBookId") @Valid String issueBookId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.deleteIssuedBook(issueBookId, baseRequest);
	}

	@RequestMapping(value = "/return_issue_book", method = RequestMethod.POST)
	public LibraryResponse returnIssuedBook(@RequestParam("issueBookId") @Valid String issueBookId) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.returnIssuedBook(issueBookId, baseRequest);
	}

	@RequestMapping(value = "/get_issued_book_list", method = RequestMethod.POST)
	public LibraryResponse getissueBookList(@RequestBody @Valid GetIssueBookRequest getissueBookRequest) {
		getissueBookRequest.setUserRequestIdentity(userDetailsService.getUserRequestIdentityFromSecurity());
		return libraryService.getissueBookList(getissueBookRequest);
	}

}
