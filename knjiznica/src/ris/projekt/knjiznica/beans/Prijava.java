package ris.projekt.knjiznica.beans;

public class Prijava {

	private int id;
	private String upIme;
	private String geslo;
	private int tk_id_osebe;
	
	public Prijava(){
		
	}
	
	public Prijava(int id, String upIme, String geslo, int tk_id_osebe){
		this.id=id;
		this.upIme=upIme;
		this.geslo=geslo;
		this.tk_id_osebe=tk_id_osebe;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpIme() {
		return upIme;
	}

	public void setUpIme(String upIme) {
		this.upIme = upIme;
	}

	public String getGeslo() {
		return geslo;
	}

	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}

	public int getTk_id_osebe() {
		return tk_id_osebe;
	}

	public void setTk_id_osebe(int tk_id_osebe) {
		this.tk_id_osebe = tk_id_osebe;
	}
	
	
}
