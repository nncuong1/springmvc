package com.nnc.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nnc.dao.RoleDao;
import com.nnc.dao.UserDao;
import com.nnc.entity.Role;
import com.nnc.entity.User;
import com.nnc.util.Paging;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao<User> userDao;
	
	@Autowired
	private RoleDao<Role> roleDao;
	
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}
	
	public void saveUser(User user) {
		log.info("insert user "+user.toString());
		user.setActiveFlag(1);
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		if(!user.getMultipartFile().getOriginalFilename().isEmpty()) {
			String fileName = System.currentTimeMillis()+"_"+user.getMultipartFile().getOriginalFilename();
			try {
				processUploadFile(user.getMultipartFile(), fileName);
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
				log.error("Error insert user : "+e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Error insert user : "+e);
			}
			user.setImgUrl(fileName);
		}
		userDao.save(user);
	}


	public void updateUser(User user) {
		if(!user.getMultipartFile().getOriginalFilename().isEmpty()) {
			try {
				String fileName = System.currentTimeMillis()+"_"+user.getMultipartFile().getOriginalFilename();
				processUploadFile(user.getMultipartFile(), fileName);
				user.setImgUrl(fileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		userDao.update(user);
	}

	public void deleteUser(User user) {
		user.setActiveFlag(0);
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		userDao.update(user);
	}

	public List<User> findUserByProperty(String property, Object value) {
		return userDao.findByProperty(property, value);
	}

	public User findById(Integer id) {
		return userDao.findById(User.class, id);
	}
	
	public List<User> getAllUsers(String keyword, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if(keyword!=null) {
			queryStr.append(" and model.name like :name or model.username like :username");
			mapParams.put("name", "%"+keyword+"%");
			mapParams.put("username", "%"+keyword+"%");
		}
		List<User> users = userDao.findAll(queryStr.toString(), mapParams, paging);
		List<Role> roles = roleDao.getRolesForUsers();
		//log.info("Catching roles for user : "+roles);
		for(User u : users) {
			u.getRole();
			//log.info("user for role : "+u.getRole().getRoleName());
		}
		return users;
	}
	
	private void processUploadFile(MultipartFile multipartFile, String fileName) throws IllegalStateException, IOException {
		String path = "G:\\data\\uploadInventory\\user\\";
		if(!multipartFile.getOriginalFilename().isEmpty()) {
			File dir = new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
				log.info("create dir : "+path);
			}
			File file = new File(path,fileName);
			multipartFile.transferTo(file);
		}
	}

	@Override
	public void registerNewCustomer(User user) throws Exception {
		user.setActiveFlag(1);
		Role role = roleDao.findById(Role.class, 3);
		user.setRole(role);
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		userDao.save(user);
		
	}
}
