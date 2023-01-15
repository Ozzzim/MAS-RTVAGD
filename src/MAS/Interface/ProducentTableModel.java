package MAS.Interface;

import java.util.List;
import java.util.Locale;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import MAS.Adres;
import MAS.Producent;
import MAS.ProducentLokalny;
import MAS.ProducentZagraniczny;

public class ProducentTableModel implements TableModel{

	List<Producent> producenci;
	
	ProducentTableModel(List<Producent> producenci){
		this.producenci = producenci;
	}
	

	@Override
	public Class<?> getColumnClass(int arg0) {
		switch(arg0) {
		case 0:
			return String.class;//Nazwa
		case 1:
			return String.class;//Typ
		case 2:
			return int.class;//Ilość produktów
		case 3:
			return int.class;//Numer kontaktowy
		case 4:
			return String.class;//Numer konta
		case 5:
			return Adres.class;//Adres (Lokalny)
		case 6:
			return Locale.class;//Kraj (Zagraniczny)
		case 7:
			return boolean.class;//Czy importowany (Zagraniczny)
		//...
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int arg0) {
		switch(arg0) {
		case 0:
			return "Nazwa";
		case 1:
			return "Typ";
		case 2:
			return "Produkty";
		case 3:
			return "Numer kontaktowy";
		case 4:
			return "Numer konta";
		case 5:
			return "Adres";
		case 6:
			return "Kraj";
		case 7:
			return "Import";
		//...
		default:
			return "N/A";
		}
	}

	@Override
	public int getRowCount() {
		return producenci.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch(arg1) {
		case 0:
			return producenci.get(arg0).getName();
		case 1:
			return producenci.get(arg0).getType();
		case 2:
			return producenci.get(arg0).getProduktAmount();
		case 3:
			return (producenci.get(arg0).getNumerKontatkowy()==0 ? null:producenci.get(arg0).getNumerKontatkowy());
		case 4:
			return producenci.get(arg0).getNumerKonta();
		case 5:
			return (producenci.get(arg0) instanceof ProducentLokalny ? ((ProducentLokalny)producenci.get(arg0)).getAdres():null);
		case 6:
			return (producenci.get(arg0) instanceof ProducentZagraniczny ? ((ProducentZagraniczny)producenci.get(arg0)).getKraj():null);
		case 7:
			return (producenci.get(arg0) instanceof ProducentZagraniczny ? ((ProducentZagraniczny)producenci.get(arg0)).isImported():null);
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
