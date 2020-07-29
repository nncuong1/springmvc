package com.nnc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nnc.dao.AuthorDao;
import com.nnc.entity.Author;
import com.nnc.util.Paging;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorDao<Author> authorDao;

	public List<Author> getAllAuthor(Author author, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if(author!=null) {
			if (author.getId() != null && author.getId()!=0) {
				queryStr.append(" and model.id=:id");
				mapParams.put("id", author.getId());
			}
			if (author.getName() != null && !StringUtils.isEmpty(author.getName())) {
				queryStr.append(" and model.name like :name");
				mapParams.put("name", "%"+author.getName()+"%");
			}
			if (author.getCode()!= null && !StringUtils.isEmpty(author.getCode())) {
				queryStr.append(" and model.code=:code");
				mapParams.put("code", author.getCode());
			}
		}

		return authorDao.findAll(queryStr.toString(), mapParams, paging);
	}

	@Override
	public void saveAuthor(Author author) throws Exception {
		author.setActiveFlag(1);
		author.setCreateDate(new Date());
		author.setUpdateDate(new Date());
		authorDao.save(author);

	}

	@Override
	public void updateAuthor(Author author) throws Exception {
		author.setUpdateDate(new Date());
		authorDao.update(author);
	}

	@Override
	public void deleteAuthor(Author author) throws Exception {
		author.setActiveFlag(0);
		author.setUpdateDate(new Date());
		authorDao.update(author);

	}

	@Override
	public List<Author> findAuthor(String property, Object value) {
		return authorDao.findByProperty(property, value);
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
		return authorDao.getIdAuthorByBook(id);
	}

	@Override
	public List<String> getAuthorNamesByBookId(int id) {
		
		return authorDao.getAuthorNamesByBook(id);
	}

}
