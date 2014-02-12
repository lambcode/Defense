package com.solidapt.defense.inGame;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class InGameLoader extends Logic {

	@Override
	public LogicInterface load() {
		return new InGame();
	}

	@Override
	public void kill() {
	}
}
