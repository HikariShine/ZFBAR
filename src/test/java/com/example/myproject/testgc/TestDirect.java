package com.example.myproject.testgc;

import java.nio.ByteBuffer;

public class TestDirect {
	
	public void directAccess() {
		long startTime = System.currentTimeMillis();
		ByteBuffer b = ByteBuffer.allocateDirect(500);
		for (int i = 0; i < 10000000; i++) {
			for (int j = 0; j < 99; j++) {
				b.putInt(j);
			}
			b.flip();
			for (int j = 0; j < 99; j++) {
				b.getInt();
			}
			b.clear();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("testDirect:" + (endTime - startTime));
	}
	
	public void bufferAccess() {
		long startTime = System.currentTimeMillis();
		ByteBuffer b = ByteBuffer.allocate(500);
		for (int i = 0; i < 10000000; i++) {
			for (int j = 0; j < 99; j++) {
				b.putInt(j);
			}
			b.flip();
			for (int j = 0; j < 99; j++) {
				b.getInt();
			}
			b.clear();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("testBuffer:" + (endTime - startTime));
	}
	
	public static void main(String[] args) {
//		TestDirect td = new TestDirect();
//		td.allocNonDirect();
//		td.allocDirect();
		TestTLAB A = new TestTLAB();
		TestTLAB B = new TestTLAB();
		A.start();
		B.start();
	}
	
	public void allocDirect() {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			ByteBuffer b = ByteBuffer.allocateDirect(1000);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("testDirectAllocate:" + (endTime - startTime));
	}
	
	public void allocNonDirect() {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			ByteBuffer b = ByteBuffer.allocate(1);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("testDirectNonAllocate:" + (endTime - startTime));
	}
	
	public static class TestTLAB extends Thread {
		
		public void run() {
			allocNonDirect();
		}
		
		public void allocNonDirect() {
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++) {
				ByteBuffer b = ByteBuffer.allocate(1);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("testDirectNonAllocate:" + (endTime - startTime));
		}
		
	}
	
}
