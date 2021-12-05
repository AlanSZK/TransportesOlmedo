package application;

import java.util.Optional;

import com.google.cloud.firestore.DocumentReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;

public class EditarCamionControlador {

	@FXML private TextField patente;
	@FXML private TextField marca;
	public String idDocumento;
	
	public void confirmar (ActionEvent e)
    {
        Optional<ButtonType> confirmacion = FUNCIONES.dialogoConfirmacion("¿Está seguro que desea continuar?");

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
                DocumentReference ref = ConectorFirebase.bdd.collection("camiones").document(idDocumento);

                ref.update("patente",patente.getText());
                ref.update("marca",marca.getText());

                cerrar(e);
            }
        }
    }

    public void cerrar (ActionEvent e)
    {
        Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
        stage.close();

    }

    public void inicializarVariables(String pat, String mar, String idDoc)
    {
        patente.setText(pat);
        marca.setText(mar);
        idDocumento = idDoc;
    }
	
}
