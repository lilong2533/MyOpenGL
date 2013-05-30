package com.android.myopengl.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.android.myopengl.R;
import com.android.myopengl.WalkIn3DWorldInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

public class SurfaceViewRender3DWalk implements GLSurfaceView.Renderer{
	 public static float y_rot = 0.0f; //场景的Y轴旋转量，对应用户视野左右旋转的场景
	 public static float x_head = 0.0f;//物体沿X轴平移的当量，用此值可以计算出xpos与zpos
	 public static float xpos = 0.0f;  //视野在x-z平面移动时，x轴当量值
	 public static float zpos = 6.0f;  //视野在x-z平面移动时，z轴当量值
	 
	 public static float walkbiasangle = 0.0f;
	 public static float walkbias = 0.0f;
	 
	 public final static float piover180 = 0.0174532925f; // 3.1415926/180
	 
	 private Context context;
	 private IntBuffer textures = IntBuffer.allocate(1);
	 
	 private LinkedList<Sector> sector_list = new LinkedList<Sector>();
	 
	 class Sector{
		 public FloatBuffer point_buffer;
		 public FloatBuffer texture_buffer;
		 public Sector(float[] point , float[] texture){
			 if(point.length > 0){
				 ByteBuffer bf = ByteBuffer.allocateDirect(point.length * 6);
				 point_buffer = bf.order(ByteOrder.nativeOrder()).asFloatBuffer();
				 point_buffer.put(point);
				 point_buffer.position(0);
			 }
			 
             if(texture.length > 0){
            	 ByteBuffer tf = ByteBuffer.allocateDirect(texture.length * 6);
            	 texture_buffer = tf.order(ByteOrder.nativeOrder()).asFloatBuffer();
            	 texture_buffer.put(texture);
            	 texture_buffer.position(0);
			 }
			 
		 }
	 }
	 
     public SurfaceViewRender3DWalk(Context context){
		 this.context = context;
		 sector_list.add(new Sector(WalkIn3DWorldInfo.floor,WalkIn3DWorldInfo.floor_ceiling_texture));
		 sector_list.add(new Sector(WalkIn3DWorldInfo.ceiling,WalkIn3DWorldInfo.floor_ceiling_texture));
		 sector_list.add(new Sector(WalkIn3DWorldInfo.a1,WalkIn3DWorldInfo.a1_texture));
		 sector_list.add(new Sector(WalkIn3DWorldInfo.a2,WalkIn3DWorldInfo.a2_texture));
		 
		 sector_list.add(new Sector(WalkIn3DWorldInfo.b1,WalkIn3DWorldInfo.b1_texture));
		 sector_list.add(new Sector(WalkIn3DWorldInfo.b2,WalkIn3DWorldInfo.b2_texture));
		 
		 sector_list.add(new Sector(WalkIn3DWorldInfo.c1,WalkIn3DWorldInfo.c1_texture));
		 sector_list.add(new Sector(WalkIn3DWorldInfo.c2,WalkIn3DWorldInfo.c2_texture));
		 
		 sector_list.add(new Sector(WalkIn3DWorldInfo.d1,WalkIn3DWorldInfo.d1_texture));
		 sector_list.add(new Sector(WalkIn3DWorldInfo.d2,WalkIn3DWorldInfo.d2_texture));
	 }

	@Override
	public void onDrawFrame(GL10 gl) {
		Log.d("******","xpos = "+xpos);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(y_rot, 0, 1, 0);
		gl.glTranslatef(x_head * 0.03f, -walkbias-0.25f, -zpos);
		
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures.get(0));
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		for(Sector item : sector_list){
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, item.point_buffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, item.texture_buffer);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 6);
		}
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		
		GLU.gluPerspective(gl,45.0f, (float)width/(float)height, 1.0f, 10.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig g) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
//		gl.glEnable(GL10.GL_BLEND);
//        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		
		loadTexture(gl);
	} 
	
	private void loadTexture(GL10 gl){  //载入纹理
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glGenTextures(1, textures);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures.get(0));
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
       
		Bitmap source_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mud);
		Bitmap temp_bitmap = Bitmap.createBitmap(source_bitmap, 0, 0, source_bitmap.getWidth(), source_bitmap.getHeight());
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, temp_bitmap, 0);
		source_bitmap.recycle();
	}
}
