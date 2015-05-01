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
			Jezik[] jeziki = Jezik.values();
			request.setAttribute("jeziki", jeziki);
			
			request.setAttribute("metoda","dodajGradivo" );
			stran="/glavnaVsebina/DodajGradivo.jsp"; //placeholder
		}
		else if(metoda.equals("pridobiVse")){
			List<GradivoZaIzpis> list = new ArrayList<GradivoZaIzpis>();
			
			try{
				//AVTORJI!!!!!!
				list = gradivoDAO.pridobiVsaGradivaZaIzpis();
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
					//avtorji
					List<Integer> IDavtorji= gradivoAvtorDAO.pridobiAvtorjeGradiva(gradivo.getId());
					List<Avtor> avtorji = new ArrayList<Avtor>();
					for(int i=0; i<IDavtorji.size(); i++){
						avtorji.add(avtorDAO.pridobiAvtorja(IDavtorji.get(i)));
					}
					request.setAttribute("avtorji", avtorji);
					
					urejanjeGr = request.getParameter("urejanjeGr");
				}
				else
					stran="/glavnaVsebina/Domov.jsp"; //placeholder
				
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
			gradivo.setLetoIzida(Integer.parseInt(request.getParameter("leto")));
			gradivo.setISBN(request.getParameter("isbn"));
			gradivo.setOpis(request.getParameter("opis"));
			gradivo.setJezik(Jezik.valueOf(request.getParameter("jezik")));
			
			String podrocjeInput = request.getParameter("podrocjeInput");
			int idPodrocja = -1;
			if(podrocjeInput!=null && !podrocjeInput.isEmpty()) //? prazn?
				idPodrocja = podrocjeDAO.dodajPodrocje(podrocjeInput);
			else if (!request.getParameter("podrocjeSelect").equals("nic"))
				idPodrocja = Integer.parseInt(request.getParameter("podrocjeSelect"));
			gradivo.setTk_id_podrocja(idPodrocja);
			
			String vrstaInput = request.getParameter("vrstaInput");
			int idVrste = -1;
			if(vrstaInput!=null && !vrstaInput.isEmpty()) //? prazn?
				idVrste = vrstaDAO.dodajVrstoGradiva(vrstaInput);
			else if (!request.getParameter("vrstaSelect").equals("nic"))
				idVrste = Integer.parseInt(request.getParameter("vrstaSelect"));
			gradivo.setTk_id_vrste(idVrste);

			String zalozbaInput = request.getParameter("zalozbaInput");
			int idZalozbe = -1;
			if(zalozbaInput!=null && !zalozbaInput.isEmpty()) //? prazn?
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
				
				String testWhiteSpaceIme = avtorjiImeInput[0].replaceAll("\\s+","");
				String testWhiteSpacePriimek = avtorjiPriimekInput[0].replaceAll("\\s+","");
				boolean prviPoln = testWhiteSpaceIme != "" && testWhiteSpacePriimek != "";
				
				if(prviPoln){
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
				boolean selectPoln = !avtorjiSelect[0].equals("-1");
				if(selectPoln){
					for(int j=0; j<avtorjiSelect.length; j++){
						if(!avtorjiSelect[j].equals("-1"))
							idAvtorjev.add(Integer.parseInt(avtorjiSelect[j]));
					}
				}
				
				if(!prviPoln && !selectPoln)//neko opozorilo  da nekaj ni blo vneseno
					stran = "/glavnaVsebina/Domov.jsp"; //placeholder
				else {
					idGradiva = gradivoDAO.dodajGradivo(gradivo);
					gradivoAvtorDAO.dodajAvtorGradivo(seznamAvtorjev, idGradiva);
					redirect = true;
					stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+idGradiva;
				}
			}
			else{//neko opozorilo  da nekaj ni blo vneseno
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
			}
		}
		else if(metoda.equals("urediGradivo") && idGradiva!=-1){
			gradivo = new Gradivo();
			gradivo.setId(idGradiva);
			gradivo.setNaslov(request.getParameter("naslov"));
			gradivo.setOriginalNaslov(request.getParameter("originalNaslov"));
			gradivo.setLetoIzida(Integer.parseInt(request.getParameter("leto")));
			gradivo.setISBN(request.getParameter("isbn"));
			gradivo.setJezik(Jezik.valueOf(request.getParameter("jezik")));
			
			String podrocjeInput = request.getParameter("podrocjeInput");
			int idPodrocja = -1;
			if(podrocjeInput!=null && !podrocjeInput.isEmpty()) //? prazn?
				idPodrocja = podrocjeDAO.dodajPodrocje(podrocjeInput);
			else if (!request.getParameter("podrocjeSelect").equals("nic"))
				idPodrocja = Integer.parseInt(request.getParameter("podrocjeSelect"));
			gradivo.setTk_id_podrocja(idPodrocja);
			
			String vrstaInput = request.getParameter("vrstaInput");
			int idVrste = -1;
			if(vrstaInput!=null && !vrstaInput.isEmpty()) //? prazn?
				idVrste = vrstaDAO.dodajVrstoGradiva(vrstaInput);
			else if (!request.getParameter("vrstaSelect").equals("nic"))
				idVrste = Integer.parseInt(request.getParameter("vrstaSelect"));
			gradivo.setTk_id_vrste(idVrste);

			String zalozbaInput = request.getParameter("zalozbaInput");
			int idZalozbe = -1;
			if(zalozbaInput!=null && !zalozbaInput.isEmpty()) //? prazn?
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
				
				String testWhiteSpaceIme = avtorjiImeInput[0].replaceAll("\\s+","");
				String testWhiteSpacePriimek = avtorjiPriimekInput[0].replaceAll("\\s+","");
				boolean prviPoln = testWhiteSpaceIme != "" && testWhiteSpacePriimek != "";
				
				if(prviPoln){
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
				boolean selectPoln = !avtorjiSelect[0].equals("-1");
				if(selectPoln){
					for(int j=0; j<avtorjiSelect.length; j++){
						if(!avtorjiSelect[j].equals("-1"))
							idAvtorjev.add(Integer.parseInt(avtorjiSelect[j]));
					}
				}
				
				if(!prviPoln && !selectPoln)//neko opozorilo  da nekaj ni blo vneseno
					stran = "/glavnaVsebina/Domov.jsp"; //placeholder
				else {
					//prvo izbris vseh vmesnih za avtorje
					gradivoAvtorDAO.izbrisiVseAvtorGradivo(idGradiva);
					gradivoDAO.urediGradivo(gradivo);
					gradivoAvtorDAO.dodajAvtorGradivo(seznamAvtorjev, idGradiva);
					redirect = true;
					stran="/knjiznica/GradivoServlet?metoda=pridobiGradivo&idGradiva="+idGradiva;
				}
			}
			else{//neko opozorilo  da nekaj ni blo vneseno
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
			}
			
		}
		else if(metoda.equals("izbrisi")){
			if(gradivoDAO.izbrisiGradivo(idGradiva)){
				redirect = true;
				stran="/knjiznica/GradivoServlet?metoda=pridobiVse";	
			}
			else //neko opozorilo da ne more zbrisat
				stran = "/glavnaVsebina/Domov.jsp"; //placeholder
		
		}
		
		if(stran=="") // ce slo kje kaj narobe..
			stran = "/glavnaVsebina/Domov.jsp"; //placeholder

		RequestDispatcher disp = request.getRequestDispatcher(stran);
		if(redirect)
			response.sendRedirect(stran);
		else if(disp !=null)
			disp.forward(request,response);
		
	}

}
