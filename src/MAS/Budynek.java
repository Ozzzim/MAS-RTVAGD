package MAS;

import java.io.Serializable;
import java.math.BigDecimal;

public class Budynek implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String nazwa;
	protected double metraz;
	protected int iloscPieter;
	protected boolean wynajem;
	protected BigDecimal czynszMiesiac;
	
	public Budynek(String nazwa, int metraz, int iloscPieter) {
		this.nazwa = nazwa;
		this.metraz = metraz;
		this.iloscPieter = iloscPieter;
		this.wynajem = false;
		this.czynszMiesiac = new BigDecimal(0);
	}
	
	public Budynek(String nazwa, int metraz, int iloscPieter, BigDecimal kosztNajmu) {
		this.nazwa = nazwa;
		this.metraz = metraz;
		this.iloscPieter = iloscPieter;
		this.wynajem = true;
		this.czynszMiesiac = kosztNajmu;
	}

	public String getNazwa() {return nazwa;}
	public void setNazwa(String nazwa) {this.nazwa = nazwa;}
	public double getMetraz() {return metraz;}
	public void setMetraz(int metraz) {this.metraz = metraz;}
	public int getIloscPieter() {return iloscPieter;}
	public void setIloscPieter(int iloscPieter) {this.iloscPieter = iloscPieter;}
	public boolean isWynajem() {return wynajem;}
	public BigDecimal getKosztNajmu() {return czynszMiesiac;}
	public void setWynajem(boolean wynajem) {
		this.wynajem = wynajem;
		if(!wynajem)
			czynszMiesiac=new BigDecimal(0);
	}
	public boolean setKosztNajmu(BigDecimal kosztNajmu) {
		if(wynajem) {
			this.czynszMiesiac = kosztNajmu;
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "Budynek "+nazwa;
	}
	public String toDetails() {
		return "Budynek "+nazwa+
			   "\nMetraż: "+metraz+"m2"+
				"\nIlość pięter: "+iloscPieter+
				(wynajem ? ("\nCzynsz za miesiąc: "+czynszMiesiac):"");
	}
}
