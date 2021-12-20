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
        tablaCamion.refresh();
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
				detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				
				stage.setScene(detalle);
				stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
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
	
	public void agregarCamion (ActionEvent e) throws InterruptedException, ExecutionException
	{
		try {
			Scene detalle = new Scene(FXMLLoader.load(getClass().getResource("AgregarCamiones.fxml")));
			detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(detalle);
			stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
			stage.setTitle("Transportes Olmedo : Agregar Camiones");
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
	
	public void editarCamion (ActionEvent e) throws InterruptedException, ExecutionException
    {
        camion c = tablaCamion.getSelectionModel().getSelectedItem(); //Cuenta del camion seleccionado

        if(c!=null)
        {
            try {
                FXMLLoader loader= new FXMLLoader(getClass().getResource("EditarCamion.fxml"));
                Parent root = loader.load();

                EditarCamionControlador ventana = (EditarCamionControlador)loader.getController();

                ventana.inicializarVariables(c.getPatente(), c.getMarca(), c.getIdDocumento());
                loader.setController(ventana);

                Scene detalle = new Scene(root);
                detalle.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                Stage stage = new Stage();

                stage.setScene(detalle);
                stage.setTitle("Transportes Olmedo : Editar Camión");
                stage.getIcons().add(new Image(new FileInputStream("img/transOlmedoLogo.png")));
                stage.showAndWait();

                cargarCamiones();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else
        {
            FUNCIONES.dialogo("Información", "No hay ningún camión seleccionado");
        }
    }
	
	public void borrarCamion (ActionEvent e) throws NumberFormatException, InterruptedException, ExecutionException, IOException
    {

        ObservableList<camion> camiones = tablaCamion.getSelectionModel().getSelectedItems();
        
        if(!camiones.isEmpty())
        {
        	Optional<ButtonType> opcion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea eliminar los camiones seleccionados?");
        	
        	if(opcion.get()==ButtonType.OK)
            {
        		for(camion c : camiones)
        		{
           		 	ConectorFirebase.bdd.collection("camiones").document(c.getIdDocumento()).delete();   
           	 	}
                
               cargarCamiones();
            }      	
        }
        else
        {
            FUNCIONES.dialogo("Información", "No hay ningún camión seleccionado");
        }
        
       
    }
    
			
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FXpat.setCellValueFactory(new PropertyValueFactory<>("patente"));
		FXmar.setCellValueFactory(new PropertyValueFactory<>("marca"));
		
		tablaCamion.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		try {
			cargarCamiones();
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
	
	}
}


