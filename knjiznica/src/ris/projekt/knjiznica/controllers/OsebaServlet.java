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
		boolean redirect = false;
		HttpSession seja = request.getSession();
		
		if(metoda.equals("dodajOs")){
			request.setAttribute("metoda","dodajOsebo" );
			stran="/glavnaVsebina/DodajOsebo.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiPrijavo")){
			seja.removeAttribute("Prijava");
			stran="/glavnaVsebina/Login.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiOsebo")){
			String urejanjeOs=null;
			String neizbrisan=null;
			try{
				uporabnik = osebaDAO.pridobiOsebo(idOsebe);
				request.setAttribute("uporabnik", uporabnik);
				
				naslov = naslovDAO.pridobiNaslov(uporabnik.getTk_id_naslova());
				request.setAttribute("naslov", naslov);
				neizbrisan = request.getParameter("neizbrisan");
				if(neizbrisan!=null)
					request.setAttribute("neizbrisan", true);
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
					list = osebaDAO.pridobiPoTipu("èlan");
				else if(filter.equals("knjiznicar"))
					list = osebaDAO.pridobiPoTipu("knjižnièar");
				request.setAttribute("osebe", list);
			}
			catch(NullPointerException e){	}
			
			stran="/glavnaVsebina/Osebe.jsp"; //placeholder
		}
		else if(metoda.equals("urediKnjiznicar")){
			request.setAttribute("metoda","spremeniPrijavo" );
			idOsebe = (Integer)seja.getAttribute("ID"); 

			Prijava prijava = osebaDAO.pridobiPrijavo(idOsebe);
			request.setAttribute("ime", prijava.getUpIme()); 
			
			stran="/glavnaVsebina/novaPrijava.jsp"; //placeholder
		}
		else if(metoda.equals("urediTip")){
			String tip = request.getParameter("tip"); //stari tip!
			
			//iz knjiznicarja v clana
			if(tip.equals("knjižnièar")){
				//se samo izbrise prijava pa spremeni tip
				osebaDAO.spremeniTipOsebe(idOsebe, tip);
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=pridobiOsebo&idOsebe="+idOsebe;
			}
			//iz clana v knjiznicarja
			else
			{
			osebaDAO.spremeniTipOsebe(idOsebe, tip);
			request.setAttribute("idOsebe", idOsebe);
			request.setAttribute("metoda", "dodajPrijavo");
			stran = "/glavnaVsebina/novaPrijava.jsp"; //placeholder
			}
		}
		else if(metoda.equals("izbrisiOsebo")){
			//v bazi niso povezani, zato je vrstni red brisanja nepomemben - drugace prvo zbrises prijavo! -nova metoda 
			if(osebaDAO.izbrisiOsebo(idOsebe)){ //izbrise tudi naslov in prijavo
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
			}
			else{ 
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=pridobiOsebo&idOsebe="+idOsebe+"&neizbrisan=true";
			}
		
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
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
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
		boolean redirect = false;
		
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

			if(uporabnik.getTipOsebe().equals(TipOsebe.knjižnièar)){
				//uporabniški raèun
				Prijava upRacun = new Prijava();
				upRacun.setUpIme(request.getParameter("upIme"));
				upRacun.setGeslo(request.getParameter("geslo"));
				upRacun.setTk_id_osebe(uporabnik.getId());
				osebaDAO.dodajPrijavo(upRacun);
			}
			//ce je blo uspesno dodano.. kaj pa ce ni blo?
			redirect = true;
			stran="/knjiznica/OsebaServlet?metoda=pridobiOsebo&idOsebe="+uporabnik.getId();
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
			int idNaslova = naslovDAO.urediNaslov(naslov);
			
			//ostalo oseba
			uporabnik = new Oseba();
			uporabnik.setIme(request.getParameter("ime"));
			uporabnik.setPriimek(request.getParameter("priimek"));
			uporabnik.setEmail(request.getParameter("email"));
			uporabnik.setTelefon(request.getParameter("tel"));
			uporabnik.setTk_id_naslova(idNaslova);
			uporabnik.setId(idOsebe);
			uporabnik = osebaDAO.urediOsebo(uporabnik);

			redirect = true;
			stran="/knjiznica/OsebaServlet?metoda=pridobiOsebo&idOsebe="+uporabnik.getId();	
			
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
				seja.setAttribute("Sprememba",true);
				stran="/glavnaVsebina/Domov.jsp"; //placeholder
			}
			else{
				seja.setAttribute("Sprememba",false);
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=urediKnjiznicar"; //placeholder
			}		
			
		}
		else if(metoda.equals("dodajPrijavo")){
			Prijava prijava = new Prijava();
			prijava.setUpIme(request.getParameter("ime"));
			prijava.setGeslo(request.getParameter("geslo"));
			prijava.setTk_id_osebe(idOsebe);
			
			osebaDAO.dodajPrijavo(prijava);
			
			stran="/glavnaVsebina/Domov.jsp"; //placeholder
	
		}
	
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
