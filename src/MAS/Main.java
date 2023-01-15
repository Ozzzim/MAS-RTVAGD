package MAS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import MAS.Interface.Mainframe;

public class Main {
	
	//<!>Ścieżka do pliku z zapisanymi obiektami. Proszę wstawić własną ścieżkę z nazwą pliku.<!>
	//<!>Database file path. Please input a location before attempting to run the program.<!>
	public static String location = "";//Example "C:\\Users\\User\\Desktop\\Storage"
	public static String fileName = "";//Example "\\databaseFile.json"

	static boolean loaded=false;//Flaga do oznaczania poprawnego ładowania ekspedientów. Zapobiega dodawaniu nowych użytkowników za każdym włączeniem programu
	
	public static void main(String[] args) {
		if(location.equals("") || fileName.equals("")) {//Display notification in case someone tries to run it without declaring database location
			new JGenericConfirmWindow("Error","Please declare data storage location and/or name in Main.java l:21");
			return;
		}
		try {
			loadExtents(location);
			loaded=true;
			System.out.println("Załadowano wszystkie pliki pomyślnie");
		}
		catch (ClassNotFoundException e) {} 
		catch (IOException e) {}
		
		if(!loaded) {
			System.out.println("Nie znaleziono pliku do odczytania. Generuję nowe dane");
			
			//Deklarowanie sklepów
			new Sklep(new Adres("Polska", "Warszawa", "al. Marchewkowa", 49),new Kompleks("Galeria Marchwkowa", new BigDecimal(1100), 0, 100));//Utworzenie sklepu
			SklepZSerwisem sklepZS = new SklepZSerwisem(new Adres("Polska", "Warszawa", "Ul. Uliczna", 32),new Budynek("Budynek Uliczny A", 150, 2));
			new Sklep(new Adres("Polska ","Lublin","ul. Warhola",1),new Kompleks("Galeria Romanowska", new BigDecimal(1),1,1));
			new SklepZSerwisem(new Adres("Polska ","Poznan","ul. Dali",1),new Kompleks("Pasaż Nowa Garbarnia", new BigDecimal(1),1,1));
			new Sklep(new Adres("Polska ","Wroclaw","ul. Viana",1),new Kompleks("Dom Kultury Wrocław", new BigDecimal(1),1,1));
			
			sklepZS.setServiceOpeningTime(SklepZSerwisem.makeLocalTime(12, 30));
			sklepZS.setServiceClosingTime(SklepZSerwisem.makeLocalTime(17, 30));
			((SklepZSerwisem)Sklep.get(3)).setServiceOpeningTime(SklepZSerwisem.makeLocalTime(11, 0));
			((SklepZSerwisem)Sklep.get(3)).setServiceClosingTime(SklepZSerwisem.makeLocalTime(16, 0));
			
			//Deklarowanie produktów
			Produkt magnetofon = new Produkt("Magnetofon","WM-5000", new BigDecimal(99.99).setScale(2, RoundingMode.DOWN));
			Produkt kaseta = new Produkt("Kaseta magnetofonowa","MetalSClass",new BigDecimal(14.99).setScale(2, RoundingMode.DOWN));
			Produkt konsola = new Produkt("Konsola do gier","GameStation3",new BigDecimal(1599.99).setScale(2, RoundingMode.DOWN));
			Produkt karta = new Produkt("Karta Graficzna","ChromiumGTY2010",new BigDecimal(1999.99).setScale(2, RoundingMode.DOWN));
			Produkt glosniki = new Produkt("Glosniki","TanneyGrandBass",new BigDecimal(249.99).setScale(2, RoundingMode.DOWN));
			
			//Deklarowanie producentów
			Producent tony = new ProducentZagraniczny("Tony",Locale.US,false,"4349504395534",432432432);
				tony.addProdukt(magnetofon);
				tony.addProdukt(kaseta);
				tony.addProdukt(konsola);
			Producent tachyon = new ProducentZagraniczny("Tachyon",Locale.CHINA, true);
				tachyon.addProdukt(karta);
			Producent mango = new ProducentLokalny("Mango", new Adres("Polska", "Poznań", "ul. Głośnikowa", 20),"331249203333",123123123);
				mango.addProdukt(glosniki);
			
			//Dodawanie produktów do sklepów 
			int i=0;
			for (Sklep s : Sklep.getSklepy()) {
				i++;
				for(int n=0; n<Produkt.getProdukty().size();n++) {
					s.addProdukt(Produkt.get(n), ((n+i)%30)*15);
				}
	        }
			
			new Produkt("Woltomierz","Volt03");//Testowy Produkt nie dodany do żadnego Sklepu;
			
			//Deklarowanie pracowników
			new Serwisant("Alexander Rutkowski", true, 957308988).addSerwis(5);
			new Ekspedient("Lucjan Szymański", true, 150688467).setHW(20);
			new Serwisant("Emil Brzeziński", true, 300720432,"997813280681517637").addSerwis(9);
			new Ekspedient("Norbert Jakubowski", true, 479835554, "884908164340508102").setHW(31);
			new Serwisant("Jan Michalak", true, 16420834, new Adres("Polska", "Warszawa", "ul. Truskawkowa", 4, 6)).addSerwis(2);
			new Ekspedient("Jagoda Czarnecka", false, 574414110,new Adres("Polska", "Warszawa", "ul. Malinowa", 3)).setHW(17);
			new Serwisant("Iga Jankowska", false, 314464881,"505486742508733322",new Adres("Polska", "Warszawa", "ul. Jagodowa", 32)).setKwalifikacja(Kwalifikacja.magister);
			new Ekspedient("Cecylia Zakrzewska", false, 769677902,"",new Adres("Polska", "Lublin","ul. Bananowa", 2,12)).setHW(44);
			new Serwisant("Klaudia Lewandowska", false, 82777507).setKwalifikacja(Kwalifikacja.inżynier);
			new Ekspedient("Lidia Krajewska", false, 420128729).setHW(1);
			new EkspedientSerwisant("Ambroży Adeptski", true,32133244).setKwalifikacja(Kwalifikacja.magister);
			
			//Dodawanie producentów do sklepów
			Sklep.get(1).addPracownik(Pracownik.get(0));
			Sklep.get(2).addPracownik(Pracownik.get(1));
			Sklep.get(1).addPracownik(Pracownik.get(2));
			Sklep.get(4).addPracownik(Pracownik.get(3));
			Sklep.get(1).addPracownik(Pracownik.get(4));
			Sklep.get(2).addPracownik(Pracownik.get(5));
			Sklep.get(3).addPracownik(Pracownik.get(6));
			Sklep.get(3).addPracownik(Pracownik.get(7));
			Sklep.get(3).addPracownik(Pracownik.get(8));
			Sklep.get(0).addPracownik(Pracownik.get(9));
			Sklep.get(3).addPracownik(Pracownik.get(10));
			
			try {
				saveExtents(location);
				System.out.println("Zapisano nowo stworzone dane.");
			} catch (IOException e) {e.printStackTrace();}
		}
		
		Mainframe a=new Mainframe();
		a.setVisible(true);
	}
	
