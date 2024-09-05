package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelDialog extends JDialog implements ActionListener{
  
  JLabel jLabel1 = new JLabel();
  JButton jYesButton = new JButton();
  JButton jNoButton = new JButton();
  boolean yesSelected = false;
  
  public CancelDialog(Frame frame) {
    
	super(frame, true);
	
	jbInit();
  }
  private void jbInit() {
  
    jLabel1.setToolTipText("");
    jLabel1.setText("Script Has Been Changed Are You Sure You Want To Exit");
    jLabel1.setBounds(new Rectangle(0, 2, 355, 22));
    this.getContentPane().setLayout(null);
    this.getContentPane().setBackground(Color.lightGray);
    jYesButton.setText("Yes");
    jYesButton.setBounds(new Rectangle(229, 57, 79, 27));
    jNoButton.setText("No");
    jNoButton.setBounds(new Rectangle(312, 57, 71, 27));
    
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jNoButton, null);
    this.getContentPane().add(jYesButton, null);
  
  //---------------------------------------------------------------------
  //      Register Listeners
  //---------------------------------------------------------------------
            
      jYesButton.addActionListener(this);		
      jNoButton.addActionListener(this);
  }
  
  public boolean yesSelected() {

  	return yesSelected;
  }
  
  public void actionPerformed(ActionEvent e) {
  
  	//Object ref = e.getSource();
	
	if (e.getActionCommand().equalsIgnoreCase("yes") ) {
			
		yesSelected = true;	
		setVisible(false);					
	}
    else {
    	setVisible(false);
	
	}
	
  }

}