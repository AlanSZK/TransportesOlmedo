package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.CuentasControlador.cuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CamionesControlador implements Initializable {
	

	
	public class camion
	{
		private String patente;
		private String marca;
		
		public camion (String pat, String mar) {
			this.patente=pat;
			this.marca=mar;
		} 
		
		
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
	
	@FXML TableView<camion> tablaCamion = new TableView<>();
	@FXML TableColumn<camion, String>  FXpat = new TableColumn<>();
	@FXML TableColumn<camion, String>  FXmar = new TableColumn<>();
	
	ObservableList <camion> listaCamiones = FXCollections.observableArrayList();

	ConectorBDD conector = new ConectorBDD();
	
	public void cargarCamiones() 
	{
		listaCamiones.clear();
		
		Connection con = conector.conectar();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM camion";
		
		try {
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				camion c = new camion(rs.getString("patente"), rs.getString("marca"));
				listaCamiones.add(c);
			}
			tablaCamion.setItems(listaCamiones);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
	

	
	
	public void agregarCamion (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarCamiones.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Agregar Camion");
			stage.showAndWait();
			
			cargarCamiones();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void editarCamion(ActionEvent e)
	{
		
	}
	
	public void borrarCamion(ActionEvent e)
	{
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FXpat.setCellValueFactory(new PropertyValueFactory<>("patente"));
		FXmar.setCellValueFactory(new PropertyValueFactory<>("marca"));
		
		cargarCamiones();
		
	}
}
