package MAS.Interface;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import MAS.Produkt;
import MAS.Sklep;

public class InventoryTableModel implements TableModel {
	
	protected Sklep sklep;
	
	public InventoryTableModel( Sklep sklep) {
		this.sklep=sklep;
	}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		switch(arg0) {
		case 0:
			return String.class;
		case 1:
			return Integer.class;
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int arg0) {
		switch(arg0) {
		case 0:
			return "Nazwa";
		case 1:
			return "Ilosc";
		default:
			return "N/A";
		}
	}

	@Override
	public int getRowCount() {
		return sklep.getInwentarz().size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Produkt p=sklep.GetProdukt(arg0);
		switch(arg1) {
		case 0:
			return p;
		case 1:
			return sklep.getInwentarz().get(p);
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
