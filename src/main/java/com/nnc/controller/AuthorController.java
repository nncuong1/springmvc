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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nnc.dto.OrderForm;
import com.nnc.dto.SearchWebForm;
import com.nnc.entity.Author;
import com.nnc.entity.Product;
import com.nnc.service.AuthorService;
import com.nnc.service.ProductService;
import com.nnc.util.Constant;
import com.nnc.util.Paging;
import com.nnc.validator.AuthorValidator;

@Controller
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private AuthorValidator authorValidator;
	
	@Autowired
	private ProductService productService;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if(binder.getTarget().getClass()== Author.class) {
			binder.setValidator(authorValidator);
		}
	}
	/*
	 * ADMIN
	 * */
	@RequestMapping("/admin/author/list")
	public String showauthorList(Model model, HttpSession session,@ModelAttribute("authorSearch") Author authorForm) {
		Paging paging = new Paging(15);
		paging.setIndexPage(1);
		List<Author> authors = authorService.getAllAuthor(authorForm, paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null) {
			model.addAttribute(Constant.MSG_SUCCESS,session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);;
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null) {
			model.addAttribute(Constant.MSG_ERROR,session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);;
		}
		model.addAttribute("pageInfor",paging);
		model.addAttribute("authors", authors);
		return "author-list";
	}
	
	@GetMapping("/admin/author/add")
	public String addauthor(Model model) {
		model.addAttribute("authorForm", new Author());
		model.addAttribute("titlePage", "Add author");
		model.addAttribute("viewOnly",false);
		return "author-action";
	}
	
	@GetMapping("/admin/author/edit/{id}")
	public String editauthor(Model model, @PathVariable("id") int id) {
		Author author = authorService.findById(id);
		if(author!=null) {
			model.addAttribute("titlePage", "Edit author");
			model.addAttribute("authorForm", author);
			model.addAttribute("viewOnly",false);
			return "author-action";
		}
		return "redirect:/admin/author/list";
	}
	@GetMapping("/admin/author/delete/{id}")
	public String deleteauthor(Model model, @PathVariable("id") int id, HttpSession session) {
		Author author = authorService.findById(id);
		if(author!=null) {
			try {
				authorService.deleteAuthor(author);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error");
			}
		}
		return "redirect:/admin/author/list";
	}
	@PostMapping("/admin/author/save")
	public String saveauthor(Model model, @ModelAttribute("authorForm") Author author,  BindingResult result, HttpSession session) {
		authorValidator.validate(author, result);
		if(result.hasErrors()) {
			if(author.getId()!=null) {
				model.addAttribute("titlePage", "Edit author");
			}else {
				model.addAttribute("titlePage", "Add author");
			}
			model.addAttribute("authorForm", author);
			model.addAttribute("viewOnly",false);
			return "author-action";
		}
		if(author.getId()!=null && author.getId()!=0) {
			try {
				authorService.updateAuthor(author);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			//model.addAttribute("message", "msg.update.success");
		}else {
			try {
				authorService.saveAuthor(author);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
			} catch (Exception e) {
				
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Insert has error");
			}
			//model.addAttribute("message", "msg.add.success");
		}
		return "redirect:/admin/author/list";
	}
	
	@RequestMapping(path="/admin/paging/author", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String showCategories(Model model, HttpSession session, @RequestBody Author authorForm,@RequestParam(required = false) int page) {
		Paging paging = new Paging(15);
		paging.setIndexPage(page);
		List<Author> authors = authorService.getAllAuthor(authorForm,paging);
		StringBuilder builder = new StringBuilder();
		for(Author c: authors) {
			builder.append("<tr>");
			builder.append("<th scope=\'row\'>"+(paging.getOffset()+authors.indexOf(c)+1)+"</th>");
			builder.append("<td>"+c.getName()+"</td>");
			builder.append("<td>"+c.getCode()+"</td>");
			builder.append("<td>");
			builder.append("<a href='/inventory/admin/author/edit/"+c.getId()+"'> Edit </a>");
			builder.append("<a href='javascript:void(0);' onclick='confirmDelete("+c.getId()+")' > Delete</a>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}
	
	/*
	 * WEB
	 * */
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
