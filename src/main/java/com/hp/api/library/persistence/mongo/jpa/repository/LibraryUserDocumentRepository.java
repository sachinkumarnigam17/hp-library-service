package com.hp.api.library.persistence.mongo.jpa.repository;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.LibraryUserDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;

@Repository
public interface LibraryUserDocumentRepository extends BaseMongoRepository<LibraryUserDocument, String> {

	LibraryUserDocument findById(ObjectId id);

	LibraryUserDocument findByUserId(String userId);

}
