package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class LoginControlador {
	
	@FXML public TextField rut;
	@FXML public TextField contrasena;
	
	
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
		
		
		
		
		
		
	}
	
}
