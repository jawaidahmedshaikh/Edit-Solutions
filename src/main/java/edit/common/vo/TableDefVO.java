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
 * Class TableDefVO.
 * 
 * @version $Revision$ $Date$
 */
public class TableDefVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _tableDefPK
     */
    private long _tableDefPK;

    /**
     * keeps track of state for field: _tableDefPK
     */
    private boolean _has_tableDefPK;

    /**
     * Field _tableName
     */
    private java.lang.String _tableName;

    /**
     * Field _accessType
     */
    private java.lang.String _accessType;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _lockDateTime
     */
    private java.lang.String _lockDateTime;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _rulesVOList
     */
    private java.util.Vector _rulesVOList;

    /**
     * Field _tableKeysVOList
     */
    private java.util.Vector _tableKeysVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TableDefVO() {
        super();
        _rulesVOList = new Vector();
        _tableKeysVOList = new Vector();
    } //-- edit.common.vo.TableDefVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addRulesVO
     * 
     * @param vRulesVO
     */
    public void addRulesVO(edit.common.vo.RulesVO vRulesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRulesVO.setParentVO(this.getClass(), this);
        _rulesVOList.addElement(vRulesVO);
    } //-- void addRulesVO(edit.common.vo.RulesVO) 

    /**
     * Method addRulesVO
     * 
     * @param index
     * @param vRulesVO
     */
    public void addRulesVO(int index, edit.common.vo.RulesVO vRulesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRulesVO.setParentVO(this.getClass(), this);
        _rulesVOList.insertElementAt(vRulesVO, index);
    } //-- void addRulesVO(int, edit.common.vo.RulesVO) 

    /**
     * Method addTableKeysVO
     * 
     * @param vTableKeysVO
     */
    public void addTableKeysVO(edit.common.vo.TableKeysVO vTableKeysVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTableKeysVO.setParentVO(this.getClass(), this);
        _tableKeysVOList.addElement(vTableKeysVO);
    } //-- void addTableKeysVO(edit.common.vo.TableKeysVO) 

    /**
     * Method addTableKeysVO
     * 
     * @param index
     * @param vTableKeysVO
     */
    public void addTableKeysVO(int index, edit.common.vo.TableKeysVO vTableKeysVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTableKeysVO.setParentVO(this.getClass(), this);
        _tableKeysVOList.insertElementAt(vTableKeysVO, index);
    } //-- void addTableKeysVO(int, edit.common.vo.TableKeysVO) 

    /**
     * Method enumerateRulesVO
     */
    public java.util.Enumeration enumerateRulesVO()
    {
        return _rulesVOList.elements();
    } //-- java.util.Enumeration enumerateRulesVO() 

    /**
     * Method enumerateTableKeysVO
     */
    public java.util.Enumeration enumerateTableKeysVO()
    {
        return _tableKeysVOList.elements();
    } //-- java.util.Enumeration enumerateTableKeysVO() 

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
        
        if (obj instanceof TableDefVO) {
        
            TableDefVO temp = (TableDefVO)obj;
            if (this._tableDefPK != temp._tableDefPK)
                return false;
            if (this._has_tableDefPK != temp._has_tableDefPK)
                return false;
            if (this._tableName != null) {
                if (temp._tableName == null) return false;
                else if (!(this._tableName.equals(temp._tableName))) 
                    return false;
            }
            else if (temp._tableName != null)
                return false;
            if (this._accessType != null) {
                if (temp._accessType == null) return false;
                else if (!(this._accessType.equals(temp._accessType))) 
                    return false;
            }
            else if (temp._accessType != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._lockDateTime != null) {
                if (temp._lockDateTime == null) return false;
                else if (!(this._lockDateTime.equals(temp._lockDateTime))) 
                    return false;
            }
            else if (temp._lockDateTime != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._rulesVOList != null) {
                if (temp._rulesVOList == null) return false;
                else if (!(this._rulesVOList.equals(temp._rulesVOList))) 
                    return false;
            }
            else if (temp._rulesVOList != null)
                return false;
            if (this._tableKeysVOList != null) {
                if (temp._tableKeysVOList == null) return false;
                else if (!(this._tableKeysVOList.equals(temp._tableKeysVOList))) 
                    return false;
            }
            else if (temp._tableKeysVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccessTypeReturns the value of field 'accessType'.
     * 
     * @return the value of field 'accessType'.
     */
    public java.lang.String getAccessType()
    {
        return this._accessType;
    } //-- java.lang.String getAccessType() 

    /**
     * Method getLockDateTimeReturns the value of field
     * 'lockDateTime'.
     * 
     * @return the value of field 'lockDateTime'.
     */
    public java.lang.String getLockDateTime()
    {
        return this._lockDateTime;
    } //-- java.lang.String getLockDateTime() 

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
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getRulesVO
     * 
     * @param index
     */
    public edit.common.vo.RulesVO getRulesVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rulesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RulesVO) _rulesVOList.elementAt(index);
    } //-- edit.common.vo.RulesVO getRulesVO(int) 

    /**
     * Method getRulesVO
     */
    public edit.common.vo.RulesVO[] getRulesVO()
    {
        int size = _rulesVOList.size();
        edit.common.vo.RulesVO[] mArray = new edit.common.vo.RulesVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RulesVO) _rulesVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RulesVO[] getRulesVO() 

    /**
     * Method getRulesVOCount
     */
    public int getRulesVOCount()
    {
        return _rulesVOList.size();
    } //-- int getRulesVOCount() 

    /**
     * Method getTableDefPKReturns the value of field 'tableDefPK'.
     * 
     * @return the value of field 'tableDefPK'.
     */
    public long getTableDefPK()
    {
        return this._tableDefPK;
    } //-- long getTableDefPK() 

    /**
     * Method getTableKeysVO
     * 
     * @param index
     */
    public edit.common.vo.TableKeysVO getTableKeysVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _tableKeysVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TableKeysVO) _tableKeysVOList.elementAt(index);
    } //-- edit.common.vo.TableKeysVO getTableKeysVO(int) 

    /**
     * Method getTableKeysVO
     */
    public edit.common.vo.TableKeysVO[] getTableKeysVO()
    {
        int size = _tableKeysVOList.size();
        edit.common.vo.TableKeysVO[] mArray = new edit.common.vo.TableKeysVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TableKeysVO) _tableKeysVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TableKeysVO[] getTableKeysVO() 

    /**
     * Method getTableKeysVOCount
     */
    public int getTableKeysVOCount()
    {
        return _tableKeysVOList.size();
    } //-- int getTableKeysVOCount() 

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
     * Method hasTableDefPK
     */
    public boolean hasTableDefPK()
    {
        return this._has_tableDefPK;
    } //-- boolean hasTableDefPK() 

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
     * Method removeAllRulesVO
     */
    public void removeAllRulesVO()
    {
        _rulesVOList.removeAllElements();
    } //-- void removeAllRulesVO() 

    /**
     * Method removeAllTableKeysVO
     */
    public void removeAllTableKeysVO()
    {
        _tableKeysVOList.removeAllElements();
    } //-- void removeAllTableKeysVO() 

    /**
     * Method removeRulesVO
     * 
     * @param index
     */
    public edit.common.vo.RulesVO removeRulesVO(int index)
    {
        java.lang.Object obj = _rulesVOList.elementAt(index);
        _rulesVOList.removeElementAt(index);
        return (edit.common.vo.RulesVO) obj;
    } //-- edit.common.vo.RulesVO removeRulesVO(int) 

    /**
     * Method removeTableKeysVO
     * 
     * @param index
     */
    public edit.common.vo.TableKeysVO removeTableKeysVO(int index)
    {
        java.lang.Object obj = _tableKeysVOList.elementAt(index);
        _tableKeysVOList.removeElementAt(index);
        return (edit.common.vo.TableKeysVO) obj;
    } //-- edit.common.vo.TableKeysVO removeTableKeysVO(int) 

    /**
     * Method setAccessTypeSets the value of field 'accessType'.
     * 
     * @param accessType the value of field 'accessType'.
     */
    public void setAccessType(java.lang.String accessType)
    {
        this._accessType = accessType;
        
        super.setVoChanged(true);
    } //-- void setAccessType(java.lang.String) 

    /**
     * Method setLockDateTimeSets the value of field
     * 'lockDateTime'.
     * 
     * @param lockDateTime the value of field 'lockDateTime'.
     */
    public void setLockDateTime(java.lang.String lockDateTime)
    {
        this._lockDateTime = lockDateTime;
        
        super.setVoChanged(true);
    } //-- void setLockDateTime(java.lang.String) 

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
     * Method setRulesVO
     * 
     * @param index
     * @param vRulesVO
     */
    public void setRulesVO(int index, edit.common.vo.RulesVO vRulesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rulesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRulesVO.setParentVO(this.getClass(), this);
        _rulesVOList.setElementAt(vRulesVO, index);
    } //-- void setRulesVO(int, edit.common.vo.RulesVO) 

    /**
     * Method setRulesVO
     * 
     * @param rulesVOArray
     */
    public void setRulesVO(edit.common.vo.RulesVO[] rulesVOArray)
    {
        //-- copy array
        _rulesVOList.removeAllElements();
        for (int i = 0; i < rulesVOArray.length; i++) {
            rulesVOArray[i].setParentVO(this.getClass(), this);
            _rulesVOList.addElement(rulesVOArray[i]);
        }
    } //-- void setRulesVO(edit.common.vo.RulesVO) 

    /**
     * Method setTableDefPKSets the value of field 'tableDefPK'.
     * 
     * @param tableDefPK the value of field 'tableDefPK'.
     */
    public void setTableDefPK(long tableDefPK)
    {
        this._tableDefPK = tableDefPK;
        
        super.setVoChanged(true);
        this._has_tableDefPK = true;
    } //-- void setTableDefPK(long) 

    /**
     * Method setTableKeysVO
     * 
     * @param index
     * @param vTableKeysVO
     */
    public void setTableKeysVO(int index, edit.common.vo.TableKeysVO vTableKeysVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _tableKeysVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTableKeysVO.setParentVO(this.getClass(), this);
        _tableKeysVOList.setElementAt(vTableKeysVO, index);
    } //-- void setTableKeysVO(int, edit.common.vo.TableKeysVO) 

    /**
     * Method setTableKeysVO
     * 
     * @param tableKeysVOArray
     */
    public void setTableKeysVO(edit.common.vo.TableKeysVO[] tableKeysVOArray)
    {
        //-- copy array
        _tableKeysVOList.removeAllElements();
        for (int i = 0; i < tableKeysVOArray.length; i++) {
            tableKeysVOArray[i].setParentVO(this.getClass(), this);
            _tableKeysVOList.addElement(tableKeysVOArray[i]);
        }
    } //-- void setTableKeysVO(edit.common.vo.TableKeysVO) 

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
    public static edit.common.vo.TableDefVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TableDefVO) Unmarshaller.unmarshal(edit.common.vo.TableDefVO.class, reader);
    } //-- edit.common.vo.TableDefVO unmarshal(java.io.Reader) 

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
