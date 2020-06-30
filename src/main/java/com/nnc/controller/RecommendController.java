package com.nnc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nnc.service.RecommendService;
import com.nnc.service.ReviewService;

@Controller
public class RecommendController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	RecommendService recommendService;
	
	@GetMapping("/admin/update-rating-file")
	public String updateRatingFile() {
		recommendService.updateCsvFile();
		return "history-list";
	}
}
