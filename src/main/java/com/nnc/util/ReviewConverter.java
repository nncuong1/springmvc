package com.nnc.util;

import org.springframework.stereotype.Component;

import com.nnc.dto.ReviewDTO;
import com.nnc.entity.Product;
import com.nnc.entity.Review;
import com.nnc.entity.User;

@Component
public class ReviewConverter {
	
	public ReviewDTO toDTO(Review review) {
		ReviewDTO result = new ReviewDTO();
		result.setComment(review.getComment());
		result.setRating(review.getRating());
		result.setCustomerName(review.getCustomer().getName());
		result.setCreateDate(DateUtil.getDaysAgo(review.getCreateDate()));
		result.setProductId(String.valueOf(review.getProduct().getId()));
		return result;
	}
	
	public Review toEntity(ReviewDTO dto) {
		Review review = new Review();
		review.setComment(dto.getComment());
		review.setRating(dto.getRating());
		Product p = new Product();
		p.setId(Integer.parseInt(dto.getProductId()));
		review.setProduct(p);
		
		User u = new User();
		u.setId(Integer.parseInt(dto.getCustomerId()));
		review.setCustomer(u);
		return review;
	}
	
	public Review toEntity(Review review,ReviewDTO dto) {
		review.setComment(dto.getComment());
		review.setRating(dto.getRating());
		Product p = new Product();
		p.setId(Integer.parseInt(dto.getProductId()));
		review.setProduct(p);
		
		User u = new User();
		u.setId(Integer.parseInt(dto.getCustomerId()));
		review.setCustomer(u);
		return review;
	}
}
