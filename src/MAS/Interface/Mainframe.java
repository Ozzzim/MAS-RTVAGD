package MAS.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import MAS.Main;
import MAS.Pracownik;
import MAS.Producent;
import MAS.Produkt;
import MAS.Sklep;

public class Mainframe extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Data
	List<Pracownik> pracownicy;
	List<Sklep> sklepy;
	List<Produkt> produkty;
	List<Producent> producenci;
	
	//Structure
	JToolBar toolbar;
		JButton toShops;
		JButton toEmployees;
		JButton toProducts;
		JButton toProducents;
	JPanel display;
	
	public Mainframe() {
		super("MAS s16760");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(1000,400));
		this.setMinimumSize(new Dimension(200,100));
		
		//Toolbar assembly
		toolbar=new JToolBar();
		toolbar.setFloatable(false);
			toShops = new JButton("Sklepy");
			toShops.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					display.removeAll();
						toShops.setFont(toShops.getFont().deriveFont(Font.BOLD));
						toEmployees.setFont(toEmployees.getFont().deriveFont(Font.PLAIN));
						toProducts.setFont(toProducts.getFont().deriveFont(Font.PLAIN));
						toProducents.setFont(toProducents.getFont().deriveFont(Font.PLAIN));
					display.add(new JSklepDisplay());
					display.repaint();
		        }  
		    });
		toolbar.add(toShops);
			
			toEmployees = new JButton("Pracownicy");
			toEmployees.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					display.removeAll();
					toShops.setFont(toShops.getFont().deriveFont(Font.PLAIN));
					toEmployees.setFont(toEmployees.getFont().deriveFont(Font.BOLD));
					toProducts.setFont(toProducts.getFont().deriveFont(Font.PLAIN));
					toProducents.setFont(toProducents.getFont().deriveFont(Font.PLAIN));
					display.add(new JPracownikDisplay());
					display.repaint();
		        }  
		    });
		toolbar.add(toEmployees);
			
			toProducts = new JButton("Produkty");
			toProducts.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
					display.removeAll();
					toShops.setFont(toShops.getFont().deriveFont(Font.PLAIN));
					toEmployees.setFont(toEmployees.getFont().deriveFont(Font.PLAIN));
					toProducts.setFont(toProducts.getFont().deriveFont(Font.BOLD));
					toProducents.setFont(toProducents.getFont().deriveFont(Font.PLAIN));
					display.add(new JProduktDisplay());
					display.repaint();
		        }  
		    });
		toolbar.add(toProducts);
		
		toProducents = new JButton("Producenci");
		toProducents.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				display.removeAll();
					toEmployees.setFont(toEmployees.getFont().deriveFont(Font.PLAIN));
					toProducts.setFont(toProducts.getFont().deriveFont(Font.PLAIN));
					toProducents.setFont(toProducts.getFont().deriveFont(Font.PLAIN));
					toProducents.setFont(toProducents.getFont().deriveFont(Font.BOLD));
				display.add(new JProducentDisplay());
				display.repaint();
	        }  
	    });
		toolbar.add(toProducents);
		
			toShops.setFont(toShops.getFont().deriveFont(Font.BOLD));
			toEmployees.setFont(toEmployees.getFont().deriveFont(Font.PLAIN));
			toProducts.setFont(toProducts.getFont().deriveFont(Font.PLAIN));
			toProducents.setFont(toProducents.getFont().deriveFont(Font.PLAIN));
		//Display assembly
		display=new JPanel(new GridLayout(1,1));

		//Assembly
		display.add(new JSklepDisplay());
		this.add(toolbar,BorderLayout.NORTH);
		this.add(display,BorderLayout.CENTER);
		addWindowListener(new java.awt.event.WindowAdapter() {//Closing operation
	        public void windowClosing(WindowEvent winEvt) {
	        	try {
	    			//Pracownik.saveExtent(Main.location+"\\pracownik.json");
	    			//Produkt.saveExtent(Main.location+"\\produkt.json");
	    			//Sklep.saveExtent(Main.location+"\\sklep.json");
	    			//Producent.saveExtent(Main.location+"\\producent.json");
	        		Main.saveExtents(Main.location);
	        		System.out.println("Zapisano potencjalne zmiany");
	    		} catch (Exception e) {e.printStackTrace();}
	        }
	    });
		this.pack();
	}

}
