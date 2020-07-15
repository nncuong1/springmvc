package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao<User> {

	public User findByUserName(String username) {
		User user = (User) sessionFactory.getCurrentSession().createQuery("FROM user u JOIN FETCH u.role WHERE u.username = :username",User.class)
				.setParameter("username", username).getSingleResult();
		return user;
	}

	public List<User> getAllUsers() {
		
		return null;
	}
	
	public List<User> getCustomerForReviews() {
		//select distinct r from user u join role r on u.role = r.id
		List<User> users =sessionFactory.getCurrentSession().
				createQuery("select distinct u from review r join user u on r.customer = u.id",User.class).getResultList();	
		return users;
	}
	
	public List<User> getCustomerForOrders() {
		List<User> customers =sessionFactory.getCurrentSession().
				createQuery("select distinct u from orders o join user u on o.user = u.id",User.class).getResultList();	
		return customers;
	}

}
