package com.nnc.dto;

import java.util.Map;

public class AuthForm {
	private int roleId;
	private int menuId;
	private int permission;
	private Map<Integer, Integer> mapAuth;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public Map<Integer, Integer> getMapAuth() {
		return mapAuth;
	}

	public void setMapAuth(Map<Integer, Integer> mapAuth) {
		this.mapAuth = mapAuth;
	}

}
