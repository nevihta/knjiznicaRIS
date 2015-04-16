package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Naslov;


public class NaslovDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static NaslovDAO nd;
	
	private NaslovDAO(){
		
	}
	
	public static synchronized NaslovDAO dobiInstanco()
	{
		if(nd==null)
			nd=new NaslovDAO();
		return nd;
	}
	
	public Naslov pridobiNaslov(int id)
	{
		Naslov naslov=new Naslov();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from naslov where ID_naslova=?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next())
			{
					naslov.setId(id);
					naslov.setUlica(rs.getString("ulica"));
					naslov.setHisnaSt(rs.getString("hisnaSt"));
					naslov.setMesto(rs.getString("mesto"));
					naslov.setPostnaSt(rs.getInt("postnaSt"));
					naslov.setDrzava(rs.getString("drzava"));
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return naslov;
	}
	
	public int dodajNaslov(Naslov n)
	{
		int idNaslova=-1;
		
		
		try {
			//prveri èe že obstaja
			povezava =  Povezava.getConnection();
			st=povezava.prepareStatement("select ID_naslova from naslov where ulica=? and hisnaSt=? and postnaSt=? and mesto=? and drzava=?");
			st.setString(1, n.getUlica());
			st.setString(2, n.getHisnaSt());
			st.setInt(3, n.getPostnaSt());
			st.setString(4, n.getMesto());
			st.setString(5, n.getDrzava());
		       
		    rs = st.executeQuery();
		    if (rs.next())
		    {
		    	idNaslova=rs.getInt("ID_naslova");
		    }
		    
		    rs.close();
		    st.close();
			
			if(idNaslova==-1){
				//vstavi in pridobi id
			
				st = povezava.prepareStatement("insert into oseba (ulica, hisnaSt, postnaSt, mesto, drzava) values (?, ?, ?, ?, ?)");
				st.setString(1, n.getUlica());
				st.setString(2, n.getHisnaSt());
				st.setInt(3, n.getPostnaSt());
				st.setString(4, n.getMesto());
				st.setString(5, n.getDrzava());
				
				
				st.executeUpdate();
		       
				st.close();
			
		
				st=povezava.prepareStatement("select ID_naslova from naslov where ulica=? and hisnaSt=? and postnaSt=? and mesto=? and drzava=?");
				st.setString(1, n.getUlica());
				st.setString(2, n.getHisnaSt());
				st.setInt(3, n.getPostnaSt());
				st.setString(4, n.getMesto());
				st.setString(5, n.getDrzava());
			       
			    rs = st.executeQuery();
			    if (rs.next())
			    {
			    	idNaslova=rs.getInt("ID_naslova");
			    }
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return idNaslova;
	}
	

	
	public int urediNaslov(Naslov n)
	{
		int idNaslova=-1;

		try {
			//preveri èe že obstaja
			povezava =  Povezava.getConnection();
			st=povezava.prepareStatement("select ID_naslova from naslov where ulica=? and hisnaSt=? and postnaSt=? and mesto=? and drzava=?");
			st.setString(1, n.getUlica());
			st.setString(2, n.getHisnaSt());
			st.setInt(3, n.getPostnaSt());
			st.setString(4, n.getMesto());
			st.setString(5, n.getDrzava());
		       
		    rs = st.executeQuery();
		    if (rs.next())
		    {
		    	idNaslova=rs.getInt("ID_naslova");
		    }
		    
		    rs.close();
		    st.close();
			
			if(idNaslova==-1){
				//èe še ne obstaja spremenimo ali naredimo novega? zaradi možnosti, da jih veè živi na istem naslovu....
			
				st = povezava.prepareStatement("update naslov set ulica=?, hisnaSt=?, postnaSt=?, mesto=?, drzava=? where ID_naslova=?");
				st.setString(1, n.getUlica());
				st.setString(2, n.getHisnaSt());
				st.setInt(3, n.getPostnaSt());
				st.setString(4, n.getMesto());
				st.setString(5, n.getDrzava());
				st.setInt(6, n.getId());
				
				st.executeUpdate();
		       
				st.close();
				idNaslova=n.getId();
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return idNaslova;
	}

	public void izbrisiNaslov()
	{
		//smiselno?
	}
}

