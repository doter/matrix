package com.matrix.core.common.enums;
/**
 * 基础数据隔离级别
 * @author Administrator
 *
 */
public enum IsolationLevel implements EnumType {
	NO_ISOLATION("不隔离", 1),
	ORG_TREE("组织树内共享", 2),
	ORG("本组织共享", 3),
	ORG_CHILDREN("本组织及下级组织共享", 4),
	ALLOCATED("分配共享", 5),
	CREATOR("创建者隔离", 6);
	
	private final String displayName;
	private final int sequence;

	private IsolationLevel(String displayName, int sequence) {
		this.displayName = displayName;
		this.sequence = sequence;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public int getValue() {
		return ordinal();
	}

	@Override
	public int getSequence() {
		return sequence;
	}
}
