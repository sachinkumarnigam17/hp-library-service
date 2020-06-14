package com.hp.api.library.service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.api.library.persistence.mongo.jpa.model.InventoryBookDocument;
import com.hp.api.library.persistence.mongo.jpa.repository.InventoryBookDocumentRepository;

@Service
public class InventoryBookDocumentDAO {

	@Autowired
	InventoryBookDocumentRepository inventoryBookDocumentRepository;

	public List<InventoryBookDocument> findByTitle(String string) {

		return inventoryBookDocumentRepository.findByTitle(string);
	}

}
