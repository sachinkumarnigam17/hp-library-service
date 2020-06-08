package com.hp.api.library.entity.response;

import com.hp.api.library.entity.model.LibraryUserModel;
import com.hp.entity.response.base.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse extends BaseResponse {

	LibraryUserModel libraryUserModel;

}
