package com.solidapt.defense;

import com.solidapt.citydefense.objects.Explosion;
import com.solidapt.citydefense.objects.GameObject;

import android.graphics.Point;
import android.util.Log;

public class CollisionDetector {
	
	public static boolean collisionDetected(Explosion x, GameObject y) {
		Point[] xPoints = getPointsFromRotatedObject(x, (int)x.getParentX(), (int)x.getParentY());
		Point[] yPoints = getPointsFromRotatedObject(y, (int)y.getXCoord(), (int)y.getYCoord());
		
		Point[] axis = getAxisFromObjectPoints(xPoints, yPoints);
		
		return collisionDetected(axis, 0, xPoints, yPoints);
	}
	
	private static Point[] getAxisFromObjectPoints(Point[] xPoints, Point[] yPoints) {
		Point[] axis = new Point[4];
		
		axis[0] = new Point(xPoints[3].x - xPoints[0].x, xPoints[3].y - xPoints[0].y);
		axis[1] = new Point(xPoints[3].x - xPoints[2].x, xPoints[3].y - xPoints[2].y);
		axis[2] = new Point(yPoints[0].x - yPoints[1].x, yPoints[0].y - yPoints[1].y);
		axis[3] = new Point(yPoints[0].x - yPoints[3].x, yPoints[0].y - yPoints[3].y);
		
		return axis;
	}

	public static Point[] getPointsFromRotatedObject(GameObject object, int x, int y) {
		double rotation = Math.toRadians(object.getRotation());
		int x1 = (int) (Math.cos((Math.PI/2)-rotation) * (object.getHeight()/2));
		int y1 = (int) (Math.sin((Math.PI/2)-rotation) * (object.getHeight()/2));
		int x2 = (int) (Math.cos(rotation) * (object.getWidth()/2));
		int y2 = (int) (Math.sin(rotation) * (object.getWidth()/2));
		
		Point[] toRet = new Point[4];
		toRet[0] = new Point(x - (x2 - x1), y - (y1 + y2));
		toRet[1] = new Point(x - (x2 + x1), y + (y1 - y2));
		toRet[2] = new Point(x + (x2 - x1), y + (y1 + y2));
		toRet[3] = new Point(x + (x2 + x1), y - (y1 - y2));
		return toRet;
	}

	public static boolean collisionDetected(Point[] axis, int axisToCheck, Point[] boxPoints1, Point[] boxPoints2) {
		if (axisToCheck == 4) {
			return true;
		}
		
		boolean isOverlapping = isOverlapping(axis[axisToCheck], boxPoints1, boxPoints2);
		if (isOverlapping) {
			return collisionDetected(axis, axisToCheck + 1, boxPoints1, boxPoints2);
		}
		else
			return false;
	}

	private static boolean isOverlapping(Point axis, Point[] boxPoints1, Point[] boxPoints2) {
		int[] pointValues1 = calculateOnVectorValues(axis, boxPoints1);
		int[] pointValues2 = calculateOnVectorValues(axis, boxPoints2);
		
		if (getMin(pointValues2) < getMax(pointValues1)) {
			if (getMin(pointValues1) < getMax(pointValues2)) {
				return true;
			}
		}
		return false;
	}

	private static int getMin(int[] pointValues) {
		int currentMin = Integer.MAX_VALUE;
		
		for (int i : pointValues) {
			if (i < currentMin) {
				currentMin = i;
			}
		}
		return currentMin;
	}

	private static int getMax(int[] pointValues) {
		int currentMax = Integer.MIN_VALUE;
		
		for (int i : pointValues) {
			if (i > currentMax) {
				currentMax = i;
			}
		}
		return currentMax;
	}

	private static int[] calculateOnVectorValues(Point axis, Point[] boxPoints) {
		int[] toRet = new int[4];
		
		for (int i = 0; i < boxPoints.length; i++) {
			int[] projection = matrixProjection(axis, boxPoints[i]);
			
			toRet[i] = (projection[0] * axis.x) + (projection[1] * axis.y);
		}
		
		return toRet;
	}

	private static int[] matrixProjection(Point axis, Point point) {
		int[] toRet = new int[2];
		
		double numerator = (point.x * axis.x) + (point.y * axis.y);
		double denominator = (axis.x * axis.x) + (axis.y * axis.y);
		double fraction = numerator / denominator;
		
		toRet[0] = (int)(fraction * axis.x);
		toRet[1] = (int)(fraction * axis.y);
		return toRet;
	}
}
