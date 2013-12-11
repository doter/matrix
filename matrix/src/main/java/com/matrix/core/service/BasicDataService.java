package com.matrix.core.service;

import java.io.Serializable;
import com.matrix.core.model.BasicDataVO;

public interface BasicDataService<T extends BasicDataVO,ID extends Serializable> extends VOService<T,ID> {

}