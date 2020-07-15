package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.Address;

@Repository
public class AddressDaoImpl extends BaseDaoImpl<Address> implements AddressDao<Address> {

	@Override
	public List<Address> findByCustomerId(int id) {
		return sessionFactory.getCurrentSession().createQuery(
                " FROM address a WHERE a.customer.id = :id",
                Address.class).setParameter("id", id).getResultList();
	}

	@Override
	public Address getAddressDetailById(int id) {
		Address addrs =sessionFactory.getCurrentSession().
				createQuery(" from address a join fetch a.province join fetch a.district join fetch a.customer where a.id = :id ",Address.class).setParameter("id", id).getSingleResult();
		return addrs;
	}
	
	
}
