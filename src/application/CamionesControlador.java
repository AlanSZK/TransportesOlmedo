package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;

public class CamionesControlador implements Initializable {
	

	
	public class camion
	{
		private String patente;
		private String marca;
		
		public String getPatente() {
			return patente;
		}
		public void setPatente(String patente) {
			this.patente = patente;
		}
		public String getMarca() {
			return marca;
		}
		public void setMarca(String marca) {
			this.marca = marca;
		}
		
		
		
	}
	
	@FXML private TableView<camion> tablaCamiones = new TableView<>();
	
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
	
	public void cargarCamiones()
	{
		
	}

	public void agregarCamion(ActionEvent e)
	{
		
	}
	public void editarCamion(ActionEvent e)
	{
		
	}
	public void borrarCamion(ActionEvent e)
	{
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
