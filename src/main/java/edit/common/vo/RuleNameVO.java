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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class RuleNameVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _processNameList
     */
    private java.util.Vector _processNameList;

    /**
     * Field _eventNameList
     */
    private java.util.Vector _eventNameList;

    /**
     * Field _eventTypeNameList
     */
    private java.util.Vector _eventTypeNameList;

    /**
     * Field _ruleNameList
     */
    private java.util.Vector _ruleNameList;


      //----------------/
     //- Constructors -/
    //----------------/

    public RuleNameVO() {
        super();
        _processNameList = new Vector();
        _eventNameList = new Vector();
        _eventTypeNameList = new Vector();
        _ruleNameList = new Vector();
    } //-- edit.common.vo.RuleNameVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEventName
     * 
     * @param vEventName
     */
    public void addEventName(java.lang.String vEventName)
        throws java.lang.IndexOutOfBoundsException
    {
        _eventNameList.addElement(vEventName);
    } //-- void addEventName(java.lang.String) 

    /**
     * Method addEventName
     * 
     * @param index
     * @param vEventName
     */
    public void addEventName(int index, java.lang.String vEventName)
        throws java.lang.IndexOutOfBoundsException
    {
        _eventNameList.insertElementAt(vEventName, index);
    } //-- void addEventName(int, java.lang.String) 

    /**
     * Method addEventTypeName
     * 
     * @param vEventTypeName
     */
    public void addEventTypeName(java.lang.String vEventTypeName)
        throws java.lang.IndexOutOfBoundsException
    {
        _eventTypeNameList.addElement(vEventTypeName);
    } //-- void addEventTypeName(java.lang.String) 

    /**
     * Method addEventTypeName
     * 
     * @param index
     * @param vEventTypeName
     */
    public void addEventTypeName(int index, java.lang.String vEventTypeName)
        throws java.lang.IndexOutOfBoundsException
    {
        _eventTypeNameList.insertElementAt(vEventTypeName, index);
    } //-- void addEventTypeName(int, java.lang.String) 

    /**
     * Method addProcessName
     * 
     * @param vProcessName
     */
    public void addProcessName(java.lang.String vProcessName)
        throws java.lang.IndexOutOfBoundsException
    {
        _processNameList.addElement(vProcessName);
    } //-- void addProcessName(java.lang.String) 

    /**
     * Method addProcessName
     * 
     * @param index
     * @param vProcessName
     */
    public void addProcessName(int index, java.lang.String vProcessName)
        throws java.lang.IndexOutOfBoundsException
    {
        _processNameList.insertElementAt(vProcessName, index);
    } //-- void addProcessName(int, java.lang.String) 

    /**
     * Method addRuleName
     * 
     * @param vRuleName
     */
    public void addRuleName(java.lang.String vRuleName)
        throws java.lang.IndexOutOfBoundsException
    {
        _ruleNameList.addElement(vRuleName);
    } //-- void addRuleName(java.lang.String) 

    /**
     * Method addRuleName
     * 
     * @param index
     * @param vRuleName
     */
    public void addRuleName(int index, java.lang.String vRuleName)
        throws java.lang.IndexOutOfBoundsException
    {
        _ruleNameList.insertElementAt(vRuleName, index);
    } //-- void addRuleName(int, java.lang.String) 

    /**
     * Method enumerateEventName
     */
    public java.util.Enumeration enumerateEventName()
    {
        return _eventNameList.elements();
    } //-- java.util.Enumeration enumerateEventName() 

    /**
     * Method enumerateEventTypeName
     */
    public java.util.Enumeration enumerateEventTypeName()
    {
        return _eventTypeNameList.elements();
    } //-- java.util.Enumeration enumerateEventTypeName() 

    /**
     * Method enumerateProcessName
     */
    public java.util.Enumeration enumerateProcessName()
    {
        return _processNameList.elements();
    } //-- java.util.Enumeration enumerateProcessName() 

    /**
     * Method enumerateRuleName
     */
    public java.util.Enumeration enumerateRuleName()
    {
        return _ruleNameList.elements();
    } //-- java.util.Enumeration enumerateRuleName() 

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
        
        if (obj instanceof RuleNameVO) {
        
            RuleNameVO temp = (RuleNameVO)obj;
            if (this._processNameList != null) {
                if (temp._processNameList == null) return false;
                else if (!(this._processNameList.equals(temp._processNameList))) 
                    return false;
            }
            else if (temp._processNameList != null)
                return false;
            if (this._eventNameList != null) {
                if (temp._eventNameList == null) return false;
                else if (!(this._eventNameList.equals(temp._eventNameList))) 
                    return false;
            }
            else if (temp._eventNameList != null)
                return false;
            if (this._eventTypeNameList != null) {
                if (temp._eventTypeNameList == null) return false;
                else if (!(this._eventTypeNameList.equals(temp._eventTypeNameList))) 
                    return false;
            }
            else if (temp._eventTypeNameList != null)
                return false;
            if (this._ruleNameList != null) {
                if (temp._ruleNameList == null) return false;
                else if (!(this._ruleNameList.equals(temp._ruleNameList))) 
                    return false;
            }
            else if (temp._ruleNameList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEventName
     * 
     * @param index
     */
    public java.lang.String getEventName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_eventNameList.elementAt(index);
    } //-- java.lang.String getEventName(int) 

    /**
     * Method getEventName
     */
    public java.lang.String[] getEventName()
    {
        int size = _eventNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_eventNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getEventName() 

    /**
     * Method getEventNameCount
     */
    public int getEventNameCount()
    {
        return _eventNameList.size();
    } //-- int getEventNameCount() 

    /**
     * Method getEventTypeName
     * 
     * @param index
     */
    public java.lang.String getEventTypeName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventTypeNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_eventTypeNameList.elementAt(index);
    } //-- java.lang.String getEventTypeName(int) 

    /**
     * Method getEventTypeName
     */
    public java.lang.String[] getEventTypeName()
    {
        int size = _eventTypeNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_eventTypeNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getEventTypeName() 

    /**
     * Method getEventTypeNameCount
     */
    public int getEventTypeNameCount()
    {
        return _eventTypeNameList.size();
    } //-- int getEventTypeNameCount() 

    /**
     * Method getProcessName
     * 
     * @param index
     */
    public java.lang.String getProcessName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _processNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_processNameList.elementAt(index);
    } //-- java.lang.String getProcessName(int) 

    /**
     * Method getProcessName
     */
    public java.lang.String[] getProcessName()
    {
        int size = _processNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_processNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getProcessName() 

    /**
     * Method getProcessNameCount
     */
    public int getProcessNameCount()
    {
        return _processNameList.size();
    } //-- int getProcessNameCount() 

    /**
     * Method getRuleName
     * 
     * @param index
     */
    public java.lang.String getRuleName(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ruleNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_ruleNameList.elementAt(index);
    } //-- java.lang.String getRuleName(int) 

    /**
     * Method getRuleName
     */
    public java.lang.String[] getRuleName()
    {
        int size = _ruleNameList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_ruleNameList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getRuleName() 

    /**
     * Method getRuleNameCount
     */
    public int getRuleNameCount()
    {
        return _ruleNameList.size();
    } //-- int getRuleNameCount() 

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
     * Method removeAllEventName
     */
    public void removeAllEventName()
    {
        _eventNameList.removeAllElements();
    } //-- void removeAllEventName() 

    /**
     * Method removeAllEventTypeName
     */
    public void removeAllEventTypeName()
    {
        _eventTypeNameList.removeAllElements();
    } //-- void removeAllEventTypeName() 

    /**
     * Method removeAllProcessName
     */
    public void removeAllProcessName()
    {
        _processNameList.removeAllElements();
    } //-- void removeAllProcessName() 

    /**
     * Method removeAllRuleName
     */
    public void removeAllRuleName()
    {
        _ruleNameList.removeAllElements();
    } //-- void removeAllRuleName() 

    /**
     * Method removeEventName
     * 
     * @param index
     */
    public java.lang.String removeEventName(int index)
    {
        java.lang.Object obj = _eventNameList.elementAt(index);
        _eventNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeEventName(int) 

    /**
     * Method removeEventTypeName
     * 
     * @param index
     */
    public java.lang.String removeEventTypeName(int index)
    {
        java.lang.Object obj = _eventTypeNameList.elementAt(index);
        _eventTypeNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeEventTypeName(int) 

    /**
     * Method removeProcessName
     * 
     * @param index
     */
    public java.lang.String removeProcessName(int index)
    {
        java.lang.Object obj = _processNameList.elementAt(index);
        _processNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeProcessName(int) 

    /**
     * Method removeRuleName
     * 
     * @param index
     */
    public java.lang.String removeRuleName(int index)
    {
        java.lang.Object obj = _ruleNameList.elementAt(index);
        _ruleNameList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeRuleName(int) 

    /**
     * Method setEventName
     * 
     * @param index
     * @param vEventName
     */
    public void setEventName(int index, java.lang.String vEventName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _eventNameList.setElementAt(vEventName, index);
    } //-- void setEventName(int, java.lang.String) 

    /**
     * Method setEventName
     * 
     * @param eventNameArray
     */
    public void setEventName(java.lang.String[] eventNameArray)
    {
        //-- copy array
        _eventNameList.removeAllElements();
        for (int i = 0; i < eventNameArray.length; i++) {
            _eventNameList.addElement(eventNameArray[i]);
        }
    } //-- void setEventName(java.lang.String) 

    /**
     * Method setEventTypeName
     * 
     * @param index
     * @param vEventTypeName
     */
    public void setEventTypeName(int index, java.lang.String vEventTypeName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventTypeNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _eventTypeNameList.setElementAt(vEventTypeName, index);
    } //-- void setEventTypeName(int, java.lang.String) 

    /**
     * Method setEventTypeName
     * 
     * @param eventTypeNameArray
     */
    public void setEventTypeName(java.lang.String[] eventTypeNameArray)
    {
        //-- copy array
        _eventTypeNameList.removeAllElements();
        for (int i = 0; i < eventTypeNameArray.length; i++) {
            _eventTypeNameList.addElement(eventTypeNameArray[i]);
        }
    } //-- void setEventTypeName(java.lang.String) 

    /**
     * Method setProcessName
     * 
     * @param index
     * @param vProcessName
     */
    public void setProcessName(int index, java.lang.String vProcessName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _processNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _processNameList.setElementAt(vProcessName, index);
    } //-- void setProcessName(int, java.lang.String) 

    /**
     * Method setProcessName
     * 
     * @param processNameArray
     */
    public void setProcessName(java.lang.String[] processNameArray)
    {
        //-- copy array
        _processNameList.removeAllElements();
        for (int i = 0; i < processNameArray.length; i++) {
            _processNameList.addElement(processNameArray[i]);
        }
    } //-- void setProcessName(java.lang.String) 

    /**
     * Method setRuleName
     * 
     * @param index
     * @param vRuleName
     */
    public void setRuleName(int index, java.lang.String vRuleName)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _ruleNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _ruleNameList.setElementAt(vRuleName, index);
    } //-- void setRuleName(int, java.lang.String) 

    /**
     * Method setRuleName
     * 
     * @param ruleNameArray
     */
    public void setRuleName(java.lang.String[] ruleNameArray)
    {
        //-- copy array
        _ruleNameList.removeAllElements();
        for (int i = 0; i < ruleNameArray.length; i++) {
            _ruleNameList.addElement(ruleNameArray[i]);
        }
    } //-- void setRuleName(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RuleNameVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RuleNameVO) Unmarshaller.unmarshal(edit.common.vo.RuleNameVO.class, reader);
    } //-- edit.common.vo.RuleNameVO unmarshal(java.io.Reader) 

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
