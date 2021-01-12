package com.nnc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnc.dao.AuthDao;
import com.nnc.entity.Authority;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthDao<Authority> authDao;

	@Override
	public List<Authority> getAuthByRoleId(int roleId) {
		return authDao.getAuthByRoleId(roleId);
	}

}
