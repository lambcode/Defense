package com.solidapt.defense.overlayMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.ScoreTracker;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;

public class GameOverOverlay extends OverlayMenu implements OverlayButtonListener {
	
	int finalScore;
	float scoreCounterPosition = 0;
	float speedToCount = 60f;
	boolean scoreReached = false;
	
	boolean highScore = true;
	float highScoreScale = 0;
	boolean highScoreGrow = true;
	
	private static final float SCALE_MAX = 1.3f;
	private static final float SCALE_MIN = .7f;
	
	private static final int SCORE_DISPLAY_POSY = 150;
	private static final int NEW_HIGH_SCORE_DISPLAY_POSY = 100;
	private static final int HIGH_SCORE_DISPLAY_POSY = 200;

	public GameOverOverlay(Logic myLoader) {
		super(myLoader, "Game Over", "Main Menu", "Depot", "Retry");
		this.setListener(this);
		finalScore = ScoreTracker.getLastGameScore();
		speedToCount *= finalScore / 50.0;
		if (this.highScore = Util.highScore < finalScore) {
			Util.highScore = finalScore;
		}
		
		Util.saveMissileInformation(); 
	}

	@Override
	public void buttonPressed(int buttonIndex) {
		if (buttonIndex == 0) {
			GameState.setTopMenu();
		}
		else if (buttonIndex == 1) {
			GameState.setInStore();
		}
		else if (buttonIndex == 2) {
			GameState.setInGame();
		}
	}
	
	@Override
	public void doLogicLoop(double time) {
		super.doLogicLoop(time);
		
		if (scoreCounterPosition < finalScore) {
			scoreCounterPosition += speedToCount * time;
		}
		else if (!scoreReached){
			scoreReached = true;
			scoreCounterPosition = finalScore;
			SoundLoader.playHighScore(Util.getWidth()/2, Util.getHeight()/2);
		}
		
		if (scoreReached && highScore) {
			if (highScoreGrow) {
				highScoreScale += .7 * time;
				if (highScoreScale > SCALE_MAX) {
					highScoreGrow = false;
				}
			}
			else {
				highScoreScale -= .7 * time;
				if (highScoreScale < SCALE_MIN) {
					highScoreGrow = true;
				}
			}
		}
	}
	
	@Override
	public void doRenderLoop(GL10 gl) {
		super.doRenderLoop(gl);
		
		Util.textRenderer.begin(1,1,1,1);
		Util.textRenderer.drawC("Score: " + ((int)Math.floor(scoreCounterPosition)), Util.getWidth()/2, SCORE_DISPLAY_POSY);
		Util.textRenderer.drawC("High Score: " + Util.highScore, Util.getWidth()/2, HIGH_SCORE_DISPLAY_POSY);
		Util.textRenderer.end();
		
		if (highScore) {
			Util.textRenderer.begin(0,1,1,1);
			Util.textRenderer.setScale(highScoreScale);
			Util.textRenderer.drawC("New High Score!", Util.getWidth()/2, NEW_HIGH_SCORE_DISPLAY_POSY);
			Util.textRenderer.end();
		}
	}

}
