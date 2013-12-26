package com.solidapt.citydefense.objects;


import android.graphics.Point;
import android.util.Log;

import com.solidapt.defense.CollisionDetector;
import com.solidapt.defense.GLSquare;
import com.solidapt.defense.Texture;

import javax.microedition.khronos.opengles.GL10;

/**
 * Definition for any object in the game
 * @author Brian
 *
 */
public abstract class GameObject {
	private static final boolean RENDER_POINTS = true;
	
	private float xCoord;
	private float yCoord;
	private int height;
	private int width;
	private float rotation = 0;

	private boolean remove = false;

    protected GLSquare placemat;
	public Texture myTexture;

    public GameObject(int xCoord, int yCoord, int width, int height) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width;
        this.height = height;

        int square = width > height ? width : height;
        placemat = new GLSquare(square, square);
    }

	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	public void setXCoord(float xCoord) {
		this.xCoord = xCoord;
	}
	public float getXCoord() {
		return this.xCoord;
	}
	
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	public void setYCoord(float yCoord) {
		this.yCoord = yCoord;
	}
	public float getYCoord() {
		return this.yCoord;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	
    
    public void markForRemoval() {
    	this.remove = true;
    }
	
    public boolean needsRemoval() {
    	return this.remove;
    }
    
	/**
	 * Run each time the game loops, try not to do too many operations here
	 * @param time - time since last loop in milliseconds
	 */
	public abstract void gameLoopLogic(double time);
	
	/**
	 * Draw graphics on canvas
	 */
	public void render(GL10 gl) {
		draw(gl);
	}
	
	protected void prepareDraw(GL10 gl) {
		gl.glPushMatrix();
        gl.glTranslatef(xCoord, yCoord, 0.0f);
        gl.glRotatef(rotation,0.0f,0.0f, 1.0f );
       
	}
	
	protected void finishDraw(GL10 gl) {
		gl.glRotatef(-rotation,0.0f,0.0f, 1.0f );
	    gl.glTranslatef(-xCoord, -yCoord, 0.0f);
	    gl.glPopMatrix();
	}
	
	public void gameRenderLoop(GL10 gl) {
		prepareDraw(gl);
		render(gl);
		finishDraw(gl);
		
		if (RENDER_POINTS && !(this instanceof Background) && ! (this instanceof Marker)) {
			Point[] x = CollisionDetector.getPointsFromRotatedObject(this);
			for (Point p : x) {
				Marker flame3 = new Marker(p.x, p.y, 15, 15);
				flame3.gameRenderLoop(gl);
			}
		}
	}
	
	public void draw(GL10 gl) {
    	placemat.draw(gl, myTexture, 0);
	}
}
