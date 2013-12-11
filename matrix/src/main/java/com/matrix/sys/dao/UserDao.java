package com.matrix.sys.dao;

import java.util.List;

import com.matrix.core.dao.BaseDao;
import com.matrix.sys.model.Role;
import com.matrix.sys.model.User;

public interface UserDao extends BaseDao<User,String>{
	public User findUserByName(String name);
	/**
	 * 
	 * <br>描述:</b>根据用户的名称（登录账号）查找该用户拥有的角色列表<br/>
	 * @param name
	 * @return 
	 * List<Role>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<Role> findRoles(String userId);
}
