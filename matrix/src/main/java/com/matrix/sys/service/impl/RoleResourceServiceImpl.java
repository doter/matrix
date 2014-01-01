/**
 * <b>包名：</b>com.matrix.sys.service.impl<br/>
 * <b>文件名：</b>RoleResourceServiceImpl.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-26-下午10:38:03<br/>
 * <br/>
 */
package com.matrix.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.matrix.core.dao.BaseDao;
import com.matrix.core.service.impl.BaseServiceImpl;
import com.matrix.sys.dao.RoleResourceDao;
import com.matrix.sys.model.Resource;
import com.matrix.sys.model.RoleResource;
import com.matrix.sys.service.RoleResourceService;

/**
 * <b>类名称：</b>RoleResourceServiceImpl<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-26 下午10:38:03<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResource,String> implements RoleResourceService {

	@Autowired
	private RoleResourceDao dao;
	
	
	@Override
	public BaseDao getDao() {
		return dao;
	}
	
	public void save(String roleId,List<String> resourceIds){
		dao.save(roleId, resourceIds);
	}
	
	/**
	 * 描述:分配给角色的资源
	 * @param roleId
	 * @return
	 */
	public List<Resource> getAssignResource(String roleId){
		List<Resource> resList = null;
		Search search = new Search(RoleResource.class);
		search.addField("resource");
		search.addFilterEqual("role.id", roleId);
		search.setResultMode(ISearch.RESULT_ARRAY);
		List<Object[]> list = dao.search(search);
		if(null != list && list.size() > 0){
			resList = new ArrayList<Resource>(list.size());
			for (Object[] array : list) {
				resList.add((Resource)array[0]);
			}
		}else{
			resList = new ArrayList<Resource>();
		}
		return resList;
	}

}
