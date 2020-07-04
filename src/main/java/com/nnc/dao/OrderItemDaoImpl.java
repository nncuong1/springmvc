package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.OrderItem;
import com.nnc.entity.Product;

@Repository
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem> implements OrderItemDao<OrderItem> {

	@Override
	public List<OrderItem> findByOrderId(int id) {
		List<OrderItem> items =sessionFactory.getCurrentSession().
				createQuery(" from OrderItem oi join fetch oi.product where oi.order.id = :id ",OrderItem.class).setParameter("id", id).getResultList();
		return items;
	}

}
