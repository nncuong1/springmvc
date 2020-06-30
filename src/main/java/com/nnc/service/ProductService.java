package com.nnc.service;

import java.util.List;

import com.nnc.entity.Product;
import com.nnc.util.Paging;

public interface ProductService {
	public void saveProduct(Product Product) throws Exception;
	public void updateProduct(Product Product) throws Exception;
	public void deleteProduct(Product Product) throws Exception;
	public List<Product> findProduct(String property, Object value);
	public List<Product> findProductByProperty(String property, Object value);
	public List<Product> getAllProduct(String keyword, Paging paging );
	public Product findById(int id);
	public List<Product> findBookByAuthorId(int id);
	public List<Product> findBookByCategoryId(int id, Paging paging);
	public Product findBookDetailById(int id);
	public List<Product> getRecommendedProducts(List<Long> multiId);
	public List<Product> getRecommendedProducts(int productId, int categoryId) throws Exception;
	public List<Product> getRecommendedProducts(Integer userId, int productId, int categoryId) throws Exception;
	public List<Product> getRelatedProduct(int productId, int categoryId);
}
