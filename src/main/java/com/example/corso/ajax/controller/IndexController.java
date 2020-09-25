package com.example.corso.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.corso.ajax.viewmodel.IndexFormBean;

@Controller
@RequestMapping({ "/index", "/" })
public class IndexController {
	@GetMapping
	public String getPage(Model model) {
		model.addAttribute("formBean", new IndexFormBean());
		return "index";
	}
}
