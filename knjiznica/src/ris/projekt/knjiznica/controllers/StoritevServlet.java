package ris.projekt.knjiznica.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ris.projekt.knjiznica.beans.*;
import ris.projekt.knjiznica.dao.*;

import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/StoritevServlet")
public class StoritevServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StoritevServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		OsebaDAO osebaDAO = OsebaDAO.dobiInstanco(); //za preverit ce id obstaja
		
		GradivoDAO gradivoDAO = GradivoDAO.dobiInstanco();
		StoritevDAO storitevDAO = StoritevDAO.dobiInstanco();
		Storitev storitev = new Storitev();
		
		int idOsebe = -1;
		String metoda="";

		try{
			metoda = request.getParameter("metoda");
			idOsebe = Integer.parseInt(request.getParameter("idOsebe"));
		}catch(Exception e){e.printStackTrace();}
		
		String stran="";
		boolean redirect = false;
		HttpSession seja = request.getSession();
		
		if (metoda.equals("pridobiIzposojeOsebe")){
			List<Storitev> seznamIzposojOsebe = storitevDAO.pridobiVseIzposojeOsebe(idOsebe);
			request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
			stran="/glavnaVsebina/IzposojeOsebe.jsp"; 
		}
		else if(metoda.equals("vsiClani")){
			List<Oseba> clani = osebaDAO.pridobiOsebe();
			request.setAttribute("clani", clani);
			stran="/glavnaVsebina/preveriUporabnika.jsp"; 
		}
		else if (metoda.equals("nastaviPodaljsanje")){
			//dao - nvoa metoda: pridobiVseIzposoje osebe, ki se niso bile podaljsane!
			List<Storitev> seznamIzposojOsebe = storitevDAO.pridobiVseIzposojeOsebeNepodaljsane(idOsebe);
			request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
			request.setAttribute("metoda", "podaljsaj");
			stran="/glavnaVsebina/UpravljanjeIzposojOsebe.jsp"; //placeholder ^^
		}
		else if (metoda.equals("nastaviVracilo")){
			List<Storitev> seznamIzposojOsebe = storitevDAO.pridobiVseIzposojeOsebe(idOsebe);
			request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
			request.setAttribute("metoda", "vrni");
			stran="/glavnaVsebina/UpravljanjeIzposojOsebe.jsp"; //placeholder ^^
		}
		else if (metoda.equals("pridobiVseIzposojeGradiva")){
			int idGradiva = -1;
			try{
				idGradiva = Integer.parseInt(request.getParameter("idGradiva"));
			}catch(Exception e){}
			//kaj ce id  gradiva ne obstaja? ^^
			if(idGradiva!=-1){
				List<Storitev> seznamIzposojGradiva = storitevDAO.pridobiVseIzposojeGradiva(idGradiva);
				request.setAttribute("seznamIzposoj", seznamIzposojGradiva);
				stran="/glavnaVsebina/IzposojeGradiva.jsp";
			}
			else //neka napaka
				stran="/glavnaVsebina/Domov.jsp"; //placeholdeer
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Storitev storitev = new Storitev();

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
		
		OsebaDAO osebaDAO = OsebaDAO.dobiInstanco(); //za preverjat ce obstaja uporabnik
		GradivoDAO gradivoDAO = GradivoDAO.dobiInstanco();
		CrnaListaDAO crnaDAO = CrnaListaDAO.dobiInstanco();
		StoritevDAO storitevDAO = StoritevDAO.dobiInstanco();
		HttpSession seja = request.getSession();

		if(metoda.equals("nastaviIzposojo")){
			int idKnjiznicarja = -1;
			try{
				idKnjiznicarja = (Integer)seja.getAttribute("ID");
			}catch(Exception e){}
			
			if(idKnjiznicarja!=-1){
				String osebaInput = request.getParameter("osebaInput");
				if(osebaInput!=null && !osebaInput.isEmpty() && !osebaInput.trim().isEmpty()) //? prazn?
					idOsebe = Integer.parseInt(osebaInput);
				else if (!request.getParameter("osebaSelect").equals("nic"))
					idOsebe = Integer.parseInt(request.getParameter("osebaSelect"));
			
				//preveri id osebe ce obstaja 
				
				//preveri, ce ni na crni list
				if(crnaDAO.preveriCeJeClanNaCl(idOsebe))
					stran="/glavnaVsebina/CrnaLista.jsp"; //placeholder
				else{
					List<Gradivo> seznamGradiv = gradivoDAO.pridobiGradivaGledeNaStanje("prosto");
					request.setAttribute("prostaGradiva", seznamGradiv);
					request.setAttribute("idOsebe", idOsebe);
					stran="/glavnaVsebina/Izposoja.jsp"; 
				}

			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp"; 
			}
		}
		else if(metoda.equals("izposoja")){
			int idKnjiznicarja = -1;
			try{
				idKnjiznicarja = (Integer)seja.getAttribute("ID");
			}catch(Exception e){}
			
			if(idKnjiznicarja!=-1){
				//dao: gradivo status updataj na izposojeno!!
				storitev.setDatumIzposoje(new Date()); //preveri kako je s time zone
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, 28); 
				Date rokVrnitve = cal.getTime();
				storitev.setRokVrnitve(rokVrnitve);
				storitev.setZePodaljsano(false);
				storitev.setTk_id_clana(idOsebe);
				storitev.setTk_id_knjiznicarja(idKnjiznicarja);
				
				//za gradiva dve moznosti: 1-iz lista, 2-stevilka gradiva (tu treba preverit ce ni izposojeno)
				List<Integer> idGradiv = new ArrayList<Integer>();
				String[] gradivaSelect = request.getParameterValues("gradivaSelect");
				String[] gradivaInput = request.getParameterValues("gradivaInput");
				
				boolean selectPoln = !gradivaSelect[0].equals("-1");
				if(selectPoln){
					for(int j=0; j<gradivaSelect.length; j++){
						if(!gradivaSelect[j].equals("-1"))
							idGradiv.add(Integer.parseInt(gradivaSelect[j]));
					}
				}
				
				boolean inputPrviPoln = false;
				try{
					String testWhiteSpace = gradivaInput[0].replaceAll("\\s+","");
					inputPrviPoln = testWhiteSpace!= "";
					if(inputPrviPoln){
						//??preverjanje, da gradiva obstajajo +  da niso izposojena
						for(int i=0;i<gradivaInput.length;i++){
							testWhiteSpace = gradivaInput[i].replaceAll("\\s+","");
							if(testWhiteSpace!=""){
								idGradiv.add(Integer.parseInt(gradivaInput[i]));
							}
							
						}
					}
				}catch(NullPointerException e){}
				
				if(!inputPrviPoln && !selectPoln)//neko opozorilo  da nekaj ni blo vneseno
					stran = "/glavnaVsebina/Domov.jsp"; //placeholder
				else {
					for(int k=0;k<idGradiv.size();k++){
						storitev.setTk_id_gradiva(idGradiv.get(k));
						storitevDAO.izposodi(storitev);
					}
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=pridobiIzposojeOsebe&idOsebe="+idOsebe;
				}
				
			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp"; 
			}
			
		}
		else if(metoda.equals("podaljsaj")){
			int idKnjiznicarja = -1;
			try{
				idKnjiznicarja = (Integer)seja.getAttribute("ID");
			}catch(Exception e){}
			
			if(idKnjiznicarja!=-1){
				//podaljsa jih lahko vec hkrati, preverjanje ce so idji (clan, gradivo),

				//dao: ze podaljasno updataj!
				String[] gradivaSelect = request.getParameterValues("gradivaSelect");
				//hidden input za datume
				String[] gradivaDatumi = request.getParameterValues("gradivaDatumi");

				for(int j=0; j<gradivaSelect.length; j++){
					if(!gradivaSelect[j].equals("-1")){
						try{
							String date = gradivaDatumi[j]; 
							SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");//nisem ziher kak v bazi?
							Calendar c = Calendar.getInstance();
							c.setTime(sdf.parse(date));
							c.add(Calendar.DATE, 28);  
							Date rokVrnitve = c.getTime();  
	
							storitev.setId(Integer.parseInt(gradivaSelect[j]));
							storitev.setRokVrnitve(rokVrnitve);
							storitev.setZePodaljsano(true);
							storitevDAO.podaljsaj(storitev);
						}catch(Exception e){}
					}
				}
				redirect = true;
				stran="/knjiznica/StoritevServlet?metoda=pridobiIzposojeOsebe&idOsebe="+idOsebe;
				
			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp"; 
			}
			
		}
		else if(metoda.equals("vrni")){
			//tu tut preverjanje, ce je na crni listi? al to rocno izbrisi?
			int idKnjiznicarja = -1;
			try{
				idKnjiznicarja = (Integer)seja.getAttribute("ID");
			}catch(Exception e){}
			
			if(idKnjiznicarja!=-1){
				//vrne jih lahko vec hkrati, preverjanje ce so idji (clan, gradivo),

				//dao: gradivo status updataj na prosto!!
				String[] gradivaSelect = request.getParameterValues("gradivaSelect");

				for(int j=0; j<gradivaSelect.length; j++){
					if(!gradivaSelect[j].equals("-1")){
						storitev.setId(Integer.parseInt(gradivaSelect[j]));
						storitev.setDatumVrnitve(new Date());
						storitevDAO.vrni(storitev);
					}
				}
				redirect = true;
				stran="/knjiznica/StoritevServlet?metoda=pridobiIzposojeOsebe&idOsebe="+idOsebe;	
			}
			else{
				seja.setAttribute("Prijava",false);
				stran="/glavnaVsebina/Login.jsp"; 
			}
			
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
			
	}

}
