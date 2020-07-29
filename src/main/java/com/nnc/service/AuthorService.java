package com.nnc.service;

import java.util.List;

import com.nnc.entity.Author;
import com.nnc.util.Paging;

public interface AuthorService {
	public void saveAuthor(Author author) throws Exception;
	public void updateAuthor(Author author) throws Exception;
	public void deleteAuthor(Author author) throws Exception;
	public List<Author> findAuthor(String property, Object value);
	public List<Author> findByBookId(int id);
	public List<Author> getAllAuthor(Author author, Paging paging );
	public Author findById(int id);
	public List<Integer> getIdAuthorByBook(int id);
	public List<String> getAuthorNamesByBookId(int id);
}
