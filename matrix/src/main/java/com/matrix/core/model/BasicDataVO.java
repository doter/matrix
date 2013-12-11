package com.matrix.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrix.core.common.enums.IsolationLevel;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BasicDataVO<ID extends Serializable> extends VO<ID> {

	/** 编码 */
	@Column(length = 32)
	private String code;

	/** 名称 */
	@Column(length = 64)
	private String name;

	/** 排序号 */
	@Column(name = "sort_no")
	private Integer sortNo;

	/** 描述 */
	@Column(length = 254)
	private String description;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 子类根据实际情况返回对应的隔离级别
	 * 
	 * @see org.matrix.core.common.enums.IsolationLevel
	 * @return
	 */
	@Transient
	@JsonIgnore
	public IsolationLevel getIsolationLevel() {
		return IsolationLevel.ORG_TREE;
	}

}
