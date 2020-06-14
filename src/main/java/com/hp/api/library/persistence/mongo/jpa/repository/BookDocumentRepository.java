package com.hp.api.library.persistence.mongo.jpa.repository;

import org.springframework.stereotype.Repository;

import com.hp.api.library.persistence.mongo.jpa.model.BookDocument;
import com.hp.persistence.jpa.mongo.repository.base.BaseMongoRepository;

@Repository
public interface BookDocumentRepository extends BaseMongoRepository<BookDocument, String> {

}
