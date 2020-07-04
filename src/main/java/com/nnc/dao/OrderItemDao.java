package com.nnc.dao;

import java.util.List;

import com.nnc.entity.OrderItem;

public interface OrderItemDao<E> extends BaseDao<E> {
	List<OrderItem> findByOrderId(int id);
}
