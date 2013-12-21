package com.matrix.sys.dao.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.matrix.sys.dao.UserDao;
import com.matrix.sys.model.OnlineUser;
import com.matrix.sys.model.Role;
import com.matrix.sys.model.User;

public class MatrixJdbcRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(MatrixJdbcRealm.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        
        OnlineUser onlineUser = (OnlineUser) getAvailablePrincipal(principals);
        
        List<Role> roles = null;
        Set<String> roleCodes = null;
       
        try {
        	roles = userDao.findRoles(onlineUser.getId());
        	if(null != roles && roles.size() > 0){
        		roleCodes = new HashSet<String>(roles.size());
        		for (Role role : roles) {
        			roleCodes.add(role.getCode());
				}
        	}
            // Retrieve roles and permissions from database
//            roleNames = getRoleNamesForUser(conn, username);
//            if (permissionsLookupEnabled) {
//                permissions = getPermissions(conn, username, roleNames);
//            }

        } catch (Exception e) {
          
           // throw new AuthorizationException(message, e);
        } finally {
            
        }

        if(null == roleCodes){
        	roleCodes = new HashSet<String>();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleCodes);
       // info.setStringPermissions(permissions);
        return info;
        
        
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String account = token.getUsername();
		if (StringUtils.isEmpty(account)) {
			throw new AccountException("用户名不能为空！");
		}
		
		User user = userDao.findUserByAccount(account);
		if(null == user){
			throw new UnknownAccountException("用户名或密码不正确！");
		}
		
		if(!user.getIsEnable()){
			throw new LockedAccountException("您的用户已锁定，请联系管理员处理！");
		}
	
		SimpleAuthenticationInfo info = null;
		try {
			

			//在线用户
			OnlineUser onlineUser = new OnlineUser(user.getId(),user.getAccount(),user.getName());
			
			info = new SimpleAuthenticationInfo(onlineUser, user.getPassword().toCharArray(), getName());
			
			info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
			
		} catch (Exception e) {
			final String message = "There was a exception error while authenticating user [" + account + "]";
			logger.error(message, e);
			throw new AuthenticationException(message, e);
		} finally {
			
		}

		return info;
	}

}
