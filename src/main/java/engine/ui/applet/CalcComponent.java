
package engine.ui.applet;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;


public class CalcComponent extends JPanel implements KeyListener, ActionListener {
  JPanel jFunctionPanel = new JPanel();
  JPanel jCalcPanel = new JPanel();
  JButton jLessEqualButton = new JButton();
  GridLayout gridLayout1 = new GridLayout();
  JButton jGreaterEqualButton = new JButton();
  JButton jNotEqualButton = new JButton();
  JButton jLessThanButton = new JButton();
  JButton jGreaterThanButton = new JButton();
  JButton jEqualButton = new JButton();
  JButton jMinButton = new JButton();
  JButton jMaxButton = new JButton();
  JButton jPowerButton = new JButton();
  GridLayout gridLayout2 = new GridLayout();
  JButton jAddButton = new JButton();
  JButton jSignButton = new JButton();
  JButton jDecimalButton = new JButton();
  JButton jZeroButton = new JButton();
  JButton jSubButton = new JButton();
  JButton jThreeButton = new JButton();
  JButton jTwoButton = new JButton();
  JButton jOneButton = new JButton();
  JButton jMultiplyButton = new JButton();
  JButton jSixButton = new JButton();
  JButton jFiveButton = new JButton();
  JButton jFourButton = new JButton();
  JButton jDivideButton = new JButton();
  JButton jNineButton = new JButton();
  JButton jEightButton = new JButton();
  JButton jSevenButton = new JButton();
  JTextField jCalcTextField = new JTextField();
  JButton jEnterButton = new JButton();
  JLabel jParamLabel = new JLabel();
  JComboBox jParameterComboBox = new JComboBox();
  String value =  new String();

  // JavaBeans property management
  PropertyChangeSupport propChangeSupport;
 
 //EnhancedEditApplet
  private EnhancedEditApplet enhancedApplet; 
  
  // URL info
  private URL baseURL;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TitledBorder titledBorder1;
  
  
  public CalcComponent(URL baseURL, EnhancedEditApplet enhancedApplet) {
  
  	this.baseURL = baseURL;
	this.enhancedApplet = enhancedApplet;
	
	jbInit();
  }
  
