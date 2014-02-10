package com.solidapt.defense.credits;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.store.Button;

public class Credits implements LogicInterface {
	
	LinkedList<String> lines = new LinkedList<String>();
	Button okButton;
	
	public Credits() {
		
		okButton = new Button(Util.getWidth() / 2, Util.getHeight() - 75, 128, 116, TextureLoader.OK_BUTTON_TEXTURE1, TextureLoader.OK_BUTTON_TEXTURE2);
		
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(Util.context.getAssets().open("credits.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (fileIn != null && fileIn.hasNext()) {
			String nextLine = fileIn.nextLine();
			lines.add(nextLine);
		}
	}

	@Override
	public void doLogicLoop(double time) {
		
	}

	@Override
	public void doRenderLoop(GL10 gl) {
		okButton.gameRenderLoop(gl);
		Util.textRenderer.begin(1,1,1,1);
		int lineSpacer = 0;
		for (String i : lines) {
			Util.textRenderer.drawC(i, Util.getWidth()/2, 60 + lineSpacer);
			lineSpacer -= Util.textRenderer.getHeight();
		}
		Util.textRenderer.end();
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		if (okButton.getPressed(x, y)) {
			if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
				GameState.setTopMenu();
			}
			else {
				okButton.setHover(true);
			}
		}
		else {
			okButton.setHover(false);
		}
	}

	@Override
	public Logic getOverlay() {
		return null;
	}

	@Override
	public void removeOverlay() {
		
	}

}
