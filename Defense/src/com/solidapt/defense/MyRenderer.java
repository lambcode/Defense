package com.solidapt.defense;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

import com.solidapt.citydefense.objects.Background;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.citydefense.objects.Logo;
import com.solidapt.citydefense.objects.ObjectList;
import com.solidapt.citydefense.objects.StandardMissile;
import com.solidapt.inGame.InGame;
import com.solidapt.mainMenu.TopMenu;
import com.solidapt.textRender.GLText;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Brian on 5/29/13.
 */
public class MyRenderer implements GLSurfaceView.Renderer {

	
	GameObject logo;
    //GameObject xp2 = new HostileMissile(220, 500, 100, 100, 150, 800, 1, 90);
	float xRatio = 0;
	float yRatio = 0;
    
    public MyRenderer(Context context) {
    	super();
		Util.context = context;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	Util.mygl = gl;
    	Util.textRenderer = new GLText(gl, Util.context.getAssets());
    	
    	gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
    	gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
    	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
    	gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
    	gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
    	gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
    	
    	
        // Set the background frame color
        //gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //gl.glClearDepthf(1.0f);
        //gl.glEnable(GL10.GL_DEPTH_TEST);
        //gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glDisable(GL10.GL_DITHER);
    	TextureLoader.setUnloaded();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
    	int width2 = 1280;
    	int height2 = 768;
    	
    	xRatio = ((float) width2) / width;
    	yRatio = ((float) height2) / height;
        
        if (height2 == 0) height2 = 1;
        float ratio = ((float)width/height);
        float ratio2 = ((float)width2/height2);
        Util.setRatio(ratio);
        Util.setWidth(width2);
        
        height2 = (int) (height2 * (ratio/ratio2));
        Util.setHeight(height2);
    	if (logo == null) {
    		logo = new Logo();
    	}

        gl.glViewport(0, 0, width, height); //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
        gl.glLoadIdentity(); 					//Reset The Projection Matrix


        gl.glOrthof(0, width2, height2 , 0, -1f, 1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
        gl.glLoadIdentity();
    }

    
    public void onDrawFrame(GL10 gl) {
    	if (Util.logoHasRendered) LogicLoop.doLoop();
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	if (GameState.isSplash()) {
    		logo.gameRenderLoop(gl);
        	Util.logoHasRendered = true; // after logo has rendered for the first time then we can load textures
    	}
    	
    	if (Util.gameRunning) {
    		if (GameState.isInGame()) {
    			Util.inGame.renderLoop(gl);
    		}
    		else if (GameState.isTopMenu()) {
    			Util.topMenu.renderLoop(gl);
    		}
    	}
    	Thread.yield();
    }
	
	public void onTouchEvent(MotionEvent e) {
		float x = e.getX() * xRatio;
		float y = e.getY() * yRatio;

		
		if (GameState.isInGame()) {
			Util.inGame.touchEvent(e, x, y);
		}
		
		if (GameState.isTopMenu()) {
			Util.topMenu.touchEvent(e, x, y);
		}
		
		
	}

}