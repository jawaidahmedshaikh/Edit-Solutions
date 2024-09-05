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
 * Class RulesVO.
 * 
 * @version $Revision$ $Date$
 */
public class RulesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _rulesPK
     */
    private long _rulesPK;

    /**
     * keeps track of state for field: _rulesPK
     */
    private boolean _has_rulesPK;

    /**
     * Field _scriptFK
     */
    private long _scriptFK;

    /**
     * keeps track of state for field: _scriptFK
     */
    private boolean _has_scriptFK;

    /**
     * Field _tableDefFK
     */
    private long _tableDefFK;

    /**
     * keeps track of state for field: _tableDefFK
     */
    private boolean _has_tableDefFK;

    /**
     * Field _ruleName
     */
    private java.lang.String _ruleName;

    /**
     * Field _processName
     */
    private java.lang.String _processName;

    /**
     * Field _eventName
     */
    private java.lang.String _eventName;

    /**
     * Field _eventTypeName
     */
    private java.lang.String _eventTypeName;

    /**
     * Field _subRuleName
     */
    private java.lang.String _subRuleName;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _description
     */
    private java.lang.String _description;

    /**
     * Field _productRuleStructureVOList
     */
    private java.util.Vector _productRuleStructureVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public RulesVO() {
        super();
        _productRuleStructureVOList = new Vector();
    } //-- edit.common.vo.RulesVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addProductRuleStructureVO
     * 
     * @param vProductRuleStructureVO
     */
    public void addProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO vProductRuleStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductRuleStructureVO.setParentVO(this.getClass(), this);
        _productRuleStructureVOList.addElement(vProductRuleStructureVO);
    } //-- void addProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method addProductRuleStructureVO
     * 
     * @param index
     * @param vProductRuleStructureVO
     */
    public void addProductRuleStructureVO(int index, edit.common.vo.ProductRuleStructureVO vProductRuleStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductRuleStructureVO.setParentVO(this.getClass(), this);
        _productRuleStructureVOList.insertElementAt(vProductRuleStructureVO, index);
    } //-- void addProductRuleStructureVO(int, edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method enumerateProductRuleStructureVO
     */
    public java.util.Enumeration enumerateProductRuleStructureVO()
    {
        return _productRuleStructureVOList.elements();
    } //-- java.util.Enumeration enumerateProductRuleStructureVO() 

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
        
        if (obj instanceof RulesVO) {
        
            RulesVO temp = (RulesVO)obj;
            if (this._rulesPK != temp._rulesPK)
                return false;
            if (this._has_rulesPK != temp._has_rulesPK)
                return false;
            if (this._scriptFK != temp._scriptFK)
                return false;
            if (this._has_scriptFK != temp._has_scriptFK)
                return false;
            if (this._tableDefFK != temp._tableDefFK)
                return false;
            if (this._has_tableDefFK != temp._has_tableDefFK)
                return false;
            if (this._ruleName != null) {
                if (temp._ruleName == null) return false;
                else if (!(this._ruleName.equals(temp._ruleName))) 
                    return false;
            }
            else if (temp._ruleName != null)
                return false;
            if (this._processName != null) {
                if (temp._processName == null) return false;
                else if (!(this._processName.equals(temp._processName))) 
                    return false;
            }
            else if (temp._processName != null)
                return false;
            if (this._eventName != null) {
                if (temp._eventName == null) return false;
                else if (!(this._eventName.equals(temp._eventName))) 
                    return false;
            }
            else if (temp._eventName != null)
                return false;
            if (this._eventTypeName != null) {
                if (temp._eventTypeName == null) return false;
                else if (!(this._eventTypeName.equals(temp._eventTypeName))) 
                    return false;
            }
            else if (temp._eventTypeName != null)
                return false;
            if (this._subRuleName != null) {
                if (temp._subRuleName == null) return false;
                else if (!(this._subRuleName.equals(temp._subRuleName))) 
                    return false;
            }
            else if (temp._subRuleName != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._description != null) {
                if (temp._description == null) return false;
                else if (!(this._description.equals(temp._description))) 
                    return false;
            }
            else if (temp._description != null)
                return false;
            if (this._productRuleStructureVOList != null) {
                if (temp._productRuleStructureVOList == null) return false;
                else if (!(this._productRuleStructureVOList.equals(temp._productRuleStructureVOList))) 
                    return false;
            }
            else if (temp._productRuleStructureVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getDescriptionReturns the value of field
     * 'description'.
     * 
     * @return the value of field 'description'.
     */
    public java.lang.String getDescription()
    {
        return this._description;
    } //-- java.lang.String getDescription() 

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
     * Method getEventNameReturns the value of field 'eventName'.
     * 
     * @return the value of field 'eventName'.
     */
    public java.lang.String getEventName()
    {
        return this._eventName;
    } //-- java.lang.String getEventName() 

    /**
     * Method getEventTypeNameReturns the value of field
     * 'eventTypeName'.
     * 
     * @return the value of field 'eventTypeName'.
     */
    public java.lang.String getEventTypeName()
    {
        return this._eventTypeName;
    } //-- java.lang.String getEventTypeName() 

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
     * Method getProcessNameReturns the value of field
     * 'processName'.
     * 
     * @return the value of field 'processName'.
     */
    public java.lang.String getProcessName()
    {
        return this._processName;
    } //-- java.lang.String getProcessName() 

    /**
     * Method getProductRuleStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductRuleStructureVO getProductRuleStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productRuleStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProductRuleStructureVO) _productRuleStructureVOList.elementAt(index);
    } //-- edit.common.vo.ProductRuleStructureVO getProductRuleStructureVO(int) 

    /**
     * Method getProductRuleStructureVO
     */
    public edit.common.vo.ProductRuleStructureVO[] getProductRuleStructureVO()
    {
        int size = _productRuleStructureVOList.size();
        edit.common.vo.ProductRuleStructureVO[] mArray = new edit.common.vo.ProductRuleStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProductRuleStructureVO) _productRuleStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProductRuleStructureVO[] getProductRuleStructureVO() 

    /**
     * Method getProductRuleStructureVOCount
     */
    public int getProductRuleStructureVOCount()
    {
        return _productRuleStructureVOList.size();
    } //-- int getProductRuleStructureVOCount() 

    /**
     * Method getRuleNameReturns the value of field 'ruleName'.
     * 
     * @return the value of field 'ruleName'.
     */
    public java.lang.String getRuleName()
    {
        return this._ruleName;
    } //-- java.lang.String getRuleName() 

    /**
     * Method getRulesPKReturns the value of field 'rulesPK'.
     * 
     * @return the value of field 'rulesPK'.
     */
    public long getRulesPK()
    {
        return this._rulesPK;
    } //-- long getRulesPK() 

    /**
     * Method getScriptFKReturns the value of field 'scriptFK'.
     * 
     * @return the value of field 'scriptFK'.
     */
    public long getScriptFK()
    {
        return this._scriptFK;
    } //-- long getScriptFK() 

    /**
     * Method getSubRuleNameReturns the value of field
     * 'subRuleName'.
     * 
     * @return the value of field 'subRuleName'.
     */
    public java.lang.String getSubRuleName()
    {
        return this._subRuleName;
    } //-- java.lang.String getSubRuleName() 

    /**
     * Method getTableDefFKReturns the value of field 'tableDefFK'.
     * 
     * @return the value of field 'tableDefFK'.
     */
    public long getTableDefFK()
    {
        return this._tableDefFK;
    } //-- long getTableDefFK() 

    /**
     * Method hasRulesPK
     */
    public boolean hasRulesPK()
    {
        return this._has_rulesPK;
    } //-- boolean hasRulesPK() 

    /**
     * Method hasScriptFK
     */
    public boolean hasScriptFK()
    {
        return this._has_scriptFK;
    } //-- boolean hasScriptFK() 

    /**
     * Method hasTableDefFK
     */
    public boolean hasTableDefFK()
    {
        return this._has_tableDefFK;
    } //-- boolean hasTableDefFK() 

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
     * Method removeAllProductRuleStructureVO
     */
    public void removeAllProductRuleStructureVO()
    {
        _productRuleStructureVOList.removeAllElements();
    } //-- void removeAllProductRuleStructureVO() 

    /**
     * Method removeProductRuleStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductRuleStructureVO removeProductRuleStructureVO(int index)
    {
        java.lang.Object obj = _productRuleStructureVOList.elementAt(index);
        _productRuleStructureVOList.removeElementAt(index);
        return (edit.common.vo.ProductRuleStructureVO) obj;
    } //-- edit.common.vo.ProductRuleStructureVO removeProductRuleStructureVO(int) 

    /**
     * Method setDescriptionSets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(java.lang.String description)
    {
        this._description = description;
        
        super.setVoChanged(true);
    } //-- void setDescription(java.lang.String) 

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
     * Method setEventNameSets the value of field 'eventName'.
     * 
     * @param eventName the value of field 'eventName'.
     */
    public void setEventName(java.lang.String eventName)
    {
        this._eventName = eventName;
        
        super.setVoChanged(true);
    } //-- void setEventName(java.lang.String) 

    /**
     * Method setEventTypeNameSets the value of field
     * 'eventTypeName'.
     * 
     * @param eventTypeName the value of field 'eventTypeName'.
     */
    public void setEventTypeName(java.lang.String eventTypeName)
    {
        this._eventTypeName = eventTypeName;
        
        super.setVoChanged(true);
    } //-- void setEventTypeName(java.lang.String) 

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
     * Method setProcessNameSets the value of field 'processName'.
     * 
     * @param processName the value of field 'processName'.
     */
    public void setProcessName(java.lang.String processName)
    {
        this._processName = processName;
        
        super.setVoChanged(true);
    } //-- void setProcessName(java.lang.String) 

    /**
     * Method setProductRuleStructureVO
     * 
     * @param index
     * @param vProductRuleStructureVO
     */
    public void setProductRuleStructureVO(int index, edit.common.vo.ProductRuleStructureVO vProductRuleStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productRuleStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProductRuleStructureVO.setParentVO(this.getClass(), this);
        _productRuleStructureVOList.setElementAt(vProductRuleStructureVO, index);
    } //-- void setProductRuleStructureVO(int, edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method setProductRuleStructureVO
     * 
     * @param productRuleStructureVOArray
     */
    public void setProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO[] productRuleStructureVOArray)
    {
        //-- copy array
        _productRuleStructureVOList.removeAllElements();
        for (int i = 0; i < productRuleStructureVOArray.length; i++) {
            productRuleStructureVOArray[i].setParentVO(this.getClass(), this);
            _productRuleStructureVOList.addElement(productRuleStructureVOArray[i]);
        }
    } //-- void setProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method setRuleNameSets the value of field 'ruleName'.
     * 
     * @param ruleName the value of field 'ruleName'.
     */
    public void setRuleName(java.lang.String ruleName)
    {
        this._ruleName = ruleName;
        
        super.setVoChanged(true);
    } //-- void setRuleName(java.lang.String) 

    /**
     * Method setRulesPKSets the value of field 'rulesPK'.
     * 
     * @param rulesPK the value of field 'rulesPK'.
     */
    public void setRulesPK(long rulesPK)
    {
        this._rulesPK = rulesPK;
        
        super.setVoChanged(true);
        this._has_rulesPK = true;
    } //-- void setRulesPK(long) 

    /**
     * Method setScriptFKSets the value of field 'scriptFK'.
     * 
     * @param scriptFK the value of field 'scriptFK'.
     */
    public void setScriptFK(long scriptFK)
    {
        this._scriptFK = scriptFK;
        
        super.setVoChanged(true);
        this._has_scriptFK = true;
    } //-- void setScriptFK(long) 

    /**
     * Method setSubRuleNameSets the value of field 'subRuleName'.
     * 
     * @param subRuleName the value of field 'subRuleName'.
     */
    public void setSubRuleName(java.lang.String subRuleName)
    {
        this._subRuleName = subRuleName;
        
        super.setVoChanged(true);
    } //-- void setSubRuleName(java.lang.String) 

    /**
     * Method setTableDefFKSets the value of field 'tableDefFK'.
     * 
     * @param tableDefFK the value of field 'tableDefFK'.
     */
    public void setTableDefFK(long tableDefFK)
    {
        this._tableDefFK = tableDefFK;
        
        super.setVoChanged(true);
        this._has_tableDefFK = true;
    } //-- void setTableDefFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RulesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RulesVO) Unmarshaller.unmarshal(edit.common.vo.RulesVO.class, reader);
    } //-- edit.common.vo.RulesVO unmarshal(java.io.Reader) 

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
