package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginControlador implements Initializable {
	
	@FXML public TextField rut;
	@FXML public TextField contrasena;
	@FXML private ImageView logo;
	
	
	//Verificar Firebase
	public void verificar (ActionEvent e) throws Exception
	{
		
		
		
		TextField[] arr = {rut,contrasena};
		boolean cuentaEncontrada = false;

		
		
		if (!FUNCIONES.camposVacios(arr))
		{
			
			
			
			try {
				
				CollectionReference administradores = ConectorFirebase.bdd.collection("administradores");
				
				ApiFuture<QuerySnapshot> querySnapshot = administradores.get();
				
				for (DocumentSnapshot doc : querySnapshot.get().getDocuments())
				{
					
					
					if(doc.get("contrasena").equals(contrasena.getText()) && (doc.get("rut").equals(rut.getText())||doc.get("usuario").equals(rut.getText())))
					{
			
						cuentaEncontrada=true;
						Scene fxml;
						try {
							
							fxml = new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml")));
							fxml.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
							FUNCIONES.cambiarEscena(fxml,e,"Transportes Olmedo : Men? principal");		
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				}
				
				if(!cuentaEncontrada)
				{
					FUNCIONES.dialogoAlerta("Atenci?n", "Usuario/rut o contrase?a son incorrectos");
				}
				
				
				
			} catch (InterruptedException | ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		else
		{
			FUNCIONES.dialogoAlerta("Error", "Existen campos sin llenar");
		}

	
	
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			logo.setImage(new Image(new FileInputStream("img/logo.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
		
	

