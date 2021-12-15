package application;


import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
		private String horaEntrega;
		private String odometroInicial;
		private String odometroFinal;
		private String detalle;
		public historial(String fechaInicio, String fechaFinal, String horaEntrega, String odometroInicial, String odometroFinal,
				String detalle) {
			super();
			this.fechaInicio = fechaInicio;
			this.fechaFinal = fechaFinal;
			this.horaEntrega = horaEntrega;
			this.odometroInicial = odometroInicial;
			this.odometroFinal = odometroFinal;
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
		public String getOdometroInicial() {
			return odometroInicial;
		}
		public void setOdometroInicial(String odometroInicial) {
			this.odometroInicial = odometroInicial;
		}
		public String getOdometroFinal() {
			return odometroFinal;
		}
		public void setOdometroFinal(String odometroFinal) {
			this.odometroFinal = odometroFinal;
		}
		public String getDetalle() {
			return detalle;
		}
		public void setDetalle(String detalle) {
			this.detalle = detalle;
		}
		public String getHoraEntrega() {
			return horaEntrega;
		}
		public void setHoraEntrega(String horaEntrega) {
			this.horaEntrega = horaEntrega;
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	@FXML private TableView<historial> tablaHistorial = new TableView<>();
	@FXML private TableColumn<historial, String> fechaInicioCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> fechaFinalCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> horaEntregaCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> odometroInicialCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> odometroFinalCol = new TableColumn<>();
	@FXML private TableColumn<historial, String> detalleCol = new TableColumn<>();
	
	ObservableList<historial> lista = FXCollections.observableArrayList();
	
	
	@FXML public TextField patenteLbl;
	@FXML public TextField marcaLbl;
	String docId;

	
	@FXML private TextField rutasTotales;
	
	public void cargarHistorial() throws InterruptedException, ExecutionException
	{
		fechaInicioCol.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		fechaFinalCol.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
		horaEntregaCol.setCellValueFactory(new PropertyValueFactory<>("horaEntrega"));
		odometroInicialCol.setCellValueFactory(new PropertyValueFactory<>("odometroInicial"));
		odometroFinalCol.setCellValueFactory(new PropertyValueFactory<>("odometroFinal"));
		detalleCol.setCellValueFactory(new PropertyValueFactory<>("detalle"));
		
		int rutasCompletadas=0;
		
		Iterable<CollectionReference> colecciones = ConectorFirebase.bdd.collection("camiones").document(docId).listCollections();

		CollectionReference coleccion = null;
		for (CollectionReference c: colecciones) {
				coleccion=c;
		}	
		
		CollectionReference historial = ConectorFirebase.bdd.collection(coleccion.getPath());
		
		ApiFuture<QuerySnapshot> querySnapshot = historial.get();
		
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
		{
			
			try {
				historial h = new historial(
					doc.get("fecha inicial").toString(), 
					doc.get("fecha final").toString(),
					doc.get("Hora de entrega").toString(),
					doc.get("odometro inicial").toString(), 
					doc.get("odometro final").toString(), 
					doc.get("detalles ruta").toString()
					
				);
				
				lista.add(h);
				rutasCompletadas++;
				
			}catch(Exception e1) {
				historial h = new historial(
						doc.get("fecha inicial").toString(), 
						"No disponible",
						"No disponible",
						doc.get("odometro inicial").toString(), 
						"No disponible", 
						"No disponible"
				);
				lista.add(h);
			}
			
																				
			
			
		}
			
		tablaHistorial.setItems(lista);
		rutasTotales.setText(String.valueOf(rutasCompletadas));
			
		
	}

	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	
	
	
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
