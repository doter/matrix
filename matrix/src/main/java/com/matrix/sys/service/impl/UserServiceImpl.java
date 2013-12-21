package com.matrix.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.matrix.core.service.impl.BasicDataServiceImpl;
import com.matrix.sys.dao.UserDao;
import com.matrix.sys.model.User;
import com.matrix.sys.service.UserService;

@Service
public class UserServiceImpl extends BasicDataServiceImpl<User,String> implements UserService{
	@Autowired
	private UserDao dao;
	
	public UserDao getDao(){
		return dao;
	}
	
	

	@Override
	public User findUserByAccount(String account){
		return dao.findUserByAccount(account);
	}

}
