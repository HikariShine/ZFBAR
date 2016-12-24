package com.example.myproject.testgc;

public class TestOnStack {

	public static class User {
		public int id = 0;
		public String name = "";
	}
	
	public static void alloc() {
		User u = new User();
		u.id = 1;
		u.name = "guangshan";
	}
	
	public static void main(String[] args) throws InterruptedException {
		
//		Thread.sleep(10000);
		
		long startTime = System.currentTimeMillis();
		for (long i = 0; i < 1000000000L; i++) {
			alloc();
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);

//		Thread.sleep(10000);
		
	}
	
}
