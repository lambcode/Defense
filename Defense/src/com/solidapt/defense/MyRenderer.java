package com.solidapt.defense;

import java.util.ArrayList;

import MainMenu.TopMenu;
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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Brian on 5/29/13.
 */
public class MyRenderer implements GLSurfaceView.Renderer {

	
	GameObject logo;
    //GameObject xp2 = new HostileMissile(220, 500, 100, 100, 150, 800, 1, 90);
    
    public MyRenderer(Context context) {
    	super();
		Util.context = context;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	Util.mygl = gl;

    	
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
        Util.setWidth(width);
        Util.setHeight(height);
    	if (logo == null) {
    		logo = new Logo();
    	}
        if (height == 0) height = 1;
        float ratio = ((float)width/height);
        Util.setRatio(ratio);

        gl.glViewport(0, 0, width, height); //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
        gl.glLoadIdentity(); 					//Reset The Projection Matrix


        gl.glOrthof(0, width, height, 0, -1f, 1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
        gl.glLoadIdentity();
    }

    
    public void onDrawFrame(GL10 gl) {
    	LogicLoop.doLoop();
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	if (GameState.isSplash()) {
    		logo.gameRenderLoop(gl);
        	Util.logoHasRendered = true; // after logo has rendered for the first time then we can load textures
    	}
    	
    	if (Util.gameRunning) {
    		if (GameState.isInGame()) {
    			for (int x = 0; x < ObjectList.buildings.size(); x++)
    				if (ObjectList.buildings.get(x) != null)ObjectList.buildings.get(x).gameRenderLoop(gl);
    			for (int x = 0; x < ObjectList.hostileMissiles.size(); x++)
    				if (ObjectList.hostileMissiles.get(x) != null) ObjectList.hostileMissiles.get(x).gameRenderLoop(gl);
    			for (int x = 0; x < ObjectList.missiles.size(); x++)
    				if (ObjectList.missiles.get(x) != null)ObjectList.missiles.get(x).gameRenderLoop(gl);
    			if (Util.turret != null) Util.turret.gameRenderLoop(gl);
    		}
    		else if (GameState.isTopMenu()) {
    			TopMenu.renderLoop(gl);
    		}
    	}
    	Thread.yield();
    }
	
	public void onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		double radians = Math.atan2(Util.getHeight() - y, Util.getWidth()/2 - x);
		
		if (GameState.isInGame()) {
			if (Util.turret != null) Util.turret.setRotation((float) Math.toDegrees(radians)-90);
		}
		
		if (GameState.isTopMenu()) {
			GameState.setInGame();
		}
		
		if (/*hostileMissiles.size() > 0 &&*/ (e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN && GameState.isInGame()){
			//((HostileMissile)hostileMissiles.get(0)).setTargetPoint((int)e.getX(), (int)e. getY());
			StandardMissile newMissile = new StandardMissile(Util.getWidth()/2, Util.getHeight(), 30, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
			ObjectList.addToList(ObjectList.missiles, newMissile);
		}
	}
	
	private void gameManage() {
		if (ObjectList.hostileMissiles.size() < 1) {
			//hostileMissiles.add(new HostileMissile(400, 600, 60, 60, 200, 800, 1, 100));
			//((HostileMissile)hostileMissiles.get(0)).setTexture();
		}
	}

}