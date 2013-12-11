/**
 * <b>包名：</b>com.matrix.sys.service.impl<br/>
 * <b>文件名：</b>OrganizationServiceImpl.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-26-下午10:34:52<br/>
 * <br/>
 */
package com.matrix.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.search.Search;
import com.matrix.core.model.TreeVO;
import com.matrix.core.service.impl.TreeServiceImpl;
import com.matrix.sys.dao.OrganizationDao;
import com.matrix.sys.model.Organization;
import com.matrix.sys.service.OrganizationService;

/**
 * <b>类名称：</b>OrganizationServiceImpl<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-26 下午10:34:52<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class OrganizationServiceImpl extends TreeServiceImpl<Organization,String> implements OrganizationService {
	@Autowired
	private OrganizationDao dao;
	
	public OrganizationDao getDao(){
		return dao;
	}
}


