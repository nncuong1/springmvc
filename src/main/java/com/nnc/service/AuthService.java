package com.nnc.service;

import java.util.List;

import com.nnc.entity.Authority;

public interface AuthService {
	public List<Authority> getAuthByRoleId(int roleId);
}
