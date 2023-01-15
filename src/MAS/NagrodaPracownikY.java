package MAS;

import java.io.Serializable;

public class NagrodaPracownikY implements Serializable{//Y - Year of acqusition
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int year;
	protected Nagroda n;
	protected Pracownik p;
	
	public NagrodaPracownikY(Nagroda n, Pracownik p, int y) {
		this.n=n;
		year=y;
		this.p=p;
	}
	
	public Nagroda getNagroda() {return n;}
	public Pracownik getPracownik() {return p;}
	public int getYear() {return year;}
	
	public String toString() {
		return n.getName()+" za rok "+year+" dla "+p;
	}
}
