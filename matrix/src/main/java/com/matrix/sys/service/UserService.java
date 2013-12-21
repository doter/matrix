package com.matrix.sys.service;

import com.matrix.core.service.BasicDataService;
import com.matrix.sys.model.User;
public interface UserService extends BasicDataService<User,String>{
	
	public User findUserByAccount(String account);

}
