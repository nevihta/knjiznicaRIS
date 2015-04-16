package ris.projekt.knjiznica.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ris.projekt.knjiznica.beans.*;
import ris.projekt.knjiznica.dao.*;


@WebServlet("/GradivoServlet")
public class GradivoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GradivoServlet() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		AvtorDAO avtorDAO = AvtorDAO.dobiInstanco();
		Avtor avtor = new Avtor();
		int idAvtorja = -1;
		String metoda="";
		try{
			metoda = request.getParameter("metoda");
			idAvtorja = Integer.parseInt(request.getParameter("idAvtor"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		
		if(metoda.equals("pridobiAvtorja")){
			String urejanje=null;
			try{
				avtor = avtorDAO.pridobiAvtorja(idAvtorja);
				request.setAttribute("avtor", avtor);
			
				urejanje = request.getParameter("urejanje");
				
			}catch(NullPointerException e){e.getMessage();}
			if(urejanje!=null){
				request.setAttribute("metoda", "spremeniAvtor");
				stran="/glavnaVsebina/DodajAvtor.jsp"; //placeholder
			}
			else{
				stran="/glavnaVsebina/Avtor.jsp"; //placeholder
			}
			
		}
		else if(metoda.equals("pridobiVse")){
			List<Avtor> list = new ArrayList<Avtor>();
			
			try{
				list = avtorDAO.pridobiVseAvtorje();
				request.setAttribute("osebe", list);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/Avtorji.jsp"; //placeholder
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		AvtorDAO avtorDAO = AvtorDAO.dobiInstanco();
		Avtor avtor = new Avtor();
		int idAvtorja = -1;
		
		String metoda="";
		try{
			metoda = request.getParameter("metoda");
			idAvtorja = Integer.parseInt(request.getParameter("idAvtor"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		
		if(metoda.equals("dodaj")){
			//oseba
			avtor.setIme(request.getParameter("ime"));
			avtor.setPriimek(request.getParameter("priimek"));
			avtorDAO.dodajAvtorja(avtor);
			
			stran="/glavnaVsebina/Domov.jsp"; //placeholder
		}
		else if(metoda.equals("izbrisi")){
			if(avtorDAO.izbrisi(idAvtorja))
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
			else //neko opozorilo da ne more zbrisat
				stran = "/glavnaVsebina/Avtor.jsp"; //placeholder
		
		}

		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);
	}

}
