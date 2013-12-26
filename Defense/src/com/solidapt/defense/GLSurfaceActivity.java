package com.solidapt.defense;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.solidapt.citydefense.objects.ObjectList;

/**
 * Created by Brian on 5/29/13.
 */
public class GLSurfaceActivity extends Activity {

    private MyGLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

		ObjectList.initializeArrarys();
		
		Util.gameRunning = true;
		
    }
    
    @Override
	public boolean onTouchEvent(MotionEvent e) {
    	mGLView.myRenderer.onTouchEvent(e);
		return true;
	}
    
    @Override
    public void onPause() {
    	super.onPause();
    	Util.gameRunning = false;
    	SoundLoader.pauseAllMusic();
    	mGLView.setVisibility(mGLView.GONE);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    	if (hasFocus && mGLView.getVisibility() == mGLView.GONE) {
    		mGLView.setVisibility(mGLView.VISIBLE);
    	}
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Util.gameRunning = true;
    	SoundLoader.resumeAllMusic();
    	LogicLoop.lastUpdate = System.currentTimeMillis();
    }
}

