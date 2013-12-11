package com.matrix.sys.service;

import com.matrix.core.service.BaseService;
import com.matrix.sys.model.User;
public interface UserService extends BaseService<User,String>{
	
	public User findUserByName(String name);

}
