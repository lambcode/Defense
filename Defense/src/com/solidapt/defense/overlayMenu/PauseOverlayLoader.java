package com.solidapt.defense.overlayMenu;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class PauseOverlayLoader extends Logic {
	
	LogicInterface parent;
	
	public PauseOverlayLoader(LogicInterface parent) {
		this.parent = parent;
	}

	@Override
	public LogicInterface load() {
		return new PauseOverlayMenu(this);
	}
	
	public void kill() {
		parent.removeOverlay();
	}

}
