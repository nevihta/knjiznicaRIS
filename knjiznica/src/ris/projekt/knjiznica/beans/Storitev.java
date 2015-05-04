package ris.projekt.knjiznica.beans;

import java.util.Date;

public class Storitev {

	private int id;
	private Date datumIzposoje;
	private Date datumVrnitve;
	private Date rokVrnitve;
	private boolean zePodaljsano;
	private int tk_id_clana;
	private int tk_id_gradiva;
	private int tk_id_knjiznicarja;

	public Storitev(){
		
	}
	
	public Storitev(int id, Date datumIzposoje, Date datumVrnitve, Date rokVrnitve, boolean zePodaljsano, int tk_id_clana, int tk_id_gradiva, int tk_id_knjiznicarja){
		this.id=id;
		this.datumIzposoje=datumIzposoje;
		this.datumVrnitve=datumVrnitve;
		this.rokVrnitve=rokVrnitve;
		this.zePodaljsano=zePodaljsano;
		this.tk_id_clana=tk_id_clana;
		this.tk_id_gradiva=tk_id_gradiva;
		this.tk_id_knjiznicarja=tk_id_knjiznicarja;

	}

	public Storitev(Date datumIzposoje, Date datumVrnitve, Date rokVrnitve, boolean zePodaljsano, int tk_id_clana, int tk_id_gradiva, int tk_id_knjiznicarja){
		this.datumIzposoje=datumIzposoje;
		this.datumVrnitve=datumVrnitve;
		this.rokVrnitve=rokVrnitve;
		this.zePodaljsano=zePodaljsano;
		this.tk_id_clana=tk_id_clana;
		this.tk_id_gradiva=tk_id_gradiva;
		this.tk_id_knjiznicarja=tk_id_knjiznicarja;

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatumIzposoje() {
		return datumIzposoje;
	}

	public void setDatumIzposoje(Date datumIzposoje) {
		this.datumIzposoje = datumIzposoje;
	}

	public Date getDatumVrnitve() {
		return datumVrnitve;
	}

	public void setDatumVrnitve(Date datumVrnitve) {
		this.datumVrnitve = datumVrnitve;
	}

	public boolean isZePodaljsano() {
		return zePodaljsano;
	}

	public void setZePodaljsano(boolean zePodaljsano) {
		this.zePodaljsano = zePodaljsano;
	}

	public int getTk_id_clana() {
		return tk_id_clana;
	}

	public void setTk_id_clana(int tk_id_clana) {
		this.tk_id_clana = tk_id_clana;
	}

	public int getTk_id_knjiznicarja() {
		return tk_id_knjiznicarja;
	}

	public void setTk_id_knjiznicarja(int tk_id_knjiznicarja) {
		this.tk_id_knjiznicarja = tk_id_knjiznicarja;
	}

	public Date getRokVrnitve() {
		return rokVrnitve;
	}

	public void setRokVrnitve(Date rokVrnitve) {
		this.rokVrnitve = rokVrnitve;
	}

	public int getTk_id_gradiva() {
		return tk_id_gradiva;
	}

	public void setTk_id_gradiva(int tk_id_gradiva) {
		this.tk_id_gradiva = tk_id_gradiva;
	}
	
	
}

