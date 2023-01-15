package MAS.Interface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JGenericConfirmWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel panel;
		JTextArea text;
		JButton okButton;
	
	public JGenericConfirmWindow(String frameName, String frameText) {
		super(frameName);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(300,200));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
				
			panel= new JPanel();
			GridLayout gl = new GridLayout(2,1);
			panel.setLayout(gl);
			//gl.setHgap(20);
			//gl.setVgap(20);
				text = new JTextArea(frameText);//,SwingConstants.CENTER);
				text.setPreferredSize(new Dimension(20,80));
				text.setEditable(false);
				text.setLineWrap(true);
				
				okButton= new JButton("Ok");
				okButton.setMaximumSize(new Dimension(20,80));
				okButton.addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){
						disposeWrap();
					}
				});
			panel.add(text);
			panel.add(okButton);
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	private void disposeWrap() {
		this.setVisible(false);
		this.dispose();
	}
}
