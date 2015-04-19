package ris.projekt.knjiznica.baza;

import java.sql.*;
import com.mysql.jdbc.Driver; 

public class Povezava {
	private static Connection povezava = null;
	
	public static Connection getConnection(){
		
			try{
				DriverManager.registerDriver(new Driver()); 
		        String baza = "jdbc:mysql://localhost/knjiznica?useUnicode=true&characterEncoding=utf-8"; 
		        String user = "root"; 
		        String geslo = "GESLO"; 
		        povezava = DriverManager.getConnection(baza, user, geslo); 
		        
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return povezava;
		}
	
	

}
