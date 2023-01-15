package MAS;

public class ProducentLokalny extends Producent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Adres adres;
	
	public ProducentLokalny(String nazwa, Adres adres) {
		super(nazwa);
		this.adres=adres;
	}

	public ProducentLokalny(String nazwa, Adres adres, String numerKonta, int numerKontaktowy) {//Deklaracja klasy ProducentLokalny z FirmaDuza
		super(nazwa, numerKonta, numerKontaktowy);
		this.adres=adres;
	}
	
	public Adres getAdres() {return adres;}
	public void setAdres(Adres adres) {this.adres=adres;}
	public String getType() {return "Lokalny";}
}
