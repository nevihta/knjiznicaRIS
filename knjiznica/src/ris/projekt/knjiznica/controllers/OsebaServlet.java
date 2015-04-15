package ris.projekt.knjiznica.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ris.projekt.knjiznica.beans.Prijava;
import ris.projekt.knjiznica.dao.PrijavaDAO;


@WebServlet("/OsebaServlet")
public class OsebaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OsebaServlet() {
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String metoda="";
		try{
			metoda = request.getParameter("metoda");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String stran="";
		PrijavaDAO ud = PrijavaDAO.dobiInstanco();
		HttpSession seja = request.getSession();
		
		if(metoda.equals("prijava")){
			Prijava uporabnik = new Prijava();
			System.out.println(request.getParameter("ime")+" "+request.getParameter("geslo"));
			uporabnik.setUpIme(request.getParameter("ime"));
			uporabnik.setGeslo(request.getParameter("geslo"));
			
			if(ud.prijava(uporabnik)){
				seja.setAttribute("Prijava",true);
				stran="/glavnaVsebina/Test.jsp";
			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp";
			}
		}
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);
	}

}
