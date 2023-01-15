package MAS.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import MAS.Produkt;

public class JProduktDisplay extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel leftSideTable;
		JTable produktDataTable;
	JPanel sideData;
		JTextArea detailsText;
		JButton addToSklep;
	
	
	public JProduktDisplay() {
		super(new BorderLayout());
			
			leftSideTable=new JPanel(new BorderLayout());
				produktDataTable=new JTable(new ProduktTableModel(Produkt.getProdukty()));
				produktDataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				produktDataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
						detailsText.setText(Produkt.get(produktDataTable.getSelectedRow()).getDetails());
					}
				});
				//produktDataTable.setMinimumSize(new Dimension(100,100));
				//produktDataTable.setPreferredSize(new Dimension(400,200));
			leftSideTable.add(new JScrollPane(produktDataTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
			leftSideTable.add(new JLabel("Data"),BorderLayout.NORTH);
			sideData=new JPanel(new BorderLayout());
			sideData.add(new JLabel("Detale"),BorderLayout.NORTH);
			sideData.setPreferredSize(new Dimension(200,0));
				detailsText = new JTextArea();
				detailsText.setEditable(false);
				addToSklep= new JButton("Dodaj do sklepu");
				addToSklep.addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){
						if(produktDataTable.getSelectedRow()>0 || produktDataTable.isRowSelected(0)) {
							new JAddProduktToSklepForm(Produkt.get(produktDataTable.getSelectedRow()),getThis());
						}
					}
				});
			sideData.add(addToSklep,BorderLayout.SOUTH);
			sideData.add(detailsText,BorderLayout.CENTER);
		this.add(leftSideTable,BorderLayout.CENTER);
		this.add(sideData,BorderLayout.EAST);
	}
	
	private JProduktDisplay getThis() {return this;}
	public void refresh() {
		detailsText.setText(Produkt.get(produktDataTable.getSelectedRow()).getDetails());
	}
	
}