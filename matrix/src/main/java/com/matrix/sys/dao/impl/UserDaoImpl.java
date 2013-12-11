package com.matrix.sys.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.googlecode.genericdao.search.Search;
import com.matrix.core.dao.impl.BaseDaoImpl;
import com.matrix.sys.dao.UserDao;
import com.matrix.sys.model.Role;
import com.matrix.sys.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {

	@Override
	public User findUserByName(String name) {
		Search search = new Search(User.class);
		search.addFilterEqual("name", name);
		return this.searchUnique(search);
	}

	@Override
	public List<Role> findRoles(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
