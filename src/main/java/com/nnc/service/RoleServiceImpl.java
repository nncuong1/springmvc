package com.nnc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.RoleDao;
import com.nnc.entity.Role;
import com.nnc.util.Paging;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao<Role> roleDao;

//	public Role findByUserId(int id) {
//		return roleDao.findByUserId(id);
//	}

	public Role findById(int id) {
		return roleDao.findById(Role.class, id);
	}

	public List<Role> getAllRoles(String keyword, Paging paging) {
		
		return roleDao.findAll(null, null, null);
	}

	@Override
	public List<Role> getAdminRoles() {
		return roleDao.getAdminRoles();
	}

	
}
