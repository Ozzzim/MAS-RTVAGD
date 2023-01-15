package MAS.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;

import MAS.Pracownik;

public class JPracownikDisplay extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	JPanel leftSideTable;
	JTable produktDataTable;
	JPanel sideData;
		JTextArea pracownikDetails;


	public JPracownikDisplay() {
		super(new BorderLayout());
		
			leftSideTable=new JPanel(new BorderLayout());
				produktDataTable=new JTable(new PracownikTableModel(Pracownik.getPracownicy()));
				//produktDataTable.setMinimumSize(new Dimension(100,100));
				//produktDataTable.setPreferredSize(new Dimension(400,200));
				produktDataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				produktDataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						pracownikDetails.setText(Pracownik.get(produktDataTable.getSelectedRow()).getDetails());
					}
				});
			leftSideTable.add(new JScrollPane(produktDataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
			leftSideTable.add(new JLabel("Data"),BorderLayout.NORTH);
			sideData=new JPanel(new BorderLayout());
			sideData.add(new JLabel("Detale"),BorderLayout.NORTH);
				pracownikDetails=new JTextArea();
				pracownikDetails.setEditable(false);
			sideData.add(pracownikDetails,BorderLayout.CENTER);
			sideData.setPreferredSize(new Dimension(200,0));
			
		this.add(leftSideTable,BorderLayout.CENTER);
		this.add(sideData,BorderLayout.EAST);
	}
}