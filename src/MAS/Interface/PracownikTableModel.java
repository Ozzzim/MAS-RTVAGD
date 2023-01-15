package MAS.Interface;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import MAS.Adres;
import MAS.Pracownik;

public class PracownikTableModel implements TableModel {
	
	List<Pracownik> pracownicy;
	
	PracownikTableModel(List<Pracownik> pracownicy){
		this.pracownicy = pracownicy;
	}
	

	@Override
	public Class<?> getColumnClass(int arg0) {
		switch(arg0) {
		case 0:
			return String.class;//Imie i nazwisko
		case 1:
			return String.class;//Plec
		case 2:
			return String.class;//Klasa pracownika
		case 3:
			return BigDecimal.class;//Placa
		case 4:
			return int.class;//telefon
		case 5:
			return Adres.class;//Adres
		case 6:
			return String.class;//Numer konta
		case 7:
			return int.class;//ID Sklepu
		//...
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public String getColumnName(int arg0) {
		switch(arg0) {
		case 0:
			return "Imię";
		case 1:
			return "Płeć";//Plec
		case 2:
			return "Zawód";//Klasa pracownika
		case 3:
			return "Płaca";//Placa
		case 4:
			return "Telefon";//telefon
		case 5:
			return "Adres";//Adres
		case 6:
			return "Numer konta";//Numer konta
		case 7:
			return "ID Sklepu";
		//...
		default:
			return "N/A";
		}
	}

	@Override
	public int getRowCount() {
		return pracownicy.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch(arg1) {
		case 0:
			return pracownicy.get(arg0).getName();
		case 1:
			return pracownicy.get(arg0).getSexDesc();//Plec
		case 2:
			return pracownicy.get(arg0).getType();//Klasa pracownika
		case 3:
			return pracownicy.get(arg0).getWage();//Placa
		case 4:
			return pracownicy.get(arg0).getPhone();//telefon
		case 5:
			return pracownicy.get(arg0).getAdres();//Adres
		case 6:
			return pracownicy.get(arg0).getAccount();//Numer konta
		case 7:
			return (pracownicy.get(arg0).getSklep()!=null ? pracownicy.get(arg0).getSklep().getID() : null);
		//...
		default:
			return "N/A";
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {	return false;}
	public void setValueAt(Object arg0, int arg1, int arg2) {}

	public void removeTableModelListener(TableModelListener arg0) {}
	public void addTableModelListener(TableModelListener arg0) {}
}
