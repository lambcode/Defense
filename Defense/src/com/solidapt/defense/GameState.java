package com.solidapt.defense;


import com.solidapt.inGame.InGame;
import com.solidapt.mainMenu.TopMenu;


public class GameState {
	private static boolean inGame = false;
	public static boolean isInGame() {
		return inGame;
	}

	public static void setInGame() {
		GameState.inGame = true;
		GameState.inGameMenu = false;
		GameState.topMenu = false;
		
		Util.inGame.release();
		Util.topMenu.release();
	}

	private static boolean inGameMenu = false;
	public static boolean isInGameMenu() {
		return inGameMenu;
	}

	public static void setInGameMenu() {
		GameState.inGameMenu = true;
		GameState.inGame = false;
		GameState.topMenu = false;
	}

	private static boolean topMenu = false;
	public static boolean isTopMenu() {
		return topMenu;
	}

	public static void setTopMenu() {
		GameState.topMenu = true;
		GameState.inGame = false;
		GameState.inGameMenu = false;
		
		Util.inGame.release();
		Util.topMenu.release();
	}

	private static boolean splash = true;
	private static double splashTime = 0;
	public static void updateSplash(double time) {
		splashTime += time;
		if (splashTime > 3 && TextureLoader.hasLoaded()) {
			GameState.splash = false;
			GameState.setTopMenu();
		}
	}
	
	public static boolean isSplash() {
		return splash;
	}

}
