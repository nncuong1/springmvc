package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.Address;
import com.nnc.entity.Authority;
import com.nnc.entity.Province;

@Repository
public class AddressDaoImpl extends BaseDaoImpl<Address> implements AddressDao<Address> {

	@Override
	public List<Address> findByCustomerId(int id) {
		return sessionFactory.getCurrentSession().createQuery(
                " FROM address a WHERE a.customer.id = :id",
                Address.class).setParameter("id", id).getResultList();
	}
	
	
}
