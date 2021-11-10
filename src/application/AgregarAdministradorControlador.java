package application;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AgregarAdministradorControlador {
	
	public void confirmar (ActionEvent e)
	{
		
	}
	
	public void cancelar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
}
