/**
 * <b>包名：</b>org.matrix.sys.model<br/>
 * <b>文件名：</b>Resource.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-25-下午10:17:02<br/>
 * <br/>
 */
package com.matrix.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.matrix.core.model.TreeVO;
import com.matrix.sys.enums.ResourceType;

/**
 * <b>类名称：</b>Resource<br/>
 * <b>类描述：</b>系统可访问资源<br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-25 下午10:17:02<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "t_sys_resource")
public class Resource extends TreeVO<String> implements Serializable{
	@Id
	@GenericGenerator(name="idGenerator",strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	/** 上级资源 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Resource parent;
	
	
	
	/** 资源类型 */
	@Enumerated(EnumType.ORDINAL)
	ResourceType type;

	/** 资源URL */
	@Column(length = 64)
	private String url;
	
	/** 资源对应的图标 */
	@Column(length = 32)
	private String icon;
	
	
	
	/** 是否需要权限 */
	@Column(name = "is_authorization")
	private Boolean isAuthorization;
	

	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}


	public Resource getParent() {
		return parent;
	}


	public void setParent(Resource parent) {
		this.parent = parent;
	}


	public ResourceType getType() {
		return type;
	}


	public void setType(ResourceType type) {
		this.type = type;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public Boolean getIsAuthorization() {
		return isAuthorization;
	}


	public void setIsAuthorization(Boolean isAuthorization) {
		this.isAuthorization = isAuthorization;
	}
	
	
}
