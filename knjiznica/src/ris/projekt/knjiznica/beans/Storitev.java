package ris.projekt.knjiznica.beans;

import java.util.Date;

public class Storitev {

	private int id;
	private Date datumIzposoje;
	private Date datumVrnitve;
	private boolean zePodaljsano;
	private int tk_id_clana;
	
	public Storitev(){
		
	}
	
	public Storitev(int id, Date datumIzposoje, Date datumVrnitve, boolean zePodaljsano, int tk_id_clana){
		this.id=id;
		this.datumIzposoje=datumIzposoje;
		this.datumVrnitve=datumVrnitve;
		this.zePodaljsano=zePodaljsano;
		this.tk_id_clana=tk_id_clana;
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
	
	
}

