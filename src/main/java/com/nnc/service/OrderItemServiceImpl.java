package com.nnc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.OrderDao;
import com.nnc.dao.OrderItemDao;
import com.nnc.entity.Order;
import com.nnc.entity.OrderItem;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemDao<OrderItem> orderItemDao;

	@Override
	public void saveOrderItem(OrderItem orderItem) throws Exception {
		orderItemDao.save(orderItem);
	}

	@Override
	public List<OrderItem> findByOrderId(int id) {
		return orderItemDao.findByOrderId(id);
	}

}
