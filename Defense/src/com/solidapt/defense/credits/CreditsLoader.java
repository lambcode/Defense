package com.solidapt.defense.credits;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class CreditsLoader extends Logic {

	@Override
	public LogicInterface load() {
		return new Credits();
	}

	@Override
	public void kill() {
		
	}

}
