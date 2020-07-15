package com.nnc.dao;

import java.util.List;

import com.nnc.entity.User;

public interface UserDao<E> extends BaseDao<E> {
	public User findByUserName(String username);
	public List<User> getAllUsers();
	public List<User> getCustomerForReviews();
	public List<User> getCustomerForOrders();
}
