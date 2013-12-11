package com.matrix.core.service;

import java.io.Serializable;

import com.matrix.core.common.enums.Status;
import com.matrix.core.model.VO;

public interface VOService<T extends VO, ID extends Serializable> extends BaseService<T, ID> {
	public void updateStatus(ID id, Status status);
}
