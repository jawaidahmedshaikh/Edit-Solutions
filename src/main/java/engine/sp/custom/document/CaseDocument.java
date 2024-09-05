package engine.sp.custom.document;

import group.ContractGroup;

import edit.common.EDITMap;

import engine.common.Constants;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import contract.*;

/**
 * Group processing really refers to "Case" or "Group" level structures - both
 * are based on the ContractGroup table.
 * 
 * In the case of the "Case" ContractGroup, it is a simple composition of the
 * following entities driven by the supplied "ContractGroupPK":
 * 
 * ContractGroup
 */
public class CaseDocument extends PRASEDocBuilder
{
    /**
     * The driving PK of the "Case" ContractGroup.
     */
    private Long contractGroupPK;

    private Long productStructureFK;

    /**
     * The Working Storage-supplied parameter which drives this document.
     */
    public static final String BUILDING_PARAMETER_NAME_CONTRACTGROUPPK = "ContractGroupPK";

    public static final String BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREFK = "ProductStructureFK";

    /**
     * The parameters names to be extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_CONTRACTGROUPPK,
                                                            BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREFK};
    
    public static final String ROOT_ELEMENT_NAME = "CaseDocVO";
    
    public CaseDocument(){}

    /**
     * A convenience constructor.
     * @param contractGroupPK
     */
    public CaseDocument(Long contractGroupPK)
    {
      super(new EDITMap(BUILDING_PARAMETER_NAME_CONTRACTGROUPPK, contractGroupPK.toString()));
    
      this.contractGroupPK = contractGroupPK;
    }

   /**
     * A convenience constructor.
     * @param contractGroupPK
     */
    public CaseDocument(Long contractGroupPK, Long productStructureFK)
    {
      super(new EDITMap(BUILDING_PARAMETER_NAME_CONTRACTGROUPPK, contractGroupPK.toString()).put(BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREFK, productStructureFK));

      this.contractGroupPK = contractGroupPK;

      this.productStructureFK = productStructureFK;
    }

    /**
     * Constructor. The ContractGroupPK is expected to be in the specified building params keyed by the BUILDING_PARAM.
     * ProductStructureFK may and may not be a parameter
     * @see #BUILDING_PARAMETER_NAME_CONTRACTGROUPPK
     * @param buildingParams
     */
    public CaseDocument(Map<String, String> buildingParams)
    {
      super(buildingParams);
      
      String contractGroupPKStr = buildingParams.get(BUILDING_PARAMETER_NAME_CONTRACTGROUPPK);

      String productStructureFK = buildingParams.get(BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREFK);

      // Watch for #NULL should it come in.
      if (contractGroupPKStr.equals(Constants.ScriptKeyword.NULL))
      {
        initEmptyDocument();
      }
      else
      {
        this.contractGroupPK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_CONTRACTGROUPPK));

        if (productStructureFK != null)
        {
            this.productStructureFK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREFK));
        }
      }
    }
    
    /**
     * Builds the document.
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK_V1(getContractGroupPK());

            Element caseDocVOElement = initEmptyDocument();

            buildContractGroupElement(contractGroup, caseDocVOElement);
        }
    }

    /**
     * Defined as "CaseDocVO".
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
     * the specified (parent) caseDocVOElement.
     * @param contractGroup
     * @param caseDocVOElement
     */
    private void buildContractGroupElement(ContractGroup contractGroup, Element caseDocVOElement)
    {
        Element contractGroupElement = contractGroup.getAsElement();

        caseDocVOElement.add(contractGroupElement);
        
        FilteredProduct filteredProduct = null;
        if (productStructureFK != null)
        {
            filteredProduct = FilteredProduct.findBy_ProductStructurePK_ContractGroup(productStructureFK, contractGroup);

            if (filteredProduct != null)
            {
                Element filteredProductElement = filteredProduct.getAsElement();
                contractGroupElement.add(filteredProductElement);
            }
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
        Element caseDocVOElement = new DefaultElement(getRootElementName());

        setRootElement(caseDocVOElement);

        setDocumentBuilt(true);

        return caseDocVOElement;
    }    
}
