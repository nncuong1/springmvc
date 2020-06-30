package com.nnc.service;

import java.util.List;

import com.nnc.entity.Role;
import com.nnc.util.Paging;

public interface RoleService {
	
//	public Role findByUserId(int id);
	public Role findById(int id);
	public List<Role> getAllRoles(String keyword, Paging paging);
	public List<Role> getAdminRoles();
}
