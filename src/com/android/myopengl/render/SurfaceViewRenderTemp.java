package com.android.myopengl.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.myopengl.PointInfo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class SurfaceViewRenderTemp implements GLSurfaceView.Renderer{
	 private Context context;
	 private static ArrayList<FloatBuffer> vertex_buffer = new ArrayList<FloatBuffer>();
	 static{
		 for(int i = 0 ; i < PointInfo.vertices2.length ; i++){
			 ByteBuffer bb = ByteBuffer.allocateDirect(PointInfo.vertices2[i].length * 4);
			 bb.order(ByteOrder.nativeOrder());
			 vertex_buffer.add(i,bb.asFloatBuffer());
			 vertex_buffer.get(i).put(PointInfo.vertices2[i]);
			 vertex_buffer.get(i).position(0);
		 }
		 
	 }
	 
     public SurfaceViewRenderTemp(Context context){
		 this.context = context;
	 }

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glLoadIdentity();
		
		GLU.gluLookAt(gl, 0.0f, 0.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);  //视图变换
	    gl.glScalef(1.0f, 2.0f, 1.0f);  //模型变换
	    
	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    
	    for(int index = 0 ; index < vertex_buffer.size() ; index++){
	    	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex_buffer.get(index));
	    	gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
	    }
	    
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-1, 1, -1, 1, 1.5f, 20.0f);  //投影变换
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig glconfig) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL10.GL_FLAT);
	} 
}
