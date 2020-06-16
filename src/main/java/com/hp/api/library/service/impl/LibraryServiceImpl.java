package com.hp.api.library.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hp.api.framework.service.core.annotation.Timed;
import com.hp.api.framework.service.core.constant.AppConstant;
import com.hp.api.framework.service.core.exception.AppCommonException;
import com.hp.api.framework.service.core.service.BaseService;
import com.hp.api.framework.service.core.util.CommonUtil;
import com.hp.api.framework.service.core.util.MongoCommonUtil;
import com.hp.api.library.entity.model.InventoryBookModel;
import com.hp.api.library.entity.model.IssuedBooksModel;
import com.hp.api.library.entity.model.ReturnBookModel;
import com.hp.api.library.entity.model.SchoolLibraryPolicyModel;
import com.hp.api.library.entity.request.AddBookRequest;
import com.hp.api.library.entity.request.EditBookRequest;
import com.hp.api.library.entity.request.EmbeddedBookRequest;
import com.hp.api.library.entity.request.GetInventoryBookRequest;
import com.hp.api.library.entity.request.InventorySortingRequest;
import com.hp.api.library.entity.request.IssueBookRequest;
import com.hp.api.library.entity.request.LibraryPolicyRequest;
import com.hp.api.library.entity.response.LibraryResponse;
import com.hp.api.library.persistence.mongo.jpa.model.BookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.InventoryBookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.IssuedBookDocument;
import com.hp.api.library.persistence.mongo.jpa.model.SchoolLibraryPolicyDocument;
import com.hp.api.library.persistence.mongo.jpa.repository.BookDocumentRepository;
import com.hp.api.library.persistence.mongo.jpa.repository.InventoryBookDocumentRepository;
import com.hp.api.library.persistence.mongo.jpa.repository.IssuedBookDocumentRepository;
import com.hp.api.library.persistence.mongo.jpa.repository.SchoolLibraryPolicyDocumentRepository;
import com.hp.api.library.service.api.LibraryService;
import com.hp.api.library.service.dao.InventoryBookDocumentDAO;
import com.hp.entity.request.base.BaseRequest;
import com.hp.entity.response.base.ResponseStatus;

