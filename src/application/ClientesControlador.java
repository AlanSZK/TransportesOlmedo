package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import application.CuentasControlador.cuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClientesControlador implements Initializable{
	
	public class cliente
	{
		private int idCliente;
		private String rut;
		private String nombre;
		private String contacto;
		private String direccion;
		private String comuna;
		private String region;
		private String idDocumento;
		
		
		public cliente(int idC, String rut, String nom, String con, String dir, String com, String reg,String doc)
		{
			this.idCliente = idC;
			this.rut = rut;
			this.nombre = nom;
			this.contacto = con;
			this.direccion = dir;
			this.comuna = com;
			this.region = reg;
			this.idDocumento = doc;
		}
		
		public int getIdCliente() {
			return idCliente;
		}
		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
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

		public String getIdDocumento() {
			return idDocumento;
		}

		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}
		
	}
	
	@FXML TableView<cliente> tablaClientes = new TableView<>();
	
	@FXML TableColumn<cliente,String> rutCol = new TableColumn<>();
	@FXML TableColumn<cliente,String> nombreCol = new TableColumn<>();
	@FXML TableColumn<cliente,String> contactoCol = new TableColumn<>();
	@FXML TableColumn<cliente,String> direccionCol = new TableColumn<>();

	
	ObservableList <cliente> listaClientes = FXCollections.observableArrayList();
	
	public static int contadorClientes=0;
	
	
	public void cargarClientes () throws InterruptedException, ExecutionException
	{
		listaClientes.clear();
		contadorClientes=0;
		
		CollectionReference clientes = ConectorFirebase.bdd.collection("clientes");	
		ApiFuture<QuerySnapshot> querySnapshot = clientes.get();
		
		for(DocumentSnapshot doc : querySnapshot.get().getDocuments())
		{
			cliente c = new cliente(Integer.valueOf(doc.get("idCliente").toString()), doc.get("rut").toString(), doc.get("nombre").toString(), doc.get("contacto").toString(), doc.get("direccion").toString(), doc.get("comuna").toString(), doc.get("region").toString(),doc.getId());
		
			listaClientes.add(c);
			
			contadorClientes++;
		}
		
		tablaClientes.setItems(listaClientes);
		
	}
	
	public void agregarCliente (ActionEvent e) throws InterruptedException, ExecutionException
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
		cliente c = tablaClientes.getSelectionModel().getSelectedItem(); //Cuenta del chofer seleccionado
		
		if(c!=null)
		{
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("EditarCliente.fxml"));
				Parent root = loader.load();
		
				EditarClienteControlador ventana = loader.getController();
				
				ventana.inicializarVariables(c.getRut(), c.getNombre(), c.getContacto(), c.getDireccion(), c.getComuna(), c.getRegion(),c.getIdDocumento());
			
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.setTitle("Transportes Olmedo : Editar Chofer");
				stage.showAndWait();
				
				
				try {
					cargarClientes();
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			FUNCIONES.dialogo("Información", "No hay ningún chofer seleccionado");
		}
		
	}
	public void borrarCliente (ActionEvent e)
	{
		cliente c = tablaClientes.getSelectionModel().getSelectedItem();
		
		if(c != null)
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea eliminar el cliente seleccionado?");
			
			if(opcion.get()==ButtonType.OK)
			{
				
				ConectorFirebase.bdd.collection("clientes").document(c.getIdDocumento()).delete();
				
				try {
					cargarClientes();
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
																														
			}
				
			else
			{
				FUNCIONES.dialogo("Información", "No hay ningún cliente seleccionado");
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rutCol.setCellValueFactory(new PropertyValueFactory<>("rut"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		contactoCol.setCellValueFactory(new PropertyValueFactory<>("contacto"));
		direccionCol.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		
		try {
			cargarClientes();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	/*
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
	*/
	
}
