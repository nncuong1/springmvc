package com.nnc.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnc.dao.AuthDao;
import com.nnc.dao.MenuDao;
import com.nnc.dto.AuthForm;
import com.nnc.entity.Authority;
import com.nnc.entity.Menu;
import com.nnc.entity.Role;
import com.nnc.util.Paging;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao<Menu> menuDao;
	@Autowired
	private AuthDao<Authority> authDao;

	public List<Menu> getMenuForRole(List<Authority> auths) {
		// Role role = roleDao.findById(Role.class, roleId);
		List<Menu> menuList = new ArrayList<Menu>();
		// khoi tao menu list con
		List<Menu> menuChildList = new ArrayList<Menu>();
		// List<Authority> auths =authDao.getAuthByRoleId(roleId);

		// lay danh sach auth theo role , menu theo auth
		for (Authority auth : auths) {
			// Authority auth = (Authority) obj;
			Menu menu = auth.getMenu();
			if (menu.getParentId() == 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1
					&& auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
				menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
				menuList.add(menu);
			} else if (menu.getParentId() != 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1
					&& auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
				menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
				menuChildList.add(menu);
			}
		}
		for (Menu menu : menuList) {
			List<Menu> childList = new ArrayList<Menu>();
			for (Menu childMenu : menuChildList) {
				if (childMenu.getParentId() == menu.getId()) {
					childList.add(childMenu);
				}
			}
			menu.setChild(childList);
		}
		// sortMenu(menuList);
		menuList.sort((Menu m1, Menu m2) -> m1.getOrderIndex() - m2.getOrderIndex());
		for (Menu menu : menuList) {
			// sortMenu(menu.getChild());
			menu.getChild().sort((Menu m1, Menu m2) -> m1.getOrderIndex() - m2.getOrderIndex());
		}
		return menuList;
	}
//	public void sortMenu(List<Menu> menus) {
//		Collections.sort(menus,new Comparator<Menu>() {
//			public int compare(Menu o1, Menu o2) {
//				return o1.getOrderIndex()-o2.getOrderIndex();
//			}
//		});
//	}

	@Override
	public List<Menu> findWithoutNPlusOne(Paging paging, String keyword) {
		StringBuilder queryStr = new StringBuilder();
		// queryStr.append(" or model.activeFlag=0");
		Map<String, Object> mapParams = new HashMap<>();
		if (keyword != null) {
			queryStr.append(" where m.url like :url");
			mapParams.put("url", "%"+keyword+"%");
		}
		List<Menu> menuList = menuDao.findWithoutNPlusOne(paging, queryStr.toString(), mapParams);
		// List<Menu> menuList = menuDao.findAll(queryStr.toString(), mapParams,
		// paging);
		return menuList;
	}

	@Override
	public Map<Integer, Integer> getMapAuths(List<Role> roles, List<Menu> menuList) {

		return null;
	}
	
	@Override
	public void changeStatus(int id) {
		Menu menu = menuDao.findById(Menu.class, id);
		if (menu != null) {
			menu.setActiveFlag(menu.getActiveFlag() == 1 ? 0 : 1);
			menu.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			menuDao.update(menu);
		}
	}

	@Override
	public void updatePermission(int permission, int menuId, int roleId) {
//		int roleId = authForm.getRoleId();
//		int menuId = authForm.getMenuId();
//		int permission = authForm.getPermission();
		Authority auth = authDao.findByRoleIdAndMenuId(roleId, menuId);
		if(auth!=null) {
			auth.setPermission(permission== 1 ? 0 : 1);
			authDao.update(auth);
		}else {
			auth = new Authority();
			auth.setActiveFlag(1);
			Role role = new Role();
			role.setId(roleId);
			Menu menu  = new Menu();
			menu.setId(menuId);
			auth.setRole(role);
			auth.setMenu(menu);
			auth.setPermission(1);
			auth.setCreateDate(new Timestamp(System.currentTimeMillis()));
			auth.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			authDao.save(auth);
		}
	}

}
