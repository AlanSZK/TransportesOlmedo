package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CuentasControlador implements Initializable {
	
	
	public class cuenta
	{
		private int idCuenta;
		private String nombreUsuario;
		private String contrasena;
		private String rut;
		private String nombre;
		private String telefono;
		private String idDocumento;
		
		public cuenta (int id,String usr,String con,String rut,String nom,String tel,String idDoc)
		{
			this.idCuenta=id;
			this.nombreUsuario=usr;
			this.contrasena=con;
			this.rut=rut;
			this.nombre=nom;
			this.telefono=tel;
			this.idDocumento = idDoc;
			
		}
		
	

		public int getIdCuenta() {
			return idCuenta;
		}
		public void setIdCuenta(int idCuenta) {
			this.idCuenta = idCuenta;
		}
		public String getNombreUsuario() {
			return nombreUsuario;
		}
		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}
		public String getContrasena() {
			return contrasena;
		}
		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
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
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}


		public String getIdDocumento() {
			return idDocumento;
		}


		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}
		
	}
	

	@FXML TableView<cuenta> tablaChoferes = new TableView<>();
	@FXML TableColumn<cuenta,String> rutChoferCol = new TableColumn<>();
	@FXML TableColumn<cuenta,String> nombreChoferCol = new TableColumn<>();
	@FXML TableColumn<cuenta,String> telChoferCol = new TableColumn<>();
	
	
	@FXML TableView<cuenta> tablaAdministradores = new TableView<>();
	@FXML TableColumn<cuenta,String> rutAdminCol = new TableColumn<>();
	@FXML TableColumn<cuenta,String> nombreAdminCol = new TableColumn<>();
	@FXML TableColumn<cuenta,String> telAdminCol = new TableColumn<>();
	
	ObservableList<cuenta> listaChoferes = FXCollections.observableArrayList();
	ObservableList<cuenta> listaAdministradores = FXCollections.observableArrayList();
	

	public static int contadorChoferes = 0;
	public static int contadorAdmins= 0;
	
	
	
	
	
	public void volver (ActionEvent e)
	{
		listaAdministradores.clear();
		listaChoferes.clear();
		
		try {
			Scene vista = new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml")));
			vista.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			FUNCIONES.cambiarEscena(vista, e, "Transportes Olmedo : Men� principal");
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
		
		

	public void cargarAdministradores()
	{
		
		listaAdministradores.clear();
		contadorAdmins=0;
		
		CollectionReference administradores = ConectorFirebase.bdd.collection("administradores");
		
		ApiFuture<QuerySnapshot> querySnapshot = administradores.get();
		
		try {
			for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
			{
				
				cuenta c = new cuenta(
						Integer.valueOf(doc.get("idCuenta").toString()), 
						doc.get("usuario").toString(), 
						doc.get("contrasena").toString(), 
						doc.get("rut").toString(), 
						doc.get("nombre").toString(), 
						doc.get("telefono").toString(),	
						doc.getId()
					);
																					
				contadorAdmins++;
				listaAdministradores.add(c);
				
			}
			
			tablaAdministradores.setItems(listaAdministradores);
			tablaAdministradores.refresh();
			
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void cargarChoferes () throws InterruptedException, ExecutionException, IOException
	{
	
		listaChoferes.clear();
		contadorChoferes=0;
		
		CollectionReference choferes = ConectorFirebase.bdd.collection("choferes");
		
		ApiFuture<QuerySnapshot> querySnapshot = choferes.get();
		
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
		{
			cuenta c = new cuenta(
					Integer.valueOf(doc.get("idCuenta").toString()), 
					doc.get("usuario").toString(), 
					doc.get("contrasena").toString(), 
					doc.get("rut").toString(), 
					doc.get("nombre").toString(), 
					doc.get("telefono").toString(),
					doc.getId()
				);
																				
			contadorChoferes++;
			listaChoferes.add(c);
		}
		
		tablaChoferes.setItems(listaChoferes);
		tablaChoferes.refresh();
		
	}
	


	//BOTONES CHOFER
	public void agregarChofer (ActionEvent e) throws InterruptedException, ExecutionException
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarChofer.fxml")));
			detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
			stage.setTitle("Transportes Olmedo : Agregar Chofer");
			stage.showAndWait();
			
			cargarChoferes();
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void editarChofer (ActionEvent e) throws InterruptedException, ExecutionException
	{
		cuenta c = tablaChoferes.getSelectionModel().getSelectedItem(); //Cuenta del chofer seleccionado
	
		if(c!=null)
		{
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("EditarChofer.fxml"));
				Parent root = loader.load();
		
				EditarChoferControlador ventana = loader.getController();
				
				ventana.inicializarVariables(c.getIdCuenta(), c.getNombreUsuario(), c.getRut(), c.getContrasena(), c.getNombre(), c.getTelefono(),c.getIdDocumento());
			
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
				stage.setTitle("Transportes Olmedo : Editar Chofer");
				stage.showAndWait();
				
				cargarChoferes();
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			FUNCIONES.dialogo("Informaci�n", "No hay ning�n chofer seleccionado");
		}
		
		
		
	}
	public void borrarChofer (ActionEvent e) throws NumberFormatException, InterruptedException, ExecutionException, IOException
	{
		

		ObservableList<cuenta> choferes = tablaChoferes.getSelectionModel().getSelectedItems();
		
		if(!choferes.isEmpty())
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("�Est� seguro que desea eliminar las cuentas seleccionadas?");
			
			
			
			if(opcion.get()==ButtonType.OK)
			{
				for(cuenta c : choferes)
				{
					ConectorFirebase.bdd.collection("choferes").document(c.getIdDocumento()).delete();					
				}	
				
				cargarChoferes();
				
			
			}		
			
		}
		else
		{
			FUNCIONES.dialogo("Informaci�n", "No hay ning�n chofer seleccionado");
		}
		
	}
		
	public void verHistorial (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("DetalleChoferes.fxml")));
			
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
			stage.setTitle("Transportes Olmedo : Historial Chofer");
			stage.showAndWait();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void editarAdministrador (ActionEvent e)
	{
		cuenta c = tablaAdministradores.getSelectionModel().getSelectedItem(); //Cuenta del chofer seleccionado
		
		if(c!=null)
		{
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("EditarAdministrador.fxml"));
				Parent root = loader.load();
		
				EditarAdministradorControlador ventana = loader.getController();
				
				ventana.inicializarVariables(c.getIdCuenta(), c.getNombreUsuario(), c.getRut(), c.getContrasena(), c.getNombre(), c.getTelefono(),c.getIdDocumento());
			
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.setTitle("Transportes Olmedo : Editar Administrador");
				stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
				stage.showAndWait();
			
				cargarAdministradores();
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			FUNCIONES.dialogo("Informaci�n", "No hay ning�n administrador seleccionado");
		}
		
		
		
		
	}
	public void agregarAdministrador (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarAdministrador.fxml")));
			detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Agregar administrador");
			stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
			stage.showAndWait();
			
			cargarAdministradores();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void borrarAdministrador (ActionEvent e)
	{
		
		
		ObservableList<cuenta> admins = tablaAdministradores.getSelectionModel().getSelectedItems();
		
		if(!admins.isEmpty())
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("�Est� seguro que desea eliminar las cuentas seleccionadas?");
			
			
			
			if(opcion.get()==ButtonType.OK)
			{
				for(cuenta c : admins)
				{
					if(c.getIdCuenta()==1)
					{
						FUNCIONES.dialogo("Informaci�n", "La cuenta seleccionada principal del administrador no puede ser borrada");
					}
					else
					{
						ConectorFirebase.bdd.collection("administradores").document(c.getIdDocumento()).delete();
					}
										
				}	
			
				cargarAdministradores();
				
			
			}		
			
		}
		else
		{
			FUNCIONES.dialogo("Informaci�n", "No hay ning�n administrador seleccionado");
		}
	
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tablaChoferes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tablaAdministradores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		rutAdminCol.setCellValueFactory(new PropertyValueFactory<>("rut"));
		nombreAdminCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		telAdminCol.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		
		rutChoferCol.setCellValueFactory(new PropertyValueFactory<>("rut"));
		nombreChoferCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		telChoferCol.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		
		
		try {
			cargarChoferes();
			cargarAdministradores();
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
	}
	
	
	
	

	
}
