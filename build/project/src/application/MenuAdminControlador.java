package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuAdminControlador implements Initializable{
	
	@FXML private ImageView imgPacientes;
	@FXML private ImageView imgGuias;
	@FXML private ImageView imgCamiones;
	@FXML private ImageView imgCuentas;
	@FXML private ImageView imgSalir;
	
	
	public void aPacientes (ActionEvent e)
	{
		try {
			Scene vistaClientes = new Scene(FXMLLoader.load(getClass().getResource("Pacientes.fxml")));
			vistaClientes.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vistaClientes, e,"Transportes Olmedo : Pacientes");
			
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			imgPacientes.setImage(new Image(new FileInputStream("img/clientes.png")));
			imgGuias.setImage(new Image(new FileInputStream("img/guias.png")));
			imgCamiones.setImage(new Image(new FileInputStream("img/camion.png")));
			imgCuentas.setImage(new Image(new FileInputStream("img/usuario.png")));
			imgSalir.setImage(new Image(new FileInputStream("img/salir.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
