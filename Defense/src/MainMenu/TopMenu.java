package MainMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.MainMenu;
import com.solidapt.defense.SoundLoader;

public class TopMenu {
	
	static TopMenu topMenu;
	GameObject background;
	GameObject buttonMissile;
	
	public TopMenu() {
		background = new MainMenu();
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
	}

	private void doLogicLoop(double time) {
		SoundLoader.startMusic(SoundLoader.menuMusic);
		background.gameLoopLogic(time);
	}

	public static void load() {
		TopMenu.topMenu = new TopMenu();
	}
	
	public static void release() {
		TopMenu.topMenu = null;
	}

}
