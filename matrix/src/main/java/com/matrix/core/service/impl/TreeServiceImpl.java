package com.matrix.core.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.googlecode.genericdao.search.Search;
import com.matrix.core.model.TreeVO;
import com.matrix.core.service.TreeService;
import com.matrix.sys.model.Organization;

public abstract class TreeServiceImpl <T extends TreeVO,ID extends Serializable> extends  BasicDataServiceImpl<T, ID> implements TreeService<T,ID>{
	public static final String LEVEL_CODE_DELIMITER = "-";
	@Override
	protected void setAddNew(T entity) {
		super.setAddNew(entity);
		//树节点相关属性的设置
		if (null != entity.getParent() 
				&& null != entity.getParent().getId()
				&& StringUtils.isNotEmpty(entity.getParent().getId().toString())){
			TreeVO parent = (TreeVO)getDao().find(entity.getParent().getId());
			int count = getChildrenSize(parent.getId()) + 1;
			String levelCode = parent.getLevelCode()+LEVEL_CODE_DELIMITER + StringUtils.leftPad(""+count, getlevelCodeLength(),"0");
			entity.setLevelCode(levelCode);
			entity.setDegree(parent.getDegree()+ 1);
			entity.setLeaf(true);
			
			if(parent.getLeaf()){
				updateLeaf(entity.getParent().getId(),false);
			}
		}else{
			//根节点
			try {
				BeanUtils.setProperty(entity, "parent", null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int count = getChildrenSize(null) + 1;
			entity.setLevelCode(StringUtils.leftPad(""+count, getlevelCodeLength(),"0"));
			entity.setDegree(0);//根结点设置为0
			entity.setLeaf(true);
		}
		
	}
	
	
	
	@Override
	protected void afterRemove(T entity) {
		super.afterRemove(entity);
		if(null != entity.getParent()){
			int count = getChildrenSize(entity.getParent().getId());
			if(0 == count){
				updateLeaf(entity.getParent().getId(),true);
			}
		}
	}


	public List<T> getChildren(String parentId){
		Search search = new Search();
		if(StringUtils.isEmpty(parentId)){
			search.addFilterNull("parent.id");
		}else{
			search.addFilterEqual("parent.id", parentId);
		}
		
		search.addSortAsc("sortNo");
		
		return getDao().search(search);
	}

	/**
	 * 如是 parentId 为null 或空，则返回根节点个数
	 * @param parentId
	 * @return
	 */
	public int getChildrenSize(Serializable parentId){
		Search search = new Search();
		if(null == parentId || StringUtils.isEmpty(parentId.toString())){
			search.addFilterNull("parent.id");
		}else{
			search.addFilterEqual("parent.id",parentId);
		}
		return getDao().count(search);
	}
	
	protected void updateLeaf(Serializable id,Boolean isLeaf) {
		Map propertys = new HashMap();
		propertys.put("leaf", isLeaf);
		getDao().updateById(getDao().getEntityClass(), id, propertys);
	}
	
	//获取层次码的长度
	protected int getlevelCodeLength() {
		return 4;
	}
}
