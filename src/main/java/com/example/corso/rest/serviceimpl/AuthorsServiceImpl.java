package com.example.corso.rest.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.corso.rest.resource.AuthorResource;
import com.example.corso.rest.service.AuthorsService;

@Service
public class AuthorsServiceImpl implements AuthorsService {
	private ConcurrentMap<String, AuthorResource> mockAuthors;

	@PostConstruct
	private void initService() {
		mockAuthors = new ConcurrentHashMap<>();
		createAuthor("Stephen", "King");
		createAuthor("J. K.", "Rowling");
	}

	private String createAuthorImpl(String firstName, String lastName) {
		AuthorResource author = new AuthorResource();
		author.setFirstName(firstName);
		author.setLastName(lastName);
		String key;
		while (mockAuthors.putIfAbsent(key = ServiceUtils.randomKey(), author) != null)
			;
		author.setId(key);
		return key;
	}

	@Override
	public List<AuthorResource> findAll() {
		return new ArrayList<>(mockAuthors.values());
	}

	@Override
	public String createAuthor(String firstName, String lastName) {
		if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName))
			return null;
		for (AuthorResource author : mockAuthors.values()) {
			if (Objects.equals(author.getFirstName(), firstName) && Objects.equals(author.getLastName(), lastName)) {
				return null;
			}
		}
		return createAuthorImpl(firstName, lastName);
	}

	@Override
	public AuthorResource findByPrimaryKey(String key) {
		return mockAuthors.get(key);
	}

	@Override
	public AuthorResource updateAuthor(String key, String firstName, String lastName) {
		AuthorResource r = mockAuthors.get(key);
		if (r != null) {
			if (StringUtils.hasText(firstName))
				r.setFirstName(firstName);
			if (StringUtils.hasText(lastName))
				r.setLastName(lastName);
		}
		return r;
	}

	@Override
	public boolean deleteAuthor(String key) {
		return mockAuthors.remove(key) != null;
	}
}
