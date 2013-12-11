package com.matrix.core.service;

import java.io.Serializable;
import com.matrix.core.model.TreeVO;

public interface TreeService <T extends TreeVO,ID extends Serializable> extends BasicDataService<T,ID>{

}
