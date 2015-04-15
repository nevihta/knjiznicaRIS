package ris.projekt.knjiznica.beans;

public class Naslov {

	private int id;
	private String ulica;
	private String hisnaSt;
	private String mesto;
	private int postnaSt;
	private String drzava;
	
	public Naslov(){
		
	}
	
	public Naslov(int id, String ulica, String hisnaSt, String mesto, int postnaSt, String drzava){
		this.id=id;
		this.ulica=ulica;
		this.hisnaSt=hisnaSt;
		this.mesto=mesto;
		this.postnaSt=postnaSt;
		this.drzava=drzava;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getHisnaSt() {
		return hisnaSt;
	}

	public void setHisnaSt(String hisnaSt) {
		this.hisnaSt = hisnaSt;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public int getPostnaSt() {
		return postnaSt;
	}

	public void setPostnaSt(int postnaSt) {
		this.postnaSt = postnaSt;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	
	
}
