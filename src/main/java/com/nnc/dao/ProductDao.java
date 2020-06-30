package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Product;
import com.nnc.util.Paging;

public interface ProductDao<E> extends BaseDao<E> {
	public List<Product> findBookByAuthorId(int id);	
	public List<Product> findBookByCategoryId(int id,Paging paging);
	public Product findBookDetailById(int id);
	public List<Product> getRecommendedProducts(List<Long> multiId);
	public List<Product> getRelatedProduct(int productId, int categoryId);
	public List<Product> getProductForReviews();
}
