package engine.ui.applet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FreeFormTextDialog extends JDialog implements ActionListener {

  JButton jEnterButton = new JButton();
  JButton jCancelButton = new JButton();
  JTextField jCallTextField = new JTextField();
  JLabel jLabel1 = new JLabel();
  String value;
  String val;
  private boolean cancelSelected = false;
  JComboBox jParamComboBox = new JComboBox();
  JLabel jLabel2 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  int first = 0;
  int last = 0;

  public FreeFormTextDialog(String val, Frame frame) {

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
	jLabel2.setText("Param:");
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 3, 0, 0), 67, 0));
    this.getContentPane().add(jCallTextField, new GridBagConstraints(0, 1, 4, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(14, 3, 0, 18), 373, 0));
    this.getContentPane().add(jCancelButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(33, 8, 14, 18), 1, 0));
    this.getContentPane().add(jEnterButton, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(33, 90, 14, 0), 19, 0));
    this.getContentPane().add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(27, 12, 0, 0), 1, 0));
    this.getContentPane().add(jParamComboBox, new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(24, 13, 0, 75), 134, 0));
	if (val.toLowerCase().indexOf("push", 0) >= 0) {
		first = val.indexOf("p");
		last  = val.indexOf(":");
		val =	val.substring(first, last);
	
		if (val.equalsIgnoreCase("push param")) {
			jParamComboBox.setEnabled(true);
			jCallTextField.setEditable(false);
		}
  	}
			
    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------
            
      jEnterButton.addActionListener(this);		
      jCancelButton.addActionListener(this);
      jCallTextField.addActionListener(this);
      jParamComboBox.addActionListener(this);
	  
  }
  
   	public boolean cancelSelected() {

  		return cancelSelected;
  	}

  
  	public void actionPerformed(ActionEvent e) {

		String text2   = "";

		if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jCallTextField) ) {
						
			if (val.toLowerCase().indexOf("push", 0) >= 0) {

				if (val.toLowerCase().indexOf("push param", 0) >= 0) {
									
					first = val.indexOf("p");
					last  = val.indexOf("m");
					val =	val.substring(first, last) + "m:";

					text2 = (String) jParamComboBox.getSelectedItem();
					value =  val + text2;
					setVisible(false);
					return;
								
				}else {
													
					val = val.toString() + ":";
					first = val.indexOf("p");
					last  = val.indexOf(":");
				}
			}
			else if (val.toLowerCase().indexOf("pop", 0) >= 0) {
				
				first = val.indexOf("p");
				last  = val.indexOf(":");
			}
			else if (val.toLowerCase().indexOf("peek", 0) >= 0) {
				
				first = val.indexOf("p");
				last  = val.indexOf(":");
			}
			else if (val.toLowerCase().indexOf("output", 0) >= 0) {
				
				first = val.indexOf("o");
				last  = val.indexOf(":");
			}
		 val =	val.substring(first, last);
		 value =  val +  ":" + jCallTextField.getText();
		 setVisible(false);
					
		}else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
					
			cancelSelected = true;
			setVisible(false);
		}
  	}
	
	public String getCommentLine() {
			
  		return value;
	}
}