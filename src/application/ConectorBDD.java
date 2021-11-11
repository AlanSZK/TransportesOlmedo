package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBDD {
	
	public Connection con;
	
	public Connection conectar()
	{
		String nombreBDD="mydb";
		String nombreUsuario="root";
		String contrasena="";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/"+nombreBDD,nombreUsuario,contrasena);
			
		} catch ( SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return con;
		
	}
}
