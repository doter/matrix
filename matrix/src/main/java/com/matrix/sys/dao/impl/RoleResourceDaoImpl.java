/**
 * <b>包名：</b>com.matrix.sys.dao.impl<br/>
 * <b>文件名：</b>RoleResourceDaoImpl.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-25-下午11:14:54<br/>
 * <br/>
 */
package com.matrix.sys.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.matrix.core.dao.impl.BaseDaoImpl;
import com.matrix.sys.dao.RoleResourceDao;
import com.matrix.sys.model.Resource;
import com.matrix.sys.model.Role;
import com.matrix.sys.model.RoleResource;

/**
 * <b>类名称：</b>RoleResourceDaoImpl<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-25 下午11:14:54<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
@Repository
public class RoleResourceDaoImpl extends BaseDaoImpl<RoleResource, String> implements RoleResourceDao{
	public void save(String roleId,List<String> resourceIds){
		//先删除该角色的所有资源,
		String hql = "delete RoleResource t where t.role.id = :roleId";
		Query query = getSession().createQuery(hql);
		query.setParameter("roleId", roleId);
		query.executeUpdate();
		
		//插入新分配的资源
		RoleResource rr = null;
		Resource res = null;
		Role role = new Role();
		role.setId(roleId);
		for(int i=0,size = resourceIds.size(); i < size; i++){
			rr = new RoleResource();
			res = new Resource();
			res.setId(resourceIds.get(i));
			rr.setResource(res);
			rr.setRole(role);
			    _save(rr);
			    if ( i % 20 == 0 ) {
			        flush();
			    }
		}
		flush();
	}
}
