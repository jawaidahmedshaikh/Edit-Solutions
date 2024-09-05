package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Apr 23, 2003
 * Time: 9:31:31 AM
 * To change this template use Options | File Templates.
 */
public class CounterDialog extends JDialog implements ActionListener{

    JButton jEnterButton = new JButton();
     JButton jCancelButton = new JButton();
     JTextField jOptionTextField = new JTextField();
     JLabel jLabel1 = new JLabel();
     GridBagLayout gridBagLayout1 = new GridBagLayout();
     JComboBox jOptionComboBox = new JComboBox();
     String value;
     String val;
     private boolean cancelSelected = false;

 public CounterDialog(String val, Frame frame) {

	 super(frame, true);
	 this.val = val;
	 jbInit();
  }

  private void jbInit() {

    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setText("Select Counter Option");
    this.getContentPane().setLayout(gridBagLayout1);
    jOptionTextField.setEnabled(true);
    jOptionTextField.setEditable(false);

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
    jCancelButton.setForeground(Color.black);

 	jOptionComboBox = new JComboBox(new String[]
	{
	  "Select Option",
          "add",
          "sub"
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

       this.getContentPane().add(jOptionTextField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 0, 23), 166, 0));

    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

      jOptionComboBox.addActionListener(this);
      jEnterButton.addActionListener(this);
      jCancelButton.addActionListener(this);
	  jOptionTextField.addActionListener(this);
    }

  	public boolean cancelSelected() {

  	return cancelSelected;
    }

  	public void actionPerformed(ActionEvent e) {

        String text = "";

        Object ref = e.getSource();

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jOptionTextField) ) {

			if ((jOptionTextField.getText() == null) || (jOptionTextField.getText().length() == 0)) {
			    return;
			}
            else {

                value =  jOptionTextField.getText();
            }
            dispose();

	    }
        else if (e.getActionCommand().equalsIgnoreCase("cancel")) {

			cancelSelected = true;
			setVisible(false);
		}

        if (ref == jOptionComboBox) {

            jOptionTextField.setEditable(true);
            jOptionTextField.requestFocus();
            text = (String) jOptionComboBox.getSelectedItem();

            if ((text == null) || (jOptionComboBox.getSelectedIndex() == 0)) {

                return;
            }
            else {

                jOptionTextField.setText(val + " " + text + ";");
                jOptionComboBox.setSelectedIndex(0);
            }
        }
  	}

	public String getCounterOption() {

		return value;
	}

}
