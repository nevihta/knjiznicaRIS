package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Storitev;

public class StoritevDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static StoritevDAO sd;
	
	private StoritevDAO(){
		
	}
	
	public static synchronized StoritevDAO dobiInstanco()
	{
		if(sd==null)
			sd=new StoritevDAO();
		return sd;
	}
	
	public void izposodi(Storitev s)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into storitev (datumIzposoje, datumVracila, rokVracila, zePodaljsano, tk_id_clana, tk_id_gradiva, tk_id_knjiznicarja) values (?,?,?,?,?,?,?)");
			st.setDate(1, (Date) s.getDatumIzposoje());
			st.setDate(2, (Date) s.getDatumVrnitve());
			st.setDate(3, (Date) s.getRokVrnitve());
			st.setBoolean(4, s.isZePodaljsano());
			st.setInt(5, s.getTk_id_clana());
			st.setInt(6, s.getTk_id_gradiva());
			st.setInt(7, s.getTk_id_knjiznicarja());

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	//ali bi šlo preko tk gradiva in osebe?
	public void vrni(Storitev s)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update storitev set datumVracila=? where ID_storitve=?)");
			st.setDate(1, (Date) s.getDatumVrnitve());
			st.setInt(2, s.getId());

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	//preveri se v servletu?
	public void podaljsaj(Storitev s)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update storitev set rokVracila=? where ID_storitve=?)");
			st.setDate(1, (Date) s.getRokVrnitve());
			st.setInt(2, s.getId());

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public ArrayList<Storitev> pridobiVseIzposojeOsebe(int idOsebe)
	{
		ArrayList<Storitev> izposoje = new ArrayList<Storitev>();
		Storitev s;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from storitev where tk_id_clana=?)");
			st.setInt(1, idOsebe);
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), idOsebe, rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				izposoje.add(s);
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return izposoje;
	}

	public ArrayList<Storitev> pridobiVseIzposojeGradiva(int idGradiva)
	{
		ArrayList<Storitev> izposoje = new ArrayList<Storitev>();
		Storitev s;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from storitev where tk_id_gradiva=?)"); //odrer by datum izposoje mogoèe? :D
			st.setInt(1, idGradiva);
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), idGradiva, rs.getInt("tk_id_knjiznicarja"));
				izposoje.add(s);
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return izposoje;
	}
}
