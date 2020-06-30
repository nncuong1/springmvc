package com.nnc.dao;

import java.util.List;
import java.util.Map;

import com.nnc.entity.Menu;
import com.nnc.util.Paging;

public interface MenuDao<E> extends BaseDao<E> {
	public List<Menu> findWithoutNPlusOne(Paging paging, String queryStr,  Map<String,Object> mapParams) ;
}
