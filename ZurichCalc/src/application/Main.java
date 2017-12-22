package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try{
			Parent root = FXMLLoader.load(getClass().getResource("ZurichMain.fxml"));
			Scene scene = new Scene(root, 800, 600);
		
//			scene.getStylesheets().add(
//					getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("KV Berechnung Zurich");
			primaryStage.show();
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
