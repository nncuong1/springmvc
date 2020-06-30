package com.nnc.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.nnc.entity.Menu;
import com.nnc.util.Paging;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao<Menu> {

	public Menu findByUserId(int userId) {
		Menu Menu =(Menu)sessionFactory.getCurrentSession().createQuery("select r from Menu r join fetch r.users where r.id = :id ", Menu.class)
		.setParameter("id", userId).getSingleResult();
		return Menu;
	}

	@Override
	public List<Menu> findWithoutNPlusOne(Paging paging, String queryStr,  Map<String,Object> mapParams) {
		StringBuilder queryString = new StringBuilder();
		StringBuilder countQuery = new StringBuilder();
		countQuery.append(" select count(*) from menu m");
		
		queryString.append("select m from menu m join fetch m.auths");
		//queryString.append(" select m from menu m left outer join ");
		if(queryStr!=null && !queryStr.isEmpty()) {
			queryString.append(queryStr);
			countQuery.append(queryStr);
		} 
		Query<Menu> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		Query<Long> countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString());
		if(mapParams!=null && !mapParams.isEmpty()) {
			for(String key : mapParams.keySet()) {
				query.setParameter(key, "%" + mapParams.get(key) + "%");
				countQ.setParameter(key, "%" + mapParams.get(key) + "%");
			}
		}
		if(paging!=null) {
			query.setFirstResult(paging.getOffset()); // bat dau tu dau (0)
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (Long) countQ.uniqueResult();
			paging.setTotalRows(totalRecords);
		}
		return query.getResultList();
	}
}
