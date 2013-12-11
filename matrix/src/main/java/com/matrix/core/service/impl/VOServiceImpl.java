package com.matrix.core.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.matrix.core.common.enums.Status;
import com.matrix.core.model.VO;
import com.matrix.core.service.VOService;

public abstract class VOServiceImpl <T extends VO, ID extends Serializable> extends  BaseServiceImpl<T, ID> implements VOService<T,ID>{
	@Override
	public boolean save(T entity) {
		if(!exists(entity)){
			setAddNew(entity);
		}else{
			setUpdate(entity);
		}
		return super.save(entity);
	}

	/**
	 * 新增时设置默认的属性
	 * @param entity
	 */
	protected void setAddNew(T entity) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		entity.setStatus(Status.SAVE);
		entity.setCreateTime(now);
		entity.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
	}
	/**
	 * 更新时时设置默认的属性
	 * @param entity
	 */
	protected void setUpdate(T entity) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		entity.setUpdateTime(now);
	}
	
	public void updateStatus(ID id, Status status){
		Map propertys = new HashMap();
		propertys.put("status", status);
		getDao().updateById(getDao().getEntityClass(), id, propertys);
	}
}
