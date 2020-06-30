package com.nnc.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nnc.dto.AuthForm;
import com.nnc.entity.Authority;
import com.nnc.entity.Menu;
import com.nnc.entity.Role;
import com.nnc.service.MenuService;
import com.nnc.service.RoleService;
import com.nnc.util.Constant;
import com.nnc.util.Paging;

@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/admin/menu/list")
	public String menuList(Model model, @RequestParam(name="keyword", required = false) String keyword, HttpSession session) {
		Paging paging = new Paging(15);
		paging.setIndexPage(1);
		List<Menu> menuList = menuService.findWithoutNPlusOne(paging, keyword);
		List<Role> roles = roleService.getAdminRoles();
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null) {
			model.addAttribute(Constant.MSG_SUCCESS,session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);;
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null) {
			model.addAttribute(Constant.MSG_ERROR,session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);;
		}
		setMapAuth(roles, menuList);
		model.addAttribute("menuList",menuList);
		model.addAttribute("roles",roles);
		model.addAttribute("pageInfor",paging);
		return "menu-list";
	}
	
	@GetMapping("/admin/menu/change-status/{id}")
	public String change(Model model, @PathVariable("id") int id, HttpSession session) {
		try {
			menuService.changeStatus(id);
			session.setAttribute(Constant.MSG_SUCCESS, "Change status success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Change status has error!!!");
		}
		return "redirect:/admin/menu/list";
	}
	
	@PostMapping("/admin/menu/update-permission")
	public String updatePermission(Model model,HttpSession session,@ModelAttribute("authForm") AuthForm authForm ) {
		try {
			menuService.updatePermission(authForm);
			session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Update has error!!!");
		}
		return "redirect:/admin/menu/list";
	}
	
	@GetMapping("/admin/menu/permission")
	public String updatePermission(Model model) {
		model.addAttribute("authForm", new AuthForm());
		initSelecbox(model);
		return "menu-permission";
	}
	
	@RequestMapping(path="/admin/paging/menu", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String pagingMenu(Model model, @RequestParam(name="keyword", required = false) String keyword ,@RequestParam(required = false) Integer page) {
		Paging paging = new Paging(15);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		List<Menu> menuList = menuService.findWithoutNPlusOne(paging, keyword);
		List<Role> roles = roleService.getAdminRoles();
		setMapAuth(roles, menuList);
		StringBuilder builder = new StringBuilder();
		for(Menu menu : menuList) {
			builder.append("<tr>");
			builder.append("<td scope='row'>"+(paging.getOffset()+menuList.indexOf(menu)+1)+"</td>");
			builder.append("<td>"+menu.getUrl()+"</td>");
			builder.append("<td>");
			builder.append("<a href='javascript:void(0);' style='text-decoration:none' onclick='confirmChange("+menu.getId()+","+menu.getActiveFlag()+")'>"+menu.getActiveFlag()+"</a>");
			builder.append("</td>");
			for(Entry<Integer, Integer> entry:menu.getMapAuth().entrySet()) {
				builder.append("<td class='permission-"+entry.getValue()+"'><i></i></td>");
			}
			builder.append("</tr>");
		}
		return builder.toString();
	}
	
	private void initSelecbox(Model model) {
		List<Role> roles = roleService.getAdminRoles();
		List<Menu> menus = menuService.findWithoutNPlusOne(null, null);
		Map<Integer, String> mapRole = new HashMap<>();
		Map<Integer, String> mapMenu = new HashMap<>();
		for(Role role :roles) {
			mapRole.put(role.getId(), role.getRoleName());
		}
		for(Menu menu:menus) {
			mapMenu.put(menu.getId(), menu.getUrl());
		}
		model.addAttribute("mapRole", mapRole);
		model.addAttribute("mapMenu", mapMenu);
	}
	
	private void setMapAuth(List<Role> roles, List<Menu> menuList) {
		Collections.sort(roles, (r1,r2)->r1.getId() - r2.getId());
		for(Menu menu : menuList) {
			Map<Integer, Integer> mapAuth = new TreeMap<Integer, Integer>();
			for(Role role : roles) {
				mapAuth.put(role.getId(), 0);
			}
			for(Object obj : menu.getAuths()) {
				Authority auth = (Authority) obj;
				mapAuth.put(auth.getRole().getId(), auth.getPermission());
			}
			menu.setMapAuth(mapAuth);
		}
	}
}
