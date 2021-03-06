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
		StoritevDAO storitevDAO=StoritevDAO.dobiInstanco();

		
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
		boolean redirect = false;
		request.setAttribute("meni", "gradivo");
		
		if(metoda.equals("dodajGr")){
			List<Avtor> avtorji = avtorDAO.pridobiVseAvtorje();
			request.setAttribute("vsiAvtorji", avtorji);
			List<Podrocje> podrocja= podrocjeDAO.pridobiVsaPodrocja();
			request.setAttribute("podrocja", podrocja);
			List<VrstaGradiva> vrste = vrstaDAO.pridobiVseVrsteGradiva();
			request.setAttribute("vrsteGradiva", vrste);
			List<Zalozba> zalozbe = zalozbaDAO.pridobiVseZalozbe();
			request.setAttribute("zalozbe", zalozbe);
			Jezik[] jeziki = Jezik.values();
			request.setAttribute("jeziki", jeziki);
			
			String niAvtorjev = null;
			String niOstalo = null;
			niAvtorjev = request.getParameter("niAvtorjev");
			if(niAvtorjev!=null)
				request.setAttribute("niAvtorjev", true);
			niOstalo= request.getParameter("niOstalo");
			if(niOstalo!=null)
				request.setAttribute("niOstalo", true);
			
			request.setAttribute("metoda","dodajGradivo" );
			stran="/glavnaVsebina/DodajGradivo.jsp"; 
		}
		else if(metoda.equals("pridobiVse")){

			List<GradivoZaIzpis> list = new ArrayList<GradivoZaIzpis>();
			String filter="false";
			
			try{
			 filter=request.getParameter("filter");
			}
			catch(Exception e){e.printStackTrace();}

			List<Podrocje> podrocja= podrocjeDAO.pridobiVsaPodrocja();
			request.setAttribute("podrocja", podrocja);
			List<VrstaGradiva> vrste = vrstaDAO.pridobiVseVrsteGradiva();
			request.setAttribute("vrsteGradiva", vrste);
			List<Zalozba> zalozbe = zalozbaDAO.pridobiVseZalozbe();
			request.setAttribute("zalozbe", zalozbe);
			Jezik[] jeziki = Jezik.values();
			request.setAttribute("jeziki", jeziki);
			
			try{

				if(filter==null){
					list = gradivoDAO.pridobiVsaGradivaZaIzpis();
				}
				else if(filter.equals("a")){
					System.out.println("v filtri je");
					String avtorImeFilter=request.getParameter("imeAvtorjaFilter");
					String avtorPriimekFilter=request.getParameter("priimekAvtorjaFilter");
					list=gradivoDAO.pridobiFiltriranaGradivaA(avtorImeFilter, avtorPriimekFilter);
				}
				else if(filter.equals("l"))
				{
					String naslovFilter=request.getParameter("naslovFilter");
					String jezikFilter=request.getParameter("jezikFilter");
					String letoIzidaFilter=request.getParameter("letoFilter");
					int vrstaFilter=Integer.parseInt(request.getParameter("vrstaFilter"));
					int podrocjeFilter=Integer.parseInt(request.getParameter("podrocjeFilter"));
					int zalozbaFilter=Integer.parseInt(request.getParameter("zalozbaFilter"));
					list=gradivoDAO.pridobiFiltriranaGradivaL(naslovFilter, jezikFilter, letoIzidaFilter, vrstaFilter, podrocjeFilter, zalozbaFilter);
				}
				else if(filter.equals("s")){
					String statusFilter=request.getParameter("statusFilter");
					list=gradivoDAO.pridobiFiltriranaGradivaS(statusFilter);
				}
				else
					list = gradivoDAO.pridobiVsaGradivaZaIzpis(); //�e se najde �aljivec, pa bo v get �el vrednost filtra spreminjat

				request.setAttribute("gradiva", list);
			}
			catch(NullPointerException e){
			}
			
			stran="/glavnaVsebina/Gradiva.jsp"; 
		}
		else if(metoda.equals("pridobiGradivo")){
			String neizbrisan=null;
			try{
				//sprememba - pridobi gradivo za izpis
				gradivo = gradivoDAO.pridobiGradivo(idGradiva); 
				request.setAttribute("gradivo", gradivo);
				
				if(gradivo!=null){
					Podrocje podrocje = podrocjeDAO.pridobiPodrocje(gradivo.getTk_id_podrocja());
					request.setAttribute("podrocje", podrocje);
					VrstaGradiva vrsta = vrstaDAO.pridobiVrsto(gradivo.getTk_id_vrste());
					request.setAttribute("vrsta", vrsta);
					Zalozba zalozba = zalozbaDAO.pridobiZalozbo(gradivo.getTk_id_zalozbe());
					request.setAttribute("zalozba", zalozba);
					//avtorji
					List<Integer> IDavtorji= gradivoAvtorDAO.pridobiAvtorjeGradiva(gradivo.getId());
					List<Avtor> avtorji = new ArrayList<Avtor>();
					for(int i=0; i<IDavtorji.size(); i++){
						avtorji.add(avtorDAO.pridobiAvtorja(IDavtorji.get(i)));
					}
					request.setAttribute("avtorji", avtorji);
					
					neizbrisan = request.getParameter("neizbrisan");
					if(neizbrisan!=null)
						request.setAttribute("neizbrisan", true);
					
					urejanjeGr = request.getParameter("urejanjeGr");
				}
				else{
					redirect = true;
					stran="/knjiznica/OsebaServlet?metoda=domov"; 
				}
				
			}catch(NullPointerException e){e.getMessage();}
			if(urejanjeGr!=null){
				List<Avtor> avtorji = avtorDAO.pridobiVseAvtorje();
				request.setAttribute("vsiAvtorji", avtorji);
				List<Podrocje> podrocja= podrocjeDAO.pridobiVsaPodrocja();
				request.setAttribute("podrocja", podrocja);
				List<VrstaGradiva> vrste = vrstaDAO.pridobiVseVrsteGradiva();
				request.setAttribute("vrsteGradiva", vrste);
				List<Zalozba> zalozbe = zalozbaDAO.pridobiVseZalozbe();
				request.setAttribute("zalozbe", zalozbe);
				Jezik[] jeziki = Jezik.values();
				request.setAttribute("jeziki", jeziki);

				String niAvtorjev = null;
				String niOstalo = null;
				niAvtorjev = request.getParameter("niAvtorjev");
				if(niAvtorjev!=null)
					request.setAttribute("niAvtorjev", true);
				niOstalo= request.getParameter("niOstalo");
				if(niOstalo!=null)
					request.setAttribute("niOstalo", true);
				
				request.setAttribute("metoda", "urediGradivo");
				stran="/glavnaVsebina/DodajGradivo.jsp"; 
			}
			else{
				stran="/glavnaVsebina/Gradivo.jsp"; 
			}
			
		}
		else if(metoda.equals("izbrisi")){
			if(gradivoDAO.izbrisiGradivo(idGradiva)){
				gradivoAvtorDAO.izbrisiVseAvtorGradivo(idGradiva);
				redirect = true;
				stran="/knjiznica/GradivoServlet?metoda=pridobiVse";	
			}
			else //neko opozorilo da ne more zbrisat
				redirect = true;
				stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+idGradiva+"&neizbrisan=true";
		}
		else if(metoda.equals("pridobiZgG")){
			ArrayList<StoritevZaIzpis> szi=storitevDAO.pridobiVseIzposojeGradiva(idGradiva);
			System.out.println(szi.size());
			Gradivo g=gradivoDAO.pridobiGradivo(idGradiva);
			request.setAttribute("zgodovinaG", szi);
			request.setAttribute("zgo", "gradivo");
			request.setAttribute("g", g);
		
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
		request.setAttribute("meni", "gradivo");
		
		if(metoda.equals("dodajGradivo")){
			gradivo = new Gradivo();
			gradivo.setNaslov(request.getParameter("naslov"));
			gradivo.setOriginalNaslov(request.getParameter("originalNaslov"));
			gradivo.setLetoIzida(Integer.parseInt(request.getParameter("leto")));
			gradivo.setISBN(request.getParameter("isbn"));
			gradivo.setOpis(request.getParameter("opis"));
			gradivo.setJezik(Jezik.valueOf(request.getParameter("jezik")));
			
			String podrocjeInput = request.getParameter("podrocjeInput");
			int idPodrocja = -1;
			if(podrocjeInput!=null && !podrocjeInput.isEmpty() && !podrocjeInput.trim().isEmpty()) 
				idPodrocja = podrocjeDAO.dodajPodrocje(podrocjeInput);
			else if (!request.getParameter("podrocjeSelect").equals("nic"))
				idPodrocja = Integer.parseInt(request.getParameter("podrocjeSelect"));
			gradivo.setTk_id_podrocja(idPodrocja);
			
			String vrstaInput = request.getParameter("vrstaInput");
			int idVrste = -1;
			if(vrstaInput!=null && !vrstaInput.isEmpty() && !vrstaInput.trim().isEmpty()) 
				idVrste = vrstaDAO.dodajVrstoGradiva(vrstaInput);
			else if (!request.getParameter("vrstaSelect").equals("nic"))
				idVrste = Integer.parseInt(request.getParameter("vrstaSelect"));
			gradivo.setTk_id_vrste(idVrste); 

			String zalozbaInput = request.getParameter("zalozbaInput");
			int idZalozbe = -1;
			if(zalozbaInput!=null && !zalozbaInput.isEmpty() && !zalozbaInput.trim().isEmpty()) 
				idZalozbe = zalozbaDAO.dodajZalozbo(zalozbaInput);
			else if (!request.getParameter("zalozbaSelect").equals("nic"))
				idZalozbe = Integer.parseInt(request.getParameter("zalozbaSelect"));
			gradivo.setTk_id_zalozbe(idZalozbe);

			if(idPodrocja!=-1 && idVrste!=-1 && idZalozbe!=-1){
				List<Integer> idAvtorjev = new ArrayList<Integer>();
				Avtor avtor = new Avtor();
				String[] avtorjiSelect = request.getParameterValues("avtorjiSelect");
				String[] avtorjiImeInput = request.getParameterValues("avtorjiImeInput");
				String[] avtorjiPriimekInput = request.getParameterValues("avtorjiPriimekInput");
				
				boolean selectPoln = !avtorjiSelect[0].equals("-1");
				if(selectPoln){
					for(int j=0; j<avtorjiSelect.length; j++){
						if(!avtorjiSelect[j].equals("-1"))
							idAvtorjev.add(Integer.parseInt(avtorjiSelect[j]));
					}
				}
				
				boolean prviPoln = false;
				try{
					String testWhiteSpaceIme = avtorjiImeInput[0].replaceAll("\\s+","");
					String testWhiteSpacePriimek = avtorjiPriimekInput[0].replaceAll("\\s+","");
					prviPoln = testWhiteSpaceIme != "" && testWhiteSpacePriimek != "";
					if(prviPoln){
						System.out.println("prvi poln");
						//for zanka pa skozi avtorje, + preverjanja povsod da ni prazno
						for(int i=0;i<avtorjiImeInput.length;i++){
							testWhiteSpaceIme = avtorjiImeInput[i].replaceAll("\\s+","");
							testWhiteSpacePriimek = avtorjiPriimekInput[i].replaceAll("\\s+","");
							if(testWhiteSpaceIme!="" && testWhiteSpacePriimek!=""){
								avtor.setIme(avtorjiImeInput[i]);
								avtor.setPriimek(avtorjiPriimekInput[i]);
								idAvtorjev.add(avtorDAO.dodajAvtorja(avtor));
							}
							
						}
					}
				}catch(NullPointerException e){}
				
				if(!prviPoln && !selectPoln){
					redirect = true;
					stran="/knjiznica/GradivoServlet?metoda=dodajGr&niAvtorjev=true";
				}
				else {
					idGradiva = gradivoDAO.dodajGradivo(gradivo);
					gradivoAvtorDAO.dodajAvtorGradivo(idAvtorjev, idGradiva);
					redirect = true;
					stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+idGradiva;
				}
			}
			else{
				redirect = true;
				stran="/knjiznica/GradivoServlet?metoda=dodajGr&niOstalo=true";
			}
		}
		else if(metoda.equals("urediGradivo") && idGradiva!=-1){
			gradivo = new Gradivo();
			gradivo.setId(idGradiva);
			gradivo.setNaslov(request.getParameter("naslov"));
			gradivo.setOriginalNaslov(request.getParameter("originalNaslov"));
			gradivo.setLetoIzida(Integer.parseInt(request.getParameter("leto")));
			gradivo.setISBN(request.getParameter("isbn"));
			gradivo.setOpis(request.getParameter("opis"));
			gradivo.setJezik(Jezik.valueOf(request.getParameter("jezik")));
			
			String podrocjeInput = request.getParameter("podrocjeInput");
			int idPodrocja = -1;
			if(podrocjeInput!=null && !podrocjeInput.isEmpty()) 
				idPodrocja = podrocjeDAO.dodajPodrocje(podrocjeInput);
			else if (!request.getParameter("podrocjeSelect").equals("nic"))
				idPodrocja = Integer.parseInt(request.getParameter("podrocjeSelect"));
			gradivo.setTk_id_podrocja(idPodrocja);
			
			String vrstaInput = request.getParameter("vrstaInput");
			int idVrste = -1;
			if(vrstaInput!=null && !vrstaInput.isEmpty()) 
				idVrste = vrstaDAO.dodajVrstoGradiva(vrstaInput);
			else if (!request.getParameter("vrstaSelect").equals("nic"))
				idVrste = Integer.parseInt(request.getParameter("vrstaSelect"));
			gradivo.setTk_id_vrste(idVrste);

			String zalozbaInput = request.getParameter("zalozbaInput");
			int idZalozbe = -1;
			if(zalozbaInput!=null && !zalozbaInput.isEmpty())
				idZalozbe = zalozbaDAO.dodajZalozbo(zalozbaInput);
			else if (!request.getParameter("zalozbaSelect").equals("nic"))
				idZalozbe = Integer.parseInt(request.getParameter("zalozbaSelect"));
			gradivo.setTk_id_zalozbe(idZalozbe);
			
			if(idPodrocja!=-1 && idVrste!=-1 && idZalozbe!=-1){
				List<Integer> idAvtorjev = new ArrayList<Integer>();
				Avtor avtor = new Avtor();
				String[] avtorjiSelect = request.getParameterValues("avtorjiSelect");
				String[] avtorjiImeInput = request.getParameterValues("avtorjiImeInput");
				String[] avtorjiPriimekInput = request.getParameterValues("avtorjiPriimekInput");
				
				boolean selectPoln = !avtorjiSelect[0].equals("-1");
				if(selectPoln){
					for(int j=0; j<avtorjiSelect.length; j++){
						if(!avtorjiSelect[j].equals("-1"))
							idAvtorjev.add(Integer.parseInt(avtorjiSelect[j]));
					}
				}
				
				boolean prviPoln = false;
				try{
					String testWhiteSpaceIme = avtorjiImeInput[0].replaceAll("\\s+","");
					String testWhiteSpacePriimek = avtorjiPriimekInput[0].replaceAll("\\s+","");
					prviPoln = testWhiteSpaceIme != "" && testWhiteSpacePriimek != "";
					if(prviPoln){
						System.out.println("prvi poln");
						//for zanka pa skozi avtorje, + preverjanja povsod da ni prazno
						for(int i=0;i<avtorjiImeInput.length;i++){
							testWhiteSpaceIme = avtorjiImeInput[i].replaceAll("\\s+","");
							testWhiteSpacePriimek = avtorjiPriimekInput[i].replaceAll("\\s+","");
							if(testWhiteSpaceIme!="" && testWhiteSpacePriimek!=""){
								avtor.setIme(avtorjiImeInput[i]);
								avtor.setPriimek(avtorjiPriimekInput[i]);
								idAvtorjev.add(avtorDAO.dodajAvtorja(avtor));
							}
							
						}
					}
				}catch(NullPointerException e){}
				
				if(!prviPoln && !selectPoln){
					redirect = true;
					stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&urejanjeGr=true&niAvtorjev=true&idGradiva="+idGradiva;
				}
				else {
					//prvo izbris vseh vmesnih za avtorje
					gradivoAvtorDAO.izbrisiVseAvtorGradivo(idGradiva);
					gradivoDAO.urediGradivo(gradivo);
					gradivoAvtorDAO.dodajAvtorGradivo(idAvtorjev, idGradiva);
					redirect = true;
					stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+idGradiva;
				}
			}
			else{
				redirect = true;
				stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&urejanjeGr=true&niOstalo=true&idGradiva="+idGradiva;
			}
			
		}
		
		if(stran==""){
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
