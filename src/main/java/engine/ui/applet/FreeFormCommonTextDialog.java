package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FreeFormCommonTextDialog extends JDialog implements ActionListener {

  JButton jEnterButton = new JButton();
  JButton jCancelButton = new JButton();
  JTextField jCallTextField = new JTextField();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String value;
  String val;
  private boolean cancelSelected = false;

  public FreeFormCommonTextDialog(String val, Frame frame) {

	 super(frame, true);
	 this.val = val;
	 jbInit();	
  }
  private void jbInit() {
  
    jEnterButton.setText("Enter");
    this.getContentPane().setLayout(gridBagLayout1);
    jCancelButton.setText("Cancel");
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Enter Text");
    this.getContentPane().add(jCallTextField, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(49, 20, 0, 3), 265, 0));
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 7, 0, 47), 67, 0));
    this.getContentPane().add(jCancelButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(16, 0, 11, 3), 1, 0));
    this.getContentPane().add(jEnterButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(16, 133, 11, 0), 19, 0));
			
    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------
            
      jEnterButton.addActionListener(this);		
      jCancelButton.addActionListener(this);
      jCallTextField.addActionListener(this);
	  
  }
  
   	public boolean cancelSelected() {

  		return cancelSelected;
  	}

  
  	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jCallTextField) ) {
							
			if ((jCallTextField.getText() == null) || (jCallTextField.getText().length() == 0)) {
			
				value =  "//";
				return;
			}
			else if (val.equalsIgnoreCase("name"))   {

				value = "Name:" + jCallTextField.getText();
			}
			else if (val.equalsIgnoreCase("type"))   {

				value = "Type:" + jCallTextField.getText();
			}
			else if (val.equalsIgnoreCase("Variable"))   {

				value = "Variable:" + jCallTextField.getText();
			}
			else if (val.equalsIgnoreCase("Calc"))   {

				value = "Calc=" + jCallTextField.getText();
			}
 			else if (val.equalsIgnoreCase("compare"))   {

				value = val + " " + jCallTextField.getText();
			}
            else if (val.equalsIgnoreCase("comment")) {

                value =  "//" + jCallTextField.getText();
            }

		setVisible(false);
	
			
		}else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
						
			cancelSelected = true;
			setVisible(false);
		}
  	}
	
	public String getTextLine() {
			
		return value;
	}
}