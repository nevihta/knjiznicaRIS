package ris.projekt.knjiznica;

import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ris.projekt.knjiznica.beans.*;

//vir: Crunchify.com
public class Email {
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	public static boolean posljiEmailZamudnina(List<StoritevZaIzpis> zamujenoGradivo){
		boolean uspesnoPoslano = false;
		try {
			generateAndSendEmail(zamujenoGradivo);
			uspesnoPoslano = true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uspesnoPoslano;
	}
	
	public static void generateAndSendEmail(List<StoritevZaIzpis> zamujenoGradivo) throws AddressException, MessagingException {
	 
	//Step1		
			System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			System.out.println("Mail Server Properties have been setup successfully..");

	//Step2		
			System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("uporabnik.ris@gmail.com")); //zamujenoGradivo.getOseba().getEmail()
			generateMailMessage.setSubject("Zamujen rok vrnitve izposojenega gradiva", "UTF-8");
			StringBuilder sb = new StringBuilder();
			sb.append("Spoštovani! <br /> Obvešèamo vas, da ste zamudili rok vrnitve izposojenega gradiva."
					+" Prosimo vas, da gradivo èimprej vrnete.");
			if(zamujenoGradivo.size()!=0){
			//tabela izposoj
				sb.append("<table>");
				sb.append("<tr><td>Izposojeno gradivo</td></tr>");
				sb.append("<tr><th>#</th><th>Naslov</th><th>Datum izposoje</th><th>Rok vrnitve</th></tr>");
				StoritevZaIzpis s = new StoritevZaIzpis();
				for(int i=1;i<=zamujenoGradivo.size();i++){
					s = zamujenoGradivo.get(i-1);
					sb.append("<tr><td>"+i+"</td><td>"+s.getGradivo().getNaslov()+"</td><td>"+
					s.getStoritev().getDatumIzposoje()+"</td><td>"+s.getStoritev().getRokVrnitve()+"</td></tr>");
				}
				sb.append("</table>");
			}
			sb.append("<br><br>Lep pozdrav, <br>Vaša Knjižnica");
			generateMailMessage.setContent(sb.toString(), "text/html; charset=UTF-8");
			System.out.println("Mail Session has been created successfully..");
			
	//Step3		
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
			
			transport.connect("smtp.gmail.com", "knjiznicaris", "projektRIS");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		}
}
