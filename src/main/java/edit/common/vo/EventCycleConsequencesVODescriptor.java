package edit.common.vo;

import org.exolab.castor.xml.validators.DecimalValidator;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.validators.LongValidator;
import org.exolab.castor.xml.validators.StringValidator;

import util.XmlFieldDescriptorBuilder;

/**
 * Class EventCycleConsequencesVODescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class EventCycleConsequencesVODescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


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

    public EventCycleConsequencesVODescriptor() {
        super();
        xmlName = "EventCycleConsequencesVO";
        
        //-- set grouping compositor
        setCompositorAsSequence();
        
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(EventCycleConsequencesVO.class, "_definitionOfLifeInsurance", "DefinitionOfLifeInsurance", 50));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_commissionTarget", "CommissionTarget"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_minimumPremiumTarget", "MinimumPremiumTarget"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_surrenderChargeTarget", "SurrenderChargeTarget"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_sevenPayPremium", "SevenPayPremium"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_guidelineLevelPremium", "GuidelineLevelPremium"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_guidelineSinglePremium", "GuidelineSinglePremium"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_statCRVM", "StatCRVM"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_fITCRVM", "FitCRVM"));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createBigDecimalXmlFieldDescriptor(EventCycleConsequencesVO.class, "_faceAmount", "FaceAmount"));

    } //-- edit.common.vo.EventCycleConsequencesVODescriptor()


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
        return edit.common.vo.EventCycleConsequencesVO.class;
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
