package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScriptDeleteErrorDialog extends JDialog implements ActionListener {

  JButton jEnterButton = new JButton();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String message;
  private boolean enterSelected = false;

  public ScriptDeleteErrorDialog(String message, Frame frame) {

	 super(frame, true);
	 this.message = message;
	 jbInit();
  }
  private void jbInit() {

    jEnterButton.setText("Enter");
    this.getContentPane().setLayout(gridBagLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText(message);
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 7, 0, 47), 67, 0));
    this.getContentPane().add(jEnterButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(16, 133, 11, 0), 19, 0));

    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

      jEnterButton.addActionListener(this);
  }

  public boolean enterSelected() {

      return enterSelected;
  }

  public void actionPerformed(ActionEvent e) {

	  if (e.getActionCommand().equalsIgnoreCase("enter")) {

			enterSelected = true;
			setVisible(false);
		}
  	}
}