package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class ClientesControlador implements Initializable{
	
	public class cliente
	{
		private int idCliente;
		private int idUbicacion;
		private String rut;
		private String nombre;
		private String contacto;
		private String direccion;
		private String comuna;
		private String region;
		
		
		public cliente(int idC, int idU, String rut, String nom, String con, String dir, String com, String reg)
		{
			this.idCliente = idC;
			this.idUbicacion = idU;
			this.rut = rut;
			this.nombre = nom;
			this.contacto = con;
			this.direccion = dir;
			this.comuna = com;
			this.region = reg;
		}
		
		public int getIdCliente() {
			return idCliente;
		}
		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}
		public int getIdUbicacion() {
			return idUbicacion;
		}
		public void setIdUbicacion(int idUbicacion) {
			this.idUbicacion = idUbicacion;
		}
		public String getRut() {
			return rut;
		}
		public void setRut(String rut) {
			this.rut = rut;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getContacto() {
			return contacto;
		}
		public void setContacto(String contacto) {
			this.contacto = contacto;
		}
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getComuna() {
			return comuna;
		}
		public void setComuna(String comuna) {
			this.comuna = comuna;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		
	}
	
	@FXML TableView<cliente> tablaClientes = new TableView<>();
	
	@FXML TableColumn<cliente,String> rutCol = new TableColumn<>();
	@FXML TableColumn<cliente,String> nombreCol = new TableColumn<>();
	@FXML TableColumn<cliente,String> contactoCol = new TableColumn<>();
	@FXML TableColumn<cliente,String> direccionCol = new TableColumn<>();

	
	ObservableList <cliente> listaClientes = FXCollections.observableArrayList();
	
	ConectorBDD conector = new ConectorBDD();
	
	public void cargarClientes ()
	{
		listaClientes.clear();
		
		Connection con = conector.conectar();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM cliente INNER JOIN ubicacion ON cliente.idUbicacion=ubicacion.idUbicacion";
		
		try {
			pst = con.prepareStatement(query);
			
			rs = pst.executeQuery();
			
			while (rs.next())
			{
				cliente c = new cliente (rs.getInt("idCliente"), rs.getInt("idUbicacion"), rs.getString("rut"), rs.getString("nombre"), rs.getString("contacto"), rs.getString("direccion"), rs.getString("comuna"), rs.getString("region"));
				
				listaClientes.add(c);
			
			}
			
			tablaClientes.setItems(listaClientes);
			
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
	
	
	public void agregarCliente (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarCliente.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Agregar Cliente");
			stage.showAndWait();
			
			cargarClientes();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void editarCliente (ActionEvent e)
	{
		
	}
	public void borrarCliente (ActionEvent e)
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
		rutCol.setCellValueFactory(new PropertyValueFactory<>("rut"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		contactoCol.setCellValueFactory(new PropertyValueFactory<>("contacto"));
		direccionCol.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		
		cargarClientes();
		
		
		
	}
	
}
