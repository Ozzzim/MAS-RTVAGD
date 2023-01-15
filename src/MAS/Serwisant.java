package MAS;

import java.math.BigDecimal;

public class Serwisant extends Pracownik implements ISerwisant {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected int iloscSerwisow=0;
	protected Kwalifikacja k = Kwalifikacja.brak;
	
	public Serwisant(String name, boolean sex, int telefon) {
		super(name, sex, telefon);
	}
	public Serwisant(String name, boolean sex, int telefon, Adres adres) {
		super(name, sex, telefon, adres);
	}
	public Serwisant(String name, boolean sex, int telefon, String numerkonta) {
		super(name, sex, telefon, numerkonta);
	}
	public Serwisant(String name, boolean sex, int telefon, String numerkonta, Adres adres) {
		super(name, sex, telefon, numerkonta, adres);
	}
	
	public BigDecimal getWage() {
		return ((baseWage.multiply(k.value)).add(bonusPerService.multiply(new BigDecimal(iloscSerwisow))));
	}
	
	public Kwalifikacja getKwalifikacja() {	return k;}
	public void setKwalifikacja(Kwalifikacja k) { this.k=k;}
	public int getSerwis() {return iloscSerwisow;}
	public void resetSerwis() {iloscSerwisow=0;}	
	public String getType() {return "Serwisant";}
	
	public void addSerwis(int i) {
		if(i<0) 
			return;
		iloscSerwisow+=i;
	}

	public String getDetails() {
		return "Serwisant\nKwalifikacja: "+k+"\n\nIlość serwisów w tym miesiącu: "+iloscSerwisow;
	}
}
