package com.matrix.sys.model;

import java.io.Serializable;

public class Test {

}

class M<PK extends Serializable>{
}

class M1 extends M<String>{
}

interface IS<T extends M,ID>{
	
}

class S<T extends M,ID> implements IS<T,ID>{};