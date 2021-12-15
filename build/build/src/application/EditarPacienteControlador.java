package application;

import java.util.Optional;

import com.google.cloud.firestore.DocumentReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarPacienteControlador {
	
	@FXML private TextField idCliente;
	@FXML private TextField nombreCliente;
	@FXML private TextField idPaciente;
	@FXML private TextField nombrePaciente;
	@FXML private TextField comuna;
	@FXML private TextField direccion;
	@FXML private TextField contacto;
	String idDoc;
	
	
	public void confirmar (ActionEvent e)
	{
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");
		
		if (confirmacion.get() == ButtonType.OK)
		{
			
			TextField[] campos = {
				idCliente,
				nombreCliente,
				idPaciente,
				nombrePaciente,
				contacto,
				direccion,
				comuna
			};
			
			if(FUNCIONES.camposVacios(campos))
			{
				FUNCIONES.dialogoAlerta("Error", "Existen campos vacíos");
			}
			
			else
			{
						
				DocumentReference ref = ConectorFirebase.bdd.collection("pacientes").document(idDoc);
				
				ref.update("idCliente",idCliente.getText());
				ref.update("nombreCliente",nombreCliente.getText());
				ref.update("idPaciente",idPaciente.getText());
				ref.update("nombrePaciente",nombrePaciente.getText());
				ref.update("direccion",direccion.getText());
				ref.update("comuna",comuna.getText());
				ref.update("contacto",contacto.getText());	
				
				
				cerrar(e);
							
			}																					
				
		}	
	}
	
	
	
	
	public void inicializarVariables(String idC, String nomC, String idP, String nomP, String com, String dir, String con,String doc)
	{
		idCliente.setText(idC);
		nombreCliente.setText(nomC);
		idPaciente.setText(idP);
		nombrePaciente.setText(nomP);
		comuna.setText(com);
		direccion.setText(dir);
		contacto.setText(con);
		idDoc=doc;
	}
	

	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	

}
