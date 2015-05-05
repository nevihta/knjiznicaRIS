package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.ZapisNaCl;

public class CrnaListaDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static CrnaListaDAO cld;
	
	private CrnaListaDAO(){
		
	}
	
	public static synchronized CrnaListaDAO dobiInstanco()
	{
		if(cld==null)
			cld=new CrnaListaDAO();
		return cld;
	}
	
	public void dodajClanaNaListo(ZapisNaCl z)
	{
		Date datum;
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("insert into zapisnacl (datumZapisa, datumIzbrisa, razlog, tk_id_osebe) values (?,?,?,?)");
			datum=new Date(z.getDatumZapisa().getTime());
			st.setDate(1, datum);
			st.setDate(2, null);
			st.setString(3, z.getRazlog());
			st.setInt(4, z.getTk_id_osebe());
			
			st.executeUpdate();
           
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public void izbrisiClanaZListe(int id)
	{
		Date datum;
		java.util.Date danasnjiDatum=new java.util.Date();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update zapisnacl set datumIzbrisa=? where ID_zapisa=?");
			datum=new Date(danasnjiDatum.getTime());
			st.setDate(1, datum);
			st.setInt(2, id);
			
			st.executeUpdate();
           
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	//pazi, vraèa zapisNaCl, ne pa Osebe! Po klicu tega bo potrebno še za vsak zapis pridobiti osebo!
	public ArrayList<ZapisNaCl> pridobiSeznamClanovNaCl(){
		
		ArrayList<ZapisNaCl> zapisi=new ArrayList<ZapisNaCl>();
		ZapisNaCl z;
		try{
			povezava =  Povezava.getConnection();
			st = povezava.prepareStatement("select * from zapisnacl where datumIzbrisa=null");
	
			   rs = st.executeQuery();
	           while (rs.next())
	            {
	            	z=new ZapisNaCl(rs.getInt("ID_Zapisa"), rs.getDate("datumZapisa"), rs.getDate("datumIzbrisa"), rs.getString("razlog"), rs.getInt("tk_id_osebe"));
	            	zapisi.add(z);		
	            }
           
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		
		return zapisi;
		
	}
	
	public boolean preveriCeJeClanNaCl(int id){
		boolean preveri=false;
		try{
			povezava =  Povezava.getConnection();
	
			st=povezava.prepareStatement("select * from zapisNaCl where tk_id_osebe=? and datumIzbrisa=null"); 
			st.setInt(1, id);
			rs=st.executeQuery();
			if(rs.next()){
					preveri=true;
			}
		}
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return preveri;
		
	}
	
}
