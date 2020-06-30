package com.nnc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.AuthorDao;
import com.nnc.entity.Author;
import com.nnc.util.Paging;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorDao<Author> authorDao;

	public List<Author> getAllAuthor(String keyword, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (keyword != null) {
			queryStr.append(" and model.name like :name or model.description like :description");
			mapParams.put("name", keyword);
			mapParams.put("description", keyword);
		}

		return authorDao.findAll(queryStr.toString(), mapParams, paging);
	}

	@Override
	public void saveAuthor(Author author) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAuthor(Author author) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAuthor(Author author) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Author> findAuthor(String property, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> findByBookId(int id) {
		
		return authorDao.findByBookId(id);
	}

	@Override
	public Author findById(int id) {
		
		return authorDao.findById(Author.class, id);
	}

	@Override
	public List<Integer> getIdAuthorByBook(int id) {
		// TODO Auto-generated method stub
		return authorDao.getIdAuthorByBook(id);
	}

	@Override
	public List<String> getAuthorNamesByBookId(int id) {
		
		return authorDao.getAuthorNamesByBook(id);
	}

}
