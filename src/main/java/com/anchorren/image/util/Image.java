package com.anchorren.image.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.anchorren.image.bean.HSV;
import com.anchorren.image.bean.RGB;

/**
 * 图像处理基类
 * 
 * @author REN
 * @time:2017年3月19日 下午2:09:43
 */
public class Image {

	public int h; // 高
	public int w; // 宽
	public int[] data; // 像素
	public boolean isGray; // 是否为灰度图像

	public Image(BufferedImage img) {

		this.h = img.getHeight();
		this.w = img.getWidth();

		this.data = img.getRGB(0, 0, w, h, null, 0, w); // 获取RGB颜色矩阵
		this.isGray = false;
	}
	
	public Image(String imagePath) {
		
		File file = new File(imagePath);
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.h = img.getHeight();
		this.w = img.getWidth();

		//this.data = img.getRGB(0, 0, w, h, null, 0, w); // 获取RGB颜色矩阵
		this.isGray = false;
	}

	public Image(int[] data, int h, int w) {

		this.data = (data == null) ? new int[w * h] : data;
		this.h = h;
		this.w = w;
		this.isGray = false;
	}

	public Image(int h, int w) {
		this(null, h, w);
	}
	
	
	/**
	 * 将Int数组转化为Image方法
	 * 
	 * @return
	 */
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_ARGB);
		int[] d = new int[w * h];
		for (int i = 0; i < this.h; i++) {
			for (int j = 0; j < this.w; j++) {
				if (this.isGray) {
					d[j + i * this.w] = (255 << 24) | (data[j + i * this.w] << 16) | (data[j + i * this.w] << 8)
							| (data[j + i * this.w]);
				} else {
					d[j + i * this.w] = data[j + i * this.w];
				}
			}
		}
		image.setRGB(0, 0, w, h, d, 0, w);
		return image;
	}

	/**
	 * 转化为灰度矩阵
	 */
	public void toGray() {

		if (!isGray) {
			this.isGray = true;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int c = this.data[x + y * w];
					int R = (c >> 16) & 0xFF;
					int G = (c >> 8) & 0xFF;
					int B = (c >> 0) & 0xFF;
					this.data[x + y * w] = (int) (0.3f * R + 0.59f * G + 0.11f * B); // to
																						// gray
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Image [h=" + h + ", w=" + w + ", data=" + Arrays.toString(data) + ", isGray=" + isGray + "]";
	}
	
	
}
