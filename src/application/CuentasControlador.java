package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

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

public class CuentasControlador implements Initializable {
	
	
	public class cuenta
	{
		private int idCuenta;
		private String nombreUsuario;
		private String contrasena;
		private String rut;
		private String nombre;
		private String telefono;
		
		public cuenta (int id,String usr,String con,String rut,String nom,String tel)
		{
			this.idCuenta=id;
			this.nombreUsuario=usr;
			this.contrasena=con;
			this.rut=rut;
			this.nombre=nom;
			this.telefono=tel;
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
	
	ConectorBDD conector = new ConectorBDD();
	
	//CARGAR TABLAS
	public void cargarChoferes()
	{
		listaChoferes.clear();
		
		String query = "SELECT * FROM cuenta INNER JOIN chofer ON "
				+ "chofer.id_cuenta=cuenta.id_cuenta";
		
		Connection con = conector.conectar();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next())
			{
				cuenta c = new cuenta(rs.getInt("id_cuenta"), rs.getString("nombreUsuario"), rs.getString("contrasena"), rs.getString("rut"), rs.getString("nombre"),rs.getString("telefono") );
				listaChoferes.add(c);
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
				pst.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		tablaChoferes.setItems(listaChoferes);	
		
	}
	public void cargarAdministradores()
	{
		listaAdministradores.clear();
		
		String query = "SELECT * FROM cuenta INNER JOIN administrador ON "
				+ "administrador.id_cuenta=cuenta.id_cuenta";
		Connection con = conector.conectar();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next())
			{
				cuenta c = new cuenta(rs.getInt("id_cuenta"), rs.getString("nombreUsuario"), rs.getString("contrasena"), rs.getString("rut"), rs.getString("nombre"),rs.getString("telefono") );
				listaAdministradores.add(c);
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
				pst.close();
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		tablaAdministradores.setItems(listaAdministradores);	
		
		
		
	}
	
	//BOTONES CHOFER
	public void agregarChofer (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarChofer.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Detalle Chofer");
			stage.showAndWait();
			
			cargarChoferes();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void editarChofer (ActionEvent e)
	{
		cuenta c = tablaChoferes.getSelectionModel().getSelectedItem(); //Cuenta del chofer seleccionado
	
		if(c!=null)
		{
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("EditarChofer.fxml"));
				Parent root = loader.load();
		
				EditarChoferControlador ventana = loader.getController();
				
				ventana.inicializarVariables(c.getIdCuenta(), c.getNombreUsuario(), c.getRut(), c.getContrasena(), c.getNombre(), c.getTelefono());
			
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				Stage stage = new Stage();
				
				stage.setScene(detalle);
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
			FUNCIONES.dialogo("Información", "No hay ningún chofer seleccionado");
		}
		
		
		
	}
	public void borrarChofer (ActionEvent e)
	{
		
		cuenta c = tablaChoferes.getSelectionModel().getSelectedItem();
		
		if(c != null)
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea eliminar la cuenta seleccionada?");
			
			if(opcion.get()==ButtonType.OK)
			{
				Connection con = conector.conectar();	
				
				
				
				
				int id = c.getIdCuenta();
				
				String query = "DELETE FROM cuenta WHERE cuenta.id_cuenta=?";
				PreparedStatement pst = null;
				
				try {
					pst = con.prepareStatement(query);
					pst.setInt(1, id);
					
					pst.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					try {
						con.close();
						pst.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				cargarChoferes();		
			}
			
		}
		else
		{
			FUNCIONES.dialogo("Información", "No hay ningún chofer seleccionado");
		}
		
		
	}
	public void verHistorial (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("DetalleChoferes.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
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
				
				ventana.inicializarVariables(c.getIdCuenta(), c.getNombreUsuario(), c.getRut(), c.getContrasena(), c.getNombre(), c.getTelefono());
			
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.setTitle("Transportes Olmedo : Editar Administrador");
				stage.showAndWait();
				
				cargarAdministradores();
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			FUNCIONES.dialogo("Información", "No hay ningún administrador seleccionado");
		}
		
		
		
		
	}
	public void agregarAdministrador (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarAdministrador.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Detalle Chofer");
			stage.showAndWait();
			
			cargarAdministradores();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void borrarAdministrador (ActionEvent e)
	{
		cuenta c = tablaAdministradores.getSelectionModel().getSelectedItem();
		
		if(c != null && !c.nombreUsuario.equals("admin"))
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea eliminar la cuenta seleccionada?");
			
			if(opcion.get()==ButtonType.OK)
			{
				Connection con = conector.conectar();	
				
				int id = c.getIdCuenta();
				
				String query = "DELETE FROM cuenta WHERE cuenta.id_cuenta=?";
				PreparedStatement pst = null;
				
				try {
					pst = con.prepareStatement(query);
					pst.setInt(1, id);
					
					pst.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					try {
						con.close();
						pst.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				cargarChoferes();		
			}
			
		}
		else if(c.nombreUsuario.equals("admin"))
		{
			FUNCIONES.dialogo("Información", "La cuenta seleccionada no puede ser borrada");
		}
		
		else
		{
			FUNCIONES.dialogo("Información", "No hay ningún administrador seleccionado");
		}
		
		
		
		cargarAdministradores();
	}
	
	public void volver (ActionEvent e)
	{
		listaAdministradores.clear();
		listaChoferes.clear();
		
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
		
		rutAdminCol.setCellValueFactory(new PropertyValueFactory<>("rut"));
		nombreAdminCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		telAdminCol.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		
		rutChoferCol.setCellValueFactory(new PropertyValueFactory<>("rut"));
		nombreChoferCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		telChoferCol.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		
		
		cargarChoferes();
		cargarAdministradores();
		
	}
}
