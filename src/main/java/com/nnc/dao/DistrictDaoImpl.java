package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.District;

@Repository
public class DistrictDaoImpl extends BaseDaoImpl<District> implements DistrictDao<District> {
	
	@Override
	public List<District> findAll() {
		List<District> districts = sessionFactory.getCurrentSession().createQuery("from district d order by d.id asc",District.class).getResultList();
		return districts;
	}
}