@Service
public class LibraryServiceImpl extends BaseService implements LibraryService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InventoryBookDocumentDAO inventoryBookDocumentDAO;

	@Autowired
	BookDocumentRepository bookDocumentRepository;

	@Autowired
	IssuedBookDocumentRepository issuedBookDocumentRepository;

	@Autowired
	InventoryBookDocumentRepository inventoryBookDocumentRepository;

	@Autowired
	SchoolLibraryPolicyDocumentRepository schoolLibraryPolicyDocumentRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Timed
	public LibraryResponse addBook(AddBookRequest addBookRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();

		try {
			List<String> iSBNs = new ArrayList<String>();
			List<EmbeddedBookRequest> embeddedBookRequests = addBookRequest.getEmbeddedBooks();
			embeddedBookRequests.parallelStream().forEach(embeddedBook -> {
				InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository
						.findByISBN(embeddedBook.getISBN());
				if (inventoryBookDocument != null) {
					iSBNs.add(inventoryBookDocument.getISBN());
				}
			});
			if (iSBNs != null && !(iSBNs.isEmpty())) {
				throw new AppCommonException("these ISBN books already exists" + iSBNs);
			}
			List<InventoryBookDocument> inventoryBookDocuments = new ArrayList<InventoryBookDocument>();
			Query query = new Query();
			query.addCriteria(Criteria.where("SchoolId").is(addBookRequest.getUserRequestIdentity().getId()));
			query.addCriteria(Criteria.where("DeletedAt").is(null));
			query.addCriteria(Criteria.where("BookDocument.Title").is(addBookRequest.getTitle()));
			query.addCriteria(Criteria.where("BookDocument.Author").is(addBookRequest.getAuthor()));
			query.addCriteria(Criteria.where("BookDocument.Publication").is(addBookRequest.getPublication()));
			query.addCriteria(Criteria.where("BookDocument.Subject").is(addBookRequest.getSubject()));
			List<InventoryBookDocument> inventoryBookDocumentsOld = mongoTemplate.find(query,
					InventoryBookDocument.class);
			if (!CommonUtil.isEmpty(inventoryBookDocumentsOld)) {

				inventoryBookDocuments = prepareInventoryBookDocument(addBookRequest,
						inventoryBookDocumentsOld.get(0).getBookDocument());
			} else {
				BookDocument bookDocument = new BookDocument(MongoCommonUtil.newObjectIdString(),
						addBookRequest.getTitle(), addBookRequest.getRemarks(), addBookRequest.getAuthor(),
						addBookRequest.getPublication(), addBookRequest.getSubject(), addBookRequest.getCategory(),
						addBookRequest.getKeywords());
				inventoryBookDocuments = prepareInventoryBookDocument(addBookRequest, bookDocument);
			}
			inventoryBookDocumentRepository.saveAll(inventoryBookDocuments);

			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while adding library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	private List<InventoryBookDocument> prepareInventoryBookDocument(AddBookRequest addBookRequest,
			BookDocument bookDocument) {

		List<InventoryBookDocument> inventoryBookDocuments = new ArrayList<InventoryBookDocument>();

		for (EmbeddedBookRequest embeddedBookRequest : addBookRequest.getEmbeddedBooks()) {

			InventoryBookDocument inventoryBookDocument = new InventoryBookDocument(
					embeddedBookRequest.getShelfRackPosition(), addBookRequest.getCost(),
					addBookRequest.getYearOfPublication(), addBookRequest.getEdition(),
					addBookRequest.getPurchasing_Date(), addBookRequest.getPages(), null, false,
					embeddedBookRequest.getDDC(), embeddedBookRequest.getISBN(), false,
					addBookRequest.getUserRequestIdentity().getSchoolId(), bookDocument);
			inventoryBookDocument.setCreatedAt(new Date());
			inventoryBookDocument.setUpdatedAt(new Date());
			inventoryBookDocuments.add(inventoryBookDocument);
		}

		return inventoryBookDocuments;
	}

	@Timed
	public LibraryResponse editBook(EditBookRequest editBookRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			InventoryBookDocument inventoryBookDocument = new InventoryBookDocument();
			List<InventoryBookDocument> inventoryBookDocumentsSE = new ArrayList<InventoryBookDocument>();
			if (CommonUtil.isEmpty(editBookRequest.getEditionOld())) {
				inventoryBookDocument = prepareInventoryBookDocument(editBookRequest);
				inventoryBookDocumentRepository.save(inventoryBookDocument);
			} else {
				inventoryBookDocumentsSE = prepareInventoryBookDocumentListOfSameEdition(editBookRequest);
				inventoryBookDocumentRepository.saveAll(inventoryBookDocumentsSE);
			}
			List<InventoryBookDocument> inventoryBookDocuments = updateBookDocument(editBookRequest);
			inventoryBookDocumentRepository.saveAll(inventoryBookDocuments);

			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while editing library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	private List<InventoryBookDocument> updateBookDocument(EditBookRequest editBookRequest) {
		Query query = new Query();
		query.addCriteria(Criteria.where("SchoolId").is(editBookRequest.getUserRequestIdentity().getSchoolId()));
		query.addCriteria(Criteria.where("DeletedAt").is(null));
		query.addCriteria(Criteria.where("BookDocument.Id").is(editBookRequest.getBookRequest().getId()));
		List<InventoryBookDocument> inventoryBookDocuments = mongoTemplate.find(query, InventoryBookDocument.class);
		BookDocument bookDocument = new BookDocument(editBookRequest.getBookRequest().getId(),
				editBookRequest.getBookRequest().getTitle(), editBookRequest.getBookRequest().getRemarks(),
				editBookRequest.getBookRequest().getAuthor(), editBookRequest.getBookRequest().getPublication(),
				editBookRequest.getBookRequest().getSubject(), editBookRequest.getBookRequest().getCategory(),
				editBookRequest.getBookRequest().getKeywords());
		if (!CommonUtil.isEmpty(inventoryBookDocuments)) {
			inventoryBookDocuments.parallelStream().forEach(inventoryBookDocument -> {
				inventoryBookDocument.setBookDocument(bookDocument);
			});
		}
		return inventoryBookDocuments;
	}

	private List<InventoryBookDocument> prepareInventoryBookDocumentListOfSameEdition(EditBookRequest editBookRequest)
			throws AppCommonException {
		Query query = new Query();
		query.addCriteria(Criteria.where("SchoolId").is(editBookRequest.getUserRequestIdentity().getSchoolId()));
		query.addCriteria(Criteria.where("DeletedAt").is(null));
		query.addCriteria(Criteria.where("Edition").is(editBookRequest.getEditionOld()));
		query.addCriteria(Criteria.where("BookDocument.Id").is(editBookRequest.getBookRequest().getId()));
		List<InventoryBookDocument> inventoryBookDocuments = mongoTemplate.find(query, InventoryBookDocument.class);
		if (CommonUtil.isEmpty(inventoryBookDocuments)) {
			throw new AppCommonException("provide valid book document Id");
		}
		List<InventoryBookDocument> inventoryBookDocumentList = new ArrayList<InventoryBookDocument>();
		if (!CommonUtil.isEmpty(inventoryBookDocuments)) {
			inventoryBookDocuments.parallelStream().forEach(inventoryBookDocument -> {
				if (inventoryBookDocument.getId().equals(editBookRequest.getId())) {
					inventoryBookDocument.setDDC(editBookRequest.getDDC());
					inventoryBookDocument.setISBN(editBookRequest.getISBN());
					if (!CommonUtil.isEmpty(editBookRequest.getShelfRackPosition())) {
						inventoryBookDocument.setShelfRackPosition(editBookRequest.getShelfRackPosition());
					}
				}
				InventoryBookDocument inventoryBookDocumentUpdated = new InventoryBookDocument(
						inventoryBookDocument.getShelfRackPosition(), editBookRequest.getCost(),
						editBookRequest.getYearOfPublication(), editBookRequest.getEdition(),
						editBookRequest.getPurchasing_Date(), editBookRequest.getPages(), null, false,
						inventoryBookDocument.getDDC(), inventoryBookDocument.getISBN(),
						inventoryBookDocument.getIsIssue(), editBookRequest.getUserRequestIdentity().getSchoolId(),
						inventoryBookDocument.getBookDocument());
				inventoryBookDocumentUpdated.setId(inventoryBookDocument.getId());

				inventoryBookDocumentList.add(inventoryBookDocumentUpdated);
			});

		}
		return inventoryBookDocumentList;
	}

	private InventoryBookDocument prepareInventoryBookDocument(EditBookRequest editBookRequest)
			throws AppCommonException {
		InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository
				.findById(MongoCommonUtil.convertStringToObjectId(editBookRequest.getId()));
		if (inventoryBookDocument == null) {
			throw new AppCommonException("provide valid book id");
		}
		InventoryBookDocument inventoryBookDocumentUpdated = new InventoryBookDocument(
				editBookRequest.getShelfRackPosition(), editBookRequest.getCost(),
				editBookRequest.getYearOfPublication(), editBookRequest.getEdition(),
				editBookRequest.getPurchasing_Date(), editBookRequest.getPages(), null, false, editBookRequest.getDDC(),
				editBookRequest.getISBN(), inventoryBookDocument.getIsIssue(),
				editBookRequest.getUserRequestIdentity().getSchoolId(), inventoryBookDocument.getBookDocument());
		inventoryBookDocumentUpdated.setId(inventoryBookDocument.getId());
		return inventoryBookDocumentUpdated;
	}

	@Timed
	public LibraryResponse deleteBook(String bookId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(bookId));
			if (inventoryBookDocument == null) {
				throw new AppCommonException("provide valid id");
			}
			inventoryBookDocument.setDeletedAt(new Date());
			inventoryBookDocument.setIsDelete(true);
			inventoryBookDocumentRepository.save(inventoryBookDocument);

			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while deleting library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse getBook(GetInventoryBookRequest getInventoryBookRequest) {

		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("DeletedAt").is(null));
			query.addCriteria(
					Criteria.where("SchoolId").is(getInventoryBookRequest.getUserRequestIdentity().getSchoolId()));

			if (!CommonUtil.isEmpty(getInventoryBookRequest.getTitle())) {
				query.addCriteria(Criteria.where("BookDocument.Title").is(getInventoryBookRequest.getTitle()));
			}
			if (!CommonUtil.isEmpty(getInventoryBookRequest.getAuthor())) {
				query.addCriteria(Criteria.where("BookDocument.Author").is(getInventoryBookRequest.getAuthor()));
			}

			if (!CommonUtil.isEmpty(getInventoryBookRequest.getCategory())) {
				query.addCriteria(Criteria.where("BookDocument.Category").is(getInventoryBookRequest.getCategory()));

			}
			if (!CommonUtil.isEmpty(getInventoryBookRequest.getKeywords())) {
				query.addCriteria(Criteria.where("BookDocument.Keywords").is(getInventoryBookRequest.getKeywords()));
			}
			if (!CommonUtil.isEmpty(getInventoryBookRequest.getPublication())) {
				query.addCriteria(
						Criteria.where("BookDocument.Publication").is(getInventoryBookRequest.getPublication()));
			}
			if (!CommonUtil.isEmpty(getInventoryBookRequest.getSubject())) {
				query.addCriteria(Criteria.where("BookDocument.Subject").is(getInventoryBookRequest.getSubject()));
			}
			List<InventoryBookDocument> inventoryBookDocuments = mongoTemplate.find(query, InventoryBookDocument.class);
			libraryResponse.setInventoryBookDocuments(inventoryBookDocuments);

			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {

			logger.error("error while getting library book details", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse barcodeScan(String iSBN) {

		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository.findByISBN(iSBN);
			if(inventoryBookDocument==null) {
				throw new AppCommonException("provide valid ISBN");
			}
			libraryResponse.setInventoryBookDocument(inventoryBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {

			logger.error("error while getting library book details", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse issueBook(IssueBookRequest issueBookRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			issueBookRequest = applySchoolLibraryPolicy(issueBookRequest);
			validateIssueBookRequest(issueBookRequest);

			InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(issueBookRequest.getInventoryBookId()));
			if (inventoryBookDocument != null) {
				if (inventoryBookDocument.getIsIssue()) {
					throw new AppCommonException("book is already issued");
				} else
					inventoryBookDocument.setIsIssue(true);
			} else {
				throw new AppCommonException("there is no such book");
			}
			IssuedBookDocument issuedBookDocument = preapreIssuedBookDocument(issueBookRequest);
			issuedBookDocument.setCreatedAt(new Date());
			inventoryBookDocumentRepository.save(inventoryBookDocument);
			issuedBookDocumentRepository.save(issuedBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {

			logger.error("error while issuing library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}

		return libraryResponse;
	}

	private void validateIssueBookRequest(IssueBookRequest issueBookRequest) throws AppCommonException {
		if ((CommonUtil.isEmpty(issueBookRequest.getUserId()) && CommonUtil.isEmpty(issueBookRequest.getStudentId()))
				|| ((!CommonUtil.isEmpty(issueBookRequest.getUserId()))
						&& (!CommonUtil.isEmpty(issueBookRequest.getStudentId())))) {
			throw new AppCommonException("provide either user Id or Student Id");
		}

		else if (!CommonUtil.isEmpty(issueBookRequest.getUserId())) {
			List<IssuedBookDocument> issuedBookDocuments = issuedBookDocumentRepository
					.findByUserIdAndDeletedAtNull(issueBookRequest.getUserId());
			if (issuedBookDocuments.size() == issueBookRequest.getMaxAllowedBook()) {
				throw new AppCommonException("already reached max limit");
			}

		} else {
			List<IssuedBookDocument> issuedBookDocuments = issuedBookDocumentRepository
					.findByStudentIdAndDeletedAtNull(issueBookRequest.getStudentId());
			if (issuedBookDocuments.size() == issueBookRequest.getMaxAllowedBook()) {
				throw new AppCommonException("already reached max limit");
			}
		}
	}

	private IssueBookRequest applySchoolLibraryPolicy(IssueBookRequest issueBookRequest) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		SchoolLibraryPolicyDocument schoolLibraryPolicyDocument = schoolLibraryPolicyDocumentRepository
				.findBySchoolId(issueBookRequest.getUserRequestIdentity().getSchoolId());

		if (schoolLibraryPolicyDocument.getUserIds().contains(issueBookRequest.getUserId())) {
			issueBookRequest.setMaxAllowedBook(schoolLibraryPolicyDocument.getMaxAllowedBook().get(1));
			cal.add(Calendar.DATE, schoolLibraryPolicyDocument.getLastDateOfReturn().get(1));
			issueBookRequest.setFinePerDay(schoolLibraryPolicyDocument.getFinePerDay().get(1));
		} else if (schoolLibraryPolicyDocument.getClassIds().contains(issueBookRequest.getUserId())) {
			issueBookRequest.setMaxAllowedBook(schoolLibraryPolicyDocument.getMaxAllowedBook().get(2));
			cal.add(Calendar.DATE, schoolLibraryPolicyDocument.getLastDateOfReturn().get(2));
			issueBookRequest.setFinePerDay(schoolLibraryPolicyDocument.getFinePerDay().get(2));
		} else if (schoolLibraryPolicyDocument.getStudentIds().contains(issueBookRequest.getUserId())) {
			issueBookRequest.setMaxAllowedBook(schoolLibraryPolicyDocument.getMaxAllowedBook().get(3));
			cal.add(Calendar.DATE, schoolLibraryPolicyDocument.getLastDateOfReturn().get(3));
			issueBookRequest.setFinePerDay(schoolLibraryPolicyDocument.getFinePerDay().get(3));
		} else {
			issueBookRequest.setMaxAllowedBook(schoolLibraryPolicyDocument.getMaxAllowedBook().get(0));
			cal.add(Calendar.DATE, schoolLibraryPolicyDocument.getLastDateOfReturn().get(0));
			issueBookRequest.setFinePerDay(schoolLibraryPolicyDocument.getFinePerDay().get(0));
		}
		issueBookRequest.setLastDate(cal.getTime());
		return issueBookRequest;
	}

	private IssuedBookDocument preapreIssuedBookDocument(IssueBookRequest issueBookRequest) throws AppCommonException {

		IssuedBookDocument issuedBookDocument = new IssuedBookDocument();

		if (!CommonUtil.isEmpty(issueBookRequest.getUserId())) {

			issuedBookDocument = makeIssuedBookDocument(issueBookRequest);
			issuedBookDocument.setUserId(issueBookRequest.getUserId());

		} else {
			issuedBookDocument = makeIssuedBookDocument(issueBookRequest);
			issuedBookDocument.setStudentId(issueBookRequest.getStudentId());

		}

		return issuedBookDocument;
	}

	private IssuedBookDocument makeIssuedBookDocument(IssueBookRequest issueBookRequest) {

		IssuedBookDocument issuedBookDocument = new IssuedBookDocument(null, null,
				issueBookRequest.getInventoryBookId(), issueBookRequest.getLastDate(), false, 0,
				issueBookRequest.getFinePerDay(), null, issueBookRequest.getUserRequestIdentity().getSchoolId(), null,
				false);
		issuedBookDocument.setUpdatedAt(new Date());
		return issuedBookDocument;
	}

	@Timed
	public LibraryResponse deleteIssuedBook(String issueBookId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			IssuedBookDocument issuedBookDocument = issuedBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(issueBookId));
			issuedBookDocument.setDeletedAt(new Date());
			issuedBookDocument.setIsDelete(true);
			InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(issuedBookDocument.getInventoryId()));
			if (inventoryBookDocument.getIsIssue()) {
				inventoryBookDocument.setIsIssue(false);
			}
			inventoryBookDocumentRepository.save(inventoryBookDocument);
			issuedBookDocumentRepository.save(issuedBookDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while deleting library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse returnIssuedBook(String inventoryId, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			InventoryBookDocument inventoryBookDocument = inventoryBookDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(inventoryId));
			if (inventoryBookDocument == null) {
				throw new AppCommonException("there is no inventory for this given Id");

			}

			IssuedBookDocument issuedBookDocument = issuedBookDocumentRepository
					.findByInventoryIdAndDeletedAtNull(inventoryId);
			if (issuedBookDocument == null) {
				throw new AppCommonException("this book is not issued");
			}

			long diff = new Date().getTime() - issuedBookDocument.getLastDate().getTime();
			if (diff < 0) {
				diff = 0;
			} else {
				issuedBookDocument.setFineReason("late submission");
			}
			float days = (diff / (1000 * 60 * 60 * 24));
			issuedBookDocument.setFine(days * issuedBookDocument.getFinePerDay());
			issuedBookDocument.setIsReturn(true);
			inventoryBookDocument.setIsIssue(false);
			ReturnBookModel returnBookModel = prepareReturnBookModel(issuedBookDocument);
			inventoryBookDocumentRepository.save(inventoryBookDocument);
			issuedBookDocumentRepository.save(issuedBookDocument);
			libraryResponse.setReturnBookModel(returnBookModel);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while returning issued library book", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	private ReturnBookModel prepareReturnBookModel(IssuedBookDocument issuedBookDocument) {
		ReturnBookModel returnBookModel = new ReturnBookModel(issuedBookDocument.getInventoryId(),
				issuedBookDocument.getLastDate(), issuedBookDocument.getIsReturn(), issuedBookDocument.getFine(),
				issuedBookDocument.getFineReason());
		return returnBookModel;
	}

	@Timed
	public LibraryResponse getissueBookList(BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("IsDeleted").is(false));
			query.addCriteria(Criteria.where("Return").is(false));
			query.addCriteria(Criteria.where("SchoolId").is(baseRequest.getUserRequestIdentity().getSchoolId()));
			List<IssuedBookDocument> issuedBookDocuments = mongoTemplate.find(query, IssuedBookDocument.class);
			if (!CommonUtil.isEmpty(issuedBookDocuments))
				libraryResponse.setIssuedBookDocuments(issuedBookDocuments);
			else
				throw new AppCommonException("there is no issued book at the moment");
			libraryResponse.setStatus(ResponseStatus.SUCCESS);

		} catch (Exception e) {
			logger.error("error while getting issued library book details", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse addLibraryPolicy(LibraryPolicyRequest libraryPolicyRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			if ((libraryPolicyRequest.getFinePerDay().size() != 4)
					|| (libraryPolicyRequest.getMaxAllowBook().size() != 4)
					|| (libraryPolicyRequest.getLastDateOfReturn().size() != 4)) {
				throw new AppCommonException("provide details for all 4 cases");
			}
			validatingLibraryPolicyRequest(libraryPolicyRequest);
			SchoolLibraryPolicyDocument schoolLibraryPolicyDocument = new SchoolLibraryPolicyDocument();
			schoolLibraryPolicyDocument = prepareSchoolLibraryPolicyDocument(libraryPolicyRequest);
			SchoolLibraryPolicyDocument schoolLibraryPolicyDocumentOld = schoolLibraryPolicyDocumentRepository
					.findBySchoolId(libraryPolicyRequest.getUserRequestIdentity().getSchoolId());
			schoolLibraryPolicyDocumentOld.setDeletedAt(new Date());
			schoolLibraryPolicyDocumentOld.setIsDelete(true);
			schoolLibraryPolicyDocumentRepository.save(schoolLibraryPolicyDocumentOld);
			schoolLibraryPolicyDocumentRepository.save(schoolLibraryPolicyDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("error while adding library policy", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	private void validatingLibraryPolicyRequest(LibraryPolicyRequest libraryPolicyRequest) throws AppCommonException {
		if (!CommonUtil.isEmpty(libraryPolicyRequest.getId())) {
			SchoolLibraryPolicyDocument schoolLibraryPolicyDocumentOld = schoolLibraryPolicyDocumentRepository
					.findById(MongoCommonUtil.convertStringToObjectId(libraryPolicyRequest.getId()));
			if (schoolLibraryPolicyDocumentOld == null) {
				throw new AppCommonException("provide valid document Id");
			}
		}
		if ((libraryPolicyRequest.getFinePerDay().size() != 4) || (libraryPolicyRequest.getMaxAllowBook().size() != 4)
				|| (libraryPolicyRequest.getLastDateOfReturn().size() != 4)) {
			throw new AppCommonException("provide details for all 4 cases");
		}

	}

	private SchoolLibraryPolicyDocument prepareSchoolLibraryPolicyDocument(LibraryPolicyRequest libraryPolicyRequest) {
		SchoolLibraryPolicyDocument schoolLibraryPolicyDocument = new SchoolLibraryPolicyDocument(
				libraryPolicyRequest.getUserRequestIdentity().getSchoolId(), libraryPolicyRequest.getUserIds(),
				libraryPolicyRequest.getClassIds(), libraryPolicyRequest.getStudentIds(),
				libraryPolicyRequest.getMaxAllowBook(), libraryPolicyRequest.getLastDateOfReturn(),
				libraryPolicyRequest.getFinePerDay(), null, false);
		return schoolLibraryPolicyDocument;
	}

	@Timed
	public LibraryResponse editLibraryPolicy(LibraryPolicyRequest libraryPolicyRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			validatingLibraryPolicyRequest(libraryPolicyRequest);
			SchoolLibraryPolicyDocument schoolLibraryPolicyDocument = new SchoolLibraryPolicyDocument();
			schoolLibraryPolicyDocument = prepareSchoolLibraryPolicyDocument(libraryPolicyRequest);
			schoolLibraryPolicyDocument.setId(libraryPolicyRequest.getId());
			schoolLibraryPolicyDocumentRepository.save(schoolLibraryPolicyDocument);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("rror while editing library policy", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse getLibraryPolicy(BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			SchoolLibraryPolicyDocument schoolLibraryPolicyDocument = schoolLibraryPolicyDocumentRepository
					.findBySchoolId(baseRequest.getUserRequestIdentity().getSchoolId());
			if (schoolLibraryPolicyDocument != null) {
				SchoolLibraryPolicyModel schoolLibraryPolicyModel = new SchoolLibraryPolicyModel(
						schoolLibraryPolicyDocument.getId(), schoolLibraryPolicyDocument.getUserIds(),
						schoolLibraryPolicyDocument.getClassIds(), schoolLibraryPolicyDocument.getStudentIds(),
						schoolLibraryPolicyDocument.getMaxAllowedBook(), schoolLibraryPolicyDocument.getFinePerDay(),
						schoolLibraryPolicyDocument.getLastDateOfReturn());
				libraryResponse.setSchoolLibraryPolicyModel(schoolLibraryPolicyModel);
				libraryResponse.setStatus(ResponseStatus.SUCCESS);
			} else
				throw new AppCommonException("there is no library policy");
		} catch (Exception e) {
			logger.error("error while getting library policy", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse deleteLibraryPolicy(BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			SchoolLibraryPolicyDocument schoolLibraryPolicyDocument = new SchoolLibraryPolicyDocument();
			schoolLibraryPolicyDocument = schoolLibraryPolicyDocumentRepository
					.findBySchoolId(baseRequest.getUserRequestIdentity().getSchoolId());
			schoolLibraryPolicyDocument.setDeletedAt(new Date());
			schoolLibraryPolicyDocument.setIsDelete(true);
			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("rror while deleting library policy", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	@Timed
	public LibraryResponse getUserIssuedBookList(String userId, Boolean IsUser, BaseRequest baseRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			List<IssuedBookDocument> issuedBookDocuments = new ArrayList<IssuedBookDocument>();
			if (IsUser) {
				issuedBookDocuments = issuedBookDocumentRepository.findByUserIdAndDeletedAtNull(userId);
			} else {
				issuedBookDocuments = issuedBookDocumentRepository.findByStudentIdAndDeletedAtNull(userId);
			}
			if (!CommonUtil.isEmpty(issuedBookDocuments)) {
				IssuedBooksModel issuedBooksModel = prepareIssuedBooksModel(issuedBookDocuments);
				libraryResponse.setIssuedBooksModel(issuedBooksModel);
			} else
				throw new AppCommonException("there is no issued book at the moment");

			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("error while getting user issued book list", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

	private IssuedBooksModel prepareIssuedBooksModel(List<IssuedBookDocument> issuedBookDocuments) {
		IssuedBooksModel issuedBooksModel = new IssuedBooksModel();
		List<IssuedBookDocument> issuedBookDocumentsActive = new ArrayList<IssuedBookDocument>();
		List<IssuedBookDocument> issuedBookDocumentsInActive = new ArrayList<IssuedBookDocument>();

		issuedBookDocuments.parallelStream().forEach(issuedBookDocument -> {
			if (issuedBookDocument.getIsReturn()) {
				issuedBookDocumentsInActive.add(issuedBookDocument);
			}

			else {
				issuedBookDocumentsActive.add(issuedBookDocument);
			}
		});

		issuedBooksModel.setIssuedBookModelsActive(issuedBookDocumentsActive);
		issuedBooksModel.setIssuedBookModelsInActive(issuedBookDocumentsInActive);

		return issuedBooksModel;
	}

	@Timed
	public LibraryResponse sortInventoryModels(InventorySortingRequest inventorySortingRequest) {
		LibraryResponse libraryResponse = new LibraryResponse();
		try {
			if (inventorySortingRequest.getSortingBasedOn().equalsIgnoreCase("title")) {
				Collections.sort(inventorySortingRequest.getInventoryBookModels(), InventoryBookModel.titleComparator);
			} else if (inventorySortingRequest.getSortingBasedOn().equalsIgnoreCase("author")) {
				Collections.sort(inventorySortingRequest.getInventoryBookModels(), InventoryBookModel.authorComparator);
			} else if (inventorySortingRequest.getSortingBasedOn().equalsIgnoreCase("publication")) {
				Collections.sort(inventorySortingRequest.getInventoryBookModels(),
						InventoryBookModel.publicationComparator);
			} else if (inventorySortingRequest.getSortingBasedOn().equalsIgnoreCase("subject")) {
				Collections.sort(inventorySortingRequest.getInventoryBookModels(),
						InventoryBookModel.subjectComparator);
			} else if (inventorySortingRequest.getSortingBasedOn().equalsIgnoreCase("category")) {
				Collections.sort(inventorySortingRequest.getInventoryBookModels(),
						InventoryBookModel.categoryComparator);
			} else if (inventorySortingRequest.getSortingBasedOn().equalsIgnoreCase("keywords")) {
				Collections.sort(inventorySortingRequest.getInventoryBookModels(),
						InventoryBookModel.keywordsComparator);
			} else {
				throw new AppCommonException("provide valid sorting string");
			}
			libraryResponse.setInventoryBookModels(inventorySortingRequest.getInventoryBookModels());
			libraryResponse.setStatus(ResponseStatus.SUCCESS);
		} catch (Exception e) {
			logger.error("error while sorting inventory model list", e);
			libraryResponse.setStatus(ResponseStatus.FAILED);
			libraryResponse.addErrorMessage(AppConstant.INVALID_REQUEST_DATA_KEY, e.getMessage());
		}
		return libraryResponse;
	}

}
