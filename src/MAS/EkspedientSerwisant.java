package MAS;

import java.math.BigDecimal;

public class EkspedientSerwisant extends Ekspedient implements ISerwisant {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int iloscSerwisow=0;
	protected Kwalifikacja k = Kwalifikacja.brak;//Enum Kwalifikacja dodatkowo przechowuje przeliczniki używane podczas obliczania wypłaty
	
	public EkspedientSerwisant(String name, boolean sex, int telefon) {
		super(name, sex, telefon);
	}
	public EkspedientSerwisant(String name, boolean sex, int telefon, Adres adres) {
		super(name, sex, telefon, adres);
	}
	public EkspedientSerwisant(String name, boolean sex, int telefon, String numerkonta) {
		super(name, sex, telefon, numerkonta);
	}
	public EkspedientSerwisant(String name, boolean sex, int telefon, String numerkonta, Adres adres) {
		super(name, sex, telefon, numerkonta, adres);
	}

	public Kwalifikacja getKwalifikacja() {	return k;}
	public void setKwalifikacja(Kwalifikacja k) { this.k=k;}
	public int getSerwis() {return iloscSerwisow;}
	public void resetSerwis() {iloscSerwisow=0;}	
	
	public void addSerwis(int i) {
		if(i<0)//Zapobiega odejmowaniu
			return;
		iloscSerwisow+=i;
	}
	
	public BigDecimal getWage() {
		return	((ISerwisant.baseWage.multiply(k.value)).add(bonusPerService.multiply(new BigDecimal(iloscSerwisow))))//Zarobki po stronie serwisanta
				.add
				(Ekspedient.baseWage.multiply(new BigDecimal(hoursWorked)))//Zarobki po stronie ekspedienta;
				;
	}
	
	public String getType() {return "EkspedientSerwisant";}
	public String getDetails() {
		return "EkspedientSerwisant\nKwalifikacja: "+k+"\n\nIlość serwisów w tym miesiącu: "+iloscSerwisow+"\nIlość godzin w tym miesiącu: "+hoursWorked;
	}
}
