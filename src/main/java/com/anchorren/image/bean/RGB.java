package com.anchorren.image.bean;

/**
 * RGB 颜色模型
 * 
 * @author REN
 * @time:2017年3月21日 下午3:29:32
 */
public class RGB {

	public int red;
	public int green;
	public int blue;

	public RGB(int r , int g, int b){
		
		this.red = r;
		this.green = g;
		this.blue = b;
	}
	
	public RGB() {
		
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	@Override
	public String toString() {
		return "RGB [red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}

}
