package com.nnc.dao;

import org.springframework.stereotype.Repository;

import com.nnc.entity.Order;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao<Order> {

	@Override
	public Order getOrderDetailById(int id) {
		Order order =sessionFactory.getCurrentSession().
				createQuery(" from orders o join fetch o.user where o.id = :id ",Order.class).setParameter("id", id).getSingleResult();
		return order;
	}
}
