package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import application.CamionesControlador.camion;
import application.CuentasControlador.cuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DetalleCamionesControlador  {
	
	public class historial
	{
		private String fechaInicio;
		private String fechaFinal;
		private String rutChofer;
		private String odometro;
		private String detalle;
		
		
		
		public historial(String fechaInicio, String fechaFinal, String rutChofer, String odometro, String detalle) {
			super();
			this.fechaInicio = fechaInicio;
			this.fechaFinal = fechaFinal;
			this.rutChofer = rutChofer;
			this.odometro = odometro;
			this.detalle = detalle;
		}
		
		
		public String getFechaInicio() {
			return fechaInicio;
		}
		public void setFechaInicio(String fechaInicio) {
			this.fechaInicio = fechaInicio;
		}
		public String getFechaFinal() {
			return fechaFinal;
		}
		public void setFechaFinal(String fechaFinal) {
			this.fechaFinal = fechaFinal;
		}
		public String getRutChofer() {
			return rutChofer;
		}
		public void setRutChofer(String rutChofer) {
			this.rutChofer = rutChofer;
		}
		public String getOdometro() {
			return odometro;
		}
		public void setOdometro(String odometro) {
			this.odometro = odometro;
		}
		public String getDetalle() {
			return detalle;
		}
		public void setDetalle(String detalle) {
			this.detalle = detalle;
		}
		
		
		
	}
	
	@FXML private TableView<historial> tablaHistorial = new TableView<>();
	@FXML private TableColumn<historial, String> fechaInicioCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> fechaFinalCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> rutChoferCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> odometroCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> detalleCol = new TableColumn<>();
	
	ObservableList<historial> lista = FXCollections.observableArrayList();
	
	
	@FXML public TextField patenteLbl;
	@FXML public TextField marcaLbl;
	String docId;

	
	@FXML private Label rutasTotales = new Label();
	
	
	public void cargarHistorial() throws InterruptedException, ExecutionException
	{
		fechaInicioCol.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		fechaFinalCol.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
		rutChoferCol.setCellValueFactory(new PropertyValueFactory<>("rutChofer"));
		odometroCol.setCellValueFactory(new PropertyValueFactory<>("odometro"));
		detalleCol.setCellValueFactory(new PropertyValueFactory<>("detalle"));
		
		Iterable<CollectionReference> colecciones = ConectorFirebase.bdd.collection("camiones").document(docId).listCollections();

		CollectionReference coleccion = null;
		for (CollectionReference c: colecciones) {
				coleccion=c;
		}	
		
		CollectionReference historial = ConectorFirebase.bdd.collection(coleccion.getPath());
		
		ApiFuture<QuerySnapshot> querySnapshot = historial.get();
		
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
		{
			
			historial h = new historial(
					doc.get("fechaInicio").toString(), 
					doc.get("fechaFinal").toString(), 
					doc.get("rutChofer").toString(), 
					doc.get("odometro").toString(), 
					doc.get("detalle").toString()
					
				);
																				
			lista.add(h);
			break;
			
		}
			
		tablaHistorial.setItems(lista);
			
		
	}

	
	
	
	/*
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		fechaInicioCol.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		fechaFinalCol.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
		rutChoferCol.setCellValueFactory(new PropertyValueFactory<>("rutChofer"));
		odometroCol.setCellValueFactory(new PropertyValueFactory<>("odometro"));
		detalleCol.setCellValueFactory(new PropertyValueFactory<>("detalle"));
			
		try {
			cargarHistorial();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	
	public void inicializarVariables(String pat, String mar, String doc)
	{
		
		patenteLbl.setText(pat);
		marcaLbl.setText(mar);
		docId= doc;
		
		try {
			cargarHistorial();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
