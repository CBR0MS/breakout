package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The game class runs the game, called by the game driver class. The moveBall
 * method checks for where the ball hits and changes direction accordingly. The
 * set of Draw methods draws the various components on screen and updates the
 * text for score and lives when necessary
 */

public class Game extends Pane {

	private static final int WINDOW_HEIGHT = 725;
	private static final int WINDOW_WIDTH = 600;
	private static int SLIDER_MOVE_DIST = 20;
	private static int NUMBER_OF_BRICKS = 45;
	private static int columns = WINDOW_HEIGHT - 400;
	private static int rows = WINDOW_WIDTH - 60;
	private static double dx = 1;
	private static double dy = 1;
	private static Duration BALL_SPEED = Duration.millis(5);
	private double x = 20;
	private double y = WINDOW_HEIGHT / 2;
	private double radius = 10;
	private int lives = 4;
	private int score = 0;
	private int brickDefault;
	private int bricksDestroyed = 0;
	private boolean correct = false;
	private boolean reDraw = true;
	private boolean resetTimer = false;
	private boolean mediumBrickDiff = false;
	private boolean brickNotEdited = true;
	private boolean started = false;
	private Text tLives = new Text();
	private Text tScore = new Text();
	private Text tTime = new Text();
	private Timeline animation;
	private Timeline timer;
	Button pause = new Button();
	Button play = new Button();
	Button menu = new Button();
	int sec = 4;

	// Object initiations

	Ball ball = (Ball) drawBall();
	Slider slider = (Slider) drawSlider();
	OutOfBounds ob = new OutOfBounds(WINDOW_WIDTH, 0, WINDOW_HEIGHT - 10);

	public Game(int diff) {

		switch (diff) {
		case 1:
			BALL_SPEED = Duration.millis(4);
			lives = 5;
			brickDefault = 1;
			SLIDER_MOVE_DIST = 25;
			NUMBER_OF_BRICKS = 27;
			break;
		case 2:
			BALL_SPEED = Duration.millis(4);
			lives = 4;
			brickDefault = 2;
			SLIDER_MOVE_DIST = 25;
			mediumBrickDiff = true;
			NUMBER_OF_BRICKS = 45;
			break;
		case 3:
			BALL_SPEED = Duration.millis(3);
			lives = 5;
			brickDefault = 3;
			SLIDER_MOVE_DIST = 30;
			NUMBER_OF_BRICKS = 45;
			break;
		default:
			NUMBER_OF_BRICKS = 45;
			break;
		}
		setGame(NUMBER_OF_BRICKS);
	}

	static Brick[] bricks = new Brick[NUMBER_OF_BRICKS];
	static int[] health = new int[NUMBER_OF_BRICKS];

