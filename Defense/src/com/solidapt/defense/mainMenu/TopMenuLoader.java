package com.solidapt.defense.mainMenu;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class TopMenuLoader extends Logic {

	@Override
	public LogicInterface load() {
		return new TopMenu();
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

}
