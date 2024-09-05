package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScriptRuleListDialog extends JDialog implements ActionListener {
  JComboBox jScriptRuleListComboBox = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton jCancelButton = new JButton();
  String [] scriptRuleNames;
  String val = null;
  String value = "";
  private boolean cancelSelected = false;
  JTextField jScriptLineTextField = new JTextField();
  JLabel jLabel3 = new JLabel();
  JButton jEnterButton = new JButton();


  public ScriptRuleListDialog(String[] scriptRuleNames, String val, Frame frame) {

		super(frame, true);
		this.scriptRuleNames = scriptRuleNames;
		this.val = val;
		jbInit();
  }

  private void jbInit() {
    this.getContentPane().setLayout(null);
	jScriptRuleListComboBox = new JComboBox(scriptRuleNames);
    jLabel1.setText("Rule:");
    jLabel1.setBounds(new Rectangle(14, 41, 41, 17));
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setText("Select a Rule Name");
    jLabel2.setBounds(new Rectangle(1, 2, 147, 17));
    jCancelButton.setText("Cancel");
    jCancelButton.setBounds(new Rectangle(294, 103, 77, 27));
    jScriptLineTextField.setEditable(false);
    jScriptLineTextField.setBounds(new Rectangle(79, 67, 147, 21));
    jLabel3.setText("New Name:");
    jLabel3.setBounds(new Rectangle(6, 69, 68, 17));
    jEnterButton.setText("Enter");
    jEnterButton.setBounds(new Rectangle(212, 104, 79, 27));
    jScriptRuleListComboBox.setBounds(new Rectangle(56, 40, 267, 21));
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jScriptRuleListComboBox, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jScriptLineTextField, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jEnterButton, null);
    this.getContentPane().add(jCancelButton, null);

	//---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

	jScriptRuleListComboBox.addActionListener(this);
	jCancelButton.addActionListener(this);
	jEnterButton.addActionListener(this);
	jScriptLineTextField.addActionListener(this);

  }

   public boolean cancelSelected() {

  	return cancelSelected;
  }

   public void actionPerformed(ActionEvent e) {

		Object ref = e.getSource();

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jScriptLineTextField) ) {

				if ((jScriptLineTextField.getText() == null) || (jScriptLineTextField.getText().length() == 0)) {
				 return;
				}
			setVisible(false);

		}
		else if (e.getActionCommand().equalsIgnoreCase("cancel")) {

		    cancelSelected = true;
  	    	setVisible(false);

		}
		else if (ref == jScriptRuleListComboBox) {
			value = (String) jScriptRuleListComboBox.getSelectedItem();
			if (value.equalsIgnoreCase("<new>")) {

				jScriptLineTextField.setEditable(true);
				jScriptLineTextField.requestFocus();
				return;
			}
			else {

			 value = val + " " + (String)  jScriptRuleListComboBox.getSelectedItem() + ":";
			 getScriptLine();
		 	 setVisible(false);

			}
		}
	}

	public String getScriptLine() {

		value = (String)  jScriptRuleListComboBox.getSelectedItem();

		if (value.equalsIgnoreCase("select rules")) {
			value = "";
		}
		else if (jScriptLineTextField.getText().equals("")) {
			value = val + " " + (String)  jScriptRuleListComboBox.getSelectedItem() + ":";
		}
		else {
			value = val +  " " + jScriptLineTextField.getText() + ":";
		}

	return value;

	}
}