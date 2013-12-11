package com.matrix.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.matrix.core.service.impl.BaseServiceImpl;
import com.matrix.sys.dao.UserDao;
import com.matrix.sys.model.User;
import com.matrix.sys.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,String> implements UserService{
	@Autowired
	private UserDao dao;
	
	public UserDao getDao(){
		return dao;
	}
	
	

	@Override
	public User findUserByName(String name) {
		return dao.findUserByName(name);
	}

}
