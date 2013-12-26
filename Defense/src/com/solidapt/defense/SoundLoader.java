package com.solidapt.defense;


import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundLoader {
    private static SoundPool soundPool = null;
    private static MediaPlayer mp0;
    private static MediaPlayer mp;
    
    public static int gameMusic;
    public static int menuMusic0;
    public static int menuMusic;
    
    public static void loadSounds() {
    	gameMusic = R.raw.constance;
    	menuMusic0 = R.raw.cannery0;
    	menuMusic = R.raw.cannery_loop;
        //Util.context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    	//int r = soundPool.load(Util.context, gameMusic, 1);
    	//soundPool.play(r, 1.0f, 1.0F, 1, 1, 1.0f);
    }
    
    public static void startMusic(int ID) {
    	if (mp == null) {
    		mp = MediaPlayer.create(Util.context, ID);
    		mp.setLooping(true);
    		//mp0 = MediaPlayer.create(Util.context, menuMusic0);
    		//mp0.setNextMediaPlayer(mp);
        }
    	if (!mp.isPlaying()) {
    		mp.start();
    	}
    }
    
    public static void stopMusic(int ID) {
    	
    }
    
    public static void pauseAllMusic() {
    	if (mp != null) {
    		mp.pause();
            //mp0.release();
            //mp0 = null;
        }
    }
    
    @SuppressLint("NewApi")
	public static void resumeAllMusic() {
    	if (mp != null) {
    		mp.start();
    	}
    }
}
