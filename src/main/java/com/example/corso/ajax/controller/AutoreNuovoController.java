package com.example.corso.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.corso.ajax.viewmodel.AutoreNuovoFormBean;

@Controller
@RequestMapping("/autore-nuovo")
public class AutoreNuovoController {
	@GetMapping
	public String getPage(Model model) {
		AutoreNuovoFormBean formBean = new AutoreNuovoFormBean();
		model.addAttribute("formBean", formBean);
		return "autore-nuovo-form";
	}

}
