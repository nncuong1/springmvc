package com.nnc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnc.entity.User;
import com.nnc.service.UserService;
import com.nnc.validator.RegisterValidator;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RegisterValidator registerValidator;
	
	@GetMapping("/register")
	public String customerRegister(Model model) {
		model.addAttribute("registerForm", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String processCustomerRegister( @ModelAttribute("registerForm") User user, BindingResult result) {
		registerValidator.validate(user, result);
		if(result.hasErrors()) {
			return "register";
		}
		try {
			userService.registerNewCustomer(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/login";
	}
}
