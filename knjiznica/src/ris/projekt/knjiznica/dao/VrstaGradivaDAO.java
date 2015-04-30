package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ris.projekt.knjiznica.baza.Povezava;
import ris.projekt.knjiznica.beans.VrstaGradiva;

public class VrstaGradivaDAO {


	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static VrstaGradivaDAO vgd;
	
	private VrstaGradivaDAO() {
		
	}
	
	public static synchronized VrstaGradivaDAO dobiInstanco()
	{
		if(vgd==null)
			vgd=new VrstaGradivaDAO();
		return vgd;
	}
	
	public int dodajVrstoGradiva(String vrsta)
	{
		int idTipaGradiva=-1;
		try{
			povezava =  Povezava.getConnection();

			//preveri, èe že obstaja
			st=povezava.prepareStatement("select ID_vrste from vrstagradiva where naziv=?");
			st.setString(1, vrsta);
			rs= st.executeQuery();
			
			if(rs.next())
			{
				idTipaGradiva=rs.getInt("ID_vrste");
			}
			
			rs.close();
			st.close();
			
			if(idTipaGradiva==-1)
			{
			st = povezava.prepareStatement("insert into vrstagradiva (naziv) values (?)");
			st.setString(1, vrsta);
			st.executeUpdate();
			st.close();
			
			//pridobi id
			st=povezava.prepareStatement("select ID_vrste from vrstagradiva where naziv=?");
			st.setString(1, vrsta);
			rs= st.executeQuery();
			
			if(rs.next())
			{
				idTipaGradiva=rs.getInt("ID_vrste");
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
		return idTipaGradiva;
	}
	
	public boolean izbrisiVrstoGradiva(int id)
	{
		boolean izbris=false;
		try{
			povezava =  Povezava.getConnection();

			//preveri, èe še ni vezano na katero gradivo
			st=povezava.prepareStatement("select count(*) as st from gradivo where tk_id_vrste=?");
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
				st = povezava.prepareStatement("delete from vrstagradiva where ID_vrste=?");
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
	
	public void spremeniVrstoGradiva(VrstaGradiva v)
	{
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("update vrstaGradiva set naziv=? where ID_vrste=?");
			st.setString(1, v.getNaziv());
			st.setInt(2, v.getId());
				
			st.executeUpdate();
		}
			
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public VrstaGradiva pridobiVrsto(int id)
	{
		VrstaGradiva g=new VrstaGradiva();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from vrstagradiva where ID_vrste=?");
			st.setInt(1, id);
			rs=st.executeQuery();
			if(rs.next())
			{
				g.setId(rs.getInt("ID_vrste"));
				g.setNaziv(rs.getString("naziv"));
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
	
	public List<VrstaGradiva> pridobiVseVrsteGradiva()
	{
		ArrayList<VrstaGradiva> vrste=new ArrayList<VrstaGradiva>();
		try{
			povezava =  Povezava.getConnection();

			st = povezava.prepareStatement("select * from vrstagradiva");
			rs=st.executeQuery();
				
			while(rs.next()){
				vrste.add(new VrstaGradiva(rs.getInt("ID_vrste"), rs.getString("naziv")));
			}
		}
			
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{rs.close();} catch(SQLException e){}
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
		return vrste;
	}
}
