package MAS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ekspedient extends Pracownik {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int localID;
	private static int nextlocalID;
	protected static List<Ekspedient> localExtent=new ArrayList<Ekspedient>();
	
	protected static BigDecimal baseWage=new BigDecimal(30);
	protected int hoursWorked=0;
	
	//CONSTRUCTORS =======================================================
	public Ekspedient(String name, boolean sex, int telefon) {
		super(name, sex, telefon);
		
		this.localID=nextlocalID;
		nextlocalID++;
		
		localExtent.add(this);
	}
	
	public Ekspedient(String name,boolean sex,int telefon, String numerkonta) {
		super(name, sex, telefon, numerkonta);
		
		this.localID=nextlocalID;
		nextlocalID++;
		
		localExtent.add(this);
	}
	public Ekspedient(String name,boolean sex,int telefon, Adres adres) {
		super(name,sex,telefon,adres);
		
		this.localID=nextlocalID;
		nextlocalID++;
		
		localExtent.add(this);
	}
	public Ekspedient(String name,boolean sex,int telefon,String numerkonta,Adres adres) {
		super(name, sex, telefon, numerkonta, adres);
		
		this.localID=nextlocalID;
		nextlocalID++;
		
		localExtent.add(this);
	}
	//====================================================================
	
	//GETTERS ============================================================
	public int getLocalID() {return localID;}
	public static Ekspedient get(int i) {return localExtent.get(i);}
	public static int count() {return localExtent.size();}
	@Override
	public BigDecimal getWage() {return baseWage.multiply(new BigDecimal(hoursWorked));}//Atrybut pochodny

	public void setHW(int h) {hoursWorked=h;}
	public static boolean setBaseWage(BigDecimal b) {//Ograniczenie atrybutu
		if(b.compareTo(minimumWage)<0)//Jeżeli nowa wartość jest mniejsza od stawki minimalnej, program wychodzi bez zmian z wynikiem negatywnym
			return false;
		baseWage=b;
		return true;
	}
	public String getType(){return "Ekspedient";}
	public String getDetails() {
		return "Ekspedient\n\nIlość godzin w tym miesiącu: "+hoursWorked;
	}
	//====================================================================
	
	//SERIALZATION =======================================================
	public static void loadExtent(String fileLoc) throws IOException,ClassNotFoundException {//Ładowanie ekstensji
		int maxID=nextlocalID;//W celu poprawnego tworzenia przyszłych obiektów musimy być pewni, że przyszłe id nie będzie się pokrywało z ładowanymi obiektami, czyli że będzie większe od wszystkich nowododanych id
		Ekspedient p;
		
		FileInputStream fileIn = new FileInputStream(fileLoc);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int i =0;
        int l = (int) in.readObject();//Pierwsza wartość w pliku powinna być integerem oznaczającego liczbę rekordów
        while(i<l) {//in.available()>0) {
	        p = (Ekspedient) in.readObject();//Odczytanie ekspedienta
	        localExtent.add(p);//Dodanie ekspedienta do ekstensji
	        Pracownik.addToExtent(p);
	        if(p.getID()>=maxID)//Sprawdzenie czy jego id jest większe-równe od aktualnej największej wartości
	        	maxID=p.getID()+1;//W przypadku gdy nowy rekord jest większy-równy, maxID jest aktualizowane
	        i++;
        }    
        in.close();
        fileIn.close();
        
        nextlocalID=maxID;//Przypisanie nowego następnego id
	}
	
	public static void saveExtent(String fileLoc ) throws IOException {//Zapisywanie ekstensji
		FileOutputStream fileOut = new FileOutputStream(fileLoc);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(localExtent.size());//Zapisanie ilości zapisywanych obiektów
	    for(int i=0;i<localExtent.size();i++) {
			out.writeObject(localExtent.get(i));//Zapisywanie ekspedienta
	    }
	    out.close();
	    fileOut.close();
	}
	//====================================================================
}
