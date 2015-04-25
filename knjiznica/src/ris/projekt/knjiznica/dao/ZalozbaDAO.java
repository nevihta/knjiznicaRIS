package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Zalozba;

public class ZalozbaDAO {


	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static ZalozbaDAO zd;
	
	private ZalozbaDAO(){
		
	}
	
	public static synchronized ZalozbaDAO dobiInstanco()
	{
		if(zd==null)
			zd=new ZalozbaDAO();
		return zd;
	}
	
	public void dodajZalozbo(Zalozba z)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into podrocje (naziv, mesto) values (?, ?)");
			st.setString(1, z.getNaziv());
			st.setString(2, z.getMesto());
			
			st.executeUpdate();
		}
		
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public boolean izbrisiZalozbo(int id)
	{
		boolean izbris=false;
		try{
			povezava =  Povezava.getConnection();

			//preveri, èe še ni vezano na katero gradivo
			st=povezava.prepareStatement("select count(*) as st from gradivo where tk_id_zalozbe=?");
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
				st = povezava.prepareStatement("delete from zalozba where ID_zalozbe=?");
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
	
	public void spremeniZalozbo(Zalozba z)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update zalozba set naziv=?, mesto=? where ID_zalozbe=?");
			st.setString(1, z.getNaziv());
			st.setString(2, z.getMesto());
			st.setInt(3, z.getId());
				
			st.executeUpdate();
		}
			
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public Zalozba pridobiZalozbo(int id)
	{
		Zalozba z=new Zalozba();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from zalozba where ID_zalozbe=?");
			st.setInt(1, id);
			rs=st.executeQuery();	
			
			if(rs.next())
			{
				z.setId(rs.getInt("ID_zalozbe"));
				z.setNaziv(rs.getString("naziv"));
				z.setMesto(rs.getString("mesto"));
			}
		}
		
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return z;
	}
	
	public void pridobiVseZalozbe()
	{
		ArrayList<Zalozba> zalozbe=new ArrayList<Zalozba>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from zalozba");
			rs=st.executeQuery();
				
			while(rs.next()){
				zalozbe.add(new Zalozba(rs.getInt("ID_zalozbe"), rs.getString("mesto"),  rs.getString("naziv")));
			}
		}
			
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
}
