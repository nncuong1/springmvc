package com.nnc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nnc.dto.ReviewDTO;
import com.nnc.entity.Review;
import com.nnc.service.ReviewService;
import com.nnc.util.Constant;
import com.nnc.util.Paging;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/admin/review/list")
	public String menuList(Model model , HttpSession session) {
		Paging paging = new Paging(15);
		paging.setIndexPage(1);
		List<Review> reviews = reviewService.getAllReview(paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null) {
			model.addAttribute(Constant.MSG_SUCCESS,session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);;
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null) {
			model.addAttribute(Constant.MSG_ERROR,session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);;
		}
		model.addAttribute("pageInfor",paging);
		model.addAttribute("reviews",reviews);
		return "review-list";
	}
	
	@GetMapping("/admin/review/change-status/{id}")
	public String change(Model model, @PathVariable("id") Integer id, HttpSession session) {
		try {
			reviewService.changeStatus(id);
			session.setAttribute(Constant.MSG_SUCCESS, "Change status success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Change status has error!!!");
		}
		return "redirect:/admin/review/list";
	}
	
	@GetMapping("/admin/review/delete")
	@ResponseBody
	public String deleteSpamReview( @RequestParam int id, HttpSession session) {
		try {
			reviewService.deleteReview(id);
			return "Delete success!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Delete has error";
		}
	}
	
	@RequestMapping(path="/admin/paging/review", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String pagingReviewAdmin(Model model, @RequestParam(required = false) Integer page) {
		Paging paging = new Paging(15);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		List<Review> reviews = reviewService.getAllReview(paging);
		StringBuilder builder = new StringBuilder();
		for(Review review : reviews) {
			builder.append("<tr>");
			builder.append("<td scope='row'>"+(paging.getOffset()+reviews.indexOf(review)+1)+"</td>");
			builder.append("<td>"+review.getCustomer().getId()+"</td>");
			builder.append("<td>"+review.getComment()+"</td>");
			builder.append("<td>"+review.getProduct().getId()+"</td>");
			builder.append("<td>");
			builder.append("<a class='review_status' href='javascript:void(0);' style='text-decoration:none;color:#fff' onclick='confirmChange("+review.getId()+","+review.getActiveFlag()+",this"+")'>"+review.getActiveFlag()+"</a>");
			builder.append("</td>");
			builder.append("<td>"+review.getCommentDate()+"</td>");
			builder.append("<td>");
			builder.append("<a class='btn btn-rounded btn-danger' href='javascript:void(0);' style='text-decoration:none;color:#fff' onclick='confirmDelete("+review.getId()+","+"this"+")'>"+"Xóa bình luận"+"</a>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}
	
	/*
	 * web client
	 * */
	@RequestMapping(path="/paging/review", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String showReviewsWeb(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer productId) {
		Paging paging = new Paging(5);
		if(page==null) {
			paging.setIndexPage(1);
		}else {
			paging.setIndexPage(page);
		}
		List<ReviewDTO> reviews = reviewService.findByProductId(productId,paging);
		StringBuilder builder = new StringBuilder();
		for(ReviewDTO r : reviews) {
			builder.append("<li>");
			builder.append("<div class='wn__comment'>");
			builder.append("<div class='thumb'>");
			builder.append("<img src='/inventory/static/web/images/blog/comment/1.jpeg' alt='comment images' >");
			builder.append("</div>");
			builder.append("<div class='content'>");
			builder.append("<div class='comnt__author d-block d-sm-flex'>");
			
			builder.append("<span class='author_name'>"+r.getCustomerName()+"</span> ");
			builder.append("<span class='rate'>"+r.getRating()+"</span> ");
			builder.append("<span>"+r.getCreateDate()+"</span>");
			builder.append("</div>");
			builder.append("<p>"+r.getComment()+"</p>");
			
			builder.append("</div>");
			builder.append("</div>");
			builder.append("</li>");
		}
		return builder.toString();
	}
	
	
}
