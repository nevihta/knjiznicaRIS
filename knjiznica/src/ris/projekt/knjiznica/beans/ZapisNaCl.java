package ris.projekt.knjiznica.beans;

import java.util.Date;

public class ZapisNaCl {

	private int id;
	private Date datumZapisa;
	private Date datumIzbrisa;
	private String razlog;
	private int tk_id_osebe;
	private Oseba oseba;
	
	public ZapisNaCl(){
		
	}
	
	public ZapisNaCl(int id, Date datumZapisa, Date datumIzbrisa, String razlog, int tk_id_osebe){
		this.id=id;
		this.datumIzbrisa=datumIzbrisa;
		this.datumZapisa=datumZapisa;
		this.razlog=razlog;
		this.tk_id_osebe=tk_id_osebe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatumZapisa() {
		return datumZapisa;
	}

	public void setDatumZapisa(Date datumZapisa) {
		this.datumZapisa = datumZapisa;
	}

	public Date getDatumIzbrisa() {
		return datumIzbrisa;
	}

	public void setDatumIzbrisa(Date datumIzbrisa) {
		this.datumIzbrisa = datumIzbrisa;
	}

	public String getRazlog() {
		return razlog;
	}

	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}

	public int getTk_id_osebe() {
		return tk_id_osebe;
	}

	public void setTk_id_osebe(int tk_id_osebe) {
		this.tk_id_osebe = tk_id_osebe;
	}

	public Oseba getOseba() {
		return oseba;
	}

	public void setOseba(Oseba oseba) {
		this.oseba = oseba;
	}

	
}


