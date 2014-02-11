package com.solidapt.defense;

import java.util.Iterator;
import java.util.LinkedList;


public class ScoreTracker {
	private static LinkedList<Score> scoresToDisplay = new LinkedList<Score>();
	private static int currentScore = 0;
	private static int totalScore = 0;
	private static double timeElapsed = 0;
	
	public static void addScore(int score, int xCoord, int yCoord) {
		scoresToDisplay.add(new Score(score, xCoord, yCoord));
		currentScore += score;
		totalScore += score;
	}
	
	public static void gameLoopLogic(double time) {
		timeElapsed += time;
		Iterator<Score> i = scoresToDisplay.iterator();
		while (i.hasNext()) {
			Score curr = i.next();
			if (!curr.needsRemoval()) {
				curr.updateLoopLogic(time);
			}
			else {
				i.remove();
			}
		}
	}
	
	public static void gameRenderLoop() {
		for (Score i : scoresToDisplay) {
			i.draw();
		}
		Util.textRenderer.begin(.8f, .8f, .8f, 1f);
		Util.textRenderer.setScale(.8f);
		Util.textRenderer.draw(String.format("Score: %08d", currentScore), 120, 35);
		Util.textRenderer.draw(String.format("Time: %ds", (int)timeElapsed), 420, 35);
		Util.textRenderer.end();
	}
	
	public static void reset() {
		scoresToDisplay.clear();
		currentScore = 0;
		timeElapsed = 0;
	}
	
	public static void setTotalScore(int score) {
		totalScore = score;
	}
	
	public static int getTotalScore() {
		return totalScore;
	}

	public static int getLastGameScore() {
		return currentScore;
	}
	
}