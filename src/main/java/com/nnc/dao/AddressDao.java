package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Address;

public interface AddressDao<E> extends BaseDao<E> {
	public List<Address> findByCustomerId(int id);
}
