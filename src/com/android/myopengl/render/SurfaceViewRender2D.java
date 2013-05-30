package com.android.myopengl.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.myopengl.PointInfo;
import com.android.myopengl.TextureInfo;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class SurfaceViewRender2D implements GLSurfaceView.Renderer{
	 private static FloatBuffer triangleBuffer;
	 private static FloatBuffer colorBuffer;
	 
	 static
		{
		    ByteBuffer bbf = ByteBuffer.allocateDirect(PointInfo.vertices1.length * 4);
			bbf.order(ByteOrder.nativeOrder());
			triangleBuffer = bbf.asFloatBuffer();
			triangleBuffer.put(PointInfo.vertices1);
			triangleBuffer.position(0);
			
			ByteBuffer cbf = ByteBuffer.allocateDirect(TextureInfo.color1.length * 4);
			cbf.order(ByteOrder.nativeOrder());
			colorBuffer = cbf.asFloatBuffer();
			colorBuffer.put(TextureInfo.color1);
			colorBuffer.position(0);
		}


	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, FloatBuffer.wrap(PointInfo.vertices1));
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, FloatBuffer.wrap(TextureInfo.color1));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 3);
		
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl, 45.0f, (float)width/(float)height, 1.0f, 10.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0, 0, 0, 0);
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

}
