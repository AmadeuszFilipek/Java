package exercises;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Exercise21 extends Application {
	
	public void start(Stage primaryStage) {
		
		Pane pane = new PaneWithTriangle();
		
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setTitle("Exercise15_21");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static class PaneWithTriangle extends Pane {
		
		private Circle crcMain;
		private Circle crc1, crc2, crc3;
		private Line ln1, ln2, ln3;
		private Label lbl1, lbl2, lbl3;
		
		PaneWithTriangle() {
			setBackground(new Background(new BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
			setPadding(new Insets(30));
			
			//create main circle
			crcMain = new Circle(200, 200, 100);
			crcMain.setFill(null);
			crcMain.setStroke(Color.BLACK);
//			crcMain.centerXProperty().bind(this.widthProperty().divide(2));
//			crcMain.centerYProperty().bind(this.heightProperty().divide(2));
			
			// create three circles
			crc1 = new Circle(10);
			crc1.setCenterX(crcMain.getCenterX() + crcMain.getRadius() * Math.cos(1));
			crc1.setCenterY(crcMain.getCenterY() - crcMain.getRadius() * Math.sin(1));
			crc1.setFill(Color.WHITE);
			crc1.setStroke(Color.BLACK);
			
			crc2 = new Circle(10);
			crc2.setCenterX(crcMain.getCenterX() + crcMain.getRadius() * Math.cos(2));
			crc2.setCenterY(crcMain.getCenterY() - crcMain.getRadius() * Math.sin(2));
			crc2.setFill(Color.WHITE);
			crc2.setStroke(Color.BLACK);

			crc3 = new Circle(10);
			crc3.setCenterX(crcMain.getCenterX() + crcMain.getRadius() * Math.cos(3));
			crc3.setCenterY(crcMain.getCenterY() - crcMain.getRadius() * Math.sin(3));
			crc3.setFill(Color.WHITE);
			crc3.setStroke(Color.BLACK);
			
			//create a line connecting 1 - 2
			ln1 = new Line();
			ln1.startXProperty().bind(crc1.centerXProperty());
			ln1.startYProperty().bind(crc1.centerYProperty());
			ln1.endXProperty().bind(crc2.centerXProperty());
			ln1.endYProperty().bind(crc2.centerYProperty());
			
			//create a line connecting 2 - 3
			ln2 = new Line();
			ln2.startXProperty().bind(crc2.centerXProperty());
			ln2.startYProperty().bind(crc2.centerYProperty());
			ln2.endXProperty().bind(crc3.centerXProperty());
			ln2.endYProperty().bind(crc3.centerYProperty());
			
			//create a line connecting 3 - 1
			ln3 = new Line();
			ln3.startXProperty().bind(crc3.centerXProperty());
			ln3.startYProperty().bind(crc3.centerYProperty());
			ln3.endXProperty().bind(crc1.centerXProperty());
			ln3.endYProperty().bind(crc1.centerYProperty());
			
			//create a label for angle 1
			lbl1 = new Label();
			lbl1.setFont(Font.font(20));
			lbl1.layoutXProperty().bind(crc1.centerXProperty().add(5));
			lbl1.layoutYProperty().bind(crc1.centerYProperty().add(5));
			
			//create label for angle 2
			lbl2 = new Label();
			lbl2.setFont(Font.font(20));
			lbl2.layoutXProperty().bind(crc2.centerXProperty().add(5));
			lbl2.layoutYProperty().bind(crc2.centerYProperty().add(5));
			
			//create a label for angle 3
			lbl3 = new Label();
			lbl3.setFont(Font.font(20));
			lbl3.layoutXProperty().bind(crc3.centerXProperty().add(5));
			lbl3.layoutYProperty().bind(crc3.centerYProperty().add(5));
			
			//create an event handlers for the circles
			declareAction(crc1);
			declareAction(crc2);
			declareAction(crc3);
			
			getChildren().addAll(crcMain, ln1, ln2, ln3, crc1, crc2, crc3, lbl1, lbl2, lbl3);
			refreshLabels();
		}
		
		private void declareAction(Circle c) {
			c.setOnMouseDragged(e -> {
				double alpha = Math.PI / 2 - Math.atan2(e.getX() - crcMain.getCenterX(), e.getY() - crcMain.getCenterY());
				System.out.println(alpha);
				c.setCenterX(crcMain.getCenterX() + crcMain.getRadius() * Math.cos(alpha));
				c.setCenterY(crcMain.getCenterY() + crcMain.getRadius() * Math.sin(alpha));
				refreshLabels();
			});
		}
		
		public void refreshLabels() {
			double a = length(crc1, crc2);
			double b = length(crc2, crc3);
			double c = length(crc3, crc1);
			
			double gamma = Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));
			double alpha = Math.toDegrees(Math.acos((b * b - a * a - c * c) / (-2 * a * c)));
			double beta = Math.toDegrees(Math.acos((c * c - b * b - a * a) / (-2 * a * b)));
			
			lbl1.setText(String.format("%.2f", alpha));
			lbl2.setText(String.format("%.2f", beta));
			lbl3.setText(String.format("%.2f", gamma));
		}
		
		private double length(Circle c1, Circle c2) {
			
			return Math.sqrt(Math.pow(c1.getCenterX() - c2.getCenterX(), 2) + 
					Math.pow(c1.getCenterY() - c2.getCenterY(), 2));
		}
	}
}
