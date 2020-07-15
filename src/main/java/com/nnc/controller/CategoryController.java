package com.nnc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nnc.entity.Category;
import com.nnc.service.CategoryService;
import com.nnc.util.Constant;
import com.nnc.util.Paging;
import com.nnc.validator.CategoryValidator;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	 
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryValidator categoryValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if(binder.getTarget().getClass()== Category.class) {
			binder.setValidator(categoryValidator);
		}
	}
	
	@RequestMapping("/category/list")
	public String showCategoryList(Model model, HttpSession session,@RequestParam(name="keyword", required = false) String keyword) {
	//public String showCategoryList(Model model, HttpSession session,@ModelAttribute("searchForm") String keyword) {
		Paging paging = new Paging(12);
		paging.setIndexPage(1);
		List<Category> categories = categoryService.getAllCategory(keyword,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null) {
			model.addAttribute(Constant.MSG_SUCCESS,session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);;
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null) {
			model.addAttribute(Constant.MSG_ERROR,session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);;
		}
		model.addAttribute("pageInfor",paging);
		model.addAttribute("categories", categories);
		return "category-list";
	}
	
	@RequestMapping(path="/paging/category", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String showCategories(Model model, HttpSession session,@RequestParam(name="keyword", required = false) String keyword,
			@RequestParam(required = false) int page) {
		Paging paging = new Paging(12);
		paging.setIndexPage(page);
		List<Category> categories = categoryService.getAllCategory(keyword,paging);
		StringBuilder builder = new StringBuilder();
		for(Category c: categories) {
			builder.append("<tr>");
			builder.append("<th scope=\'row\'>"+(paging.getOffset()+categories.indexOf(c)+1)+"</th>");
			builder.append("<td>"+c.getId()+"</td>");
			builder.append("<td>"+c.getName()+"</td>");
			builder.append("<td>"+c.getDescription()+"</td>");
			builder.append("<td>");
			builder.append("<a href='/inventory/admin/category/view/"+c.getId()+"'> View </a>");
			builder.append("<a href='/inventory/admin/category/edit/"+c.getId()+"'> Edit </a>");
			builder.append("<a href='javascript:void(0);' onclick='confirmDelete("+c.getId()+")' > Delete</a>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}
	
	
	
	@GetMapping("/category/add")
	public String addCategory(Model model) {
		model.addAttribute("categoryForm", new Category());
		model.addAttribute("titlePage", "Add category");
		model.addAttribute("viewOnly",false);
		return "category-action";
	}
	@GetMapping("/category/edit/{id}")
	public String editCategory(Model model, @PathVariable("id") int id) {
		Category category = categoryService.findById(id);
		if(category!=null) {
			model.addAttribute("titlePage", "Edit category");
			model.addAttribute("categoryForm", category);
			model.addAttribute("viewOnly",false);
			return "category-action";
		}
		return "redirect:/admin/category/list";
	}
	@GetMapping("/category/view/{id}")
	public String viewCategory(Model model, @PathVariable("id") int id) {
		Category category = categoryService.findById(id);
		if(category!=null) {
			model.addAttribute("titlePage", "View category");
			model.addAttribute("categoryForm", category);
			model.addAttribute("viewOnly",true);
			return "category-action";
		}
		return "redirect:/admin/category/list";
	}
	
	@PostMapping("/category/save")
	public String saveCategory(Model model, @ModelAttribute("categoryForm") Category category,  BindingResult result, HttpSession session) {
		categoryValidator.validate(category, result);
		if(result.hasErrors()) {
			if(category.getId()!=null) {
				model.addAttribute("titlePage", "Edit category");
			}else {
				model.addAttribute("titlePage", "Add category");
			}
			model.addAttribute("categoryForm", category);
			model.addAttribute("viewOnly",false);
			return "category-action";
		}
		if(category.getId()!=null && category.getId()!=0) {
			try {
				categoryService.updateCategory(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			//model.addAttribute("message", "msg.update.success");
		}else {
			try {
				categoryService.saveCategory(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
			} catch (Exception e) {
				
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Insert has error");
			}
			//model.addAttribute("message", "msg.add.success");
		}
		return "redirect:/admin/category/list";
	}
	@GetMapping("/category/delete/{id}")
	public String deleteCategory(Model model, @PathVariable("id") int id, HttpSession session) {
		Category category = categoryService.findById(id);
		if(category!=null) {
			try {
				categoryService.deleteCategory(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error");
			}
		}
		return "redirect:/admin/category/list";
	}
	
	
}
