package com.solidapt.defense;


import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.*;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.defense.credits.CreditsLoader;
import com.solidapt.defense.store.StoreLoader;
import com.solidapt.inGame.InGameLoader;
import com.solidapt.mainMenu.TopMenuLoader;
import com.solidapt.textRender.GLText;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.util.Log;


public class Util {
	public static boolean logoHasRendered;
    public static GL10 mygl;
    public static Context context;
    public static boolean gameRunning = false;
    private static int width;
    private static int height;
    public static GameObject turret;
    public static GLText textRenderer;
    public static boolean muted;
    public static int highScore;
    
    public static MissileInformation[] missileInformation = new MissileInformation[5];
    
    public static InGameLoader inGame = new InGameLoader();
    public static TopMenuLoader topMenu = new TopMenuLoader();
    public static StoreLoader inStore = new StoreLoader();
    public static CreditsLoader credits = new CreditsLoader();
    
    private static final int STANDARD_COST = 20;
    private static final int FLOWER_COST = 100;
    private static final int HORIZON_COST = 300;
    private static final int CHANDALIER_COST = 300;

    private static final float BASIC_HEAT = .5f;
    private static final float STANDARD_HEAT = .1f;
    private static final float FLOWER_HEAT = .2f;
    private static final float HORIZON_HEAT = .4f;
    private static final float CHANDALIER_HEAT = .4f;
	
    private static float ratio;
    
	//private static final String MY_AD_UNIT_ID = "ca-app-pub-7386125560018860/4481562433";
	private static final String MY_AD_UNIT_ID = "ca-app-pub-7386125560018860/2200590438";
	private static InterstitialAd interstitial;
	
    public static float getRatio() {
        return ratio;
    }

    public static void setRatio(float ratio) {
        Util.ratio = ratio;
    }


	public static SurfaceTexture getImage(int spriteID) {
         return null;
    }
	
	
	public static void setWidth(int width) {
		Util.width = width;
	}
	
	public static int getWidth() {
		return width;
	}
    
    public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Util.height = height;
	}
	
	public static void setUpMissileInformation() {
		SharedPreferences prefs = context.getSharedPreferences("Prefs", 0);

		ScoreTracker.setTotalScore(prefs.getInt("Score", 0));
		muted = prefs.getBoolean("Muted", false);
		highScore = prefs.getInt("High Score", 0);
		missileInformation[0] = new MissileInformation(prefs.getInt("Missile1", -1), "Basic Missile", TextureLoader.MISSILE_TEXTURE, 0, BASIC_HEAT);
		missileInformation[1] = new MissileInformation(prefs.getInt("Missile2", 100), "Standard Missile", TextureLoader.MISSILE_TEXTURE, STANDARD_COST, STANDARD_HEAT);
		missileInformation[2] = new MissileInformation(prefs.getInt("Missile3", 0), "Flower", TextureLoader.RADIO_ACTIVE_MISSILE_TEXTURE, FLOWER_COST, FLOWER_HEAT);
		missileInformation[3] = new MissileInformation(prefs.getInt("Missile4", 0), "Horizon", TextureLoader.HORIZON_MISSILE_TEXTURE, HORIZON_COST, HORIZON_HEAT);
		missileInformation[4] = new MissileInformation(prefs.getInt("Missile5", 0), "Chandalier", TextureLoader.CHANDELIER_TEXTURE, CHANDALIER_COST, CHANDALIER_HEAT);
	}
	
	public static void saveMissileInformation() {
		SharedPreferences.Editor prefs = context.getSharedPreferences("Prefs", 0).edit();

		prefs.putInt("Score", ScoreTracker.getTotalScore());
		prefs.putBoolean("Muted", muted);
		prefs.putInt("High Score", highScore);
		prefs.putInt("Missile1", missileInformation[0].getCount());
		prefs.putInt("Missile2", missileInformation[1].getCount());
		prefs.putInt("Missile3", missileInformation[2].getCount());
		prefs.putInt("Missile4", missileInformation[3].getCount());
		prefs.putInt("Missile5", missileInformation[4].getCount());
		prefs.commit();
	}
    
    public static void loadAds(Activity activity) {
		
    	// Create the interstitial.
        interstitial = new InterstitialAd(activity);
        interstitial.setAdUnitId(MY_AD_UNIT_ID);

	}

	public static void loadNewAd() {
		// Create ad request.
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("F09D920454F8EFB86E908C7CE15D6339").addTestDevice("B9FD25CA3B0CAEA47A46ACAA2DA08329").build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
	}
	
	public static boolean displayInterstitialAd() {
	    if (interstitial.isLoaded()) {
	      interstitial.show();
	      return true;
	    }
	    else {
	    	Log.d("Solidapt", "Interstitial ad was not ready to be shown.");
	    }
	    return false;
	  }

	public static void resetMissileInformation() {
		setUpMissileInformation();
	}
}
