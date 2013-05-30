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
import android.util.AttributeSet;

public class SurfaceViewRenderTouch3D implements GLSurfaceView.Renderer{
	 public static float cubeRotX;
	 public static float cubeRotY;
	 public static float cubeRotZ;
	 private Context context;
	 private IntBuffer texTuresArray;
	 
	 private static FloatBuffer[] cubeVertexBfr;
	 private static FloatBuffer[] cubeTextureBfr;
	 
	 private static  FloatBuffer lightAmbient;
	 private static FloatBuffer lightDifBfr;
	 private static FloatBuffer lightPosBfr;
	 
	 private final static float lightAmb[]= { 0.5f, 0.5f, 0.5f, 1.0f };
	 private final static float lightDif[]= { 1.0f, 1.0f, 1.0f, 1.0f };
	 private final static float lightPos[]= { 0.0f, 0.0f, 2.0f, 1.0f };
 

		
		private static Matrix myMatrix;
	 
	 static{
		 cubeVertexBfr = new FloatBuffer[6];
		 cubeTextureBfr = new FloatBuffer[6];
		 
		 for(int i = 0; i < 6 ; i++){
			 cubeVertexBfr[i] = FloatBuffer.wrap(PointInfo.vertices2[i]);
			 cubeTextureBfr[i] = FloatBuffer.wrap(TextureInfo.texture1[i]);
		 }
		 
		 myMatrix = new Matrix();
		 myMatrix.postScale(-1, 1);
		 
		 lightAmbient = FloatBuffer.wrap(lightAmb);
		 lightDifBfr = FloatBuffer.wrap(lightDif);
		 lightPosBfr = FloatBuffer.wrap(lightPos);
	 }
	 
	 
     public SurfaceViewRenderTouch3D(Context context){
		 this.context = context;
	 } 
     
     public SurfaceViewRenderTouch3D(Context context, AttributeSet attrs) {
	 }

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glRotatef(cubeRotX, 1, 0, 0);
		gl.glRotatef(cubeRotY, 0, 1, 0);
		gl.glRotatef(cubeRotZ, 0, 0, 1);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texTuresArray.get(0));
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		for(int i = 0 ; i < 6 ; i++ ){
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBfr[i]);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTextureBfr[i]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		}
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 1.0f, 100.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColorx(0, 0, 0, 0);
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		loadTexture(gl);
		setLightConfig(gl);
	}
	
	private void loadTexture(GL10 gl){
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.crate);
		Bitmap texbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),myMatrix , false);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texTuresArray = IntBuffer.allocate(3);
		gl.glGenTextures(3, texTuresArray);
		// --(1)--
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texTuresArray.get(0));
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		// --(2)--
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texTuresArray.get(1));
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		// --(3)--
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texTuresArray.get(2));
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		texbitmap.recycle();
	}
	
	private void setLightConfig(GL10 gl){
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDifBfr);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POINTS, lightPosBfr);
	}
	
}
