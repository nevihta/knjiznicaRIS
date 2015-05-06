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
		
		int idOsebe = -1;
		String metoda="";

		try{
			metoda = request.getParameter("metoda");
			idOsebe = Integer.parseInt(request.getParameter("idOsebe"));
		}catch(Exception e){e.printStackTrace();}
		
		String stran="";
		boolean redirect = false;
		request.setAttribute("meni", "izposoja");
		
		if(metoda.equals("vsiClani")){
			String neObstaja = null;
			List<Oseba> clani = osebaDAO.pridobiOsebe();
			request.setAttribute("clani", clani);
			neObstaja = request.getParameter("neObstaja");
			if(neObstaja!=null)
				request.setAttribute("neObstaja", true);
			
			stran="/glavnaVsebina/preveriUporabnika.jsp"; 
		}
		else if (metoda.equals("nastaviPodaljsanje")){
			if(idOsebe!=-1){
				List<StoritevZaIzpis> seznamIzposojOsebe = storitevDAO.pridobiVseIzposojeOsebeNepodaljsane(idOsebe);
				request.setAttribute("seznamIzposoj", seznamIzposojOsebe);
				request.setAttribute("metoda", "podaljsaj");
				request.setAttribute("idO", idOsebe);

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
			request.setAttribute("idO", idOsebe);
			stran="/glavnaVsebina/UpravljanjeIzposojOsebe.jsp"; //placeholder ^^
			}
			else{
				redirect=true;
				stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje"; //placeholder ^
			}

		}
		else if (metoda.equals("pridobiVseAktualneIzposoje")){
			String filter="false";
			List<StoritevZaIzpis> seznamIzposojGradiva=null ;
			boolean pretekle=false;
			boolean fil=false;

		
			try{
				 filter=request.getParameter("filter");
			}
			catch(Exception e){e.printStackTrace();}
					
			try{

				if(filter==null){
					seznamIzposojGradiva= storitevDAO.pridobiVseAktualneIzposoje();
				}
				else if(filter.equals("o")){
					String osebaImeFilter=request.getParameter("imeOsebeFilter");
					String osebaPriimekFilter=request.getParameter("priimekOsebeFilter");
					seznamIzposojGradiva=storitevDAO.pridobiFiltriraneIzposojeO(osebaImeFilter, osebaPriimekFilter);
				}
				else if(filter.equals("i"))
				{
					String idfilter=request.getParameter("id");
					seznamIzposojGradiva=storitevDAO.pridobiVseAktualneIzposojeOsebe(Integer.parseInt(idfilter));
					fil=true;
				}
				else if(filter.equals("s")){
					String statusFilter=request.getParameter("statusFilter");
					if(statusFilter.equals("rok"))
						seznamIzposojGradiva=storitevDAO.pridobiStoritvePrekoRoka();
					else if ((statusFilter.equals("akt"))||(statusFilter.equals("pret")))	
						seznamIzposojGradiva=storitevDAO.pridobiVseIzposojeGledeNaStatus(statusFilter);
					else
						seznamIzposojGradiva= storitevDAO.pridobiVseAktualneIzposoje(); //èe se najde šaljivec, pa bo v get šel vrednost filtra spreminjat
				if(statusFilter.equals("pret"))
					pretekle= true;
				}
				
				else
					seznamIzposojGradiva= storitevDAO.pridobiVseAktualneIzposoje(); //èe se najde šaljivec, pa bo v get šel vrednost filtra spreminjat

				request.setAttribute("filter", fil);
				request.setAttribute("pretekle", pretekle);
				request.setAttribute("gradiva", seznamIzposojGradiva);
			}
			catch(NullPointerException e){
			}
			List<Oseba> osebeKiImajoSposojenoGradivo=osebaDAO.pridobiOsebeKiImajoSposojenoGradivo();
			request.setAttribute("seznamIzposoj", seznamIzposojGradiva);
			request.setAttribute("osebe", osebeKiImajoSposojenoGradivo);
			//nastavi osebe, ki imajo aktualne izposoje
			
			stran="/glavnaVsebina/Izposoje.jsp";
		}//zaradi redirecta
		else if(metoda.equals("nastaviIzposojo")){
			String niIzbrano = null;
			String niProsto = null;
			List<Gradivo> seznamGradiv = gradivoDAO.pridobiGradivaGledeNaStanje("prosto");
			request.setAttribute("prostaGradiva", seznamGradiv);
			request.setAttribute("idOsebe", idOsebe);
			//opozorilo
			niIzbrano = request.getParameter("niIzbrano");
			if(niIzbrano!=null)
				request.setAttribute("niIzbrano", true);
			//ce gradivo ni prosto
			niProsto = request.getParameter("niProsto");
			if(niProsto!=null){
				request.setAttribute("niProsto", true);
				request.setAttribute("niProstoID", request.getParameter("idGradiva"));
			}
			stran="/glavnaVsebina/Izposoja.jsp"; 
				
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
		request.setAttribute("meni", "izposoja");
		
		if(metoda.equals("nastaviIzposojo")){
			int idKnjiznicarja = -1;
			try{
				idKnjiznicarja = (Integer)seja.getAttribute("ID");
			}catch(Exception e){}
			
			if(idKnjiznicarja!=-1){
				String osebaInput = request.getParameter("osebaInput");
				if(osebaInput!=null && !osebaInput.isEmpty() && !osebaInput.trim().isEmpty()) //? prazn?
					idOsebe = Integer.parseInt(osebaInput)-1000;
				else if (!request.getParameter("osebaSelect").equals("nic"))
					idOsebe = Integer.parseInt(request.getParameter("osebaSelect"));
			
				//preveri id osebe ce obstaja 
				if(osebaDAO.pridobiOsebo(idOsebe).getIme()!=null){
					//preveri, ce ni na crni list
					if(crnaDAO.preveriCeJeClanNaCl(idOsebe)){
						redirect = true;
						stran="/knjiznica/CrnaListaServlet?metoda=pridobiVse&filter=akt";	
					}
					else{
						List<Gradivo> seznamGradiv = gradivoDAO.pridobiGradivaGledeNaStanje("prosto");
						request.setAttribute("prostaGradiva", seznamGradiv);
						request.setAttribute("idOsebe", idOsebe);
						stran="/glavnaVsebina/Izposoja.jsp"; 
					}
				}
				else{ //oseba ne obstaja
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=vsiClani&neObstaja=true";
				}

			}
			else{
				request.setAttribute("meni", "domov");
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
				Set<Integer> idGradiv = new HashSet<Integer>();
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
				int id = -1;
				int niProsto = -1;
				try{
					String testWhiteSpace = gradivaInput[0].replaceAll("\\s+","");
					inputPrviPoln = testWhiteSpace!= "";
					if(inputPrviPoln){
						//??preverjanje, da gradiva obstajajo +  da niso izposojena
						for(int i=0;i<gradivaInput.length;i++){
							testWhiteSpace = gradivaInput[i].replaceAll("\\s+","");
							if(testWhiteSpace!=""){
								id = Integer.parseInt(gradivaInput[i])-1000;
								if(gradivoDAO.preveriZaIzposojo(id))
									idGradiv.add(id);
								else{
									System.out.println("ni prost");
									niProsto = id; 
									break;
								}
							}
							
						}
					}
				}catch(NullPointerException e){}
				
				if(!inputPrviPoln && !selectPoln){ //opozorilo
					redirect = true;
					stran = "/knjiznica/StoritevServlet?metoda=nastaviIzposojo&niIzbrano=true&idOsebe="+idOsebe; //placeholder
				}
				else if(niProsto!=-1){
					//opozorilo da vnesenega gradiva ni mogoce izposoditi
					redirect = true;
					stran = "/knjiznica/StoritevServlet?metoda=nastaviIzposojo&niProsto=true&idOsebe="+idOsebe+"&idGradiva="+(niProsto+1000); //placeholder
				
				}
				else {

					List<Storitev> seznamStoritev = new ArrayList<Storitev>();
					for(Integer k : idGradiv){
						storitev = new Storitev(datumIzposoje, null, rokVrnitve, false, idOsebe, k, idKnjiznicarja);
						seznamStoritev.add(storitev);
					}
					storitevDAO.izposodi(seznamStoritev);
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje&filter=i&id="+idOsebe;
				}
				
			}
			else{
				request.setAttribute("meni", "domov");
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
				
				if(gradivaSelect!=null){
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
					stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje&filter=i&id="+idOsebe;
				}
				else {
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje";	
				}
			}
			else{
				request.setAttribute("meni", "domov");
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
				if(gradivaSelect!=null){
					for(int j=0; j<gradivaSelect.length; j++){
	
						if(!gradivaSelect[j].equals("-1")){
							storitev = new Storitev();
							storitev.setId(Integer.parseInt(gradivaSelect[j]));
							storitev.setDatumVrnitve(new Date());
							seznamStoritev.add(storitev);
						}
					}
					storitevDAO.vrni(seznamStoritev);
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje&filter=i&id="+idOsebe;
				}
				else {
					redirect = true;
					stran="/knjiznica/StoritevServlet?metoda=pridobiVseAktualneIzposoje";	
				}
			}
			else{
				request.setAttribute("meni", "domov");
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
