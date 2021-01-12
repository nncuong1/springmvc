package com.nnc.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnc.dao.DistrictDao;
import com.nnc.entity.District;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictDao<District> districtDao;

	@Override
	public List<District> findAll() {
		return districtDao.findAll();
	}

}
