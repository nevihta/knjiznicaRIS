package ris.projekt.knjiznica.controllers;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ris.projekt.knjiznica.beans.*;
import ris.projekt.knjiznica.dao.*;


@WebServlet("/OsebaServlet")
public class OsebaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OsebaServlet() {
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		OsebaDAO osebaDAO = OsebaDAO.dobiInstanco();
		NaslovDAO naslovDAO = NaslovDAO.dobiInstanco();
		int idOsebe = -1;
		String metoda="";
		
		Oseba uporabnik = new Oseba();
		Naslov naslov = new Naslov();

		try{
			metoda = request.getParameter("metoda");
			idOsebe = Integer.parseInt(request.getParameter("idOsebe"));
		}catch(Exception e){e.printStackTrace();}
		
		String stran="";
		HttpSession seja = request.getSession();
		
		if(metoda.equals("dodajOs")){
			request.setAttribute("metoda","dodajOsebo" );
			stran="/glavnaVsebina/DodajOsebo.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiOsebo")){
			String urejanjeOs=null;
			try{
				uporabnik = osebaDAO.pridobiOsebo(idOsebe);
				request.setAttribute("uporabnik", uporabnik);
				
				naslov = naslovDAO.pridobiNaslov(uporabnik.getTk_id_naslova());
				request.setAttribute("naslov", naslov);

				urejanjeOs = request.getParameter("urejanjeOs");
				
			}catch(NullPointerException e){e.getMessage();}
			if(urejanjeOs!=null){
				request.setAttribute("metoda", "urediOsebo");
				stran="/glavnaVsebina/DodajOsebo.jsp"; //placeholder
			}
			else{
				stran="/glavnaVsebina/Oseba.jsp"; //placeholder
			}
			
		}
		else if(metoda.equals("pridobiVse")){
			List<Oseba> list = new ArrayList<Oseba>();
			String filter = request.getParameter("filter");
			try{
				if(filter.equals("vse"))
					list = osebaDAO.pridobiOsebe();
				else if(filter.equals("clan"))
					list = osebaDAO.pridobiClane();
				else if(filter.equals("knjiznicar"))
					list = osebaDAO.pridobiKnjiznicarje();
				request.setAttribute("osebe", list);
			}
			catch(NullPointerException e){	}
			
			stran="/glavnaVsebina/Osebe.jsp"; //placeholder
		}
		else if(metoda.equals("urediKnjiznicar")){
			request.setAttribute("metoda","spremeniPrijavo" );
			idOsebe = (Integer)seja.getAttribute("ID"); 

			Prijava prijava = osebaDAO.pridobiPrijavo(idOsebe);
			request.setAttribute("upIme", prijava.getUpIme()); 
			
			stran="/glavnaVsebina/urediPrijava.jsp"; //placeholder
		}
		else if(metoda.equals("domov")){
			stran = "/glavnaVsebina/Domov.jsp"; 
		}
		else if(metoda.equals("odjava")){
			seja.removeAttribute("Prijava");
			seja.removeAttribute("ID");
			stran="/glavnaVsebina/Domov.jsp";
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Oseba uporabnik = new Oseba();
		Naslov naslov = new Naslov();
		
		String metoda="";
		int idOsebe = -1;
		try{
			metoda = request.getParameter("metoda");
			idOsebe = Integer.parseInt(request.getParameter("idOsebe"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String stran="";
		OsebaDAO osebaDAO = OsebaDAO.dobiInstanco();
		NaslovDAO naslovDAO = NaslovDAO.dobiInstanco();
		HttpSession seja = request.getSession();
		
		if(metoda.equals("prijava")){
			Prijava prijava = new Prijava();
			System.out.println(request.getParameter("ime")+" "+request.getParameter("geslo"));
			prijava.setUpIme(request.getParameter("ime"));
			prijava.setGeslo(request.getParameter("geslo"));
			
			int idUporabnika = osebaDAO.preveriPrijavo(prijava);
			if(idUporabnika!=-1)
			{
				seja.setAttribute("Prijava",true);
				seja.setAttribute("ID", idUporabnika);
				stran="/glavnaVsebina/Domov.jsp";
			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp"; 
			}
		}
		else if(metoda.equals("dodajOsebo")){
			//preverjanje, ce so vsi podatki vneseni? v jsp, + tukaj?
			//najprej naslov - za tk v osebo
			
			naslov.setUlica(request.getParameter("ulica"));
			naslov.setHisnaSt(request.getParameter("hisna"));
			naslov.setMesto(request.getParameter("mesto"));
			//try catch ali v jsp preverjanje?
			naslov.setPostnaSt(Integer.parseInt(request.getParameter("posta")));
			naslov.setDrzava(request.getParameter("drzava"));
			naslov = naslovDAO.dodajNaslov(naslov);
			
			//oseba
			uporabnik.setIme(request.getParameter("ime"));
			uporabnik.setPriimek(request.getParameter("priimek"));
			uporabnik.setTipOsebe(TipOsebe.valueOf(request.getParameter("tip")));
			uporabnik.setEmail(request.getParameter("email"));
			uporabnik.setTelefon(request.getParameter("tel"));
			uporabnik.setTk_id_naslova(naslov.getId());
			uporabnik = osebaDAO.dodajOsebo(uporabnik);
			
			if(uporabnik.getTipOsebe().toString().equals(TipOsebe.knjižnièar)){
				//uporabniški raèun
				Prijava upRacun = new Prijava();
				upRacun.setUpIme(request.getParameter("upIme"));
				upRacun.setGeslo(request.getParameter("geslo"));
				upRacun.setTk_id_osebe(uporabnik.getId());
				osebaDAO.dodajPrijavo(upRacun);;
			}
			request.setAttribute("naslov", naslov);
			request.setAttribute("uporabnik", uporabnik);
			stran="/glavnaVsebina/Domov.jsp"; //placeholder
		}
		else if(metoda.equals("urediOsebo")){
			//naslov
			naslov = new Naslov();
			naslov.setUlica(request.getParameter("ulica"));
			naslov.setHisnaSt(request.getParameter("hisna"));
			naslov.setMesto(request.getParameter("mesto"));
			//try catch ali v jsp preverjanje?
			naslov.setPostnaSt(Integer.parseInt(request.getParameter("posta")));
			naslov.setDrzava(request.getParameter("drzava"));
			naslov.setId(Integer.parseInt(request.getParameter("idNaslov"))); // try catch =)	
			naslov = naslovDAO.urediNaslov(naslov);
			
			//ostalo oseba
			uporabnik = new Oseba();
			uporabnik.setIme(request.getParameter("ime"));
			uporabnik.setPriimek(request.getParameter("priimek"));
			uporabnik.setTipOsebe(TipOsebe.valueOf(request.getParameter("tip")));
			uporabnik.setEmail(request.getParameter("email"));
			uporabnik.setTelefon(request.getParameter("tel"));
			uporabnik.setTk_id_naslova(naslov.getId());
			uporabnik.setId(idOsebe);
			uporabnik = osebaDAO.urediOsebo(uporabnik);
			
			request.setAttribute("naslov", naslov);
			request.setAttribute("uporabnik", uporabnik);
			stran="/glavnaVsebina/domov.jsp"; //placeholder			
			
		}
		else if(metoda.equals("urediTip")){
			String tip = request.getParameter("tip"); //stari tip!
			
			//iz knjiznicarja v clana
			if(tip.equals(TipOsebe.knjižnièar)){
				//se samo izbrise prijava pa spremeni tip
				osebaDAO.spremeniTipOsebe(idOsebe, tip);
				stran = "/glavnaVsebina/Oseba.jsp";
			}
			//iz clana v knjiznicarja
			osebaDAO.spremeniTipOsebe(idOsebe, tip);
			request.setAttribute("idOsebe", idOsebe);
			stran = "/glavnaVsebina/novaPrijava.jsp"; //placeholder
		}
		else if(metoda.equals("izbrisiOsebo")){
			//v bazi niso povezani, zato je vrstni red brisanja nepomemben - drugace prvo zbrises prijavo! -nova metoda 
			if(osebaDAO.izbrisiOsebo(idOsebe))
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
			else //neko opozorilo da ne more zbrisat
				stran = "/glavnaVsebina/Oseba.jsp"; //placeholder
			//naslov izbris?
		
		}
		else if(metoda.equals("spremeniPrijavo")){
			Prijava prijava = new Prijava();
			prijava.setUpIme(request.getParameter("ime"));
			prijava.setGeslo(request.getParameter("geslo"));
			int idPrijave = osebaDAO.pridobiPrijavo((Integer)seja.getAttribute("ID")).getId();
			prijava.setId(idPrijave);
			String novoGeslo = request.getParameter("gesloN");
			boolean uspesno = osebaDAO.spremeniUporabniskoImeInGeslo(prijava,novoGeslo);
			
			if(uspesno){
				stran="/glavnaVsebina/Domov.jsp"; //placeholder
			}
			else{
				//neki meessage
				stran="/glavnaVsebina/urediPrijava.jsp"; //placeholder
			}		
			
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);
	}

}
