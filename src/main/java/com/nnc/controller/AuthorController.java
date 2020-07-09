package com.nnc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nnc.dto.SearchWebForm;
import com.nnc.entity.Author;
import com.nnc.entity.Product;
import com.nnc.service.AuthorService;
import com.nnc.service.ProductService;

@Controller
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/author")
	public ModelAndView getAuthorBooks(@RequestParam(name="authorId") int id,  @ModelAttribute("search_mini_form") SearchWebForm searchForm) {
		ModelAndView model = new ModelAndView("author");
		Author author = authorService.findById(id);
		List<Product> products = productService.findBookByAuthorId(id);
		model.addObject("products", products);
		model.addObject("author", author);
		return model;
	}
}
