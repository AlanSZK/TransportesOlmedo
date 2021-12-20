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

public class AgregarCamionesControlador {

	@FXML private TextField patente;
	@FXML private TextField marca;
	
	
	public void confirmar(ActionEvent e)
    {

        Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que quiere añadir este camión?");
        if (confirmacion.get() == ButtonType.OK)
        {
            TextField[] campos = {
                patente,
                marca
            };

            if(FUNCIONES.camposVacios(campos))
            {
                FUNCIONES.dialogoAlerta("Error", "Existen campos vacíos");
            }
            else
            {
                HashMap<String, Object> datos = new HashMap<>();
                datos.put("patente", patente.getText());
                datos.put("marca", marca.getText());

                //String uuid = java.util.UUID.randomUUID().toString();

                DocumentReference ref = ConectorFirebase.bdd.collection("camiones").document(patente.getText());
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


