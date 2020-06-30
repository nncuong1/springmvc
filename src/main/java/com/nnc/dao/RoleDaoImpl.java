package com.nnc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nnc.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao<Role> {

	public Role findByUserId(int userId) {
		Role role =(Role)sessionFactory.getCurrentSession().createQuery("select r from role r join fetch r.users where r.id = :id ", Role.class)
		.setParameter("id", userId).getSingleResult();
		return role;
	}

	public List<Role> getRolesForUsers() {
		List<Role> roles =sessionFactory.getCurrentSession().
				createQuery("select distinct r from user u join role r on u.role = r.id",Role.class).getResultList();	
		return roles;
	}

	@Override
	public List<Role> getAdminRoles() {
		return sessionFactory.getCurrentSession().
				createQuery("from role r where r.id != 3 and r.activeFlag= 1",Role.class).getResultList();
	}
}
