package MAS;

import java.io.Serializable;
import java.math.BigDecimal;

public class Kompleks implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Sklep sklep;
	
	protected String nazwa;
	protected BigDecimal czynszMiesiac;
	protected int pietro;
	protected double metraz;
	
	public Kompleks(String nazwa, BigDecimal czynszMiesiac, int pietro, double metraz) {
		this.nazwa = nazwa;
		this.czynszMiesiac = czynszMiesiac;
		this.pietro = pietro;
		this.metraz = metraz;
	}

	public String getNazwa() {return nazwa;	}
	public BigDecimal getCzynszMiesiac() {return czynszMiesiac;}
	public int getPietro() {return pietro;}
	public double getmetraz() {return metraz;}
	public Sklep getSklep() {return sklep;}
	
	public void setNazwa(String nazwa) {this.nazwa = nazwa;}
	public void setCzynszMiesiac(BigDecimal czynszMiesiac) {this.czynszMiesiac = czynszMiesiac;}
	public void setPietro(int pietro) {	this.pietro = pietro;}
	public void setmetraz(double metraz) {this.metraz = metraz;}
	public void setSklep(Sklep s) {sklep=s;}
	
	public String toString() {return "Kompleks "+nazwa;}
	public String toDetails() {return "Kompleks "+nazwa+
			   "\nMetraż: "+metraz+"m2"+
				"\nPiętro: "+pietro+
				"\nCzynsz za miesiąc: "+czynszMiesiac;
	}
}
