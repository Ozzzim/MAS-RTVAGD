package MAS.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import MAS.Sklep;

public class JSklepDisplay extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	boolean listener=false;
	Sklep currentSklep;
	
	JPanel leftShopList;
		JList<Sklep> sklepList;
	JPanel centerInventory;
		JTable inventoryTable;
	JPanel rightDescryption;
		JTextArea rightDescryptionText;
	
	public JSklepDisplay() {
		super(new BorderLayout());
		//this.add(new JLabel(text),BorderLayout.CENTER);
		
		leftShopList=new JPanel(new BorderLayout());
			sklepList=new JList<Sklep>(new JListModelSklep(Sklep.getSklepy()));
			sklepList.addListSelectionListener(new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent arg0) {
					if(!arg0.getValueIsAdjusting()) {
						if(currentSklep!=null && currentSklep.getID()==arg0.getFirstIndex())
							currentSklep=Sklep.get(arg0.getLastIndex());
						else
							currentSklep=Sklep.get(arg0.getFirstIndex());
						//System.out.println(currentSklep);
						inventoryTable.setModel(new InventoryTableModel(currentSklep));
						//System.out.println("!"+s.getDetails());
						rightDescryptionText.setText(currentSklep.getDetails());
						repaint();
						//System.out.print(inventoryTable.getModel().getValueAt(0, 1));
					}
					//listener=!listener;
				}
			
			});
		leftShopList.add(new JLabel("Sklepy"),BorderLayout.NORTH);
		leftShopList.add(sklepList,BorderLayout.CENTER);
		//System.out.println(sklepList);
		
		centerInventory = new JPanel(new BorderLayout());
			inventoryTable = new JTable();
		centerInventory.add(inventoryTable,BorderLayout.CENTER);
		centerInventory.add(new JLabel("Inwentarz"),BorderLayout.NORTH);
		rightDescryption= new JPanel(new BorderLayout());
			rightDescryptionText = new JTextArea("");
			rightDescryptionText.setEditable(false);
			rightDescryptionText.setLineWrap(true);
		rightDescryption.add(rightDescryptionText,BorderLayout.CENTER);
		rightDescryption.add(new JLabel("Opis"),BorderLayout.NORTH);
		
		leftShopList.setPreferredSize(new Dimension(200,500));
		rightDescryption.setPreferredSize(new Dimension(200,0));
		centerInventory.setPreferredSize(new Dimension(400,0));
		
		add(new JScrollPane(leftShopList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.WEST);
		add(new JScrollPane(centerInventory,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		add(rightDescryption,BorderLayout.EAST);
	}
}
