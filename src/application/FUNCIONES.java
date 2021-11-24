package application;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FUNCIONES {
<<<<<<< HEAD
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
	
	public static Optional<ButtonType> dialogoConfirmacion (String msg)
	{
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		
		alerta.setTitle("Confirmación");
		alerta.setHeaderText(null);
		alerta.setContentText(msg);
		
		
		return alerta.showAndWait();
	}

	public static void dialogo(String titulo, String msg) 
	{
		Alert alerta = new Alert(AlertType.INFORMATION);
		
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(msg);
		
		alerta.showAndWait();
		
	}

	
	public static boolean camposVacios (TextField[] campos)
	{
		for (TextField campo : campos)
		{
			if(campo.getText().trim().isEmpty())
			{
				return true;
			}
		}
		
		return false;
		
	}



    

   



}

