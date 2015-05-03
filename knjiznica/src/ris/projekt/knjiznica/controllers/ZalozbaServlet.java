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

import ris.projekt.knjiznica.beans.Zalozba;
import ris.projekt.knjiznica.dao.ZalozbaDAO;

@WebServlet("/ZalozbaServlet")
public class ZalozbaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ZalozbaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ZalozbaDAO zalozbaDAO = ZalozbaDAO.dobiInstanco();
		String metoda="";
		int idZalozba=-1;
		try{
			metoda = request.getParameter("metoda");
			idZalozba = Integer.parseInt(request.getParameter("idZalozba"));

		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		boolean redirect = false;
		
		if(metoda.equals("pridobiVse")){
			List<Zalozba> list = new ArrayList<Zalozba>();
			
			try{
				list = zalozbaDAO.pridobiVseZalozbe();
				request.setAttribute("zalozbe", list);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/Zalozbe.jsp"; //placeholder
		}
		
		else if(metoda.equals("izbrisi")){
			if(zalozbaDAO.izbrisiZalozbo(idZalozba)){
				redirect = true;
				stran="/knjiznica/ZalozbaServlet?metoda=pridobiVse";	
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ZalozbaDAO zalozbaDAO = ZalozbaDAO.dobiInstanco();
		Zalozba zalozba = new Zalozba();
		int idZalozba = -1;
		
		String metoda="";
		try{
			metoda = request.getParameter("metoda");
			idZalozba = Integer.parseInt(request.getParameter("idZalozba"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		boolean redirect = false;
		
		if(metoda.equals("dodaj")){
			zalozbaDAO.dodajZalozbo(request.getParameter("naziv"));
			redirect = true;
			stran="/knjiznica/ZalozbaServlet?metoda=pridobiVse";	
		}
		else if(metoda.equals("urediZalozbo")){
			zalozba = new Zalozba();
			zalozba.setNaziv(request.getParameter("naziv"));
			zalozba.setId(idZalozba); 
			zalozbaDAO.spremeniZalozbo(zalozba);

			redirect = true;
			stran="/knjiznica/ZalozbaServlet?metoda=pridobiVse";		
			
		}


		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
