package ris.projekt.knjiznica.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession seja = request.getSession();
		
		if(metoda.equals("dodajOsnaCL")){
			if(idOsebe!=-1){
				request.setAttribute("idOsebe", idOsebe);
				request.setAttribute("metoda","dodajOSnaCL" );
				stran="/glavnaVsebina/novZapisCL.jsp"; //placeholder
			}
			else
				stran="/glavnaVsebina/Domov.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiVse")){
			//+ filter za stare zapise?
			//rabimo osebe!
			List<ZapisNaCl> crnaLista = crnaDAO.pridobiSeznamClanovNaCl();
			request.setAttribute("crnaLista", crnaLista);
			stran="/glavnaVsebina/CrnaLista.jsp"; //placeholder
		}
		else if(metoda.equals("odstrani")){
			//ne izbrise iz baze, ampak samo iz crne liste - "odstrani"
			int idZapisa = -1;
			try{
				request.getParameter("idZapisa");
			}catch(Exception e){e.getMessage();}
			
			if(idZapisa!=-1){
				crnaDAO.izbrisiClanaZListe(idZapisa);
				redirect = true;
				stran="/knjiznica/CrnaListaServlet?metoda=pridobiVse";	
			}
			else {
				stran="/glavnaVsebina/Domov.jsp"; //placeholder
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

		if(metoda.equals("dodajOSnaCL")){
			if(idOsebe!=-1){
				zapis.setTk_id_osebe(idOsebe);
				zapis.setRazlog(request.getParameter("razlog"));
				zapis.setDatumZapisa(new Date());
				crnaDAO.dodajClanaNaListo(zapis);
				redirect = true;
				stran="/knjiznica/CrnaListaServlet?metoda=pridobiVse";	
			}
			else
				stran="/glavnaVsebina/Domov.jsp"; //placeholder
		}
		else if(metoda.equals("dodajNovoZamujanje")){
			//direktno iz seznama zamudnin gumbek? naredimo seznam zamudnikov?
			//kak potem, ce je ze na listi - mogoce pri seznamu zamudnikov 
			//samo pri tistih linki, ki se niso na list?
			if(idOsebe!=-1){
				zapis.setTk_id_osebe(idOsebe);
				zapis.setRazlog("Zamudnina");
				zapis.setDatumZapisa(new Date());
				crnaDAO.dodajClanaNaListo(zapis);
				redirect = true;
				stran="/knjiznica/CrnaListaServlet?metoda=pridobiVse";	
			}
			else
				stran="/glavnaVsebina/Domov.jsp"; //placeholder
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
