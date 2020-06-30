package com.nnc.service;

import java.util.List;

import com.nnc.dto.OrderForm;
import com.nnc.entity.Order;
import com.nnc.util.Paging;

public interface OrderService {
	public void saveOrder(Order order) throws Exception;
	public List<Order> getAllOrder	( Paging paging ,OrderForm orderForm);
	public Order findById(int id);
}
