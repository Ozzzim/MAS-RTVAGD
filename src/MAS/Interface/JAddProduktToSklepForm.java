package MAS.Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import MAS.Produkt;
import MAS.Sklep;

public class JAddProduktToSklepForm extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	boolean listener=false;
	Sklep currentSklep;
	
	JPanel panel;
		JList<Sklep> sklepList;
		JPanel rightPanel;
			JLabel containsField;
			JPanel amountPanel;
				JLabel amountDescryption;
				JTextField amount;
			JButton submit;
	
	public JAddProduktToSklepForm(Produkt p, JProduktDisplay jpd) {
		super("Adding "+p.getNazwa()+"...");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
			panel = new JPanel(new GridLayout(1,2));
			sklepList = new JList<Sklep>(new JListModelSklep(Sklep.getSklepy()));
			sklepList.addListSelectionListener(new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent arg0) {
					if(listener) {
						if(currentSklep!=null && currentSklep.getID()==arg0.getFirstIndex())
							currentSklep=Sklep.get(arg0.getLastIndex());
						else
							currentSklep=Sklep.get(arg0.getFirstIndex());
						containsField.setText("Ten sklep posiada "+currentSklep.GetAmount(p)+" "+p.getNazwa());
						repaint();
						//System.out.print(inventoryTable.getModel().getValueAt(0, 1));
					}
					listener=!listener;
				}
			});
			rightPanel = new JPanel();
			GridLayout gl = new GridLayout(3,1);
			gl.setVgap(20);
			rightPanel.setLayout(gl);
				containsField = new JLabel();
				submit = new JButton ("Dodaj");
				submit.addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){
						int i=0;
						try {
							i=Integer.parseInt(amount.getText());
						}catch(Exception exc) {
							new JGenericConfirmWindow("Error!","Dodawana wartość musi być liczbą całkowitą.");
							disposeWrap();
							return;
						}
						if(currentSklep==null)
							new JGenericConfirmWindow("Error!","Nie został wybrany żaden sklep.");
						else {
							if(!currentSklep.contains(p) && i<0 || currentSklep.contains(p) && -i>currentSklep.GetAmount(p)) {
								new JGenericConfirmWindow("Error!","Nie ma wystarczająco produktu do usunięcia.");
							} else {
								if(!currentSklep.contains(p)) {
									currentSklep.addProdukt(p, i);
									new JGenericConfirmWindow("Success!","Produkt został pomyślnie wstawiony.");
								} else {
									if(i<0) {
										currentSklep.decrease(p, -i);
										new JGenericConfirmWindow("Success!","Produkt został pomyślnie usunięty.");
									} else {
										currentSklep.increase(p, i);
										new JGenericConfirmWindow("Success!","Produkt został pomyślnie wstawiony.");
									}
								}
							}
						}
						disposeWrap();
						jpd.refresh();
					}
				});
				amountPanel = new JPanel(new GridLayout(2,1));
					amountDescryption=new JLabel("Ilość produktu");
					amount= new JTextField();
				amountPanel.add(amountDescryption);
				amountPanel.add(amount);
			rightPanel.add(containsField);
			rightPanel.add(amountPanel);
			rightPanel.add(submit);
		panel.add(sklepList);
		panel.add(rightPanel);
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	private void disposeWrap() {
		this.setVisible(false);
		this.dispose();
	}
	
}
