package com.nnc.dao;

import java.util.List;

import com.nnc.entity.Authority;

public interface AuthDao<E> extends BaseDao<E> {
	public List<Authority> getAuthByRoleId(int roleId);
	//public List<Authority> getAuthorityForMenu();
	public Authority findByRoleIdAndMenuId(int roleId, int menuId);
}
