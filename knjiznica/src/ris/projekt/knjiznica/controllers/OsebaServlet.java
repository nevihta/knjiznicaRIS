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
		StoritevDAO storitevDAO=StoritevDAO.dobiInstanco();
		CrnaListaDAO crnaListaDAO=CrnaListaDAO.dobiInstanco();
		
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
		request.setAttribute("meni", "osebe");
		
		if(metoda.equals("dodajOs")){
			request.setAttribute("metoda","dodajOsebo" );
			stran="/glavnaVsebina/DodajOsebo.jsp"; 
		}
		else if(metoda.equals("pridobiPrijavo")){
			seja.removeAttribute("Prijava");
			stran="/glavnaVsebina/Login.jsp"; 
		}
		else if(metoda.equals("pridobiOsebo")){
			String urejanjeOs=null;
			String neizbrisan=null;
			Boolean jeNaCL;
			try{
				uporabnik = osebaDAO.pridobiOsebo(idOsebe);
				request.setAttribute("uporabnik", uporabnik);
				
				naslov = naslovDAO.pridobiNaslov(uporabnik.getTk_id_naslova());
				request.setAttribute("naslov", naslov);
				neizbrisan = request.getParameter("neizbrisan");
				
				if(neizbrisan!=null)
					request.setAttribute("neizbrisan", true);
				
				urejanjeOs = request.getParameter("urejanjeOs");
				
				jeNaCL=crnaListaDAO.preveriCeJeClanNaCl(idOsebe);
				
				request.setAttribute("cl", jeNaCL);
			}catch(NullPointerException e){e.getMessage();}
			if(urejanjeOs!=null){
				request.setAttribute("metoda", "urediOsebo");
				stran="/glavnaVsebina/DodajOsebo.jsp";
			}
			else{
				stran="/glavnaVsebina/Oseba.jsp"; 
			}
			
		}
		else if(metoda.equals("pridobiVse")){
			boolean filt = false;
			List<Oseba> list = new ArrayList<Oseba>();
			String filter = request.getParameter("filter");
			String poslano = null;
			try{
				if(filter.equals("vse"))
					list = osebaDAO.pridobiOsebe();
				else if(filter.equals("clan"))
					list = osebaDAO.pridobiPoTipu("�lan");
				else if(filter.equals("knjiznicar"))
					list = osebaDAO.pridobiPoTipu("knji�ni�ar");
				else if(filter.equals("zamudnik"))
				{
					filt=true;
					list=osebaDAO.pridobiZamudnike();
				}
				request.setAttribute("filt", filt);
				request.setAttribute("osebe", list);
			}
			catch(NullPointerException e){	}
			
			//obvestilo, ali je mail poslan/ni poslan
			poslano = request.getParameter("poslano");
			if(poslano!=null){
				if(poslano.equals("true"))
					request.setAttribute("poslano", true);
				else if(poslano.equals("false"))
					request.setAttribute("poslano", false);
			}
			
			stran="/glavnaVsebina/Osebe.jsp"; 
		}
		else if(metoda.equals("urediKnjiznicar")){
			request.setAttribute("metoda","spremeniPrijavo" );
			idOsebe = (Integer)seja.getAttribute("ID"); 

			Prijava prijava = osebaDAO.pridobiPrijavo(idOsebe);
			request.setAttribute("ime", prijava.getUpIme()); 
			
			stran="/glavnaVsebina/novaPrijava.jsp"; 
		}
		else if(metoda.equals("urediTip")){
			String tip = request.getParameter("tip"); //stari tip!
			
			//iz knjiznicarja v clana
			if(tip.equals("knji�ni�ar")){
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
			stran = "/glavnaVsebina/novaPrijava.jsp"; 
			}
		}
		else if(metoda.equals("izbrisiOsebo")){
			//v bazi niso povezani, zato je vrstni red brisanja nepomemben - drugace prvo zbrises prijavo! -nova metoda 
			if(osebaDAO.izbrisiOsebo(idOsebe)){ //izbrise tudi naslov in prijavo
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=domov"; 
			}
			else{ 
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=pridobiOsebo&idOsebe="+idOsebe+"&neizbrisan=true";
			}
		
		}
		else if(metoda.equals("domov")){
			request.setAttribute("meni", "domov");
			stran = "/glavnaVsebina/Domov.jsp"; 
		}
		else if(metoda.equals("odjava")){
			seja.removeAttribute("Prijava");
			seja.removeAttribute("ID");
			redirect = true;
			stran="/knjiznica/OsebaServlet?metoda=domov"; 
		}
		else if(metoda.equals("pridobiZgO")){
			ArrayList<StoritevZaIzpis> szi=storitevDAO.pridobiVseIzposojeOsebe(idOsebe);
			Oseba o=osebaDAO.pridobiOsebo(idOsebe);
			request.setAttribute("zgodovinaO", szi);
			request.setAttribute("zgo", "oseba");
			request.setAttribute("o", o);
		
			stran="/glavnaVsebina/ZgodovinaIzposoj.jsp";
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
		request.setAttribute("meni", "osebe");
		
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
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=domov"; 
			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp"; 
			}
		}
		else if(metoda.equals("dodajOsebo")){
			//najprej naslov - za tk v osebo
			naslov.setUlica(request.getParameter("ulica"));
			naslov.setHisnaSt(request.getParameter("hisna"));
			naslov.setMesto(request.getParameter("mesto"));
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

			if(uporabnik.getTipOsebe().equals(TipOsebe.knji�ni�ar)){
				//uporabni�ki ra�un
				Prijava upRacun = new Prijava();
				upRacun.setUpIme(request.getParameter("upIme"));
				upRacun.setGeslo(request.getParameter("geslo"));
				upRacun.setTk_id_osebe(uporabnik.getId());
				osebaDAO.dodajPrijavo(upRacun);
			}
			redirect = true;
			stran="/knjiznica/OsebaServlet?metoda=pridobiOsebo&idOsebe="+uporabnik.getId();
		}
		else if(metoda.equals("urediOsebo")){
			//naslov
			naslov = new Naslov();
			naslov.setUlica(request.getParameter("ulica"));
			naslov.setHisnaSt(request.getParameter("hisna"));
			naslov.setMesto(request.getParameter("mesto"));
			naslov.setPostnaSt(Integer.parseInt(request.getParameter("posta")));
			naslov.setDrzava(request.getParameter("drzava"));
			naslov.setId(Integer.parseInt(request.getParameter("idNaslov"))); 	
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
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=domov"; 
			}
			else{
				seja.setAttribute("Sprememba",false);
				redirect = true;
				stran="/knjiznica/OsebaServlet?metoda=urediKnjiznicar"; 
			}		
			
		}
		else if(metoda.equals("dodajPrijavo")){
			Prijava prijava = new Prijava();
			prijava.setUpIme(request.getParameter("ime"));
			prijava.setGeslo(request.getParameter("geslo"));
			prijava.setTk_id_osebe(idOsebe);
			
			osebaDAO.dodajPrijavo(prijava);
			redirect = true;
			stran="/knjiznica/OsebaServlet?metoda=domov"; 
	
		}
	
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
	}

}
