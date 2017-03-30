package com.anchorren.image.test;

import java.util.Arrays;

import Texture.TextureTool;

public class RGBtoHSV {

	public static void main(String[] args) throws Exception {
		
		
		TextureTool tool  = new TextureTool();
		// Object[] texture = tool.Texture(1, "F:\\snaps\\images\\wms\\2\\2_1.png");
		 Object[] texture = tool.Texture(1, "F:\\snaps\\images\\wms\\2\\2_1.png");
		 System.out.println("length:" + texture.length);
		 System.out.println(texture);
		 for(int i = 0; i < texture.length ; i ++){
			 System.out.println(texture[i]);
		 }
		 String[] split = texture[0].toString().split(" ");
		 String[] result = new String[8];
		 int i = 0;
		 for (String string : split) {
			 if(string.trim().length() >= 6){
				 result[i++] = string.trim();
			 }
		}
		 System.out.println(Arrays.toString(result));

		
		/*String paths = System.getProperty("java.library.path");
		String[] pathArr = paths.split(";");
		for (String string : pathArr) {
			
			System.out.println(string);
		}*/
		/*TextureTool texture = new TextureTool();
		Object[] array = texture.Texture(1, "E:\\shiyan\\23\\S_2.png");
		System.out.println(Arrays.toString(array));*/
	/*	String imgPath = "E:\\shiyan\\23\\S_2.png";
		int[] hist = ImageUtil.getImageHSVHist(imgPath);
		System.out.println(Arrays.toString(hist));*/
		
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
		int imaRGB[][]=new int[width][height];//���RGB��Ϣ���飬�ص�  
		  
		//��bufIma��ȡRGB��������  
		for(int i=0;i<width;i++){
			
			for(int j=0;j<height;j++)  
				imaRGB[i][j]=img.getRGB(i,j)&0xFFFFF;//��̫��  
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
