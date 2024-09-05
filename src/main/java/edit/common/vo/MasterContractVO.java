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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class MasterContractVO.
 * 
 * @version $Revision$ $Date$
 */
public class MasterContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _masterContractPK
     */
    private long _masterContractPK;

    /**
     * keeps track of state for field: _masterContractPK
     */
    private boolean _has_masterContractPK;

    /**
     * Field _filteredProductFK
     */
    private long _filteredProductFK;

    /**
     * keeps track of state for field: _filteredProductFK
     */
    private boolean _has_filteredProductFK;

    /**
     * Field _masterContractNumber
     */
    private java.lang.String _masterContractNumber;

    /**
     * Field _masterContractName
     */
    private java.lang.String _masterContractName;

    /**
     * Field _masterContractEffectiveDate
     */
    private java.lang.String _masterContractEffectiveDate;

    /**
     * Field _masterContractTerminationDate
     */
    private java.lang.String _masterContractTerminationDate;

    /**
     * Field _creationDate
     */
    private java.lang.String _creationDate;

    /**
     * Field _creationOperator
     */
    private java.lang.String _creationOperator;

    /**
     * Field _stateCT
     */
    private java.lang.String _stateCT;

    /**
     * Field _brandingCompanyCT
     */
    private java.lang.String _brandingCompanyCT;

    /**
     * Field _masterContractVOList
     */
    private java.util.Vector _masterContractVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public MasterContractVO() {
        super();
        _masterContractVOList = new Vector();
    } //-- edit.common.vo.MasterContractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addMasterContractVO
     * 
     * @param vMasterContractVO
     */
    public void addMasterContractVO(edit.common.vo.MasterContractVO vMasterContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vMasterContractVO.setParentVO(this.getClass(), this);
        _masterContractVOList.addElement(vMasterContractVO);
    } //-- void addMasterContractVO(edit.common.vo.MasterContractVO) 

    /**
     * Method addMasterContractVO
     * 
     * @param index
     * @param vMasterContractVO
     */
    public void addMasterContractVO(int index, edit.common.vo.MasterContractVO vMasterContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vMasterContractVO.setParentVO(this.getClass(), this);
        _masterContractVOList.insertElementAt(vMasterContractVO, index);
    } //-- void addMasterContractVO(int, edit.common.vo.MasterContractVO) 

    /**
     * Method enumerateMasterContractVO
     */
    public java.util.Enumeration enumerateMasterContractVO()
    {
        return _masterContractVOList.elements();
    } //-- java.util.Enumeration enumerateMasterContractVO() 

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
        
        if (obj instanceof MasterContractVO) {
        
            MasterContractVO temp = (MasterContractVO)obj;
            if (this._masterContractPK != temp._masterContractPK)
                return false;
            if (this._has_masterContractPK != temp._has_masterContractPK)
                return false;
            if (this._filteredProductFK != temp._filteredProductFK)
                return false;
            if (this._has_filteredProductFK != temp._has_filteredProductFK)
                return false;
            if (this._masterContractNumber != null) {
                if (temp._masterContractNumber == null) return false;
                else if (!(this._masterContractNumber.equals(temp._masterContractNumber))) 
                    return false;
            }
            else if (temp._masterContractNumber != null)
                return false;
            if (this._masterContractName != null) {
                if (temp._masterContractName == null) return false;
                else if (!(this._masterContractName.equals(temp._masterContractName))) 
                    return false;
            }
            else if (temp._masterContractName != null)
                return false;
            if (this._masterContractEffectiveDate != null) {
                if (temp._masterContractEffectiveDate == null) return false;
                else if (!(this._masterContractEffectiveDate.equals(temp._masterContractEffectiveDate))) 
                    return false;
            }
            else if (temp._masterContractEffectiveDate != null)
                return false;
            if (this._masterContractTerminationDate != null) {
                if (temp._masterContractTerminationDate == null) return false;
                else if (!(this._masterContractTerminationDate.equals(temp._masterContractTerminationDate))) 
                    return false;
            }
            else if (temp._masterContractTerminationDate != null)
                return false;
            if (this._creationDate != null) {
                if (temp._creationDate == null) return false;
                else if (!(this._creationDate.equals(temp._creationDate))) 
                    return false;
            }
            else if (temp._creationDate != null)
                return false;
            if (this._creationOperator != null) {
                if (temp._creationOperator == null) return false;
                else if (!(this._creationOperator.equals(temp._creationOperator))) 
                    return false;
            }
            else if (temp._creationOperator != null)
                return false;
            if (this._stateCT != null) {
                if (temp._stateCT == null) return false;
                else if (!(this._stateCT.equals(temp._stateCT))) 
                    return false;
            }
            else if (temp._stateCT != null)
                return false;
            if (this._brandingCompanyCT != null) {
                if (temp._brandingCompanyCT == null) return false;
                else if (!(this._brandingCompanyCT.equals(temp._brandingCompanyCT))) 
                    return false;
            }
            else if (temp._brandingCompanyCT != null)
                return false;
            if (this._masterContractVOList != null) {
                if (temp._masterContractVOList == null) return false;
                else if (!(this._masterContractVOList.equals(temp._masterContractVOList))) 
                    return false;
            }
            else if (temp._masterContractVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCreationDateReturns the value of field
     * 'creationDate'.
     * 
     * @return the value of field 'creationDate'.
     */
    public java.lang.String getCreationDate()
    {
        return this._creationDate;
    } //-- java.lang.String getCreationDate() 

    /**
     * Method getCreationOperatorReturns the value of field
     * 'creationOperator'.
     * 
     * @return the value of field 'creationOperator'.
     */
    public java.lang.String getCreationOperator()
    {
        return this._creationOperator;
    } //-- java.lang.String getCreationOperator() 

    /**
     * Method getFilteredProductFKReturns the value of field
     * 'filteredProductFK'.
     * 
     * @return the value of field 'filteredProductFK'.
     */
    public long getFilteredProductFK()
    {
        return this._filteredProductFK;
    } //-- long getFilteredProductFK() 

    /**
     * Method getMasterContractEffectiveDateReturns the value of
     * field 'masterContractEffectiveDate'.
     * 
     * @return the value of field 'masterContractEffectiveDate'.
     */
    public java.lang.String getMasterContractEffectiveDate()
    {
        return this._masterContractEffectiveDate;
    } //-- java.lang.String getMasterContractEffectiveDate() 

    /**
     * Method getMasterContractNameReturns the value of field
     * 'masterContractName'.
     * 
     * @return the value of field 'masterContractName'.
     */
    public java.lang.String getMasterContractName()
    {
        return this._masterContractName;
    } //-- java.lang.String getMasterContractName() 

    /**
     * Method getMasterContractNumberReturns the value of field
     * 'masterContractNumber'.
     * 
     * @return the value of field 'masterContractNumber'.
     */
    public java.lang.String getMasterContractNumber()
    {
        return this._masterContractNumber;
    } //-- java.lang.String getMasterContractNumber() 

    /**
     * Method getMasterContractPKReturns the value of field
     * 'masterContractPK'.
     * 
     * @return the value of field 'masterContractPK'.
     */
    public long getMasterContractPK()
    {
        return this._masterContractPK;
    } //-- long getMasterContractPK() 

    /**
     * Method getMasterContractTerminationDateReturns the value of
     * field 'masterContractTerminationDate'.
     * 
     * @return the value of field 'masterContractTerminationDate'.
     */
    public java.lang.String getMasterContractTerminationDate()
    {
        return this._masterContractTerminationDate;
    } //-- java.lang.String getMasterContractTerminationDate() 

    /**
     * Method getMasterContractVO
     * 
     * @param index
     */
    public edit.common.vo.MasterContractVO getMasterContractVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _masterContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.MasterContractVO) _masterContractVOList.elementAt(index);
    } //-- edit.common.vo.MasterContractVO getMasterContractVO(int) 

    /**
     * Method getMasterContractVO
     */
    public edit.common.vo.MasterContractVO[] getMasterContractVO()
    {
        int size = _masterContractVOList.size();
        edit.common.vo.MasterContractVO[] mArray = new edit.common.vo.MasterContractVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.MasterContractVO) _masterContractVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.MasterContractVO[] getMasterContractVO() 

    /**
     * Method getMasterContractVOCount
     */
    public int getMasterContractVOCount()
    {
        return _masterContractVOList.size();
    } //-- int getMasterContractVOCount() 

    /**
     * Method getStateCTReturns the value of field 'stateCT'.
     * 
     * @return the value of field 'stateCT'.
     */
    public java.lang.String getStateCT()
    {
        return this._stateCT;
    } //-- java.lang.String getStateCT() 

    public java.lang.String getBrandingCompanyCT()
    {
        return this._brandingCompanyCT;
    } 

    /**
     * Method hasFilteredProductFK
     */
    public boolean hasFilteredProductFK()
    {
        return this._has_filteredProductFK;
    } //-- boolean hasFilteredProductFK() 

    /**
     * Method hasMasterContractPK
     */
    public boolean hasMasterContractPK()
    {
        return this._has_masterContractPK;
    } //-- boolean hasMasterContractPK() 

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
     * Method removeAllMasterContractVO
     */
    public void removeAllMasterContractVO()
    {
        _masterContractVOList.removeAllElements();
    } //-- void removeAllMasterContractVO() 

    /**
     * Method removeMasterContractVO
     * 
     * @param index
     */
    public edit.common.vo.MasterContractVO removeMasterContractVO(int index)
    {
        java.lang.Object obj = _masterContractVOList.elementAt(index);
        _masterContractVOList.removeElementAt(index);
        return (edit.common.vo.MasterContractVO) obj;
    } //-- edit.common.vo.MasterContractVO removeMasterContractVO(int) 

    /**
     * Method setCreationDateSets the value of field
     * 'creationDate'.
     * 
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(java.lang.String creationDate)
    {
        this._creationDate = creationDate;
        
        super.setVoChanged(true);
    } //-- void setCreationDate(java.lang.String) 

    /**
     * Method setCreationOperatorSets the value of field
     * 'creationOperator'.
     * 
     * @param creationOperator the value of field 'creationOperator'
     */
    public void setCreationOperator(java.lang.String creationOperator)
    {
        this._creationOperator = creationOperator;
        
        super.setVoChanged(true);
    } //-- void setCreationOperator(java.lang.String) 

    /**
     * Method setFilteredProductFKSets the value of field
     * 'filteredProductFK'.
     * 
     * @param filteredProductFK the value of field
     * 'filteredProductFK'.
     */
    public void setFilteredProductFK(long filteredProductFK)
    {
        this._filteredProductFK = filteredProductFK;
        
        super.setVoChanged(true);
        this._has_filteredProductFK = true;
    } //-- void setFilteredProductFK(long) 

    /**
     * Method setMasterContractEffectiveDateSets the value of field
     * 'masterContractEffectiveDate'.
     * 
     * @param masterContractEffectiveDate the value of field
     * 'masterContractEffectiveDate'.
     */
    public void setMasterContractEffectiveDate(java.lang.String masterContractEffectiveDate)
    {
        this._masterContractEffectiveDate = masterContractEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setMasterContractEffectiveDate(java.lang.String) 

    /**
     * Method setMasterContractNameSets the value of field
     * 'masterContractName'.
     * 
     * @param masterContractName the value of field
     * 'masterContractName'.
     */
    public void setMasterContractName(java.lang.String masterContractName)
    {
        this._masterContractName = masterContractName;
        
        super.setVoChanged(true);
    } //-- void setMasterContractName(java.lang.String) 

    /**
     * Method setMasterContractNumberSets the value of field
     * 'masterContractNumber'.
     * 
     * @param masterContractNumber the value of field
     * 'masterContractNumber'.
     */
    public void setMasterContractNumber(java.lang.String masterContractNumber)
    {
        this._masterContractNumber = masterContractNumber;
        
        super.setVoChanged(true);
    } //-- void setMasterContractNumber(java.lang.String) 

    /**
     * Method setMasterContractPKSets the value of field
     * 'masterContractPK'.
     * 
     * @param masterContractPK the value of field 'masterContractPK'
     */
    public void setMasterContractPK(long masterContractPK)
    {
        this._masterContractPK = masterContractPK;
        
        super.setVoChanged(true);
        this._has_masterContractPK = true;
    } //-- void setMasterContractPK(long) 

    /**
     * Method setMasterContractTerminationDateSets the value of
     * field 'masterContractTerminationDate'.
     * 
     * @param masterContractTerminationDate the value of field
     * 'masterContractTerminationDate'.
     */
    public void setMasterContractTerminationDate(java.lang.String masterContractTerminationDate)
    {
        this._masterContractTerminationDate = masterContractTerminationDate;
        
        super.setVoChanged(true);
    } //-- void setMasterContractTerminationDate(java.lang.String) 

    /**
     * Method setMasterContractVO
     * 
     * @param index
     * @param vMasterContractVO
     */
    public void setMasterContractVO(int index, edit.common.vo.MasterContractVO vMasterContractVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _masterContractVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vMasterContractVO.setParentVO(this.getClass(), this);
        _masterContractVOList.setElementAt(vMasterContractVO, index);
    } //-- void setMasterContractVO(int, edit.common.vo.MasterContractVO) 

    /**
     * Method setMasterContractVO
     * 
     * @param masterContractVOArray
     */
    public void setMasterContractVO(edit.common.vo.MasterContractVO[] masterContractVOArray)
    {
        //-- copy array
        _masterContractVOList.removeAllElements();
        for (int i = 0; i < masterContractVOArray.length; i++) {
            masterContractVOArray[i].setParentVO(this.getClass(), this);
            _masterContractVOList.addElement(masterContractVOArray[i]);
        }
    } //-- void setMasterContractVO(edit.common.vo.MasterContractVO) 

    /**
     * Method setStateCTSets the value of field 'stateCT'.
     * 
     * @param stateCT the value of field 'stateCT'.
     */
    public void setStateCT(java.lang.String stateCT)
    {
        this._stateCT = stateCT;
        
        super.setVoChanged(true);
    } //-- void setStateCT(java.lang.String) 

    public void setBrandingCompanyCT(java.lang.String brandingCompanyCT)
    {
        this._brandingCompanyCT = brandingCompanyCT;
        
        super.setVoChanged(true);
    } 
    
    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.MasterContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.MasterContractVO) Unmarshaller.unmarshal(edit.common.vo.MasterContractVO.class, reader);
    } //-- edit.common.vo.MasterContractVO unmarshal(java.io.Reader) 

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
