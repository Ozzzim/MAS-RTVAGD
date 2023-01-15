package MAS;

import java.io.Serializable;

public class Adres implements Serializable{
	/**
	 * 
	 * Argument złożony
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String kraj;
	protected String miasto;
	protected String ulica;
	protected int ulica_adres;
	protected int nr_mieszkania;//Jeżeli wartość jest ujemna, będzie ona ignorowana
	
	public Adres(String kraj, String miasto, String ulica, int ulica_adres,	int nr_mieszkania) {
		this.kraj=kraj;
		this.miasto=miasto;
		this.ulica=ulica;
		this.ulica_adres=ulica_adres;
		this.nr_mieszkania=nr_mieszkania;
	}
	
	public Adres(String kraj, String miasto, String ulica, int ulica_adres) {
		this.kraj=kraj;
		this.miasto=miasto;
		this.ulica=ulica;
		this.ulica_adres=ulica_adres;
		this.nr_mieszkania=-1;
	}
	
	public String toString() {
		return ulica+" "+ulica_adres+((nr_mieszkania < 0) ? "" : "m"+nr_mieszkania)+", "+miasto+", "+kraj;
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == null || getClass() != o.getClass()) return false;
		 return (((Adres)o)).kraj == this.kraj && ((Adres)o).miasto == this.miasto && ((Adres)o).ulica == this.ulica && ((Adres)o).ulica_adres == this.ulica_adres && ((Adres)o).nr_mieszkania == this.nr_mieszkania;
	}
}
