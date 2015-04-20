package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.Oseba;
import ris.projekt.knjiznica.beans.Prijava;
import ris.projekt.knjiznica.beans.TipOsebe;

public class OsebaDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static OsebaDAO od;
	
	private OsebaDAO(){
		
	}
	
	public static synchronized OsebaDAO dobiInstanco()
	{
		if(od==null)
			od=new OsebaDAO();
		return od;
	}
	
	public Oseba dodajOsebo(Oseba o)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into oseba (ime, priimek, tipOsebe, email, telefon, tk_id_naslova) values (?, ?, ?, ?, ?, ?)");
			st.setString(1, o.getIme());
			st.setString(2, o.getPriimek());
			st.setString(3, o.getTipOsebe().toString());
			st.setString(4, o.getEmail());
			st.setString(5, o.getTelefon());
			st.setInt(6, o.getTk_id_naslova());
			
			//naslov se doda v bazo že prej pa se id zapiše pod tk_id_naslova
			
			st.executeUpdate();
           
			st.close();
			
            st=povezava.prepareStatement("select ID_osebe from oseba where ime=? and priimek=? and tipOsebe=? and email=? and telefon=? and tk_id_naslova=?");
            st.setString(1, o.getIme());
			st.setString(2, o.getPriimek());
			st.setString(3, o.getTipOsebe().toString());
			st.setString(4, o.getEmail());
			st.setString(5, o.getTelefon());
			st.setInt(6, o.getTk_id_naslova());
           
            rs = st.executeQuery();
            if (rs.next())
            {
            	o.setId(rs.getInt("ID_osebe"));
            }
			
			
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return o;
	}
	
	public Oseba urediOsebo(Oseba o)
	{
		try{
			povezava = Povezava.getConnection();
			st = povezava.prepareStatement("update oseba set ime=?, priimek=?, email=?, telefon=?, tk_id_naslova=? where ID_osebe=?");
            
			st.setString(1, o.getIme());
            st.setString(2, o.getPriimek());
            st.setString(3, o.getEmail());
            st.setString(4, o.getTelefon()); 
            st.setInt(5,o.getTk_id_naslova()); 
            st.setInt(6, o.getId());
            st.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return o;
	}
	
	public boolean izbrisiOsebo(int id)
	{
		
		boolean izbris=false;
		try{
			povezava = Povezava.getConnection();
			//preveri èe si oseba ni še niè sposodila
			st=povezava.prepareStatement("select count(*) as st from storitev where tk_id_clana=?"); 
			st.setInt(1, id);
			rs=st.executeQuery();
			if(rs.next()){
				if(0==rs.getInt("st")){
					izbris=true;
				}
			}
			rs.close();
			st.close();
			
			
			if(izbris==true){
				izbris=false;
				//preveri èe oseba ni nikomur niè sposodila (v vlogi knjižnièara)
				st=povezava.prepareStatement("select count(*) as st from storitev where tk_id_knjiznicarja=?"); 
				st.setInt(1, id);
				rs=st.executeQuery();
				if(rs.next()){
					if(0==rs.getInt("st")){
						izbris=true;
					}
				}
				rs.close();
				st.close();
			}
			
			if(izbris==true){
				izbris=false;
				//preveri, èe oseba ni bila na èrni listi
				st=povezava.prepareStatement("select count(*) as st from zapisNaCl where tk_id_osebe=?"); 
				st.setInt(1, id);
				rs=st.executeQuery();
				if(rs.next()){
					if(0==rs.getInt("st")){
						izbris=true;
					}
				}
				rs.close();
				st.close();
			}
			
			if (izbris==true){
				//zbrisi prijavo
				st=povezava.prepareStatement("delete from prijava where tk_id_osebe=? and ID_prijave<>0"); //ID_prijave<>0 ... ker tk_id_osebe ni kljuè ne moreš spreminjat glede na njega
				st.setInt(1, id);
	            st.executeUpdate();
	            st.close();
	            //izbris naslova 
				//zbrisi osebo
				st = povezava.prepareStatement("delete from oseba where ID_osebe=?");
	            st.setInt(1, id);
	            st.executeUpdate();
			}
			

        } 
		catch (SQLException e) {
            e.printStackTrace();
        }
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}

		return izbris;
	}
	
	public void spremeniTipOsebe(int id, String tip)
	{
		povezava = Povezava.getConnection();
		String novTip;
		if (tip.equals("èlan"))
			novTip="knjižnièar";
		
		else
		{
			novTip="èlan";		
			try {
				st=povezava.prepareStatement("delete from prijava where tk_id_osebe=? and ID_prijave<>0");
				st.setInt(1, id);
	            st.executeUpdate();
	            st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

		}
		
		
		try {
			st = povezava.prepareStatement("update oseba set tipOsebe=? where ID_osebe=?");
			st.setString(1, novTip);
	        st.setInt(2, id);            
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

	public Oseba pridobiOsebo(int id)
	{
		Oseba oseba=new Oseba();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from oseba where ID_osebe=?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next())
			{
					oseba.setId(id);
					oseba.setIme(rs.getString("ime"));
					oseba.setPriimek(rs.getString("priimek"));
					oseba.setTipOsebe(TipOsebe.valueOf(rs.getString("tipOsebe")));
					oseba.setEmail(rs.getString("email"));
					oseba.setTelefon(rs.getString("telefon"));
					oseba.setTk_id_naslova(rs.getInt("tk_id_naslova"));
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		return oseba;
	}
	
	public ArrayList<Oseba> pridobiOsebe(){
		ArrayList<Oseba> osebe = new ArrayList<Oseba>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from oseba");

			rs = st.executeQuery();
			
			Oseba o;
			while (rs.next())
			{
				o=new Oseba(rs.getInt("ID_osebe"), rs.getString("ime"), rs.getString("priimek"), TipOsebe.valueOf(rs.getString("tipOsebe")), rs.getString("email"), rs.getString("telefon"), rs.getInt("tk_id_naslova"));
				osebe.add(o);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return osebe;
		
	}
	
	public ArrayList<Oseba> pridobiClane()
	{
		ArrayList<Oseba> osebe = new ArrayList<Oseba>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from oseba where tipOsebe=?");
			st.setString(1, "èlan");
			rs = st.executeQuery();
			
			Oseba o;
			while (rs.next())
			{
				o=new Oseba(rs.getInt("ID_osebe"), rs.getString("ime"), rs.getString("priimek"), TipOsebe.èlan, rs.getString("email"), rs.getString("telefon"), rs.getInt("tk_id_naslova"));
				osebe.add(o);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return osebe;
	}
	
	//monika - preveri =))
	public ArrayList<Oseba> pridobiKnjiznicarje()
	{
		ArrayList<Oseba> osebe = new ArrayList<Oseba>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from oseba where tipOsebe=?");
			st.setString(1, "knjižnièar");
			rs = st.executeQuery();
			
			Oseba o;
			while (rs.next())
			{
				o=new Oseba(rs.getInt("ID_osebe"), rs.getString("ime"), rs.getString("priimek"), TipOsebe.knjižnièar, rs.getString("email"), rs.getString("telefon"), rs.getInt("tk_id_naslova"));
				osebe.add(o);
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return osebe;
	}

	public int preveriPrijavo(Prijava p) {
		int idKnjiznicarja=-1;

		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from prijava where upIme=? and geslo=?");
			st.setString(1, p.getUpIme());
			st.setString(2, p.getGeslo());

			rs = st.executeQuery();
			
			if(rs.next())
				idKnjiznicarja=rs.getInt("tk_id_osebe");
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return idKnjiznicarja;
		
	}
	
	//dodelaj za preverjanje ce staro vneseno geslo drzi, potem update z novim
	public boolean spremeniUporabniskoImeInGeslo(Prijava p, String novoGeslo){
		boolean uspesnaPrijava = false;
		
		povezava = Povezava.getConnection();
		try {
			st = povezava.prepareStatement("update prijava set upIme=?, geslo=? where tk_id_osebe=? and ID_prijave<>0");
			st.setString(1, p.getUpIme());
			st.setString(2, p.getGeslo());
	        st.setInt(3, p.getTk_id_osebe());
	        st.executeUpdate();
		}
	     catch(SQLException e){
				e.printStackTrace();
		}
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return uspesnaPrijava;
	}
	
	public void dodajPrijavo(Prijava p)
	{

		povezava = Povezava.getConnection();
		try {
			st = povezava.prepareStatement("insert into prijava (upIme, geslo, tk_id_osebe) values (?, ?, ?)");
			st.setString(1, p.getUpIme());
			st.setString(2, p.getGeslo());
	        st.setInt(3, p.getTk_id_osebe());
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

	public Prijava pridobiPrijavo(int idOsebe) {
		Prijava prijava=new Prijava();
		povezava = Povezava.getConnection();
		try {
			st = povezava.prepareStatement("select * from prijava where tk_id_osebe=?");
			st.setInt(1, idOsebe);

			rs = st.executeQuery();
			
			if(rs.next())
			{
				prijava.setGeslo(rs.getString("geslo"));
				prijava.setId(rs.getInt("ID_prijave"));
				prijava.setUpIme(rs.getString("upIme"));
				prijava.setTk_id_osebe(idOsebe);	
			}
			
			
		}
	     catch(SQLException e){
				e.printStackTrace();
		}
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}		
		
		return prijava;
	}
}
