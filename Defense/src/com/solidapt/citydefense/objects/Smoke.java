package com.solidapt.citydefense.objects;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.TextureLoader;

public class Smoke extends StaticObject {
	
	Random rnd = new Random();
	LinkedList<SmokeParticle> particles = new LinkedList<SmokeParticle>();
	boolean looping;
	int loopingCount;

	public Smoke(int xCoord, int yCoord, boolean looping) {
		super(xCoord, yCoord, 1, 1);
		this.looping = looping;
	}

	@Override
	public void gameLoopLogic(double time) {
		if (loopingCount >= 30 && particles.size() == 0) {
			this.markForRemoval();
		}
		if (particles.size() < 30 && rnd.nextDouble() < .2 && loopingCount < 30) {
			if (!looping) {
				loopingCount++;
			}
			float xInit = (float) rnd.nextGaussian();
			float yInit = rnd.nextFloat();
			float xFinal = xInit * 3;
			float yFinal = yInit + 40;
			particles.add(new SmokeParticle((float)xInit, (float)yInit, (float)xFinal, (float)yFinal, 2, 2));
		}
		
		Iterator<SmokeParticle> i = particles.iterator();
		while (i.hasNext()) {
			if (i.next().gameLoopLogic2(time)) {
				i.remove();
			}
		}
	}
	
	@Override
	public void render(GL10 gl) {
		for (GameObject i : particles) {
			i.gameRenderLoop(gl);
		}
		gl.glColor4f(1f, 1f, 1f, 1f);
	}
	
	class SmokeParticle extends StaticObject {
		
		float addX;
		float addY;
		
		float currX;
		float currY;
		
		float size = 1f;
		float transparency = 1f;

		public SmokeParticle(float xCoord, float yCoord, float xFinal, float yFinal, int width, int height) {
			super((int)xCoord, (int)yCoord, width, height);
			currX = xCoord;
			currY = yCoord;
			addX = (xFinal - xCoord) * .5f;
			addY = -(yFinal - yCoord) * .5f;
			this.myTexture = TextureLoader.SMOKE_PARTICLE_TEXTURE;
		}

		/**
		 * Returns true if object needs removal
		 */
		public boolean gameLoopLogic2(double time) {
			this.setRotation((float) (this.getRotation() + (10 * time)));
			this.setXCoord((int) (currX = (float) (currX + (addX * time))));
			this.setYCoord((int)(currY = (float) (currY + (addY * time))));
			size = (float) (size + 6 * time);
			this.setWidth((int) size);
			this.setWidth((int) size);
			transparency -= .3 * time;
			
			return transparency <= 0;
		}
		
		@Override
		public void render(GL10 gl) {
			gl.glColor4f(1f, 1f, 1f, transparency);
			gl.glScalef(size, size, 0);
			this.draw(gl);
			gl.glScalef(size, size, 0);
		}

		@Override
		public void gameLoopLogic(double time) {
			//NOT USED
			//USE gameLoopLogic2 instead
		}
		
	}

}
