package application;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import io.grpc.netty.shaded.io.netty.handler.codec.DateFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GuiasControlador implements Initializable {

	
	@FXML TableView<guia> tablaGuias = new TableView<>();
	
	@FXML TableColumn<guia,String> fechaCol = new TableColumn<>();
	@FXML TableColumn<guia,String> nGuiaCol = new TableColumn<>();
	@FXML TableColumn<guia,String> patenteCol= new TableColumn<>();
	@FXML TableColumn<guia,String> choferCol= new TableColumn<>();
	@FXML TableColumn<guia,String> regionCol= new TableColumn<>();
	@FXML TableColumn<guia,String> estadoCol= new TableColumn<>();
	
	ObservableList<guia> guias = FXCollections.observableArrayList();
	
	@FXML private TextField idInput;
	@FXML private DatePicker fechaInput;
	
	
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
		
		
		
		
		public guia(String chofer, String comuna, String contacto, String direccion, String estado, String fecha,
				String id, String nombre, String patente, String region, String rut) {
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
	}
	
	
	
	public void cargarGuias () throws InterruptedException, ExecutionException
	{
		CollectionReference coleccion = ConectorFirebase.bdd.collection("guias");
		ApiFuture<QuerySnapshot> snap = coleccion.get();
		
		for (DocumentSnapshot doc : snap.get().getDocuments())
		{
			guia g = new guia(
					doc.get("chofer").toString(), 
					doc.get("comuna").toString(), 
					doc.get("contacto").toString(), 
					doc.get("direccion").toString(), 
					doc.get("estado").toString(), 
					doc.get("fecha").toString(), 
					doc.get("id").toString(), 
					doc.get("nombre").toString(), 
					doc.get("patente").toString(), 
					doc.get("region").toString(), 
					doc.get("rut").toString());
			
			guias.add(g);	
					
		}
		
		tablaGuias.setItems(guias);
		
	}
	
	public void mostrarGuiasPorId (ActionEvent e)
	{
		
		ObservableList<guia> listaGuias = FXCollections.observableArrayList();
		
		String input = idInput.getText();
		
		if(input.trim().isEmpty())
		{
			tablaGuias.setItems(guias);
	
		}
		else
		{
			for(guia g : guias)
			{
				if(g.getId().startsWith(input))
				{
					listaGuias.add(g);
				}
			}
			tablaGuias.setItems(listaGuias);
			
			
		}
		
		
		
		
		
		
	}
	
	public void mostrarGuiasPorFecha (ActionEvent e)
	{
		ObservableList<guia> listaGuias = FXCollections.observableArrayList();
		
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyy");
		
		String fecha = fechaInput.getValue().format(formatoFecha);
		
		
		if(fecha.trim().isEmpty())
		{
			tablaGuias.setItems(guias);
	
		}
		else
		{
			for(guia g : guias)
			{
				if(g.getFecha().equals(fecha))
				{
					listaGuias.add(g);
				}
			}
			tablaGuias.setItems(listaGuias);
			
			
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
