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
 * Class ElementVO.
 * 
 * @version $Revision$ $Date$
 */
public class ElementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _elementPK
     */
    private long _elementPK;

    /**
     * keeps track of state for field: _elementPK
     */
    private boolean _has_elementPK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _sequenceNumber
     */
    private int _sequenceNumber;

    /**
     * keeps track of state for field: _sequenceNumber
     */
    private boolean _has_sequenceNumber;

    /**
     * Field _process
     */
    private java.lang.String _process;

    /**
     * Field _event
     */
    private java.lang.String _event;

    /**
     * Field _eventType
     */
    private java.lang.String _eventType;

    /**
     * Field _elementName
     */
    private java.lang.String _elementName;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _elementCompanyRelationVOList
     */
    private java.util.Vector _elementCompanyRelationVOList;

    /**
     * Field _elementStructureVOList
     */
    private java.util.Vector _elementStructureVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ElementVO() {
        super();
        _elementCompanyRelationVOList = new Vector();
        _elementStructureVOList = new Vector();
    } //-- edit.common.vo.ElementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addElementCompanyRelationVO
     * 
     * @param vElementCompanyRelationVO
     */
    public void addElementCompanyRelationVO(edit.common.vo.ElementCompanyRelationVO vElementCompanyRelationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vElementCompanyRelationVO.setParentVO(this.getClass(), this);
        _elementCompanyRelationVOList.addElement(vElementCompanyRelationVO);
    } //-- void addElementCompanyRelationVO(edit.common.vo.ElementCompanyRelationVO) 

    /**
     * Method addElementCompanyRelationVO
     * 
     * @param index
     * @param vElementCompanyRelationVO
     */
    public void addElementCompanyRelationVO(int index, edit.common.vo.ElementCompanyRelationVO vElementCompanyRelationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vElementCompanyRelationVO.setParentVO(this.getClass(), this);
        _elementCompanyRelationVOList.insertElementAt(vElementCompanyRelationVO, index);
    } //-- void addElementCompanyRelationVO(int, edit.common.vo.ElementCompanyRelationVO) 

    /**
     * Method addElementStructureVO
     * 
     * @param vElementStructureVO
     */
    public void addElementStructureVO(edit.common.vo.ElementStructureVO vElementStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vElementStructureVO.setParentVO(this.getClass(), this);
        _elementStructureVOList.addElement(vElementStructureVO);
    } //-- void addElementStructureVO(edit.common.vo.ElementStructureVO) 

    /**
     * Method addElementStructureVO
     * 
     * @param index
     * @param vElementStructureVO
     */
    public void addElementStructureVO(int index, edit.common.vo.ElementStructureVO vElementStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vElementStructureVO.setParentVO(this.getClass(), this);
        _elementStructureVOList.insertElementAt(vElementStructureVO, index);
    } //-- void addElementStructureVO(int, edit.common.vo.ElementStructureVO) 

    /**
     * Method enumerateElementCompanyRelationVO
     */
    public java.util.Enumeration enumerateElementCompanyRelationVO()
    {
        return _elementCompanyRelationVOList.elements();
    } //-- java.util.Enumeration enumerateElementCompanyRelationVO() 

    /**
     * Method enumerateElementStructureVO
     */
    public java.util.Enumeration enumerateElementStructureVO()
    {
        return _elementStructureVOList.elements();
    } //-- java.util.Enumeration enumerateElementStructureVO() 

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
        
        if (obj instanceof ElementVO) {
        
            ElementVO temp = (ElementVO)obj;
            if (this._elementPK != temp._elementPK)
                return false;
            if (this._has_elementPK != temp._has_elementPK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._sequenceNumber != temp._sequenceNumber)
                return false;
            if (this._has_sequenceNumber != temp._has_sequenceNumber)
                return false;
            if (this._process != null) {
                if (temp._process == null) return false;
                else if (!(this._process.equals(temp._process))) 
                    return false;
            }
            else if (temp._process != null)
                return false;
            if (this._event != null) {
                if (temp._event == null) return false;
                else if (!(this._event.equals(temp._event))) 
                    return false;
            }
            else if (temp._event != null)
                return false;
            if (this._eventType != null) {
                if (temp._eventType == null) return false;
                else if (!(this._eventType.equals(temp._eventType))) 
                    return false;
            }
            else if (temp._eventType != null)
                return false;
            if (this._elementName != null) {
                if (temp._elementName == null) return false;
                else if (!(this._elementName.equals(temp._elementName))) 
                    return false;
            }
            else if (temp._elementName != null)
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
            if (this._elementCompanyRelationVOList != null) {
                if (temp._elementCompanyRelationVOList == null) return false;
                else if (!(this._elementCompanyRelationVOList.equals(temp._elementCompanyRelationVOList))) 
                    return false;
            }
            else if (temp._elementCompanyRelationVOList != null)
                return false;
            if (this._elementStructureVOList != null) {
                if (temp._elementStructureVOList == null) return false;
                else if (!(this._elementStructureVOList.equals(temp._elementStructureVOList))) 
                    return false;
            }
            else if (temp._elementStructureVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getElementCompanyRelationVO
     * 
     * @param index
     */
    public edit.common.vo.ElementCompanyRelationVO getElementCompanyRelationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _elementCompanyRelationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ElementCompanyRelationVO) _elementCompanyRelationVOList.elementAt(index);
    } //-- edit.common.vo.ElementCompanyRelationVO getElementCompanyRelationVO(int) 

    /**
     * Method getElementCompanyRelationVO
     */
    public edit.common.vo.ElementCompanyRelationVO[] getElementCompanyRelationVO()
    {
        int size = _elementCompanyRelationVOList.size();
        edit.common.vo.ElementCompanyRelationVO[] mArray = new edit.common.vo.ElementCompanyRelationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ElementCompanyRelationVO) _elementCompanyRelationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ElementCompanyRelationVO[] getElementCompanyRelationVO() 

    /**
     * Method getElementCompanyRelationVOCount
     */
    public int getElementCompanyRelationVOCount()
    {
        return _elementCompanyRelationVOList.size();
    } //-- int getElementCompanyRelationVOCount() 

    /**
     * Method getElementNameReturns the value of field
     * 'elementName'.
     * 
     * @return the value of field 'elementName'.
     */
    public java.lang.String getElementName()
    {
        return this._elementName;
    } //-- java.lang.String getElementName() 

    /**
     * Method getElementPKReturns the value of field 'elementPK'.
     * 
     * @return the value of field 'elementPK'.
     */
    public long getElementPK()
    {
        return this._elementPK;
    } //-- long getElementPK() 

    /**
     * Method getElementStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ElementStructureVO getElementStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _elementStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ElementStructureVO) _elementStructureVOList.elementAt(index);
    } //-- edit.common.vo.ElementStructureVO getElementStructureVO(int) 

    /**
     * Method getElementStructureVO
     */
    public edit.common.vo.ElementStructureVO[] getElementStructureVO()
    {
        int size = _elementStructureVOList.size();
        edit.common.vo.ElementStructureVO[] mArray = new edit.common.vo.ElementStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ElementStructureVO) _elementStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ElementStructureVO[] getElementStructureVO() 

    /**
     * Method getElementStructureVOCount
     */
    public int getElementStructureVOCount()
    {
        return _elementStructureVOList.size();
    } //-- int getElementStructureVOCount() 

    /**
     * Method getEventReturns the value of field 'event'.
     * 
     * @return the value of field 'event'.
     */
    public java.lang.String getEvent()
    {
        return this._event;
    } //-- java.lang.String getEvent() 

    /**
     * Method getEventTypeReturns the value of field 'eventType'.
     * 
     * @return the value of field 'eventType'.
     */
    public java.lang.String getEventType()
    {
        return this._eventType;
    } //-- java.lang.String getEventType() 

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
     * Method getProcessReturns the value of field 'process'.
     * 
     * @return the value of field 'process'.
     */
    public java.lang.String getProcess()
    {
        return this._process;
    } //-- java.lang.String getProcess() 

    /**
     * Method getSequenceNumberReturns the value of field
     * 'sequenceNumber'.
     * 
     * @return the value of field 'sequenceNumber'.
     */
    public int getSequenceNumber()
    {
        return this._sequenceNumber;
    } //-- int getSequenceNumber() 

    /**
     * Method hasElementPK
     */
    public boolean hasElementPK()
    {
        return this._has_elementPK;
    } //-- boolean hasElementPK() 

    /**
     * Method hasSequenceNumber
     */
    public boolean hasSequenceNumber()
    {
        return this._has_sequenceNumber;
    } //-- boolean hasSequenceNumber() 

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
     * Method removeAllElementCompanyRelationVO
     */
    public void removeAllElementCompanyRelationVO()
    {
        _elementCompanyRelationVOList.removeAllElements();
    } //-- void removeAllElementCompanyRelationVO() 

    /**
     * Method removeAllElementStructureVO
     */
    public void removeAllElementStructureVO()
    {
        _elementStructureVOList.removeAllElements();
    } //-- void removeAllElementStructureVO() 

    /**
     * Method removeElementCompanyRelationVO
     * 
     * @param index
     */
    public edit.common.vo.ElementCompanyRelationVO removeElementCompanyRelationVO(int index)
    {
        java.lang.Object obj = _elementCompanyRelationVOList.elementAt(index);
        _elementCompanyRelationVOList.removeElementAt(index);
        return (edit.common.vo.ElementCompanyRelationVO) obj;
    } //-- edit.common.vo.ElementCompanyRelationVO removeElementCompanyRelationVO(int) 

    /**
     * Method removeElementStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ElementStructureVO removeElementStructureVO(int index)
    {
        java.lang.Object obj = _elementStructureVOList.elementAt(index);
        _elementStructureVOList.removeElementAt(index);
        return (edit.common.vo.ElementStructureVO) obj;
    } //-- edit.common.vo.ElementStructureVO removeElementStructureVO(int) 

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
     * Method setElementCompanyRelationVO
     * 
     * @param index
     * @param vElementCompanyRelationVO
     */
    public void setElementCompanyRelationVO(int index, edit.common.vo.ElementCompanyRelationVO vElementCompanyRelationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _elementCompanyRelationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vElementCompanyRelationVO.setParentVO(this.getClass(), this);
        _elementCompanyRelationVOList.setElementAt(vElementCompanyRelationVO, index);
    } //-- void setElementCompanyRelationVO(int, edit.common.vo.ElementCompanyRelationVO) 

    /**
     * Method setElementCompanyRelationVO
     * 
     * @param elementCompanyRelationVOArray
     */
    public void setElementCompanyRelationVO(edit.common.vo.ElementCompanyRelationVO[] elementCompanyRelationVOArray)
    {
        //-- copy array
        _elementCompanyRelationVOList.removeAllElements();
        for (int i = 0; i < elementCompanyRelationVOArray.length; i++) {
            elementCompanyRelationVOArray[i].setParentVO(this.getClass(), this);
            _elementCompanyRelationVOList.addElement(elementCompanyRelationVOArray[i]);
        }
    } //-- void setElementCompanyRelationVO(edit.common.vo.ElementCompanyRelationVO) 

    /**
     * Method setElementNameSets the value of field 'elementName'.
     * 
     * @param elementName the value of field 'elementName'.
     */
    public void setElementName(java.lang.String elementName)
    {
        this._elementName = elementName;
        
        super.setVoChanged(true);
    } //-- void setElementName(java.lang.String) 

    /**
     * Method setElementPKSets the value of field 'elementPK'.
     * 
     * @param elementPK the value of field 'elementPK'.
     */
    public void setElementPK(long elementPK)
    {
        this._elementPK = elementPK;
        
        super.setVoChanged(true);
        this._has_elementPK = true;
    } //-- void setElementPK(long) 

    /**
     * Method setElementStructureVO
     * 
     * @param index
     * @param vElementStructureVO
     */
    public void setElementStructureVO(int index, edit.common.vo.ElementStructureVO vElementStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _elementStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vElementStructureVO.setParentVO(this.getClass(), this);
        _elementStructureVOList.setElementAt(vElementStructureVO, index);
    } //-- void setElementStructureVO(int, edit.common.vo.ElementStructureVO) 

    /**
     * Method setElementStructureVO
     * 
     * @param elementStructureVOArray
     */
    public void setElementStructureVO(edit.common.vo.ElementStructureVO[] elementStructureVOArray)
    {
        //-- copy array
        _elementStructureVOList.removeAllElements();
        for (int i = 0; i < elementStructureVOArray.length; i++) {
            elementStructureVOArray[i].setParentVO(this.getClass(), this);
            _elementStructureVOList.addElement(elementStructureVOArray[i]);
        }
    } //-- void setElementStructureVO(edit.common.vo.ElementStructureVO) 

    /**
     * Method setEventSets the value of field 'event'.
     * 
     * @param event the value of field 'event'.
     */
    public void setEvent(java.lang.String event)
    {
        this._event = event;
        
        super.setVoChanged(true);
    } //-- void setEvent(java.lang.String) 

    /**
     * Method setEventTypeSets the value of field 'eventType'.
     * 
     * @param eventType the value of field 'eventType'.
     */
    public void setEventType(java.lang.String eventType)
    {
        this._eventType = eventType;
        
        super.setVoChanged(true);
    } //-- void setEventType(java.lang.String) 

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
     * Method setProcessSets the value of field 'process'.
     * 
     * @param process the value of field 'process'.
     */
    public void setProcess(java.lang.String process)
    {
        this._process = process;
        
        super.setVoChanged(true);
    } //-- void setProcess(java.lang.String) 

    /**
     * Method setSequenceNumberSets the value of field
     * 'sequenceNumber'.
     * 
     * @param sequenceNumber the value of field 'sequenceNumber'.
     */
    public void setSequenceNumber(int sequenceNumber)
    {
        this._sequenceNumber = sequenceNumber;
        
        super.setVoChanged(true);
        this._has_sequenceNumber = true;
    } //-- void setSequenceNumber(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ElementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ElementVO) Unmarshaller.unmarshal(edit.common.vo.ElementVO.class, reader);
    } //-- edit.common.vo.ElementVO unmarshal(java.io.Reader) 

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
