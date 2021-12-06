package application;


import java.util.HashMap;
import java.util.Optional;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;

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
				
				String uuid = java.util.UUID.randomUUID().toString();
				
				
				DocumentReference ref = ConectorFirebase.bdd.collection("choferes").document(uuid);
				ApiFuture<WriteResult> resultado = ref.set(datos);
				
				
				
				cerrar(e);
			}
		
		}
		
	
	
	}
	
	
	//MySQL
	/*
	@SuppressWarnings("resource")
	public void confirmar (ActionEvent e)
	{
		ConectorBDD conector = new ConectorBDD();
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
				Connection con = conector.conectar();
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				
				
				String query1 = "INSERT INTO cuenta (nombreUsuario,contrasena,telefono) VALUES (?,?,?)";		
				String query2 = "SELECT cuenta.id_cuenta FROM cuenta WHERE nombreUsuario=?";
				String query3 = "INSERT INTO chofer (rut,id_cuenta,nombre) VALUES (?,?,?)";
				
				try {
					//QUERY 1
					pst = con.prepareStatement(query1);
					
					pst.setString(1, nombreUsuario.getText());
					pst.setString(2, contrasenaUsuario.getText());
					pst.setString(3, telefonoChofer.getText());
					
					pst.executeUpdate();
					
			
					
					//QUERY 2
					pst = con.prepareStatement(query2);
					
					int idCuenta = 0;
					
					pst.setString(1, nombreUsuario.getText());
					
					rs = pst.executeQuery();
					
					while(rs.next())
					{
						idCuenta = rs.getInt("id_cuenta");
					}
				
					
					//QUERY 3
					pst=con.prepareStatement(query3);
					
					pst.setString(1, rutUsuario.getText());
					pst.setInt(2, idCuenta);
					pst.setString(3, nombreChofer.getText());
					
					pst.executeUpdate();
					pst.close();
					
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					try {
						con.close();
						pst.close();
						rs.close();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();		}
					
				}			
				cerrar(e);
			
			}
			
		}
		
	}
	*/
	public void cerrar (ActionEvent e)
	{
		Stage stage = (Stage)((Node)e.getTarget()).getScene().getWindow();
		stage.close();
		
	}
	
}
