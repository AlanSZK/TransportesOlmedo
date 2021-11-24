package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarAdministradorControlador {
	
	@FXML public TextField idUsuario=new TextField();
	@FXML private TextField nombreUsuario=new TextField();
	@FXML private TextField rutUsuario=new TextField();
	@FXML private TextField contrasenaUsuario=new TextField();
	@FXML private TextField nombreAdministrador=new TextField();
	@FXML private TextField telefonoAdministrador=new TextField();
	
	public void confirmar (ActionEvent e)
	{
		
	}
	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	
	public void inicializarVariables(int id, String usr, String rut, String con, String nom, String tel)
	{
		idUsuario.setText(String.valueOf(id));
		nombreUsuario.setText(usr);
		rutUsuario.setText(rut);
		contrasenaUsuario.setText(con);
		nombreAdministrador.setText(nom);
		telefonoAdministrador.setText(tel);
		
	}
}
