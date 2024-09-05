package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Apr 23, 2003
 * Time: 2:15:18 PM
 * To change this template use Options | File Templates.
 */
public class InstructionDialog extends JDialog implements ActionListener{

     JButton jEnterButton = new JButton();
     JButton jCancelButton = new JButton();
     JLabel jLabel1 = new JLabel();
     JLabel jLabel2 = new JLabel();
     GridBagLayout gridBagLayout1 = new GridBagLayout();
     JComboBox jTypeComboBox = new JComboBox();
     JComboBox jModeComboBox = new JComboBox();
     String value;
     String val;
     private boolean cancelSelected = false;

 public InstructionDialog(String val, Frame frame) {

	 super(frame, true);
	 this.val = val;

     if (val.equalsIgnoreCase("Inst:Type") || (val.equalsIgnoreCase("Type")))  {

         jbTypeInit();
     }
     else {

	    jbModeInit();
     }
  }

  private void jbTypeInit() {

    this.getContentPane().setLayout(gridBagLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setText("Select Type Option");

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

      if (val.equalsIgnoreCase("Inst:Type"))  {

        jTypeComboBox = new JComboBox(new String[]
        {
              "Select Option",
              "None",
              "Frequency"
        }

	    );
      }
      else {

         jTypeComboBox = new JComboBox(new String[]
        {
              "Select Option",
              "List",
              "VectorProd"
        }

	    );
      }

       this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
               ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(27, 1, 0, 81), 99, 0));

       this.getContentPane().add(jTypeComboBox, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(17, 0, 0, 22), 59, 0));

       this.getContentPane().add(jCancelButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(22, 0, 1, 2), 19, -1));

       this.getContentPane().add(jEnterButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(21, 102, 1, 0), 31, 0));



    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

      jTypeComboBox.addActionListener(this);
      jEnterButton.addActionListener(this);
      jCancelButton.addActionListener(this);
    }

    private void jbModeInit() {

      this.getContentPane().setLayout(gridBagLayout1);
      jLabel2.setFont(new java.awt.Font("Dialog", 1, 14));
      jLabel2.setText("Select Mode Option");

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

       jModeComboBox = new JComboBox(new String[]
      {
            "Select Option",
            "12",
            "01",
            "04",
            "02"
      }
      );

         this.getContentPane().add(jLabel2, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
                 ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(27, 1, 0, 81), 99, 0));

         this.getContentPane().add(jModeComboBox, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                 ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(17, 0, 0, 22), 59, 0));

         this.getContentPane().add(jCancelButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
              ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(22, 0, 1, 2), 19, -1));

         this.getContentPane().add(jEnterButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
              ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(21, 102, 1, 0), 31, 0));



      //---------------------------------------------------------------------
      //      Register Listeners
      //---------------------------------------------------------------------

        jModeComboBox.addActionListener(this);
        jEnterButton.addActionListener(this);
        jCancelButton.addActionListener(this);
      }

  	public boolean cancelSelected() {

  	return cancelSelected;
    }

  	public void actionPerformed(ActionEvent e) {

        String text = "";

        if (val.equalsIgnoreCase("Inst:Type")) {

            val = "Inst:Type" + "=";
            text = (String) jTypeComboBox.getSelectedItem();
            value = val + text;
        }
        else if (val.equalsIgnoreCase("Inst:Mode")) {

            val = "Inst:Mode" + "=";
            text = (String) jModeComboBox.getSelectedItem();
            value = val + text;
        }
        else if (val.equalsIgnoreCase("Type"))  {

            val = "Type" + ":";
            text = (String) jTypeComboBox.getSelectedItem();
            value = val + text;
        }

        if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == text) ) {

            if ((text == null) || (jTypeComboBox.getSelectedIndex() == 0)) {

                return;
            }

            //value = val + text;

            setVisible(false);

        }
        else if (e.getActionCommand().equalsIgnoreCase("cancel")) {

            cancelSelected = true;
            setVisible(false);
        }
  	}

	public String getInstructionOption() {

		return value;
	}
}
