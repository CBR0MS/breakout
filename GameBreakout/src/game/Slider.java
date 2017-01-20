package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Slider is a simple rectangle that can move around the X axis of the pane and
 * deflect the ball up. It will need a size, color, and a way to determine if
 * the ball hits it; the contains() method. The slider also causes different
 * deflection angles when hit in different sections, with the outermost sections
 * causing 15 degrees, then 30, then 45 in the center.
 * 
 * Future additions: make the angle of deflection a function of the speed of the
 * slider. This would require the addition of a function that determined the
 * instantaneous change in position of the slider in relation to some point x.
 * This would give its velocity, which could be used to return a unique angle. 
 */

public class Slider extends Rectangle {

	private static final int SLIDER_X = 100;
	private static final int SLIDER_Y = 10;
	private static final int DIVISOR_AREA = SLIDER_X / 5;
	private static final Color COLOR = Color.WHITE;
	private static int numberOfSliderHits = 0;
	public int reCount = 0;

	Slider() {
	}

	public static int getXSide() {
		return SLIDER_X;
	}

	public static int getYSide() {
		return SLIDER_Y;
	}

	public static Color getColor() {
		return COLOR;
	}

	public int getNumberOfSliderHits() {
		return numberOfSliderHits;
	}

	public void sliderHit() {
		numberOfSliderHits++;
	}

	public boolean contains(int x, int y) {

		if ((x > getX() && x < getX() + SLIDER_X) && (y < getY() && y > getY() - SLIDER_Y)) {
			return true;
		} else {
			return false;
		}
	}

	public double getHitArea(double x, Slider slider) {

		if ((x > slider.getX() && x < slider.getX() + DIVISOR_AREA)
				|| (x < slider.getX() + SLIDER_X && x > (slider.getX() + SLIDER_X) - DIVISOR_AREA)) {
			return -0.3; // 15 degrees
		}
		if ((x > slider.getX() + DIVISOR_AREA && x < slider.getX() + 2 * DIVISOR_AREA)
				|| (x > slider.getX() + 3 * DIVISOR_AREA && x < slider.getX() + (SLIDER_X - DIVISOR_AREA))) {
			return -0.4; // 30 degrees
		} else {
			return -1.2; // 45 degrees
		}
	}
}