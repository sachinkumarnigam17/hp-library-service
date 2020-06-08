package com.hp.api.library.service.api;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.hp.api.library.entity.request.GetIssueBookRequest;
import com.hp.api.library.entity.request.IssueBookRequest;
import com.hp.api.library.entity.request.LibraryBookRequest;
import com.hp.api.library.entity.request.LibraryUserRequest;
import com.hp.api.library.entity.response.LibraryResponse;
import com.hp.entity.request.base.BaseRequest;

public interface LibraryService {

	LibraryResponse addBook(LibraryBookRequest libraryBookRequest, MultipartFile[] bookImage);

	LibraryResponse editBook(LibraryBookRequest libraryBookRequest, MultipartFile[] bookImage);

	LibraryResponse deleteBook(String bookId, BaseRequest baseRequest);

	LibraryResponse getBook(String title, String author, String publication, String category, String keywords);

	LibraryResponse issueBook(IssueBookRequest issueBookRequest);

	LibraryResponse editIssueBook(IssueBookRequest issueBookRequest);

	LibraryResponse getissueBookList(GetIssueBookRequest getissueBookRequest);

	LibraryResponse addLibraryUser(LibraryUserRequest registerUserRequest);

	LibraryResponse editLibraryUser(LibraryUserRequest registerUserRequest);

	LibraryResponse getLibraryUser(String userId, BaseRequest baseRequest);

	LibraryResponse deleteLibraryUser(String libraryUserId, BaseRequest baseRequest);

	LibraryResponse deleteIssuedBook(@Valid String issueBookId, BaseRequest baseRequest);

	LibraryResponse returnIssuedBook(@Valid String issueBookId, BaseRequest baseRequest);

}
