package com.android.myopengl;

import com.android.myopengl.render.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SurfaceViewRenderList extends Activity {
	private GLSurfaceView.Renderer mainSurfaceViewRender;
	private GLSurfaceView myGLSurface;
	
	private float lastX;
	private float lastY;
	
	private boolean use_layout = false;
	private Button view_or_move ;
	private boolean change_yrot = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        initSurfaceViewRender();
        if(!use_layout){
        	myGLSurface = new GLSurfaceView(this);
        	setTouMing(myGLSurface);
            setContentView(myGLSurface);
        }else{
        	setContentView(R.layout.other_opengl);
        	myGLSurface = (GLSurfaceView)findViewById(R.id.myopengl);
        	view_or_move = (Button)findViewById(R.id.vire_or_move);
        	view_or_move.setOnTouchListener(new OnTouchListener(){
        		
				public boolean onTouch(View view, MotionEvent me) {
					if(!change_yrot){
						change_yrot = true;
					}else{
						change_yrot = false;
					}
					
					return false;
				}
        	});
        }
        myGLSurface.setRenderer(mainSurfaceViewRender);
    }
    
    private void initSurfaceViewRender(){
    	Intent intent = getIntent();
    	int position = intent.getIntExtra(GLActivity.POSITION, 0);
        switch(position){
		case 0:{
			mainSurfaceViewRender = new SurfaceViewRender2D();
			use_layout = false;
		    break;
		}
		case 1:{
			mainSurfaceViewRender = new SurfaceViewRender3D(this);
			use_layout = false;
		    break;
		}
		case 2:{
			mainSurfaceViewRender = new SurfaceViewRenderTouch3D(this);
			use_layout = false;
		    break;
		}
		case 3:{
			mainSurfaceViewRender = new SurfaceViewRenderStar3D(this);
			use_layout = false;
		    break;
		}
		case 4:{
			mainSurfaceViewRender = new SurfaceViewRender3DWalk(this);
			use_layout = true;
		    break;
		}
		}
    }
    
    private void setTouMing(GLSurfaceView glSurfaceView){
    	glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
    	glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    	glSurfaceView.setZOrderOnTop(true);
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		switch(action){
		case MotionEvent.ACTION_DOWN :{
			lastX = x;
			lastY = y;
		}
		case MotionEvent.ACTION_MOVE :{
			float delx = x - lastX;
			float dely = y - lastY;
			boolean option_y_z = false;
			//Touch3D
			SurfaceViewRenderTouch3D.cubeRotY += lastX - x;
			SurfaceViewRenderTouch3D.cubeRotX += lastY - y;
			
			//Walk3D
			if(Math.abs(delx) > 1){
				if(delx < 0){
					if(change_yrot){
						SurfaceViewRender3DWalk.y_rot -=1.0f;
						option_y_z = false;
					}else{
						SurfaceViewRender3DWalk.xpos += Math.sin(SurfaceViewRender3DWalk.x_head*SurfaceViewRender3DWalk.piover180) * 0.1f;
						SurfaceViewRender3DWalk.x_head +=2.0f;
						option_y_z = true;
					}
				}else if(delx > 0){
					if(change_yrot){
						SurfaceViewRender3DWalk.y_rot+=1.0f;
						option_y_z = false;
					}else{
						SurfaceViewRender3DWalk.xpos -= Math.sin(SurfaceViewRender3DWalk.x_head*SurfaceViewRender3DWalk.piover180) * 0.1f;
						SurfaceViewRender3DWalk.x_head -=2.0f;
						option_y_z = true;
					}
					
				}
			}
			if(option_y_z){
				if(Math.abs(dely) > 1){
					if(dely < 0){
						SurfaceViewRender3DWalk.zpos -= Math.cos(SurfaceViewRender3DWalk.x_head*SurfaceViewRender3DWalk.piover180) * 0.05f;
					    if(SurfaceViewRender3DWalk.walkbiasangle >= 359.0f){
					    	SurfaceViewRender3DWalk.walkbiasangle = 0.0f;
					    }else{
					    	SurfaceViewRender3DWalk.walkbiasangle+=10;
					    }
					}else if(dely > 0){
						SurfaceViewRender3DWalk.zpos += Math.cos(SurfaceViewRender3DWalk.x_head*SurfaceViewRender3DWalk.piover180) * 0.05f;
					    if(SurfaceViewRender3DWalk.walkbiasangle <= 1.0f){
					    	SurfaceViewRender3DWalk.walkbiasangle = 359.0f;
					    }else{
					    	SurfaceViewRender3DWalk.walkbiasangle-=10;
					    }
					    
					}
					SurfaceViewRender3DWalk.walkbias = (float)Math.sin(SurfaceViewRender3DWalk.walkbiasangle * SurfaceViewRender3DWalk.piover180)/20.0f;
				}
			}
			
			lastX = event.getX();
			lastY = event.getY();
		}
		
		}
		return super.onTouchEvent(event);
	}
}


















