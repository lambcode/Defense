package MainMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;

public class TopMenu {
	
	static TopMenu topMenu;
	GameObject background;
	GameObject buttonMissile;
	
	public TopMenu() {
		background = new MainMenu();
		buttonMissile = new ButtonMissile((int) (Util.getWidth()/2.5), Util.getHeight()+120, 60, 120, (int)(Util.getWidth() / 2.01), (int) (Util.getHeight()*.65), 400);
		//new ButtonMissile(Util.getWidth()/2, Util.getHeight() + 65, 30, 60, Util.getWidth()/3, (int) (Util.getHeight()*.6), 100);
	}
	
	public static void logicLoop(double time) {
		if (topMenu == null)
			TopMenu.load();
		
		topMenu.doLogicLoop(time);
	}
	
	public static void renderLoop(GL10 gl) {
		if (topMenu == null)
			TopMenu.load();
		
		topMenu.doRenderLoop(gl);
	}
	
	private void doRenderLoop(GL10 gl) {
		background.gameRenderLoop(gl);
		buttonMissile.gameRenderLoop(gl);
	}

	private void doLogicLoop(double time) {
		SoundLoader.startMusic(SoundLoader.menuMusic);
		background.gameLoopLogic(time);
		buttonMissile.gameLoopLogic(time);
	}

	public static void load() {
		TopMenu.topMenu = new TopMenu();
	}
	
	public static void release() {
		TopMenu.topMenu = null;
	}

}
