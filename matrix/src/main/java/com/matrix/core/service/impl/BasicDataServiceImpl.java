package com.matrix.core.service.impl;

import java.io.Serializable;
import com.matrix.core.model.BasicDataVO;
import com.matrix.core.service.BasicDataService;

public abstract class BasicDataServiceImpl <T extends BasicDataVO,ID extends Serializable> extends  VOServiceImpl<T, ID> implements BasicDataService<T,ID>{

}
