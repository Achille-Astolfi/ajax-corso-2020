package com.example.corso.rest.service;

import java.util.List;

import com.example.corso.rest.resource.AuthorResource;

public interface AuthorsService {

	AuthorResource findByPrimaryKey(String key);

	String createAuthor(String firstName, String lastName);

	List<AuthorResource> findAll();

	AuthorResource updateAuthor(String key, String firstName, String lastName);

	boolean deleteAuthor(String key);

}