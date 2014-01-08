package com.solidapt.defense.overlayMenu;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class OverlayLoader extends Logic {
	
	LogicInterface parent;
	
	public OverlayLoader(LogicInterface parent) {
		this.parent = parent;
	}

	@Override
	public LogicInterface load() {
		return new OverlayMenu(this);
	}
	
	public void kill() {
		parent.removeOverlay();
	}

}
