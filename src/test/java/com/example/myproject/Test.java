package com.example.myproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		int s = 1;
		
		System.out.println(s >> 1);
		List<Date> list = new ArrayList<>();
		Date d1 = new Date();
		Date d2 = new Date(d1.getTime() + 2000);
		Date d3 = new Date(d1.getTime() + 1000);
		list.add(d1);
		list.add(d2);
		list.add(d3);
		list.stream().sorted((dd1, dd2) -> dd1.compareTo(dd2)).forEach(d -> System.out.println(d));
		Integer ss = null;
		System.out.println(ss + "abc");
//		String num = "4294967296";
//		System.out.println(chuBigNum(num, 65536));
	}

	public static String plusBigNum(String num, int plus) {
		StringBuilder sb = new StringBuilder();
		int carry = 0;
		for (int i = num.length() - 1; i >= 0; i--) {
			int one = (num.charAt(i) - '0') * plus + carry;
			carry = one / 10;
			sb.append(one - carry * 10);
		}
		return carry > 0 ? carry + sb.reverse().toString() : sb.reverse().toString();
	}
	
	public static String chuBigNum(String num, int chu) {
		StringBuilder sb = new StringBuilder();
		int carry = 0;
		for (int i = 0; i < num.length(); i++) {
			int one = carry * 10 + (num.charAt(i) - '0');
			carry = one % chu;
			if (sb.length() > 0 || one >= chu) sb.append(one / chu);
		}
		return sb.toString();
	}
	
}





