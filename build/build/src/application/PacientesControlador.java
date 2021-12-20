package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PacientesControlador implements Initializable{
	
	public class paciente
	{
		private String idCliente;
		private String idPaciente;	
		private String nombreCliente;
		private String nombrePaciente;
		private String contacto;
		private String direccion;
		private String comuna;
		private String idDocumento;
		
		
		public paciente(String idCliente, String idPaciente, String nombreCliente, String nombrePaciente,
				String contacto, String direccion, String comuna, String idDocumento) {
			super();
			this.idCliente = idCliente;
			this.idPaciente = idPaciente;
			this.nombreCliente = nombreCliente;
			this.nombrePaciente = nombrePaciente;
			this.contacto = contacto;
			this.direccion = direccion;
			this.comuna = comuna;
			this.idDocumento = idDocumento;
		}
		public String getIdCliente() {
			return idCliente;
		}
		public void setIdCliente(String idCliente) {
			this.idCliente = idCliente;
		}
		public String getIdPaciente() {
			return idPaciente;
		}
		public void setIdPaciente(String idPaciente) {
			this.idPaciente = idPaciente;
		}
		public String getNombreCliente() {
			return nombreCliente;
		}
		public void setNombreCliente(String nombreCliente) {
			this.nombreCliente = nombreCliente;
		}
		public String getNombrePaciente() {
			return nombrePaciente;
		}
		public void setNombrePaciente(String nombrePaciente) {
			this.nombrePaciente = nombrePaciente;
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
		public String getIdDocumento() {
			return idDocumento;
		}
		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}
		
		
		
		
	}
	
	@FXML TableView<paciente> tablaPacientes = new TableView<>();
	
	@FXML TableColumn<paciente,String> pacienteCol = new TableColumn<>();
	@FXML TableColumn<paciente,String> clienteCol = new TableColumn<>();
	@FXML TableColumn<paciente,String> contactoCol = new TableColumn<>();
	@FXML TableColumn<paciente,String> direccionCol = new TableColumn<>();

	
	ObservableList <paciente> listaPacientes = FXCollections.observableArrayList();
	
	@FXML ChoiceBox<String> opcionBuscar;
	@FXML TextField buscarInput;
	public static int contadorClientes=0;
	
	
	public void cargarPacientes () throws InterruptedException, ExecutionException
	{
		listaPacientes.clear();
		contadorClientes=0;
		
		CollectionReference clientes = ConectorFirebase.bdd.collection("pacientes");	
		ApiFuture<QuerySnapshot> querySnapshot = clientes.get();
		
		if(!querySnapshot.get().getDocuments().isEmpty())
		{
		
			for(DocumentSnapshot doc : querySnapshot.get().getDocuments())
			{
				paciente p = new paciente(doc.get("idCliente").toString(), doc.get("idPaciente").toString(), doc.get("nombreCliente").toString(), doc.get("nombrePaciente").toString(), doc.get("contacto").toString(), doc.get("direccionPaciente").toString(),doc.get("comuna").toString(),doc.getId().toString() );
			
				listaPacientes.add(p);
				
				contadorClientes++;
			}
			
			tablaPacientes.setItems(listaPacientes);
			tablaPacientes.refresh();
		}
		
	}
	
	public void agregarPaciente(ActionEvent e) throws InterruptedException, ExecutionException
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarPaciente.fxml")));
			detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Agregar Paciente");
			stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
			stage.showAndWait();
			
			cargarPacientes();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void editarPaciente (ActionEvent e)
	{
		paciente p = tablaPacientes.getSelectionModel().getSelectedItem(); //Cuenta del chofer seleccionado
		
		
		
		if(p!=null)
		{
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("EditarPaciente.fxml"));
				Parent root = loader.load();
		
				EditarPacienteControlador ventana = loader.getController();
				
				ventana.inicializarVariables(p.getIdCliente(), p.getNombreCliente(), p.getIdPaciente(), p.getNombrePaciente(), p.getComuna(), p.getDireccion(), p.getContacto(), p.getIdDocumento());
			
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.setTitle("Transportes Olmedo : Editar Paciente");
				stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
				stage.showAndWait();
				
				
				try {
					cargarPacientes();
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
			FUNCIONES.dialogo("Información", "No hay ningún cliente seleccionado");
		}
		
	}
	public void borrarPaciente (ActionEvent e)
	{
		
		ObservableList<paciente> pacientes = tablaPacientes.getSelectionModel().getSelectedItems();
		
		if(!pacientes.isEmpty())
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea eliminar a los pacientes seleccionados?");
			
			if(opcion.get()==ButtonType.OK)
			{
				
				for(paciente p : pacientes)
				{
				
					ConectorFirebase.bdd.collection("pacientes").document(p.getIdDocumento()).delete();
					
				}
				try {
					cargarPacientes();
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else
		{
			FUNCIONES.dialogo("Información", "No hay ningún paciente seleccionado");
		}
		
	}
	
	
	@SuppressWarnings({ "resource", "unused" })
	public void cargarExcel (ActionEvent e)
	{
		FileChooser fileChooser = new FileChooser();
		
		File excel = fileChooser.showOpenDialog((Stage)((Node)e.getTarget()).getScene().getWindow());
		try {
			FileInputStream excelInput = new FileInputStream(excel);
			
			XSSFWorkbook libroExcel = new XSSFWorkbook(excelInput);
			XSSFSheet hoja = libroExcel.getSheetAt(0);
			String idPaciente="";
			
			int i=0;
			Iterator<Row> iterador = hoja.iterator();
			
			
			
			while(iterador.hasNext())
			{
				Row row = iterador.next();
				if(i!=0)
				{
					HashMap<String, Object> datos = new HashMap<>();		
					
					DataFormatter format = new DataFormatter();
					try
					{
						
						datos.put("idCliente",format.formatCellValue(row.getCell(0)));
						datos.put("nombreCliente",format.formatCellValue(row.getCell(1)));
						datos.put("idPaciente",format.formatCellValue(row.getCell(2)));
						datos.put("nombrePaciente", row.getCell(3).toString());
						datos.put("contacto", row.getCell(7).toString());
						datos.put("direccionPaciente", row.getCell(8).toString());
						datos.put("comuna", row.getCell(6).toString());
						
						
						
						DocumentReference ref = ConectorFirebase.bdd.collection("pacientes").document(format.formatCellValue(row.getCell(2)));
						ref.set(datos);
						idPaciente = row.getCell(2).toString();
						
						
						
					}catch (Exception e1) {
						e1.printStackTrace();
						FUNCIONES.dialogo("Éxito", "Pacientes cargados con éxito");
						break;
					}
				}
				i++;
				
			
			}
			
		} catch (IOException | POIXMLException e2) {
			FUNCIONES.dialogoAlerta("Error", "El archivo seleccionado no es correcto");
			}
								
		
		try {
			cargarPacientes();
		} catch (InterruptedException |ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
			
		
	}
	
	public void buscarPor (ActionEvent e)
	{
		
		String input = buscarInput.getText().toUpperCase();
		
		if(input.trim().isEmpty())
		{
			tablaPacientes.setItems(listaPacientes);
	
		}
		else
		{
			String opcion = opcionBuscar.getValue();
			ObservableList<paciente> lista= FXCollections.observableArrayList();
			
			if(opcion.equals("Código cliente"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getIdCliente().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
				
			}
			else if (opcion.equals("Código paciente"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getIdPaciente().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
			}
			else if (opcion.equals("Nombre cliente"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getNombreCliente().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
			}
			else if (opcion.equals("Nombre paciente"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getNombrePaciente().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
			}
			else if (opcion.equals("Comuna"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getComuna().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
			}
			else if (opcion.equals("Dirección"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getDireccion().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
			}
			else if (opcion.equals("Contacto"))
			{
				for(paciente p : listaPacientes)
				{
					if(p.getContacto().startsWith(input))
					{
						lista.add(p);
					}
				}
				tablaPacientes.setItems(lista);
			}
			else
			{
				FUNCIONES.dialogoAlerta("Error", "No hay ninguna opción ingresada");
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
		
		tablaPacientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		opcionBuscar.setItems(FXCollections.observableArrayList("Código cliente","Código paciente","Nombre cliente","Nombre paciente","Comuna","Dirección","Contacto"));
		
		pacienteCol.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));
		clienteCol.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
		contactoCol.setCellValueFactory(new PropertyValueFactory<>("contacto"));
		direccionCol.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		
		try {
			cargarPacientes();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
