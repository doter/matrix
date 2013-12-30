package com.matrix.core.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matrix.core.common.enums.Status;
import com.matrix.core.web.util.EnumTypeSerialize;

/**
 * 
 * <B>描述：</B><BR>
 * 值对象最高基类 <BR>
 * 
 * @author rong yang
 * @version 1.0 2013-06-22
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class VO<ID extends Serializable> extends Entity<ID> {
	/** 创建时间 */
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "create_time",updatable=false)
	private Timestamp createTime;

	/** 最近一次更新时间 */
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "update_time")
	private Timestamp updateTime;
	
	/** 更新人员,登录的用户账号 */
	@Column(name="update_user",length = 36)
	private String updateUser;

	/** 创建者的用户,登录的用户账号 */
	@Column(length = 36,updatable=false)
	private String creator;

	/**
	 * 状态
	 * 
	 * @see org.matrix.core.common.enums.Status
	 */
	@JsonSerialize(using = EnumTypeSerialize.class)
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	/** 额外的临时信息 */
	@JsonIgnore
	@SuppressWarnings("rawtypes")
	@Transient
	private Map additional;

	public VO() {
		super();
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Map getAdditional() {
		if (null == additional) {
			additional = new HashMap();
		}
		return additional;
	}

	public Object getAdditional(Object key) {
		// TODO 参数合法性检查
		Object result = getAdditional().get(key);
		return result;
	}

	@SuppressWarnings("unchecked")
	public void setAdditional(Object key, Object value) {
		// TODO 参数合法性检查
		getAdditional().put(key, value);
	}

	@SuppressWarnings("rawtypes")
	@JsonIgnore
	public Collection getAdditionalKeys() {
		return (Collection) getAdditional().keySet();
	}
}
