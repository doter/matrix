/**
 * <b>包名：</b>com.matrix.sys.service.impl<br/>
 * <b>文件名：</b>RoleServiceImpl.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-26-下午10:37:16<br/>
 * <br/>
 */
package com.matrix.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.matrix.core.dao.BaseDao;
import com.matrix.core.service.impl.BasicDataServiceImpl;
import com.matrix.sys.dao.RoleDao;
import com.matrix.sys.model.Role;
import com.matrix.sys.service.RoleService;

/**
 * <b>类名称：</b>RoleServiceImpl<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-26 下午10:37:16<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class RoleServiceImpl extends BasicDataServiceImpl<Role,String> implements RoleService {
	@Autowired
	private RoleDao dao;

	@Override
	public BaseDao getDao() {
		return dao;
	}

}
