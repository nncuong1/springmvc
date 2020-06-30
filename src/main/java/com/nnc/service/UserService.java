package com.nnc.service;

import java.util.List;

import com.nnc.entity.User;
import com.nnc.util.Paging;

public interface UserService {
	
	public User findByUserName(String userName);
	public void saveUser(User user) throws Exception;
	public void updateUser(User user) throws Exception;
	public void deleteUser(User user) throws Exception;
	public List<User> findUserByProperty(String property, Object value);
	public List<User> getAllUsers(String keyword, Paging paging);
	public User findById(Integer id);
	public void registerNewCustomer(User user) throws Exception;
}
