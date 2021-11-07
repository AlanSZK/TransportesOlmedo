package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MenuAdminControlador {
	public void aClientes (ActionEvent e)
	{
		try {
			Scene vistaClientes = new Scene(FXMLLoader.load(getClass().getResource("Clientes.fxml")));
			vistaClientes.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vistaClientes, e,"Transportes Olmedo : Clientes");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void aCamiones (ActionEvent e)
	{
		try {
			Scene vistaCamiones = new Scene(FXMLLoader.load(getClass().getResource("Camiones.fxml")));
			vistaCamiones.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vistaCamiones, e, "Transportes Olmedo : Camiones");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void aGuias (ActionEvent e)
	{
		try {
			Scene vistaGuias = new Scene(FXMLLoader.load(getClass().getResource("Guias.fxml")));
			vistaGuias.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vistaGuias, e, "Transportes Olmedo : Guías");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void aCuentas (ActionEvent e)
	{
		try {
			Scene vistaCuentas = new Scene(FXMLLoader.load(getClass().getResource("Cuentas.fxml")));
			vistaCuentas.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vistaCuentas, e, "Transportes Olmedo : Cuentas");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void aLogin(ActionEvent e)
	{
		try {
			Scene vistaCuentas = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
			vistaCuentas.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vistaCuentas, e, "Transportes Olmedo : Login");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
