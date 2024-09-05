package engine.sp.custom.document;

import billing.BillSchedule;

import group.ContractGroup;

import group.PayrollDeductionSchedule;

import edit.common.EDITMap;

import engine.common.Constants;
import java.util.Map;

import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Group processing really refers to "Case" or "Group" level structures - both
 * are based on the ContractGroup table.
 * 
 * In the case of the "Group" ContractGroup, it is a simple composition of the
 * following entities driven by the supplied "ContractGroupPK":
 * 
 * ContractGroup
 * ContractGroup.BillSchedule (0..1)
 * ContractGroup.PayrollDeductionSchedule (0..1)
 */
public class GroupDocument extends PRASEDocBuilder
{
    /**
     * The driving PK of the "Group" ContractGroup.
     */
    private Long contractGroupPK;

    /**
     * The Working Storage-supplied parameter which drives this document.
     */
    public static final String BUILDING_PARAMETER_NAME_CONTRACTGROUPPK = "ContractGroupPK";
    
    /**
     * The parameters names to be extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_CONTRACTGROUPPK};
    
    public static final String ROOT_ELEMENT_NAME = "GroupDocVO";

    public GroupDocument(){}

    /**
     * A convenience constructor.
     * @param contractGroupPK
     */
    public GroupDocument(Long contractGroupPK)
    {
      super(new EDITMap(BUILDING_PARAMETER_NAME_CONTRACTGROUPPK, contractGroupPK.toString()));
    
      this.contractGroupPK = contractGroupPK;
    }
    
    /**
     * Constructor. The TrxEffectiveDate is expected to be in the
     * specified building params keyed by the BUILDING_PARAM.
     * @see #BUILDING_PARAMETER_NAME_CONTRACTGROUPPK
     * @param buildingParams
     */
    public GroupDocument(Map<String, String> buildingParams)
    {
      super(buildingParams);
    
      String contractGroupPKStr = buildingParams.get(BUILDING_PARAMETER_NAME_CONTRACTGROUPPK);
      
      // Watch for #NULL should it come in.
      if (contractGroupPKStr.equals(Constants.ScriptKeyword.NULL))
      {
        initEmptyDocument();
      }
      else
      {
        this.contractGroupPK = new Long(contractGroupPKStr);            
      }
    }
    
    /**
     * Builds the document.
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element groupDocVOElement = initEmptyDocument();
            
            ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK_V1(getContractGroupPK());
            
            buildContractGroupElement(contractGroup, groupDocVOElement);

            buildBillScheduleElement(contractGroup.getBillSchedule(), groupDocVOElement);
        }
    }

    /**
     * Defined as "GroupDocVO".
     * @return
     */
    public String getRootElementName()
    {
        return ROOT_ELEMENT_NAME;
    }

    /**
     * @see #contractGroupPK
     * @return
     */
    public Long getContractGroupPK()
    {
        return contractGroupPK;
    }

    /**
     * Builds builds and associates the specified contractGroup as a DOM4J element to 
     * the specified (parent) groupDocVOElement. The building process continues with
     * the PayrollDeductionSchedule (if it exists).
     * @param contractGroup
     * @param groupDocVOElement
     */
    private void buildContractGroupElement(ContractGroup contractGroup, Element groupDocVOElement)
    {
        Element contractGroupElement = contractGroup.getAsElement();
        
        groupDocVOElement.add(contractGroupElement);
        
        buildPayrollDeductionElement(contractGroup.getPayrollDeductionSchedules(), contractGroupElement);
    }

    /**
     * Builds the BillSchedule as a child of the GroupDocVO. Since it is an association of ContractGroup (not a 
     * child), it was moved to this level. Building continues with the BillSkip entity.
     * @param billSchedule
     * @param groupDocVOElement
     */
    private void buildBillScheduleElement(BillSchedule billSchedule, Element groupDocVOElement)
    {
        if (billSchedule != null)
        {
            Element billScheduleElement = billSchedule.getAsElement();
            
            groupDocVOElement.add(billScheduleElement);            
        }
    }

    /**
     * Builds the PayrollDeductionElement and adds it to its parent structure of contractGroupElement. 
     * There are at most one.
     * @param payrollDeductionSchedules
     * @param contractGroupElement
     */
    private void buildPayrollDeductionElement(Set<PayrollDeductionSchedule> payrollDeductionSchedules, Element contractGroupElement)
    {
        if (!payrollDeductionSchedules.isEmpty())
        {
            Element payrollDedectionElement = payrollDeductionSchedules.iterator().next().getAsElement();
            
            contractGroupElement.add(payrollDedectionElement);
        }
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }

    /**
     * An "empty" document is the default starting point for any document.
     * The document may be populated with contents, but it may not as well. For
     * example, the building parameter PK may be "#NULL". That flags a business
     * decision to build the empty document (only).
     * 
     * @return the default empty document
     */
    private Element initEmptyDocument()
    {
        Element groupDocVOElement = new DefaultElement(getRootElementName());

        setRootElement(groupDocVOElement);

        setDocumentBuilt(true);

        return groupDocVOElement;
    }
}
