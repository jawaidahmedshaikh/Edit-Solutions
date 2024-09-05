package engine.sp.custom.document;

import contract.Segment;

import edit.common.EDITMap;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;


/**
 * Creates a SegmentDocument entry for each (rider) Segment that is associated
 * with the specified driving base Segment.
 * 
 * The structure completely mimics the SegmentDocument except that there may
 * be multiples of the structure: one for each rider Segment.
 * 
 * The structure is:
 * RiderDocVO
 * SegmentDocVO (one for each rider - see SegmentDocument)
 */
public class RiderDocument extends PRASEDocBuilder
{
    /**
     * The driving/base SegmentPK.
     */
    private Long segmentPK;

    public static final String ROOT_ELEMENT_NAME = "RiderDocVO";

    /**
     * The key name of the building parameter.
     */
    public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";
    
    /**
     * The building parameters extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_SEGMENTPK};
    
    public RiderDocument(){}

    public RiderDocument(Long segmentPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK.toString()));

        this.segmentPK = segmentPK;
    }

    /**
     * Constructor. The specified buildig parameters is expected to 
     * contain the keyed SegmentPK.
     * @see #BUILDING_PARAMETER_NAME_SEGMENTPK 
     * @param buildingParams
     */
    public RiderDocument(Map<String, String> buildingParams)
    {
        super(buildingParams);

        this.segmentPK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_SEGMENTPK));
    }

    /**
     * Constructor. The specified riderDocument is to be
     * the fully constructed Document. The building parameter
     * of SegmentPK is assumed to be "0", otherwise this constructor
     * wouldn't be used since the document could be constructed
     * directly from the DB.
     * @param riderVOElements
     */
    public RiderDocument(List<Element> riderVOElements)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, "0"));
        
        Element rootElement = new DefaultElement(getRootElementName());
        
        for (Element riderVOElement:riderVOElements)
        {
            SegmentDocument segmentDocument = new SegmentDocument(riderVOElement);
          
            rootElement.add(segmentDocument.getRootElement());
        }
        
        setRootElement(rootElement);
        
        setDocumentBuilt(true);
    }

    /**
     * Builds a SegmentDocument for each rider Segment associated with the
     * driving/baseSegmentPK.
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element riderDocumentElement = new DefaultElement(getRootElementName());

            Segment[] riderSegments = Segment.findRidersBy_SegmentPK_V3(getSegmentPK());

            for (Segment riderSegment: riderSegments)
            {
                SegmentDocument segmentDocument = new SegmentDocument(riderSegment);

                segmentDocument.build();

                Element riderElement = segmentDocument.getRootElement();

                riderDocumentElement.add(riderElement);
            }

            setRootElement(riderDocumentElement);

            setDocumentBuilt(true);
        }
    }

    /**
     * The driving/baseSegmentPK of the riders associated with this document.
     * @return
     */
    public Long getSegmentPK()
    {
        return segmentPK;
    }

    /**
     * 
     * @return
     */
    public String getRootElementName()
    {
        return ROOT_ELEMENT_NAME;
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
