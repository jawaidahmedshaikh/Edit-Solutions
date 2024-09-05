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
 * Class SearchResponseContractInfo.
 * 
 * @version $Revision$ $Date$
 */
public class SearchResponseContractInfo extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _clientRoleFK
     */
    private long _clientRoleFK;

    /**
     * keeps track of state for field: _clientRoleFK
     */
    private boolean _has_clientRoleFK;

    /**
     * Field _roleType
     */
    private java.lang.String _roleType;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _contractNumber
     */
    private java.lang.String _contractNumber;

    /**
     * Field _segmentStatus
     */
    private java.lang.String _segmentStatus;

    /**
     * Field _optionCode
     */
    private java.lang.String _optionCode;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _businessContractName
     */
    private java.lang.String _businessContractName;

    /**
     * Field _overrideStatus
     */
    private java.lang.String _overrideStatus;


      //----------------/
     //- Constructors -/
    //----------------/

    public SearchResponseContractInfo() {
        super();
    } //-- edit.common.vo.SearchResponseContractInfo()


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
        
        if (obj instanceof SearchResponseContractInfo) {
        
            SearchResponseContractInfo temp = (SearchResponseContractInfo)obj;
            if (this._clientRoleFK != temp._clientRoleFK)
                return false;
            if (this._has_clientRoleFK != temp._has_clientRoleFK)
                return false;
            if (this._roleType != null) {
                if (temp._roleType == null) return false;
                else if (!(this._roleType.equals(temp._roleType))) 
                    return false;
            }
            else if (temp._roleType != null)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._contractNumber != null) {
                if (temp._contractNumber == null) return false;
                else if (!(this._contractNumber.equals(temp._contractNumber))) 
                    return false;
            }
            else if (temp._contractNumber != null)
                return false;
            if (this._segmentStatus != null) {
                if (temp._segmentStatus == null) return false;
                else if (!(this._segmentStatus.equals(temp._segmentStatus))) 
                    return false;
            }
            else if (temp._segmentStatus != null)
                return false;
            if (this._optionCode != null) {
                if (temp._optionCode == null) return false;
                else if (!(this._optionCode.equals(temp._optionCode))) 
                    return false;
            }
            else if (temp._optionCode != null)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._businessContractName != null) {
                if (temp._businessContractName == null) return false;
                else if (!(this._businessContractName.equals(temp._businessContractName))) 
                    return false;
            }
            else if (temp._businessContractName != null)
                return false;
            if (this._overrideStatus != null) {
                if (temp._overrideStatus == null) return false;
                else if (!(this._overrideStatus.equals(temp._overrideStatus))) 
                    return false;
            }
            else if (temp._overrideStatus != null)
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
     * Method getClientRoleFKReturns the value of field
     * 'clientRoleFK'.
     * 
     * @return the value of field 'clientRoleFK'.
     */
    public long getClientRoleFK()
    {
        return this._clientRoleFK;
    } //-- long getClientRoleFK() 

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
     * Method getOptionCodeReturns the value of field 'optionCode'.
     * 
     * @return the value of field 'optionCode'.
     */
    public java.lang.String getOptionCode()
    {
        return this._optionCode;
    } //-- java.lang.String getOptionCode() 

    /**
     * Method getOverrideStatusReturns the value of field
     * 'overrideStatus'.
     * 
     * @return the value of field 'overrideStatus'.
     */
    public java.lang.String getOverrideStatus()
    {
        return this._overrideStatus;
    } //-- java.lang.String getOverrideStatus() 

    /**
     * Method getProductStructureFKReturns the value of field
     * 'productStructureFK'.
     * 
     * @return the value of field 'productStructureFK'.
     */
    public long getProductStructureFK()
    {
        return this._productStructureFK;
    } //-- long getProductStructureFK() 

    /**
     * Method getRoleTypeReturns the value of field 'roleType'.
     * 
     * @return the value of field 'roleType'.
     */
    public java.lang.String getRoleType()
    {
        return this._roleType;
    } //-- java.lang.String getRoleType() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method getSegmentStatusReturns the value of field
     * 'segmentStatus'.
     * 
     * @return the value of field 'segmentStatus'.
     */
    public java.lang.String getSegmentStatus()
    {
        return this._segmentStatus;
    } //-- java.lang.String getSegmentStatus() 

    /**
     * Method hasClientRoleFK
     */
    public boolean hasClientRoleFK()
    {
        return this._has_clientRoleFK;
    } //-- boolean hasClientRoleFK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method setClientRoleFKSets the value of field
     * 'clientRoleFK'.
     * 
     * @param clientRoleFK the value of field 'clientRoleFK'.
     */
    public void setClientRoleFK(long clientRoleFK)
    {
        this._clientRoleFK = clientRoleFK;
        
        super.setVoChanged(true);
        this._has_clientRoleFK = true;
    } //-- void setClientRoleFK(long) 

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
     * Method setOptionCodeSets the value of field 'optionCode'.
     * 
     * @param optionCode the value of field 'optionCode'.
     */
    public void setOptionCode(java.lang.String optionCode)
    {
        this._optionCode = optionCode;
        
        super.setVoChanged(true);
    } //-- void setOptionCode(java.lang.String) 

    /**
     * Method setOverrideStatusSets the value of field
     * 'overrideStatus'.
     * 
     * @param overrideStatus the value of field 'overrideStatus'.
     */
    public void setOverrideStatus(java.lang.String overrideStatus)
    {
        this._overrideStatus = overrideStatus;
        
        super.setVoChanged(true);
    } //-- void setOverrideStatus(java.lang.String) 

    /**
     * Method setProductStructureFKSets the value of field
     * 'productStructureFK'.
     * 
     * @param productStructureFK the value of field
     * 'productStructureFK'.
     */
    public void setProductStructureFK(long productStructureFK)
    {
        this._productStructureFK = productStructureFK;
        
        super.setVoChanged(true);
        this._has_productStructureFK = true;
    } //-- void setProductStructureFK(long) 

    /**
     * Method setRoleTypeSets the value of field 'roleType'.
     * 
     * @param roleType the value of field 'roleType'.
     */
    public void setRoleType(java.lang.String roleType)
    {
        this._roleType = roleType;
        
        super.setVoChanged(true);
    } //-- void setRoleType(java.lang.String) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method setSegmentStatusSets the value of field
     * 'segmentStatus'.
     * 
     * @param segmentStatus the value of field 'segmentStatus'.
     */
    public void setSegmentStatus(java.lang.String segmentStatus)
    {
        this._segmentStatus = segmentStatus;
        
        super.setVoChanged(true);
    } //-- void setSegmentStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SearchResponseContractInfo unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SearchResponseContractInfo) Unmarshaller.unmarshal(edit.common.vo.SearchResponseContractInfo.class, reader);
    } //-- edit.common.vo.SearchResponseContractInfo unmarshal(java.io.Reader) 

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
