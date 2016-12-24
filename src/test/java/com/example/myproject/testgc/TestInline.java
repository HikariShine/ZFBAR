package com.example.myproject.testgc;

public class TestInline {

	public static int i = 0;
	
	public static void inc() {
		i++;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			inc();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("inline:" + (endTime - startTime));
	}
	
}
