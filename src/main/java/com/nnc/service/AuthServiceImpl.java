package com.nnc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.AuthDao;
import com.nnc.entity.Author;
import com.nnc.entity.Authority;
import com.nnc.entity.Author;
import com.nnc.util.Paging;

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
