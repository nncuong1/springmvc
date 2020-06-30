package com.nnc.service;

import java.util.List;

import com.nnc.entity.Category;
import com.nnc.util.Paging;

public interface CategoryService {
	public void saveCategory(Category category) throws Exception;
	public void updateCategory(Category category) throws Exception;
	public void deleteCategory(Category category) throws Exception;
	public List<Category> findCategory(String property, Object value);
	public List<Category> getAllCategory(String keyword, Paging paging );
	public Category findById(int id);
	
}
