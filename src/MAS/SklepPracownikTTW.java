package MAS;

import java.io.Serializable;

public class SklepPracownikTTW implements Serializable, Comparable<SklepPracownikTTW>{//TTW-Total Time Worked
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Sklep s;
	protected Pracownik p;
	protected int hours=0;
	
	public static SklepPracownikTTW Create(Sklep s, Pracownik p) {//Metoda służąca do generowania obiektów asocjacji
		//System.out.println("Check"+(p instanceof Serwisant)+" "+(s instanceof SklepZSerwisem));							//NAND
		if(s==null || p==null || (s.containsPracownik(p) && p.getSklep()==s) || !(p instanceof Serwisant ? (s instanceof SklepZSerwisem) : true)) {//Sprawdzamy czy zarówno sklep jak i pracownik nie są nullami oraz czy nie posiadają już właściwej asocjacji
			return null;
		}
		SklepPracownikTTW spttw=new SklepPracownikTTW(s,p);//Tworzenie nowej relacji
		if(!s.containsPracownik(p)) {//Sprawdzamy czy sklep nie ma już danego pracownika
			s.addPracownik(spttw);//Wysyłanie asocjacji do sklepu
		} 
		if( p.getSklep()!=s) {//Sprawdzamy czy sklep nie został już ustawiony
			p.setSklep(spttw);//Wysyłanie asocjacji do pracownika
		}
		return spttw;
	}
	
	private SklepPracownikTTW(Sklep sk, Pracownik pr) {
		s=sk;
		p=pr;
	}
	
	public Sklep getSklep() {return s;}
	public Pracownik getPracownik() {return p;}
	public int getHours() {return hours;}
	public boolean setHours(int h) {
		if(h<0)return false; 
		hours=h;
		return true;
	}
	
	public void remove() {//Usuwanie asocjacji
		if(s!=null && s.containsPracownik(p)) {//Jeżeli pracownik nie został jeszcze usunięty w sklepie
			s.removePracownik(this.p);
			s=null;
		}
		if(p!=null && p.getSklep()==s) {//Jeżeli sklep nie został jeszcze usuniętu w pracowniku
			Pracownik temp=p;//Potrzebny jest pusty obiekt SPTTW z powodu dwóch wersji metody setSklep
			p=null;
			temp.setSklepNull();
		}
		
	}
	
	public String toString() {
		return s+" "+hours+" "+p;
	}

	public boolean equals(Object obj) {//Porównywanie obiektów
		if(obj!=null) {
			SklepPracownikTTW temp=(SklepPracownikTTW)obj;
			return this.p==temp.p && this.s==temp.s;
		}
		return false;
	}

	@Override
	public int compareTo(SklepPracownikTTW arg0) {
		return p.name.compareTo(arg0.p.name);
	}
}
