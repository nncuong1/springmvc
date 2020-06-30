package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.Province;

@Repository
public class ProvinceDaoImpl extends BaseDaoImpl<Province> implements ProvinceDao<Province> {
	
	@Override
	public List<Province> findAll() {
//		String hql = "from province";
//		Query<Province> query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Province> provinces = sessionFactory.getCurrentSession().createQuery("from province p order by p.id asc",Province.class).getResultList();
		return provinces;
	}
}
