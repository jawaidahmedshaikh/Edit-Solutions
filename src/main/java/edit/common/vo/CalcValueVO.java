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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class CalcValueVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _ruleName
     */
    private java.lang.String _ruleName;

    /**
     * Field _segmentVO
     */
    private edit.common.vo.SegmentVO _segmentVO;

    /**
     * Field _calcType
     */
    private java.lang.String _calcType;

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
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public CalcValueVO() {
        super();
    } //-- edit.common.vo.CalcValueVO()


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
        
        if (obj instanceof CalcValueVO) {
        
            CalcValueVO temp = (CalcValueVO)obj;
            if (this._ruleName != null) {
                if (temp._ruleName == null) return false;
                else if (!(this._ruleName.equals(temp._ruleName))) 
                    return false;
            }
            else if (temp._ruleName != null)
                return false;
            if (this._segmentVO != null) {
                if (temp._segmentVO == null) return false;
                else if (!(this._segmentVO.equals(temp._segmentVO))) 
                    return false;
            }
            else if (temp._segmentVO != null)
                return false;
            if (this._calcType != null) {
                if (temp._calcType == null) return false;
                else if (!(this._calcType.equals(temp._calcType))) 
                    return false;
            }
            else if (temp._calcType != null)
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
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCalcTypeReturns the value of field 'calcType'.
     * 
     * @return the value of field 'calcType'.
     */
    public java.lang.String getCalcType()
    {
        return this._calcType;
    } //-- java.lang.String getCalcType() 

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
     * Method getRuleNameReturns the value of field 'ruleName'.
     * 
     * @return the value of field 'ruleName'.
     */
    public java.lang.String getRuleName()
    {
        return this._ruleName;
    } //-- java.lang.String getRuleName() 

    /**
     * Method getSegmentVOReturns the value of field 'segmentVO'.
     * 
     * @return the value of field 'segmentVO'.
     */
    public edit.common.vo.SegmentVO getSegmentVO()
    {
        return this._segmentVO;
    } //-- edit.common.vo.SegmentVO getSegmentVO() 

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
     * Method setCalcTypeSets the value of field 'calcType'.
     * 
     * @param calcType the value of field 'calcType'.
     */
    public void setCalcType(java.lang.String calcType)
    {
        this._calcType = calcType;
        
        super.setVoChanged(true);
    } //-- void setCalcType(java.lang.String) 

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
     * Method setSegmentVOSets the value of field 'segmentVO'.
     * 
     * @param segmentVO the value of field 'segmentVO'.
     */
    public void setSegmentVO(edit.common.vo.SegmentVO segmentVO)
    {
        this._segmentVO = segmentVO;
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CalcValueVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CalcValueVO) Unmarshaller.unmarshal(edit.common.vo.CalcValueVO.class, reader);
    } //-- edit.common.vo.CalcValueVO unmarshal(java.io.Reader) 

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
