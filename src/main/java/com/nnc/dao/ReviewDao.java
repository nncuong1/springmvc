package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Review;
import com.nnc.util.Paging;

public interface ReviewDao<E> extends BaseDao<E> {
	public List<Review> findByProductId(Integer id,Paging paging);
	public List<Object[]> getDataForCsvFile();
	public List<Review> getAllReview(Paging paging);
	public void deleteReview(int id);
}
