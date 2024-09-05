package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectOperandDialog extends JDialog implements ActionListener {

	JLabel jLabel1 = new JLabel();
 	JComboBox jOperandComboBox = new JComboBox();
 	JLabel jLabel2 = new JLabel();
 	JTextField jOperandTextField = new JTextField();
 	JLabel jLabel3 = new JLabel();
 	JButton jOperandCancelButton = new JButton("Cancel");
 	JButton jOperandEnterButton = new JButton("Enter");
 	String val = null;
	String value = null;
	private boolean cancelSelected = false;
 	JComboBox jParamComboBox = new JComboBox();
  	JLabel jLabel4 = new JLabel();
	GridBagLayout gridBagLayout1 = new GridBagLayout();


  public SelectOperandDialog(String val, Frame frame) {

		super(frame, true);
		this.val = val;
		jbInit();
  }

 private void jbInit() {

 	jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setText("Select Operand & Value " + val);
    this.getContentPane().setLayout(gridBagLayout1);
    jLabel2.setToolTipText("");
    jLabel2.setText("Operand:");
    jOperandTextField.setEnabled(true);
    jOperandTextField.setEditable(false);
    jLabel3.setText("Value:");

	jOperandEnterButton.setToolTipText("Enter");
    jOperandEnterButton.setBackground(Color.lightGray);
    jOperandEnterButton.setBorderPainted(true);
	jOperandEnterButton.setActionCommand("enter");
	jOperandEnterButton.setText("Enter");
    jOperandEnterButton.setForeground(Color.black);

    jOperandCancelButton.setToolTipText("Cancel");
    jOperandCancelButton.setBackground(Color.lightGray);
    jOperandCancelButton.setBorderPainted(true);
	jOperandCancelButton.setActionCommand("cancel");
	jOperandCancelButton.setText("Cancel");
    jOperandCancelButton.setForeground(Color.black);

	jOperandComboBox = new JComboBox(new String[]
	{
	  "Select Operand",
          "ws:",
          "str:",
          "num:",
		  "func:",
		  "bool:",
		  "date:",
		  "param:"
	}
	);

	jParamComboBox = new JComboBox(new String[]
	{
        "Select Parameters",
            "AccumFees",
            "AccumLoads",
            "AllocationPct",
            "AllocOverride",
            "Amount",
            "AmountPaidYTD",
            "AmountPdToDate",
            "AnnuitantCounter",
            "BankAcctNumber",
            "BankAccountType",
            "BankRoutingNumber",
            "CertainPeriod",
            "CityWithholdingInd",
            "CityWithholdingOvrd",
            "Class",
            "ClientId",
            "CompanyKey",
            "ContractNumber",
            "CountyWithholdingInd",
            "CountyWithholdingOvrd",
            "CoverageAmount",
            "CumDollars",
            "CumUnits",
            "DateOfDeath",
            "DeathBenefitOpt",
            "DepositDate",
            "DisbursementSource",
            "Dollars",
            "EffectiveDate",
            "ExclusionRatio",
            "ExpectedReturn",
            "FedWithholdingInd",
            "FedWithholdingOvrd",
            "FeesOverride",
            "FlatAge",
            "FlatDuration",
            "FlatExtra",
            "FlatExtraAmount",
            "FlatExtraAmtAge",
            "FlatExtraAmtDur",
            "FlatExtraPctAge",
            "FlatExtraPctDur",
            "FlatExtraPercent",
            "Frequency",
            "FromToIndicator",
            "Fund",
            "FundCounter",
            "FundType",
            "GrossAmount",
            "InterestRate",
            "IssueAge",
            "LastCheckDate",
            "LastValDate",
            "LoadsOverride",
            "LumpSumPaidYTD",
            "LumpSumPdToDate",
            "ModalAmount",
            "NetAmount",
            "NextPaymentDate",
            "NumRemainingPymts",
            "Option7702",
            "OriginInvestment",
            "OwnerCounter",
            "PayeeAllocation",
            "PayeeAmtPdYTD",
            "PayeeAmtPTD",
            "PayeeCounter",
            "PayeeDollars",
            "PayeeEffDate",
            "PayeeStatus",
            "PayeeTermDate",
            "PayoutBasis",
            "PayoutOption",
            "PercentAge",
            "PercentDuration",
            "PercentExtra",
            "Post86Investment",
            "PremPayTerm",
            "PremTaxesOverride",
            "PreNoteEffDate",
            "RecoveredCostBasis",
            "ReducePercent1",
            "RelationshipInd",
            "ResidentState",
            "RiderEndDate",
            "RiderId",
            "RiderStartDate",
            "RiderType",
            "SelectedIndex",
            "Sex",
            "StartDate",
            "StateWithholdingInd",
            "StateWithholdingOvrd",
            "StatusChangeDate",
            "StatusCode",
            "SubStandardRating",
            "TableRating",
            "TaxFilingStatus",
            "TerminationDate",
            "TransactionRiderType",
            "TransactionType",
            "TrxAmountReceived",
            "TrxCostBasis",
            "TrxDistCode",
            "TrxEffectiveDate",
            "TrxMemoCode",
            "TrxNetAmountInd",
            "TrxPremiumType",
            "TrxProcessDate",
            "TrxSequence",
            "TrxSuspenseAmount",
            "TrxTaxYear",
            "Units",
            "WithdrawalsYTD",
            "YearlyTaxableBenefit"

    }
	);
	jParamComboBox.setEnabled(false);

	this.getContentPane().setBackground(Color.lightGray);
    jLabel4.setText("Param:");
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(27, 1, 0, 81), 99, 0));
    this.getContentPane().add(jOperandComboBox, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(17, 0, 0, 22), 59, 0));
    this.getContentPane().add(jOperandCancelButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(22, 0, 1, 2), 19, -1));
    this.getContentPane().add(jOperandEnterButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(21, 102, 1, 0), 31, 0));
    this.getContentPane().add(jParamComboBox, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 21), 47, 0));
    this.getContentPane().add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(21, 8, 0, 0), 1, 0));
    this.getContentPane().add(jOperandTextField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 0, 23), 166, 0));
    this.getContentPane().add(jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(13, 23, 0, 0), 7, 0));
    this.getContentPane().add(jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 19, 0, 0), 1, 0));

    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------

	jOperandComboBox.addActionListener(this);
	jParamComboBox.addActionListener(this);
	jOperandEnterButton.addActionListener(this);
	jOperandCancelButton.addActionListener(this);
	jOperandTextField.addActionListener(this);
  }

	//----------------------------------------------
    //        		Public Methods
    // ---------------------------------------------


  public String getScriptLine() {

  	return value;
  }

  public boolean cancelSelected() {

  	return cancelSelected;
  }

  public void actionPerformed(ActionEvent e) {

        String text  = "";
		String text2 = "";
		String extText = "";

		Object ref = e.getSource();

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jOperandTextField) ) {
			if ((jOperandTextField.getText() == null) || (jOperandTextField.getText().length() == 0)) {
				return;
			}else {
				value = jOperandTextField.getText();
			}
			dispose();

		}else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
			if (val.equalsIgnoreCase("output")) {

				value = "output";
			}

			cancelSelected = true;
			dispose();

		}

	 	if (ref == jOperandComboBox) {
	 		jOperandTextField.setEditable(true);
			jOperandTextField.requestFocus();
			text = (String) jOperandComboBox.getSelectedItem();
            extText = jOperandTextField.getText();
			if (text.equalsIgnoreCase("param:")) {
				jParamComboBox.setEnabled(true);
			}
            if ((extText.length() == 0 || extText == null) ||
                    (!val.equalsIgnoreCase("output") && (!val.equalsIgnoreCase("compare")))) {

			    jOperandTextField.setText(val +" " + text);
            }
            else {

                jOperandTextField.setText(extText +  ";" + text);
            }

			jOperandComboBox.setSelectedIndex(0);

		}
		else if (ref == jParamComboBox) {
			text2 = (String) jParamComboBox.getSelectedItem();
			extText = jOperandTextField.getText();
			jOperandTextField.setText(extText + text2);
			jParamComboBox.setSelectedIndex(0);
		}
  }
}