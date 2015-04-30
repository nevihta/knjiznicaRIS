package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Podrocje;

public class PodrocjeDAO {


	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static PodrocjeDAO pd;
	
	private PodrocjeDAO(){
		
	}
	
	public static synchronized PodrocjeDAO dobiInstanco()
	{
		if(pd==null)
			pd=new PodrocjeDAO();
		return pd;
	}
	
	public int dodajPodrocje(String podrocje)
	{
		int idPodrocja=-1;
		try{
			povezava =  Povezava.getConnection();

			//preveri, èe že obstaja
			st=povezava.prepareStatement("select ID_podrocja from podrocje where naziv=?");
			st.setString(1, podrocje);
			rs= st.executeQuery();
			
			if(rs.next())
			{
				idPodrocja=rs.getInt("ID_podrocja");
			}
			
			rs.close();
			st.close();
			
			if(idPodrocja==-1)
			{
				st = povezava.prepareStatement("insert into podrocje (naziv) values (?)");
				st.setString(1, podrocje);
				
				st.executeUpdate();
				
				st.close();
				
				//pridobi id
				st=povezava.prepareStatement("select ID_podrocja from podrocje where naziv=?");
				st.setString(1, podrocje);
				rs= st.executeQuery();
				
				if(rs.next())
				{
					idPodrocja=rs.getInt("ID_podrocja");
				}
				
				rs.close();
				st.close();
			}
		}
		
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return idPodrocja;
	}
	
	public boolean izbrisiPodrocje(int id)
	{
		boolean izbris=false;
		try{
			povezava =  Povezava.getConnection();

			//preveri, èe še ni vezano na katero gradivo
			st=povezava.prepareStatement("select count(*) as st from gradivo where tk_id_podrocja=?");
			st.setInt(1, id);
			rs=st.executeQuery();
			if(rs.next()){
				if(0==rs.getInt("st")){
					izbris=true;
				}
			}
			
			rs.close();
			st.close();
			
			if(izbris==true)
			{
				st = povezava.prepareStatement("delete from podrocje where ID_podrocja=?");
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
	
	public void spremeniPodrocje(Podrocje p)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update podrocje set naziv=? where ID_podrocja=?");
			st.setString(1, p.getNaziv());
			st.setInt(2, p.getId());
				
			st.executeUpdate();
		}
			
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public Podrocje pridobiPodrocje(int id)
	{
		Podrocje p=new Podrocje();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from podrocje where ID_podrocja=?");
			st.setInt(1, id);
			rs=st.executeQuery();
			if (rs.next()){
				p.setId(rs.getInt("ID_podrocja"));
				p.setNaziv(rs.getString("naziv"));
			}
		}
		
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return p;
	}
	
	public ArrayList<Podrocje> pridobiVsaPodrocja()
	{
		ArrayList<Podrocje> podrocja=new ArrayList<Podrocje>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from podrocje");
			rs=st.executeQuery();
				
			while(rs.next()){
				podrocja.add(new Podrocje(rs.getInt("ID_podrocja"), rs.getString("naziv")));
			}
		}
			
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	
		return podrocja;

	}
	
}
