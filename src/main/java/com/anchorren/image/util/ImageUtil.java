package com.anchorren.image.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.anchorren.image.bean.HSL;
import com.anchorren.image.bean.HSV;
import com.anchorren.image.bean.RGB;

public class ImageUtil {
	
	
	
	public static void main(String[] args) throws Exception {
		
		String imgPath = "";
		int[] hist = getImageHSVHist(imgPath);
		System.out.println(hist);
	}

	/**
	 * 获取输入图像的HSV颜色直方图
	 * 步骤：
	 * 		1）.输入图像，获取每一个像素的RGB颜色
	 * 		2）.将RGB转换为HSV（HSB）颜色空间
	 * 		3）.将HSV进行非均匀量化。H（7级）、S（3级）、V（3级）
	 * 		4）.将量化后结果按照公式( L = 9H + 3S + V ) 组合成63种颜色空间。
	 * 		5) .计算图像在L下的颜色直方图
	 * @param image
	 * @throws Exception
	 */
	public static int[] getImageHSVHist(String image) throws Exception {
		//int[] rgb = new int[3];
		RGB rgb = new RGB();
		File file = new File(image);
		float[] hsbvals = null;
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		int[] hist = new int[63]; //存储量化后的63级HSV颜色
		System.out.println("width=" + width + ",height=" + height + ".");
		System.out.println("minx=" + minx + ",miniy=" + miny + ".");
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				try {
					int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
					rgb.red = (pixel & 0xff0000) >> 16;
					rgb.green = (pixel & 0xff00) >> 8;
					rgb.blue = (pixel & 0xff);
				} catch (Exception e) {
					System.out.println("ERROR: i = " + i +" ; j = " + j );
					e.printStackTrace();
				}
				
				hsbvals = Color.RGBtoHSB(rgb.red, rgb.green, rgb.blue, hsbvals); //RGB转HSV

				HSV hsv = HSLQuantization(hsbvals); //HSV非均匀量化
				int L = getLFromHSV(hsv); //HSV颜色组合
		        hist[L]++;  //颜色直方图
				
				/*System.out.println("i=" + i + ",j=" + j + ":(" + rgb.red + "," + rgb.green + "," + rgb.blue + ") --> "
						+ "(" + hsv.getH() + "," + hsv.getS() + "," + hsv.getV() + ") --> L = " + L);*/
				
			}
		}
		return hist;
	}
	/**
	 * 获取图像颜色直方图
	 * @return
	 */
/*	public int[] hist(){  
       // toGray();  
        int[] hist = new int[256];  
        int len = h*w;  
          
        for(int i=0;i<len;i++)  
            hist[data[i]]++;  
        return hist;  
    }  
	*/
	/**
	 * L = 9H + 3S + V 
	 * @param hsl
	 * @return
	 */
	public static int getLFromHSV(HSV hsv){
		
		return (int) (9 * hsv.getH() + 3*hsv.getS() + hsv.getV());
	}
	
	/**
	 * HSV 非均匀量化
	 * 
	 * @param hsbvals
	 * @return
	 */
	public static HSV HSLQuantization(float[] hsbvals){
		if(hsbvals == null){
			return null;
		}
		
		float H = hsbvals[0] * 360;
		float S = hsbvals[1];
		float V = hsbvals[2];
		
		if((330 < H && H <= 360) || (0 <= H && H <= 22)){
			H = 0;
		}else if(22 < H && H <= 45){
			H = 1;
		}else if(45 < H && H <= 75){
			H = 2;
		}else if(75 < H && H <= 155){
			H = 3;
		}else if(155 < H && H <= 186){
			H = 4;
		}else if(186 < H && H <= 287){
			H = 5;
		}else if(287 < H && H <= 330){
			H = 6;
		}
		
		//S非均匀量化
		if(0 <= S && S <= 0.2){
			S = 0;
		}else if(0.2 < S && S <= 0.7){
			S = 1;
		}else if(0.7 < S && S <= 1){
			S = 2;
		}
		
		//L 非均匀量化
		if(0 <= V && V <= 0.2){
			V = 0;
		}else if(0.2 < V && V <= 0.7){
			V = 1;
		}else if(0.7 < V && V <= 1){
			V = 2;
		}
		
		return new HSV(H, S, V);
	}
	
	/**
	 * RGB --> HSL
	 * @param rgb
	 * @return
	 */
	public static HSL RGB2HSL(RGB rgb) {

		if (rgb == null) {

			return null;

		}

		float H, S, L, var_Min, var_Max, del_Max, del_R, del_G, del_B;
		H = 0;
		var_Min = Math.min(rgb.red, Math.min(rgb.blue, rgb.green));
		var_Max = Math.max(rgb.red, Math.max(rgb.blue, rgb.green));
		del_Max = var_Max - var_Min;
		L = (var_Max + var_Min) / 2;

		if (del_Max == 0) {

			H = 0;
			S = 0;

		} else {
			if (L < 128) {
				S = 256 * del_Max / (var_Max + var_Min);
			} else {
				S = 256 * del_Max / (512 - var_Max - var_Min);
			}

			del_R = ((360 * (var_Max - rgb.red) / 6) + (360 * del_Max / 2)) / del_Max;
			del_G = ((360 * (var_Max - rgb.green) / 6) + (360 * del_Max / 2)) / del_Max;
			del_B = ((360 * (var_Max - rgb.blue) / 6) + (360 * del_Max / 2)) / del_Max;
			
			if (rgb.red == var_Max) {
				H = del_B - del_G;
			} else if (rgb.green == var_Max) {
				H = 120 + del_R - del_B;
			} else if (rgb.blue == var_Max) {
				H = 240 + del_G - del_R;
			}

			if (H < 0) {
				H += 360;
			}

			if (H >= 360) {
				H -= 360;
			}

			if (L >= 256) {
				L = 255;
			}

			if (S >= 256) {
				S = 255;
			}

		}

			return new HSL(H, S, L);


	}

	/**
	 * HSL 转 RGB
	 * @param hsl
	 * @return
	 */
	public static RGB HSL2RGB(HSL hsl) {

		if (hsl == null) {

			return null;

		}

		float H = hsl.getH();

		float S = hsl.getS();

		float L = hsl.getL();

		float R, G, B, var_1, var_2;

		if (S == 0) {

			R = L;

			G = L;

			B = L;

		} else {

			if (L < 128) {

				var_2 = (L * (256 + S)) / 256;

			} else {

				var_2 = (L + S) - (S * L) / 256;

			}

			if (var_2 > 255) {

				var_2 = Math.round(var_2);

			}

			if (var_2 > 254) {

				var_2 = 255;

			}

			var_1 = 2 * L - var_2;

			R = RGBFromHue(var_1, var_2, H + 120);

			G = RGBFromHue(var_1, var_2, H);

			B = RGBFromHue(var_1, var_2, H - 120);

		}

		R = R < 0 ? 0 : R;

		R = R > 255 ? 255 : R;

		G = G < 0 ? 0 : G;

		G = G > 255 ? 255 : G;

		B = B < 0 ? 0 : B;

		B = B > 255 ? 255 : B;

		return new RGB((int) Math.round(R), (int) Math.round(G), (int) Math

				.round(B));

	}

	public static float RGBFromHue(float a, float b, float h) {

		if (h < 0) {

			h += 360;

		}

		if (h >= 360) {

			h -= 360;

		}

		if (h < 60) {

			return a + ((b - a) * h) / 60;

		}

		if (h < 180) {

			return b;

		}

		if (h < 240) {

			return a + ((b - a) * (240 - h)) / 60;

		}

		return a;

	}

}
