package com.nnc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.ProvinceDao;
import com.nnc.entity.Province;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	private ProvinceDao<Province> provinceDao;

	@Override
	public List<Province> findAll() {
		return provinceDao.findAll();
	}

}
