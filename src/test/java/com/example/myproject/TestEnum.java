package com.example.myproject;

import java.util.HashMap;
import java.util.Map;

public class TestEnum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map a = new HashMap<String, Object>();
		System.out.println((Integer)a.get("1"));
		TestCode.valueOf(TestCode.A.name());
	}

}
