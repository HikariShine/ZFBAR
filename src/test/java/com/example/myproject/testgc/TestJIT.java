package com.example.myproject.testgc;

public class TestJIT {
	
	public static double calcPi() {
		double re = 0;
		for (int i = 0; i < 10000; i++) {
			re += ((i & 1) == 0 ? -1 : 1) * 1.0 / (2 * i - 1);
		}
		return re * 4;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			calcPi();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("beforeJIT:" + (endTime - startTime));
		
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			calcPi();
		}
		endTime = System.currentTimeMillis();
		System.out.println("afterJIT:" + (endTime - startTime));
	}
	
}
