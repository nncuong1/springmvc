package com.nnc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.runtime.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
//	@Autowired
//	ProvinceService provinceService;
	
	private static final Logger log = Logger.getLogger(ProductServiceImpl.class);
	
	
	@RequestMapping("/shop")
	public String home(Model model, @RequestParam(required = false) String page, @RequestParam(required = false) String key) {
		Paging paging = new Paging(9);
		if(page==null || StringUtils.isEmpty(page)) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(Integer.parseInt(page));
		}
		if(key==null || key.isEmpty()) {
			key = null;
		}
		List<Product> products = productService.getAllProduct(key, paging);
		List<Category> categories = categoryService.getAllCategory(null, null);
		model.addAttribute("pageInfor",paging);
		model.addAttribute("products", products);
		model.addAttribute("categories",categories);
		return "shop";
	}
	
	@RequestMapping("/category/{id}")
	public String category(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String keyword, 
			@PathVariable("id") int id) {
		Paging paging = new Paging(3);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		if(keyword==null || keyword.isEmpty()) {
			keyword = null;
		}
		List<Product> products = productService.findBookByCategoryId(id,paging);
		List<Category> categories = categoryService.getAllCategory(null, null);
		model.addAttribute("pageInfor",paging);
		model.addAttribute("products", products);
		model.addAttribute("categories",categories);
		return "shop";
	}
}
