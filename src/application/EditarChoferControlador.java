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

public class EditarChoferControlador {
	
	@FXML public TextField idUsuario=new TextField();
	@FXML private TextField nombreUsuario=new TextField();
	@FXML private TextField rutUsuario=new TextField();
	@FXML private TextField contrasenaUsuario=new TextField();
	@FXML private TextField nombreChofer=new TextField();
	@FXML private TextField telefonoChofer=new TextField();
	
	
	
	@SuppressWarnings("resource")
	public void confirmar (ActionEvent e)
	{
		ConectorBDD conector = new ConectorBDD();
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");
		
		if (confirmacion.get() == ButtonType.OK)
		{
			
			TextField[] campos = {
				idUsuario,
				nombreUsuario,
				rutUsuario,
				contrasenaUsuario,
				nombreChofer,
				telefonoChofer
			};
			
			if(FUNCIONES.camposVacios(campos))
			{
				FUNCIONES.dialogoAlerta("Error", "Existen campos vacíos");
			}
			
			else
			{
				Connection con = conector.conectar();
				PreparedStatement pst = null;
				
				
				
				String query1 = "UPDATE cuenta SET nombreUsuario=?,contrasena=?,telefono=? "
						+ "WHERE id_cuenta=?";		

				String query2 = "UPDATE chofer SET nombre=? WHERE rut=?";
				
				
				try {
		
					pst = con.prepareStatement(query1);
					
					pst.setString(1, nombreUsuario.getText());
					pst.setString(2, contrasenaUsuario.getText());
					pst.setString(3, telefonoChofer.getText());
					pst.setInt(4, Integer.valueOf(idUsuario.getText()));
					
					pst.executeUpdate();
					
			
					pst = con.prepareStatement(query2);
					
					pst.setString(1, nombreChofer.getText());
					pst.setString(2, rutUsuario.getText());
					
					pst.executeUpdate();
				
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					try {
						con.close();
						pst.close();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();		}
					
				}			
			}
			cerrar(e);
		}
	}
	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}

	public void inicializarVariables(int id, String usr, String rut, String con, String nom, String tel)
	{
		idUsuario.setText(String.valueOf(id));
		nombreUsuario.setText(usr);
		rutUsuario.setText(rut);
		contrasenaUsuario.setText(con);
		nombreChofer.setText(nom);
		telefonoChofer.setText(tel);
		
	}
	

}
