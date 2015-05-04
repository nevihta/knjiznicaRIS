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

import ris.projekt.knjiznica.beans.Podrocje;
import ris.projekt.knjiznica.dao.PodrocjeDAO;

@WebServlet("/PodrocjeServlet")
public class PodrocjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PodrocjeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		PodrocjeDAO podrocjeDAO = PodrocjeDAO.dobiInstanco();
		String metoda="";
		int idPodrocja=-1;
		boolean redirect = false;

		
		try{
			metoda = request.getParameter("metoda");
			idPodrocja = Integer.parseInt(request.getParameter("idPodrocje"));

		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		
		
		if(metoda.equals("pridobiVse")){
			List<Podrocje> list = new ArrayList<Podrocje>();
			String neizbrisan=null;
			try{
				list = podrocjeDAO.pridobiVsaPodrocja();
				request.setAttribute("podrocja", list);
				neizbrisan = request.getParameter("neizbrisan");
				if(neizbrisan!=null)
					request.setAttribute("neizbrisan", true);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/Podrocja.jsp"; //placeholder
		}
		else if(metoda.equals("izbrisi")){
			if(podrocjeDAO.izbrisiPodrocje(idPodrocja)){
				redirect=true;
				stran="/knjiznica/PodrocjeServlet?metoda=pridobiVse";
			}
			else {
				redirect=true;
				stran="/knjiznica/PodrocjeServlet?metoda=pridobiVse&neizbrisan=true";
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
		
		PodrocjeDAO podrocjeDAO = PodrocjeDAO.dobiInstanco();
		Podrocje podrocje = new Podrocje();
		int idPodrocja = -1;
		
		String metoda="";
		try{
			metoda = request.getParameter("metoda");
			idPodrocja = Integer.parseInt(request.getParameter("idPodrocje"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		boolean redirect = false;
		
		if(metoda.equals("dodaj")){
			podrocjeDAO.dodajPodrocje(request.getParameter("naziv"));
			
			redirect = true;
			stran="/knjiznica/PodrocjeServlet?metoda=pridobiVse";	
		}
		else if(metoda.equals("urediPodrocje")){
			podrocje = new Podrocje();
			podrocje.setNaziv(request.getParameter("naziv"));
			podrocje.setId(idPodrocja); 
			podrocjeDAO.spremeniPodrocje(podrocje);

			redirect = true;
			stran="/knjiznica/PodrocjeServlet?metoda=pridobiVse";		
			
		}
	

		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
