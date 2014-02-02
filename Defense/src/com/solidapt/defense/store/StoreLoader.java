package com.solidapt.defense.store;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class StoreLoader extends Logic {

	@Override
	public LogicInterface load() {
		return new Store();
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
