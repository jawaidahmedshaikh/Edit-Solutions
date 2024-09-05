/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.sp.custom.document;

import engine.ProductStructure;
import java.util.Map;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * As of this writing, this is a trivial document and consists of a single
 * VO; the ProductStructureVO.
 * 
 * Current limitations are preventing us from including the ProductStructureVO
 * as an additional element in the SegmentDocument (mostly because the Redo document
 * has not yet been converted to Hiberate/Dom4J). As such, we have opted to create
 * a separate document.
 * 
 * Note to future developers: There is a CRUD API that is to be added to the functionality of
 * PRASE. This includes the ability to run ad-hoc queries via hql. I mention this because should
 * that API had been available, I would have used the ad-hoc query instead of this approach.
 * 
 * @author gfrosti
 */
public class ProductStructureDocument extends PRASEDocBuilder
{
    /**
     * The driving PK of the ProductStructureDocVO.
     */
    private Long productStructurePK;

    /**
     * The Working Storage-supplied parameter which drives this document.
     */
    public static final String BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREPK = "ProductStructurePK";
    
    /**
     * The parameters names to be extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREPK};
    
    public static final String ROOT_ELEMENT_NAME = "ProductStructureDocVO";
    
    public ProductStructureDocument(){}
    
    /**
     * Constructor. The ProductStructurePK is expected to be in the
     * specified building params keyed by the BUILDING_PARAM.
     * @see #BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREPK
     * @param buildingParams
     */
    public ProductStructureDocument(Map<String, String> buildingParams)
    {
      super(buildingParams);
      
      this.productStructurePK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_PRODUCTSTRUCTUREPK));        
    }
    
    /**
     * Builds the document.
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            ProductStructure productStructure = ProductStructure.findSeparateBy_ProductStructurePK(productStructurePK);

            Element rootElement = new DefaultElement(getRootElementName());

            buildProductStructureElement(productStructure, rootElement);
            
            setRootElement(rootElement);

            setDocumentBuilt(true);            
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
     * @see #productStructurePK
     * @return
     */
    public Long getProductStructurePK()
    {
        return productStructurePK;
    }

    /**
     * Builds builds and associates the specified productStructure as a DOM4J element to 
     * the specified (parent) ProductStructureDocVO (Element).
     * @param contractGroup
     * @param caseDocVOElement
     */
    private void buildProductStructureElement(ProductStructure productStructure, Element productStructureDocVOElement)
    {
        Element productStructureElement = productStructure.getAsElement();
        
        productStructureDocVOElement.add(productStructureElement);
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
