package com.solidapt.defense;

public class MissileInformation {
	private int count;
	private float heatIncreaseValue;
	private int cost;
	private Texture texture;
	private String name;
	
	public MissileInformation(int count, String name, Texture texture, int cost, float heatIncreaseValue) {
		this.count = count;
		this.name = name;
		this.texture = texture;
		this.cost = cost;
		this.heatIncreaseValue = heatIncreaseValue;
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
	
	public int getCost() {
		return cost;
	}
	
	public boolean buy(int amount) {
		if (canBuy(amount)) {
			ScoreTracker.setTotalScore(ScoreTracker.getTotalScore() - (amount * cost));
			this.count += amount;
			Util.saveMissileInformation();
			return true;
		}
		return false;
	}

	public boolean canBuy(int amount) {
		return ScoreTracker.getTotalScore() >= (amount * cost);
	}
	
	public float getHeatValue() {
		return heatIncreaseValue;
	}
	
}
