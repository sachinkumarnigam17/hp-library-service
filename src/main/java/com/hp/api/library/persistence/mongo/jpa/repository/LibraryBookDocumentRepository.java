package com.hp.api.library.persistence.mongo.jpa.repository;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.LibraryBookDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;


@Repository
public interface LibraryBookDocumentRepository extends BaseMongoRepository<LibraryBookDocument, String> {

	LibraryBookDocument findById(ObjectId bookId);
	
}