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
			List<StoritevZaIzpis> seznamIzposojOsebe = storitevDAO.pridobiVseAktualneIzposojeOsebe(idOsebe);
			request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
			stran="/glavnaVsebina/IzposojeOsebe.jsp"; 
		}
		else if(metoda.equals("vsiClani")){
			List<Oseba> clani = osebaDAO.pridobiOsebe();
			request.setAttribute("clani", clani);
			stran="/glavnaVsebina/preveriUporabnika.jsp"; 
		}
		else if (metoda.equals("nastaviPodaljsanje")){
			if(idOsebe!=-1){
				List<StoritevZaIzpis> seznamIzposojOsebe = storitevDAO.pridobiVseIzposojeOsebeNepodaljsane(idOsebe);
				request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
				request.setAttribute("metoda", "podaljsaj");
				stran="/glavnaVsebina/UpravljanjeIzposojOsebe.jsp"; //placeholder ^^
			}
			else{
				redirect=true;
				stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje"; //placeholder ^
			}

		}
		else if (metoda.equals("nastaviVracilo")){
			if(idOsebe!=-1){
			List<StoritevZaIzpis> seznamIzposojOsebe = storitevDAO.pridobiVseAktualneIzposojeOsebe(idOsebe);
			request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
			request.setAttribute("metoda", "vrni");
			stran="/glavnaVsebina/UpravljanjeIzposojOsebe.jsp"; //placeholder ^^
			}
			else{
				redirect=true;
				stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje"; //placeholder ^
			}

		}
		else if (metoda.equals("pridobiVseAktualneIzposoje")){
			List<StoritevZaIzpis> seznamIzposojGradiva = storitevDAO.pridobiVseAktualneIzposoje();
			List<Oseba> osebeKiImajoSposojenoGradivo=osebaDAO.pridobiOsebeKiImajoSposojenoGradivo();
			request.setAttribute("seznamIzposoj", seznamIzposojGradiva);
			request.setAttribute("osebe", osebeKiImajoSposojenoGradivo);
			//nastavi osebe, ki imajo aktualne izposoje
			
			stran="/glavnaVsebina/Izposoje.jsp";
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
		System.out.println(metoda);
		
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
				if(osebaDAO.pridobiOsebo(idOsebe).getIme()!=null){
					//preveri, ce ni na crni list
					if(crnaDAO.preveriCeJeClanNaCl(idOsebe)){
						//rabimo osebe!
						List<ZapisNaCl> crnaLista = crnaDAO.pridobiSeznamClanovNaCl();
						request.setAttribute("crnaLista", crnaLista);
						stran="/glavnaVsebina/CrnaLista.jsp"; //placeholder
					}
					else{
						List<Gradivo> seznamGradiv = gradivoDAO.pridobiGradivaGledeNaStanje("prosto");
						request.setAttribute("prostaGradiva", seznamGradiv);
						request.setAttribute("idOsebe", idOsebe);
						stran="/glavnaVsebina/Izposoja.jsp"; 
					}
				}
				else{ //oseba ne obstaja
					//nastavi v jsp obvestilo, preuredi vsiClani^^
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=vsiClani&neObstaja=true";
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
				Date datumIzposoje = new Date(); //preveri kako je s time zone
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, 28); 
				Date rokVrnitve = cal.getTime();

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

					List<Storitev> seznamStoritev = new ArrayList<Storitev>();
					for(int k=0;k<idGradiv.size();k++){
						storitev = new Storitev(datumIzposoje, null, rokVrnitve, false, idOsebe, idGradiv.get(k), idKnjiznicarja);
						seznamStoritev.add(storitev);
					}
					storitevDAO.izposodi(seznamStoritev);
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

				String[] gradivaSelect = request.getParameterValues("gradivaSelect");
				List<Storitev> seznamStoritev = new ArrayList<Storitev>();
				String id;
				String datum;
				
				for(int j=0; j<gradivaSelect.length; j++){
					String[] razdeli=gradivaSelect[j].split("\\*");
					id=razdeli[0];
					datum=razdeli[1];
					if(!id.equals("-1")){
						try{
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//nisem ziher kak v bazi?
							Calendar c = Calendar.getInstance();
							c.setTime(sdf.parse(datum));
							c.add(Calendar.DATE, 28);  
							Date rokVrnitve = c.getTime(); 
							
							storitev = new Storitev();
							storitev.setId(Integer.parseInt(id));
							storitev.setRokVrnitve(rokVrnitve);
							seznamStoritev.add(storitev);
						}catch(Exception e){}
					}
				}
				storitevDAO.podaljsaj(seznamStoritev);
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

				String[] gradivaSelect = request.getParameterValues("gradivaSelect");
				List<Storitev> seznamStoritev = new ArrayList<Storitev>();
				
				for(int j=0; j<gradivaSelect.length; j++){
					System.out.println(gradivaSelect[j]);

					if(!gradivaSelect[j].equals("-1")){
						storitev = new Storitev();
						storitev.setId(Integer.parseInt(gradivaSelect[j]));
						storitev.setDatumVrnitve(new Date());
						seznamStoritev.add(storitev);
					}
				}
				storitevDAO.vrni(seznamStoritev);
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
