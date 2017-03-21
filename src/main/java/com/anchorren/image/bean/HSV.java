package com.anchorren.image.bean;

/**
 * HSV颜色模型
 * 
 * @author REN
 * @time:2017年3月21日 下午3:26:16
 */
public class HSV {

	/** 色调 */
	private float h = 0;
	/** 饱和度 */
	private float s = 0;
	/** 亮度 */
	private float v = 0;

	public HSV(float h, float s, float v) {
		
		this.h = h;
		this.s = s;
		this.v =v;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getS() {
		return s;
	}

	public void setS(float s) {
		this.s = s;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "HSV [h=" + h + ", s=" + s + ", v=" + v + "]";
	}

}
