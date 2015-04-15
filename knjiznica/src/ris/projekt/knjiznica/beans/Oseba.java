package ris.projekt.knjiznica.beans;

public class Oseba {

	private int id;
	private String ime;
	private String priimek;
	private TipOsebe tipOsebe;
	private String email;
	private String telefon;
	private int tk_id_naslova;
	
	public Oseba(){
		
	}
	
	public Oseba(int id, String ime, String priimek, TipOsebe tipOsebe, String email, String telefon, int tk_id_naslova){
		this.id=id;
		this.ime=ime;
		this.priimek=priimek;
		this.tipOsebe=tipOsebe;
		this.email=email;
		this.telefon=telefon;
		this.tk_id_naslova=tk_id_naslova;
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

	public TipOsebe getTipOsebe() {
		return tipOsebe;
	}

	public void setTipOsebe(TipOsebe tipOsebe) {
		this.tipOsebe = tipOsebe;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public int getTk_id_naslova() {
		return tk_id_naslova;
	}

	public void setTk_id_naslova(int tk_id_naslova) {
		this.tk_id_naslova = tk_id_naslova;
	}


	
	
	
}
