package com.nnc.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.nnc.entity.Authority;

@Repository
public class AuthDaoImpl extends BaseDaoImpl<Authority> implements AuthDao<Authority> {

	public List<Authority> getAuthByRoleId(int roleId) {
		return sessionFactory.getCurrentSession().createQuery(
                "SELECT a FROM authority a JOIN FETCH a.menu WHERE a.role.id = :roleId",
                Authority.class).setParameter("roleId", roleId).getResultList();
	}
	
	@Override
	public Authority findByRoleIdAndMenuId(int roleId, int menuId) {
		String hql ="from authority model where model.role.id=:roleId and model.menu.id=:menuId";
		Query<Authority> query =  sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("roleId", roleId);
		query.setParameter("menuId", menuId);
		List<Authority> auths = query.getResultList();
		if(!CollectionUtils.isEmpty(auths)) {
			return auths.get(0);
		}
		return null;
	}

	@Override
	public List<Authority> getAuthByMenuId(int menuId) {
		return sessionFactory.getCurrentSession().createQuery(
                "from authority a where a.menu.id=:menuId",
                Authority.class).setParameter("menuId", menuId).getResultList();
	}

}
