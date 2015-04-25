package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing.Validation;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Gradivo;
import ris.projekt.knjiznica.beans.Jezik;
import ris.projekt.knjiznica.beans.Oseba;
import ris.projekt.knjiznica.beans.TipOsebe;

public class GradivoDAO {


	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static GradivoDAO gd;
	
	private GradivoDAO(){
		
	}
	
	public static synchronized GradivoDAO dobiInstanco()
	{
		if(gd==null)
			gd=new GradivoDAO();
		return gd;
	}
	
	public int dodajGradivo(Gradivo g){
		
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into gradivo (naslov, originalNaslov, jezik, letoIzida, ISBN, opis, tk_id_podrocja, tk_id_vrste, tk_id_zalozbe) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, g.getNaslov());
			st.setString(2, g.getOriginalNaslov());
			st.setString(3, g.getJezik().toString());
			st.setInt(4, g.getLetoIzida());
			st.setString(5, g.getISBN());
			st.setString(6, g.getOpis());
			st.setInt(7, g.getTk_id_podrocja());
			st.setInt(8, g.getTk_id_vrste());
			st.setInt(9, g.getTk_id_zalozbe());
					
			st.executeUpdate();
           
			st.close();
			
            st=povezava.prepareStatement("select ID_gradiva from gradivo where naslov=?, originalNaslov=?, jezik=?, letoIzida=?, ISBN=?, opis=?, tk_id_podrocja=?, tk_id_vrste=?, tk_id_zalozbe=?");
        	st.setString(1, g.getNaslov());
			st.setString(2, g.getOriginalNaslov());
			st.setString(3, g.getJezik().toString());
			st.setInt(4, g.getLetoIzida());
			st.setString(5, g.getISBN());
			st.setString(6, g.getOpis());
			st.setInt(7, g.getTk_id_podrocja());
			st.setInt(8, g.getTk_id_vrste());
			st.setInt(9, g.getTk_id_zalozbe());
           
            rs = st.executeQuery();
            if (rs.next())
            {
            	g.setId(rs.getInt("ID_gradiva"));
            }
			
			
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return g.getId();	
	}
	
	public Gradivo urediGradivo(Gradivo g)
	{
		try
		{
	        st=povezava.prepareStatement("update gradivo set naslov=?, originalNaslov=?, jezik=?, letoIzida=?, ISBN=?, opis=?, tk_id_podrocja=?, tk_id_vrste=?, tk_id_zalozbe=? where ID_gradiva=?");
	    	st.setString(1, g.getNaslov());
			st.setString(2, g.getOriginalNaslov());
			st.setString(3, g.getJezik().toString());
			st.setInt(4, g.getLetoIzida());
			st.setString(5, g.getISBN());
			st.setString(6, g.getOpis());
			st.setInt(7, g.getTk_id_podrocja());
			st.setInt(8, g.getTk_id_vrste());
			st.setInt(9, g.getTk_id_zalozbe());
			st.setInt(10, g.getId());
	        
			st.executeUpdate();
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return g;
	}
	
	public boolean izbrisiGradivo(int id)
	{
		boolean izbris=false;
		try
		{
	        //preveri, èe je gradivo že bilo izposojeno
			st=povezava.prepareStatement("select count(*) as st from gradivo_storitev where tk_id_gradiva=?");
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
				st=povezava.prepareStatement("delete from gradivo where ID_gradiva=?");
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
	
	public Gradivo pridobiGradivo(int id)
	{
		Gradivo g=new Gradivo();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from gradivo where ID_gradiva=?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next())
			{
					g.setId(id);
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); //problem, èe da jezik pod drugo pa ga ni med enumi... preverjanje z if-else prej kak shraniš?
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setTk_id_podrocja(rs.getInt("tk_id_podrocja"));
					g.setTk_id_vrste(rs.getInt("tk_id_vrste"));
					g.setTk_id_zalozbe(rs.getInt("tk_id_zalozbe"));

			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return g;
	}
	
	public ArrayList<Gradivo> pridobiVsaGradiva(){
		ArrayList<Gradivo> gradiva=new ArrayList<Gradivo>();
		Gradivo g;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from gradivo");
			
			rs = st.executeQuery();
			
			while (rs.next())
			{
					g=new Gradivo();
					g.setId(rs.getInt("ID_gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); //problem, èe da jezik pod drugo pa ga ni med enumi... preverjanje z if-else prej kak shraniš?
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setTk_id_podrocja(rs.getInt("tk_id_podrocja"));
					g.setTk_id_vrste(rs.getInt("tk_id_vrste"));
					g.setTk_id_zalozbe(rs.getInt("tk_id_zalozbe"));
					gradiva.add(g);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return gradiva;
	}
	
	//èe je izposojeno ali prosto
	public ArrayList<Gradivo> pridobiGradivaGledeNaStanje(String stanje){
		
		ArrayList<Gradivo> gradiva=new ArrayList<Gradivo>();
		Gradivo g;
		java.util.Date danasnjiDatum=new java.util.Date();

		try{
			povezava =  Povezava.getConnection();
			
			if(stanje.equals("izposojeno")){
				st = povezava.prepareStatement("select g.* from gradivo g, gradivo_storitev gs, storitev s where gs.tk_id_gradiva=g.ID_gradiva and gs.tk_id_storitve=s.ID_storitve and s.datumVracila>?");
			}
			else{ //èe je prosto
				//bo potrebno preverit èe logika štima ... ko pridemo tak daleè :D
				st = povezava.prepareStatement("select * from gradivo g, gradivo_storitev gs, storitev s where gs.tk_id_gradiva=g.ID_gradiva and gs.tk_id_storitve=s.ID_storitve and s.datumVracila<?");
			}
			st.setDate(1, (Date) danasnjiDatum);
			rs = st.executeQuery();
			
			if (rs.next())
			{
					g=new Gradivo();
					g.setId(rs.getInt("ID_Gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); //problem, èe da jezik pod drugo pa ga ni med enumi... preverjanje z if-else prej kak shraniš?
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setTk_id_podrocja(rs.getInt("tk_id_podrocja"));
					g.setTk_id_vrste(rs.getInt("tk_id_vrste"));
					g.setTk_id_zalozbe(rs.getInt("tk_id_zalozbe"));
					gradiva.add(g);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return gradiva;
	}
	
}
