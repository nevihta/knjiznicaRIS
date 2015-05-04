package ris.projekt.knjiznica.beans;

public class StoritevZaIzpis {

	private Storitev storitev;
	private Gradivo gradivo;
	private Oseba oseba;
	
	public StoritevZaIzpis(){
		
	}
	
	public StoritevZaIzpis(Storitev storitev, Gradivo gradivo){
		this.storitev=storitev;
		this.gradivo=gradivo;
	}

	public StoritevZaIzpis(Storitev storitev, Oseba oseba){
		this.storitev=storitev;
		this.oseba=oseba;
	}
	
	public Storitev getStoritev() {
		return storitev;
	}

	public void setStoritev(Storitev storitev) {
		this.storitev = storitev;
	}

	public Gradivo getGradivo() {
		return gradivo;
	}

	public void setGradivo(Gradivo gradivo) {
		this.gradivo = gradivo;
	}

	public Oseba getOseba() {
		return oseba;
	}

	public void setOseba(Oseba oseba) {
		this.oseba = oseba;
	}
	
	
	
	
}
