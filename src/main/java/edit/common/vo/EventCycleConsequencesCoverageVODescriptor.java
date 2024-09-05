package edit.common.vo;

import org.exolab.castor.xml.validators.DecimalValidator;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.validators.LongValidator;
import org.exolab.castor.xml.validators.StringValidator;

import util.XmlFieldDescriptorBuilder;

/**
 * Class EventCycleConsequencesCoverageVODescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class EventCycleConsequencesCoverageVODescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field nsPrefix
     */
    private java.lang.String nsPrefix;

    /**
     * Field nsURI
     */
    private java.lang.String nsURI;

    /**
     * Field xmlName
     */
    private java.lang.String xmlName;

    /**
     * Field identity
     */
    private org.exolab.castor.xml.XMLFieldDescriptor identity;


      //----------------/
     //- Constructors -/
    //----------------/

    public EventCycleConsequencesCoverageVODescriptor() {
        super();
        xmlName = "EventCycleConsequencesCoverageVO";
        
        //-- set grouping compositor
        setCompositorAsSequence();
        
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_GUID", "GUID", 20));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_sourceCoverageID", "SourceCoverageID", 20));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_productCode", "ProductCode", 20));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_effectiveDate", "EffectiveDate", 10));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_qualifiedBenefit", "QualifiedBenefit", 50));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_commissionTarget", "CommissionTarget"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_minimumPremiumTarget", "MinimumPremiumTarget"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesCoverageVO.class, "_faceAmount", "FaceAmount"));

    } //-- edit.common.vo.EventCycleConsequencesCoverageVODescriptor()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method getAccessMode
     */
    public org.exolab.castor.mapping.AccessMode getAccessMode()
    {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode() 

    /**
     * Method getExtends
     */
    public org.exolab.castor.mapping.ClassDescriptor getExtends()
    {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends() 

    /**
     * Method getIdentity
     */
    public org.exolab.castor.mapping.FieldDescriptor getIdentity()
    {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity() 

    /**
     * Method getJavaClass
     */
    public java.lang.Class getJavaClass()
    {
        return edit.common.vo.EventCycleConsequencesCoverageVO.class;
    } //-- java.lang.Class getJavaClass() 

    /**
     * Method getNameSpacePrefix
     */
    public java.lang.String getNameSpacePrefix()
    {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix() 

    /**
     * Method getNameSpaceURI
     */
    public java.lang.String getNameSpaceURI()
    {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI() 

    /**
     * Method getValidator
     */
    public org.exolab.castor.xml.TypeValidator getValidator()
    {
        return this;
    } //-- org.exolab.castor.xml.TypeValidator getValidator() 

    /**
     * Method getXMLName
     */
    public java.lang.String getXMLName()
    {
        return xmlName;
    } //-- java.lang.String getXMLName() 

}
