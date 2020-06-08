package com.hp.api.library.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hp.api.framework.service.core.annotation.Timed;
import com.hp.api.framework.service.core.constant.AppConstant;
import com.hp.api.framework.service.core.exception.AppCommonException;
import com.hp.api.framework.service.core.service.BaseService;
import com.hp.api.framework.service.core.util.CommonUtil;
import com.hp.api.framework.service.core.util.MongoCommonUtil;
import com.hp.api.library.entity.model.LibraryUserModel;
import com.hp.api.library.entity.request.EmbeddedBookRequest;
import com.hp.api.library.entity.request.GetIssueBookRequest;
import com.hp.api.library.entity.request.IssueBookRequest;
import com.hp.api.library.entity.request.LibraryBookRequest;
import com.hp.api.library.entity.request.LibraryUserRequest;
import com.hp.api.library.entity.response.LibraryResponse;
import com.hp.api.library.persistence.mongo.jpa.model.EmbeddedBookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.LibraryBookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.LibraryIssuedBookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.LibraryUserDocument;
import com.hp.api.library.persistence.mongo.jpa.repository.IssuedBookDocumentRepository;
import com.hp.api.library.persistence.mongo.jpa.repository.LibraryBookDocumentRepository;
import com.hp.api.library.persistence.mongo.jpa.repository.LibraryUserDocumentRepository;
import com.hp.api.library.service.api.LibraryService;
import com.hp.entity.request.base.BaseRequest;
import com.hp.entity.response.base.ResponseStatus;

