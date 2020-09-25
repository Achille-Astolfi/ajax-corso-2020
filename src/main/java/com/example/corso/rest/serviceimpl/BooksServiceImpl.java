package com.example.corso.rest.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.corso.rest.resource.AuthorResource;
import com.example.corso.rest.resource.BookResource;
import com.example.corso.rest.service.AuthorsService;
import com.example.corso.rest.service.BooksService;

@Service
public class BooksServiceImpl implements BooksService {
	private ConcurrentMap<String, BookResource> mockBooks;

	@Autowired
	private AuthorsService authorsService;

	@PostConstruct
	private void initService() {
		mockBooks = new ConcurrentHashMap<>();

		List<AuthorResource> authors = authorsService.findAll();

		createBookImpl("It", authors.get(0));
		createBookImpl("Shining", authors.get(1));
		createBookImpl("Harry Potter", authors.get(0));
		createBookImpl("Cormoran Strike", authors.get(1));
	}

	private String createBookImpl(String title, AuthorResource author) {
		BookResource book = new BookResource();
		book.setTitle(title);
		book.setAuthor(author);
		String key;
		while (mockBooks.putIfAbsent(key = ServiceUtils.randomKey(), book) != null)
			;
		book.setId(key);
		return key;
	}

	@Override
	public List<BookResource> findAll() {
		return new ArrayList<>(mockBooks.values());
	}

	@Override
	public String createBook(String title, AuthorResource author) {
		if (!StringUtils.hasText(title) || author == null) {
			return null;
		}
		return createBookImpl(title, author);
	}

	@Override
	public BookResource findByPrimaryKey(String key) {
		return mockBooks.get(key);
	}

	@Override
	public List<BookResource> findByAuthor(AuthorResource author) {
		return mockBooks.values().stream().filter(b -> b.getAuthor() == author).collect(Collectors.toList());
	}

	@Override
	public BookResource updateBook(String key, String title, AuthorResource author) {
		BookResource r = mockBooks.get(key);
		if (r != null) {
			if (StringUtils.hasText(title))
				r.setTitle(title);
			if (author != null)
				r.setAuthor(author);
		}
		return r;
	}

	@Override
	public boolean deleteBook(String key) {
		return mockBooks.remove(key) != null;
	}
}
