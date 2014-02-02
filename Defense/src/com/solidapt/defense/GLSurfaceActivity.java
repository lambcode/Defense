package com.solidapt.defense;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
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

		
		Util.gameRunning = true;

		Util.loadAds(this);
		
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
    	int error = 0;
    	if ((error = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)) != ConnectionResult.SUCCESS) {
    		GooglePlayServicesUtil.getErrorDialog(error, this, 1001).show();
    	}
    	else {
    		Util.gameRunning = true;
    		SoundLoader.resumeAllMusic();
    		LogicLoop.lastUpdate = System.currentTimeMillis();
    	}
    }
}