@Service
public class LibraryServiceImpl extends BaseService implements LibraryService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LibraryBookDocumentRepository libraryBookDocumentRepository;

	@Autowired
	IssuedBookDocumentRepository issuedBookDocumentRepository;

	@Autowired
	LibraryUserDocumentRepository libraryUserDocumentRepository;

	@Timed
	public LibraryResponse addBook(LibraryBookRequest libraryBookRequest, MultipartFile[] bookImage) {
		LibraryResponse libraryResponse = new LibraryResponse();

		try {
			LibraryBookDocument libraryBookDocument = prepareLibraryBookDocument(libraryBookRequest);
			libraryBookDocumentRepository.save(libraryBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);

		}
		return libraryResponse;
	}

	private LibraryBookDocument prepareLibraryBookDocument(LibraryBookRequest libraryBookRequest) {
		LibraryBookDocument libraryBookDocument = new LibraryBookDocument(libraryBookRequest.getTitle(),
				libraryBookRequest.getImage(), libraryBookRequest.getRemarks(), libraryBookRequest.getAuthor(),
				libraryBookRequest.getEmbeddedBooks().size(), libraryBookRequest.getPublication(),
				libraryBookRequest.getSubject(), libraryBookRequest.getCategory(), libraryBookRequest.getKeywords(),
				libraryBookRequest.getCost(), libraryBookRequest.getYearOfPublication(),
				libraryBookRequest.getEdition(), libraryBookRequest.getPurchasing_Date(), libraryBookRequest.getPages(),
				null, prepareEmbeddedBookDocuments(libraryBookRequest.getEmbeddedBooks()));
		libraryBookDocument.setCreatedAt(new Date());
		libraryBookDocument.setUpdatedAt(new Date());
		if (!CommonUtil.isEmpty(libraryBookRequest.getId())) {
			libraryBookDocument.setId(libraryBookRequest.getId());
		}
		return libraryBookDocument;
	}

	private List<EmbeddedBookDocument> prepareEmbeddedBookDocuments(List<EmbeddedBookRequest> embeddedBooks) {

		List<EmbeddedBookDocument> embeddedBookDocuments = new ArrayList<EmbeddedBookDocument>();
		embeddedBooks.parallelStream().forEach(embeddedBook -> {
			EmbeddedBookDocument embeddedBookDocument = new EmbeddedBookDocument(MongoCommonUtil.newObjectId(),
					embeddedBook.getShelfRackPosition(), embeddedBook.getDDC(), embeddedBook.getISBN(), false);
			embeddedBookDocuments.add(embeddedBookDocument);
		});
		return embeddedBookDocuments;
	}

	@Timed
	public LibraryResponse editBook(LibraryBookRequest libraryBookRequest, MultipartFile[] bookImage) {
		LibraryResponse libraryResponse = new LibraryResponse();

		try {
			LibraryBookDocument libraryBookDocument = prepareLibraryBookDocument(libraryBookRequest);
			libraryBookDocumentRepository.save(libraryBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);

		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse deleteBook(String bookId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryBookDocument libraryBookDocument = libraryBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(bookId));
			libraryBookDocumentRepository.save(libraryBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);

		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse getBook(String title, String author, String publication, String category, String keywords) {

		LibraryResponse libraryResponse = new LibraryResponse();
		try {

		} catch (Exception e) {
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse issueBook(IssueBookRequest issueBookRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		LibraryBookDocument libraryBookDocument = new LibraryBookDocument();
		try {
			String userId = issueBookRequest.getUserRequestIdentity().getId();
			LibraryUserDocument libraryUserDocument = libraryUserDocumentRepository.findByUserId(userId);
			if (libraryUserDocument == null) {
				throw new AppCommonException("User doesn't has access to library");
			}
			String bookIssuedId;
			if (!CommonUtil.isEmpty(issueBookRequest.getBookId())) {
				libraryBookDocument = libraryBookDocumentRepository
						.findById(MongoCommonUtil.convertStringToObjectId(issueBookRequest.getBookId()));

				LibraryIssuedBookDocument libraryIssuedBookDocument = prepareLibraryIssuedBookDocument(issueBookRequest,
						libraryBookDocument);
				issuedBookDocumentRepository.save(libraryIssuedBookDocument);
				bookIssuedId = libraryIssuedBookDocument.getId();
				List<String> activeIssuedBooks = new ArrayList<String>();
				if (!activeIssuedBooks.isEmpty()) {
					activeIssuedBooks = libraryUserDocument.getActiveIssuedBooks();
					activeIssuedBooks.add(bookIssuedId);
				} else {
					activeIssuedBooks.add(bookIssuedId);
				}
				libraryUserDocument.setActiveIssuedBooks(activeIssuedBooks);
			}
			libraryUserDocumentRepository.save(libraryUserDocument);
			libraryBookDocumentRepository.save(libraryBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {

			logger.error("error while issuing library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}

		return libraryResponse;
	}

	private LibraryIssuedBookDocument prepareLibraryIssuedBookDocument(IssueBookRequest issueBookRequest,
			LibraryBookDocument libraryBookDocument) throws AppCommonException {

		List<EmbeddedBookDocument> embeddedBookDocuments = libraryBookDocument.getEmbeddedBooks();

		EmbeddedBookDocument embeddedBookDocumentEmpty = new EmbeddedBookDocument();
		Boolean bookAvailable = false;
		for (EmbeddedBookDocument embeddedBookDocument : embeddedBookDocuments) {
			if (!embeddedBookDocument.getIsIssue()) {
				embeddedBookDocumentEmpty.setDDC(embeddedBookDocument.getDDC());
				embeddedBookDocumentEmpty.setId(embeddedBookDocument.getId());
				embeddedBookDocument.setIsIssue(true);
				embeddedBookDocumentEmpty.setShelfRackPosition(embeddedBookDocument.getShelfRackPosition());
				embeddedBookDocumentEmpty.setISBN(embeddedBookDocument.getISBN());
				bookAvailable = true;
				break;
			}
		}
		if (!bookAvailable) {
			throw new AppCommonException("all books already issued");

		}
		libraryBookDocument.setEmbeddedBooks(embeddedBookDocuments);

		LibraryIssuedBookDocument libraryIssuedBookDocument = new LibraryIssuedBookDocument(issueBookRequest.getType(),
				issueBookRequest.getPersonId(), null, issueBookRequest.getLastDate(), false, issueBookRequest.getFine(),
				issueBookRequest.getFineReason(), libraryBookDocument.getTitle(), libraryBookDocument.getRemarks(),
				libraryBookDocument.getAuthor(), libraryBookDocument.getPublication(), libraryBookDocument.getSubject(),
				embeddedBookDocumentEmpty.getShelfRackPosition(), libraryBookDocument.getCategory(),
				libraryBookDocument.getKeywords(), embeddedBookDocumentEmpty.getId(), libraryBookDocument.getCost(),
				libraryBookDocument.getYearOfPublication(), libraryBookDocument.getEdition(),
				libraryBookDocument.getPurchasing_Date(), libraryBookDocument.getPages(),
				embeddedBookDocumentEmpty.getDDC(), embeddedBookDocumentEmpty.getISBN());

		if (!CommonUtil.isEmpty(issueBookRequest.getId())) {
			LibraryIssuedBookDocument libraryIssuedBookDocumentOld = issuedBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(issueBookRequest.getId()));
			if (libraryIssuedBookDocumentOld != null) {
				libraryIssuedBookDocument.setId(issueBookRequest.getId());
			}
		}
		return libraryIssuedBookDocument;
	}

	@Timed
	public LibraryResponse editIssueBook(IssueBookRequest issueBookRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			if (CommonUtil.isEmpty(issueBookRequest.getBookId())) {
				LibraryBookDocument libraryBookDocument = libraryBookDocumentRepository
						.findById(MongoCommonUtil.convertStringToObjectId(issueBookRequest.getBookId()));

				LibraryIssuedBookDocument libraryIssuedBookDocument = prepareLibraryIssuedBookDocument(issueBookRequest,
						libraryBookDocument);

				issuedBookDocumentRepository.save(libraryIssuedBookDocument);

			}
			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);
		}

		return libraryResponse;
	}

	@Timed
	public LibraryResponse deleteIssuedBook(String issueBookId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryIssuedBookDocument libraryIssuedBookDocument = issuedBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(issueBookId));
			if (libraryIssuedBookDocument != null) {
				libraryIssuedBookDocument.setDeletedAt(new Date());
				issuedBookDocumentRepository.save(libraryIssuedBookDocument);
			}
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse returnIssuedBook(String issueBookId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryIssuedBookDocument libraryIssuedBookDocument = issuedBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(issueBookId));
			if (libraryIssuedBookDocument != null) {
				libraryIssuedBookDocument.setIsReturn(true);
			} else {
				throw new AppCommonException("no record found for given book");
			}
			LibraryUserDocument libraryUserDocument = libraryUserDocumentRepository
					.findByUserId(libraryIssuedBookDocument.getPersonId());
			if (libraryUserDocument == null) {
				throw new AppCommonException("wrong data saved in collection regarding this book issue");
			}
			List<String> activeIssueBooks = libraryUserDocument.getActiveIssuedBooks();
			List<String> inActiveIssueBooks = new ArrayList<String>();
			if ((libraryUserDocument.getInActiveIssuedBooks() != null)
					&& !(libraryUserDocument.getInActiveIssuedBooks().isEmpty())
					&& libraryUserDocument.getInActiveIssuedBooks().contains(issueBookId)) {
				throw new AppCommonException("this book already returned");
			} else if ((libraryUserDocument.getInActiveIssuedBooks() != null)
					&& (!(libraryUserDocument.getInActiveIssuedBooks().isEmpty()))) {
				inActiveIssueBooks.addAll(libraryUserDocument.getInActiveIssuedBooks());
			}

			List<String> activeIssueBooksNew = new ArrayList<String>();
			if (activeIssueBooks.contains(issueBookId)) {
				activeIssueBooks.parallelStream().forEach(activeIssueBook -> {
					if (!activeIssueBook.equals(issueBookId)) {
						activeIssueBooksNew.add(activeIssueBook);
					}
				});
			} else {
				throw new AppCommonException("there is no such book in user's account");
			}
			inActiveIssueBooks.add(issueBookId);
			libraryUserDocument.setActiveIssuedBooks(activeIssueBooksNew);
			libraryUserDocument.setInActiveIssuedBooks(inActiveIssueBooks);
			issuedBookDocumentRepository.save(libraryIssuedBookDocument);
			libraryUserDocumentRepository.save(libraryUserDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while returning issued library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse getissueBookList(GetIssueBookRequest getissueBookRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {

			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);

		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse addLibraryUser(LibraryUserRequest libraryUserRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryUserDocument libraryUserDocument = prepareLibraryUserDocument(libraryUserRequest);
			libraryUserDocumentRepository.save(libraryUserDocument);

			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("error while registering library user", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());

		}
		return libraryResponse;
	}

	private LibraryUserDocument prepareLibraryUserDocument(LibraryUserRequest libraryUserRequest) {

		LibraryUserDocument libraryUserDocument = new LibraryUserDocument(libraryUserRequest.getType(),
				libraryUserRequest.getUserId(), libraryUserRequest.getName(), libraryUserRequest.getImage(), null,
				libraryUserRequest.getMaxBookAllow(), null, null);
		if (!CommonUtil.isEmpty(libraryUserRequest.getId())) {
			LibraryUserDocument libraryUserDocumentOld = libraryUserDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(libraryUserRequest.getId()));
			if (!(libraryUserDocumentOld.getActiveIssuedBooks().isEmpty())
					&& libraryUserDocumentOld.getActiveIssuedBooks() != null) {
				libraryUserDocument.setActiveIssuedBooks(libraryUserDocumentOld.getActiveIssuedBooks());
			}
			if (!(libraryUserDocumentOld.getInActiveIssuedBooks().isEmpty())
					&& libraryUserDocumentOld.getInActiveIssuedBooks() != null) {
				libraryUserDocument.setInActiveIssuedBooks(libraryUserDocumentOld.getInActiveIssuedBooks());
			}
			libraryUserDocument.setId(libraryUserRequest.getId());
		}

		return libraryUserDocument;
	}

	@Timed
	public LibraryResponse editLibraryUser(LibraryUserRequest libraryUserRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryUserDocument libraryUserDocument = prepareLibraryUserDocument(libraryUserRequest);
			libraryUserDocumentRepository.save(libraryUserDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse getLibraryUser(String userId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryUserDocument libraryUserDocument = libraryUserDocumentRepository.findByUserId(userId);
			if (libraryUserDocument != null) {
				LibraryUserModel libraryUserModel = new LibraryUserModel(libraryUserDocument.getId(),
						libraryUserDocument.getUserId(), libraryUserDocument.getName(), libraryUserDocument.getImage(),
						libraryUserDocument.getType(), libraryUserDocument.getMaxAllowedBook());
				libraryResponse.setLibraryUserModel(libraryUserModel);
			}
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);

		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse deleteLibraryUser(String libraryUserId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			LibraryUserDocument libraryUserDocumentOld = libraryUserDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(libraryUserId));

			if (libraryUserDocumentOld != null) {
				libraryUserDocumentOld.setDeletedAt(new Date());
				libraryUserDocumentRepository.save(libraryUserDocumentOld);
			} else {

			}
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			libraryResponse.setStatus(ResponseStatus.FAILED);

		}
		return libraryResponse;
	}

}
