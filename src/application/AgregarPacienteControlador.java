package application;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import com.google.cloud.firestore.DocumentReference;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarPacienteControlador {
	
	@FXML private TextField idCliente;
	@FXML private TextField nombreCliente;
	@FXML private TextField idPaciente;
	@FXML private TextField nombrePaciente;
	@FXML private TextField comuna;
	@FXML private TextField direccion;
	@FXML private TextField contacto;
	
	
	public void confirmar (ActionEvent e)
	{
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");
		
		if(confirmacion.get() == ButtonType.OK)
		{
			TextField[] campos = {idCliente,idPaciente,nombreCliente,nombrePaciente,contacto,direccion,comuna};
			
			if (!FUNCIONES.camposVacios(campos))
			{
				Map<String, Object> datos = new HashMap<>();
				
				datos.put("idCliente", idCliente.getText());
				datos.put("nombreCliente", nombreCliente.getText());
				datos.put("idPaciente", idPaciente.getText());
				datos.put("nombrePaciente", nombrePaciente.getText());
				datos.put("direccionPaciente", direccion.getText());
				datos.put("comuna", comuna.getText());
				datos.put("contacto", contacto.getText());
				
				
				
				
				DocumentReference ref = ConectorFirebase.bdd.collection("pacientes").document(idPaciente.getText());
				ref.set(datos);
				
				cerrar(e);
				
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
