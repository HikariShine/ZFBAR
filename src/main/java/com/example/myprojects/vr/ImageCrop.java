package com.example.myprojects.vr;

import java.awt.image.BufferedImage;

public class ImageCrop {
	private static int[] minRgb = new int[]{10, 20, 30};
	private static int[] maxRgb = new int[]{30, 40, 60};
	
	public static BufferedImage crop(BufferedImage src) throws Exception {
		int height = src.getHeight();
		int width = src.getWidth();
		int quyangh = height / 2 + 100;
		int quyangw = width / 4;
		int bianyuanLeft = 0;
		int bianyuanRight = 0;
		int bianyuanUp = 0;
		int bianyuanDown = 0;
		for (int i = quyangw; i < width / 2; i++) {
			if (isBianyuan(src, i, quyangh, 50, 5)) {
				bianyuanLeft = i + 10;
				bianyuanRight = width - bianyuanLeft;
				break;
			}
		}
		for (int i = height - 20; i > width / 2; i--) {
			if (isCenter(src, i, 10, 5)) {
				bianyuanDown = i - 190;
				bianyuanUp = bianyuanDown - 10 - (bianyuanRight - bianyuanLeft);
				break;
			}
		}
		BufferedImage image = new BufferedImage(bianyuanRight - bianyuanLeft, 
				bianyuanDown - bianyuanUp, BufferedImage.TYPE_INT_BGR);  
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				if (!(k < bianyuanLeft || k >= bianyuanRight || i < bianyuanUp || i >= bianyuanDown)) {
					image.setRGB(k - bianyuanLeft, i - bianyuanUp, src.getRGB(k, i));
				}
			}
		}
		return image;// 输出到文件流

	}
	
	private static int[] getRgb(int pixel) {
		int[] rgb = new int[3];
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);
		return rgb;
	}
	
	private static boolean isBianyuan(BufferedImage image, int w, int h, int count, int yuzhi) {
		int yu = 0;
		for (int i = w; i < image.getWidth() / 2; i++) {
			for (int j = h; j < h + count; j++) {
				int[] rgb = getRgb(image.getRGB(w, h));
				for (int k = 0; k < 3; k++) {
					if (rgb[k] < minRgb[k] || rgb[k] > maxRgb[k]) {
						yu++;
					}
				}
			}
		}
		if (yu > yuzhi) {
			return false;
		}
		return true;
	}
	
	private static boolean isCenter(BufferedImage image, int h, int banjing, int yuzhi) {
		int yu = 0;
		for (int i = image.getWidth() / 2 - banjing; i < image.getWidth() / 2 + banjing; i++) {
			for (int j = h - banjing; j < Math.min(h + banjing, image.getHeight()); j++) {
				int[] rgb = getRgb(image.getRGB(i, j));
				for (int k = 0; k < 3; k++) {
					if (rgb[k] < minRgb[k] || rgb[k] > maxRgb[k]) {
						yu++;
					}
				}
			}
		}
		if (yu > yuzhi) {
			return false;
		}
		return true;
	}
}
