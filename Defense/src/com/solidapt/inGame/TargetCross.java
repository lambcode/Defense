package com.solidapt.inGame;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.Projectile;
import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.TextureLoader;

public class TargetCross extends StaticObject {
	
	TargetCircle outer;
	TargetCircle inner;
	
	Projectile parentMissile;
	float transparency = .24f;

	public TargetCross(int xCoord, int yCoord, int width, int height, Projectile parentMissile) {
		super(xCoord, yCoord, (int) (width * .3), (int) (height * .3));

		this.myTexture = TextureLoader.TARGET_CROSS_TEXTURE;
		this.outer = new TargetCircle(0, 0, width, height, 0f, 100f);
		this.inner = new TargetCircle(0, 0, (int) (width * .6), (int) (height * .6), 90f, -100f);
		this.parentMissile = parentMissile;
	}

	@Override
	public void gameLoopLogic(double time) {
		if (parentMissile.getExploding()) {
			transparency -= .3f * time;
		}
		if (parentMissile.needsRemoval()) {
			this.markForRemoval();
		}
		outer.gameLoopLogic(time);
		inner.gameLoopLogic(time);

	}
	
	@Override
	public void render(GL10 gl) {
		gl.glColor4f(1f, 1f, 1f, transparency);
		this.draw(gl);
		gl.glColor4f(1f, 0f, 0f, transparency);
		inner.gameRenderLoop(gl);
		gl.glColor4f(0f, 1f, 0f, transparency);
		outer.gameRenderLoop(gl);
		gl.glColor4f(1f, 1f, 1f, 1f);
	}
	
	class TargetCircle extends StaticObject {
		
		float rotationSpeed;

		public TargetCircle(int xCoord, int yCoord, int width, int height, float initialRotation, float rotationSpeed) {
			super(xCoord, yCoord, width, height);
			this.rotationSpeed = rotationSpeed;
			this.setRotation(initialRotation);
			this.myTexture = TextureLoader.TARGET_CIRCLE_TEXTURE;
		}

		@Override
		public void gameLoopLogic(double time) {
			float rotation = this.getRotation();
			this.setRotation((float) (rotation + (rotationSpeed * time)));
		}
		
	}

}
