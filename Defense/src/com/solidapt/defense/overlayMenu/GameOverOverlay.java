package com.solidapt.defense.overlayMenu;

import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.SoundLoader;

public class GameOverOverlay extends OverlayMenu implements OverlayButtonListener {

	public GameOverOverlay(Logic myLoader) {
		super(myLoader, "Game Over", "Main Menu", "Store", "Retry");
		this.setListener(this);
		SoundLoader.startMusic(SoundLoader.gameOverMusic);
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

}
