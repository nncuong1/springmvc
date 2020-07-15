package com.nnc.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnc.dto.SearchWebForm;
import com.nnc.entity.Address;
import com.nnc.entity.District;
import com.nnc.entity.Order;
import com.nnc.entity.OrderItem;
import com.nnc.entity.OrderItemId;
import com.nnc.entity.Province;
import com.nnc.entity.User;
import com.nnc.service.AddressService;
import com.nnc.service.DistrictService;
import com.nnc.service.OrderItemService;
import com.nnc.service.OrderService;
import com.nnc.service.ProvinceService;
import com.nnc.util.Constant;

@Controller
public class CustomerController {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	ProvinceService provinceService;
	
	@Autowired
	DistrictService districtService;
	
	
	private static final Logger log = Logger.getLogger(ProductController.class);

	@PostMapping("/customer/order")
	public String customerPay(HttpSession session, @ModelAttribute("addressForm") Address address) throws ParseException {
		User customer = (User) session.getAttribute(Constant.CUSTOMER_INFOR);

		Object obj = session.getAttribute(Constant.CART_INFOR);
		Map<Integer, OrderItem> map = (Map<Integer, OrderItem>) obj;
		if (obj != null && map.size() > 0) {
			Order order = new Order();
			order.setActiveFlag(1);
			order.setUser(customer);
			order.setStatus(1);
			order.setCreateDate(new Date());
			order.setUpdateDate(new Date());
			//order.setAddress(address);
			if(address.getId()!=null && address.getId()!=0) {
				log.info("Customer use old address");
				Address oldAddress = addressService.findById(address.getId());
				order.setAddress(oldAddress);
			}else {
				Address newAddress = new Address();
				log.info("Customer use new address");
				newAddress.setActiveFlag(1);
				newAddress.setCreateDate(new Date());
				newAddress.setUpdateDate(new Date());
				Province province = new Province();
				province.setId(address.getProvinceId());
				newAddress.setProvince(province);
				District district = new District();
				district.setId(address.getDistrictId());
				newAddress.setDistrict(district);
				newAddress.setAddr(address.getAddr());
				newAddress.setPhone(address.getPhone());
				newAddress.setFullName(address.getFullName());
				newAddress.setCustomer(customer);
				
				order.setAddress(newAddress);
			}
			try {
				orderService.saveOrder(order);
			} catch (Exception e) {
				log.error("tai saso : "+e);
				e.printStackTrace();
			}
			for (Entry<Integer, OrderItem> entry : map.entrySet()) {
				OrderItem orderItem = entry.getValue();
				OrderItemId orderItemId = new OrderItemId();
				orderItemId.setOrderId(order.getId());
				orderItemId.setProductId(orderItem.getProduct().getId());
				//log.info("order id : "+order.getId());
				orderItem.setOrder(order);
				orderItem.setId(orderItemId);
				try {
					orderItemService.saveOrderItem(orderItem);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			session.removeAttribute("cart");
			return "redirect:/customer/order-success";
		} else {
			session.setAttribute(Constant.MSG_NO_ITEM, "Chua co san pham nao trong gio");
			return "redirect:/cart";
		}

	}
	

	@GetMapping("/customer/order-success")
	public String successOrder(HttpSession session, @ModelAttribute("search_mini_form") SearchWebForm searchForm) {
		//session.setAttribute(Constant.MSG_PREVIOUS_PAGE, "/customer/order-success");
		return "success-order";
	}
	
	@GetMapping("/customer/checkout")
	public String checkout(HttpSession session, Model model, @ModelAttribute("search_mini_form") SearchWebForm searchForm) {
		User customer = (User) session.getAttribute(Constant.CUSTOMER_INFOR);
		List<Address> addresses = addressService.findByCustomerId(customer.getId());
		List<Province> provinces = provinceService.findAll();
		List<District> districts = districtService.findAll();

		model.addAttribute("addressForm", new Address());
		model.addAttribute("addresses", addresses);
		model.addAttribute("provinces", provinces);
		model.addAttribute("districts", districts);

		return "checkout";
	}
}
