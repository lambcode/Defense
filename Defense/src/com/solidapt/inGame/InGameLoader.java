package com.solidapt.inGame;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class InGameLoader extends Logic {

	@Override
	public LogicInterface load() {
		return new InGame();
	}
}
