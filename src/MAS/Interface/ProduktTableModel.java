package MAS.Interface;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import MAS.Produkt;

public class ProduktTableModel implements TableModel{

	List<Produkt> produkty;
	
	public ProduktTableModel(List<Produkt> produkty) {
		this.produkty=produkty;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return String.class;
		case 1:
			return BigDecimal.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return "Nazwa produktu";
		case 1:
			return "Cena";
		case 2:
			return "Producent";
		case 3:
			return "Kod";
		default:
			return "N/A";
		}
	}

	@Override
	public int getRowCount() {
		return produkty.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return produkty.get(rowIndex).getNazwa();
		case 1:
			return produkty.get(rowIndex).getCena();
		case 2:
			return produkty.get(rowIndex).getProducent();
		case 3:
			return produkty.get(rowIndex).getKodFirmowy();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
	
	public void addTableModelListener(TableModelListener arg0) {}
	public void removeTableModelListener(TableModelListener l) {}

	

}
