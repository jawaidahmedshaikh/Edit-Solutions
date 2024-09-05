package engine.sp.custom.document;

import edit.common.EDITMap;

import edit.services.db.hibernate.SessionHelper;

import event.ClientSetup;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;
import event.InvestmentAllocationOverride;
import event.OverdueChargeRemaining;

import fission.utility.DOMUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;


/**
 * 1. The population of OverdueChargeRemainingVO in Natural and Redo Docs will need to be updated.  
 * The VOs should continue to include any Overdue Charge records for transactions in an overdue (O) status,
 * but the values will need to be calculated rather than pulled directly from the table.  
 * 
 * Calculation of amounts should be as follows:
 * 
 * a. Remaining Coi = Overdue Charge Remaining Coi value minus the sum of all Overdue Charge Settled table Settled Coi records 
 * where the Overdue Charge Settled Overdue Charge FK = Overdue Charge Overdue Charge PK 
 * (excluding those Overdue Charge Settled records for EDITTrxs with Status of U or R)
 * 
 * b. Remaining Admin = Overdue Charge Remaining Admin value minus the sum of all Overdue Charge Settled table Settled Admin 
 * records where the Overdue Charge Settled Overdue Charge FK = Overdue Charge Overdue Charge PK 
 * (excluding those Overdue Charge Settled records for EDITTrxs with Status of U or R)
 * 
 * c. Remaining Expense = Overdue Charge Remaining Expense value minus the sum of all Overdue Charge Settled table Settled Expense 
 * records where the Overdue Charge Settled Overdue Charge FK = Overdue Charge Overdue Charge PK 
 * (excluding those Overdue Charge Settled records for EDITTrxs with Status of U or R)
 * 
 * 2. The setting of EDITTrx Pending Status from overdue O to history H done during the update process (post-PRASE) 
 * will need to be modified to perform the same calculation as #2 above to determine when the Pending Status should be updated.  
 * When the result for each of the 3 remaining values is 0 (Coi, Admin and Expense), the Pending Status should be set from O to H.
 * 
 * 3. The population of the Overdue Charge Remaining pop-up on the history pages will need to be modified to calculate the remaining values in the same manner as #2.
 * 
 * Structure:
 * 
 * OverdueChargeRemainingDocVO
 */
public class OverdueChargeRemainingDocument extends PRASEDocBuilder
{
    /**
     * The driving editTrxPK.
     */
    private Long editTrxPK;

    /**
     * 
     */
    private EDITTrx editTrx;

    /**
     * The key name of the driving EDITTrxPK.
     */
    public static final String BUILDING_PARAMETER_NAME_EDITTRX = "EDITTrxPK";

    /**
     * The list of all paramter names that could be used in the building of this document.
     */
    private static final String[] buildingParameterNames = { BUILDING_PARAMETER_NAME_EDITTRX };

    public OverdueChargeRemainingDocument(){}
    
    /**
     * Constuctor. The specified building params is expected to contain the EDITTrxPK.
     * @param buildingParameters
     */
    public OverdueChargeRemainingDocument(Map<String, String> buildingParameters)
    {
        super(buildingParameters);

        this.editTrxPK = new Long(buildingParameters.get(OverdueChargeRemainingDocument.BUILDING_PARAMETER_NAME_EDITTRX));
    }

