package com.nnc.service;

import java.util.List;

import com.nnc.entity.Address;

public interface AddressService {
	public List<Address> findByCustomerId(int id);
	public Address findById(int id);
	public Address getAddressDetailById(int id);
}
