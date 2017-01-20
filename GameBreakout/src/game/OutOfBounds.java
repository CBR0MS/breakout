package game;

/**
 * Out of bounds class determines if the ball is out of bounds on the bottom.
 * Its method contains() will determine if the ball is in the area defined as
 * passed through the constructor. The out of bounds area is a line that lays on
 * the bottom of the pane.
 */

public class OutOfBounds {

	private static int xMax;
	private static int xMin;
	private static int yMin;
	public int reCount = 0;

	OutOfBounds(int xmax, int xmin, int ymin) {
		xMax = xmax;
		xMin = xmin;
		yMin = ymin;
	}

	public boolean contains(double x, double y) {

		if ((x > xMin && x < xMax) && (y >= yMin)) {
			return true;
		} else {
			return false;
		}
	}
}
