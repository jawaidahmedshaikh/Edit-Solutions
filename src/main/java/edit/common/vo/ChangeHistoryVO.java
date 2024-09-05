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
 * Class ChangeHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class ChangeHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _changeHistoryPK
     */
    private long _changeHistoryPK;

    /**
     * keeps track of state for field: _changeHistoryPK
     */
    private boolean _has_changeHistoryPK;

    /**
     * Field _modifiedRecordFK
     */
    private long _modifiedRecordFK;

    /**
     * keeps track of state for field: _modifiedRecordFK
     */
    private boolean _has_modifiedRecordFK;

    /**
     * Field _tableName
     */
    private java.lang.String _tableName;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _processDate
     */
    private java.lang.String _processDate;

    /**
     * Field _fieldName
     */
    private java.lang.String _fieldName;

    /**
     * Field _beforeValue
     */
    private java.lang.String _beforeValue;

    /**
     * Field _afterValue
     */
    private java.lang.String _afterValue;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _nonFinancialTypeCT
     */
    private java.lang.String _nonFinancialTypeCT;

    /**
     * Field _roleCT
     */
    private java.lang.String _roleCT;

    /**
     * Field _pendingStatus
     */
    private java.lang.String _pendingStatus;

    /**
     * Field _parentFK
     */
    private long _parentFK;

    /**
     * keeps track of state for field: _parentFK
     */
    private boolean _has_parentFK;

    /**
     * Field _changeHistoryCorrespondenceVOList
     */
    private java.util.Vector _changeHistoryCorrespondenceVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ChangeHistoryVO() {
        super();
        _changeHistoryCorrespondenceVOList = new Vector();
    } //-- edit.common.vo.ChangeHistoryVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addChangeHistoryCorrespondenceVO
     * 
     * @param vChangeHistoryCorrespondenceVO
     */
    public void addChangeHistoryCorrespondenceVO(edit.common.vo.ChangeHistoryCorrespondenceVO vChangeHistoryCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChangeHistoryCorrespondenceVO.setParentVO(this.getClass(), this);
        _changeHistoryCorrespondenceVOList.addElement(vChangeHistoryCorrespondenceVO);
    } //-- void addChangeHistoryCorrespondenceVO(edit.common.vo.ChangeHistoryCorrespondenceVO) 

    /**
     * Method addChangeHistoryCorrespondenceVO
     * 
     * @param index
     * @param vChangeHistoryCorrespondenceVO
     */
    public void addChangeHistoryCorrespondenceVO(int index, edit.common.vo.ChangeHistoryCorrespondenceVO vChangeHistoryCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vChangeHistoryCorrespondenceVO.setParentVO(this.getClass(), this);
        _changeHistoryCorrespondenceVOList.insertElementAt(vChangeHistoryCorrespondenceVO, index);
    } //-- void addChangeHistoryCorrespondenceVO(int, edit.common.vo.ChangeHistoryCorrespondenceVO) 

    /**
     * Method enumerateChangeHistoryCorrespondenceVO
     */
    public java.util.Enumeration enumerateChangeHistoryCorrespondenceVO()
    {
        return _changeHistoryCorrespondenceVOList.elements();
    } //-- java.util.Enumeration enumerateChangeHistoryCorrespondenceVO() 

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
        
        if (obj instanceof ChangeHistoryVO) {
        
            ChangeHistoryVO temp = (ChangeHistoryVO)obj;
            if (this._changeHistoryPK != temp._changeHistoryPK)
                return false;
            if (this._has_changeHistoryPK != temp._has_changeHistoryPK)
                return false;
            if (this._modifiedRecordFK != temp._modifiedRecordFK)
                return false;
            if (this._has_modifiedRecordFK != temp._has_modifiedRecordFK)
                return false;
            if (this._tableName != null) {
                if (temp._tableName == null) return false;
                else if (!(this._tableName.equals(temp._tableName))) 
                    return false;
            }
            else if (temp._tableName != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._processDate != null) {
                if (temp._processDate == null) return false;
                else if (!(this._processDate.equals(temp._processDate))) 
                    return false;
            }
            else if (temp._processDate != null)
                return false;
            if (this._fieldName != null) {
                if (temp._fieldName == null) return false;
                else if (!(this._fieldName.equals(temp._fieldName))) 
                    return false;
            }
            else if (temp._fieldName != null)
                return false;
            if (this._beforeValue != null) {
                if (temp._beforeValue == null) return false;
                else if (!(this._beforeValue.equals(temp._beforeValue))) 
                    return false;
            }
            else if (temp._beforeValue != null)
                return false;
            if (this._afterValue != null) {
                if (temp._afterValue == null) return false;
                else if (!(this._afterValue.equals(temp._afterValue))) 
                    return false;
            }
            else if (temp._afterValue != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._nonFinancialTypeCT != null) {
                if (temp._nonFinancialTypeCT == null) return false;
                else if (!(this._nonFinancialTypeCT.equals(temp._nonFinancialTypeCT))) 
                    return false;
            }
            else if (temp._nonFinancialTypeCT != null)
                return false;
            if (this._roleCT != null) {
                if (temp._roleCT == null) return false;
                else if (!(this._roleCT.equals(temp._roleCT))) 
                    return false;
            }
            else if (temp._roleCT != null)
                return false;
            if (this._pendingStatus != null) {
                if (temp._pendingStatus == null) return false;
                else if (!(this._pendingStatus.equals(temp._pendingStatus))) 
                    return false;
            }
            else if (temp._pendingStatus != null)
                return false;
            if (this._parentFK != temp._parentFK)
                return false;
            if (this._has_parentFK != temp._has_parentFK)
                return false;
            if (this._changeHistoryCorrespondenceVOList != null) {
                if (temp._changeHistoryCorrespondenceVOList == null) return false;
                else if (!(this._changeHistoryCorrespondenceVOList.equals(temp._changeHistoryCorrespondenceVOList))) 
                    return false;
            }
            else if (temp._changeHistoryCorrespondenceVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAfterValueReturns the value of field 'afterValue'.
     * 
     * @return the value of field 'afterValue'.
     */
    public java.lang.String getAfterValue()
    {
        return this._afterValue;
    } //-- java.lang.String getAfterValue() 

    /**
     * Method getBeforeValueReturns the value of field
     * 'beforeValue'.
     * 
     * @return the value of field 'beforeValue'.
     */
    public java.lang.String getBeforeValue()
    {
        return this._beforeValue;
    } //-- java.lang.String getBeforeValue() 

    /**
     * Method getChangeHistoryCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.ChangeHistoryCorrespondenceVO getChangeHistoryCorrespondenceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _changeHistoryCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ChangeHistoryCorrespondenceVO) _changeHistoryCorrespondenceVOList.elementAt(index);
    } //-- edit.common.vo.ChangeHistoryCorrespondenceVO getChangeHistoryCorrespondenceVO(int) 

    /**
     * Method getChangeHistoryCorrespondenceVO
     */
    public edit.common.vo.ChangeHistoryCorrespondenceVO[] getChangeHistoryCorrespondenceVO()
    {
        int size = _changeHistoryCorrespondenceVOList.size();
        edit.common.vo.ChangeHistoryCorrespondenceVO[] mArray = new edit.common.vo.ChangeHistoryCorrespondenceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ChangeHistoryCorrespondenceVO) _changeHistoryCorrespondenceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ChangeHistoryCorrespondenceVO[] getChangeHistoryCorrespondenceVO() 

    /**
     * Method getChangeHistoryCorrespondenceVOCount
     */
    public int getChangeHistoryCorrespondenceVOCount()
    {
        return _changeHistoryCorrespondenceVOList.size();
    } //-- int getChangeHistoryCorrespondenceVOCount() 

    /**
     * Method getChangeHistoryPKReturns the value of field
     * 'changeHistoryPK'.
     * 
     * @return the value of field 'changeHistoryPK'.
     */
    public long getChangeHistoryPK()
    {
        return this._changeHistoryPK;
    } //-- long getChangeHistoryPK() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getFieldNameReturns the value of field 'fieldName'.
     * 
     * @return the value of field 'fieldName'.
     */
    public java.lang.String getFieldName()
    {
        return this._fieldName;
    } //-- java.lang.String getFieldName() 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getModifiedRecordFKReturns the value of field
     * 'modifiedRecordFK'.
     * 
     * @return the value of field 'modifiedRecordFK'.
     */
    public long getModifiedRecordFK()
    {
        return this._modifiedRecordFK;
    } //-- long getModifiedRecordFK() 

    /**
     * Method getNonFinancialTypeCTReturns the value of field
     * 'nonFinancialTypeCT'.
     * 
     * @return the value of field 'nonFinancialTypeCT'.
     */
    public java.lang.String getNonFinancialTypeCT()
    {
        return this._nonFinancialTypeCT;
    } //-- java.lang.String getNonFinancialTypeCT() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getParentFKReturns the value of field 'parentFK'.
     * 
     * @return the value of field 'parentFK'.
     */
    public long getParentFK()
    {
        return this._parentFK;
    } //-- long getParentFK() 

    /**
     * Method getPendingStatusReturns the value of field
     * 'pendingStatus'.
     * 
     * @return the value of field 'pendingStatus'.
     */
    public java.lang.String getPendingStatus()
    {
        return this._pendingStatus;
    } //-- java.lang.String getPendingStatus() 

    /**
     * Method getProcessDateReturns the value of field
     * 'processDate'.
     * 
     * @return the value of field 'processDate'.
     */
    public java.lang.String getProcessDate()
    {
        return this._processDate;
    } //-- java.lang.String getProcessDate() 

    /**
     * Method getRoleCTReturns the value of field 'roleCT'.
     * 
     * @return the value of field 'roleCT'.
     */
    public java.lang.String getRoleCT()
    {
        return this._roleCT;
    } //-- java.lang.String getRoleCT() 

    /**
     * Method getTableNameReturns the value of field 'tableName'.
     * 
     * @return the value of field 'tableName'.
     */
    public java.lang.String getTableName()
    {
        return this._tableName;
    } //-- java.lang.String getTableName() 

    /**
     * Method hasChangeHistoryPK
     */
    public boolean hasChangeHistoryPK()
    {
        return this._has_changeHistoryPK;
    } //-- boolean hasChangeHistoryPK() 

    /**
     * Method hasModifiedRecordFK
     */
    public boolean hasModifiedRecordFK()
    {
        return this._has_modifiedRecordFK;
    } //-- boolean hasModifiedRecordFK() 

    /**
     * Method hasParentFK
     */
    public boolean hasParentFK()
    {
        return this._has_parentFK;
    } //-- boolean hasParentFK() 

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
     * Method removeAllChangeHistoryCorrespondenceVO
     */
    public void removeAllChangeHistoryCorrespondenceVO()
    {
        _changeHistoryCorrespondenceVOList.removeAllElements();
    } //-- void removeAllChangeHistoryCorrespondenceVO() 

    /**
     * Method removeChangeHistoryCorrespondenceVO
     * 
     * @param index
     */
    public edit.common.vo.ChangeHistoryCorrespondenceVO removeChangeHistoryCorrespondenceVO(int index)
    {
        java.lang.Object obj = _changeHistoryCorrespondenceVOList.elementAt(index);
        _changeHistoryCorrespondenceVOList.removeElementAt(index);
        return (edit.common.vo.ChangeHistoryCorrespondenceVO) obj;
    } //-- edit.common.vo.ChangeHistoryCorrespondenceVO removeChangeHistoryCorrespondenceVO(int) 

    /**
     * Method setAfterValueSets the value of field 'afterValue'.
     * 
     * @param afterValue the value of field 'afterValue'.
     */
    public void setAfterValue(java.lang.String afterValue)
    {
        this._afterValue = afterValue;
        
        super.setVoChanged(true);
    } //-- void setAfterValue(java.lang.String) 

    /**
     * Method setBeforeValueSets the value of field 'beforeValue'.
     * 
     * @param beforeValue the value of field 'beforeValue'.
     */
    public void setBeforeValue(java.lang.String beforeValue)
    {
        this._beforeValue = beforeValue;
        
        super.setVoChanged(true);
    } //-- void setBeforeValue(java.lang.String) 

    /**
     * Method setChangeHistoryCorrespondenceVO
     * 
     * @param index
     * @param vChangeHistoryCorrespondenceVO
     */
    public void setChangeHistoryCorrespondenceVO(int index, edit.common.vo.ChangeHistoryCorrespondenceVO vChangeHistoryCorrespondenceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _changeHistoryCorrespondenceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vChangeHistoryCorrespondenceVO.setParentVO(this.getClass(), this);
        _changeHistoryCorrespondenceVOList.setElementAt(vChangeHistoryCorrespondenceVO, index);
    } //-- void setChangeHistoryCorrespondenceVO(int, edit.common.vo.ChangeHistoryCorrespondenceVO) 

    /**
     * Method setChangeHistoryCorrespondenceVO
     * 
     * @param changeHistoryCorrespondenceVOArray
     */
    public void setChangeHistoryCorrespondenceVO(edit.common.vo.ChangeHistoryCorrespondenceVO[] changeHistoryCorrespondenceVOArray)
    {
        //-- copy array
        _changeHistoryCorrespondenceVOList.removeAllElements();
        for (int i = 0; i < changeHistoryCorrespondenceVOArray.length; i++) {
            changeHistoryCorrespondenceVOArray[i].setParentVO(this.getClass(), this);
            _changeHistoryCorrespondenceVOList.addElement(changeHistoryCorrespondenceVOArray[i]);
        }
    } //-- void setChangeHistoryCorrespondenceVO(edit.common.vo.ChangeHistoryCorrespondenceVO) 

    /**
     * Method setChangeHistoryPKSets the value of field
     * 'changeHistoryPK'.
     * 
     * @param changeHistoryPK the value of field 'changeHistoryPK'.
     */
    public void setChangeHistoryPK(long changeHistoryPK)
    {
        this._changeHistoryPK = changeHistoryPK;
        
        super.setVoChanged(true);
        this._has_changeHistoryPK = true;
    } //-- void setChangeHistoryPK(long) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setFieldNameSets the value of field 'fieldName'.
     * 
     * @param fieldName the value of field 'fieldName'.
     */
    public void setFieldName(java.lang.String fieldName)
    {
        this._fieldName = fieldName;
        
        super.setVoChanged(true);
    } //-- void setFieldName(java.lang.String) 

    /**
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setModifiedRecordFKSets the value of field
     * 'modifiedRecordFK'.
     * 
     * @param modifiedRecordFK the value of field 'modifiedRecordFK'
     */
    public void setModifiedRecordFK(long modifiedRecordFK)
    {
        this._modifiedRecordFK = modifiedRecordFK;
        
        super.setVoChanged(true);
        this._has_modifiedRecordFK = true;
    } //-- void setModifiedRecordFK(long) 

    /**
     * Method setNonFinancialTypeCTSets the value of field
     * 'nonFinancialTypeCT'.
     * 
     * @param nonFinancialTypeCT the value of field
     * 'nonFinancialTypeCT'.
     */
    public void setNonFinancialTypeCT(java.lang.String nonFinancialTypeCT)
    {
        this._nonFinancialTypeCT = nonFinancialTypeCT;
        
        super.setVoChanged(true);
    } //-- void setNonFinancialTypeCT(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setParentFKSets the value of field 'parentFK'.
     * 
     * @param parentFK the value of field 'parentFK'.
     */
    public void setParentFK(long parentFK)
    {
        this._parentFK = parentFK;
        
        super.setVoChanged(true);
        this._has_parentFK = true;
    } //-- void setParentFK(long) 

    /**
     * Method setPendingStatusSets the value of field
     * 'pendingStatus'.
     * 
     * @param pendingStatus the value of field 'pendingStatus'.
     */
    public void setPendingStatus(java.lang.String pendingStatus)
    {
        this._pendingStatus = pendingStatus;
        
        super.setVoChanged(true);
    } //-- void setPendingStatus(java.lang.String) 

    /**
     * Method setProcessDateSets the value of field 'processDate'.
     * 
     * @param processDate the value of field 'processDate'.
     */
    public void setProcessDate(java.lang.String processDate)
    {
        this._processDate = processDate;
        
        super.setVoChanged(true);
    } //-- void setProcessDate(java.lang.String) 

    /**
     * Method setRoleCTSets the value of field 'roleCT'.
     * 
     * @param roleCT the value of field 'roleCT'.
     */
    public void setRoleCT(java.lang.String roleCT)
    {
        this._roleCT = roleCT;
        
        super.setVoChanged(true);
    } //-- void setRoleCT(java.lang.String) 

    /**
     * Method setTableNameSets the value of field 'tableName'.
     * 
     * @param tableName the value of field 'tableName'.
     */
    public void setTableName(java.lang.String tableName)
    {
        this._tableName = tableName;
        
        super.setVoChanged(true);
    } //-- void setTableName(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ChangeHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ChangeHistoryVO) Unmarshaller.unmarshal(edit.common.vo.ChangeHistoryVO.class, reader);
    } //-- edit.common.vo.ChangeHistoryVO unmarshal(java.io.Reader) 

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
