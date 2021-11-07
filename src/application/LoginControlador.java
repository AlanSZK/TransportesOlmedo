package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class LoginControlador {
	
	@FXML public TextField rut;
	@FXML public TextField contrasena;
	
	
	public void verificar (ActionEvent e)
	{
		Scene fxml;
		try {
			
			fxml = new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml")));
			fxml.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(fxml,e,"Transportes Olmedo : Menú principal");		
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
}
