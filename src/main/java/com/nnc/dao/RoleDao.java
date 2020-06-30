package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Role;
import com.nnc.entity.User;

public interface RoleDao<E> extends BaseDao<E> {
	public Role findByUserId(int id);
	public List<Role> getRolesForUsers();
	public List<Role> getAdminRoles();
}
