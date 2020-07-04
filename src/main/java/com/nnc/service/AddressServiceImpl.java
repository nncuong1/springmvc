package com.nnc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.AddressDao;
import com.nnc.entity.Address;


@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressDao<Address> addressDao;

	@Override
	public List<Address> findByCustomerId(int id) {
		List<Address> addresss = addressDao.findByCustomerId(id);
		return addresss;
	}

	@Override
	public Address findById(int id) {
		return addressDao.findById(Address.class, id);
	}

	@Override
	public Address getAddressDetailById(int id) {
		return addressDao.getAddressDetailById(id);
	}
}
