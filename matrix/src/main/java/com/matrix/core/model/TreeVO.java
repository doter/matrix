package com.matrix.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.matrix.sys.model.Organization;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class TreeVO<ID extends Serializable> extends BasicDataVO<ID> {
	/** 树的层级编码 */
	@Column(name = "level_code", length = 254)
	private String levelCode;

	/** 树的度 */
	@Column
	private Integer degree;
	
	/** 是否叶子节点 */
	@Column(name = "is_leaf")
	private Boolean leaf;

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	public abstract TreeVO getParent();
}
