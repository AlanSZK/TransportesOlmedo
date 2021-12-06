package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import application.CuentasControlador.cuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CamionesControlador implements Initializable {
	
	
	
	public class camion
    {
        private String patente;
        private String marca;
        private String idDocumento;

        public camion (String pat, String mar, String idD) {
            this.patente=pat;
            this.marca=mar;
            this.idDocumento=idD;
        } 

        public camion() {
			// TODO Auto-generated constructor stub
		}

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
        public String getIdDocumento() {
            return idDocumento;
        }
        public void setIdDocumento(String idDocumento) {
            this.idDocumento = idDocumento;
        }

    }
	
	@FXML TableView<camion> tablaCamion = new TableView<>();
	@FXML TableColumn<camion, String>  FXpat = new TableColumn<>();
	@FXML TableColumn<camion, String>  FXmar = new TableColumn<>();
	
	ObservableList <camion> listaCamiones = FXCollections.observableArrayList();

	ConectorBDD conector = new ConectorBDD();
	
	public static String patente;
	public static String marca;
	public static String idDocumento;
	
	
	public void cargarCamiones() throws InterruptedException, ExecutionException, IOException
    {
        listaCamiones.clear();
        CollectionReference camiones = ConectorFirebase.bdd.collection("camiones");
        ApiFuture<QuerySnapshot> querySnapshot = camiones.get();
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
        {
            camion c = new camion(
                    doc.get("patente").toString(), 
                    doc.get("marca").toString(),
                    doc.getId().toString()
            );
            listaCamiones.add(c);
        }
        tablaCamion.setItems(listaCamiones);
    }
	
	/*
	public void cargarCamiones() 
	{
		listaCamiones.clear();
		
		Connection con = conector.conectar();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM camion";
		
		try {
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				camion c = new camion(rs.getString("patente"), rs.getString("marca"));
				listaCamiones.add(c);
			}
			tablaCamion.setItems(listaCamiones);
			
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
	
	
	
	public void cargarCamionesFirebase () throws InterruptedException, ExecutionException, IOException
	{
	
		
		CollectionReference camiones = ConectorFirebase.bdd.collection("camiones");
		
		ApiFuture<QuerySnapshot> querySnapshot = camiones.get();
		
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
		{
			System.out.println(doc.get("patente"));
		}
			
	}	
	
	@SuppressWarnings("unused")
	public void verHistorial (ActionEvent e) throws IOException
	{
		camion c = tablaCamion.getSelectionModel().getSelectedItem(); //Cuenta del chofer seleccionado
		
		
		
		if(c!=null)
		{
			try {
				FXMLLoader loader= new FXMLLoader(getClass().getResource("DetalleCamiones.fxml"));
				Parent root = loader.load();
		
				DetalleCamionesControlador ventana = loader.getController();
				
				ventana.inicializarVariables(c.getPatente(),c.getMarca(),c.getIdDocumento());
				
				loader.setController(ventana);
				
				Scene detalle = new Scene(root);
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.setTitle("Transportes Olmedo : Historial camión");
				stage.showAndWait();
				

				
				
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
	
	
	public void agregarCamion (ActionEvent e)
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarCamiones.fxml")));
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.setTitle("Transportes Olmedo : Agregar Camion");
			stage.showAndWait();
			
			try {
				cargarCamiones();
			} catch (InterruptedException | ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void editarCamion(ActionEvent e)
	{
		
	}
	
	public void borrarCamion(ActionEvent e)
	{
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		try {
			cargarCamionesFirebase();
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
		
		e.printStackTrace();
		*/

		FXpat.setCellValueFactory(new PropertyValueFactory<>("patente"));
		FXmar.setCellValueFactory(new PropertyValueFactory<>("marca"));
		
		try {
			cargarCamiones();
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stu	
		//}
	
	
	}
}
