package com.hp.api.library.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hp.api.framework.service.core.app.BaseApplication;

@SpringBootApplication
public class LibraryApplication extends BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
