package edit.common.vo;

import org.exolab.castor.xml.validators.DecimalValidator;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.validators.LongValidator;
import org.exolab.castor.xml.validators.StringValidator;

import util.XmlFieldDescriptorBuilder;

/**
 * Class PolicySummaryVODescriptor.
 * 
 * @version $Revision$ $Date$
 */
public class PolicySummaryVODescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


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

    public PolicySummaryVODescriptor() {
        super();
        xmlName = "PolicySummaryVO";
        
        //-- set grouping compositor
        setCompositorAsSequence();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl  desc           = null;
        org.exolab.castor.xml.XMLFieldHandler              handler        = null;
        org.exolab.castor.xml.FieldValidator               fieldValidator = null;
        //-- initialize attribute descriptors
        
        //-- initialize element descriptors
        
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(PolicySummaryVO.class, "_GUID", "GUID", 20));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(PolicySummaryVO.class, "_policyNumber", "PolicyNumber", 15));
        addFieldDescriptor(XmlFieldDescriptorBuilder.createStringXmlFieldDescriptor(PolicySummaryVO.class, "_effectiveDate", "EffectiveDate", 10));

        //-- _eventCycleConsequencesList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(edit.common.vo.EventCycleConsequencesVO.class, "_eventCycleConsequencesVO", "EventCycleConsequencesVO", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                PolicySummaryVO target = (PolicySummaryVO) object;
                return target.getEventCycleConsequencesVO();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                	PolicySummaryVO target = (PolicySummaryVO) object;
                    target.setEventCycleConsequencesVO((edit.common.vo.EventCycleConsequencesVO) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new edit.common.vo.EventCycleConsequencesVO();
            }
        } );
        desc.setHandler(handler);
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _eventCycleConsequencesList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
       
              
        //-- _eventCycleConsequencesCoverageList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(edit.common.vo.EventCycleConsequencesCoverageVO.class, "_eventCycleConsequencesCoverageList", "EventCycleConsequencesCoverageVO", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue( java.lang.Object object ) 
                throws IllegalStateException
            {
                PolicySummaryVO target = (PolicySummaryVO) object;
                return target.getEventCycleConsequencesCoverageVO();
            }
            public void setValue( java.lang.Object object, java.lang.Object value) 
                throws IllegalStateException, IllegalArgumentException
            {
                try {
                	PolicySummaryVO target = (PolicySummaryVO) object;
                    target.addEventCycleConsequencesCoverageVO((edit.common.vo.EventCycleConsequencesCoverageVO) value);
                }
                catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            public java.lang.Object newInstance( java.lang.Object parent ) {
                return new edit.common.vo.EventCycleConsequencesCoverageVO();
            }
        } );
        desc.setHandler(handler);
        desc.setMultivalued(true);
        addFieldDescriptor(desc);
        
        //-- validation code for: _eventCycleConsequencesCoverageList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        
    } //-- edit.common.vo.PolicySummaryVODescriptor()


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
        return edit.common.vo.PolicySummaryVO.class;
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
