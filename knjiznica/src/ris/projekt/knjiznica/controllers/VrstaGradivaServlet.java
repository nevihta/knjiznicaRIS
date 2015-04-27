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

import ris.projekt.knjiznica.beans.VrstaGradiva;
import ris.projekt.knjiznica.dao.VrstaGradivaDAO;

@WebServlet("/VrstaGradivaServlet")
public class VrstaGradivaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public VrstaGradivaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		VrstaGradivaDAO vrstaGradivaDAO = VrstaGradivaDAO.dobiInstanco();
		String metoda="";
		try{
			metoda = request.getParameter("metoda");
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		
		
		if(metoda.equals("pridobiVse")){
			List<VrstaGradiva> list = new ArrayList<VrstaGradiva>();
			
			try{
				list = vrstaGradivaDAO.pridobiVseVrsteGradiva();
				request.setAttribute("vrsteGradiva", list);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/VrsteGradiva.jsp"; //placeholder
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		VrstaGradivaDAO vrstaGradivaDAO = VrstaGradivaDAO.dobiInstanco();
		VrstaGradiva vrstaGradiva = new VrstaGradiva();
		int idVrsteGradiva = -1;
		
		String metoda="";
		try{
			metoda = request.getParameter("metoda");
			idVrsteGradiva = Integer.parseInt(request.getParameter("idVrstaGradiva"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		boolean redirect = false;
		
		if(metoda.equals("dodaj")){
			vrstaGradivaDAO.dodajVrstoGradiva(request.getParameter("naziv"));
			
			redirect = true;
			stran="/knjiznica/VrstaGradivaServlet?metoda=pridobiVse";	
		}
		else if(metoda.equals("urediVrstoGradiva")){
			vrstaGradiva = new VrstaGradiva();
			vrstaGradiva.setNaziv(request.getParameter("naziv"));
			vrstaGradiva.setId(idVrsteGradiva); // try catch =)	
			vrstaGradivaDAO.spremeniVrstoGradiva(vrstaGradiva);

			redirect = true;
			stran="/knjiznica/VrstaGradivaServlet?metoda=pridobiVse";		
			
		}
		else if(metoda.equals("izbrisi")){
			if(vrstaGradivaDAO.izbrisiVrstoGradiva(idVrsteGradiva)){
				redirect = true;
				stran="/knjiznica/VrstaGradivaServlet?metoda=pridobiVse";	
			}
			else //neko opozorilo da ne more zbrisat
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
		
		}

		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
