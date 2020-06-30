package com.nnc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.nnc.entity.Author;

@Repository
public class AuthorDaoImpl extends BaseDaoImpl<Author> implements AuthorDao<Author> {

	@Override
	public List<Author> findByBookId(int id) {
//		List<Author> authors =sessionFactory.getCurrentSession().
//				createQuery("from author a join a.products p where p.id = :id",Author.class).setParameter("id", id).getResultList();
		List<Author> authors =sessionFactory.getCurrentSession().
				createQuery("select a from author a join a.products p where p.id = :id",Author.class).setParameter("id", id).getResultList();
		return authors;
	}
	
	@Override
	public List<Integer> getIdAuthorByBook(int id) {
		List<Integer> rs = new ArrayList<Integer>();
		Query q  =sessionFactory.getCurrentSession().createQuery("select a.id from author a join a.products p where p.id = :id").setParameter("id", id);
		List<Object> listResult = q.list();
		for (Object row : listResult) {
		    rs.add((Integer) row);
		}
		return rs;
	}
	
	@Override
	public List<String> getAuthorNamesByBook(int id) {
		List<String> rs = new ArrayList<String>();
		Query q  =sessionFactory.getCurrentSession().createQuery("select a.name from author a join a.products p where p.id = :id").setParameter("id", id);
		List<Object> listResult = q.list();
		for (Object row : listResult) {
		    rs.add((String) row);
		}
		return rs;
	}
}
