package com.solidapt.defense.overlayMenu;

import com.solidapt.defense.GameState;

public class PauseOverlayMenu extends OverlayMenu implements OverlayButtonListener{
	
	public PauseOverlayMenu(PauseOverlayLoader myLoader) {
		super(myLoader, "Paused", "Main Menu", "Continue");
		this.setListener(this);
	}
	
	@Override
	public void buttonPressed(int buttonIndex) {
		if (buttonIndex == 0) {
			GameState.setTopMenu();
		}
		else if (buttonIndex == 1) {
			this.kill();
		}
	}

}
