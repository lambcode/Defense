package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class Product extends StaticObject {
	
	MissileInformation info;
	ColorSquare backDrop;
	
	public Product(int xCoord, int yCoord, MissileInformation info) {
		super(xCoord, yCoord, 150, 150);
		this.backDrop = new ColorSquare(Util.getWidth()/2, yCoord, Util.getWidth()+10, 140, .1f, .1f, .1f, 1);
		this.myTexture = info.getTexture();
		this.info = info;
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void gameRenderLoop(GL10 gl) {
		backDrop.gameRenderLoop(gl);
		super.gameRenderLoop(gl);
	}
	
	@Override 
	public void render(GL10 gl) {
		gl.glRotatef(45, 0, 0, 1);
		this.draw(gl);
		gl.glRotatef(-45, 0, 0, 1);
		
		Util.textRenderer.begin(1, 1, 1, 1);
		Util.textRenderer.draw(info.getName(), 90, -10);
		Util.textRenderer.end();
		
		Util.textRenderer.begin(0, .8f, 0, 1);
		Util.textRenderer.setScale(.7f);
		Util.textRenderer.draw("Cost: " + info.getCost(), 90, 40);
		Util.textRenderer.end();
	}

}
