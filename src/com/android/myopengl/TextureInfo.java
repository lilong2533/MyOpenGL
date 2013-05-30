package com.android.myopengl;

public class TextureInfo {
	public static final float[] color1 = new float[] {
		 1, 0, 0, 1,
		 0, 1, 0, 1,
		 0, 0, 1, 1 
	 };
	
	public final static float[][] texture1 = new float[][] {
		new float[] { // 上面
			1, 0,
			1, 1,
			0, 1,
			0, 0
		},
		new float[] { // 下面
			0, 0,
			1, 0,
			1, 1,
			0, 1
		},
		new float[] { // 前面
			1, 1,
			0, 1,
			0, 0,
			1, 0
		},
		new float[] { // 后面
			0, 1,
			0, 0,
			1, 0,
			1, 1
		},
		new float[] { // 左面
			1, 1,
			0, 1,
			0, 0,
			1, 0
		},
		new float[] { // 右面
			0, 1,
			0, 0,
			1, 0,
			1, 1
		},
	};

}
