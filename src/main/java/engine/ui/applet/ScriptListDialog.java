package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScriptListDialog extends JDialog implements ActionListener {

  JComboBox jScriptListComboBox = new JComboBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  String [] scriptNames;
  String val = null;
  String value = "";
  String selectedItem = "";
  private boolean cancelSelected = false;
  private boolean continueProcess = false;
  JTextField jScriptLineTextField = new JTextField();
  JLabel jLabel3 = new JLabel();
  JButton jEnterButton = new JButton();
  JButton jCancelButton = new JButton();
//  JButton jContinueButton = new JButton();
  JTextField jScriptLineText2 = new JTextField();


  public ScriptListDialog(String[] scriptNames, String val, Frame frame) {
  		
		super(frame, true);
		this.scriptNames = scriptNames;
		this.val = val;
		jbInit();

        if (val.equalsIgnoreCase("call"))  {

            jbCallInit();
        }

        else {

            jbIfInit();
        }
 }
  
  private void jbInit() {

        this.getContentPane().setLayout(null);
        jScriptListComboBox = new JComboBox(scriptNames);
        jLabel1.setText("Script:");
        jLabel1.setBounds(new Rectangle(14, 41, 41, 17));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel2.setText("Select a Script");
        jLabel2.setBounds(new Rectangle(1, 2, 95, 17));
        jCancelButton.setText("Cancel");
        jCancelButton.setBounds(new Rectangle(294, 103, 77, 27));
        jScriptLineTextField.setEditable(false);
        jScriptLineTextField.setBounds(new Rectangle(79, 67, 147, 21));
        jLabel3.setText("New Name:");
        jLabel3.setBounds(new Rectangle(6, 69, 68, 17));
        jEnterButton.setText("Enter");
        jEnterButton.setBounds(new Rectangle(212, 104, 79, 27));
        jScriptListComboBox.setBounds(new Rectangle(56, 40, 267, 21));

	
  }

 private void jbCallInit() {


        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jScriptListComboBox, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jScriptLineTextField, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(jEnterButton, null);
        this.getContentPane().add(jCancelButton, null);

	//---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

	jScriptListComboBox.addActionListener(this);
	jCancelButton.addActionListener(this);
	jEnterButton.addActionListener(this);
	jScriptLineTextField.addActionListener(this);

  }

 private void jbIfInit() {

        jScriptLineText2.setEditable(false);
        jScriptLineText2.setBounds(new Rectangle(90, 105, 220, 21));
        jEnterButton.setText("Enter");
        jEnterButton.setBounds(new Rectangle(212, 184, 79, 27));
        jCancelButton.setText("Cancel");
        jCancelButton.setBounds(new Rectangle(294, 183, 77, 27));
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jScriptListComboBox, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jScriptLineTextField, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(jEnterButton, null);
        this.getContentPane().add(jCancelButton, null);
        this.getContentPane().add(jScriptLineText2, null);

	//---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

	jScriptListComboBox.addActionListener(this);
	jCancelButton.addActionListener(this);
	jEnterButton.addActionListener(this);
    jScriptLineTextField.addActionListener(this);
    jScriptLineText2.addActionListener(this);

  }

   public boolean cancelSelected() {

  	return cancelSelected;
  }

   public boolean continueProcess() {

  	return continueProcess;
  }

   public void actionPerformed(ActionEvent e) {

        continueProcess = false;

	    if (!val.equalsIgnoreCase("call")) {

            doIfProcess(e);
        }
        else  {

            Object ref = e.getSource();

            if (e.getActionCommand().equalsIgnoreCase("enter") || (ref == jScriptLineTextField) ) {

                   if ((jScriptLineTextField.getText() == null) || (jScriptLineTextField.getText().length() == 0)) {

                       return;
                   }
                   else {

                       value = val + " " + jScriptLineTextField.getText() + ":";
                   }

                   setVisible(false);

            }
            else if (e.getActionCommand().equalsIgnoreCase("cancel")) {

               cancelSelected = true;
               setVisible(false);

            }
            else if (ref == jScriptListComboBox) {
               selectedItem = (String) jScriptListComboBox.getSelectedItem();
               if (selectedItem.equalsIgnoreCase("<new>")) {

                   jScriptLineTextField.setEditable(true);
                   jScriptLineTextField.requestFocus();
                   return;
               }
               else {

                   selectedItem =  selectedItem + ":";
                   formatScriptLine();
                   setVisible(false);
               }
            }
        }
	}

    public void doIfProcess(ActionEvent e)  {

 		Object ref = e.getSource();

		if (e.getActionCommand().equalsIgnoreCase("enter") || (ref == jScriptLineText2) ) {

                selectedItem = jScriptLineTextField.getText();

                if ((selectedItem == null) || (selectedItem.length() == 0)) {

                    setVisible(false);
				    return;
				}
                else {

                    continueProcess = true;

                    selectedItem = selectedItem + ":";

                    formatScriptLine();

                    jScriptLineText2.setText(value);
                    jScriptLineText2.requestFocus();
                    jScriptLineText2.setEnabled(false);
                    jScriptLineTextField.setText("");
                }
		}

		else if (e.getActionCommand().equalsIgnoreCase("cancel")) {

		    cancelSelected = true;
  	    	setVisible(false);

		}

        else if (ref == jScriptListComboBox) {
             continueProcess = true;
		     selectedItem = (String) jScriptListComboBox.getSelectedItem();
			 if (selectedItem.equalsIgnoreCase("<new>")) {

                 if (jScriptLineTextField.getText() == null || jScriptLineTextField.getText().length() == 0)   {

			        jScriptLineTextField.setEditable(true);
				    jScriptLineTextField.requestFocus();
				    return;
                 }
                 else {

                   selectedItem =  jScriptLineTextField.getText() + ":";
                 }
			 }
			 else {

			     selectedItem =  selectedItem + ":";
			     formatScriptLine();

                 jScriptLineText2.setText(value);
                 jScriptLineText2.requestFocus();
                 jScriptLineText2.setEnabled(false);


			 }
		}

        if (!continueProcess) {

            setVisible(false);
        }
    }

	public String formatScriptLine() {

		if (selectedItem.equalsIgnoreCase("select scripts")) {
			selectedItem =  "";
		}
		else if (!jScriptLineTextField.getText().equals("")) {

			selectedItem = jScriptLineTextField.getText() + ":";
		}

        if (value.equals("")) {

            value = val + " " + selectedItem;
        }
        else {

            value = value + ";" + selectedItem;
        }

	return value;
		
	}

	public String getScriptLine() {

		return value;
	}
}