package com.example.corso.rest.controller;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.corso.rest.dto.AuthorRequestDto;
import com.example.corso.rest.resource.AuthorResource;
import com.example.corso.rest.resource.AuthorsResource;
import com.example.corso.rest.resource.BooksResource;
import com.example.corso.rest.service.AuthorsService;
import com.example.corso.rest.service.BooksService;

@RequestMapping("/authors")
@RestController
public class AuthorsController {
	@Autowired
	private AuthorsService authorsService;
	@Autowired
	private BooksService booksService;

	@GetMapping
	public ResponseEntity<AuthorsResource> getAuthors() {
		AuthorsResource authors = new AuthorsResource();
		authors.setResources(authorsService.findAll());
		return ResponseEntity.ok(authors);
	}

	@PostMapping
	public ResponseEntity<Void> postAuthors(@RequestBody AuthorRequestDto dto) {
		String id = authorsService.createAuthor(dto.getFirstName(), dto.getLastName());
		if (id == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(id).build().toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorResource> getAuthor(@PathVariable("id") String id) {
		AuthorResource author = authorsService.findByPrimaryKey(id);
		return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/books")
	public ResponseEntity<BooksResource> getAuthorBooks(@PathVariable("id") String id) {
		AuthorResource author = authorsService.findByPrimaryKey(id);
		if (author == null) {
			return ResponseEntity.notFound().build();
		}
		BooksResource books = new BooksResource();
		books.setResources(booksService.findByAuthor(author));
		return ResponseEntity.ok(books);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AuthorResource> patchAuthor(@PathVariable("id") String id,
			@RequestBody AuthorRequestDto dto) {
		if (StringUtils.hasText(dto.getId()) && !Objects.equals(id, dto.getId())) {
			return ResponseEntity.badRequest().build();
		}
		AuthorResource author = authorsService.updateAuthor(id, "", "Rowling");
		return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable("id") String id) {
		return authorsService.deleteAuthor(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
