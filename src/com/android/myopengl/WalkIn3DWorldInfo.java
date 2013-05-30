package com.android.myopengl;

public class WalkIn3DWorldInfo {
	private static float zer = 0.0f;
	private static float one = 1.0f;
	private static float two = 2.0f;
	private static float tre = 3.0f;
	
	private static float zp_one = 0.1f;
	private static float zp_fa = 0.5f;
	private static float op_fa = 1.5f;
	private static float zp_six = 0.6f;
	
	public static float[] floor = new float[]{
		-tre , -one , -tre,
		-tre , -one ,  tre,
		 tre , -one ,  tre,
		-tre , -one , -tre,
	     tre , -one , -tre,
	     tre , -one ,  tre
	};
	
	public static float[] floor_ceiling_texture = new float[]{
		zer , zp_six,
		zer , zer,
		zp_six , zer,
		zer , zp_six,
		zp_six , zp_six,
		zp_six , zer
	};
	
	public static float[] ceiling = new float[]{
		-tre , one , -tre,
		-tre , one ,  tre,
		 tre , one ,  tre,
		-tre , one , -tre,
	     tre , one , -tre,
	     tre , one ,  tre
	};
	
	public static float[] a1 = new float[]{
		-2.0f ,  1.0f , -2.0f,
		-2.0f , -1.0f , -2.0f,
		-0.15f, -1.0f , -2.0f,
		-2.0f ,  1.0f , -2.0f,
		-0.15f,  1.0f , -2.0f,
		-0.15f, -1.0f , -2.0f
	};
	
	public static float[] a1_texture = new float[]{
			0 , 0.1f,
			0 , 0,
		0.15f , 0,
			0 , 0.1f,
		0.15f , 0.1f,
		0.15f , 0
	};
	
	public static float[] a2 = new float[]{
		 2.0f , 1.0f ,-2.0f,
		 2.0f ,-1.0f ,-2.0f,
		 0.5f ,-1.0f ,-2.0f,
		 2.0f , 1.0f ,-2.0f,
		 0.5f , 1.0f ,-2.0f,
		 0.5f ,-1.0f ,-2.0f
	};
	
	public static float[] a2_texture = new float[]{
		 0.2f, 0.1f,
		 0.2f, 0.0f,
		 0.05f, 0.0f,
		 0.2f, 0.1f,
		 0.05f, 0.1f,
		 0.05f, 0.0f
	};
	
	
	public static float[] b1 = new float[]{
		-2.0f ,1.0f ,2.0f,
		-2.0f ,-1.0f ,2.0f,
		-0.5f ,-1.0f ,2.0f,
		-2.0f ,1.0f ,2.0f, 
		-0.5f ,1.0f ,2.0f,
		-0.5f ,-1.0f , 2.0f   
	};
	
	public static float[] b1_texture = new float[]{
		0.2f, 0.1f, 
		0.2f, 0.0f,   
		0.05f,0.0f,  
		0.2f, 0.1f, 
		0.05f,0.1f, 
		0.05f,0.0f  
	};
	
	public static float[] b2 = new float[]{
		 2.0f, 1.0f,2.0f,  
		 2.0f,-1.0f,2.0f, 
		 0.5f,-1.0f,2.0f, 
		 2.0f, 1.0f,2.0f,  
		 0.5f, 1.0f,2.0f,  
		 0.5f,-1.0f,2.0f  
	};
	
	public static float[] b2_texture = new float[]{
		0.2f, 0.1f,
		0.2f, 0.0f, 
		0.05f,0.0f, 
		0.2f, 0.1f,
		0.05f,0.1f,
		0.05f,0.0f
	};
	
	public static float[] c1 = new float[]{
		-2.0f, 1.0f,-2.0f,  
		-2.0f,-1.0f,-2.0f, 
		-2.0f,-1.0f,-0.5f, 
		-2.0f, 1.0f,-2.0f,  
		-2.0f, 1.0f,-0.5f,  
		-2.0f,-1.0f,-0.5f   
	};
	
	public static float[] c1_texture = new float[]{
		0.0f, 0.10f,   
		0.0f, 0.0f,    
		0.15f,0.0f,    
		0.0f, 0.10f,   
		0.15f,0.10f,   
		0.15f,0.0f  
	};
	
	public static float[] c2 = new float[]{
		-2.0f,1.0f,2.0f, 
		-2.0f,-1.0f,2.0f, 
		-2.0f,-1.0f,0.5f, 
		-2.0f, 1.0f,2.0f,  
		-2.0f, 1.0f,0.5f,  
		-2.0f,-1.0f,0.5f   
	};
	
	public static float[] c2_texture = new float[]{
		0.2f, 0.1f,  
		0.2f, 0.0f,  
		0.05f, 0.0f,  
		0.2f, 0.1f,  
		0.05f, 0.1f,  
		0.05f, 0.0f
	};
	
	public static float[] d1 = new float[]{
		2.0f, 1.0f,-2.0f,  
		2.0f,-1.0f,-2.0f, 
		2.0f,-1.0f,-0.5f,
		2.0f, 1.0f,-2.0f,  
		2.0f, 1.0f,-0.5f,  
		2.0f,-1.0f,-0.5f   
	};
	
	public static float[] d1_texture = new float[]{
		0.0f, 0.1f,
		0.0f, 0.0f,
		0.15f, 0.0f,
		0.0f, 0.1f,
		0.15f, 0.1f,
		0.15f, 0.0f  
	};
	
	public static float[] d2 = new float[]{
		2.0f, 1.0f,2.0f,  
		2.0f,-1.0f,2.0f, 
		2.0f,-1.0f,0.5f, 
		2.0f, 1.0f,2.0f,  
		2.0f, 1.0f,0.5f,  
		2.0f,-1.0f,0.5f  
	};
	
	public static float[] d2_texture = new float[]{
		0.2f, 0.1f, 
		0.2f, 0.0f, 
		0.05f, 0.0f, 
		0.2f, 0.1f, 
		0.05f, 0.1f, 
		0.05f, 0.0f
	};
	

}




























