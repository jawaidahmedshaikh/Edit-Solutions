package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FreeFormVectorDialog extends JDialog implements ActionListener {

  JButton jEnterButton = new JButton();
  JButton jCancelButton = new JButton();
  JTextField jCallTextField = new JTextField();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String value;
  String val;
  private boolean cancelSelected = false;
  JComboBox jOptionComboBox = new JComboBox();
  JTextField jOptionTextField = new JTextField();


  public FreeFormVectorDialog(String val, Frame frame) {

	 super(frame, true);
	 this.val = val;

     if (val.equalsIgnoreCase("getvector")) {
	    jbGetVectorInit();
     }
      else {
        jbVectorInit();
     }
  }
  
  private void jbGetVectorInit() {
  
    jEnterButton.setText("Enter");
    this.getContentPane().setLayout(gridBagLayout1);
    jCancelButton.setText("Cancel");
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Enter List Name");
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

    private void jbVectorInit() {

       this.getContentPane().setLayout(gridBagLayout1);
       jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
       jLabel1.setText("Select List Option");

       jOptionTextField.setEnabled(true);

       jEnterButton.setToolTipText("Enter");
       jEnterButton.setBackground(Color.lightGray);
       jEnterButton.setBorderPainted(true);
       jEnterButton.setActionCommand("enter");
       jEnterButton.setText("Enter");
       jEnterButton.setForeground(Color.black);

       jCancelButton.setToolTipText("Cancel");
       jCancelButton.setBackground(Color.lightGray);
       jCancelButton.setBorderPainted(true);
       jCancelButton.setActionCommand("cancel");
       jCancelButton.setText("Cancel");

        jOptionComboBox = new JComboBox(new String[]
       {
             "Select Option",
             "Start",
             "End"
       }
       );

          this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
                  ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(27, 1, 0, 81), 99, 0));

          this.getContentPane().add(jOptionComboBox, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                  ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(17, 0, 0, 22), 59, 0));

          this.getContentPane().add(jCancelButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(22, 0, 1, 2), 19, -1));

          this.getContentPane().add(jEnterButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(21, 102, 1, 0), 31, 0));


       //---------------------------------------------------------------------
       //      Register Listeners
       //---------------------------------------------------------------------

         jOptionComboBox.addActionListener(this);
         jEnterButton.addActionListener(this);
         jCancelButton.addActionListener(this);
       }


  	public boolean cancelSelected() {

  	return cancelSelected;
    }
  
  	public void actionPerformed(ActionEvent e) {
		
        if (val.equalsIgnoreCase("getvector")) {
             getVectorProcess(e);
        }
        else {
             vectorProcess(e);
        }
   }

    public void getVectorProcess(ActionEvent e) {

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jCallTextField) ) {
							
			if ((jCallTextField.getText() == null) || (jCallTextField.getText().length() == 0)) {
			 return;				
			}
						
			value = val + " " + jCallTextField.getText() + ":";
			setVisible(false);
			
		}else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
			
			cancelSelected = true;
			setVisible(false);
		}
  	}

  	public void vectorProcess(ActionEvent e) {

        String text = (String) jOptionComboBox.getSelectedItem();

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == text) ) {

			if ((text == null) || (jOptionComboBox.getSelectedIndex() == 0)) {

			    return;
			}

            val = val.toUpperCase();
            value = val + ":" + text;

			setVisible(false);

	   }else if (e.getActionCommand().equalsIgnoreCase("cancel")) {

			cancelSelected = true;
			setVisible(false);
		}
  	}

	public String getVector() {
	
		return value;
	}
}