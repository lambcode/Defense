package com.solidapt.citydefense.objects;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.GLSquare;
import com.solidapt.defense.TextureLoader;

/**
 * Buildings that missiles attract to
 * @author Brian
 *
 */
public class Building extends Structure {
	boolean exploding = false;

	GLSquare placemat1;
	GLSquare placemat2;
	GLSquare placemat3;
	GLSquare placemat4;
	
	LinkedList<Smoke> smoke = new LinkedList<Smoke>();
	Random rnd = new Random();
	
	boolean[] damaged = new boolean[4];
	
	public Building(int xCoord, int yCoord, int width, int height, int spriteID, int health) {
		super(xCoord, yCoord, width, height, spriteID, health);
		this.myTexture = TextureLoader.BUILDING_COLLAPSE_TEXTURE;
		this.setCurrentFrame(myTexture.getFrames());
		
		int square = width > height ? width : height;
        placemat1 = new GLSquare(square/2, square/2);
        placemat2 = new GLSquare(square/2, square/2);
        placemat3 = new GLSquare(square/2, square/2);
        placemat4 = new GLSquare(square/2, square/2);
	}

	private boolean dead;
	
	@Override
	public void kill() {
		this.dead = true;
	}

	private GameObject lastExplosion;
	@Override
	public void gameLoopLogic(double time) {
		removeSmoke();
		updateSmoke(time);
		GameObject testExplosionResult = ExplosionTracker.collisionDetected(this);
		if (testExplosionResult != null && testExplosionResult != lastExplosion && !this.exploding) {
			lastExplosion = testExplosionResult;
			boolean found = false;
			if (!damaged[0] || !damaged[1] || !damaged[2] || !damaged[3]) {
				while(!found) {
					int i = (int) Math.floor(Math.random()*4);
					if (!damaged[i]) {
						found =true;
						damaged[i] = true;
						addSmoke(i);
						this.setCurrentFrame(0);//Restart debris/building collapse
					}
				}
			}
			else if (!this.exploding) {
				this.setCurrentFrame(0); //Resart debris/building collapse
				this.exploding = true;
				for (Smoke i : smoke) {
					i.stopSmoke();
				}
				addSmoke(-1);
				addSmoke(-1);
			}
		}

		//Update Collapse Texture
		if (this.getCurrentFrame() < myTexture.getFrames()) this.updateFrame(time);
		
	}
	
	private void updateSmoke(double time) {
		for (GameObject i : smoke) {
			i.gameLoopLogic(time);
		}
	}

	private void removeSmoke() {
		Iterator<Smoke> i = smoke.iterator();
		while (i.hasNext()) {
			if (i.next().needsRemoval()) {
				i.remove();
			}
		}
	}

	private void addSmoke(int i) {
		
		int width = this.getWidth();
		int height = this.getHeight();
		int x = 0;
		int y = height/2;
		float xGive = 0;
		float yGive = 0;
		if (i == 0 || i == 1) {
			y = -height / 4;
			xGive = width/5;
		}
		else if (i == 2 || i == 3){
			y = height / 4;
			xGive = width / 4;
		}
		
		if (i == 0 || i == 2) {
			x = -width / 4;
			yGive = height/4;
		}
		else if (i == 1 || i == 3) {
			x = width / 4;
			yGive = height/4;
		}
		
		int smokeX = (int) (x + ((rnd.nextFloat() * xGive) - (xGive / 2)));
		int smokeY = (int) (y + ((rnd.nextFloat() * yGive) - (yGive / 2)));
		
		smoke.add(new Smoke(smokeX, smokeY, false));
	}

	@Override
	public void render(GL10 gl) {
		int width = this.getWidth();
		int height = this.getHeight();
		float square = width > height ? width : height;
		
		if (!this.exploding) {
			gl.glTranslatef(-square / 4, -square / 4, 0);
			placemat1.draw(gl, damaged[0] ? TextureLoader.BUILDING_TEXTURE2 : TextureLoader.BUILDING_TEXTURE, 0);
			gl.glTranslatef(square / 2, 0, 0);
			placemat2.draw(gl, damaged[1] ? TextureLoader.BUILDING_TEXTURE2 : TextureLoader.BUILDING_TEXTURE, 1);
			gl.glTranslatef(-square / 2, square / 2, 0);
			placemat3.draw(gl, damaged[2] ? TextureLoader.BUILDING_TEXTURE2 : TextureLoader.BUILDING_TEXTURE, 2);
			gl.glTranslatef(square / 2, 0, 0);
			placemat4.draw(gl, damaged[3] ? TextureLoader.BUILDING_TEXTURE2 : TextureLoader.BUILDING_TEXTURE, 3);
			gl.glTranslatef(-square / 4, -square / 4, 0);
		}
		
		this.draw(gl);
		
		for (GameObject i : smoke) {
			i.gameRenderLoop(gl);
		}
	}

}
