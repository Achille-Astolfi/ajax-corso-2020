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
import com.example.corso.rest.dto.BookRequestDto;
import com.example.corso.rest.resource.AuthorResource;
import com.example.corso.rest.resource.BookResource;
import com.example.corso.rest.resource.BooksResource;
import com.example.corso.rest.service.AuthorsService;
import com.example.corso.rest.service.BooksService;

@RequestMapping("/books")
@RestController
public class BooksController {
	@Autowired
	private BooksService booksService;
	@Autowired
	private AuthorsService authorsService;

	@GetMapping
	public ResponseEntity<BooksResource> getBooks() {
		BooksResource books = new BooksResource();
		books.setResources(booksService.findAll());
		return ResponseEntity.ok(books);
	}

	@PostMapping
	public ResponseEntity<Void> postBooks(@RequestBody BookRequestDto dto) {
		AuthorRequestDto authorDto = dto.getAuthor();
		if (authorDto == null) {
			return ResponseEntity.badRequest().build();
		}
		AuthorResource author;
		if (StringUtils.hasText(authorDto.getId())) {
			author = authorsService.findByPrimaryKey(authorDto.getId());
			if (author == null) {
				return ResponseEntity.notFound().build();
			}
		} else {
			String authorId = authorsService.createAuthor(authorDto.getFirstName(), authorDto.getLastName());
			if (authorId == null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			author = authorsService.findByPrimaryKey(authorId);
		}
		String id = booksService.createBook(dto.getTitle(), author);
		if (id == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(id).build().toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResource> getBook(@PathVariable("id") String id) {
		BookResource book = booksService.findByPrimaryKey(id);
		return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<BookResource> patchBook(@PathVariable("id") String id, @RequestBody BookRequestDto dto) {
		if (StringUtils.hasText(dto.getId()) && !Objects.equals(id, dto.getId())) {
			return ResponseEntity.badRequest().build();
		}
		AuthorRequestDto authorDto = dto.getAuthor();
		AuthorResource author = null;
		if (authorDto != null && StringUtils.hasText(authorDto.getId())) {
			author = authorsService.findByPrimaryKey(authorDto.getId());
		} else if (authorDto != null) {
			String authorId = authorsService.createAuthor(authorDto.getFirstName(), authorDto.getLastName());
			if (authorId != null) {
				author = authorsService.findByPrimaryKey(authorId);
			}
		}
		BookResource book = booksService.updateBook(id, dto.getTitle(), author);
		return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") String id) {
		return booksService.deleteBook(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
