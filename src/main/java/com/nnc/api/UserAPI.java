package com.nnc.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserAPI {
	
	@Autowired
	private RestTemplate restTemplate;
//	
//	@GetMapping(path="/api/user")
//	public List<User> showCategories(Model model, HttpSession session,@RequestParam(name="keyword", required = false) String keyword,
//			@RequestParam(required = false) int page) {
//		Paging paging = new Paging(10);
//		paging.setIndexPage(page);
//		List<User> users = userService.getAllUsers(keyword,paging);
//		return users;
//	}
	@CrossOrigin(origins = "https://thongtindoanhnghiep.co/api/city")
	@RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public List<City> getCities(){
        String uri = "https://thongtindoanhnghiep.co/api/city";
        City[] cities = restTemplate.getForObject(uri, City[].class);
       // City city = restTemplate.postForObject(uri,brokerRequest,City.class);
        return Arrays.asList(cities);
    }
}
