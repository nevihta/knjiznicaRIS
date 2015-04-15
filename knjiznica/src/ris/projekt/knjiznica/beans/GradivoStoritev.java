package ris.projekt.knjiznica.beans;

public class GradivoStoritev {

	private int id;
	private int tk_id_storitve;
	private int tk_id_gradiva;
	
	public GradivoStoritev(){
		
	}
	
	public GradivoStoritev(int id, int tk_id_storitve, int tk_id_gradiva){
		this.id=id;
		this.tk_id_gradiva=tk_id_gradiva;
		this.tk_id_storitve=tk_id_storitve;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTk_id_storitve() {
		return tk_id_storitve;
	}

	public void setTk_id_storitve(int tk_id_storitve) {
		this.tk_id_storitve = tk_id_storitve;
	}

	public int getTk_id_gradiva() {
		return tk_id_gradiva;
	}

	public void setTk_id_gradiva(int tk_id_gradiva) {
		this.tk_id_gradiva = tk_id_gradiva;
	}
	
	
}
