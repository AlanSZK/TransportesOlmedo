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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class LoginControlador {
	
	@FXML public TextField rut;
	@FXML public TextField contrasena;
	
	
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
							FUNCIONES.cambiarEscena(fxml,e,"Transportes Olmedo : Menú principal");		
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				}
				
				if(!cuentaEncontrada)
				{
					FUNCIONES.dialogoAlerta("Atención", "Usuario/rut o contraseña son incorrectos");
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

	
	
	/*
	//Verificar MySQL
	
	public void verificar (ActionEvent e)
	{
		
		String query = "SELECT cuenta.contrasena FROM administrador INNER JOIN "
				+ "cuenta ON administrador.id_cuenta=cuenta.id_cuenta "
				+ "WHERE administrador.rut=? ";
		
		ConectorBDD conector = new ConectorBDD();
		Connection con = conector.conectar();
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, rut.getText());
			
			rs = pst.executeQuery();
			
			if (rs.next() == true)
			{
				String pass = rs.getString(1);
				
				if (pass.equals(contrasena.getText()))
				{
					Scene fxml;
					try {
						
						fxml = new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml")));
						fxml.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						FUNCIONES.cambiarEscena(fxml,e,"Transportes Olmedo : Menú principal");		
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
				else
				{
					FUNCIONES.dialogoAlerta("Error", "Clave o rut incorrecto");
				}
					
			}
			else
			{
				FUNCIONES.dialogoAlerta("Error", "Clave o rut incorrecto");
			}
			
			
			
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}finally {
			try {
				con.close();
				pst.close();
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	*/	
}
		
	

