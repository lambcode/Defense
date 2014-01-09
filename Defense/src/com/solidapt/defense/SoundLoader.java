package com.solidapt.defense;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

public class SoundLoader {
    private static SoundPool soundPool = null;
    private static MediaPlayer mp;
    
    public static MusicFile gameMusic;
    public static MusicFile menuMusic;
    
    private static MusicFile lastLoaded;
    
    private static int[] explosions = new int[3];
    
    public static void loadSounds() {
    	gameMusic = new MusicFile(R.raw.constance, .5f);
    	menuMusic = new MusicFile(R.raw.hitman, 1.0f);
        //Util.context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    	
    	soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    	explosions[0] = soundPool.load(Util.context, R.raw.explosion1, 1);
    	explosions[1] = soundPool.load(Util.context, R.raw.explosion2, 1);
    	explosions[2] = soundPool.load(Util.context, R.raw.explosion3, 1);
    	//soundPool.play(r, 1.0f, 1.0F, 1, 1, 1.0f);
    }
    
    public static void playExplosion(int x, int y) {
    	float lVol = .5f;
    	float rVol = .5f;
    	
    	int index = (int) Math.floor(Math.random() * 3);
    	int center = Util.getWidth() / 2;
    	int difference = center - x;
    	float percentOfHalf = Math.abs(difference) / center;
    	float halfOfPercent = percentOfHalf / 2;
    	
    	
    	if (difference < 0) {
    		lVol += halfOfPercent;
    		rVol -= halfOfPercent;
    	}
    	else {
    		lVol -= halfOfPercent;
    		rVol += halfOfPercent;
    	}
    	
    	soundPool.play(explosions[index], lVol, rVol, 1, 0, 1f);
    }
    
    public static void startMusic(MusicFile ID) {
    	if (mp == null) {
    		createPlayer(ID);
        }
    	else if (lastLoaded != ID) {
    		mp.release();
    		mp = null;
    		
    		createPlayer(ID);
    	}
    	
    	if (!mp.isPlaying()) {
    		mp.start();
    	}
    }


	private static void createPlayer(MusicFile ID) {
		mp = MediaPlayer.create(Util.context, ID.ID);
		mp.setVolume(ID.volume, ID.volume);
		mp.setLooping(true);
		SoundLoader.lastLoaded = ID;
	}
    
    public static void stopMusic(int ID) {
    	
    }
    
    public static void pauseAllMusic() {
    	if (mp != null) {
    		mp.pause();
        }
    	if (soundPool != null) soundPool.autoPause();
    }
    
	public static void resumeAllMusic() {
    	if (mp != null) {
    		mp.start();
    	}
    	if (soundPool != null) soundPool.autoResume();
    }
    
    static class MusicFile {
    	
    	int ID;
    	float volume;
    	
    	public MusicFile(int ID, float volume) {
    		this.ID = ID;
    		this.volume = volume;
    	}
    	
    }
}
