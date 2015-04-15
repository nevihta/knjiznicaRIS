package ris.projekt.knjiznica.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;

import ris.projekt.knjiznica.baza.DataSource;
import ris.projekt.knjiznica.beans.Prijava;

public class PrijavaDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static PrijavaDAO pd;
	
	private PrijavaDAO(){
		
	}
	
	public static synchronized PrijavaDAO dobiInstanco(){
		if(pd==null)
			pd=new PrijavaDAO();
		return pd;
	}
	
	
	public boolean prijava(Prijava p) {
		boolean prijava=false;
		try{
			povezava =  DataSource.getInstance().getConnection();
			st = povezava.prepareStatement("select * from prijava where upIme=? and geslo=?");
			st.setString(1, p.getUpIme());
			st.setString(2, p.getGeslo());

			rs = st.executeQuery();
			
			if(rs.next())
				prijava=true;
		}
		catch(SQLException e){e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (PropertyVetoException e) {e.printStackTrace();}
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return prijava;
		
	}

	
	
}
