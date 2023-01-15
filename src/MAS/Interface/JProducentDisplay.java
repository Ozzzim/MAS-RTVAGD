package MAS.Interface;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import MAS.Producent;

public class JProducentDisplay extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTable table;
	
	JProducentDisplay(){
		super(new BorderLayout());
		
		
		table=new JTable(new ProducentTableModel(Producent.getProducenci()));
		this.add(new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
	}
	
}
