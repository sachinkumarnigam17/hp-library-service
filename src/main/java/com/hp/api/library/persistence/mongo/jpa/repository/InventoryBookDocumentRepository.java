package com.hp.api.library.persistence.mongo.jpa.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.InventoryBookDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;

@Repository
public interface InventoryBookDocumentRepository extends BaseMongoRepository<InventoryBookDocument, String> {

	InventoryBookDocument findById(ObjectId id);

	InventoryBookDocument findByISBN(String iSBN);

	@Query("{'bookDocument.title': ?0}")
	List<InventoryBookDocument> findByTitle(String string);

	
}

