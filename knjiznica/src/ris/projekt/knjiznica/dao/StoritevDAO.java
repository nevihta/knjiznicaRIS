package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Gradivo;
import ris.projekt.knjiznica.beans.GradivoZaIzpis;
import ris.projekt.knjiznica.beans.Jezik;
import ris.projekt.knjiznica.beans.Oseba;
import ris.projekt.knjiznica.beans.Storitev;
import ris.projekt.knjiznica.beans.StoritevZaIzpis;

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
	
	public void izposodi(List<Storitev> storitve)
	{
		Date datum;
		Storitev s;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into storitev (datumIzposoje, datumVracila, rokVracila, zePodaljsano, tk_id_clana, tk_id_gradiva, tk_id_knjiznicarja) values (?,?,?,?,?,?,?)");
			for(int i=0; i<storitve.size(); i++)
			{
			s=storitve.get(i);
			datum=new Date(s.getDatumIzposoje().getTime());
			st.setDate(1, datum);
			st.setDate(2, null);
			datum=new Date(s.getRokVrnitve().getTime());
			st.setDate(3, datum);
			st.setBoolean(4, false);
			st.setInt(5, s.getTk_id_clana());
			st.setInt(6, s.getTk_id_gradiva());
			st.setInt(7, s.getTk_id_knjiznicarja());
			st.executeUpdate();
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public void vrni(List<Storitev> storitve)
	{
		Date datum;
		Storitev s;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update storitev set datumVracila=? where ID_storitve=?");
			
			for(int i=0; i<storitve.size(); i++)
			{
			s=storitve.get(i);
			datum=new Date(s.getDatumVrnitve().getTime());
			st.setDate(1, datum);
			st.setInt(2, s.getId());
			st.executeUpdate();
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public void podaljsaj(List<Storitev> storitve)
	{
		Date datum;
		Storitev s;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update storitev set rokVracila=?, zePodaljsano=? where ID_storitve=?");
			for(int i=0; i<storitve.size();i++)
			{
				s=storitve.get(i);
				datum=new Date(s.getRokVrnitve().getTime());
				st.setDate(1, datum);
				st.setBoolean(2, true);
				st.setInt(3, s.getId());
				st.executeUpdate();
			}		

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public ArrayList<StoritevZaIzpis> pridobiVseIzposojeOsebe(int idOsebe)
	{
		ArrayList<StoritevZaIzpis> izposoje = new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Storitev s;
		Gradivo g;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select s.*, g.naslov from storitev s, gradivo g where tk_id_clana=? and s.tk_id_gradiva=g.ID_gradiva and s.datumVracila is not null");
			st.setInt(1, idOsebe);
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), idOsebe, rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo();
				g.setNaslov(rs.getString("naslov"));
				g.setId(s.getTk_id_gradiva());
				szi=new StoritevZaIzpis(s, g);
				izposoje.add(szi);
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return izposoje;
	}

	public ArrayList<StoritevZaIzpis> pridobiVseIzposojeGradiva(int idGradiva)
	{
		ArrayList<StoritevZaIzpis> izposoje = new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Storitev s;
		Oseba o;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select s.*, o.ime, o.priimek from storitev s, oseba o where tk_id_gradiva=? and o.ID_osebe=s.tk_id_clana and s.datumVracila is not null");
			st.setInt(1, idGradiva);
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), idGradiva, rs.getInt("tk_id_knjiznicarja"));
				o=new Oseba();
				o.setIme(rs.getString("ime"));
				o.setPriimek(rs.getString("priimek"));
				o.setId(s.getTk_id_clana());
				szi=new StoritevZaIzpis(s,o);
				izposoje.add(szi);
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return izposoje;
	}

	public ArrayList<StoritevZaIzpis> pridobiVseIzposojeOsebeNepodaljsane(int idOsebe) {
		ArrayList<StoritevZaIzpis> storitve= new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Storitev s;
		Gradivo g;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from storitev s, gradivo g where g.ID_gradiva=s.tk_id_gradiva and zePodaljsano=? and datumVracila is null and tk_id_clana=?"); //odrer by datum izposoje mogoèe? :D
			st.setBoolean(1, false);
			st.setInt(2, idOsebe);
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), idOsebe, rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				szi=new StoritevZaIzpis(s,g);
				storitve.add(szi);
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return storitve;
	}
	
	public ArrayList<StoritevZaIzpis> pridobiVseAktualneIzposoje(){
		ArrayList<StoritevZaIzpis> storitve= new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Storitev s;
		Oseba o;
		Gradivo g;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select s.*, g.*, o.ime, o.priimek, o.ID_osebe from storitev s, gradivo g, oseba o where g.ID_gradiva=s.tk_id_gradiva and o.ID_osebe=s.tk_id_clana and datumVracila is null");
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				o=new Oseba();
				o.setId(s.getTk_id_clana());
				o.setIme(rs.getString("ime"));
				o.setPriimek(rs.getString("priimek"));
				szi=new StoritevZaIzpis(s,g);
				szi.setOseba(o);
				storitve.add(szi);
			}

		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return storitve;
	}
	
	public ArrayList<StoritevZaIzpis> pridobiVseAktualneIzposojeOsebe(int idOsebe){
		ArrayList<StoritevZaIzpis> storitve= new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Storitev s;
		Gradivo g;
		Oseba o;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select s.*, g.*, o.ime, o.priimek, o.ID_osebe  from storitev s, gradivo g, oseba o where o.ID_osebe=s.tk_id_clana and g.ID_gradiva=s.tk_id_gradiva and datumVracila is null and tk_id_clana=?"); 
			st.setInt(1, idOsebe);
			rs=st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), idOsebe, rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				o=new Oseba();
				o.setId(s.getTk_id_clana());
				o.setIme(rs.getString("ime"));
				o.setPriimek(rs.getString("priimek"));
				szi=new StoritevZaIzpis(s,g);
				szi.setOseba(o);
				storitve.add(szi);
			}

			System.out.println(storitve.size());
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return storitve;
	}
	
	public List<StoritevZaIzpis> pridobiFiltriraneIzposojeO(String osebaImeFilter, String osebaPriimekFilter) {
		ArrayList<StoritevZaIzpis> storitve= new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Storitev s;
		Oseba o;
		Gradivo g;
		try{
			povezava =  Povezava.getConnection();
			String sql;
			if((osebaImeFilter!="")||(osebaPriimekFilter!=""))
			{
				sql="select s.*, g.*, o.ime, o.priimek, o.ID_osebe from storitev s, gradivo g, oseba o where g.ID_gradiva=s.tk_id_gradiva and o.ID_osebe=s.tk_id_clana and datumVracila is null";
				if(osebaImeFilter!="")
					sql+=" and o.ime like '" + osebaImeFilter.trim() +"'";
				if(osebaPriimekFilter!="")
					sql+=" and o.priimek like '" + osebaPriimekFilter.trim() +"'";
			}
			else
			{
				sql="select s.*, g.*, o.ime, o.priimek, o.ID_osebe from storitev s, gradivo g, oseba o where g.ID_gradiva=s.tk_id_gradiva and o.ID_osebe=s.tk_id_clana and datumVracila is null";
			}
			
			st=povezava.prepareStatement(sql);
			
			rs=st.executeQuery();
			
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				o=new Oseba();
				o.setId(s.getTk_id_clana());
				o.setIme(rs.getString("ime"));
				o.setPriimek(rs.getString("priimek"));
				szi=new StoritevZaIzpis(s,g);
				szi.setOseba(o);
				storitve.add(szi);
			}
			
		}		
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return storitve;
	}

	public List<StoritevZaIzpis> pridobiStoritvePrekoRoka() {
		ArrayList<StoritevZaIzpis> storitve=new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Gradivo g;
		Storitev s;
		Oseba o;
		java.util.Date danasnjiDatum=new java.util.Date();
		Date datum;
		try{
			povezava =  Povezava.getConnection();
			
			st = povezava.prepareStatement("select s.*, g.*, o.ime, o.priimek, o.ID_osebe from gradivo g, storitev s, oseba o where o.ID_osebe=s.tk_id_clana and s.tk_id_gradiva=g.ID_gradiva and s.datumVracila is null and s.rokVracila<?");
			datum=new Date(danasnjiDatum.getTime());
			st.setDate(1, datum);
			rs = st.executeQuery();
			
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				o=new Oseba();
				o.setId(s.getTk_id_clana());
				o.setIme(rs.getString("ime"));
				o.setPriimek(rs.getString("priimek"));
				szi=new StoritevZaIzpis(s,g);
				szi.setOseba(o);
				storitve.add(szi);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return storitve;
	}
	
	public List<StoritevZaIzpis> pridobiStoritveOsebePrekoRoka(int id) {
		ArrayList<StoritevZaIzpis> storitve=new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Gradivo g;
		Storitev s;
		java.util.Date danasnjiDatum=new java.util.Date();
		Date datum;
		try{
			povezava =  Povezava.getConnection();
			
			st = povezava.prepareStatement("select s.*, g.* from gradivo g, storitev s where s.tk_id_gradiva=g.ID_gradiva and s.datumVracila is null and s.rokVracila<? and s.tk_id_clana=?");
			datum=new Date(danasnjiDatum.getTime());
			st.setDate(1, datum);
			st.setInt(2, id);
			rs = st.executeQuery();
			
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				szi=new StoritevZaIzpis(s,g);
				storitve.add(szi);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return storitve;
	}


	public List<StoritevZaIzpis> pridobiVseIzposojeGledeNaStatus(String statusFilter) {
		ArrayList<StoritevZaIzpis> storitve=new ArrayList<StoritevZaIzpis>();
		StoritevZaIzpis szi;
		Gradivo g;
		Storitev s;
		Oseba o;

		try{
			povezava =  Povezava.getConnection();
			
			if(statusFilter.equals("akt"))
				st = povezava.prepareStatement("select s.*, g.*, o.ime, o.priimek, o.ID_osebe from storitev s, gradivo g, oseba o where g.ID_gradiva=s.tk_id_gradiva and o.ID_osebe=s.tk_id_clana and datumVracila is null");
			else
				st = povezava.prepareStatement("select s.*, g.*, o.ime, o.priimek, o.ID_osebe from storitev s, gradivo g, oseba o where g.ID_gradiva=s.tk_id_gradiva and o.ID_osebe=s.tk_id_clana and datumVracila is not null");

			rs = st.executeQuery();
			while (rs.next())
			{
				s=new Storitev(rs.getInt("ID_storitve"), rs.getDate("datumIzposoje"), rs.getDate("datumVracila"), rs.getDate("rokVracila"), rs.getBoolean("zePodaljsano"), rs.getInt("tk_id_clana"), rs.getInt("tk_id_gradiva"), rs.getInt("tk_id_knjiznicarja"));
				g=new Gradivo(rs.getInt("ID_gradiva"), rs.getString("naslov"), rs.getString("originalNaslov"), Jezik.valueOf(rs.getString("jezik")), rs.getInt("letoIzida"), rs.getString("ISBN"), rs.getString("opis"), rs.getInt("tk_id_podrocja"), rs.getInt("tk_id_vrste"), rs.getInt("tk_id_zalozbe"));
				szi=new StoritevZaIzpis(s,g);
				o=new Oseba();
				o.setId(s.getTk_id_clana());
				o.setIme(rs.getString("ime"));
				o.setPriimek(rs.getString("priimek"));
				szi=new StoritevZaIzpis(s,g);
				szi.setOseba(o);
				storitve.add(szi);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return storitve;
	}
}
