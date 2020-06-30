package com.nnc.service;

import java.util.List;

import com.nnc.dto.ReviewDTO;
import com.nnc.entity.Review;
import com.nnc.util.Paging;

public interface ReviewService {
	public void saveReview(ReviewDTO reviewDTO) throws Exception;
	public void updateReview(Review review) throws Exception;
	public void deleteReview(Review review) throws Exception;
	public List<Review> findReview(String property, Object value);
	public List<Review> getAllReview( Paging paging );
	public Review findById(int id);
	public List<ReviewDTO> findByProductId(int id, Paging paging);
	public void deleteReview(int id);
	public void changeStatus(int id);
}

