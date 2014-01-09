package com.solidapt.defense;

import com.solidapt.textRender.GLText;

import android.graphics.BitmapFactory;


public class TextureLoader {
	public static Texture MAIN_MENU_TEXTURE;
	public static Texture MISSILE_TEXTURE;
	public static Texture RED_FLAME_TEXTURE;
	public static Texture BLUE_FLAME_TEXTURE;
	public static Texture EXPLOSION_TEXTURE;
	public static Texture BUILDING_TEXTURE;
	
	public static Texture TURRET_BASE_TEXTURE;
	public static Texture TURRET_GUN_TEXTURE;
	
	public static Texture MARKER_TEXTURE;
	public static Texture OVERLAY_MENU_BUTTON;
	
	private static boolean loaded = false;
	private static BitmapFactory.Options options = new BitmapFactory.Options();
	
	public static void loadTextures() {

		options.inScaled = false; // load the Bitmap without scaling weirdly
		MISSILE_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.missile_plain, options));
		RED_FLAME_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.propelgrid, options), 18, 50, true);
		BLUE_FLAME_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.propelgridblue, options), 18, 50, true);
		EXPLOSION_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.explosion2, options), 64, 120, false);
		BUILDING_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.buildinggrid2, options), 63, 80, false);
		
		TURRET_BASE_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.turretbase, options));
		TURRET_GUN_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.turretgun, options));
		
		MARKER_TEXTURE = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.marker, options));
		OVERLAY_MENU_BUTTON = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.overlaymenubutton, options));
		MAIN_MENU_TEXTURE = new Texture(BitmapFactory.decodeResource(Util.context.getResources(), R.drawable.startscreen));
		
		Util.textRenderer.load("Cabin-Bold.ttf", Util.getHeight()/20, 1, 1);
		loaded = true;
	}
	
	public static boolean hasLoaded() {
		return loaded;
	}
	
	public static void setUnloaded() {
		loaded = false;
	}
}
