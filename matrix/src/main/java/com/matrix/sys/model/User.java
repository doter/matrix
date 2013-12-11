package com.matrix.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.matrix.core.model.VO;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_sys_user")
public class User extends VO<String> implements Serializable {
	@Id
	@GenericGenerator(name="idGenerator",strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	/** 用户名，登录系统的账号 */
	@Column(length = 32,nullable=false)
	private String name;

	/** 显示名称 */
	@Column(name = "display_name", length = 32, nullable=false)
	private String displayName;

	/** 密码 */
	@Column(length = 254 , nullable=false)
	private String password;
	
	@Column(length = 64)
	private String salt;

	/** 是否启用 */
	@Column(name = "is_enable")
	private Boolean isEnable;

	/** 所属组织 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Organization org;

	/** 排序号 */
	@Column(name = "sort_no")
	private Integer sortNo;

	/** 描述 */
	@Column(length = 254)
	private String description;

	public User() {
		super();
	}
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
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
