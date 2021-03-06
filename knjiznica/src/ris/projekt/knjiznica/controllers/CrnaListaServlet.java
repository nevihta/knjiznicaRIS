package ris.projekt.knjiznica.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ris.projekt.knjiznica.Email;
import ris.projekt.knjiznica.beans.*;
import ris.projekt.knjiznica.dao.*;

@WebServlet("/CrnaListaServlet")
public class CrnaListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CrnaListaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		CrnaListaDAO crnaDAO = CrnaListaDAO.dobiInstanco();
		
		int idOsebe = -1;
		String metoda="";

		try{
			metoda = request.getParameter("metoda");
			idOsebe = Integer.parseInt(request.getParameter("idOsebe"));
		}catch(Exception e){e.getMessage();}
		
		String stran="";
		boolean redirect = false;
		request.setAttribute("meni", "lista");
		
		if(metoda.equals("dodajOsnaCL")){
			if(idOsebe!=-1){
				request.setAttribute("idOsebe", idOsebe);
				request.setAttribute("metoda","dodajOSnaCL" );
				stran="/glavnaVsebina/novZapisCL.jsp"; 
			}
			else{
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=domov"; 
			}
				
		}
		else if(metoda.equals("pridobiVse")){
			boolean pretekle=false;
			String filter=request.getParameter("filter");
			List<ZapisNaCl> crnaLista;
			if(filter.equals("pret"))
			{
				crnaLista = crnaDAO.pridobiSeznamClanovNaClPret();
				pretekle=true;
			}
			else
				crnaLista = crnaDAO.pridobiSeznamClanovNaCl();

			request.setAttribute("pretekle", pretekle);
			request.setAttribute("crnaLista", crnaLista);
			stran="/glavnaVsebina/CrnaLista.jsp"; 
		}
		else if(metoda.equals("email")){
			OsebaDAO os = OsebaDAO.dobiInstanco();
			Oseba o = os.pridobiOsebo(idOsebe);
			StoritevDAO st = StoritevDAO.dobiInstanco();
			List<StoritevZaIzpis> zamujenoGradivo = st.pridobiStoritveOsebePrekoRoka(idOsebe);
			if(Email.posljiEmailZamudnina(o.getEmail(), zamujenoGradivo)){
				//obvestilo, da je bil mail poslan
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=pridobiVse&filter=zamudnik&poslano=true"; 
			}
			else{
				//obvestilo, da je prislo do napake
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=pridobiVse&filter=zamudnik&poslano=false"; 
			}
			
		}
		else if(metoda.equals("odstrani")){
			//ne izbrise iz baze, ampak samo iz crne liste - "odstrani"
			int idZapisa = -1;
			try{
				 idZapisa=Integer.parseInt(request.getParameter("idZapisa"));
			}catch(Exception e){e.getMessage();}
			
			if(idZapisa!=-1){
				crnaDAO.izbrisiClanaZListe(idZapisa);
				redirect = true;
				stran="/knjiznica/CrnaListaServlet?metoda=pridobiVse&filter=akt";	
			}
			else {
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=domov"; 
			}
		
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		CrnaListaDAO crnaDAO = CrnaListaDAO.dobiInstanco();
		
		int idOsebe = -1;
		String metoda="";
		
		ZapisNaCl zapis = new ZapisNaCl();

		try{
			metoda = request.getParameter("metoda");
			idOsebe = Integer.parseInt(request.getParameter("idOsebe"));
		}catch(Exception e){e.printStackTrace();}
		
		String stran="";
		boolean redirect = false;
		request.setAttribute("meni", "lista");
		
		if(metoda.equals("dodajOSnaCL")){
			if(idOsebe!=-1){
				zapis.setTk_id_osebe(idOsebe);
				zapis.setRazlog(request.getParameter("razlog"));
				zapis.setDatumZapisa(new Date());
				crnaDAO.dodajClanaNaListo(zapis);
				redirect = true;
				stran="/knjiznica/CrnaListaServlet?metoda=pridobiVse&filter=akt";	
			}
			else{
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=domov"; 
			}
		}

		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
