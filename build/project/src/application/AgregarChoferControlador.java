package application;


import java.util.HashMap;
import java.util.Optional;

import com.google.cloud.firestore.DocumentReference;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarChoferControlador {
	
	@FXML private TextField nombreUsuario=new TextField();
	@FXML private TextField rutUsuario=new TextField();
	@FXML private TextField contrasenaUsuario=new TextField();
	@FXML private TextField nombreChofer=new TextField();
	@FXML private TextField telefonoChofer=new TextField();
	

	//Firebase
	public void confirmar(ActionEvent e)
	{
		
		
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que quiere añadir al chofer?");
		
		if (confirmacion.get() == ButtonType.OK)
		{
			
			TextField[] campos = {
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
			
				HashMap<String, Object> datos = new HashMap<>();
				datos.put("idCuenta", (CuentasControlador.contadorChoferes+CuentasControlador.contadorAdmins+1));
				datos.put("usuario", nombreUsuario.getText());
				datos.put("rut", rutUsuario.getText());
				datos.put("contrasena", contrasenaUsuario.getText());
				datos.put("nombre", nombreChofer.getText());
				datos.put("telefono", telefonoChofer.getText());	
				
				
				DocumentReference ref = ConectorFirebase.bdd.collection("choferes").document(rutUsuario.getText());
				ref.set(datos);
				
				
				
				cerrar(e);
			}
		
		}
		
	
	
	}
	
	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	
}
