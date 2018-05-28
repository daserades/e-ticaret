package com.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

	@RequestMapping("/")
	public String index(Model model,Principal principal){
		model.addAttribute("merhaba",principal.getName());
		return "index";
	}
}
