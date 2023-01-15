package MAS;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SklepZSerwisem extends Sklep {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<Serwisant> serwisanci;
	protected LocalTime openingTime;
	protected LocalTime closingTime;
	
	protected static List<SklepZSerwisem> localExtent = new ArrayList<SklepZSerwisem>();
	
	public SklepZSerwisem(Adres adres, Budynek budynek) {
		super(adres, budynek);
		serwisanci = new ArrayList<Serwisant>();
		
		localExtent.add(this);
	}
	
	public SklepZSerwisem(Adres adres, Kompleks kompleks) {
		super(adres, kompleks);
		serwisanci = new ArrayList<Serwisant>();
		
		localExtent.add(this);
	}
	
	public void setServiceOpeningTime(LocalTime ot) {
		openingTime=ot;
	}
	public void setServiceClosingTime(LocalTime ct) {
		closingTime=ct;
	}
	public String getServiceOpeningHours() {
		return openingTime.format(DateTimeFormatter.ISO_LOCAL_TIME)+" - "+closingTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
	}
	public LocalTime getServiceOpeningTime() {
		return openingTime;
	}
	public LocalTime getServiceClosingTime() {
		return closingTime;
	}
	public boolean isServiceOpen() {
		if(openingTime ==null || closingTime== null)
			return false;
		LocalTime temp=LocalTime.now();
		return temp.isAfter(openingTime) && temp.isBefore(closingTime);
	}
	
	public static LocalTime makeLocalTime(int hours, int minutes) {//Metoda pomocnicza do łatwego deklarowania godzin
		LocalTime out = LocalTime.NOON;
		if(hours>12)
			out=out.plusHours(hours-12);
		else
			out=out.minusHours(12-hours);
		out=out.plusMinutes(minutes);
		return out;
	}
	
	//PRACOWNIK + SERWISANCI=================================================================
	public boolean addPracownik(Pracownik p) {//ASOCJACJA Z ATRYBUTEM
		if(!containsPracownik(p)) {//Sprawdzamy czy asocjacja już istnieje, w celu zapobiegnięcia zapętlaniu
			if(p instanceof Serwisant) {
				serwisanci.add((Serwisant)p);
			}
			SklepPracownikTTW.Create(this,p);//Zainicjowanie tworzenia asocjacji z atrybutem
			return true;
		}
		return false;
	}
	
	public boolean addPracownik(SklepPracownikTTW spttw) {
		if(!pracownicy.contains(spttw) && spttw.getSklep()==this) {//Sprawdzamy czy asocjacja już istnieje oraz czy odnosi się ona do tego konkretnego sklepu
			if(spttw.getPracownik() instanceof Serwisant)
				serwisanci.add((Serwisant)spttw.getPracownik());
			pracownicy.add(spttw);
			Collections.sort(pracownicy);
			return true;
		}
		return false;
	}
	
	public List<Serwisant> getSerwisanci() {//ASOCJACJA ZWYKLA
		return serwisanci;
	}
	
	public boolean removePracownik(Pracownik p) {
		int i=0;
		while(i<pracownicy.size()) {
			if(p==pracownicy.get(i).getPracownik()) {
				if(p instanceof Serwisant)
					serwisanci.remove(p);
				SklepPracownikTTW temp=pracownicy.get(i);
				pracownicy.remove(i);
				temp.remove();
				return true;
			}
			i++;
		}
		return false;
	}
	
	public boolean removePracownik(int i) {//ASOCJACJA ZWYKLA
		return removePracownik(getPracownik(i));
	}
	
	public Serwisant getSerwisant(int i) {return serwisanci.get(i);}
	
	public static List<SklepZSerwisem> getLocalExtent(){return localExtent;}
	
	public String getDetails() {
		return adres+"\nIlosc pracowników: "+pracownicy.size()+"\nLokacja: "+(budynek!=null ? budynek.toDetails() : kompleks.toDetails())+"\n\nGodziny otwarcia serwisu: "+getServiceOpeningHours()+"\nIlość serwisantów: "+serwisanci.size();
	}
}
