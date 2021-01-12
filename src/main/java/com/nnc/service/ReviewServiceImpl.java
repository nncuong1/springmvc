package com.nnc.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnc.dao.ProductDao;
import com.nnc.dao.ReviewDao;
import com.nnc.dao.UserDao;
import com.nnc.dto.ReviewDTO;
import com.nnc.entity.Product;
import com.nnc.entity.Review;
import com.nnc.entity.User;
import com.nnc.util.DateUtil;
import com.nnc.util.Paging;
import com.nnc.util.ReviewConverter;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDao<Review> reviewDao;
	
	@Autowired
	private UserDao<User> userDao;
	
	@Autowired
	private ProductDao<Product> productDao;
	
	@Autowired
	private ReviewConverter reviewConverter;

	public void saveReview(ReviewDTO reviewDTO) {
		Review review = reviewConverter.toEntity(reviewDTO);
		review.setActiveFlag(0);
		review.setCreateDate(new Date());
		review.setUpdateDate(new Date());
		reviewDao.save(review);
	} 

	public void updateReview(Review review)  {
		review.setUpdateDate(new Date());
		reviewDao.update(review);
	}

	public void deleteReview(Review review)  {
		review.setActiveFlag(0);
		review.setUpdateDate(new Date());
		reviewDao.update(review);
	}

	public List<Review> findReview(String property, Object value) {
		return reviewDao.findByProperty(property, value);
	}

	public Review findById(int id) {
		
		return reviewDao.findById(Review.class, id);
	}

	@Override
	public List<ReviewDTO> findByProductId(int id, Paging paging) {
		List<Review> reviews = reviewDao.findByProductId(id,paging);
	//	List<User> customers = userDao.getCustomerForReviews();
		List<ReviewDTO> reviewDTO = new ArrayList<ReviewDTO>();
		for(Review r : reviews) {
		//	r.getCustomer();
			ReviewDTO dto = reviewConverter.toDTO(r);
			reviewDTO.add(dto);
		}
		return reviewDTO;
	}

	@Override
	public List<Review> getAllReview(Paging paging) {
		List<Review> reviews =  reviewDao.getAllReview(paging);
		for(Review r : reviews) {
			try {
				r.setCommentDate(DateUtil.formatDate(r.getCreateDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return reviews;
	}

	@Override
	public void deleteReview(int id) {
		reviewDao.deleteReview(id);
	}
	
	@Override
	public void changeStatus(int id) {
		Review review = reviewDao.findById(Review.class, id);
		if (review != null) {
			review.setActiveFlag(review.getActiveFlag() == 1 ? 0 : 1);
			review.setUpdateDate(new Date());
			reviewDao.update(review);
		}
		
	}	
}
