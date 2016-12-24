package com.example.myproject;

public enum TestCode {

	A(5, "提示"), B(1, "提示");
	
	private int value;
	
	private String desc;
	
	private TestCode(int i, String desc) {
		this.value = i;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}
	
}
