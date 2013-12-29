/**
 * <b>包名：</b>com.matrix.sys.service.impl<br/>
 * <b>文件名：</b>ResourceServiceImpl.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-26-下午10:36:21<br/>
 * <br/>
 */
package com.matrix.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.matrix.core.common.enums.Status;
import com.matrix.core.dao.BaseDao;
import com.matrix.core.service.impl.TreeServiceImpl;
import com.matrix.sys.dao.ResourceDao;
import com.matrix.sys.model.Resource;
import com.matrix.sys.service.ResourceService;

/**
 * <b>类名称：</b>ResourceServiceImpl<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-26 下午10:36:21<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class ResourceServiceImpl extends TreeServiceImpl<Resource,String> implements ResourceService {
	@Autowired
	private ResourceDao dao;
	@Override
	public BaseDao getDao() {
		return dao;
	}
	
	protected void setDefaultValue(Resource res){
		super.setDefaultValue(res);
		res.setStatus(Status.VALID);
	}

}
