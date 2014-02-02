package com.solidapt.defense;

public class MissileInformation {
	private int count;
	private float heatIncreaseValue;
	private Texture texture;
	private String name;
	
	public MissileInformation(int count, String name, Texture texture) {
		this.count = count;
		this.name = name;
		this.texture = texture;
	}
	
	public void decreaseCount() {
		count --;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getName() {
		return name;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
}
