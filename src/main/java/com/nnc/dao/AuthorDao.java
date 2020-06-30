package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Author;

public interface AuthorDao<E> extends BaseDao<E> {
	
	List<Author> findByBookId(int id);
	public List<Integer> getIdAuthorByBook(int id);
	public List<String> getAuthorNamesByBook(int id);
}
