package com.solidapt.defense;

public class Score {
	
	String score;
	float x;
	float y;
	float transparency = 1;
	
	public Score(int score, float x, float y) {
		if (score > 0) {
			this.score = "+" + String.valueOf(score);
		}
		else {
			this.score = String.valueOf(score);
		}
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		Util.textRenderer.begin(.2f, 1f, .2f, transparency);
		Util.textRenderer.setScale(.65f);
		Util.textRenderer.drawC(score, x, y);
		Util.textRenderer.end();
	}
	
	public void updateLoopLogic(double time) {
		this.y -= time * 30;
		this.transparency -= .5 * time;
	}

	public boolean needsRemoval() {
		return transparency <= 0;
	}
}