package com.nnc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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

import com.nnc.dto.ReviewDTO;
import com.nnc.dto.SearchWebForm;
import com.nnc.entity.Author;
import com.nnc.entity.Category;
import com.nnc.entity.Product;
import com.nnc.entity.User;
import com.nnc.service.AuthorService;
import com.nnc.service.CategoryService;
import com.nnc.service.ProductService;
import com.nnc.service.RecommendService;
import com.nnc.service.ReviewService;
import com.nnc.util.Constant;
import com.nnc.util.Paging;
import com.nnc.validator.ProductValidator;

@Controller
//@RequestMapping("/admin")
public class ProductController {

	private static final Logger log = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductValidator productValidator;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private RecommendService recommendService;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Product.class) {
			binder.setValidator(productValidator);
		}
	}

	@RequestMapping("/admin/product/list")
	public String showproductList(Model model, HttpSession session,
			@RequestParam(name = "keyword", required = false) String keyword) {
		Paging paging = new Paging(10);
		paging.setIndexPage(1);
		List<Product> products = productService.getAllProduct(keyword, paging);
		if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
			;
		}
		if (session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
			;
		}
		model.addAttribute("pageInfor", paging);
		model.addAttribute("products", products);
		return "product-list";
	}

	@GetMapping("/admin/product/add")
	public String addProduct(Model model) {
		List<Category> categories = categoryService.getAllCategory(null, null);
		List<Author> authors = authorService.getAllAuthor(null, null);
		Map<String, String> mapCategory = new HashMap<String, String>();
		for (Category category : categories) {
			mapCategory.put(String.valueOf(category.getId()), category.getName());
		}
		Map<String, String> mapAuthor = new HashMap<String, String>();
		for (Author author : authors) {
			mapAuthor.put(String.valueOf(author.getId()), author.getName());
		}
		model.addAttribute("productForm", new Product());
		model.addAttribute("mapCategory", mapCategory);
		model.addAttribute("mapAuthor", mapAuthor);
		model.addAttribute("titlePage", "Add product");

		model.addAttribute("viewOnly", false);
		return "product-action";
	}

	@GetMapping("/admin/product/edit/{id}")
	public String editProduct(Model model, @PathVariable("id") int id) {
		Product product = productService.findById(id);
//		Product product = productService.findBookDetailById(id);
		if (product != null) {
			log.info("start in controller add mapp");
			List<Category> categories = categoryService.getAllCategory(null, null);
			List<Author> authors = authorService.getAllAuthor(null, null);
			Map<String, String> mapCategory = new HashMap<String, String>();
			for (Category category : categories) {
				mapCategory.put(String.valueOf(category.getId()), category.getName());
			}
			Map<String, String> mapAuthor = new HashMap<String, String>();
			for (Author author : authors) {
				mapAuthor.put(String.valueOf(author.getId()), author.getName());
			}
//			for(Author a: product.getAuthors()) {
//				log.info("author : "+a.getName());
//			}
			model.addAttribute("mapCategory", mapCategory);
			model.addAttribute("mapAuthor", mapAuthor);
			model.addAttribute("titlePage", "Edit product");
			model.addAttribute("productForm", product);
			model.addAttribute("viewOnly", false);
			return "product-action";
		}
		return "redirect:/admin/product/list";
	}

	@GetMapping("/admin/product/view/{id}")
	public String viewProduct(Model model, @PathVariable("id") int id) {
		Product product = productService.findById(id);
		if (product != null) {
			Category category = categoryService.findById(product.getCategory().getId());
			List<String> authorNames = authorService.getAuthorNamesByBookId(id);
			String names = String.join(",", authorNames);
			model.addAttribute("titlePage", "View product");
			model.addAttribute("productForm", product);
			model.addAttribute("category", category);
			model.addAttribute("names", names);
			model.addAttribute("viewOnly", true);
			return "product-action";
		}
		return "redirect:/admin/product/list";
	}

	@PostMapping("/admin/product/save")
	public String saveProduct(Model model, @ModelAttribute("productForm") Product product, BindingResult result,
			HttpSession session) {
		productValidator.validate(product, result);
		if (result.hasErrors()) {
			if (product.getId() != null) {
				model.addAttribute("titlePage", "Edit product");
			} else {
				model.addAttribute("titlePage", "Add product");
				log.error(result.getAllErrors());
			}
			List<Category> categories = categoryService.getAllCategory(null, null);
			List<Author> authors = authorService.getAllAuthor(null, null);
			Map<String, String> mapCategory = new HashMap<String, String>();
			for (Category category : categories) {
				mapCategory.put(String.valueOf(category.getId()), category.getName());
			}
			Map<String, String> mapAuthor = new HashMap<String, String>();
			for (Author author : authors) {
				mapAuthor.put(String.valueOf(author.getId()), author.getName());
			}
			model.addAttribute("mapCategory", mapCategory);
			model.addAttribute("mapAuthor", mapAuthor);
			model.addAttribute("productForm", product);
			model.addAttribute("viewOnly", false);
			return "product-action";
		}
		if (product.getId() != null && product.getId() != 0) {
			try {

				log.info(product.getAuthorId());
				productService.updateProduct(product);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
		} else {
			try {
				log.info(product.getAuthorId());
				productService.saveProduct(product);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
			} catch (Exception e) {

				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Insert has error");
			}
		}
		return "redirect:/admin/product/list";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteproduct(Model model, @PathVariable("id") int id, HttpSession session) {
		Product product = productService.findById(id);
		if (product != null) {
			try {
				productService.deleteProduct(product);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error");
			}
		}
		return "redirect:/admin/product/list";
	}

	@RequestMapping(path = "/admin/paging/product", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String showproducts(Model model, HttpSession session,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(required = false) int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);
		List<Product> products = productService.getAllProduct(keyword, paging);
		StringBuilder builder = new StringBuilder();
		for (Product p : products) {
			builder.append("<tr>");
			builder.append("<th scope=\'row\'>" + (paging.getOffset() + products.indexOf(p) + 1) + "</th>");
			if (p.getImgUrl() != null && !p.getImgUrl().isEmpty()) {
				builder.append("<td> <img src='/inventory/files/product/" + p.getImgUrl()
						+ "' width='50px' height='50px' /></td>");
			} else {
				builder.append("<td> No image </td>");
			}
			builder.append("<td>" + p.getTitle() + "</td>");
			builder.append("<td>" + p.getPrice() + "</td>");
			builder.append("<td>");
			builder.append("<a href='/inventory/admin/product/view/" + p.getId() + "'> View </a>");
			builder.append("<a href='/inventory/admin/product/edit/" + p.getId() + "'> Edit </a>");
			builder.append("<a href='javascript:void(0);' onclick='confirmDelete(" + p.getId() + ")' > Delete</a>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}

	@GetMapping("/product")
	public String productDetail(Model model, @RequestParam(name = "productId") int id,
			@RequestParam(required = false) Integer page, HttpSession session,  @ModelAttribute("search_mini_form") SearchWebForm searchForm) throws Exception {
		List<Category> categories = categoryService.getAllCategory(null, null);
		Product product = productService.findById(id);
		List<Author> authors = authorService.findByBookId(id);
		// pagenation comment
		Paging paging = new Paging(5);
		if (page == null) {
			paging.setIndexPage(1);
		} else {
			paging.setIndexPage(page);
		}
		List<ReviewDTO> reviews = reviewService.findByProductId(id, paging);
		User customer = (User) session.getAttribute(Constant.CUSTOMER_INFOR);
		List<Product> recommendedProducts = null;
		if (customer != null) {
			recommendedProducts = productService.getRecommendedProducts(customer.getId(), id, product.getCategory().getId());
		} else {
			recommendedProducts = productService.getRecommendedProducts(null, id, product.getCategory().getId());
		}
		model.addAttribute("recommendedProducts", recommendedProducts);
		model.addAttribute("pageInfor", paging);
		model.addAttribute("authors", authors);
		model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		model.addAttribute("reviews", reviews);
		return "single-product";
	}
}
