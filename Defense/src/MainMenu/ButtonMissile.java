package MainMenu;


import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.BlueFlame;
import com.solidapt.citydefense.objects.Explosion;
import com.solidapt.citydefense.objects.Projectile;
import com.solidapt.defense.TextureLoader;

public class ButtonMissile extends Projectile {

	public ButtonMissile(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.MISSILE_TEXTURE;
		ButtonExplosion temp = new ButtonExplosion(0, 0, (int)(this.getWidth()*2.8), (int)(this.getHeight()*2.8));
		flame = new BlueFlame(0, (int) (height*.9), width, height);
		explosion = temp;
	}
	
	@Override
	public void gameLoopLogic(double time) {
		
		if (explosion.getCurrentFrame() == 32) {
			explosion.gameLoopLogic(time);
		}
		else {
			super.gameLoopLogic(time);
		}
	}

	@Override
	public void render(GL10 gl) {
        if (!(this.getExploding() && explosion.getCurrentFrame() > 30)) {
        	flame.gameRenderLoop(gl);
        	this.draw(gl);
        }
        explosion.gameRenderLoop(gl);

	}
}
