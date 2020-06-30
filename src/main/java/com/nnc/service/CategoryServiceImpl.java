package com.nnc.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.CategoryDao;
import com.nnc.entity.Category;
import com.nnc.util.Paging;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDao<Category> categoryDao;

	public void saveCategory(Category category) {
		category.setActiveFlag(1);
		if(category.getDescription()==null || category.getDescription().isEmpty()) {
			category.setDescription(null);
		}
		category.setCreateDate(new Timestamp(System.currentTimeMillis()));
		category.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		categoryDao.save(category);
	} 

	public void updateCategory(Category category)  {
		category.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		categoryDao.update(category);
	}

	public void deleteCategory(Category category)  {
		category.setActiveFlag(0);
		category.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		categoryDao.update(category);
	}

	public List<Category> findCategory(String property, Object value) {
		return categoryDao.findByProperty(property, value);
	}

	public List<Category> getAllCategory(String keyword, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams= new HashMap<String, Object>();
		if(keyword!=null) {
			queryStr.append(" and model.name like :name or model.description like :description");
			mapParams.put("name", "%"+keyword+"%");
			mapParams.put("description", "%"+keyword+"%");
		}
		
		return categoryDao.findAll(queryStr.toString(),mapParams, paging);
	}

	public Category findById(int id) {
		
		return categoryDao.findById(Category.class, id);
	}
	
}
