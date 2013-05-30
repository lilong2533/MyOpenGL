package com.android.myopengl;

public class PointInfo {
	public static final float[] vertices1 = new float[] {  //三角形
		 0,  1, 0,
		-1, -1, 0,
		 1, -1, 0 
	};
	
	public static final float[][] vertices2 = new float[][]{  //立方体
		 new float[] {    // 上面
				 1, 1,-1,
				-1, 1,-1,
				-1, 1, 1,
				 1, 1, 1
			},
			new float[] { // 下面
				 1,-1, 1,
				-1,-1, 1,
				-1,-1,-1,
				 1,-1,-1
			},
			new float[] { // 前面
				 1, 1, 1,
				-1, 1, 1,
				-1,-1, 1,
				 1,-1, 1
			},
			new float[] { // 后面
				 1,-1,-1,
				-1,-1,-1,
				-1, 1,-1,
				 1, 1,-1
			},
			new float[] { // 左面
				-1, 1, 1,
				-1, 1,-1,
				-1,-1,-1,
				-1,-1, 1
			},
			new float[] { // 右面
				 1, 1,-1,
				 1, 1, 1,
				 1,-1, 1,
				 1,-1,-1
			},
	 };

}
