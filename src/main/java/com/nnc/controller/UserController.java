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

import com.nnc.entity.Role;
import com.nnc.entity.User;
import com.nnc.service.RoleService;
import com.nnc.service.UserService;
import com.nnc.util.Constant;
import com.nnc.util.Paging;
import com.nnc.validator.UserValidator;

@Controller
@RequestMapping("/admin")
public class UserController {
	
	private static Logger log =  Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserValidator userValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if(binder.getTarget().getClass()== User.class) {
			binder.setValidator(userValidator);
		}
	}
	
	@RequestMapping("/user/list")
	public String showUserList(Model model, HttpSession session,@RequestParam(name="keyword", required = false) String keyword) {
		Paging paging = new Paging(10);
		paging.setIndexPage(1);
		log.info("Get user ");
		List<User> users = userService.getAllUsers(keyword,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null) {
			model.addAttribute(Constant.MSG_SUCCESS,session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);;
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null) {
			model.addAttribute(Constant.MSG_ERROR,session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);;
		}
		model.addAttribute("pageInfor",paging);
		model.addAttribute("users", users);
		return "user-list";
	}
	
	@GetMapping("/user/add")
	public String addUser(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("titlePage", "Add user");
		List<Role> roles = roleService.getAllRoles(null, null);
		Map<String,String> mapRole = new HashMap<String, String>();
		for(Role role : roles) {
			mapRole.put(String.valueOf(role.getId()), role.getRoleName());
		}
		model.addAttribute("mapRole", mapRole);
		model.addAttribute("viewOnly",false);
		return "user-action";
	}
	
	@GetMapping("/user/edit/{id}")
	public String edit(Model model , @PathVariable("id") Integer id) {
		log.info("Edit user with id="+id);
		User user = userService.findById(id);
		if(user!=null) {
			List<Role> roles = roleService.getAllRoles(null, null);
			Map<String, String> mapRole = new HashMap<String,String>();
			for(Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			//UserRole userRole =(UserRole) user.getUserRoles().iterator().next();
			//user.setRoleID(userRole.getRole().getId());
			log.info("role theo user : "+user.getRole().getId());
			model.addAttribute("mapRole", mapRole);
			model.addAttribute("titlePage", "Edit Users");
			model.addAttribute("userForm", user);
			
		//	model.addAttribute("user", user);
			
			model.addAttribute("viewOnly", false);
			model.addAttribute("editMode", true);
			return "user-action";
		}
		return "redirect:/admin/user/list";
	}
	

	@GetMapping("/user/view/{id}")
	public String viewUser(Model model , @PathVariable("id") Integer id) {
		log.info("View user with id="+id);
		User user = userService.findById(id);
		if(user!=null) {
			Role role = roleService.findById(user.getRole().getId());
//			log.info("role id = "+user.getRole().getId());
//			log.info("role name = "+role.getRoleName());
			log.info("user image url = "+user.getImgUrl());
			model.addAttribute("role", role);
			model.addAttribute("titlePage", "View Users");
			model.addAttribute("userForm", user);
			model.addAttribute("viewOnly", true);
		//	model.addAttribute("user", user);
			model.addAttribute("editMode", true);
			return "user-action";
		}
		return "redirect:/admin/user/list";
	}
	
	@PostMapping("/user/save")
	public String save(Model model, @ModelAttribute("userForm") User user, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			log.error("Has error : "+result);
			if(user.getId()!=null) {
				model.addAttribute("titlePage","Edit User");
				//model.addAttribute("editMode",true);
			}else {
				model.addAttribute("titlePage","Add User");
			}
			List<Role> roles = roleService.getAllRoles(null, null);
			Map<String, String> mapRole = new HashMap<String,String>();
			for(Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);
			model.addAttribute("viewOnly", false);
			model.addAttribute("userForm", user);
			return "user-action";
		}
		if(user.getId()!=null && user.getId()!=0) {
			try {
				log.info("Update user with id : "+user.getId());
				userService.updateUser(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success");
			} catch (Exception e) {
				log.error("Loi update : "+e);
				session.setAttribute(Constant.MSG_ERROR, "Update error");
			}
		}else {
			try {
				log.info("Save user with username : "+user.getUsername());
				session.setAttribute(Constant.MSG_SUCCESS, "Add success");
				userService.saveUser(user);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Loi insert : "+e);
				session.setAttribute(Constant.MSG_ERROR, "Add error");
			}
		}
		return "redirect:/admin/user/list";
	}
	
	@GetMapping("/user/delete/{id}")
	public String delete(Model model , @PathVariable("id") Integer id,HttpSession session) {
		User user = userService.findById(id);
		if(user!=null) {
			log.info("Delete user with id : "+id);
			try {
				userService.deleteUser(user);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/admin/user/list";
	}
	
	@RequestMapping(path="/paging/user", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String showUsers(Model model, HttpSession session,@RequestParam(name="keyword", required = false) String keyword,
			@RequestParam(required = false) int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);
		List<User> users = userService.getAllUsers(keyword,paging);
		StringBuilder builder = new StringBuilder();
		for(User u: users) {
			builder.append("<tr>");
			builder.append("<th scope=\'row\'>"+(paging.getOffset()+users.indexOf(u)+1)+"</th>");
			if(u.getImgUrl()!=null && !u.getImgUrl().isEmpty()) {
				builder.append("<td> <img src='/inventory/files/user/"+u.getImgUrl()+"' width='50px' height='50px' /></td>");
			}else {
				builder.append("<td> No image </td>");
			}
			builder.append("<td>"+u.getName()+"</td>");
			builder.append("<td>"+u.getRole().getRoleName()+"</td>");
			builder.append("<td>");
			builder.append("<a href='/inventory/admin/user/view/"+u.getId()+"'> View </a>");
			builder.append("<a href='/inventory/admin/user/edit/"+u.getId()+"'> Edit </a>");
			builder.append("<a href='javascript:void(0);' onclick='confirmDelete("+u.getId()+")' > Delete</a>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}

}

