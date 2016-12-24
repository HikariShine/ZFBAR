package com.example.myproject.strings;

import java.util.HashMap;
import java.util.Map;

public class ChangableString {

	private String string;

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	public ChangableString() {
		this.string = "";
	}
	
	public ChangableString(String str) {
		this.string = str;
	}
	
	@Override
	public int hashCode() {
		return this.string.hashCode();
	}
	
	public static void main(String[] args) {
		ChangableString cs = new ChangableString();
		System.out.println(cs.hashCode());
		String s1 = "我们";
		String s2 = "是什么";
		s1.toString();
		cs.setString(s1 + s2 + 6);//jvm自动调用StringBuilder
		System.out.println(cs.hashCode());
		Map<ChangableString, String> map = new HashMap<>();
		map.put(cs, cs.getString());
		cs.setString("1");
		System.out.println(map.get(cs));
	}
	
}
