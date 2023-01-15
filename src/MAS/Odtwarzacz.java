package MAS;

import java.util.EnumSet;
import java.util.Iterator;

enum TypOdtwarzacza{
	Gramofon,
	Magnetofon,
	CD
}

enum CassetteDolbyReduction{
	None,
	A,
	B,
	C,
	S
}

enum VinylSpeed {
	XXXIII_I_III_RPM ("33 1/3RPM"),
	XLVRPM ("45RPM"),
	LXXVIIIRPM ("78RPM");
	
	String name;
	
	private VinylSpeed(String name) {this.name=name;}
	public String toString() {return name;}
	
	public static VinylSpeed getByName(String name) {
		for (VinylSpeed vs : VinylSpeed.values()) {
			if(vs.name.equals(name))
				return vs;
		}
		return null;
	}
}

enum VinylControlType{
	Automatic,
	SemiAutomatic,
	Manual
}

public class Odtwarzacz extends Produkt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected EnumSet<TypOdtwarzacza> typy;
	
	protected boolean wyjscieNaSluchawki;
	protected boolean radio;
	//Winyl properties
	protected EnumSet<VinylSpeed> speed;
	protected VinylControlType controlType;
	//Magnetofon properties
	protected CassetteDolbyReduction CassetteDBNR;
	protected boolean CassetterCrOMetalSupport;
	//CD Properties
	protected boolean CDromSupport;
	
	public Odtwarzacz(String name, String kodFirmowy, EnumSet<TypOdtwarzacza> typy) {
		super(name, kodFirmowy);
		this.typy=typy;
	}
	
	//GETTERS ===============================================================================
	public boolean getAudioOutput() {return wyjscieNaSluchawki;}
	public boolean getHasRadio() {return radio;}
		//Winyl
	public String getSpeedModes() throws Exception{
		if(!typy.contains(TypOdtwarzacza.Gramofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		Iterator<VinylSpeed> i=speed.iterator();
		String out="";
		while(i.hasNext()) {
			out+=" "+i+",";
		}
		return out.substring(0, out.length()-1);
	}
	public VinylControlType getControlType() throws Exception{
		if(!typy.contains(TypOdtwarzacza.Gramofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		return controlType;
	}
		//Magnetofon
	public CassetteDolbyReduction getDolbyReduction() throws Exception{
		if(!typy.contains(TypOdtwarzacza.Magnetofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		return CassetteDBNR;
	}
	public boolean getCrOMetalSupport() throws Exception{
		if(!typy.contains(TypOdtwarzacza.Magnetofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		return CassetterCrOMetalSupport;
	}
		//CD
	public boolean getCDRomSupport() throws Exception{
		if(!typy.contains(TypOdtwarzacza.CD))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		return CDromSupport;
	}
	//=======================================================================================
	//SETTERS ===============================================================================
	public boolean setAudioOutput() {return wyjscieNaSluchawki;}
	public boolean setHasRadio() {return radio;}
		//Winyl
	public void setSpeedModes(EnumSet<VinylSpeed> speed) throws Exception{
		if(!typy.contains(TypOdtwarzacza.Gramofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		this.speed=speed;
	}
	public void setControlType(VinylControlType vct) throws Exception{
		if(!typy.contains(TypOdtwarzacza.Gramofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		this.controlType=vct;
	}
		//Magnetofon
	public void setDolbyReduction(CassetteDolbyReduction cdr) throws Exception{
		if(!typy.contains(TypOdtwarzacza.Magnetofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		CassetteDBNR=cdr;
	}
	public void setCrOMetalSupport(boolean CrOMS) throws Exception{
		if(!typy.contains(TypOdtwarzacza.Magnetofon))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		CassetterCrOMetalSupport = CrOMS;
	}
		//CD
	public void setCDRomSupport(boolean cdrs) throws Exception{
		if(!typy.contains(TypOdtwarzacza.CD))//Sprawdzanie czy należy do odpowiedniej klasy
			throw new Exception("Invalid Odtwarzacz type");
		CDromSupport = cdrs;
	}
	//=======================================================================================
	
	public String toString(){
		String out=name+" odtwarzacz";
		for(int i=0; i<typy.size();i++) {
			if(typy.contains(TypOdtwarzacza.Gramofon))
				out+=" Gramofon,";
			if(typy.contains(TypOdtwarzacza.Magnetofon))
				out+=" Magnetofon,";
			if(typy.contains(TypOdtwarzacza.CD))
				out+=" CD,";
		}
		return out.substring(0, out.length()-1);
	}
}
