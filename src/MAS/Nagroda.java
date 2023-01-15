package MAS;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Nagroda implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private BigDecimal moneyBonus=BigDecimal.ZERO;
	private List<NagrodaPracownikY> nagrodzeniPracownicy;
	
	//CONSTRUCTORS =======================================================
	public Nagroda(String name) {
		this.name=name;
		nagrodzeniPracownicy=new ArrayList<NagrodaPracownikY>();
	}
	
	public Nagroda(String name, BigDecimal moneyBonus) {
		this.name=name;
		this.moneyBonus=moneyBonus;
		nagrodzeniPracownicy=new ArrayList<NagrodaPracownikY>();
	}
	//====================================================================
	
	//GETTERS ============================================================
	public String getName() {return name;}
	public BigDecimal getBonus() {return moneyBonus;}
	public NagrodaPracownikY getPracownik(int i) {return nagrodzeniPracownicy.get(i);}
	//====================================================================
	
	//OBSŁUGA BAG ========================================================
	public void giveAward(Pracownik p) {
		NagrodaPracownikY temp = new NagrodaPracownikY(this,p,Year.now().getValue());//Utworzenie nowej relacji pomiędzy pracownikiem a nagrodą
		nagrodzeniPracownicy.add(temp);
		p.addRecord(temp);//Zwrotne przydzielenie nagrody pracownikowi
	}
	
	public void giveAward(Pracownik p, int y) {//Wersja z własnym przypisaniem roku
		NagrodaPracownikY temp = new NagrodaPracownikY(this,p,y);//Utworzenie nowej relacji pomiędzy pracownikiem a nagrodą
		nagrodzeniPracownicy.add(temp);
		p.addRecord(temp);//Zwrotne przydzielenie nagrody pracownikowi
	}
	
	public boolean addRecord(NagrodaPracownikY npy) {
		if(npy.getNagroda()==this) {
			nagrodzeniPracownicy.add(npy);
			return true;
		}
		return false;
	}
}
