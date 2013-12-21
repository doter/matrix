package com.matrix.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;
import com.matrix.core.dao.impl.BaseDaoImpl;
import com.matrix.sys.dao.UserDao;
import com.matrix.sys.model.Role;
import com.matrix.sys.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {

	@Override
	public User findUserByAccount(String account){
		Search search = new Search(User.class);
		search.addFilterEqual("account", account);
		return this.searchUnique(search);
	}

	@Override
	public List<Role> findRoles(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Search getFindPageSearch(Map queryParams) {
		Search search = super.getFindPageSearch(queryParams);
		search.addFilterNotEmpty("org.id");
		return search;
	}
}
