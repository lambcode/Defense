package com.solidapt.defense;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

public class SoundLoader {
    private static SoundPool soundPool = null;
    private static MediaPlayer mp0;
    private static MediaPlayer mp;
    
    public static MusicFile gameMusic;
    public static MusicFile menuMusic;
    
    private static MusicFile lastLoaded;
    
    public static void loadSounds() {
    	gameMusic = new MusicFile(R.raw.constance);
    	menuMusic = new MusicFile(R.raw.cannery0, R.raw.cannery_loop);
        //Util.context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    	//int r = soundPool.load(Util.context, gameMusic, 1);
    	//soundPool.play(r, 1.0f, 1.0F, 1, 1, 1.0f);
    }
    
    public static void startMusic(MusicFile ID) {
    	if (mp == null) {
    		if (mp0 != null) mp0.release();
    		mp0 = null;
    		createPlayer(ID);
    		//mp0 = MediaPlayer.create(Util.context, menuMusic0);
    		//mp0.setNextMediaPlayer(mp);
        }
    	else if (lastLoaded != ID) {
    		mp.release();
    		mp = null;
    		if (mp0 != null) mp0.release();
    		mp0 = null;
    		
    		createPlayer(ID);
    	}
    	
    	if (!mp.isPlaying() && mp0 == null) {
    		mp.start();
    	}
    	
    	if (mp.isPlaying()) {
    		if (mp0 != null) mp0.release();
    		mp0 = null;
    	}
    	
    	if (mp0 != null && !mp0.isPlaying()) {
    		mp0.start();
    	}
    }

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private static void createPlayer(MusicFile ID) {
		mp = MediaPlayer.create(Util.context, ID.nextID);
		mp.setLooping(true);
		
		
		if (ID.hasNext()) {
			mp0 = MediaPlayer.create(Util.context, ID.ID);
			mp0.setNextMediaPlayer(mp);
		}
		SoundLoader.lastLoaded = ID;
	}
    
    public static void stopMusic(int ID) {
    	
    }
    
    public static void pauseAllMusic() {
    	if (mp != null && mp0 == null) {
    		mp.pause();
        }
    	if (mp0 != null) {
    		mp0.pause();
    	}
    }
    
	public static void resumeAllMusic() {
    	if (mp != null && mp0 == null) {
    		mp.start();
    	}
    	if (mp0 != null) {
    		mp0.start();
    	}
    }
    
    static class MusicFile {
    	
    	int ID;
    	int nextID;
    	
    	public MusicFile(int ID, int nextID) {
    		this.ID = ID;
    		this.nextID = nextID;
    	}
    	
    	public MusicFile(int ID) {
    		this(-1, ID);
    	}
    	
    	public boolean hasNext() {
    		return ID != -1;
    	}
    	
    }
}
