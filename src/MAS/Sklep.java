package MAS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sklep implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static List<Sklep> Extent = new ArrayList<Sklep>();//Ekstensja klasy
	private static int nextID=0;//Numerowanie wystąpień klasy
	
	private int id;
	
	Adres adres;//Atrybut złożony
	
	protected List<SklepPracownikTTW> pracownicy;	//Atrybut powtarzalny {Sorted}
	protected Map<Produkt,Integer> inwentarz; //Atrybut powtarzalny
	
	//Ograniczenie Subset
	protected Pracownik kierownik;
	
	//Ograniczenie XOR
	protected Budynek budynek;
	protected Kompleks kompleks;
	
	//CONSTRUCTORS ==========================================================================
	public Sklep(Adres adres, Budynek budynek) {
		id=nextID;
		nextID++;

		pracownicy=new ArrayList<SklepPracownikTTW>();
		inwentarz = new TreeMap<Produkt,Integer>();
		
		if(checkAdres(adres)) 
			throw new IllegalArgumentException("Adress is already occupied");
		this.adres=adres;
		
		this.budynek=budynek;
		
		Extent.add(this);//Dodanie do ekstensji
	}
	
	public Sklep(Adres adres, Kompleks kompleks) {
		id=nextID;
		nextID++;

		pracownicy=new ArrayList<SklepPracownikTTW>();
		inwentarz = new TreeMap<Produkt,Integer>();
		
		if(checkAdres(adres)) 
			throw new IllegalArgumentException("Adress is already occupied");
		this.adres=adres;
		
		this.kompleks=kompleks;
		
		Extent.add(this);//Dodanie do ekstensji
	}
	//=======================================================================================
	
	//UTILITIES =============================================================================	
	public boolean equals(Object obj) {//Porównywanie obiektów
		if(((Sklep)obj).id==id)
			return true;
		return false;
	}
	public String toString() {return id+" "+adres.toString();}
	public String getDetails() {
		return adres+"\nIlosc pracowników: "+pracownicy.size()+"\nLokacja: "+(budynek!=null ? budynek.toDetails() : kompleks.toDetails());
	}
	private static boolean checkAdres(Adres a) {//Metoda sprawdza czy dany adres jest już używany w sklepie
		for(int i=0; i<Extent.size();i++) {//Przechodzimy przez ekstensję klasy
			if(a.equals(Extent.get(i).getAdres()))//Sprawdzamy czy dany sklep posiada sprawdzany adres
				return true;
		}
		return false;
	}
	//=======================================================================================
	
	//GETTERS ===============================================================================
	public int getID() {return id;}
	public Adres getAdres() {return adres;}
	public Pracownik getPracownik(int i) {return pracownicy.get(i).getPracownik();}
	public int getHours(int i) {return pracownicy.get(i).getHours();}
	public Pracownik getKierownik() {return kierownik;}
	public Kompleks getKompleks() {
		if(budynek!=null)
			throw new IllegalArgumentException("Sklep znajduje sie w pojedynczym budynku.");
		return kompleks;
	}
	public Budynek getBudynek() {
		if(kompleks!=null)
			throw new IllegalArgumentException("Sklep znajduje sie w kompleksie.");
		return budynek;
	}
	//public static List<Sklep> getExtent() {return Extent;}
	//=======================================================================================
	
	//SETTERS ===============================================================================
	public void setAdres(Adres adres) {//Ograniczenie {Unique}
		if(checkAdres(adres)) //Jeżeli adres jest już używany przez inny sklepy wychodzimy za pomocą IllegalArgumentException
			throw new IllegalArgumentException("Adress is already occupied");
		this.adres=adres;
	}
	public void setHours(int i,int h) {	pracownicy.get(i).setHours(h);}
	public void setBudynek(Budynek b) {//Ograniczenie {xor}
		if(kompleks!=null)//Jeżeli przeciwna relacja istnieje to jest ona usuwana
			kompleks=null;//*/throw new IllegalArgumentException("Sklep musi być częścią kompleksu.");
		budynek=b;
	}
	public void setKompleks(Kompleks k)  {//Ograniczenie {xor}
		if(budynek!=null)//Jeżeli przeciwna relacja istnieje to jest ona usuwana
			budynek=null;//*/throw new IllegalArgumentException("Sklep musi znajdować się w budynku.");
		kompleks=k;
	}
	public boolean setKierownik(Pracownik p) {//Ograniczenie {subset}
		if(!containsPracownik(p))//Zabezpieczenie przeciwko ustawieniu kierownika spoza sklepu
			return false;
		kierownik=p;
		return true;
	}
	//=======================================================================================
	
	//PRACOWNIK =============================================================================
	public boolean addPracownik(Pracownik p) {//ASOCJACJA Z ATRYBUTEM
		if(!containsPracownik(p)) {//Sprawdzamy czy asocjacja już istnieje, w celu zapobiegnięcia zapętlaniu
			SklepPracownikTTW.Create(this,p);//Zainicjowanie tworzenia asocjacji z atrybutem
			return true;
		}
		return false;
	}
	
	public boolean addPracownik(SklepPracownikTTW spttw) {
		if(!pracownicy.contains(spttw) && spttw.getSklep()==this) {//Sprawdzamy czy asocjacja już istnieje oraz czy odnosi się ona do tego konkretnego sklepu
			pracownicy.add(spttw);//Dodanie nowej asocjacji
			Collections.sort(pracownicy);//Sortowanie w ramach parametru {sorted}
			//spttw.getPracownik().setSklep(spttw);//Zwrotne ustawienie pracownika
			return true;
		}
		return false;
	}
	
	public List<Pracownik> getPracownicy() {//ASOCJACJA ZWYKLA
		List<Pracownik> temp=new ArrayList<Pracownik>();
		for(int i=0; i<pracownicy.size();i++) {
			temp.add(pracownicy.get(i).getPracownik());
		}
		/*int i=0;
		List<Pracownik> temp=new ArrayList<Pracownik>();
		while(i<pracownicy.size()) {
			temp.add(Pracownik.get(pracownicy.get(i)));
			i++;
		}*/
		return temp;
	}
	
	public boolean containsPracownik(Pracownik p) {
		int i=0;
		while(i<pracownicy.size()) {
			if(p==pracownicy.get(i).getPracownik())
				return true;
			i++;
		}
		
		return false;
	}
	
	public boolean removePracownik(Pracownik p) {
		int i=0;
		while(i<pracownicy.size()) {
			if(p==pracownicy.get(i).getPracownik()) {
				SklepPracownikTTW temp=pracownicy.get(i);
				if(kierownik != null && kierownik.equals(temp.getPracownik()))//Usuwanie kierownika
					kierownik=null;
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
	//=======================================================================================
	
	//INWENTARZ =============================================================================
	public boolean removeProdukt(Produkt p) {//Usuwanie produktów z inwentarzu sklepu
		if(inwentarz.containsKey(p)) {//W przypadku gdy inwentarz nie posiada podanego produktu, metoda jest zamykana z negatywnym wynikiem
				inwentarz.remove(p);
				p.removeSklep(this);//Asocjacja wtórna
				return true;
		}
		return false;
	}
	
	public boolean increase(Produkt p, int amount){
		if(amount<1)return false;//Zabezpieczenie przeciwko używaniu ujemnych lub zerowych wartości
		if(inwentarz.containsKey(p)) {//Sprawdzamy czy produkt jest już przechowywany
			inwentarz.replace(p, inwentarz.get(p)+amount);//Zmiana ilości produktu w inwentarzu
			return true;
		}
		return false;
	}
	
	public boolean decrease(Produkt p, int amount){
		if(inwentarz.containsKey(p)) {//W przypadku gdy inwentarz nie posiada podanego produktu, metoda jest zamykana z negatywnym wynikiem
			int am=inwentarz.get(p);//Zmienna przechowująca ilość produktu przed wykonaniem operacji
			if(am==amount) {//W przypadku gdy ilość usuwanego produktu jest równa całkowitej jego ilości w sklepie, klucz zostaje usunięty
				removeProdukt(p);//Usuwanie relacji
				return true;
			}
			if(am<amount || amount<0)//W przypadku gdy próbujemy usunąć ujemną ilość lub więcej produktów niż jest w inwentarzu, metoda jest zamykana z negatywnym wynikiem
				return false;
			inwentarz.replace(p, am-amount);//Zmiana ilości produktu w inwentarzu
			return true;
		}
		return false;
	}
	
	public boolean addProdukt(Produkt p, int initialAmount) {//Dodawanie produktów do inwentarzu sklepu
		//Zabezpieczenie przeciwko używaniu ujemnych lub zerowych wartości
		if(!(initialAmount<1) && !inwentarz.containsKey(p)) {//Sprawdzamy czy produkt jest już przechowywany
			inwentarz.put(p,initialAmount);//Tworzenie nowego rekordu
			p.addSklep(this,1);//Zwrotne dodanie produktu do sklepu
			return true;
		}
		
		return false;
	}
	
	public String GetInventoryData() {//Przekazuje string z sformatowaną zawartością inwentarzu
		String out="";
		ArrayList<Produkt> prod= new ArrayList<Produkt>(inwentarz.keySet());
		for(int i=0; i<prod.size();i++) {
			out+=prod.get(i)+"\t-\t"+inwentarz.get(prod.get(i))+"\n";
		}
		return out;
	}
	
	public boolean contains(Produkt p) {return inwentarz.containsKey(p);}
	public Produkt GetProdukt(int i) {return new ArrayList<Produkt>(inwentarz.keySet()).get(i);}
	public int GetAmount(Produkt p) {return (inwentarz.containsKey(p) ? inwentarz.get(p):0);}//Zwraca ilość produktu w inwentarzu
	public Map<Produkt, Integer> getInwentarz(){return inwentarz;}//Zwraca cały inwentarz
	public static List<Sklep> getSklepy(){return Extent;}
	//=======================================================================================
	
	//SERIALZATION ==========================================================================
	public static void loadExtent(String fileLoc) throws IOException,ClassNotFoundException {//Ładowanie ekstensji
		int maxID=nextID;//W celu poprawnego tworzenia przyszłych obiektów musimy być pewni, że przyszłe id nie będzie się pokrywało z ładowanymi obiektami, czyli że będzie większe od wszystkich nowododanych id
		Sklep s;
		
		FileInputStream fileIn = new FileInputStream(fileLoc);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int i =0;
        int l = (int) in.readObject();//Pierwsza wartość w pliku powinna być integerem oznaczającego liczbę rekordów
        while(i<l) {
	        s = (Sklep) in.readObject();//Odczytanie sklepu
	        Extent.add(s);//Dodanie sklepu do ekstensji
	        if(s instanceof SklepZSerwisem)//Dodawanie instancji SklepówZSerwisem do lokalnego ekstentu
	        	SklepZSerwisem.localExtent.add((SklepZSerwisem)s);
	        if(s.getID()>=maxID)//Sprawdzenie czy jego id jest większe-równe od aktualnej największej wartości
	        	maxID=s.getID()+1;//W przypadku gdy nowy rekord jest większy-równy, maxID jest aktualizowane
	        i++;
        }    
        in.close();
        fileIn.close();
        
        nextID=maxID;//Przypisanie nowego następnego id
	}
	public static void saveExtent(String fileLoc ) throws IOException {//Zapisywanie ekstensji
		FileOutputStream fileOut = new FileOutputStream(fileLoc);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(Extent.size());//Zapisanie ilości zapisywanych obiektów
	    for(int i=0;i<Extent.size();i++) {
			out.writeObject(Extent.get(i));//Zapisywanie Sklepu
	    }
	    out.close();
	    fileOut.close();
	}
	public static void loadExtent(List<Sklep> list) {
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
	//=======================================================================================
	
	//EXTENSION =============================================================================
	public static Sklep get(int id) {//Otrzymanie pojedyńczego sklepu z ekstensji
		int i=0;
		while(i<Extent.size()) {
			if(Extent.get(i).id==id)
				return Extent.get(i);
			i++;
		}
		return null;
	}
	
	public static boolean remove(int id) {//Usunięcie sklepu z ekstensji
		Sklep removed=Extent.get(id);
		if(removed==null)//Jeżeli sklepu nie ma w ekstensji to metoda kończy się z negatywnym wynikiem
			return false;
		
		//Usuwanie inwentarzu instancji
		Produkt[] p = (Produkt[]) removed.inwentarz.keySet().toArray();
		for(int i=0;i<p.length;i++) {
			removed.inwentarz.remove(p[i]);
		}
		Extent.remove(removed);
		return true;
	}
	//=======================================================================================
}
