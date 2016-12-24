package com.example.myprojects.vr;

import java.awt.image.BufferedImage;

public class Image {

	private static int[] minRgb = new int[]{30, 35, 35};
	private static int[] maxRgb = new int[]{70, 80, 105};
	private static int interferingCount = 27;
	private static int interferingHeight = 10;
	private static int yanshen = 1;
	
	public static BufferedImage process(BufferedImage src) throws Exception {
		int height = src.getHeight();
		int width = src.getWidth();
		int ganraoLine = 0;
		for (int i = 30; i < height; i++) {
			if (calcLineCovariance(src, 5, i) < 100) {
				ganraoLine = i;
			}
		}
		int[][] pixels = getPixels(src, ganraoLine, 5);
		interferingHeight = calcInterferingHeight(height, interferingCount);
		for (int i = 0; i < height; i++) {
			if (calcInterfering(src, pixels, i, 5) < 1000) {
				for (int j = Math.max(0, i - yanshen); j < Math.min(i + interferingHeight - yanshen + 1, height); j++) {
					for (int k = 0; k < width; k++) {
						src.setRGB(k, j, src.getRGB(k, Math.min(Math.max(i - yanshen - 2, 0), height)));  
					}
				}
				i += interferingHeight + 3;
			}
		}
		return src;

	}
	
	private static int[][] getPixels(BufferedImage src, int line, int start) {
		int[][] pixels = new int[src.getWidth() - start][3];
		for (int i = start; i < src.getWidth(); i++) {
			pixels[i - start] = getRgb(src.getRGB(i, line));
		}
		return pixels;
	}
	
	private static int calcInterfering(BufferedImage src, int[][] source, int line, int start) {
		int result = 0;
		for (int i = start; i < src.getWidth(); i++) {
			for (int j = 0; j < 3; j++) {
				result += (int)Math.pow(source[i - start][j] - getRgb(src.getRGB(i, line))[j], 2);
			}
		}
		result = result / ((src.getWidth() - start) * 3);
		return result;
	}

	private static int[] getRgb(int pixel) {
		int[] rgb = new int[3];
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);
//		System.out.println(rgb[0] + " " + rgb[1] + " " + rgb[2]);
		return rgb;
	}
	
	private static boolean isInterfering(BufferedImage image, int line, int start, int yuzhi) {
		int[] rgb;
		int count = 0;
		for (int i = start; i < image.getWidth(); i++) {
			rgb = getRgb(image.getRGB(i, line));
			for (int k = 0; k < 3; k++) {
				if (rgb[k] < minRgb[k] || rgb[k] > maxRgb[k]) {
					count++;
				}
			}
		}
		if (count > yuzhi) {
//			System.out.println(false);
			return false;
		}
//		System.out.println(true);
		return true;
	}
	
	private static int[][] getMinMaxRgb(BufferedImage src, int line) {
		int[][] rgbs = new int[2][3];
		int[] minRgb = rgbs[0];
		int[] maxRgb = rgbs[1];
		for (int j = 0; j < 3; j++) {
			minRgb[j] = 255;
			maxRgb[j] = 0;
		}
		for (int i = 0; i < src.getWidth(); i++) {
			int[] rgb = getRgb(src.getRGB(i, line));
			for (int j = 0; j < 3; j++) {
				minRgb[j] = Math.min(minRgb[j], rgb[j]);
				maxRgb[j] = Math.max(maxRgb[j], rgb[j]);
			}
		}
		for (int j = 0; j < 3; j++) {
			minRgb[j] -= 20;
			maxRgb[j] += 20; 
		}
		return rgbs;
	}

	private static int calcInterferingHeight(int height, int count){
		int h = height / count * 3 / 6;
		return h;
	}
	
	private static int calcLineCovariance(BufferedImage src, int start, int line){
		int[] avgRgb = new int[3];
		for (int i = start; i < src.getWidth(); i++) {
			int[] rgb = getRgb(src.getRGB(i, line));
			for (int k = 0; k < 3; k++) {
				avgRgb[k] += rgb[k];
			}
		}
		for (int k = 0; k < 3; k++) {
			avgRgb[k] = avgRgb[k] / (src.getWidth() - start);
		}
		int result = 0;
		for (int i = start; i < src.getWidth(); i++) {
			for (int j = 0; j < 3; j++) {
				result += (int)Math.pow(avgRgb[j] - getRgb(src.getRGB(i, line))[j], 2);
			}
		}
		result = result / ((src.getWidth() - start) * 3);
		return result;
	}
	
}