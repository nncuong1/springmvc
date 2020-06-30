package com.nnc.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.OrderDao;
import com.nnc.dao.UserDao;
import com.nnc.dto.OrderForm;
import com.nnc.entity.Order;
import com.nnc.entity.User;
import com.nnc.util.DateUtil;
import com.nnc.util.Paging;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao<Order> orderDao;
	
	@Autowired
	private UserDao<User> userDao;

	@Override
	public void saveOrder(Order order) throws Exception {
		orderDao.save(order);
	}

	@Override
	public List<Order> getAllOrder(Paging paging,OrderForm orderForm) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if(orderForm!=null) {
			if(orderForm.getStatusType()!=null && Integer.parseInt(orderForm.getStatusType())!=0) {
				queryStr.append(" and model.status = :status");
				mapParams.put("status",Integer.parseInt(orderForm.getStatusType()));
			}
			if(orderForm.getCustomerId()!=null && orderForm.getCustomerId()!=0) {
				queryStr.append(" and model.user.id = :userId");
				mapParams.put("userId",orderForm.getCustomerId());
			}
			if(orderForm.getFromDate()!=null) {
				queryStr.append(" and model.updateDate >= :fromDate");
				mapParams.put("fromDate",orderForm.getFromDate());
			}
			if(orderForm.getToDate()!=null) {
				queryStr.append(" and model.updateDate <= :toDate");
				mapParams.put("toDate",orderForm.getToDate());
			}
		}
		queryStr.append(" order by model.status asc, model.createDate desc");
		List<Order> orders = orderDao.findAll(queryStr.toString(), mapParams, paging);
		List<User> customers = userDao.getCustomerForOrders();
		for(Order order : orders) {
			order.getUser();
			try {
				order.setOrderDate(DateUtil.formatDate(order.getCreateDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return orders;
	}

	@Override
	public Order findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
