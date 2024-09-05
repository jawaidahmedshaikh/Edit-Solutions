/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class UISearchContractVO.
 * 
 * @version $Revision$ $Date$
 */
public class UISearchContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _relationship
     */
    private java.lang.String _relationship;

    /**
     * Field _option
     */
    private java.lang.String _option;

    /**
     * Field _businessContractName
     */
    private java.lang.String _businessContractName;

    /**
     * Field _segmentPK
     */
    private long _segmentPK;

    /**
     * keeps track of state for field: _segmentPK
     */
    private boolean _has_segmentPK;


      //----------------/
     //- Constructors -/
    //----------------/

    public UISearchContractVO() {
        super();
    } //-- edit.common.vo.UISearchContractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Note: hashCode() has not been overriden
     * 
     * @param obj
     */
    public boolean equals(java.lang.Object obj)
    {
        if ( this == obj )
            return true;
        
        if (super.equals(obj)==false)
            return false;
        
        if (obj instanceof UISearchContractVO) {
        
            UISearchContractVO temp = (UISearchContractVO)obj;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._relationship != null) {
                if (temp._relationship == null) return false;
                else if (!(this._relationship.equals(temp._relationship))) 
                    return false;
            }
            else if (temp._relationship != null)
                return false;
            if (this._option != null) {
                if (temp._option == null) return false;
                else if (!(this._option.equals(temp._option))) 
                    return false;
            }
            else if (temp._option != null)
                return false;
            if (this._businessContractName != null) {
                if (temp._businessContractName == null) return false;
                else if (!(this._businessContractName.equals(temp._businessContractName))) 
                    return false;
            }
            else if (temp._businessContractName != null)
                return false;
            if (this._segmentPK != temp._segmentPK)
                return false;
            if (this._has_segmentPK != temp._has_segmentPK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBusinessContractNameReturns the value of field
     * 'businessContractName'.
     * 
     * @return the value of field 'businessContractName'.
     */
    public java.lang.String getBusinessContractName()
    {
        return this._businessContractName;
    } //-- java.lang.String getBusinessContractName() 

    /**
     * Method getContractNumberReturns the value of field
     * 'contractNumber'.
     * 
     * @return the value of field 'contractNumber'.
     */
    public java.lang.String getContractNumber()
    {
        return this._contractNumber;
    } //-- java.lang.String getContractNumber() 

    /**
     * Method getOptionReturns the value of field 'option'.
     * 
     * @return the value of field 'option'.
     */
    public java.lang.String getOption()
    {
        return this._option;
    } //-- java.lang.String getOption() 

    /**
     * Method getRelationshipReturns the value of field
     * 'relationship'.
     * 
     * @return the value of field 'relationship'.
     */
    public java.lang.String getRelationship()
    {
        return this._relationship;
    } //-- java.lang.String getRelationship() 

    /**
     * Method getSegmentPKReturns the value of field 'segmentPK'.
     * 
     * @return the value of field 'segmentPK'.
     */
    public long getSegmentPK()
    {
        return this._segmentPK;
    } //-- long getSegmentPK() 

    /**
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method hasSegmentPK
     */
    public boolean hasSegmentPK()
    {
        return this._has_segmentPK;
    } //-- boolean hasSegmentPK() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method setBusinessContractNameSets the value of field
     * 'businessContractName'.
     * 
     * @param businessContractName the value of field
     * 'businessContractName'.
     */
    public void setBusinessContractName(java.lang.String businessContractName)
    {
        this._businessContractName = businessContractName;
        
        super.setVoChanged(true);
    } //-- void setBusinessContractName(java.lang.String) 

    /**
     * Method setContractNumberSets the value of field
     * 'contractNumber'.
     * 
     * @param contractNumber the value of field 'contractNumber'.
     */
    public void setContractNumber(java.lang.String contractNumber)
    {
        this._contractNumber = contractNumber;
        
        super.setVoChanged(true);
    } //-- void setContractNumber(java.lang.String) 

    /**
     * Method setOptionSets the value of field 'option'.
     * 
     * @param option the value of field 'option'.
     */
    public void setOption(java.lang.String option)
    {
        this._option = option;
        
        super.setVoChanged(true);
    } //-- void setOption(java.lang.String) 

    /**
     * Method setRelationshipSets the value of field
     * 'relationship'.
     * 
     * @param relationship the value of field 'relationship'.
     */
    public void setRelationship(java.lang.String relationship)
    {
        this._relationship = relationship;
        
        super.setVoChanged(true);
    } //-- void setRelationship(java.lang.String) 

    /**
     * Method setSegmentPKSets the value of field 'segmentPK'.
     * 
     * @param segmentPK the value of field 'segmentPK'.
     */
    public void setSegmentPK(long segmentPK)
    {
        this._segmentPK = segmentPK;
        
        super.setVoChanged(true);
        this._has_segmentPK = true;
    } //-- void setSegmentPK(long) 

    /**
     * Method setStatusSets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
        
        super.setVoChanged(true);
    } //-- void setStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UISearchContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UISearchContractVO) Unmarshaller.unmarshal(edit.common.vo.UISearchContractVO.class, reader);
    } //-- edit.common.vo.UISearchContractVO unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
