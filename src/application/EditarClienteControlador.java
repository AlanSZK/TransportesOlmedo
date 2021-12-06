package application;

import java.util.Optional;

import com.google.cloud.firestore.DocumentReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarClienteControlador {
	@FXML private TextField rut;
	@FXML private TextField nombre;
	@FXML private TextField contacto;
	@FXML private TextField direccion;
	@FXML private TextField comuna;
	@FXML private TextField region;
	String idDoc;
	
	
	public void confirmar (ActionEvent e)
	{
		Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");
		
		if (confirmacion.get() == ButtonType.OK)
		{
			
			TextField[] campos = {
				rut,
				nombre,
				contacto,
				direccion,
				comuna,
				region
			};
			
			if(FUNCIONES.camposVacios(campos))
			{
				FUNCIONES.dialogoAlerta("Error", "Existen campos vacíos");
			}
			
			else
			{
						
				DocumentReference ref = ConectorFirebase.bdd.collection("clientes").document(idDoc);
				
				ref.update("rut",rut.getText());
				ref.update("nombre",nombre.getText());
				ref.update("contacto",contacto.getText());
				ref.update("direccion",direccion.getText());
				ref.update("comuna",comuna.getText());
				ref.update("region",region.getText());
					
				
				cerrar(e);
							
			}																					
				
		}	
	}
	
	
	
	
	public void inicializarVariables(String r, String n, String c, String d, String com, String rn,String doc)
	{
		rut.setText(r);
		nombre.setText(n);
		contacto.setText(c);
		direccion.setText(d);
		comuna.setText(com);
		region.setText(rn);
		idDoc=doc;
	}

	
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	

}
