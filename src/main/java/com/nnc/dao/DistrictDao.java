package com.nnc.dao;

import java.util.List;

import com.nnc.entity.District;

public interface DistrictDao<E> extends BaseDao<E> {
	public List<District> findAll();
}
