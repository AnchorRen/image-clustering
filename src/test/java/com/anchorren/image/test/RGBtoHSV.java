package com.anchorren.image.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.anchorren.image.util.ImageUtil;

public class RGBtoHSV {

	public static void main(String[] args) throws Exception {
		
		
		ImageUtil.getImagePixel("E:\\shiyan\\23\\S_2.png");
		
		
	/*	BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream("E:\\shiyan\\23\\B_3.png"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image image = new Image(img);
		System.out.println(image);
		//System.out.println(image.data.length);
		
		int width=img.getWidth();  
		int height=img.getHeight();  
		int imaRGB[][]=new int[width][height];//存放RGB信息数组，重点  
		  
		//从bufIma读取RGB到数组中  
		for(int i=0;i<width;i++){
			
			for(int j=0;j<height;j++)  
				imaRGB[i][j]=img.getRGB(i,j)&0xFFFFF;//不太懂  
		}
		
		for(int i = 0 ; i < imaRGB.length ; i ++  ){
			for(int j = 0; j < imaRGB[i].length; j ++){
				System.out.print(imaRGB[i][j] + " ");
			}
			System.out.println();
		}*/
		
		/*image.toGray();
		
		BufferedImage grayImg = image.toImage();
		ImageIO.write(grayImg, "png", new File("E:\\shiyan\\B_3.png"));*/
	}
}
