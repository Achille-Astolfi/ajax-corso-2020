package com.example.corso.rest.dto;

public class BookRequestDto {
	private String id;
	private String title;
	private AuthorRequestDto author;

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

	public AuthorRequestDto getAuthor() {
		return author;
	}

	public void setAuthor(AuthorRequestDto author) {
		this.author = author;
	}
}
