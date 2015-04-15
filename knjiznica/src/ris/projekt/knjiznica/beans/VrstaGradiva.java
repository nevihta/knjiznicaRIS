package ris.projekt.knjiznica.beans;

public class VrstaGradiva {

	private int id;
	private String naziv;
	
	public VrstaGradiva(){
		
	}
	
	public VrstaGradiva(int id, String naziv){
		this.id=id;
		this.naziv=naziv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
	
}
