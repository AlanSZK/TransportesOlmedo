package application;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FUNCIONES {
	public static void cambiarEscena (Scene fxml , ActionEvent e, String titulo)
	{
		
		Stage currentStage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		
		currentStage.setTitle(titulo);
		
		currentStage.setScene(fxml);
		
		
	}
	
	public static void dialogoAlerta (String titulo, String msg)
	{
		Alert alerta = new Alert(AlertType.WARNING);
		
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(msg);
		
		alerta.showAndWait();
	}
	

	

}


