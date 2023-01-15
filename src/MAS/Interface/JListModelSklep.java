package MAS.Interface;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import MAS.Sklep;

public class JListModelSklep implements ListModel<Sklep> {
	
	public List<Sklep> sklepy;
	
	public JListModelSklep(List<Sklep> sklepy) {
		this.sklepy=sklepy;
	}
	
	@Override
	public Sklep getElementAt(int arg0) {
		return sklepy.get(arg0);
	}

	@Override
	public int getSize() {
		return sklepy.size();
	}

	
	@Override
	public void removeListDataListener(ListDataListener arg0) {}
	@Override
	public void addListDataListener(ListDataListener arg0) {}
}
