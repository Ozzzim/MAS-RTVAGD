package MAS;

import java.math.BigDecimal;

public enum Kwalifikacja {
		brak("1"),
		inżynier("1.2"),
		magister("1.5");
		
		BigDecimal value;
		
		private Kwalifikacja(String val) {this.value=new BigDecimal(val);}
}
