package application;
	

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	

	@Override
	public void start(Stage primaryStage) {
		
		try {	
			ConectorFirebase.conectar();
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Transportes Olmedo : Login");
			primaryStage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
