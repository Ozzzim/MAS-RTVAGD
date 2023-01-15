package MAS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public abstract class Producent implements Serializable, Comparable<Producent>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected Map<String, Produkt> produktDatabase;
	//private RozmiarFirmy rozmiar; 
	protected String numerKonta;
	protected int numerKontaktowy;
	
	
	
	private static List<Producent> Extent = new ArrayList<Producent>();
	private int id;
	private static int nextID=0;
	
	public Producent(String nazwa) {
		this.name=nazwa;
		produktDatabase = new TreeMap<String, Produkt>();
		
		id=nextID;
		nextID++;
		Extent.add(this);
	}
	
	/*public Producent(String nazwa, String numerKonta, int numerKontaktowy,  int liczbaPracownikow) {//Konstruktor dla malego producenta
		this.name=nazwa;
		produktDatabase = new TreeMap<String, Produkt>();
		//rozmiar=new FirmaMala(numerKonta, numerKontaktowy, liczbaPracownikow);//Deklarowanie wieloaspektowej klasy FirmaMala
		
		
		id=nextID;
		nextID++;
		Extent.add(this);
	}*/
	
	public Producent(String nazwa, String numerKonta, int numerKontaktowy) {//Konstruktor dla duzego producenta
		this.name=nazwa;
		produktDatabase = new TreeMap<String, Produkt>();
		//rozmiar=new FirmaDuza(numerKonta, numerKontaktowy, nazwiskoReprezentanta);//Deklarowanie wieloaspektowej klasy FirmaDuza
		this.numerKontaktowy=numerKontaktowy;
		this.numerKonta=numerKonta;
		
		id=nextID;
		nextID++;
		Extent.add(this);
	}
	
	
	//PRODUKT ASSOCIATION(QUALIFIER) ========================================================
    public boolean addProdukt(Produkt p) {//Dodanie nowego produktu
        if(!produktDatabase.containsKey(p.getKodFirmowy())) {//Sprawdzamy czy asocjacja już istnieje, w celu zapobiegnięcia zapętlaniu. Używamy do tego gettera kodFirmowego produktu
            produktDatabase.put(p.getKodFirmowy(), p);//Wstawienie produktu do mapy
            return p.setProducent(this);//Wiadomość zwrotna dla produktu
        }
        return false;
    }
 
    public Produkt getProdukt(String kodFirmowy) {
        if(!produktDatabase.containsKey(kodFirmowy))//Zabezpieczenie przeciwko nie istniejącym produktom
        	return null;
 
        return produktDatabase.get(kodFirmowy);
    }
    
    public boolean removeProdukt(String kp) {//Usuwanie produktów
    	if(produktDatabase.containsKey(kp)) {//Sprawdzamy czy asocjacja już nie istnieje, w celu zapobiegnięcia zapętlaniu
    		Produkt temp=produktDatabase.get(kp);//Tworzymy obiekt tymczasowy w celu późniejszego zwrotnego usunięcia
    		produktDatabase.remove(kp);
    		
    		temp.setProducent(null);//Zwrotne usunięcie asocjacji
    		return true;
    	}
        return false;
    }
	//=======================================================================================
    
    public String getNumerKonta() {return numerKonta;}
	public int getNumerKontatkowy() {return numerKontaktowy;}
	
	//UTILITIES =============================================================================
    public String toString() { 
    	/*if(rozmiar!=null) {
    		return rozmiar.toString();
    	}*/
    	return name;
    }
    
    public int compareTo(Producent arg0) { return name.compareTo(arg0.name);}
	//=======================================================================================
    
    public String getType() {return "Producent";}
	public String getName() {return name;}
	public int getID() {return id;}
	public static List<Producent> getProducenci(){return Extent;}
	public static Producent get(int i) {return Extent.get(i);}
	public int getProduktAmount() {return produktDatabase.size();}
	
	//SERIALZATION =======================================================
	public static void loadExtent(String fileLoc) throws IOException,ClassNotFoundException {
		int maxID=nextID;
		Producent p;
		
		FileInputStream fileIn = new FileInputStream(fileLoc);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int i =0;
        int l = (int) in.readObject();
        while(i<l) {//in.available()>0) {
	        p = (Producent) in.readObject();
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
	public static void loadExtent(List<Producent> list) {
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