    /**
      * Constructor. The specified building parameters is expected to contain
      * the keyed EDITTrxPK.
      * @param editTrxPK
      * @see #BUILDING_PARAMETER_NAME_EDITTRX
      */
    public OverdueChargeRemainingDocument(Long editTrxPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_EDITTRX, editTrxPK.toString()));

        this.editTrxPK = editTrxPK;
    }
    
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element overdueChargeRemainingDocumentElement = buildOverdueChargeRemainingDocumentElement();
      
            // The datasource for this document.
            OverdueChargeRemaining overdueChargeRemaining = new OverdueChargeRemaining(getEDITTrx(), OverdueChargeRemaining.SCOPE_SEGMENT_LEVEL);
      
            overdueChargeRemaining.calculate();
      
            Set<OverdueChargeRemaining.OverdueChargeRemainingAmount> amounts = overdueChargeRemaining.getOverdueChargeRemainingAmounts();
      
            for (OverdueChargeRemaining.OverdueChargeRemainingAmount amount: amounts)
            {
                Element overdueChargeRemainingElement = buildOverdueChargeRemainingElement(amount);
      
                overdueChargeRemainingDocumentElement.add(overdueChargeRemainingElement);      
            }
          
            setRootElement(overdueChargeRemainingDocumentElement);
          
            setDocumentBuilt(true);
        }
    }

    /**
      * The driving EDITTrxPK.
      */
    private Long getEDITTrxPK()
    {
        return editTrxPK;
    }
  
    /**
      * Returns true if the driving transaction is new.
      * @return
      */
    private boolean isNewTransaction()
    {
        return this.editTrxPK == 0 ? true : false;
    }
  
    /**
      * The driving EDITTrx. This builds a composite of...
      * 
      * EDITTrx.ClientSetup.ContractSetup
      * 
      * ... since a later query will need object associations of the EDITTrx.
      * @return
      */
    private EDITTrx getEDITTrx()
    {
        if (editTrx == null)
        {
            if (isNewTransaction())
            {
                editTrx = getEDITTrxFromGroupSetupDocument();
            }
            else
            {
                editTrx = EDITTrx.findSeparateBy_EDITTrxPK_V1(getEDITTrxPK());      
            }      
        }
        
        return editTrx;
    }
  
    /**
     * Builds GroupSetup, ContractSetup, ClientSetup and EDITTrx from GroupSetupDocVO.
     * @return
     */
    // Always expects to have one and only one GroupSetup, ContractSetup, ClientSetup and edittrx.
    // Always must have GroupSetup, ContractSetup, ClientSetup and EDITTrx
    private EDITTrx getEDITTrxFromGroupSetupDocument()
    {
        PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder) getSPParams().getDocumentByName("GroupSetupDocVO");
        
        Element groupSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO", groupSetupDocument).get(0);
        
        GroupSetup groupSetup = (GroupSetup) SessionHelper.mapToHibernateEntity(GroupSetup.class, groupSetupElement, SessionHelper.EDITSOLUTIONS);
        
        Element contractSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO", groupSetupDocument).get(0);
        
        ContractSetup contractSetup = (ContractSetup) SessionHelper.mapToHibernateEntity(ContractSetup.class, contractSetupElement, SessionHelper.EDITSOLUTIONS);
        
        Element clientSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO", groupSetupDocument).get(0);
        
        ClientSetup clientSetup = (ClientSetup) SessionHelper.mapToHibernateEntity(ClientSetup.class, clientSetupElement, SessionHelper.EDITSOLUTIONS);
        
        Element editTrxElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO", groupSetupDocument).get(0);
        
        EDITTrx editTrx = (EDITTrx) SessionHelper.mapToHibernateEntity(EDITTrx.class, editTrxElement, SessionHelper.EDITSOLUTIONS);

        List<InvestmentAllocationOverride> investmentAllocationOverrides = new ArrayList<InvestmentAllocationOverride>();

        List<Element> investmentAllocationOverrideElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.InvestmentAllocationOverrideVO", groupSetupDocument);
        
        for (Element investmentAllocationOverrideElement : investmentAllocationOverrideElements)
        {
            InvestmentAllocationOverride investmentAllocationOverride = (InvestmentAllocationOverride) SessionHelper.mapToHibernateEntity(InvestmentAllocationOverride.class, investmentAllocationOverrideElement, SessionHelper.EDITSOLUTIONS);
            
            investmentAllocationOverrides.add(investmentAllocationOverride);            
        }
        
        groupSetup.addContractSetup(contractSetup);
        contractSetup.addClientSetup(clientSetup);
        clientSetup.addEDITTrx(editTrx);
        
        for (InvestmentAllocationOverride investmentAllocationOverride : investmentAllocationOverrides)
        {
            contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
        }
        
        return editTrx;
    }
    
    /**
      * The root element of this OverdueChargeRemainingDocument.
      * @return
      */
    private Element buildOverdueChargeRemainingDocumentElement()
    {
        Element rootElement = new DefaultElement(getRootElementName());
        
        return rootElement;
    }

    /**
      * Creates the summary element for OverdueCharges for the the specified OverdueCharge(PK).
      * @param amount
      * @return the constructed OverdueChargeRemaining Element
      */
    private Element buildOverdueChargeRemainingElement(OverdueChargeRemaining.OverdueChargeRemainingAmount amount)
    {
        Element overdueChargeRemainingElement = new DefaultElement("OverdueChargeRemainingVO");
        
        // OverdueChargePK
        Element overdueChargePKElement = new DefaultElement("OverdueChargePK");
        
        overdueChargePKElement.setText(amount.getOverdueCharge().getOverdueChargePK().toString());
        
        // RemainingCoi
        Element remainingCoiElement = new DefaultElement("RemainingCoi");
        
        remainingCoiElement.setText(amount.getRemainingCoi().toString());
        
        // RemainingAdmin
        Element remainingAdminElement = new DefaultElement("RemainingAdmin");
        
        remainingAdminElement.setText(amount.getRemainingAdmin().toString());
        
        // RemainingExpense
        Element remainingExpenseElement = new DefaultElement("RemainingExpense");
        
        remainingExpenseElement.setText(amount.getRemainingExpense().toString());
        
        // Add them together.
        overdueChargeRemainingElement.add(overdueChargePKElement);

        overdueChargeRemainingElement.add(remainingCoiElement);

        overdueChargeRemainingElement.add(remainingAdminElement);

        overdueChargeRemainingElement.add(remainingExpenseElement);

        return overdueChargeRemainingElement;
    }

    /**
     * 
     * @return
     */
    public String getRootElementName()
    {
        return "OverdueChargeRemainingDocVO";
    }

    public String[] getBuildingParameterNames()
    {
        return this.buildingParameterNames;
    }
}
