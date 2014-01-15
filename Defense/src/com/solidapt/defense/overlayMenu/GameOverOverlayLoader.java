package com.solidapt.defense.overlayMenu;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class GameOverOverlayLoader extends Logic {

	@Override
	public LogicInterface load() {
		return new GameOverOverlay(this);
	}

	@Override
	public void kill() {
	}

}
