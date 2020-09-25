package com.example.corso.rest.service;

import java.util.List;

import com.example.corso.rest.resource.AuthorResource;
import com.example.corso.rest.resource.BookResource;

public interface BooksService {

	BookResource findByPrimaryKey(String key);

	String createBook(String title, AuthorResource author);

	List<BookResource> findAll();

	BookResource updateBook(String key, String title, AuthorResource author);

	boolean deleteBook(String key);

	List<BookResource> findByAuthor(AuthorResource author);

}