package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Avtor;

public class AvtorDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static AvtorDAO ad;
	
	private AvtorDAO(){
		
	}
	
	public static synchronized AvtorDAO dobiInstanco()
	{
		if(ad==null)
			ad=new AvtorDAO();
		return ad;
	}
	
	public void dodajAvtorja(Avtor a)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into avtor (ime, priimek) values (?,?)");
			st.setString(1, a.getIme());
			st.setString(2, a.getPriimek());
			
			st.executeUpdate();
  
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public void urediAvtorja(Avtor a) {
		try{
			povezava = Povezava.getConnection();
			st = povezava.prepareStatement("update avtor set ime=?, priimek=? where ID_avtorja=?");
            
			st.setString(1, a.getIme());
            st.setString(2, a.getPriimek());
            st.setInt(3, a.getId());
            st.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
	}
	
	public ArrayList<Avtor> pridobiVseAvtorje() 
	{
		ArrayList<Avtor> avtorji = new ArrayList<Avtor>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from avtor");

			rs = st.executeQuery();
			
			Avtor a;
			while (rs.next())
			{
				a=new Avtor(rs.getInt("ID_avtorja"), rs.getString("ime"), rs.getString("priimek"));
				avtorji.add(a);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return avtorji;	
	} 
	
	public Avtor pridobiAvtorja(int id)
	{
		Avtor avtor = new Avtor();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from avtor where ID_avtorja=?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next())
			{
				avtor.setId(id);
				avtor.setIme(rs.getString("ime"));
				avtor.setPriimek(rs.getString("priimek"));				
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return avtor;
	}
	
	public boolean izbrisi(int id){
		boolean izbris=false;
		try{
			povezava = Povezava.getConnection();
			//preveri èe avtor še ni vezan na nobeno gradivo
			st=povezava.prepareStatement("select count(*) as st from gradivo_avtor ga where ga.tk_id_avtorja=?"); 
			st.setInt(1, id);
			rs=st.executeQuery();
			if(rs.next()){
				if(0==rs.getInt("st")){
					izbris=true;
				}
			}
			rs.close();
			st.close();
			
			if (izbris==true){
				st=povezava.prepareStatement("delete from avtor where ID_avtorja=?"); 
				st.setInt(1, id);
		           st.executeUpdate();
		   
			}
		}	
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return izbris;
	}

	

	
}
	