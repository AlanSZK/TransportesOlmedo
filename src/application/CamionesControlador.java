package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;

public class CamionesControlador implements Initializable {
	
	ConectorFirebase conector = new ConectorFirebase();
	
	public class camion
	{
		private String patente;
		private String marca;
		
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
	
	@FXML private TableView<camion> tablaCamiones = new TableView<>();
	
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
	
	public void cargarCamiones()
	{
		
	}
	
	
	public void cargarCamionesFirebase () throws InterruptedException, ExecutionException, IOException
	{
	
		
		CollectionReference camiones = ConectorFirebase.bdd.collection("camiones");
		
		ApiFuture<QuerySnapshot> querySnapshot = camiones.get();
		
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
		{
			System.out.println(doc.get("patente"));
		}
			
	}	
	

	public void agregarCamion(ActionEvent e)
	{
		
	}
	public void editarCamion(ActionEvent e)
	{
		
	}
	public void borrarCamion(ActionEvent e)
	{
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			cargarCamionesFirebase();
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
		// TODO Auto-generated method stu	
		}
	}
}
