package com.android.myopengl.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.myopengl.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class SurfaceViewRenderStar3D implements GLSurfaceView.Renderer{
	private Context context;
	private ArrayList<Star> starArray = new ArrayList<Star>();
	private static int starMaxNum = 50;
	
	private static float zoom = -40.0f;  //星星深入屏幕的距离
	private static float title = 90.0f;  //模型和视图变化时候旋转的角度
	private static float spin = 0.0f;    //星星自传角度
	
	private static Random rand = new Random(255);
	private static Matrix matrix = new Matrix();
	
	private IntBuffer texture =  IntBuffer.allocate(1);
	
	private static float one = 1.0f;
	
	private float[] vertexArray = new float[]{
		one , one ,0,
	   -one , one ,0,
	   -one ,-one ,0,
	    one ,-one ,0
	};
	
	private float[] textureArray = new float[]{
		1, 1,
		0, 1,
		0, 0,
		1, 0	
	};
	
	static{
		matrix.postScale(-1.0f, 1.0f);
	}
	
	//定义星星类
	public class Star{
		private float distanceToZero;
		private float angleToZero;
		
		private float red;
		private float green;
		private float blue;
		
        public Star(float angleToZero,float distanceToZero,float red,float green,float blue){
        	this.angleToZero = angleToZero;
        	this.distanceToZero = distanceToZero;
        	this.red = red;
        	this.green = green;
        	this.blue = blue;
		}
        
        public Star(float angleToZero,float distanceToZero){
        	this.angleToZero = angleToZero;
        	this.distanceToZero = distanceToZero;
		}
	}

	public SurfaceViewRenderStar3D(Context context){
		this.context = context;
		for(float i = 1 ; i < starMaxNum ; i+=1){
			starArray.add(new Star(0.0f, i/starMaxNum * 10.0f,
					rand.nextInt(255)%256,rand.nextInt(255)%256,rand.nextInt(255)%256));
			
//			starArray.add(new Star(0.0f, i/starMaxNum * 10.0f));
		}
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.get(0));
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		for(int i = 1 ; i < starArray.size(); i++){
			//模型和视图变换
			gl.glLoadIdentity();
			gl.glTranslatef(0.0f, 0.0f, zoom);
			
			gl.glRotatef(title, 1, 0, 0);
			gl.glRotatef(starArray.get(i-1).angleToZero, 0, 1, 0);
			
			gl.glTranslatef(starArray.get(i-1).distanceToZero, 0.0f, 0.0f);
			
			gl.glRotatef(-starArray.get(i-1).angleToZero, 0, 1, 0);
			gl.glRotatef(-title, 1, 0, 0);    
			
			gl.glRotatef(spin, 0, 0, 1);
			
			//执行绘制操作
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, FloatBuffer.wrap(vertexArray));
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, FloatBuffer.wrap(textureArray));
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, FloatBuffer.wrap(new float[]{
			    starArray.get(i-1).red , 0 ,0 ,1,
			    0 , starArray.get(i-1).green , 0 ,1,
			    0 , 0 ,starArray.get(i-1).blue , 1
			}));
			
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			
			spin+=0.01f;
			starArray.get(i-1).angleToZero += ((float)(starMaxNum + i))/starMaxNum;
			starArray.get(i-1).distanceToZero -= 0.01f;
			
			if(starArray.get(i-1).distanceToZero < 0.0f){
				starArray.get(i-1).distanceToZero += 10.0f;
			}
		}
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 1.0f, 100.0f);
        
        gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		loadTexture(gl);
		
		gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        
//		gl.glEnable(GL10.GL_LIGHT0);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f,0.5f}));
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, FloatBuffer.wrap(new float[]{1.0f,1.0f,1.0f,1.0f}));
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POINTS, FloatBuffer.wrap(new float[]{0.0f,0.0f,2.0f,1.0f}));
	}
	
	private void loadTexture(GL10 gl){
		gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glGenTextures(1, texture);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.get(0));
        
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        Bitmap resource = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, resource, 0);
        resource.recycle();
	}
	
}
