package com.nnc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nnc.dto.ReviewDTO;
import com.nnc.service.ReviewService;
import com.nnc.util.Paging;

@RestController
//@RequestMapping("/admin/api")
public class ApiController {	
	
//	@GetMapping("/check")
//	@ResponseBody
//	public String testApi() {
//		return "Hello world";
//	}
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/api/reviews")
	public void createNew(@RequestBody ReviewDTO reviewDTO) {
		try {
			reviewService.saveReview(reviewDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@GetMapping("/api/reviews")
	public List<ReviewDTO> getReviewDTOs(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer productId) {
		Paging paging = new Paging(5);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		List<ReviewDTO> reviews = reviewService.findByProductId(productId,paging);
		return reviews;
	}	
}
