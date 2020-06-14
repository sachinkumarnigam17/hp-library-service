package com.hp.api.library.entity.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.api.library.entity.model.InventoryBookModel;
import com.hp.entity.request.base.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventorySortingRequest extends BaseRequest {

	@JsonProperty("inventory_book_models")
	private List<InventoryBookModel> InventoryBookModels;

	@JsonProperty("sorting_based_on")
	private String sortingBasedOn;
}
