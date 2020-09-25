package com.example.corso.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autori")
public class AutoriController {
	@GetMapping
	public String getPage() {
		return "autori";
	}
}
