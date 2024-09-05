package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IfCondition extends JDialog implements ActionListener {

  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JComboBox jConditionComboBox = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JComboBox jScriptComboBox = new JComboBox();
  JTextField jConditionTextField = new JTextField();
  JButton jConditionEnterButton = new JButton("Enter");
  JButton jConditionCancelButton = new JButton("Cancel");
  JLabel jLabel4 = new JLabel();
  private boolean cancelSelected = true;
  String val = null;
  String [] scriptNames;
  String value = null;
  JTextField jNewScriptNameTextField = new JTextField();
  JLabel jLabel5 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public IfCondition(String[] scriptNames, String val, Frame frame) {
  		
		super(frame, true);
		this.scriptNames = scriptNames;
		this.val = val;
		jbInit();
  }
    
  private void jbInit() {
  
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Select Condition " + "for " + val);
    this.getContentPane().setLayout(gridBagLayout1);
    jLabel2.setText("Condition:");
    jLabel3.setText("Script:");
	
	jScriptComboBox = new JComboBox(scriptNames);
    jScriptComboBox.setEnabled(false);


	jConditionComboBox = new JComboBox(new String[]
	{
	  "Select Condition",
          "if",
          "iftol",
          "while",
		  "whiletol"		
    }
	);

	jConditionEnterButton.setToolTipText("Enter");
    jConditionEnterButton.setBackground(Color.lightGray);
    jConditionEnterButton.setBorderPainted(true);
	jConditionEnterButton.setActionCommand("enter");
	jConditionEnterButton.setText("Enter");
    jConditionEnterButton.setForeground(Color.black);
    
    jConditionCancelButton.setToolTipText("Cancel");
    jConditionCancelButton.setBackground(Color.lightGray);
    jConditionCancelButton.setBorderPainted(true);
	jConditionCancelButton.setActionCommand("cancel");
	jConditionCancelButton.setText("Cancel");
    jConditionCancelButton.setForeground(Color.black);
	
    jLabel4.setText("Script Line:");
    jNewScriptNameTextField.setEditable(false);
    jLabel5.setText("New Script Name:");
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(39, 0, 0, 0), 286, 0));
    this.getContentPane().add(jConditionComboBox, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(30, 0, 0, 23), 91, 0));
    this.getContentPane().add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(32, 7, 0, 0), 9, 0));
    this.getContentPane().add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 25, 0, 6), 7, 0));
    this.getContentPane().add(jConditionTextField, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, 0, 0, 15), 213, 0));
    this.getContentPane().add(jScriptComboBox, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 20), 86, 0));
    this.getContentPane().add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 7, 0, 0), 4, 0));
    this.getContentPane().add(jLabel5, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 8, 0, 0), 7, 0));
    this.getContentPane().add(jNewScriptNameTextField, new GridBagConstraints(2, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 54), 135, 0));
    this.getContentPane().add(jConditionEnterButton, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(14, 100, 10, 0), 28, 0));
    this.getContentPane().add(jConditionCancelButton, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 7, 10, 0), 14, 0));
			
	//---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------
	
	jScriptComboBox.addActionListener(this);
	jConditionComboBox.addActionListener(this);
	jConditionEnterButton.addActionListener(this);		
	jConditionCancelButton.addActionListener(this);
	jConditionTextField.addActionListener(this);
	jNewScriptNameTextField.addActionListener(this);
  }

  //----------------------------------------------
  //        		Public Methods
  // ---------------------------------------------

 	public boolean cancelSelected() {

 	 	return cancelSelected;
  	}


	public void actionPerformed(ActionEvent e) {
		
        String text       = "";
		String extText    = "";

		Object ref = e.getSource();
		
	
		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jConditionTextField) ) {
							
			if ((jConditionTextField.getText() == null) || (jConditionTextField.getText().length() == 0)) {
				return;
			}
					
			cancelSelected = false;
			setVisible(false);
			
		}else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
		
			cancelSelected = true;
			setVisible(false);
		
		}
		 else if (ref == jConditionComboBox) {
		 	jConditionTextField.setEditable(true);
			jConditionTextField.requestFocus();
			text = (String) jConditionComboBox.getSelectedItem();

            if (text.startsWith("if")) {

                jScriptComboBox.setEnabled(true);
            }


			if (text.equalsIgnoreCase("Select Condition")) {
				
				jConditionTextField.setText("");
				jConditionComboBox.setSelectedIndex(0);
			
			}
			else if (text.equalsIgnoreCase("iftol")) {
			  	
				text = "if" + val + "tol";
				jConditionTextField.setText(text);
				jConditionComboBox.setSelectedIndex(0);
			}
			else if (text.equalsIgnoreCase("whiletol")) {
				
				text = "while" + val + "tol";	
				jConditionTextField.setText(text);
				jConditionComboBox.setSelectedIndex(0);
			}
			else {
				jConditionTextField.setText(text + val);
				jConditionComboBox.setSelectedIndex(0);
				}
						
		}else if (ref == jScriptComboBox) {
		 	
			text = (String)  jScriptComboBox.getSelectedItem();
			
			if (text.equalsIgnoreCase("Select Scripts")) {
			
				jNewScriptNameTextField.setText("");
				jScriptComboBox.setSelectedIndex(0);
			}
			else if (text.equalsIgnoreCase("<new>")) {
				
				jConditionTextField.setEnabled(false);
				jNewScriptNameTextField.setEditable(true);
				jNewScriptNameTextField.requestFocus();
			
				extText = jConditionTextField.getText();
								
				jScriptComboBox.setSelectedIndex(0);
				jNewScriptNameTextField.requestFocus();
			}
			else {
			
			extText = jConditionTextField.getText();
			jConditionTextField.setText(extText + " " + text);
			jScriptComboBox.setSelectedIndex(0);
			jConditionTextField.requestFocus();
			}
		}		
	}
	
	public String getScriptLine() {

		String scriptNameText = jNewScriptNameTextField.getText().trim();

		if (scriptNameText.length() == 0) {
		
			value = jConditionTextField.getText().trim();

            if (value.startsWith("if")) {

                value = value + ":";
            }

			return value;
		}
		else  {

            value = jConditionTextField.getText().trim() + " " + scriptNameText + ":";

			return value;
		}
  }
}