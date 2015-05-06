package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Avtor;
import ris.projekt.knjiznica.beans.Gradivo;
import ris.projekt.knjiznica.beans.GradivoZaIzpis;
import ris.projekt.knjiznica.beans.Jezik;


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
			
            st=povezava.prepareStatement("select ID_gradiva from gradivo where naslov=? and originalNaslov=? and jezik=? and letoIzida=? and ISBN=? and opis=? and tk_id_podrocja=? and tk_id_vrste=? and tk_id_zalozbe=?");
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
			povezava =  Povezava.getConnection();

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
			povezava =  Povezava.getConnection();

	        //preveri, èe je gradivo že bilo izposojeno
			st=povezava.prepareStatement("select count(*) as st from storitev where tk_id_gradiva=?");
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
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
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
	
	//pridobi gradiva preko roka
	public ArrayList<Gradivo> pridobiGradivaKiSoPrekoRokaVrnitve(){
		
		ArrayList<Gradivo> gradiva=new ArrayList<Gradivo>();
		Gradivo g;
		java.util.Date danasnjiDatum=new java.util.Date();

		try{
			povezava =  Povezava.getConnection();
			
			st = povezava.prepareStatement("select g.* from gradivo g, gradivo_storitev gs, storitev s where gs.tk_id_gradiva=g.ID_gradiva and gs.tk_id_storitve=s.ID_storitve and s.rokVracila<?");
			
			st.setDate(1, (Date) danasnjiDatum);
			rs = st.executeQuery();
			
			if (rs.next())
			{
					g=new Gradivo();
					g.setId(rs.getInt("ID_Gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
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
				st = povezava.prepareStatement("select g.* from gradivo g,  storitev s where s.tk_id_gradiva=g.ID_gradiva and s.datumVracila is null");
			
				rs = st.executeQuery();
			
				while (rs.next())
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
			else{
				st = povezava.prepareStatement("select * from gradivo");
				rs = st.executeQuery();
				while (rs.next())
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
				rs.close();
				st.close();
				
				System.out.println(gradiva.size());

				
				st=povezava.prepareStatement("select count(*) as st from storitev where tk_id_gradiva=? and datumVracila is null");
			
				for (int i=0; i<gradiva.size(); i++)
				{
					st.setInt(1, gradiva.get(i).getId());
					rs=st.executeQuery();
					if(rs.next())
					{
						System.out.println(rs.getInt("st"));
						if(rs.getInt("st")!=0)
						{
							gradiva.remove(i);
							i--;
						}
					}
					
				}
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

	public List<GradivoZaIzpis> pridobiVsaGradivaZaIzpis() {
		ArrayList<GradivoZaIzpis> gradiva=new ArrayList<GradivoZaIzpis>();
		GradivoZaIzpis g;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select g.*, p.naziv as podrocje, vg.naziv as vrsta, z.naziv as zalozba from gradivo g, podrocje p, vrstagradiva vg, zalozba z where g.tk_id_podrocja=p.ID_podrocja and g.tk_id_zalozbe=z.ID_zalozbe and g.tk_id_vrste=vg.ID_vrste");
			rs = st.executeQuery();
			
			while (rs.next())
			{
					g=new GradivoZaIzpis();
					g.setId(rs.getInt("ID_gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setPodrocje(rs.getString("podrocje"));
					g.setVrsta(rs.getString("vrsta"));
					g.setZalozba(rs.getString("zalozba"));
					gradiva.add(g);
			}
			
			rs.close();
			st.close();
				
			ArrayList<Avtor> avtorjiGradiva;
			Avtor a;
			st=povezava.prepareStatement("select a.* from avtor a, gradivo_avtor ga where a.ID_avtorja=ga.tk_id_avtorja and ga.tk_id_gradiva=?");
			for(int i=0; i<gradiva.size(); i++){
				avtorjiGradiva=new ArrayList<Avtor>();
				st.setInt(1, gradiva.get(i).getId());
				rs=st.executeQuery();
				while (rs.next())
				{
					a=new Avtor(rs.getInt("ID_avtorja"), rs.getString("ime"), rs.getString("priimek"));
					avtorjiGradiva.add(a);
				}
				rs.close();
				gradiva.get(i).setAvtorji(avtorjiGradiva);
			}
		
		}
		catch(SQLException e){
			e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return gradiva;
	}

	public List<GradivoZaIzpis> pridobiFiltriranaGradivaL(String jezikFilter, String letoIzidaFilter, int vrstaFilter,	int podrocjeFilter, int zalozbaFilter) {
		ArrayList<GradivoZaIzpis> gradiva=new ArrayList<GradivoZaIzpis>();
		GradivoZaIzpis g;
		try{
			povezava =  Povezava.getConnection();
		
			String sql="select g.*, p.naziv as podrocje, vg.naziv as vrsta, z.naziv as zalozba from gradivo g, podrocje p, vrstagradiva vg, zalozba z where g.tk_id_podrocja=p.ID_podrocja and g.tk_id_zalozbe=z.ID_zalozbe and g.tk_id_vrste=vg.ID_vrste";
			
			if(!jezikFilter.equals("brez")){
				sql+=" and g.jezik='" + jezikFilter+ "'";
			}
			if(vrstaFilter!=-1){
				sql+=" and vg.ID_vrste='" + vrstaFilter+ "'";
			}
			if(zalozbaFilter!=-1){
				sql+=" and z.ID_zalozbe='" + zalozbaFilter+ "'";

			}
			if(podrocjeFilter!=-1){
				sql+=" and p.ID_podrocja='" + podrocjeFilter+ "'";

			}
			if(letoIzidaFilter!=""){
				sql+=" and g.letoIzida='" + letoIzidaFilter+ "'";

			}
			
			
			System.out.println(sql);
			
			st = povezava.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next())
			{
					g=new GradivoZaIzpis();
					g.setId(rs.getInt("ID_gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setPodrocje(rs.getString("podrocje"));
					g.setVrsta(rs.getString("vrsta"));
					g.setZalozba(rs.getString("zalozba"));
					gradiva.add(g);
			}
			
			rs.close();
			st.close();
				
			ArrayList<Avtor> avtorjiGradiva;
			Avtor a;
			st=povezava.prepareStatement("select a.* from avtor a, gradivo_avtor ga where a.ID_avtorja=ga.tk_id_avtorja and ga.tk_id_gradiva=?");
			for(int i=0; i<gradiva.size(); i++){
				avtorjiGradiva=new ArrayList<Avtor>();
				st.setInt(1, gradiva.get(i).getId());
				rs=st.executeQuery();
				while (rs.next())
				{
					a=new Avtor(rs.getInt("ID_avtorja"), rs.getString("ime"), rs.getString("priimek"));
					avtorjiGradiva.add(a);
				}
				rs.close();
				gradiva.get(i).setAvtorji(avtorjiGradiva);
			}
		
		}
		catch(SQLException e){
			e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return gradiva;
	}

	public List<GradivoZaIzpis> pridobiFiltriranaGradivaA(String avtorImeFilter, String avtorPriimekFilter) {
		ArrayList<GradivoZaIzpis> gradiva=new ArrayList<GradivoZaIzpis>();
		GradivoZaIzpis g;
		try{
			povezava =  Povezava.getConnection();
			String sql;
			if((avtorImeFilter!="")||(avtorPriimekFilter!=""))
			{
				sql="select g.*, p.naziv as podrocje, vg.naziv as vrsta, z.naziv as zalozba from gradivo g, podrocje p, vrstagradiva vg, zalozba z, gradivo_avtor ga, avtor a where g.tk_id_podrocja=p.ID_podrocja and g.tk_id_zalozbe=z.ID_zalozbe and g.tk_id_vrste=vg.ID_vrste and ga.tk_id_gradiva=g.ID_gradiva and ga.tk_id_avtorja=a.ID_avtorja";
				if(avtorImeFilter!="")
					sql+=" and a.ime like '" + avtorImeFilter.trim() +"'";
				if(avtorPriimekFilter!="")
					sql+=" and a.priimek like '" + avtorPriimekFilter.trim() +"'";
			}
			else
			{
				sql="select g.*, p.naziv as podrocje, vg.naziv as vrsta, z.naziv as zalozba from gradivo g, podrocje p, vrstagradiva vg, zalozba z where g.tk_id_podrocja=p.ID_podrocja and g.tk_id_zalozbe=z.ID_zalozbe and g.tk_id_vrste=vg.ID_vrste";
			}
			
			System.out.println(sql);
			st = povezava.prepareStatement(sql);
			rs = st.executeQuery();
			
			while (rs.next())
			{
					g=new GradivoZaIzpis();
					g.setId(rs.getInt("ID_gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setPodrocje(rs.getString("podrocje"));
					g.setVrsta(rs.getString("vrsta"));
					g.setZalozba(rs.getString("zalozba"));
					gradiva.add(g);
			}
			
			rs.close();
			st.close();
				
			ArrayList<Avtor> avtorjiGradiva;
			Avtor a;
			st=povezava.prepareStatement("select a.* from avtor a, gradivo_avtor ga where a.ID_avtorja=ga.tk_id_avtorja and ga.tk_id_gradiva=?");
			for(int i=0; i<gradiva.size(); i++){
				avtorjiGradiva=new ArrayList<Avtor>();
				st.setInt(1, gradiva.get(i).getId());
				rs=st.executeQuery();
				while (rs.next())
				{
					a=new Avtor(rs.getInt("ID_avtorja"), rs.getString("ime"), rs.getString("priimek"));
					avtorjiGradiva.add(a);
				}
				rs.close();
				gradiva.get(i).setAvtorji(avtorjiGradiva);
			}
		
			
		}
		catch(SQLException e){
			e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return gradiva;
	}

	public List<GradivoZaIzpis> pridobiFiltriranaGradivaS(String statusFilter) {

		ArrayList<GradivoZaIzpis> gradiva=new ArrayList<GradivoZaIzpis>();
		GradivoZaIzpis g;
		try{
			povezava =  Povezava.getConnection();
			
			if(statusFilter.equals("izposojeno")){
				st = povezava.prepareStatement("select g.*, p.naziv as podrocje, vg.naziv as vrsta, z.naziv as zalozba from gradivo g, vrstagradiva vg, podrocje p, zalozba z, storitev s  where g.tk_id_podrocja=p.ID_podrocja and g.tk_id_zalozbe=z.ID_zalozbe and g.tk_id_vrste=vg.ID_vrste and p.ID_podrocja=g.tk_id_podrocja and s.tk_id_gradiva=g.ID_gradiva and s.datumVracila is null");
			
				rs = st.executeQuery();
			
				while (rs.next())
				{
						g=new GradivoZaIzpis();
						g.setId(rs.getInt("ID_Gradiva"));
						g.setNaslov(rs.getString("naslov"));
						g.setOriginalNaslov(rs.getString("originalNaslov"));
						g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
						g.setLetoIzida(rs.getInt("letoIzida"));
						g.setISBN(rs.getString("ISBN"));
						g.setOpis(rs.getString("opis"));
						g.setPodrocje(rs.getString("podrocje"));
						g.setVrsta(rs.getString("vrsta"));
						g.setZalozba(rs.getString("zalozba"));
						gradiva.add(g);
				}
			}
			else{
				st = povezava.prepareStatement("select g.*, p.naziv as podrocje, vg.naziv as vrsta, z.naziv as zalozba from gradivo g, podrocje p, vrstagradiva vg, zalozba z where g.tk_id_podrocja=p.ID_podrocja and g.tk_id_zalozbe=z.ID_zalozbe and g.tk_id_vrste=vg.ID_vrste");
				rs = st.executeQuery();
				while (rs.next())
				{
					g=new GradivoZaIzpis();
					g.setId(rs.getInt("ID_Gradiva"));
					g.setNaslov(rs.getString("naslov"));
					g.setOriginalNaslov(rs.getString("originalNaslov"));
					g.setJezik(Jezik.valueOf(rs.getString("jezik"))); 
					g.setLetoIzida(rs.getInt("letoIzida"));
					g.setISBN(rs.getString("ISBN"));
					g.setOpis(rs.getString("opis"));
					g.setPodrocje(rs.getString("podrocje"));
					g.setVrsta(rs.getString("vrsta"));
					g.setZalozba(rs.getString("zalozba"));
					gradiva.add(g);
				}
				rs.close();
				st.close();
				
				System.out.println(gradiva.size());

				
				st=povezava.prepareStatement("select count(*) as st from storitev where tk_id_gradiva=? and datumVracila is null");
			
				for (int i=0; i<gradiva.size(); i++)
				{
					st.setInt(1, gradiva.get(i).getId());
					rs=st.executeQuery();
					if(rs.next())
					{
						System.out.println(rs.getInt("st"));
						if(rs.getInt("st")!=0)
						{
							gradiva.remove(i);
							i--;
						}
					}
					
				}
			}						
				
			ArrayList<Avtor> avtorjiGradiva;
			Avtor a;
			st=povezava.prepareStatement("select a.* from avtor a, gradivo_avtor ga where a.ID_avtorja=ga.tk_id_avtorja and ga.tk_id_gradiva=?");
			for(int i=0; i<gradiva.size(); i++){
				avtorjiGradiva=new ArrayList<Avtor>();
				st.setInt(1, gradiva.get(i).getId());
				rs=st.executeQuery();
				while (rs.next())
				{
					a=new Avtor(rs.getInt("ID_avtorja"), rs.getString("ime"), rs.getString("priimek"));
					avtorjiGradiva.add(a);
				}
				rs.close();
				gradiva.get(i).setAvtorji(avtorjiGradiva);
			}
		
		}
		catch(SQLException e){
			e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return gradiva;

	}
	
	public boolean preveriZaIzposojo(int idGradiva)
	{
		boolean izposoja=true;
		
		try{
			povezava =  Povezava.getConnection();
			
			st=povezava.prepareStatement("select * from gradivo where ID_gradiva=?");
			st.setInt(1, idGradiva);
			
			rs=st.executeQuery();
			if(!rs.next())
					izposoja=false;
			
			if (izposoja=true)
			{
				rs.close();
				st.close();
				
				st=povezava.prepareStatement("select g.* from gradivo g,  storitev s where s.tk_id_gradiva=g.ID_gradiva and s.datumVracila is null and ID_gradiva=?");
				st.setInt(1, idGradiva);
				
				rs=st.executeQuery();
				if(rs.next())
						izposoja=false;
			}
		}
			catch(SQLException e){
				e.printStackTrace();} 
			finally{
				try{rs.close();} catch(SQLException e){}
				try{st.close();} catch(SQLException e){}
				try{povezava.close();} catch(SQLException e){}
			}
		return izposoja;
	}
}
