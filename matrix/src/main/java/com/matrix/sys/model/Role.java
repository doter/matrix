/**
 * <b>包名：</b>org.matrix.sys.model<br/>
 * <b>文件名：</b>Role.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-25-下午9:46:23<br/>
 * <br/>
 */
package com.matrix.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.matrix.core.model.VO;

/**
 * <b>类名称：</b>Role<br/>
 * <b>类描述：</b>角色<br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-25 下午9:46:23<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_sys_role")
public class Role extends VO<String> implements Serializable {
	@Id
	@GenericGenerator(name="idGenerator",strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	/** 角色编码 */
	@Column(length = 32)
	private String code;
	
	/** 角色名称 */
	@Column(length = 64)
	private String name;


	/** 排序号 */
	@Column(name = "sort_no")
	private Integer sortNo;

	/** 描述 */
	@Column(length = 254)
	private String description;

	public Role() {
		super();
	}

	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
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
	
	
}
