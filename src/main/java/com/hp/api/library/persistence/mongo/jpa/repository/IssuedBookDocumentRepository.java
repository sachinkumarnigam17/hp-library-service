package com.hp.api.library.persistence.mongo.jpa.repository;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.LibraryIssuedBookDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;

@Repository
public interface IssuedBookDocumentRepository extends BaseMongoRepository<LibraryIssuedBookDocument, String> {

	
	LibraryIssuedBookDocument findById(ObjectId id);
	
}