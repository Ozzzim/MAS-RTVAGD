package MAS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
//import java.io.Serializable;
//import java.io.*;
import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class Pracownik implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int nextID=0;
	protected static List<Pracownik> Extent=new ArrayList<Pracownik>();
	
	protected transient int id=-1;
	protected String name;
	protected boolean sex;
	protected int telefon;
	protected SklepPracownikTTW workplace;//Asocjacja z atrybutem
	
	protected Adres adres;		//Argument Opcjonalny, Złożony
	protected String numerKonta;//Argument Opcjonalny. Integer jest za krótki na przetrzymywanie numerów kont bankowych, więc są one przechowywane w stringu który jest sprawdzany
	
	protected static BigDecimal minimumWage=new BigDecimal(18.30);//Atrybut klasowy
	
	protected List<NagrodaPracownikY> recievedAwarads;
	
	//CONSTRUCTORS =======================================================
	public Pracownik(String name,boolean sex,int telefon) {
		this.id=nextID;
		nextID++;
		
		this.name=name;
		this.sex=sex;
		this.telefon=telefon;
		recievedAwarads = new ArrayList<NagrodaPracownikY>();
		
		Extent.add(this);
	}
	public Pracownik(String name,boolean sex,int telefon, String numerkonta) {
		this.id=nextID;
		nextID++;
		
		this.name=name;
		this.sex=sex;
		
		this.telefon=telefon;
		this.setAccount(numerkonta);//Zapobiega przypisaniu niepoprawnego numeru konta
		
		recievedAwarads = new ArrayList<NagrodaPracownikY>();
		
		Extent.add(this);
	}
	public Pracownik(String name,boolean sex,int telefon, Adres adres) {
		this.id=nextID;
		nextID++;
		
		this.name=name;
		this.sex=sex;
		
		this.telefon=telefon;
		this.adres=adres;
		
		recievedAwarads = new ArrayList<NagrodaPracownikY>();
		
		Extent.add(this);
	}
	public Pracownik(String name,boolean sex,int telefon,String numerkonta,Adres adres) {
		this.id=nextID;
		nextID++;
		
		this.name=name;
		this.sex=sex;
		
		this.telefon=telefon;
		this.adres=adres;
		this.setAccount(numerkonta);//Zapobiega przypisaniu niepoprawnego numeru konta
		//this.numerKonta=numerkonta;
		
		Extent.add(this);
	}
	//====================================================================
	
	//GETTERS ============================================================
	public int getID() {return id;}
	public String getName() {return name;}
	public boolean getSex() {return sex;}
	public String getSexDesc() {if(sex){return "male";}return "female";}//Zwraca słowny opis płci Pracownika
	public int getPhone() {return telefon;}
	public Adres getAdres() {return adres;}
	public String getAccount() {return numerKonta;}
	public abstract String getType();
	public static Pracownik get(int i) {return Extent.get(i);}
	public static List<Pracownik> getPracownicy(){return Extent;}
	public static int count() {return Extent.size();}
	public static BigDecimal getMinimumWage() {return minimumWage;} 
	public Sklep getSklep() {if(workplace!=null)return workplace.getSklep();return null;}
	public int getHours() {if(workplace==null)return 0; else return workplace.getHours();}
	public List<NagrodaPracownikY> getNagrody() {return recievedAwarads;}
	public abstract String getDetails();
	public abstract BigDecimal getWage();//Przesłonięcie
	//====================================================================
	
	//SETTERS ============================================================
	public boolean setName(String newName) {
		this.name=newName;
		return true;
	}
	public boolean setSex(boolean newSex) {
		this.sex=newSex;
		return true;
	}
	public boolean setAdres(Adres newAdres) {
		this.adres=newAdres;
		return true;
	}
	public boolean setPhone(int newTelefon) {
		this.telefon=newTelefon;
		return true;
	}
	public boolean setAccount(String newNumerKonta) {
		if(Pattern.matches("[0-9]+", newNumerKonta) == true) {//Sprawdzanie czy numer konta składa się z samych cyfr
			this.numerKonta=newNumerKonta;
			return true;
		}
		return false;
	}
	public boolean setHours(int h) {
		if(workplace!=null)
			return workplace.setHours(h);
		return false;
	}
	
	//SKLEP ASSOSIATION ==================================================
	public boolean setSklep(Sklep s) {
		if(s==null)
			return false;
		
		if(workplace!=null) //W przypadku gdy wymieniamy miejsce pracy usuwamy poprzednią asocjację przed stworzeniem nowej
			workplace.remove();
		
		workplace=SklepPracownikTTW.Create(s, this);//Zainicjowanie tworzenia asocjacji z atrybutem
		return workplace!=null;
	}
	
	public boolean setSklep(SklepPracownikTTW spttw) {//Wersja funkcji setSklep przyjmująca gotowy obiekt spttw
		if(spttw.getPracownik()!=this)//Funkcja nie przyjmie spttw który się nie odnosi do tej instancji pracownika
			return false;
		if( workplace==null) {//W przypadku gdy pracownik nie jest powiązany jeszcze z żadnym sklepem
			workplace=spttw;
			return true;
		}
		if(spttw==null || workplace.getSklep()!=spttw.getSklep()) {//W przypadku gdy zmieniamy/czyścimy odnośnik do sklepu
			SklepPracownikTTW temp=workplace;//Tworzymy obiekt tymczasowy w celu późniejszego zwrotnego usunięcia
			workplace=spttw;
			temp.remove();//Usunięcie zwrotne referencji z poprzedniego sklepu
			return true;
		} 
		return false;
	}
	
	public void setSklepNull(){
		if(workplace!=null) {//Zabezpieczenie przeciwko nullpointerexception
			SklepPracownikTTW temp=workplace;//Tworzymy obiekt tymczasowy w celu późniejszego zwrotnego usunięcia
			workplace=null;
			temp.remove();
		}
	}
	//====================================================================
	
	protected static boolean addToExtent(Pracownik p) {//Dodaje pracownika do extentu
		for(int i=0;i<Extent.size();i++) {
			if(p==Extent.get(i)) {//W przypadku wystąpienia pracownika metoda kończy się bez dodawania
				return false;
			}
		}
		p.id=nextID;
		nextID++;
		Extent.add(p);
		return true;
	}
	
	public static BigDecimal getWageSpendings() {//Metoda klasowa
		BigDecimal sum = new BigDecimal(0);
		for(int i=0;i<Extent.size();i++) {
			sum=sum.add(Extent.get(i).getWage());
		}
		return sum;
	}
	
	public String toString() {
		return name;
	}
	
	public void giveAward(Nagroda n) {
		NagrodaPracownikY temp = new NagrodaPracownikY(n,this,Year.now().getValue());//Utworzenie nowej relacji pomiędzy pracownikiem a nagrodą
		recievedAwarads.add(temp);
		n.addRecord(temp);//Zwrotne przydzielenie nagrody pracownikowi
	}
	
	public void giveAward(Nagroda n, int y) {//Wersja z własnym przypisaniem roku
		NagrodaPracownikY temp = new NagrodaPracownikY(n,this,y);//Utworzenie nowej relacji pomiędzy pracownikiem a nagrodą
		recievedAwarads.add(temp);
		n.addRecord(temp);//Zwrotne przydzielenie pracownikowi nagrody
	}
	
	public boolean addRecord(NagrodaPracownikY npy) {
		if(npy.getPracownik()==this) {
			recievedAwarads.add(npy);
			return true;
		}
		return false;
	}
	
	public boolean isKierownik() {//Ograniczenie Subset
		if(workplace!=null && workplace.getSklep().getKierownik()!=null)//Zabezpieczenie w przypadku gdy pracownik/kierownik nie został jeszcze przydzielony do sklepu
			return workplace.getSklep().getKierownik().equals(this);//Sprawdzamy czy kierownik sklepu danego pracownika jest tą samą osobą
		return false;
	}
	
	//SERIALZATION =======================================================
	public static void loadExtent(String fileLoc) throws IOException,ClassNotFoundException {
		int maxID=nextID;
		Pracownik p;
		
		FileInputStream fileIn = new FileInputStream(fileLoc);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int i =0;
        int l = (int) in.readObject();
        while(i<l) {//in.available()>0) {
	        p = (Pracownik) in.readObject();
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
	public static void loadExtent(List<Pracownik> list) {
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
