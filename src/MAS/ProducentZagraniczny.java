package MAS;

import java.util.Locale;

public class ProducentZagraniczny extends Producent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Locale kraj;
	boolean imported;
	
	public ProducentZagraniczny(String nazwa, Locale kraj, boolean imported) {
		super(nazwa);
		this.kraj=kraj;
		this.imported=imported;
	}

	public ProducentZagraniczny(String nazwa, Locale kraj, boolean imported, String numerKonta, int numerKontaktowy) {//Deklaracja klasy ProducentZagraniczny z FirmaDuza
		super(nazwa, numerKonta, numerKontaktowy);
		this.kraj=kraj;
		this.imported=imported;
	}
	
	public boolean isImported() {return imported;}
	public Locale getKraj() {return kraj;}
	
	public void setImported(boolean imported) {this.imported=imported;}
	public void setLocale(Locale kraj) {this.kraj=kraj;}
	public String getType() {return "Zagraniczny";}
}
