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
		
		GradivoDAO gradivoDAO = GradivoDAO.dobiInstanco();
		AvtorDAO avtorDAO = AvtorDAO.dobiInstanco();
		ZalozbaDAO zalozbaDAO = ZalozbaDAO.dobiInstanco();
		GradivoAvtorDAO gradivoAvtorDAO = GradivoAvtorDAO.dobiInstanco();
		PodrocjeDAO podrocjeDAO = PodrocjeDAO.dobiInstanco();
		VrstaGradivaDAO vrstaDAO = VrstaGradivaDAO.dobiInstanco();
		
		String metoda="";
		int idGradiva = -1;
		try{
			metoda = request.getParameter("metoda");
			idGradiva = Integer.parseInt(request.getParameter("idGradiva"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		Gradivo gradivo = new Gradivo();
		String urejanjeGr=null;
		
		if(metoda.equals("dodajGr")){
			//? dodat neki default id=-1?
			List<Avtor> avtorji = avtorDAO.pridobiVseAvtorje();
			request.setAttribute("avtorji", avtorji);
			List<Podrocje> podrocja= podrocjeDAO.pridobiVsaPodrocja();
			request.setAttribute("podrocja", podrocja);
			List<VrstaGradiva> vrste = vrstaDAO.pridobiVseVrsteGradiva();
			request.setAttribute("vrsteGradiva", vrste);
			List<Zalozba> zalozbe = zalozbaDAO.pridobiVseZalozbe();
			request.setAttribute("zalozbe", zalozbe);
			
			request.setAttribute("metoda","dodajGradivo" );
			stran="/glavnaVsebina/DodajGradivo.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiVse")){
			List<Gradivo> list = new ArrayList<Gradivo>();
			
			try{
				list = gradivoDAO.pridobiVsaGradiva();
				request.setAttribute("gradiva", list);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/Gradiva.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiGradivo")){
			try{
				//sprememba - pridobi gradivo za izpis
				gradivo = gradivoDAO.pridobiGradivo(idGradiva); 
				request.setAttribute("gradivo", gradivo);
				
				//?? vse za avtorje, itd.?
				if(gradivo!=null){
					Podrocje podrocje = podrocjeDAO.pridobiPodrocje(gradivo.getTk_id_podrocja());
					request.setAttribute("podrocje", podrocje);
					VrstaGradiva vrsta = vrstaDAO.pridobiVrsto(gradivo.getTk_id_vrste());
					request.setAttribute("vrsta", vrsta);
					Zalozba zalozba = zalozbaDAO.pridobiZalozbo(gradivo.getTk_id_zalozbe());
					request.setAttribute("zalozba", zalozba);
	
					urejanjeGr = request.getParameter("urejanjeGr");
				}
				else
					stran="/glavnaVsebina/Domov.jsp"; //placeholder
				
			}catch(NullPointerException e){e.getMessage();}
			if(urejanjeGr!=null){
				//?? seznam podrocij itd?
				List<Avtor> avtorji = avtorDAO.pridobiVseAvtorje();
				request.setAttribute("avtorji", avtorji);
				List<Podrocje> podrocja= podrocjeDAO.pridobiVsaPodrocja();
				request.setAttribute("podrocja", podrocja);
				List<VrstaGradiva> vrste = vrstaDAO.pridobiVseVrsteGradiva();
				request.setAttribute("vrsteGradiva", vrste);
				List<Zalozba> zalozbe = zalozbaDAO.pridobiVseZalozbe();
				request.setAttribute("zalozbe", zalozbe);
				
				request.setAttribute("metoda", "urediGradivo");
				stran="/glavnaVsebina/DodajGradivo.jsp"; //placeholder
			}
			else{
				stran="/glavnaVsebina/Gradivo.jsp"; //placeholder
			}
			
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		disp.forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		GradivoDAO gradivoDAO = GradivoDAO.dobiInstanco();
		AvtorDAO avtorDAO = AvtorDAO.dobiInstanco();
		ZalozbaDAO zalozbaDAO = ZalozbaDAO.dobiInstanco();
		GradivoAvtorDAO gradivoAvtorDAO = GradivoAvtorDAO.dobiInstanco();
		PodrocjeDAO podrocjeDAO = PodrocjeDAO.dobiInstanco();
		VrstaGradivaDAO vrstaDAO = VrstaGradivaDAO.dobiInstanco();
		
		String metoda="";
		int idGradiva = -1;
		try{
			metoda = request.getParameter("metoda");
			idGradiva = Integer.parseInt(request.getParameter("idGradiva"));
		}
		catch(Exception e){e.printStackTrace();}
		String stran="";
		Gradivo gradivo = new Gradivo();
		boolean redirect = false;
		
		if(metoda.equals("dodajGradivo")){
			//preverjanja ali je vnaprej doloceno ali ne? vec avtorjev?
			
			//verzija da je vse vnaprej doloceno
			gradivo = new Gradivo();
			gradivo.setNaslov(request.getParameter("naslov"));
			gradivo.setOriginalNaslov(request.getParameter("originalNaslov"));
			gradivo.setJezik(jezik);
			gradivo.setLetoIzida(Integer.parseInt(request.getParameter("leto")));
			gradivo.setISBN(request.getParameter("isbn"));
			gradivo.setOpis(request.getParameter("opis"));
			gradivo.setTk_id_podrocja(tk_id_podrocja);
			gradivo.setTk_id_vrste(tk_id_vrste);
			gradivo.setTk_id_zalozbe(tk_id_zalozbe);
			
			//gradivoDAO.dodajGradivo(gradivo);
			//avtorji?
			
			redirect = true;
			stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+gradivo.getId();
		}
		else if(metoda.equals("urediGradivo")){
			gradivo = new Gradivo();
			gradivo.setNaslov(request.getParameter("naslov"));
			gradivo.setOriginalNaslov(request.getParameter("originalNaslov"));
			gradivo.setJezik(jezik);
			gradivo.setLetoIzida(letoIzida);
			gradivo.setISBN(iSBN);
			gradivo.setOpis(opis);
			gradivo.setTk_id_podrocja(tk_id_podrocja);
			gradivo.setTk_id_vrste(tk_id_vrste);
			gradivo.setTk_id_zalozbe(tk_id_zalozbe);
			
			//gradivoDAO.dodajGradivo(gradivo);
			//avtorji?

			redirect = true;
			stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+gradivo.getId();
			
		}
		else if(metoda.equals("izbrisi")){
			if(gradivoDAO.izbrisiGradivo(idGradiva)){
				redirect = true;
				stran="/knjiznica/GradivoServlet?metoda=pridobiVse";	
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
