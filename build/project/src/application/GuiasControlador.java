package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GuiasControlador implements Initializable {

	
	@FXML TableView<guia> tablaGuias = new TableView<>();
	
	@FXML TableColumn<guia,String> fechaCol = new TableColumn<>();
	@FXML TableColumn<guia,String> nGuiaCol = new TableColumn<>();
	@FXML TableColumn<guia,String> patenteCol= new TableColumn<>();
	@FXML TableColumn<guia,String> choferCol= new TableColumn<>();
	@FXML TableColumn<guia,String> regionCol= new TableColumn<>();
	@FXML TableColumn<guia,String> estadoCol= new TableColumn<>();
	
	ObservableList<guia> guias = FXCollections.observableArrayList();
	
	@FXML DatePicker fechaDesde;
	@FXML DatePicker fechaHasta;
	@FXML ChoiceBox<String> opcionBuscar;
	@FXML TextField buscarInput;
	
	@FXML CheckBox mostrarTodo;
	
	
	public class guia 
	{
		private String chofer;
		private String comuna;
		private String contacto;
		private String direccion;
		private String estado;
		private String fecha;
		private String id;
		private String nombre;
		private String patente;
		private String region;
		private String rut;
		private String urlImagen;
		private String fechaEntrega;
		private String horaEntrega;
		private String detalleEntrega;
		private String idDocumento;
		public guia(String chofer, String comuna, String contacto, String direccion, String estado, String fecha,
				String id, String nombre, String patente, String region, String rut, String urlImagen,
				String fechaEntrega, String horaEntrega, String detalleEntrega, String idDocumento) {
			super();
			this.chofer = chofer;
			this.comuna = comuna;
			this.contacto = contacto;
			this.direccion = direccion;
			this.estado = estado;
			this.fecha = fecha;
			this.id = id;
			this.nombre = nombre;
			this.patente = patente;
			this.region = region;
			this.rut = rut;
			this.urlImagen = urlImagen;
			this.fechaEntrega = fechaEntrega;
			this.horaEntrega = horaEntrega;
			this.detalleEntrega = detalleEntrega;
			this.idDocumento = idDocumento;
		}
		public String getChofer() {
			return chofer;
		}
		public void setChofer(String chofer) {
			this.chofer = chofer;
		}
		public String getComuna() {
			return comuna;
		}
		public void setComuna(String comuna) {
			this.comuna = comuna;
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
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public String getFecha() {
			return fecha;
		}
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getPatente() {
			return patente;
		}
		public void setPatente(String patente) {
			this.patente = patente;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getRut() {
			return rut;
		}
		public void setRut(String rut) {
			this.rut = rut;
		}
		public String getUrlImagen() {
			return urlImagen;
		}
		public void setUrlImagen(String urlImagen) {
			this.urlImagen = urlImagen;
		}
		public String getFechaEntrega() {
			return fechaEntrega;
		}
		public void setFechaEntrega(String fechaEntrega) {
			this.fechaEntrega = fechaEntrega;
		}
		public String getHoraEntrega() {
			return horaEntrega;
		}
		public void setHoraEntrega(String horaEntrega) {
			this.horaEntrega = horaEntrega;
		}
		public String getDetalleEntrega() {
			return detalleEntrega;
		}
		public void setDetalleEntrega(String detalleEntrega) {
			this.detalleEntrega = detalleEntrega;
		}
		public String getIdDocumento() {
			return idDocumento;
		}
		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}
		
		
		
		
	}
	
	
	
	public void cargarGuias () throws InterruptedException, ExecutionException
	{
		CollectionReference coleccion = ConectorFirebase.bdd.collection("guias");
		ApiFuture<QuerySnapshot> snap = coleccion.get();
		
		for (DocumentSnapshot doc : snap.get().getDocuments())
		{
			String imagen,fechaEntrega,horaEntrega,detalleEntrega;
			
			
			if(doc.get("estado").equals("Entregado")||(doc.get("estado").equals("Parcialmente Entregado")))
			{
				imagen = doc.get("imagen").toString();
				fechaEntrega = doc.get("fechaEntrega").toString();
				horaEntrega = doc.get("horaEntrega").toString();
				detalleEntrega = doc.get("detalle de entrega").toString();
			}
			else
			{
				imagen="";
				fechaEntrega = "No disponible";
				horaEntrega = "No disponible";
				detalleEntrega = "No disponible";
			}
			
			guia g = new guia(
					doc.get("chofer").toString(), 
					doc.get("comuna").toString(), 
					doc.get("contacto").toString(), 
					doc.get("direccion").toString(), 
					doc.get("estado").toString(), 
					doc.get("fecha").toString(), 
					doc.get("id").toString(), 
					doc.get("nombre").toString(), 
					doc.get("camion").toString(), 
					doc.get("region").toString(), 
					doc.get("rut").toString(),
					imagen,
					fechaEntrega,
					horaEntrega,
					detalleEntrega,
					doc.getId()
			);
			
			guias.add(g);	
		
		}
			
		tablaGuias.setItems(guias);	
		tablaGuias.refresh();
			
					
	}
		
	public void verDetalle(ActionEvent e)
	{
		guia g = tablaGuias.getSelectionModel().getSelectedItem();
		
		if(g!=null)
		{
			try {
				FXMLLoader loader = new FXMLLoader (getClass().getResource("DetalleGuias.fxml"));
				Parent root = loader.load();
				
				DetalleGuiasControlador ventana = loader.getController();
				ventana.inicializarVariables(g.getFechaEntrega(), g.getHoraEntrega(), g.getUrlImagen(),g.getId(),g.getPatente(),g.getChofer(),g.getDireccion(),g.getComuna(),g.getRegion(),g.getFecha(),g.getRut(),g.getNombre(),g.getContacto(),g.getDetalleEntrega());
				
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
				stage.setTitle("Transportes Olmedo : Detalle Guía");
				stage.showAndWait();
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else
		{
			FUNCIONES.dialogo("Información", "No hay ninguna guía seleccionada");
		}
		
	}
	
	public void buscarPor (ActionEvent e)
	{
		
		String input = buscarInput.getText();
		
		
		if(input.trim().isEmpty()&&mostrarTodo.isSelected())
		{
			tablaGuias.setItems(guias);
	
		}
		else
		{
			String opcion = opcionBuscar.getValue();
			ObservableList<guia> lista= FXCollections.observableArrayList();
			
			if(mostrarTodo.isSelected())
			{
			
				if(opcion.equals("Número Guía/Factura"))
				{
					for(guia g : guias)
					{
						if(g.getId().startsWith(input))
						{
							lista.add(g);
						}
					}
					tablaGuias.setItems(lista);
					
				}
				else if (opcion.equals("Rut chofer"))
				{
					for(guia g : guias)
					{
						if(g.getChofer().startsWith(input))
						{
							lista.add(g);
						}
					}
					tablaGuias.setItems(lista);
				}
				else if (opcion.equals("Patente camión"))
				{
					for(guia g : guias)
					{
						if(g.getPatente().startsWith(input))
						{
							lista.add(g);
						}
					}
					tablaGuias.setItems(lista);
				}
				else if (opcion.equals("Nombre paciente"))
				{
					for(guia g : guias)
					{
						if(g.getNombre().startsWith(input))
						{
							lista.add(g);
						}
					}
					tablaGuias.setItems(lista);
				}
				
				else
				{
					FUNCIONES.dialogoAlerta("Error", "No hay ninguna opción ingresada");
				}
							
				
			}
			else
			{
				try {
					
					List<LocalDate> listaFechas  = obtenerFechasEntre(fechaDesde.getValue(),fechaHasta.getValue());
					
					if(opcion.equals("Número Guía/Factura"))
					{
						for(guia g : guias)
						{
							if(g.getId().startsWith(input)&&fechaEnLista(g.getFecha(),listaFechas))
							{
								lista.add(g);
							}
						}
						tablaGuias.setItems(lista);
						
					}
					else if (opcion.equals("Rut chofer"))
					{
						for(guia g : guias)
						{
							if(g.getRut().startsWith(input)&&fechaEnLista(g.getFecha(),listaFechas))
							{
								lista.add(g);
							}
						}
						tablaGuias.setItems(lista);
					}
					else if (opcion.equals("Patente camión"))
					{
						for(guia g : guias)
						{
							if(g.getPatente().startsWith(input)&&fechaEnLista(g.getFecha(),listaFechas))
							{
								lista.add(g);
							}
						}
						tablaGuias.setItems(lista);
					}
					else if (opcion.equals("Nombre paciente"))
					{
						for(guia g : guias)
						{
							if(g.getNombre().startsWith(input)&&fechaEnLista(g.getFecha(),listaFechas))
							{
								lista.add(g);
							}
						}
						tablaGuias.setItems(lista);
					}
					
					else
					{
						for(guia g : guias)
						{
							if(fechaEnLista(g.getFecha(),listaFechas))
							{
								lista.add(g);
							}
						}
						tablaGuias.setItems(lista);
					}
					
					
				}catch (Exception e1) {
					FUNCIONES.dialogoAlerta("Error", "Hay datos sin ingresar");
				}
			}
		}
							
		
	}
	
	public List<LocalDate> obtenerFechasEntre(LocalDate desde, LocalDate hasta)
	{
		long diasEntreMedio = ChronoUnit.DAYS.between(desde, hasta.plusDays(1));
		return IntStream.iterate(0, i -> i + 1)
				.limit(diasEntreMedio)
				.mapToObj(i->desde.plusDays(i))
				.collect(Collectors.toList());
				
	}
	
	public boolean fechaEnLista (String fecha, List<LocalDate> lista)
	{
		DateTimeFormatter formato= DateTimeFormatter.ofPattern("d-MM-yyyy");
		
		
		for(LocalDate elem:lista)
		{
			
			if (formato.format(elem).toString().equals(fecha))
			{
				return true;
			}
		}
		
		
		return false;
	}
	
	
	public void cambiarOpcionFechas (ActionEvent e)
	{
		if(mostrarTodo.isSelected())
		{
			fechaDesde.setValue(null);
			fechaDesde.setDisable(true);
			fechaHasta.setValue(null);
			fechaHasta.setDisable(true);
			buscarPor(e);
		}
		else
		{
			fechaDesde.setDisable(false);
			fechaHasta.setDisable(false);
		}
	}
	
	
	public void borrarGuias(ActionEvent e)	
	{
		ObservableList<guia> guias = tablaGuias.getSelectionModel().getSelectedItems();
		
		if(!guias.isEmpty())
		{
			Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea eliminar las guías seleccionadas?");
			
			if(opcion.get()==ButtonType.OK)
			{
				
				for(guia g: guias)
				{
				
					ConectorFirebase.bdd.collection("guias").document(g.getIdDocumento()).delete();
					
				}
				try {
					cargarGuias();
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else
		{
			FUNCIONES.dialogo("Información", "No hay ninguna guía seleccionada");
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
		
		tablaGuias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		opcionBuscar.setItems(FXCollections.observableArrayList("Número Guía/Factura","Rut chofer","Patente camión","Nombre paciente"));
		opcionBuscar.getSelectionModel().select(0);
		
		mostrarTodo.setSelected(true);
		fechaDesde.setDisable(true);
		fechaHasta.setDisable(true);
		
			
		fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		nGuiaCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		patenteCol.setCellValueFactory(new PropertyValueFactory<>("patente"));
		choferCol.setCellValueFactory(new PropertyValueFactory<>("chofer"));
		regionCol.setCellValueFactory(new PropertyValueFactory<>("region"));
		estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
		
		
		try {
			cargarGuias();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