	public void setGame(int NUMBER_OF_BRICKS) {

		// draw bricks

		if (reDraw) {

			int k = 0;

			for (int i = 40; i < columns; i += 60) {

				for (int j = 40; j < rows; j += 60) {

					if (k != NUMBER_OF_BRICKS) {

						if ((k == 8 || k == 32 || k == 9) && (mediumBrickDiff)) {
							Brick rec = new Brick(brickDefault + 1);
							drawBrick(j, i, rec, brickDefault + 1);
							health[k] = brickDefault + 1;
							bricks[k] = rec;
							brickNotEdited = false;
						} else if (k % 2 == 0 && mediumBrickDiff && brickNotEdited) {
							Brick rec = new Brick(brickDefault - 1);
							drawBrick(j, i, rec, brickDefault - 1);
							health[k] = brickDefault - 1;
							bricks[k] = rec;
						} else {
							Brick rec = new Brick(brickDefault);
							drawBrick(j, i, rec, brickDefault);
							health[k] = brickDefault;
							bricks[k] = rec;
						}
						k++;
						brickNotEdited = true;
					}
				}
			}
		}

		// set the ball and slider to correct positions

		x = 20;
		y = WINDOW_HEIGHT / 2;
		dx = 1;
		dy = 1;
		slider.setX(WINDOW_WIDTH / 2 - Slider.getXSide() / 2 + 20);

		// add the ball and slider objects

		getChildren().add(ball);
		getChildren().add(slider);
		drawScore(score, tScore);
		drawLives(lives, tLives);
		drawPause(pause);
		drawMenu(menu, 1);

		// start the animation

		animation = new Timeline(new KeyFrame(BALL_SPEED, e -> moveBall()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}

	// method to reset the game and animation

	public void deleteGame() {

		getChildren().remove(ball);
		getChildren().remove(slider);
		animation = null;
		if (resetTimer){
			resetTime();	
		}
	}

	/*
	 * The moveBall() method is what is running while the ball moves to detect
	 * hits on different surfaces. If the ball hits the sides of the pane, the X
	 * velocity is set to its inverse. If the ball hits the slider, the Y
	 * velocity is also set to its inverse. The same is true for the bricks,
	 * depending on which side the ball hits, the X or Y velocity is set to its
	 * inverse. If the ball hits the bottom, its position is reset.
	 */

	private void moveBall() {

		if (resetTimer) {
			resetTime();
			resetTimer = false;
		}

		if (started == false) {
			animation.pause();
			sec = 4;
			time();
			resetTimer = true;
			started = true;
		}

		// Check the sides of the pane

		if (x < radius || x > WINDOW_WIDTH - radius) {
			if (correct && dy < 0) {
				dy = -1;
			}
			if (correct && dy > 0) {
				dy = 1;
			}
			dx *= -1;
			System.out.println(dy);
			System.out.println(dx);
		}
		if (y < radius || y > WINDOW_HEIGHT - radius) {
			dy *= -1;
		}

		// Check for hits on the slider

		if (slider.contains(x, y + 5) && slider.reCount <= 0) {

			double z = slider.getHitArea(x, slider);
			dy *= z;
			slider.sliderHit();
			System.out.println(dy);
			System.out.println(dx);
			slider.reCount = 5;

			if (dy == -0.3 || dy == -0.4 || dy == -1.2) {
				if (z != -1) {
					correct = true;
				}
			} else {
				dy = -1;
			}
		}
		slider.reCount--;

		// Check for out of bounds

		if (ob.contains(x, y) && ob.reCount <= 0) {
			lives--;

			if (lives <= 0) {
				pause();
				gameEnd();
			} else {
				animation.stop();
				reDraw = false;
				deleteGame();
				setGame(NUMBER_OF_BRICKS);
				animation.pause();
				sec = 4;
				time();
				resetTimer = true;
			}
			drawLives(lives, tLives);
		}

		// Check for hits on bricks

		for (int i = 0; i < NUMBER_OF_BRICKS; i++) {

			if (bricks[i].contains(x - radius, y - radius) && bricks[i].reCount <= 0) {

				if (y <= bricks[i].getY() - (Brick.getYSide() / 2)) {
					// Top hit
					dy *= -1;
				}
				if (y >= bricks[i].getY() + (Brick.getYSide() / 2)) {
					// bottom hit
					dy *= -1;
				} else {
					// left and right hits
					dx *= -1;
				}
				bricks[i].reCount = 35;
				health[i]--;
				drawBrick(bricks[i].getX(), bricks[i].getY(), bricks[i], health[i]);

				if (health[i] == 0) {
					score += 100;
					drawScore(score, tScore);
					bricksDestroyed++;
				}
			}
			bricks[i].reCount--;
		}
		x += dx;
		y += dy;
		ball.setCenterX(x);
		ball.setCenterY(y);

		// check for end of game

		if (bricksDestroyed == NUMBER_OF_BRICKS) {
			animation.stop();
			gameEnd();
		}

	}

	// start, stop, and pause the animation from the main function

	public void play() {
		animation.play();
	}

	public void pause() {
		animation.pause();
	}

	public void stop() {
		animation.stop();
	}

	// start the count down timer to reset the ball in play

	public void time() {

		timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

			sec--;
			if (sec > 0) {
				drawTime(sec, tTime);
			} else {
				play();
				getChildren().remove(tTime);
				timer.stop();
			}
		}));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();
	}

	public void resetTime() {
		timer.stop();
		timer = null;
	}

	// move slider methods called from the main

	public void moveSliderRight() {

		if (slider.getX() < WINDOW_WIDTH - Slider.getXSide()) {
			slider.setX(slider.getX() + SLIDER_MOVE_DIST);
		}
	}

	public void moveSliderLeft() {

		if (slider.getX() > 0) {
			slider.setX(slider.getX() - SLIDER_MOVE_DIST);
		}
	}

	// Methods to draw the components follow

	public Node drawBall() {

		Ball cir = new Ball();
		cir.setFill(Color.WHITE);
		cir.setCenterX(WINDOW_WIDTH / 2);
		cir.setCenterY(WINDOW_HEIGHT / 2);
		cir.setRadius(radius);
		return cir;
	}

	public Node drawSlider() {

		Slider sli = new Slider();
		sli.setFill(Slider.getColor());
		sli.setX(WINDOW_WIDTH / 2 - 2 * Slider.getXSide());
		sli.setY(600);
		sli.setWidth(Slider.getXSide());
		sli.setHeight(Slider.getYSide());
		return sli;
	}

	public void drawBrick(double x, double y, Brick rec, int health) {

		getChildren().remove(rec);
		rec.setType(health);
		rec.setFill(Brick.getColor());
		rec.setX(x);
		rec.setY(y);
		rec.setWidth(Brick.getXSide());
		rec.setHeight(Brick.getYSide());

		if (health > 0) {
			getChildren().add(rec);
		} else {
			rec.setX(800);
			rec.setY(800);
		}
	}

	public void drawLives(int lives, Text tScore) {

		getChildren().remove(tScore);
		if (lives > 0) {
			tScore.setText("LIVES REMAINING: " + (lives - 1));
		}
		tScore.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		tScore.setFill(Color.WHITE);
		tScore.setX(10);
		tScore.setY(20);
		getChildren().add(tScore);
	}

	public void drawScore(int score, Text tScore) {

		getChildren().remove(tScore);
		tScore.setText("SCORE: " + score);
		tScore.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		tScore.setFill(Color.WHITE);
		tScore.setX(WINDOW_WIDTH - 100);
		tScore.setY(20);
		getChildren().add(tScore);
	}

	public void drawTime(int time, Text tTime) {

		getChildren().remove(tTime);

		tTime.setText("" + time);
		tTime.setStyle("-fx-font-family: Quantico; -fx-font-size: 55;");
		tTime.setFill(Color.WHITE);
		tTime.setY(550);
		tTime.setX(WINDOW_WIDTH / 2);
		getChildren().add(tTime);
	}

	public void gameEnd() {

		Text end = new Text();
		Text fscore = new Text();
		Rectangle rec = new Rectangle();
		rec.setX(0);
		rec.setY(0);
		rec.setWidth(WINDOW_WIDTH);
		rec.setHeight(WINDOW_HEIGHT);
		rec.setFill(Color.BLACK);
		rec.setOpacity(.50);

		if (bricksDestroyed == NUMBER_OF_BRICKS) {
			end.setText("You Won!");
		} else {
			end.setText("Game Over!");
		}
		fscore.setText("Your score: " + score);
		fscore.setStyle("-fx-font-family: Quantico; -fx-font-size: 55;");
		fscore.setFill(Color.WHITE);
		fscore.setX(100);
		fscore.setY(400);
		end.setStyle("-fx-font-family: Quantico; -fx-font-size: 55;");
		end.setFill(Color.WHITE);
		end.setX(150);
		end.setY(300);
		getChildren().add(rec);
		drawMenu(menu, 2);
		getChildren().addAll(fscore, end);
	}

	public void drawPause(Button pause) {
		getChildren().remove(pause);
		pause.setText("Pause");
		pause.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		pause.setLayoutX(395);
		pause.setLayoutY(WINDOW_HEIGHT - 40);
		getChildren().add(pause);
	}
	
	public void drawMenu(Button menu, int i ){
		
		getChildren().remove(menu);
		menu.setText("Return to Menu");
		menu.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		
		if (i == 1){
			menu.setLayoutX(465);
			menu.setLayoutY(WINDOW_HEIGHT - 40);	
		}
		else{
			menu.setLayoutX(WINDOW_WIDTH / 2 - 60);
			menu.setLayoutY(475);
		}
		getChildren().add(menu);
	}

	public void drawPlay() {
		play.setText("  Play ");
		play.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		play.setLayoutX(10);
		play.setLayoutY(WINDOW_HEIGHT - 40);
		getChildren().add(play);
	}

	public void removePlay() {
		getChildren().remove(play);
	}
}