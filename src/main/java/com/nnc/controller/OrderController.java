package com.nnc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nnc.dto.OrderForm;
import com.nnc.entity.Menu;
import com.nnc.entity.Order;
import com.nnc.entity.OrderItem;
import com.nnc.entity.Product;
import com.nnc.entity.Role;
import com.nnc.service.OrderService;
import com.nnc.service.ProductService;
import com.nnc.util.Constant;
import com.nnc.util.MapStatus;
import com.nnc.util.Paging;

@Controller
//@SessionAttributes("cart")
public class OrderController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired OrderService orderService;
	
	@RequestMapping("/admin/order/list")
	public String showOrderList(Model model, HttpSession session,@ModelAttribute("orderForm") OrderForm orderForm) {
		Paging paging = new Paging(15);
		paging.setIndexPage(1);
		List<Order> orders = orderService.getAllOrder(paging, orderForm);
		Map<String, String> mapStatus = MapStatus.getInstance().getMapStatus();
		model.addAttribute("mapStatus",mapStatus);
		model.addAttribute("pageInfor",paging);
		model.addAttribute("orders", orders);
		return "order-list";
	}
	
	@RequestMapping(path="/admin/paging/order", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String pagingMenu(@RequestBody OrderForm orderForm,@RequestParam(required = false) Integer page) {
		Paging paging = new Paging(15);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		List<Order> orders = orderService.getAllOrder(paging, orderForm);
		StringBuilder builder = new StringBuilder();
		for(Order order : orders) {
			builder.append("<tr>");
			builder.append("<td scope='row'>"+(paging.getOffset()+orders.indexOf(order)+1)+"</td>");
			builder.append("<td>"+order.getUser().getId()+"</td>");
			builder.append("<td>");
			builder.append("<a href='javascript:void(0);' style='text-decoration:none' >"+order.getStatus()+"</a>");
			builder.append("</td>");
			builder.append("<td>"+order.getOrderDate()+"</td>");
			builder.append("<td>");
			builder.append("<a href='/inventory/admin/order/view/" + order.getId() + "'> Chi tiết đơn hàng </a>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}
	
	
	/*
	 * web client
	*/
	@GetMapping("/add_to_cart")
	@ResponseBody
	public String addToCart(@RequestParam(name = "productId") int id, @RequestParam(name = "qty",  required = false) Integer qty, HttpSession session) {
		Product product = productService.findById(id);
		Object obj = session.getAttribute(Constant.CART_INFOR);
		Map<Integer, OrderItem> map;
		if(obj==null) {
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity((qty==null)?1:qty);
			orderItem.setUnitPrice(product.getPrice());
			orderItem.setProduct(product);
			
			//Map<Integer, OrderItem> map = new HashMap<Integer, OrderItem>();
			map = new HashMap<Integer, OrderItem>();
			map.put(product.getId(), orderItem);
			
			session.setAttribute(Constant.CART_INFOR, map);
		}else {
			//Map<Integer, OrderItem> map = (Map<Integer, OrderItem>) obj;
			map = (Map<Integer, OrderItem>) obj;
			OrderItem orderItem = map.get(product.getId());
			
			if(orderItem==null) {
				OrderItem item = new OrderItem();
				item.setQuantity((qty==null)?1:qty);
				item.setUnitPrice(product.getPrice());
				item.setProduct(product);
				map.put(product.getId(), item);
			}else {
				orderItem.setQuantity((qty==null)?orderItem.getQuantity()+1:orderItem.getQuantity()+qty);
			}
			session.setAttribute(Constant.CART_INFOR,map);
		}
		//return "Them vao gio hang thanh cong";
		//return "redirect:/shop";
		return String.valueOf(map.size());
	}
	
	@GetMapping("/update_to_cart")
	@ResponseBody
	public String updateQuantity(@RequestParam(name = "productId") int id, @RequestParam(name = "qty",  required = false) Integer qty, HttpSession session) {
	//	Product product = productService.findById(id);
		Object obj = session.getAttribute(Constant.CART_INFOR);
		Map<Integer, OrderItem> map;
		if(obj!=null) {
			map = (Map<Integer, OrderItem>) obj;
			OrderItem orderItem = map.get(id);
			orderItem.setQuantity(qty);
			session.setAttribute(Constant.CART_INFOR, map);
		}
		return "";
	}
	
	@GetMapping("/remove_cartItem")
	@ResponseBody
	public String removeCartItem(HttpSession session, @RequestParam(name = "key") Integer key) {
		
		Object obj = session.getAttribute(Constant.CART_INFOR);
		if(obj!=null) {
			Map<Integer, OrderItem> map =(Map<Integer, OrderItem>) obj;
			
			// xoa theo key
			map.remove(key);
			
			// update cart
			session.setAttribute(Constant.CART_INFOR, map);
			return String.valueOf(map.size());
		}
		return "-1";
		//return "Da xoa san pham khoi gio hang";
		//return "redirect:/shop";
	}
	
	@GetMapping("/cart")
	public String viewCart(HttpSession session, Model model) {
		if(session.getAttribute(Constant.MSG_NO_ITEM)!=null) {
			model.addAttribute(Constant.MSG_NO_ITEM,session.getAttribute(Constant.MSG_NO_ITEM));
			session.removeAttribute(Constant.MSG_NO_ITEM);;
		}
		return "cart";
	}
	
}
