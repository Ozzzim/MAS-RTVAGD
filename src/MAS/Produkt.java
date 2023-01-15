package MAS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Produkt implements Serializable, Comparable<Produkt> {
	/**
	 * 
	 * Uwaga. Zawartość tej klasy jest tymczasowa i podlega przyszłym zmianom 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static List<Produkt> Extent = new ArrayList<Produkt>();//Ekstensja klasy
	private static int nextID=0;//Numerowanie wystąpień klasy
	
	private int id;
	
	protected String name;
	protected BigDecimal cena;
	protected String kodFirmowy;
	protected Producent producent;
	List<Sklep> availability;
	
	//CONSTRUCTORS ==========================================================================
	public Produkt(String name, String kodFirmowy) {
		this.name=name;
		this.kodFirmowy=kodFirmowy;
		availability=new ArrayList<Sklep>();
		cena=BigDecimal.ZERO;

		
		id=nextID;
		nextID++;
		Extent.add(this);
	}
	public Produkt(String name, String kodFirmowy, BigDecimal cena) {
		this.name=name;
		this.kodFirmowy=kodFirmowy;
		availability=new ArrayList<Sklep>();
		this.cena=cena;
		
		id=nextID;
		nextID++;
		Extent.add(this);
	}
	//=======================================================================================	
	
	//GETTERS ===============================================================================
	public int getID() {return id;}
	public String getNazwa() {return name;}
	public BigDecimal getCena() {return cena;}
	public String getKodFirmowy() {return kodFirmowy;}
	public Producent getProducent() {return producent;}
	public Sklep getSklepWhereAvailable(int i) {return availability.get(i);}
	public static List<Produkt> getProdukty(){return Extent;}
	public static Produkt get(int i) {return Extent.get(i);}
	public String getDetails() {return "Dostępny w "+availability.size()+" sklepach.\nŁącznie "+getTotalSum()+" sztuk.";}
	public int getTotalSum() {
		int sum=0;
		
		for(int i=0;i<availability.size();i++) {
			sum+=availability.get(i).GetAmount(this);
		}
		return sum;
	}
	//=======================================================================================
	
	//SETTERS ===============================================================================
	public boolean setProducent(Producent p) {
		if(producent!=p) {//Sprawdzamy czy asocjacja już istnieje, w celu zapobiegnięcia zapętlaniu
			if(producent!=null)//Zabezpieczenie przeciwko nullpointerexception, w przypadku gdy producent nie jest ustawiony
				producent.removeProdukt(kodFirmowy);//Wiadomość zwrotna do usunięcia powiązania z poprzedniego producenta
			producent=p;//Ustawienie nowego producenta
			
			if(producent!=null)//W przypadku gdy usuwamy przynależność do producenta p.addProdukt by zwróciło null exception
				p.addProdukt(this);//Wiadomość zwrotna do dodania nowego powiązania nowemu producentowi
			return true;
		}
		return false;
	}
	//=======================================================================================
	
	//SKLEP ASSOCIATION(BINARY)==============================================================
	public boolean addSklep(Sklep s, int initialAmount) {
		//Nie może istnieć ujemna ilość produktu w sklepie
		if(!(initialAmount<1) && !availability.contains(s)) {//Sprawdzamy czy asocjacja już istnieje, w celu zapobiegnięcia zapętlaniu
			availability.add(s);//Dodajemy do asocjacje od strony produktu
			s.addProdukt(this, initialAmount);//Wiadomość zwrotna do dodania nowego powiązania dla sklepu
			return true;
		} 
		return false;
	}
	
	public boolean removeSklep(Sklep s) {
		if(availability.contains(s)) {//Sprawdzamy czy asocjacja już istnieje, w celu zapobiegnięcia zapętlaniu
			availability.remove(s);//Sklep jest usuwany najpierw po stronie produktu w celu zatrzymania zwrotnego removeSklep po stronie sklepu
			s.removeProdukt(this);//Wywołuje komendę która usunie cały inwentarz. W przypadku zwrotności metoda wykrywa wartości poniżej 0
			return true;
		}
		return false;
	}
	
	public boolean isAvailableIn(Sklep s) {return availability.contains(s);}
	//=======================================================================================
	
	//UTILITIES =============================================================================
	public String toString() { return name;}
	public int compareTo(Produkt arg0) { return name.compareTo(arg0.name);}
	public boolean equals(Object obj) {
		if(((Produkt)obj).name.equals(name))
			return true;
		return false;
	}
	//=======================================================================================
	
	//SERIALZATION =======================================================
	public static void loadExtent(String fileLoc) throws IOException,ClassNotFoundException {
		int maxID=nextID;
		Produkt p;
		
		FileInputStream fileIn = new FileInputStream(fileLoc);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int i =0;
        int l = (int) in.readObject();
        while(i<l) {//in.available()>0) {
	        p = (Produkt) in.readObject();
	        Extent.add(p);
	        if(p.getID()>maxID)
	        	maxID=p.getID();
	        i++;
        }    
        in.close();
        fileIn.close();
        
        nextID=maxID;
	}
	public static void saveExtent(String fileLoc ) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(fileLoc);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(Extent.size());//Save how many items are being recorded
	    for(int i=0;i<Extent.size();i++) {
			out.writeObject(Extent.get(i));
	    }
	    out.close();
	    fileOut.close();
	}
	public static void loadExtent(List<Produkt> list) {
		int maxID=nextID;
		int i=0;
		
		Extent.addAll(list);
		
		while(i<list.size()) {
			if(maxID<list.get(i).id)
				maxID=list.get(i).id;
			i++;
		}
		
		nextID=maxID;
	}
	//====================================================================*/
}