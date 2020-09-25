package com.example.corso.rest.resource;

public class BookResource {
	private String id;
	private String title;
	private AuthorResource author;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AuthorResource getAuthor() {
		return author;
	}

	public void setAuthor(AuthorResource author) {
		this.author = author;
	}
}
