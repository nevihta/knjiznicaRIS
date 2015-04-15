package ris.projekt.knjiznica.beans;

public class GradivoAvtor {

	private int id;
	private int tk_id_avtorja;
	private int tk_id_gradiva;
	
	public GradivoAvtor(){
		
	}
	
	public GradivoAvtor(int id, int tk_id_avtorja, int tk_id_gradiva){
		this.id=id;
		this.tk_id_gradiva=tk_id_gradiva;
		this.tk_id_avtorja=tk_id_avtorja;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTk_id_avtorja() {
		return tk_id_avtorja;
	}

	public void setTk_id_avtorja(int tk_id_avtorja) {
		this.tk_id_avtorja = tk_id_avtorja;
	}

	public int getTk_id_gradiva() {
		return tk_id_gradiva;
	}

	public void setTk_id_gradiva(int tk_id_gradiva) {
		this.tk_id_gradiva = tk_id_gradiva;
	}

	
	
}
