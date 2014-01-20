package com.solidapt.defense;

public class MissileInformation {
	private int count;
	private float heatIncreaseValue;
	
	public MissileInformation(int count) {
		this.count = count;
	}
	
	public void decreaseCount() {
		count --;
	}
	
	public int getCount() {
		return count;
	}
	
}
