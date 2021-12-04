package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetalleGuiasControlador {
	@FXML private Label fechaEntregaLbl;
	@FXML private Label horaEntregaLbl;
	@FXML private Label nGuiaLbl;
	@FXML private Label camionLbl;
	@FXML private Label choferLbl;
	@FXML private Label direccionLbl;
	@FXML private Label comunaLbl;
	@FXML private Label regionLbl;
	@FXML private Label fechaLbl;
	@FXML private Label rutClienteLbl;
	@FXML private Label nombreClienteLbl;
	@FXML private Label contactoClienteLbl;
	
	@FXML private ImageView imagenConfirmacion = new ImageView();
	
	
	public void volver (ActionEvent e)
	{
		Stage win = (Stage)((Node)e.getTarget()).getScene().getWindow();
		win.close();
	}
	
	public void inicializarVariables (String fecha, String hora, String url, String id, String camion, String chofer, String direccion, String comuna, String region, String fechaGuia, String rut, String cliente, String contacto)
	{
		fechaEntregaLbl.setText(fechaEntregaLbl.getText()+fecha);
		horaEntregaLbl.setText(horaEntregaLbl.getText()+hora);
		imagenConfirmacion.setImage(new Image(url));
		nGuiaLbl.setText(nGuiaLbl.getText()+id);
		camionLbl.setText(camionLbl.getText()+camion);
		choferLbl.setText(choferLbl.getText()+chofer);
		direccionLbl.setText(direccionLbl.getText()+direccion);
		comunaLbl.setText(comunaLbl.getText()+comuna);
		regionLbl.setText(regionLbl.getText()+region);
		fechaLbl.setText(fechaLbl.getText()+fechaGuia);
		rutClienteLbl.setText(rutClienteLbl.getText()+rut);
		nombreClienteLbl.setText(nombreClienteLbl.getText()+cliente);
		contactoClienteLbl.setText(contactoClienteLbl.getText()+contacto);
			
	}
	
	
}
