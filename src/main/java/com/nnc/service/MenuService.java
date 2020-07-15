package com.nnc.service;

import java.util.List;
import java.util.Map;

import com.nnc.entity.Authority;
import com.nnc.entity.Menu;
import com.nnc.entity.Role;
import com.nnc.util.Paging;

public interface MenuService {
	public List<Menu> getMenuForRole(List<Authority> auths);
	public List<Menu> findWithoutNPlusOne(Paging paging , String keyword);
	public Map<Integer, Integer> getMapAuths(List<Role> roles, List<Menu> menuList);
	public void changeStatus(int id);
	public void updatePermission(int permission, int menuId, int roleId);
}
