package engine.sp.custom.document;

import edit.common.EDITMap;

import java.util.Map;

import javax.jws.WebService;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import reinsurance.ContractTreaty;


/**
 * Driven by a Base Segment, this buildds a Reinsurance Element for each ContractTreaty
 * associated with the driving Base Segment.
 * 
 * Business rules are as follows:
 * 
 * There are no business rules. This is a structural-only document and contains the following
 * entities:
 * 
 * ReinsuranceDocVO.TreatyVO
 * ReinsuranceDocVO.TreatyVO.ContractTreatyVO
 * ReinsuranceDocVO.TreatyGroup.TreatyGroupNumber
 * ReinsuranceDocVO.Reinsurer.ReinsurerNumber
 * 
 */
public class ReinsuranceDocument extends PRASEDocBuilder
{
  /**
   * The driving SegmentPK.
   */
  private Long segmentPK;
  
  /**
   * The named building key.
   */
  public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";
  
  /**
   * The building parameters extracted from working storage used to build this document.
   */
  private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_SEGMENTPK};
  
  public ReinsuranceDocument(){}
  
  public ReinsuranceDocument(Long segmentPK)
  {
    super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK.toString()));
  
    this.segmentPK = segmentPK;
  }
  
  /**
   * Constructor. The specified building parameters is expected to 
   * contain the SegmentPK.
   * @param buildingParameters
   */
  public ReinsuranceDocument(Map<String, String> buildingParameters)
  {
    super(buildingParameters);
  
    this.segmentPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_SEGMENTPK));
  }

  /**
   * Adds a Reinsurance entity for each ContractTreaty associated with the driving Segment.
   */
  public void build()
  {
    if (!isDocumentBuilt())
    {
      // The driving data source.
      ContractTreaty[] contractTreaties = ContractTreaty.findBy_SegmentPK_V1(getSegmentPK());
      
      Element reinsuranceDocumentElement = buildReinsuranceDocumentElement(contractTreaties);
      
      setRootElement(reinsuranceDocumentElement);
      
      setDocumentBuilt(true);
    }
  }

  /**
   * Getter.
   * @see #segmentPK
   * @return
   */
  public Long getSegmentPK()
  {
    return segmentPK;
  }

  /**
   * The root Element of this Document. The building process continues with the
   * Reinsurance element(s).
   * @return
   */
  private Element buildReinsuranceDocumentElement(ContractTreaty[] contractTreaties)
  {
    Element reinsuranceDocumentElement = new DefaultElement("ReinsuranceDocVO");
  
    for (ContractTreaty contractTreaty: contractTreaties)
    {
      buildReinsuranceElement(contractTreaty, reinsuranceDocumentElement);
    }
    
    return reinsuranceDocumentElement;
  }

  /**
   * Builds the ReinsuranceElement with its TreatyGroupNumber and ReinsurerNumber.
   * The building process continues with TreatyGroup.
   * @param contractTreaty the driving Element
   * @param reinsuranceDocumentElement the containing Element
   */
  private void buildReinsuranceElement(ContractTreaty contractTreaty, Element reinsuranceDocumentElement)
  {
    // The current element.
    Element reinsuranceElement = new DefaultElement("ReinsuranceVO");
  
    Element treatyGroupNumberElement = new DefaultElement("TreatyGroupNumber");
    
    treatyGroupNumberElement.setText(contractTreaty.getTreaty().getTreatyGroup().getTreatyGroupNumber());
    
    Element reinsurerNumberElement = new DefaultElement("ReinsurerNumber");
    
    reinsurerNumberElement.setText(contractTreaty.getTreaty().getReinsurer().getReinsurerNumber());
    
    reinsuranceElement.add(treatyGroupNumberElement);
    
    reinsuranceElement.add(reinsurerNumberElement);
    
    // Continue the building process..
    buildTreatyElement(contractTreaty, reinsuranceElement);
    
    reinsuranceDocumentElement.add(reinsuranceElement);
  }

  /**
   * Builds the TreatyElement and continues the building process with the ContractTreaty
   * Element.
   * @param contractTreaty the driving Element in that there is a ContractTreaty.Treaty association
   * @param reinsuranceElement the containing Element
   */
  private void buildTreatyElement(ContractTreaty contractTreaty, Element reinsuranceElement)
  {
    Element treatyElement = contractTreaty.getTreaty().getAsElement();
    
    reinsuranceElement.add(treatyElement);
    
    buildContractTreatyElement(contractTreaty, treatyElement);
  }

  /**
   * Builds the ContractTreatyElement.
   * @param contractTreaty the driving Element
   * @param treatyElement the containing Element
   */
  private void buildContractTreatyElement(ContractTreaty contractTreaty, Element treatyElement)
  {
    Element contractTreatyElement = contractTreaty.getAsElement();
    
    treatyElement.add(contractTreatyElement);
  }

  /**
   * 
   * @return
   */
  public String getRootElementName()
  {
    return "ReinsuranceDocVO";
  }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
