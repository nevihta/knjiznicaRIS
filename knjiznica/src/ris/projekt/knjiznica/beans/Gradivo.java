package ris.projekt.knjiznica.beans;



public class Gradivo {

	private int id;
	private String naslov;
	private String originalNaslov;
	private Jezik jezik;
	private int letoIzida;
	private String ISBN;
	private String opis;
	private int tk_id_podrocja;
	private int tk_id_vrste;
	private int tk_id_zalozbe;
	
	public Gradivo(){
		
	}
	
	public Gradivo(int id, String naslov, String originalNaslov, Jezik jezik, int letoIzida, String ISBN, String opis, int tk_id_podrocja, int tk_id_vrste, int tk_id_zalozbe){
		this.id=id;
		this.naslov=naslov;
		this.originalNaslov=originalNaslov;
		this.jezik=jezik;
		this.letoIzida=letoIzida;
		this.ISBN=ISBN;
		this.opis=opis;
		this.tk_id_podrocja=tk_id_podrocja;
		this.tk_id_vrste=tk_id_vrste;
		this.tk_id_zalozbe=tk_id_zalozbe;	
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

	public int getTk_id_podrocja() {
		return tk_id_podrocja;
	}

	public void setTk_id_podrocja(int tk_id_podrocja) {
		this.tk_id_podrocja = tk_id_podrocja;
	}

	public int getTk_id_vrste() {
		return tk_id_vrste;
	}

	public void setTk_id_vrste(int tk_id_vrste) {
		this.tk_id_vrste = tk_id_vrste;
	}

	public int getTk_id_zalozbe() {
		return tk_id_zalozbe;
	}

	public void setTk_id_zalozbe(int tk_id_zalozbe) {
		this.tk_id_zalozbe = tk_id_zalozbe;
	}
	
	
}
