package com.hp.api.library.persistence.mongo.jpa.repository;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.SchoolLibraryPolicyDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;

@Repository
public interface SchoolLibraryPolicyDocumentRepository
		extends BaseMongoRepository<SchoolLibraryPolicyDocument, String> {

	SchoolLibraryPolicyDocument findBySchoolId(String schoolId);

	SchoolLibraryPolicyDocument findById(ObjectId convertStringToObjectId);

}
