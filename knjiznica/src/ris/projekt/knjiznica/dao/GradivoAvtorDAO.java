package ris.projekt.knjiznica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ris.projekt.knjiznica.baza.Povezava;

public class GradivoAvtorDAO {

	private Connection povezava;
	private PreparedStatement st;
	private ResultSet rs;
	
	private static GradivoAvtorDAO gad;
	
	private GradivoAvtorDAO(){
		
	}
	
	public static synchronized GradivoAvtorDAO dobiInstanco()
	{
		if(gad==null)
			gad=new GradivoAvtorDAO();
		return gad;
	}
	
	public void dodajAvtorGradivo(List<Integer> idAvtorjev, int idGradiva)
	{
		try {
			povezava =  Povezava.getConnection();
			
			st=povezava.prepareStatement("insert into gradivo_avtor(tk_id_gradiva, tk_id_avtorja) values (?,?)");
			st.setInt(1, idGradiva);
			for(int i=0; i<idAvtorjev.size(); i++)
			{
				st.setInt(2, idAvtorjev.get(i));
				st.executeUpdate();
			}
					
		}catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
	}
	
	public void izbrisiAvtorGradivo(int idGradiva, int idAvtorja)
	{
		try{
			povezava = Povezava.getConnection();
			st=povezava.prepareStatement("delete from gradivo_avtor where tk_id_gradiva=? and tk_id_avtorja=? and ID_ga<>0"); 
			st.setInt(1, idGradiva);
			st.setInt(2, idAvtorja);
		    st.executeUpdate();
	
		}	
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
	}
	
	//vraèa seznam avtorjev gradiva
	public ArrayList<Integer> pridobiAvtorjeGradiva(int idGradiva)
	{
		ArrayList<Integer> avtorji=new ArrayList<Integer>();
		try{
			povezava = Povezava.getConnection();
			st=povezava.prepareStatement("select * from gradivo_avtor where tk_id_gradiva=?"); 
			st.setInt(1, idGradiva);
		    rs=st.executeQuery();
		    while (rs.next())
		    {
		    	avtorji.add(rs.getInt("tk_id_avtorja"));
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
	
	public ArrayList<Integer> pridobiGradivaAvtorja(int idAvtorja)
	{
		ArrayList<Integer> gradiva=new ArrayList<Integer>();
		try{
			povezava = Povezava.getConnection();
			st=povezava.prepareStatement("select * from gradivo_avtor where tk_id_avtorja=?"); 
			st.setInt(1, idAvtorja);
		    rs=st.executeQuery();
		    while (rs.next())
		    {
		    	gradiva.add(rs.getInt("tk_id_gradiva"));
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

	public void izbrisiVseAvtorGradivo(int idGradiva) {
		
		try{
			povezava = Povezava.getConnection();
			st=povezava.prepareStatement("delete from gradivo_avtor where tk_id_gradiva=? and ID_ga<>0"); 
			st.setInt(1, idGradiva);
			st.executeUpdate();
		}	
		catch(SQLException e){e.printStackTrace();} 
		finally{
			try{st.close();} catch(SQLException e){}
			try{povezava.close();} catch(SQLException e){}
		}
		
	}
	
}
