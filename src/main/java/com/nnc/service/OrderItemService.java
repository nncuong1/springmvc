package com.nnc.service;

import java.util.List;

import com.nnc.entity.OrderItem;

public interface OrderItemService {
	public void saveOrderItem(OrderItem orderItem) throws Exception;
	public List<OrderItem> findByOrderId(int id);
}
