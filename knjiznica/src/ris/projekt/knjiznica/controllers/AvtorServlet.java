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

import ris.projekt.knjiznica.beans.Avtor;
import ris.projekt.knjiznica.dao.AvtorDAO;

@WebServlet("/AvtorServlet")
public class AvtorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AvtorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		AvtorDAO avtorDAO = AvtorDAO.dobiInstanco();
		String metoda="";
		int idAvtorja=-1;
		try{
			metoda = request.getParameter("metoda");
			idAvtorja = Integer.parseInt(request.getParameter("idAvtor"));

		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		boolean redirect=false;
		
		if(metoda.equals("pridobiVse")){
			List<Avtor> list = new ArrayList<Avtor>();
			String neizbrisan=null;
			try{
				list = avtorDAO.pridobiVseAvtorje();
				request.setAttribute("avtorji", list);
				neizbrisan = request.getParameter("neizbrisan");
				if(neizbrisan!=null)
					request.setAttribute("neizbrisan", true);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/Avtorji.jsp"; //placeholder
		}
		else if(metoda.equals("izbrisi")){
			if(avtorDAO.izbrisi(idAvtorja)){
				redirect = true;
				stran="/knjiznica/AvtorServlet?metoda=pridobiVse";	
			}
			else{
				redirect = true;
				stran="/knjiznica/AvtorServlet?metoda=pridobiVse&neizbrisan=true";
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
		boolean redirect = false;
		
		if(metoda.equals("dodaj")){
			avtor.setIme(request.getParameter("ime"));
			avtor.setPriimek(request.getParameter("priimek"));
			avtorDAO.dodajAvtorja(avtor);
			
			redirect = true;
			stran="/knjiznica/AvtorServlet?metoda=pridobiVse";	
		}
		else if(metoda.equals("urediAvtor")){
			avtor = new Avtor();
			avtor.setIme(request.getParameter("ime"));
			avtor.setPriimek(request.getParameter("priimek"));
			avtor.setId(idAvtorja); 
			avtorDAO.urediAvtorja(avtor); 

			redirect = true;
			stran="/knjiznica/AvtorServlet?metoda=pridobiVse";	
			
		}
	
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
