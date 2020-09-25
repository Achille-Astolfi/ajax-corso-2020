package com.example.corso.rest.resource;

import java.util.List;

public class AuthorsResource {
	private List<AuthorResource> resources;

	public List<AuthorResource> getResources() {
		return resources;
	}

	public void setResources(List<AuthorResource> resources) {
		this.resources = resources;
	}
}
