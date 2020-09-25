package com.example.corso.rest.resource;

import java.util.List;

public class BooksResource {
	private List<BookResource> resources;

	public List<BookResource> getResources() {
		return resources;
	}

	public void setResources(List<BookResource> resources) {
		this.resources = resources;
	}
}
