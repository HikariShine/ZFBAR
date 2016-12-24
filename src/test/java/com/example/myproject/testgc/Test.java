package com.example.myproject.testgc;

public class Test {

	public static void main(String[] args) {
		testCallGc();
	}

	@SuppressWarnings("unused")
	public static void testCallGc() {
		int i = 0;
		int j = 1;
		{
			GCTest gc = new GCTest();
		}
		int k = 2;
		System.gc();
	}
	
	public static class GCTest {
		public int s = 1;
		protected void finalize() {
			System.out.println("GC了！");
		}
	}
	
}
