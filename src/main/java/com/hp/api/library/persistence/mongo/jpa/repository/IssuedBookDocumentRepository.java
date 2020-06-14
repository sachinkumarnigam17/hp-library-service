package com.hp.api.library.persistence.mongo.jpa.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.IssuedBookDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;

@Repository
public interface IssuedBookDocumentRepository extends BaseMongoRepository<IssuedBookDocument, String> {

	IssuedBookDocument findById(ObjectId id);

	IssuedBookDocument findByInventoryIdAndDeletedAtNull(String inventoryId);

	List<IssuedBookDocument> findByUserIdAndDeletedAtNull(String userId);

	List<IssuedBookDocument> findByStudentIdAndDeletedAtNull(String userId);

}
