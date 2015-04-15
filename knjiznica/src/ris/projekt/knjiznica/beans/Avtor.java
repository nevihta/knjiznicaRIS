package ris.projekt.knjiznica.beans;

public class Avtor {

	private int id;
	private String ime;
	private String priimek;
	
	public Avtor(){
		
	}
	
	public Avtor(int id, String ime, String priimek){
		this.id=id;
		this.ime=ime;
		this.priimek=priimek;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPriimek() {
		return priimek;
	}

	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}
	
	
	
}
