/**
 * <b>包名：</b>org.matrix.core.model<br/>
 * <b>文件名：</b>Entity.java<br/>
 * <b>版本信息：</b>1.0.0<br/>
 * <b>日期：</b>2013-6-26-下午9:18:49<br/>
 * <br/>
 */
package com.matrix.core.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.persistence.MappedSuperclass;

/**
 * <b>类名称：</b>Entity<br/>
 * <b>类描述：</b>可持久化的对象<br/>
 * <b>创建人：</b>rong yang<br/>
 * <b>修改人：</b>rong yang<br/>
 * <b>修改时间：</b>2013-6-26 下午9:18:49<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Entity<ID extends Serializable> implements Serializable,Cloneable {
	public abstract ID getId();

	public abstract  void setId(ID id);

	public Object clone() throws CloneNotSupportedException {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(this);

			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
			return in.readObject();
		} catch (Exception e) {
			throw new RuntimeException("cannot clone class [" + this.getClass().getName() + "] var serialization: " + e.toString());
		}
	}
}
