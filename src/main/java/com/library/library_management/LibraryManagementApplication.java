package com.library.library_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Library Management Application.
 * This is the main class that initializes and runs the Spring Boot application.
 *
 * The application is designed to manage a library system, including features
 * such as book cataloging, user management, and borrowing/returning of books.
 *
 * It leverages the Spring Boot framework to provide a robust and scalable
 * platform for library operations.
 */
@SpringBootApplication
public class LibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
