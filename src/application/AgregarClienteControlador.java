package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarClienteControlador {
	
	@FXML private TextField rut;
	@FXML private TextField nombre;
	@FXML private TextField contacto;
	@FXML private TextField direccion;
	@FXML private TextField comuna;
	@FXML private TextField region;
	
	ConectorBDD conector = new ConectorBDD();
	
	@SuppressWarnings("resource")
	public void confirmar (ActionEvent e)
	{
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");
		
		if(confirmacion.get() == ButtonType.OK)
		{
			TextField[] campos = {rut,nombre,contacto,direccion,comuna,region};
			
			if (!FUNCIONES.camposVacios(campos))
			{
				Connection con = conector.conectar();
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				int id=0;
				
				String query1 = "INSERT INTO ubicacion(direccion,comuna,region) VALUES (?,?,?)";
				String query2 = "SELECT idUbicacion FROM ubicacion ORDER BY idUbicacion DESC LIMIT 1";
				String query3 = "INSERT INTO CLIENTE (idUbicacion,rut,nombre,contacto) VALUES (?,?,?,?)";
				
				
				
				try {
					pst = con.prepareStatement(query1);
					pst.setString(1, direccion.getText());
					pst.setString(2, comuna.getText());
					pst.setString(3, region.getText());
					
					pst.executeUpdate();
					
					pst = con.prepareStatement(query2);
					rs = pst.executeQuery();
					
					
					while(rs.next())
					{
						id = rs.getInt("idUbicacion");
					}
					
					pst = con.prepareStatement(query3);
					pst.setInt(1, id);
					pst.setString(2, rut.getText());
					pst.setString(3, nombre.getText());
					pst.setString(4, contacto.getText());
					
					pst.executeUpdate();
					
					cerrar(e);
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
			else
			{
				FUNCIONES.dialogoAlerta("Error", "Hay campos sin llenar");
			}
			
		}
		
		
	}
	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	
	
	
}
