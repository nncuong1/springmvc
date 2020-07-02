package com.nnc.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nnc.entity.Authority;
import com.nnc.entity.Menu;
import com.nnc.entity.Role;
import com.nnc.entity.User;
import com.nnc.service.AuthService;
import com.nnc.service.MenuService;
import com.nnc.service.UserService;
import com.nnc.util.Constant;
import com.nnc.validator.LoginValidator;

@Controller
//@RequestMapping("/admin")
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private LoginValidator loginValidator;
	@Autowired
	private AuthService authService;
	@Autowired
	private MenuService menuService;
	
	private static final Logger log = Logger.getLogger(LoginController.class);
	
	@GetMapping("/admin/home")
	public String home() {
		return "home-admin";
	}
	
	@GetMapping("/admin/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new User());
		return "login";
	}

	@PostMapping("/admin/login")
	public String processLogin (Model model, @ModelAttribute("loginForm") User user, BindingResult result, HttpSession session) {
		loginValidator.validate(user, result);
		if(result.hasErrors()) {
			return "login";
		}
		User u = userService.findByUserName(user.getUsername());
		Role role = u.getRole();
		
		List<Authority> auths =authService.getAuthByRoleId(role.getId());
		
		Set<Authority> authsSet = new HashSet<Authority>();
		for(Authority auth :auths) {
			authsSet.add(auth);
		}
		
		u.getRole().setAuths(authsSet);
		
		List<Menu> menuList = menuService.getMenuForRole(auths);
		
		session.setAttribute(Constant.MENU_SESSION, menuList);
		session.setAttribute(Constant.USER_INFOR, u);
		//session.setAttribute(Constant.AUTHS_SESSION, auths);
		return "redirect:/admin/home";
		
	}
	
	@GetMapping("/admin/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.MENU_SESSION);
		session.removeAttribute(Constant.USER_INFOR);
		//session.removeAttribute(Constant.AUTHS_SESSION);
		return "redirect:/admin/login";
	}
	
	@GetMapping("/admin/access-denied")
	public String accessDenied() {
		return "access-denied";
	}
	
	// Start Member Login and register
	@GetMapping("/login")
	public String customerLogin(Model model) {
		model.addAttribute("customerForm",new User());
		return "login-web";
	}
	@PostMapping("/login")
	public String processCustomerLogin(Model model, @ModelAttribute("customerForm") User user, BindingResult result, HttpSession session, @RequestParam(required = false) String from) {
		loginValidator.validate(user, result);
		if(result.hasErrors()) {
			return "login-web";
		}
		
		User u = userService.findByUserName(user.getUsername());
		session.setAttribute(Constant.CUSTOMER_INFOR, u);
//		if(session.getAttribute(Constant.MSG_PREVIOUS_PAGE)!=null) {
//			String url = (String)session.getAttribute(Constant.MSG_PREVIOUS_PAGE);
//			session.removeAttribute(Constant.MSG_PREVIOUS_PAGE);
//			return "redirect:"+url;
//		}
		if(from!=null && !from.isEmpty()) {
			//log.info("request from : "+from.substring(0, from.length()-1));
			return "redirect:"+from.substring(0, from.length()-1);
		}else {
			log.info("no from : ");
		}
		return "redirect:/shop";
	}
	
	@GetMapping("/logout")
	public String customerLogout(HttpSession session) {
		session.removeAttribute(Constant.CUSTOMER_INFOR);
		return "redirect:/shop";
	}
}
