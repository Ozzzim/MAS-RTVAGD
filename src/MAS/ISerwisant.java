package MAS;

import java.math.BigDecimal;

public interface ISerwisant {
	static BigDecimal baseWage=new BigDecimal(200);
	static BigDecimal bonusPerService=new BigDecimal(20);
	
	public Kwalifikacja getKwalifikacja();
	public void setKwalifikacja(Kwalifikacja k);
	public void addSerwis(int i);
	public int getSerwis();
	public void resetSerwis();
}
