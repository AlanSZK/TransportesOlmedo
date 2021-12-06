package application;

import java.util.Optional;

import com.google.cloud.firestore.DocumentReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarAdministradorControlador {
	
	@FXML public TextField idUsuario=new TextField();
	@FXML private TextField nombreUsuario=new TextField();
	@FXML private TextField rutUsuario=new TextField();
	@FXML private TextField contrasenaUsuario=new TextField();
	@FXML private TextField nombreAdministrador=new TextField();
	@FXML private TextField telefonoAdministrador=new TextField();
	String idDocumento;
	
	public void confirmar (ActionEvent e)
	{
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");
		
		if (confirmacion.get() == ButtonType.OK)
		{
			
			TextField[] campos = {
				contrasenaUsuario,
				nombreAdministrador,
				telefonoAdministrador
			};
			
			if(FUNCIONES.camposVacios(campos))
			{
				FUNCIONES.dialogoAlerta("Error", "Existen campos vacíos");
			}
			
			else
			{
				
						
				DocumentReference ref = ConectorFirebase.bdd.collection("administradores").document(idDocumento);
				
				ref.update("contrasena",contrasenaUsuario.getText());
				ref.update("nombre",nombreAdministrador.getText());
				ref.update("telefono",telefonoAdministrador.getText());
				
				cerrar(e);
							
			}																					
				
		}	
	}
	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	
	public void inicializarVariables(int id, String usr, String rut, String con, String nom, String tel,String idDoc)
	{
		idUsuario.setText(String.valueOf(id));
		nombreUsuario.setText(usr);
		rutUsuario.setText(rut);
		contrasenaUsuario.setText(con);
		nombreAdministrador.setText(nom);
		telefonoAdministrador.setText(tel);
		idDocumento = idDoc;
		
	}
}
