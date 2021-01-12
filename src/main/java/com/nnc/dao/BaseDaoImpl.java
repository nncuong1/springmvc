package com.nnc.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nnc.util.Paging;

@Repository
@Transactional
public class BaseDaoImpl<E> implements BaseDao<E>{
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<E> findAll(String queryStr, Map<String,Object> mapParams, Paging paging) {
		StringBuilder queryString = new StringBuilder();
		StringBuilder countQuery = new StringBuilder();
		countQuery.append(" select count(*) from ").append(getGenericName()).append(" as model where model.activeFlag = 1");
		
		queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1");
		if(queryStr!=null && !queryStr.isEmpty()) {
			queryString.append(queryStr);
			countQuery.append(queryStr);
		} 
		Query<E> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		Query<E> countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString());
		if(mapParams!=null && !mapParams.isEmpty()) {
			for(String key : mapParams.keySet()) {
				query.setParameter(key,   mapParams.get(key) );
				countQ.setParameter(key,  mapParams.get(key) );
			}
		}
		if(paging!=null) {
			query.setFirstResult(paging.getOffset()); // bat dau tu dau (0)
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (Long) countQ.uniqueResult();
			paging.setTotalRows(totalRecords);
		}
		return query.getResultList();
	//	return sessionFactory.getCurrentSession().createQuery(queryString.toString()).getResultList();
	}

	public E findById(Class<E> e, Serializable id) {
		return sessionFactory.getCurrentSession().get(e, id);
	}

	public List<E> findByProperty(String property, Object value) {
		StringBuilder queryString = new StringBuilder("");
		queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1 and model.").append(property).append(" = :").append(property);
		Query<E> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		//query.setParameter(0, value);
		query.setParameter(property,value);
		return query.getResultList();
	}

	public void save(E instance) {
		sessionFactory.getCurrentSession().save(instance);
	}

	public void update(E instance) {
		sessionFactory.getCurrentSession().merge(instance);
	}
	
	public String getGenericName() {
		String s = getClass().getGenericSuperclass().toString();
		Pattern pattern = Pattern.compile("\\<(.*?)\\>");
		Matcher m = pattern.matcher(s);
		String generic="null";
		if(m.find()) {
			generic = m.group(1);
		}
		return generic;
	}
}