  private void jbInit() {
  
    titledBorder1 = new TitledBorder("");
    this.setLayout(gridBagLayout1);
    jFunctionPanel.setBackground(Color.lightGray);
    jFunctionPanel.setBorder(BorderFactory.createEtchedBorder());
    jFunctionPanel.setLayout(gridLayout1);
    jCalcPanel.setBackground(Color.lightGray);
    jCalcPanel.setBorder(BorderFactory.createEtchedBorder());
    jCalcPanel.setLayout(gridLayout2);
    jLessEqualButton.setBorder(BorderFactory.createEtchedBorder());
    jLessEqualButton.setPreferredSize(new Dimension(45, 45));
    jLessEqualButton.setText("<=");
    gridLayout1.setRows(3);
    gridLayout1.setColumns(3);
    jGreaterEqualButton.setText(">=");
    jGreaterEqualButton.setPreferredSize(new Dimension(45, 45));
    jGreaterEqualButton.setBorder(BorderFactory.createEtchedBorder());
    jNotEqualButton.setText("!=");
    jNotEqualButton.setPreferredSize(new Dimension(45, 45));
    jNotEqualButton.setBorder(BorderFactory.createEtchedBorder());
    jLessThanButton.setText("<");
    jLessThanButton.setPreferredSize(new Dimension(45, 45));
    jLessThanButton.setBorder(BorderFactory.createEtchedBorder());
    jGreaterThanButton.setText(">");
    jGreaterThanButton.setPreferredSize(new Dimension(45, 45));
    jGreaterThanButton.setBorder(BorderFactory.createEtchedBorder());
    jEqualButton.setText("=");
    jEqualButton.setPreferredSize(new Dimension(45, 45));
    jEqualButton.setBorder(BorderFactory.createEtchedBorder());
    jMinButton.setText("Min");
    jMinButton.setPreferredSize(new Dimension(45, 45));
    jMinButton.setBorder(BorderFactory.createEtchedBorder());
    jMaxButton.setText("Max");
    jMaxButton.setPreferredSize(new Dimension(45, 45));
    jMaxButton.setBorder(BorderFactory.createEtchedBorder());
    jPowerButton.setText("y*");
    jPowerButton.setPreferredSize(new Dimension(45, 45));
    jPowerButton.setBorder(BorderFactory.createEtchedBorder());
    gridLayout2.setRows(4);
    gridLayout2.setColumns(4);
    jAddButton.setBorder(BorderFactory.createEtchedBorder());
    jAddButton.setPreferredSize(new Dimension(45, 45));
    jAddButton.setText("+");
    jSignButton.setText("+/-");
    jSignButton.setPreferredSize(new Dimension(45, 45));
    jSignButton.setBorder(BorderFactory.createEtchedBorder());
    jDecimalButton.setText(".");
    jDecimalButton.setPreferredSize(new Dimension(45, 45));
    jDecimalButton.setBorder(BorderFactory.createEtchedBorder());
    jZeroButton.setText("0");
    jZeroButton.setPreferredSize(new Dimension(45, 45));
    jZeroButton.setBorder(BorderFactory.createEtchedBorder());
    jSubButton.setText("-");
    jSubButton.setPreferredSize(new Dimension(45, 45));
    jSubButton.setBorder(BorderFactory.createEtchedBorder());
    jThreeButton.setText("3");
    jThreeButton.setPreferredSize(new Dimension(45, 45));
    jThreeButton.setBorder(BorderFactory.createEtchedBorder());
    jTwoButton.setText("2");
    jTwoButton.setPreferredSize(new Dimension(45, 45));
    jTwoButton.setBorder(BorderFactory.createEtchedBorder());
    jOneButton.setText("1");
    jOneButton.setPreferredSize(new Dimension(45, 45));
    jOneButton.setBorder(BorderFactory.createEtchedBorder());
    jMultiplyButton.setText("*");
    jMultiplyButton.setPreferredSize(new Dimension(45, 45));
    jMultiplyButton.setBorder(BorderFactory.createEtchedBorder());
    jSixButton.setText("6");
    jSixButton.setPreferredSize(new Dimension(45, 45));
    jSixButton.setBorder(BorderFactory.createEtchedBorder());
    jFiveButton.setText("5");
    jFiveButton.setPreferredSize(new Dimension(45, 45));
    jFiveButton.setBorder(BorderFactory.createEtchedBorder());
    jFourButton.setText("4");
    jFourButton.setPreferredSize(new Dimension(45, 45));
    jFourButton.setBorder(BorderFactory.createEtchedBorder());
    jDivideButton.setBorder(BorderFactory.createEtchedBorder());
    jDivideButton.setPreferredSize(new Dimension(45, 45));
    jDivideButton.setText("/");
    jNineButton.setBorder(BorderFactory.createEtchedBorder());
    jNineButton.setPreferredSize(new Dimension(45, 45));
    jNineButton.setText("9");
    jEightButton.setBorder(BorderFactory.createEtchedBorder());
    jEightButton.setPreferredSize(new Dimension(45, 45));
    jEightButton.setText("8");
    jSevenButton.setBorder(BorderFactory.createEtchedBorder());
    jSevenButton.setPreferredSize(new Dimension(45, 45));
    jSevenButton.setText("7");
    jEnterButton.setText("Enter");
    jParamLabel.setText("Param:");
	jParamLabel.setForeground(Color.black);
    // populate comboBox
    // ATTENTION: These paremeters below should be loaded from
    // from a db or configuration file, etc.
    jParameterComboBox = new JComboBox(new String[]
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

    this.setBackground(Color.lightGray);
    this.setBorder(BorderFactory.createEtchedBorder());

    this.add(jFunctionPanel, new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 27, 0, 63), 141, -6));
    jFunctionPanel.add(jPowerButton, null);
    jFunctionPanel.add(jMaxButton, null);
    jFunctionPanel.add(jMinButton, null);
    jFunctionPanel.add(jEqualButton, null);
    jFunctionPanel.add(jGreaterThanButton, null);
    jFunctionPanel.add(jLessThanButton, null);
    jFunctionPanel.add(jNotEqualButton, null);
    jFunctionPanel.add(jGreaterEqualButton, null);
    jFunctionPanel.add(jLessEqualButton, null);
    this.add(jCalcPanel, new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 31, 0, 69), 86, -74));
    jCalcPanel.add(jSevenButton, null);
    jCalcPanel.add(jEightButton, null);
    jCalcPanel.add(jNineButton, null);
    jCalcPanel.add(jDivideButton, null);
    jCalcPanel.add(jFourButton, null);
    jCalcPanel.add(jFiveButton, null);
    jCalcPanel.add(jSixButton, null);
    jCalcPanel.add(jMultiplyButton, null);
    jCalcPanel.add(jOneButton, null);
    jCalcPanel.add(jTwoButton, null);
    jCalcPanel.add(jThreeButton, null);
    jCalcPanel.add(jSubButton, null);
    jCalcPanel.add(jZeroButton, null);
    jCalcPanel.add(jDecimalButton, null);
    jCalcPanel.add(jSignButton, null);
    jCalcPanel.add(jAddButton, null);
    this.add(jParameterComboBox, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 2, 18), 182, 1));
    this.add(jParamLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 1, 5));
    this.add(jCalcTextField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 205, 7));
    this.add(jEnterButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 43), 9, -1));

    // JavaBeans property management
    propChangeSupport		= new PropertyChangeSupport(this);

    //-----------------------------------------------------
    //                    Eye Candy
    //-----------------------------------------------------

    //Calculator Eye Candy---------------------------------
	
	URL calcButtonURL  = null;
	URL extraButtonURL = null;
	
	try {
	
		calcButtonURL  = new URL(baseURL, "PORTAL/engine/images/calcbutton.gif");
		extraButtonURL = new URL(baseURL, "PORTAL/engine/images/extrabutton.gif");
	}
	catch(Exception e) {
	
		System.out.println(e);
		e.printStackTrace();
	}
	
					  
    jOneButton.setIcon(new ImageIcon(calcButtonURL));
    jOneButton.setToolTipText("one");
    jOneButton.setBackground(Color.lightGray);
    jOneButton.setBorderPainted(false);
    jOneButton.setActionCommand("1");
    jOneButton.setForeground(Color.white);
    jOneButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jOneButton.setVerticalTextPosition(SwingConstants.CENTER);

    jTwoButton.setIcon(new ImageIcon(calcButtonURL));
    jTwoButton.setToolTipText("Two");
    jTwoButton.setBackground(Color.lightGray);
    jTwoButton.setBorderPainted(false);
    jTwoButton.setActionCommand("2");
    jTwoButton.setForeground(Color.white);
    jTwoButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jTwoButton.setVerticalTextPosition(SwingConstants.CENTER);

    jThreeButton.setIcon(new ImageIcon(calcButtonURL));
    jThreeButton.setToolTipText("Three");
    jThreeButton.setBackground(Color.lightGray);
    jThreeButton.setBorderPainted(false);
    jThreeButton.setActionCommand("3");
    jThreeButton.setForeground(Color.white);
    jThreeButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jThreeButton.setVerticalTextPosition(SwingConstants.CENTER);

    jFourButton.setIcon(new ImageIcon(calcButtonURL));
    jFourButton.setToolTipText("Four");
    jFourButton.setBackground(Color.lightGray);
    jFourButton.setBorderPainted(false);
    jFourButton.setActionCommand("4");
    jFourButton.setForeground(Color.white);
    jFourButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jFourButton.setVerticalTextPosition(SwingConstants.CENTER);

    jFiveButton.setIcon(new ImageIcon(calcButtonURL));
    jFiveButton.setToolTipText("Five");
    jFiveButton.setBackground(Color.lightGray);
    jFiveButton.setBorderPainted(false);
    jFiveButton.setActionCommand("5");
    jFiveButton.setForeground(Color.white);
    jFiveButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jFiveButton.setVerticalTextPosition(SwingConstants.CENTER);

    jSixButton.setIcon(new ImageIcon(calcButtonURL));
    jSixButton.setToolTipText("Six");
    jSixButton.setBackground(Color.lightGray);
    jSixButton.setBorderPainted(false);
    jSixButton.setActionCommand("6");
    jSixButton.setForeground(Color.white);
    jSixButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jSixButton.setVerticalTextPosition(SwingConstants.CENTER);

    jSevenButton.setIcon(new ImageIcon(calcButtonURL));
    jSevenButton.setToolTipText("Seven");
    jSevenButton.setBackground(Color.lightGray);
    jSevenButton.setBorderPainted(false);
    jSevenButton.setActionCommand("7");
    jSevenButton.setForeground(Color.white);
    jSevenButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jSevenButton.setVerticalTextPosition(SwingConstants.CENTER);

    jEightButton.setIcon(new ImageIcon(calcButtonURL));
    jEightButton.setToolTipText("Eight");
    jEightButton.setBackground(Color.lightGray);
    jEightButton.setBorderPainted(false);
    jEightButton.setActionCommand("8");
    jEightButton.setForeground(Color.white);
    jEightButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jEightButton.setVerticalTextPosition(SwingConstants.CENTER);

    jNineButton.setIcon(new ImageIcon(calcButtonURL));
    jNineButton.setToolTipText("Nine");
    jNineButton.setBackground(Color.lightGray);
    jNineButton.setBorderPainted(false);
    jNineButton.setActionCommand("9");
    jNineButton.setForeground(Color.white);
    jNineButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jNineButton.setVerticalTextPosition(SwingConstants.CENTER);

    jZeroButton.setIcon(new ImageIcon(calcButtonURL));
    jZeroButton.setToolTipText("Zero");
    jZeroButton.setBackground(Color.lightGray);
    jZeroButton.setBorderPainted(false);
    jZeroButton.setActionCommand("0");
    jZeroButton.setForeground(Color.white);
    jZeroButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jZeroButton.setVerticalTextPosition(SwingConstants.CENTER);

    jDecimalButton.setIcon(new ImageIcon(calcButtonURL));
    jDecimalButton.setToolTipText("Decimal");
    jDecimalButton.setBackground(Color.lightGray);
    jDecimalButton.setBorderPainted(false);
    jDecimalButton.setActionCommand(".");
    jDecimalButton.setForeground(Color.white);
    jDecimalButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jDecimalButton.setVerticalTextPosition(SwingConstants.CENTER);

    jSignButton.setIcon(new ImageIcon(calcButtonURL));
    jSignButton.setToolTipText("Sign");
    jSignButton.setBackground(Color.lightGray);
    jSignButton.setBorderPainted(false);
    jSignButton.setActionCommand("+/-");
    jSignButton.setForeground(Color.white);
    jSignButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jSignButton.setVerticalTextPosition(SwingConstants.CENTER);

    jAddButton.setIcon(new ImageIcon(calcButtonURL));
    jAddButton.setToolTipText("Addition");
    jAddButton.setBackground(Color.lightGray);
    jAddButton.setBorderPainted(false);
    jAddButton.setActionCommand("+");
    jAddButton.setForeground(Color.white);
    jAddButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jAddButton.setVerticalTextPosition(SwingConstants.CENTER);

    jSubButton.setIcon(new ImageIcon(calcButtonURL));
    jSubButton.setToolTipText("Subtraction");
    jSubButton.setBackground(Color.lightGray);
    jSubButton.setBorderPainted(false);
    jSubButton.setActionCommand("-");
    jSubButton.setForeground(Color.white);
    jSubButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jSubButton.setVerticalTextPosition(SwingConstants.CENTER);
	
    jMultiplyButton.setIcon(new ImageIcon(calcButtonURL));
    jMultiplyButton.setToolTipText("Multiply");
    jMultiplyButton.setBackground(Color.lightGray);
    jMultiplyButton.setBorderPainted(false);
    jMultiplyButton.setActionCommand("*");
    jMultiplyButton.setForeground(Color.white);
    jMultiplyButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jMultiplyButton.setVerticalTextPosition(SwingConstants.CENTER);

    jDivideButton.setIcon(new ImageIcon(calcButtonURL));
    jDivideButton.setToolTipText("Divide");
    jDivideButton.setBackground(Color.lightGray);
    jDivideButton.setBorderPainted(false);
    jDivideButton.setActionCommand("/");
    jDivideButton.setForeground(Color.white);
    jDivideButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jDivideButton.setVerticalTextPosition(SwingConstants.CENTER);

    //----------------------------------------------------------------
    //Functional Panel Eye Candy
	//---------------------------------------------------------------------

    jMaxButton.setIcon(new ImageIcon(extraButtonURL));
    jMaxButton.setToolTipText("Max");
    jMaxButton.setBackground(Color.lightGray);
    jMaxButton.setBorderPainted(false);
    jMaxButton.setActionCommand("max");
    jMaxButton.setForeground(Color.white);
    jMaxButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jMaxButton.setVerticalTextPosition(SwingConstants.CENTER);

    jMinButton.setIcon(new ImageIcon(extraButtonURL));
    jMinButton.setToolTipText("Min");
    jMinButton.setBackground(Color.lightGray);
    jMinButton.setBorderPainted(false);
    jMinButton.setActionCommand("min");
    jMinButton.setForeground(Color.white);
    jMinButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jMinButton.setVerticalTextPosition(SwingConstants.CENTER);

    jPowerButton.setIcon(new ImageIcon(extraButtonURL));
    jPowerButton.setToolTipText("Power");
    jPowerButton.setBackground(Color.lightGray);
    jPowerButton.setBorderPainted(false);
    jPowerButton.setActionCommand("power");
    jPowerButton.setForeground(Color.white);
    jPowerButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jPowerButton.setVerticalTextPosition(SwingConstants.CENTER);

    jEqualButton.setIcon(new ImageIcon(extraButtonURL));
    jEqualButton.setToolTipText("Equal");
    jEqualButton.setBackground(Color.lightGray);
    jEqualButton.setBorderPainted(false);
    jEqualButton.setActionCommand("eq");
    jEqualButton.setForeground(Color.white);
    jEqualButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jEqualButton.setVerticalTextPosition(SwingConstants.CENTER);

    jGreaterThanButton.setIcon(new ImageIcon(extraButtonURL));
    jGreaterThanButton.setToolTipText("Greater Than");
    jGreaterThanButton.setBackground(Color.lightGray);
    jGreaterThanButton.setBorderPainted(false);
    jGreaterThanButton.setActionCommand("gt");
    jGreaterThanButton.setForeground(Color.white);
    jGreaterThanButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jGreaterThanButton.setVerticalTextPosition(SwingConstants.CENTER);

    jLessThanButton.setIcon(new ImageIcon(extraButtonURL));
    jLessThanButton.setToolTipText("Less Than");
    jLessThanButton.setBackground(Color.lightGray);
    jLessThanButton.setBorderPainted(false);
    jLessThanButton.setActionCommand("lt");
    jLessThanButton.setForeground(Color.white);
    jLessThanButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jLessThanButton.setVerticalTextPosition(SwingConstants.CENTER);

    jNotEqualButton.setIcon(new ImageIcon(extraButtonURL));
    jNotEqualButton.setToolTipText("Not Equal");
    jNotEqualButton.setBackground(Color.lightGray);
    jNotEqualButton.setBorderPainted(false);
    jNotEqualButton.setActionCommand("ne");
    jNotEqualButton.setForeground(Color.white);
    jNotEqualButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jNotEqualButton.setVerticalTextPosition(SwingConstants.CENTER);

    jGreaterEqualButton.setIcon(new ImageIcon(extraButtonURL));
    jGreaterEqualButton.setToolTipText("Greater Equal");
    jGreaterEqualButton.setBackground(Color.lightGray);
    jGreaterEqualButton.setBorderPainted(false);
    jGreaterEqualButton.setActionCommand("ge");
    jGreaterEqualButton.setForeground(Color.white);
    jGreaterEqualButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jGreaterEqualButton.setVerticalTextPosition(SwingConstants.CENTER);

    jLessEqualButton.setIcon(new ImageIcon(extraButtonURL));
    jLessEqualButton.setToolTipText("Less Equal");
    jLessEqualButton.setBackground(Color.lightGray);
    jLessEqualButton.setBorderPainted(false);
    jLessEqualButton.setActionCommand("le");
    jLessEqualButton.setForeground(Color.white);
    jLessEqualButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jLessEqualButton.setVerticalTextPosition(SwingConstants.CENTER);

    jEnterButton.setIcon(new ImageIcon(extraButtonURL));
    jEnterButton.setToolTipText("Enter");
    jEnterButton.setBackground(Color.lightGray);
    jEnterButton.setBorderPainted(false);
	jEnterButton.setActionCommand("enter");
    jEnterButton.setForeground(Color.white);
    jEnterButton.setHorizontalTextPosition(SwingConstants.CENTER);
    jEnterButton.setVerticalTextPosition(SwingConstants.CENTER);


    //---------------------------------------------------------------------
    //      Register Listeners
    //---------------------------------------------------------------------
    jOneButton.addActionListener(this);
    jTwoButton.addActionListener(this);
    jThreeButton.addActionListener(this);
    jFourButton.addActionListener(this);
    jFiveButton.addActionListener(this);
    jSixButton.addActionListener(this);
    jSevenButton.addActionListener(this);
    jEightButton.addActionListener(this);
    jNineButton.addActionListener(this);
    jZeroButton.addActionListener(this);
    jDecimalButton.addActionListener(this);
    jSignButton.addActionListener(this);
    jMultiplyButton.addActionListener(this);
    jDivideButton.addActionListener(this);
    jAddButton.addActionListener(this);
    jSubButton.addActionListener(this);
    jEnterButton.addActionListener(this);
    jCalcTextField.addActionListener(this);
    jMinButton.addActionListener(this);
    jMaxButton.addActionListener(this);
    jPowerButton.addActionListener(this);
    jEqualButton.addActionListener(this);
    jGreaterThanButton.addActionListener(this);
    jLessThanButton.addActionListener(this);
    jNotEqualButton.addActionListener(this);
    jGreaterEqualButton.addActionListener(this);
    jLessEqualButton.addActionListener(this);
	
	//Add Keystroke listener
	jCalcTextField.addKeyListener(this);


    jParameterComboBox.addActionListener(this);
  }
    //----------------------------------------------
    //        		Public Methods
    // ---------------------------------------------
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

		propChangeSupport.addPropertyChangeListener(pcl);
    }

    public Dimension getPreferredSize() {

      return new Dimension(400, 200);
    }
	
	//Set focus method
	public void setFocus() {
	
		jCalcTextField.requestFocus();
	}	
			
    //----------------------------------------------
    //        		Private Methods
    // ---------------------------------------------
	private void firePropertyChangeEvent(String eventMsg)  {

    	PropertyChangeEvent event = new PropertyChangeEvent(this,
	 		                  	                    "CALCULATOR_EVENT",
	                                                  null,
	                                                  eventMsg);
		propChangeSupport.firePropertyChange(event);
	}

  	public void actionPerformed(ActionEvent e) {
		
        String text = jCalcTextField.getText().trim();
		Object ref = e.getSource();

		  if (e.getActionCommand().equalsIgnoreCase("enter") || (e.getSource() == jCalcTextField) ) {
			if ((jCalcTextField.getText() == null) || (jCalcTextField.getText().length() == 0)) {
				return;			
			}else {
				firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
				text = "";
			}
		  }
          else if (e.getActionCommand().equals( "1")) {
            text += "1";
          }
          else if (e.getActionCommand().equals( "2")) {
            text += "2";
          }
          else if (e.getActionCommand().equals( "3")) {
            text += "3";
          }
          else if (e.getActionCommand().equals( "4")) {
            text += "4";
          }
          else if (e.getActionCommand().equals( "5")) {
            text += "5";
          }
          else if (e.getActionCommand().equals( "6")) {
            text += "6";
          }
          else if (e.getActionCommand().equals( "7")) {
            text += "7";
          }
          else if (e.getActionCommand().equals( "8")) {
            text += "8";
          }
          else if (e.getActionCommand().equals( "9")) {
            text += "9";
          }
          else if (e.getActionCommand().equals( "0")) {
            text += "0";
          }
          else if (e.getActionCommand().equals( ".")) {
            text += ".";
          }
          else if (e.getActionCommand().equals( "+/-")) {
            String number = jCalcTextField.getText();
            float i = - Float.parseFloat(number);
            text = Float.toString(i);
          }
          else if (e.getActionCommand().equals( "+")) {
            if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("add");

			text = "";
          }
          else if (e.getActionCommand().equals( "-")) {

            if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("sub");

			text = "";
          }
          else if (e.getActionCommand().equals( "/")) {

			if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("div");

			text = "";
          }
          else if (e.getActionCommand().equals( "*")) {

			if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("mult");

			text = "";
           }
          else if (e.getActionCommand().equals( "max")) {

            if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("max");

			text = "";
          }
          else if (e.getActionCommand().equals( "min")) {

			if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("min");

			text = "";
          }
          else if (e.getActionCommand().equals( "power")) {

            if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("power");

			text = "";
          }
		  else if ( (e.getActionCommand().equalsIgnoreCase("eq"))
		  		|| (e.getActionCommand().equalsIgnoreCase("gt"))
		   		|| (e.getActionCommand().equalsIgnoreCase( "lt")) 
		   		|| (e.getActionCommand().equalsIgnoreCase( "ne")) 
		   		|| (e.getActionCommand().equalsIgnoreCase( "ge")) 
		   		|| (e.getActionCommand().equalsIgnoreCase( "le")) ) {
		
			String val = e.getActionCommand();
						
			Object frame = enhancedApplet.getParent();
			while ( !( frame instanceof Frame) ) 
				frame = ( (Component) frame).getParent();
			
			IfCondition ic = new IfCondition(enhancedApplet.getScriptNames(), val, (Frame) frame);
		
			ic.setSize(390,280);
			ic.setVisible(true);

			if (ic.cancelSelected() == false) {
				
				value = ic.getScriptLine().trim();
				firePropertyChangeEvent(value);
				jCalcTextField.requestFocus();
				return; 
			}
			else {				
				
				return;
			}
		 }
		 else if (ref == jParameterComboBox) {

			// ATTENTION - Using the up and down areas with the combo box
			// causes the event to be fired. This is not acceptable.
			firePropertyChangeEvent("push param:" + (String) jParameterComboBox.getSelectedItem());
			jParameterComboBox.setSelectedIndex(0);
		}
          jCalcTextField.setText(text);
		  jCalcTextField.requestFocus();
        }
   
	  public void keyTyped(KeyEvent e)  {
	
		char key = e.getKeyChar();		  
		
		if (key == '+') {
		
			if (jCalcTextField.getText().trim().length() != 0) {

    		        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

			  	firePropertyChangeEvent("add");

				jCalcTextField.setText("");
          }
          else if (key == '-') {

            if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("sub");

			jCalcTextField.setText("");
          }
          else if (key == '/') {

			if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("div");

			jCalcTextField.setText("");
          }
          else if (key == '*') {

			if (jCalcTextField.getText().trim().length() != 0) {

    	        firePropertyChangeEvent("push num:" + jCalcTextField.getText().trim());
            }

		  	firePropertyChangeEvent("mult");

			jCalcTextField.setText("");
           }
	
	    jCalcTextField.requestFocus();
		
	   }
	 
	  public void keyPressed(KeyEvent e)  {
	  
	  }

	 public void keyReleased(KeyEvent e)  {
	 	
		char key = e.getKeyChar();
		
	 	if (key == '+' || key == '-' || key == '*' || key == '/') {
	 		jCalcTextField.setText("");
		}				
	 	
	  }

}   
	