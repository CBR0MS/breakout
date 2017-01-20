package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Brick has four health levels which lower as soon as the ball hits. When the
 * final level is reached, the brick is destroyed and removed. Brick needs X and
 * Y coordinates and different colors representative of its health levels. Like
 * the other classes, the brick also has a contains() method to determine if
 * there are hits.
 */

public class Brick extends Rectangle {

	private static final int BRICK_X = 40;
	private static final int BRICK_Y = 40;
	private static Color color = Color.BLACK;
	private static int health = 4;
	public int reCount;

	Brick(int type) {

		setType(type);
	}

	// check if another bounds hits with brick's bounds
	// i.e. when the ball's bounds enters that of the brick

	public boolean contains(int x, int y) {

		if ((x > getX() && x < getX() + BRICK_X) && (y < getY() && y > getY() - BRICK_Y)) {
			return true;
		} else {
			return false;
		}
	}

	// set health and color based on health

	public void setType(int p_health) {

		health = p_health;

		if (health == 3) {

			color = Color.PURPLE;
		}

		if (health == 2) {

			color = Color.DARKRED;
		}
		if (health == 1) {

			color = Color.RED;
		}
		if (health <= 0) {

			color = Color.WHITE;
		}
	}

	public int getHealth() {
		return health;
	}

	public static Color getColor() {
		return color;
	}

	public static int getXSide() {
		return BRICK_X;
	}

	public static int getYSide() {
		return BRICK_Y;
	}
}