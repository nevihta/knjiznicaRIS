package ris.projekt.knjiznica.beans;

public class Zalozba {

	private int id;
	private String mesto;
	private String naziv;
	
	public Zalozba(){
		
	}
	
	public Zalozba(int id, String mesto, String naziv){
		this.id=id;
		this.mesto=mesto;
		this.naziv=naziv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
}
