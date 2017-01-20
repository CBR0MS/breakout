package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The driver starts the Game menu, and from there can set up a new game and
 * receive the keyboard input.
 */

public class GameDriver extends Application {

	public static void main(String[] args) {
		Application.launch();
	}

	private Stage stage;

	@Override
	public void start(Stage primaryStage) {

		this.stage = primaryStage;
		startMenu();
	}

	public Scene playGame(Game game) {

		game.play();
		game.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.D) {
				game.moveSliderRight();
			}
			if (e.getCode() == KeyCode.A) {
				game.moveSliderLeft();
			}
			if (e.getCode() == KeyCode.RIGHT) {
				game.moveSliderRight();
			}
			if (e.getCode() == KeyCode.LEFT) {
				game.moveSliderLeft();
			}
		});
		
		game.pause.setOnAction(e -> {
			game.pause();
			game.drawPlay();
		});
		game.play.setOnAction( e -> {
			game.play();
			game.removePlay();
		});
		game.menu.setOnAction( e -> {
			game.stop();
			game.deleteGame();
			startMenu();
		});
		
		Background back = new Background(new BackgroundFill(Color.BLACK, null, null));
		game.setBackground(back);
		Scene scene = new Scene(game, 600, 725);

		return scene;
	}
	
	public void startMenu()
    {
		GameMenu menu = new GameMenu();
		Scene scene1 = new Scene(menu, 600, 725);
		stage.setScene(scene1);
		stage.show();
		scene1.getStylesheets().add("https://fonts.googleapis.com/css?family=Quantico:700i");

		menu.easy.setOnAction(e -> {
			Game game = new Game(1);
			Scene scene = playGame(game);
			stage.setScene(scene);
			stage.show();
			game.requestFocus();
		});

		menu.med.setOnAction(e -> {
			Game game = new Game(2);
			Scene scene = playGame(game);
			stage.setScene(scene);
			stage.show();
			game.requestFocus();
		});

		menu.hard.setOnAction(e -> {
			Game game = new Game(3);
			Scene scene = playGame(game);
			stage.setScene(scene);
			stage.show();
			game.requestFocus();
		});
    }
}