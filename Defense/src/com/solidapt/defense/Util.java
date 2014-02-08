package com.solidapt.defense;


import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.defense.store.StoreLoader;
import com.solidapt.inGame.InGameLoader;
import com.solidapt.mainMenu.TopMenuLoader;
import com.solidapt.textRender.GLText;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.media.SoundPool;


public class Util {
	public static boolean logoHasRendered;
    public static GL10 mygl;
    public static Context context;
    public static boolean gameRunning = false;
    private static int width;
    private static int height;
    public static GameObject turret;
    public static GLText textRenderer;
    
    public static MissileInformation[] missileInformation = new MissileInformation[4];
    
    public static InGameLoader inGame = new InGameLoader();
    public static TopMenuLoader topMenu = new TopMenuLoader();
    public static StoreLoader inStore = new StoreLoader();
    
    private static final int STANDARD_COST = 20;
    private static final int FLOWER_COST = 100;
    private static final int HORIZON_COST = 300;
    private static final int CHANDALIER_COST = 300;
	
    private static float ratio;
	
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
		missileInformation[0] = new MissileInformation(prefs.getInt("Missile1", 100), "Standard Missile", TextureLoader.MISSILE_TEXTURE, STANDARD_COST);
		missileInformation[1] = new MissileInformation(prefs.getInt("Missile2", 0), "Flower", TextureLoader.RADIO_ACTIVE_MISSILE_TEXTURE, FLOWER_COST);
		missileInformation[2] = new MissileInformation(prefs.getInt("Missile3", 0), "Horizon", TextureLoader.HORIZON_MISSILE_TEXTURE, HORIZON_COST);
		missileInformation[3] = new MissileInformation(prefs.getInt("Missile4", 0), "Chandalier", TextureLoader.HORIZON_MISSILE_TEXTURE, CHANDALIER_COST);
	}
	
	public static void saveMissileInformation() {
		SharedPreferences.Editor prefs = context.getSharedPreferences("Prefs", 0).edit();

		prefs.putInt("Score", ScoreTracker.getTotalScore());
		prefs.putInt("Missile1", missileInformation[0].getCount());
		prefs.putInt("Missile2", missileInformation[1].getCount());
		prefs.putInt("Missile3", missileInformation[2].getCount());
		prefs.putInt("Missile4", missileInformation[3].getCount());
		prefs.commit();
	}

	public static void resetMissileInformation() {
		setUpMissileInformation();
	}
}
