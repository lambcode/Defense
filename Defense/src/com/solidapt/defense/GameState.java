package com.solidapt.defense;


public class GameState {
	private static boolean inGame = false;
	public static boolean isInGame() {
		return inGame;
	}

	public static void setInGame(boolean inGame) {
		GameState.inGame = inGame;
	}

	private static boolean inGameMenu = false;
	public static boolean isInGameMenu() {
		return inGameMenu;
	}

	public static void setInGameMenu(boolean inGameMenu) {
		GameState.inGameMenu = inGameMenu;
	}

	private static boolean topMenu = false;
	public static boolean isTopMenu() {
		return topMenu;
	}

	public static void setTopMenu(boolean topMenu) {
		GameState.topMenu = topMenu;
	}

	private static boolean splash = true;
	private static double splashTime = 0;
	public static void updateSplash(double time) {
		splashTime += time;
		if (splashTime > 3 && TextureLoader.hasLoaded()) {
			GameState.splash = false;
			GameState.inGame = true;
		}
	}
	
	public static boolean isSplash() {
		return splash;
	}

}
