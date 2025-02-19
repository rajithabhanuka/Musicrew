package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	private UserRepository repository;

	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/memberRegistration")
	public String memberRegistration(@ModelAttribute User user, Model model) {
		model.addAttribute("userInput", repository.findAll());

		return "memberRegistration";
	}

	@PostMapping("/profile")
	public String confirmingRegistration(@Validated @ModelAttribute User user, BindingResult result, Model model) {
		model.addAttribute("userInput", repository.findAll());

		if (result.hasErrors()) {
			return "memberRegistration";
		}
		repository.save(user);
		return "login";
	}
}
