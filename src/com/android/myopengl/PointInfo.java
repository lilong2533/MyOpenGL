package com.android.myopengl;

public class PointInfo {
	public static final float[] vertices1 = new float[] {  //������
		 0,  1, 0,
		-1, -1, 0,
		 1, -1, 0 
	};
	
	public static final float[][] vertices2 = new float[][]{  //������
		 new float[] {    // ����
				 1, 1,-1,
				-1, 1,-1,
				-1, 1, 1,
				 1, 1, 1
			},
			new float[] { // ����
				 1,-1, 1,
				-1,-1, 1,
				-1,-1,-1,
				 1,-1,-1
			},
			new float[] { // ǰ��
				 1, 1, 1,
				-1, 1, 1,
				-1,-1, 1,
				 1,-1, 1
			},
			new float[] { // ����
				 1,-1,-1,
				-1,-1,-1,
				-1, 1,-1,
				 1, 1,-1
			},
			new float[] { // ����
				-1, 1, 1,
				-1, 1,-1,
				-1,-1,-1,
				-1,-1, 1
			},
			new float[] { // ����
				 1, 1,-1,
				 1, 1, 1,
				 1,-1, 1,
				 1,-1,-1
			},
	 };

}