	public static void loadExtents(String fileLoc) throws IOException,ClassNotFoundException {
		List<Pracownik> prac = new ArrayList<Pracownik>();
		List<Sklep> skle = new ArrayList<Sklep>();
		List<Produkt> prdt = new ArrayList<Produkt>();
		List<Producent> prdc = new ArrayList<Producent>();
		
		FileInputStream fileIn = new FileInputStream(fileLoc+fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        int i =0;
        int l = (int) in.readObject();
        
        while(i<l) {
	        prac.add(((Pracownik) in.readObject()));
	        i++;
        }    
        
        i =0;
        l = (int) in.readObject();
                
        while(i<l) {
	        skle.add(((Sklep) in.readObject()));
	        i++;
        }    
        
        i =0;
        l = (int) in.readObject();
        
        while(i<l) {
	        prdt.add(((Produkt) in.readObject()));
	        i++;
        }
        
        i =0;
        l = (int) in.readObject();
        
        while(i<l) {
	        prdc.add(((Producent) in.readObject()));
	        i++;
        }
        
        Pracownik.loadExtent(prac);
        Sklep.loadExtent(skle);
        Produkt.loadExtent(prdt);
        Producent.loadExtent(prdc);
        
        in.close();
        fileIn.close();
	}
	public static void saveExtents(String fileLoc ) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(fileLoc+fileName);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    //Order
	    	//Pracownik
	    	//Sklep
	    	//Produkt
	    	//Producent
	    
	    
	    out.writeObject(Pracownik.getPracownicy().size());//Save how many items are being recorded
	    for(int i=0;i<Pracownik.getPracownicy().size();i++) {
			out.writeObject(Pracownik.get(i));
	    }
	    
	    out.writeObject(Sklep.getSklepy().size());//Save how many items are being recorded
	    for(int i=0;i<Sklep.getSklepy().size();i++) {
			out.writeObject(Sklep.get(i));
	    }
	    
	    out.writeObject(Produkt.getProdukty().size());//Save how many items are being recorded
	    for(int i=0;i<Produkt.getProdukty().size();i++) {
			out.writeObject(Produkt.get(i));
	    }
	    
	    out.writeObject(Producent.getProducenci().size());//Save how many items are being recorded
	    for(int i=0;i<Producent.getProducenci().size();i++) {
			out.writeObject(Producent.get(i));
	    }
	    
	    out.close();
	    fileOut.close();
	}
}
