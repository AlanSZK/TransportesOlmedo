package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CuentasControlador implements Initializable {

	public void cargarChoferes()
	{
		
	}
	public void cargarAdministradores()
	{
		
	}
	
	public void agregarChofer (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarChofer.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Detalle Chofer");
			stage.showAndWait();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void editarChofer (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("EditarChofer.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Detalle Chofer");
			stage.showAndWait();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	public void borrarChofer (ActionEvent e)
	{
		
	}
	
	
	public void editarAdministrador (ActionEvent e)
	{
		
	}
	public void agregarAdministrador (ActionEvent e)
	{
		
	}
	public void borrarAdministrador (ActionEvent e)
	{
		
	}
	
	public void volver (ActionEvent e)
	{
		try {
			Scene vista = new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml")));
			vista.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vista, e, "Transportes Olmedo : Menú principal");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarChoferes();
		cargarAdministradores();
		
	}
}
