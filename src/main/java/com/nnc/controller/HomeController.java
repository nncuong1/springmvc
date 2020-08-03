package com.nnc.controller;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nnc.dto.SearchWebForm;
import com.nnc.entity.Category;
import com.nnc.entity.Product;
import com.nnc.service.CategoryService;
import com.nnc.service.ProductService;
import com.nnc.service.ProductServiceImpl;
import com.nnc.util.Paging;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	private static final Logger log = Logger.getLogger(ProductServiceImpl.class);
	
	@RequestMapping("/shop")
	public String shop(Model model, @ModelAttribute("search_mini_form") SearchWebForm searchForm,  @RequestParam(required = false) Integer page) {
		Paging paging = new Paging(12);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		if(searchForm.getKeyword()==null || searchForm.getKeyword().isEmpty()) {
			searchForm.setKeyword(null);
		}
		List<Product> products = productService.getAllProduct(searchForm, paging);
		List<Category> categories = categoryService.getAllCategory(null, null);
		model.addAttribute("pageInfor",paging);
		model.addAttribute("products", products);
		model.addAttribute("categories",categories);
		return "shop";
	}
	
	@RequestMapping("/home")
	public String home(Model model, @ModelAttribute("search_mini_form") SearchWebForm searchForm) {
		List<Product> newestProducts = productService.getNewestProducts();
		List<Product> bestSellerProducts = productService.getBestSellerProducts();
		
		model.addAttribute("newestProducts",newestProducts);
		model.addAttribute("bestSellerProducts",bestSellerProducts);
		return "home";
	}
	
	@RequestMapping("/category/{id}")
	public String categorySideBar(Model model, @ModelAttribute("search_mini_form") SearchWebForm searchForm, @RequestParam(required = false) Integer page, @PathVariable("id") int id) {
		Paging paging = new Paging(9);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		List<Product> products = productService.findBookByCategoryId(id,paging);
		List<Category> categories = categoryService.getAllCategory(null, null);
		model.addAttribute("pageInfor",paging);
		model.addAttribute("products", products);
		model.addAttribute("categories",categories);
		return "shop";
	}
}
