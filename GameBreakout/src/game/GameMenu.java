package game;

import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameMenu extends Pane {

	public Button hard = new Button();
	public Button med = new Button();
	public Button easy = new Button();
	Text diff = new Text();
	Text title = new Text();
	Text cred = new Text();
	Random rand = new Random();
	
	GameMenu() {

		Background back = new Background(new BackgroundFill(Color.BLACK, null, null));
		setBackground(back);
		
		for (int i = 0; i < 40; i++){
			
			Rectangle rec = makeRandomRectangle();
			getChildren().add(rec);
		}

		title.setText("BRICK BREAKER");
		title.setStyle("-fx-font-family: Quantico; -fx-font-size: 65;");
		title.setFill(Color.WHITE);
		title.setX(50);
		title.setY(200);
		getChildren().add(title);
		
		diff.setText("SELECT DIFFICULTY");
		diff.setStyle("-fx-font-family: Quantico; -fx-font-size: 30;");
		diff.setFill(Color.WHITE);
		diff.setX(150);
		diff.setY(300);
		getChildren().add(diff);
		
		cred.setText("Built by Christian Broms");
		cred.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		cred.setFill(Color.WHITE);
		cred.setX(15);
		cred.setY(700);
		getChildren().add(cred);

		easy.setText("Easy");
		easy.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		easy.setLayoutX(150);
		easy.setLayoutY(325);
		getChildren().add(easy);

		med.setText("Medium");
		med.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		med.setLayoutX(250);
		med.setLayoutY(325);
		getChildren().add(med);

		hard.setText("Hard");
		hard.setStyle("-fx-font-family: Quantico; -fx-font-size: 15;");
		hard.setLayoutX(375);
		hard.setLayoutY(325);
		getChildren().add(hard);
	}
	
	public Rectangle makeRandomRectangle(){
		
		Rectangle rec = new Rectangle();
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		Color randomColor = Color.rgb(r, g, b);
		rec.setFill(randomColor);
		rec.setOpacity(0.25);
		rec.setX(rand.nextInt(601));
		rec.setY(rand.nextInt(701));
		rec.setWidth(60);
		rec.setHeight(60);
		return rec;
		
	}


}
