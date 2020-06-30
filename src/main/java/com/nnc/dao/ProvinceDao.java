package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Province;

public interface ProvinceDao<E> extends BaseDao<E> {
	public List<Province> findAll();
}
