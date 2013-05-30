package com.android.myopengl.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.myopengl.PointInfo;
import com.android.myopengl.R;
import com.android.myopengl.TextureInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class SurfaceViewRender3D implements GLSurfaceView.Renderer{
	 private Context context;
	 private static FloatBuffer[] PointArrayBuffer;
	 private static FloatBuffer[] colorArrayBuffer;
	 
	 private static float cubeRotX;
	 private static float cubeRotY;
	 private static float cubeRotZ;
	 
	 private IntBuffer texturesBuffer;
	 private static Matrix yFlipMatrix;
	 
	 private static  FloatBuffer lightAmbient;
	 private static FloatBuffer lightDifBfr;
	 private static FloatBuffer lightPosBfr;
	 
	 private final static float lightAmb[]= { 0.5f, 0.5f, 0.5f, 1.0f };
	 private final static float lightDif[]= { 1.0f, 1.0f, 1.0f, 1.0f };
	 private final static float lightPos[]= { 0.0f, 0.0f, 2.0f, 1.0f };
	 
	 static{
		 lightAmbient = FloatBuffer.wrap(lightAmb);
		 lightDifBfr = FloatBuffer.wrap(lightDif);
		 lightPosBfr = FloatBuffer.wrap(lightPos);
		 
		 PointArrayBuffer = new FloatBuffer[6];
		 colorArrayBuffer = new FloatBuffer[6];
		 
		 for(int i = 0 ;i < 6 ; i++){
			 PointArrayBuffer[i] = FloatBuffer.wrap(PointInfo.vertices2[i]);
			 colorArrayBuffer[i] = FloatBuffer.wrap(TextureInfo.texture1[i]);
		 }
		 
		 yFlipMatrix = new Matrix();
		 yFlipMatrix.postScale(1, -1);
	 }
	 
	 
     public SurfaceViewRender3D(Context context){
		 this.context = context;
	 } 

	@Override
	public void onDrawFrame(GL10 gl){
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(0 , 0, -6);
		
		gl.glRotatef(cubeRotX, 1, 0, 0);
		gl.glRotatef(cubeRotY, 0, 1, 0);
		gl.glRotatef(cubeRotZ, 0, 0, 1);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texturesBuffer.get(0));
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		for(int i = 0 ; i < 6 ; i++){
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, PointArrayBuffer[i]);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, colorArrayBuffer[i]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		}
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		cubeRotX += 1.2f;
		cubeRotY += 0.8f;
		cubeRotZ += 0.6f;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height){
		if (height == 0) height = 1;
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl,45.0f, (float)width/(float)height, 1.0f, 100.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config){
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0,0,0,0);
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
        loadTexture(gl);
		
		setLightConfig(gl);
	}
	
	private void loadTexture(GL10 gl){
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texturesBuffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, texturesBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texturesBuffer.get(0));
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		
		Bitmap bitmap_temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.nehe);
		Bitmap bitmap_resource = Bitmap.createBitmap(bitmap_temp, 0, 0, bitmap_temp.getWidth(), bitmap_temp.getHeight(),yFlipMatrix,false);
		
//		ByteBuffer bitmap_buffer = ByteBuffer.allocate(bitmap_resource.getWidth() * bitmap_resource.getHeight() * 32);
//		bitmap_resource.copyPixelsToBuffer(bitmap_buffer);
//		if(bitmap_buffer != null){
//			gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, 3, bitmap_resource.getWidth(), bitmap_resource.getHeight(), 0, GL10.GL_RGB, GL10.GL_UNSIGNED_BYTE, bitmap_buffer);
//		}
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap_resource, 0);
		bitmap_resource.recycle();
	}
	
	private void setLightConfig(GL10 gl){
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDifBfr);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POINTS, lightPosBfr);
	}
}
