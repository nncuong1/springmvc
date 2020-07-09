package com.nnc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nnc.service.RecommendService;
import com.nnc.service.ReviewService;
import com.nnc.util.Constant;

@Controller
public class RecommendController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	RecommendService recommendService;
	
	@GetMapping("/admin/update-rating-file")
	public String updateRatingFile(HttpSession session) {
		//recommendService.updateCsvFile();
		//return "history-list";
		try {
			recommendService.updateCsvFile();
			session.setAttribute(Constant.MSG_SUCCESS, "Cap nhat danh sach goi y thanh cong!!!");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Cap nhat that bai!!!");
		}
		return "redirect:/admin/review/list";
	}
}
