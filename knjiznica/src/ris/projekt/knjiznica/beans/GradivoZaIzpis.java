package ris.projekt.knjiznica.beans;

import java.util.ArrayList;



public class GradivoZaIzpis {

	private int id;
	private String naslov;
	private String originalNaslov;
	private Jezik jezik;
	private int letoIzida;
	private String ISBN;
	private String opis;
	private String podrocje;
	private String vrsta;
	private String zalozba;
	private ArrayList<Avtor> avtorji;
	
	public GradivoZaIzpis(){
		
	}
	
	public GradivoZaIzpis(int id, String naslov, String originalNaslov, Jezik jezik, int letoIzida, String ISBN, String opis, String podrocje, String vrsta, String zalozba, ArrayList<Avtor> avtorji){
		this.id=id;
		this.naslov=naslov;
		this.originalNaslov=originalNaslov;
		this.jezik=jezik;
		this.letoIzida=letoIzida;
		this.ISBN=ISBN;
		this.opis=opis;
		this.podrocje=podrocje;
		this.vrsta=vrsta;
		this.zalozba=zalozba;
		this.avtorji=avtorji;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getOriginalNaslov() {
		return originalNaslov;
	}

	public void setOriginalNaslov(String originalNaslov) {
		this.originalNaslov = originalNaslov;
	}

	public Jezik getJezik() {
		return jezik;
	}

	public void setJezik(Jezik jezik) {
		this.jezik = jezik;
	}

	public int getLetoIzida() {
		return letoIzida;
	}

	public void setLetoIzida(int letoIzida) {
		this.letoIzida = letoIzida;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getPodrocje() {
		return podrocje;
	}

	public void setPodrocje(String podrocje) {
		this.podrocje = podrocje;
	}

	public String getVrsta() {
		return vrsta;
	}

	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}

	public String getZalozba() {
		return zalozba;
	}

	public void setZalozba(String zalozba) {
		this.zalozba = zalozba;
	}

	public ArrayList<Avtor> getAvtorji() {
		return avtorji;
	}

	public void setAvtorji(ArrayList<Avtor> avtorji) {
		this.avtorji = avtorji;
	}
	
}
