package game;

import javafx.scene.shape.Circle;

/**
 * Ball is an extension of the circle class, so all it needs are setters and
 * getters for X and Y and the constant radius 
 */

public class Ball extends Circle {

	private static int x = 0;
	private static int y = 0;
	public final static int BALL_RADIUS = 7;

	Ball() {

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int p_x) {
		x = p_x;
	}

	public void setY(int p_y) {
		y = p_y;
	}
}
