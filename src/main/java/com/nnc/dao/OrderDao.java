package com.nnc.dao;

import com.nnc.entity.Order;

public interface OrderDao<E> extends BaseDao<E> {
	public Order getOrderDetailById(int id);
}
