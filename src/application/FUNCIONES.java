package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FUNCIONES {
	public static void cambiarEscena (Scene fxml , ActionEvent e, String titulo)
	{
		
		Stage currentStage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		
		currentStage.setTitle(titulo);
		
		currentStage.setScene(fxml);
		
		
		
	}
	

	

}


