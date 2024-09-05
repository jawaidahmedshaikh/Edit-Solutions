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
public class LogEntryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private long _id;

    /**
     * keeps track of state for field: _id
     */
    private boolean _has_id;

    /**
     * Field _time
     */
    private java.lang.String _time;

    /**
     * Field _type
     */
    private java.lang.String _type;

    /**
     * Field _logName
     */
    private java.lang.String _logName;

    /**
     * Field _contextName
     */
    private java.lang.String _contextName;

    /**
     * Field _logContextEntryVOList
     */
    private java.util.Vector _logContextEntryVOList;

    /**
     * Field _message
     */
    private java.lang.String _message;

    /**
     * Field _exceptionMessageTraceList
     */
    private java.util.Vector _exceptionMessageTraceList;

    /**
     * Field _exceptionStackTraceList
     */
    private java.util.Vector _exceptionStackTraceList;


      //----------------/
     //- Constructors -/
    //----------------/

    public LogEntryVO() {
        super();
        _logContextEntryVOList = new Vector();
        _exceptionMessageTraceList = new Vector();
        _exceptionStackTraceList = new Vector();
    } //-- edit.common.vo.LogEntryVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addExceptionMessageTrace
     * 
     * @param vExceptionMessageTrace
     */
    public void addExceptionMessageTrace(java.lang.String vExceptionMessageTrace)
        throws java.lang.IndexOutOfBoundsException
    {
        _exceptionMessageTraceList.addElement(vExceptionMessageTrace);
    } //-- void addExceptionMessageTrace(java.lang.String) 

    /**
     * Method addExceptionMessageTrace
     * 
     * @param index
     * @param vExceptionMessageTrace
     */
    public void addExceptionMessageTrace(int index, java.lang.String vExceptionMessageTrace)
        throws java.lang.IndexOutOfBoundsException
    {
        _exceptionMessageTraceList.insertElementAt(vExceptionMessageTrace, index);
    } //-- void addExceptionMessageTrace(int, java.lang.String) 

    /**
     * Method addExceptionStackTrace
     * 
     * @param vExceptionStackTrace
     */
    public void addExceptionStackTrace(java.lang.String vExceptionStackTrace)
        throws java.lang.IndexOutOfBoundsException
    {
        _exceptionStackTraceList.addElement(vExceptionStackTrace);
    } //-- void addExceptionStackTrace(java.lang.String) 

    /**
     * Method addExceptionStackTrace
     * 
     * @param index
     * @param vExceptionStackTrace
     */
    public void addExceptionStackTrace(int index, java.lang.String vExceptionStackTrace)
        throws java.lang.IndexOutOfBoundsException
    {
        _exceptionStackTraceList.insertElementAt(vExceptionStackTrace, index);
    } //-- void addExceptionStackTrace(int, java.lang.String) 

    /**
     * Method addLogContextEntryVO
     * 
     * @param vLogContextEntryVO
     */
    public void addLogContextEntryVO(edit.common.vo.LogContextEntryVO vLogContextEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLogContextEntryVO.setParentVO(this.getClass(), this);
        _logContextEntryVOList.addElement(vLogContextEntryVO);
    } //-- void addLogContextEntryVO(edit.common.vo.LogContextEntryVO) 

    /**
     * Method addLogContextEntryVO
     * 
     * @param index
     * @param vLogContextEntryVO
     */
    public void addLogContextEntryVO(int index, edit.common.vo.LogContextEntryVO vLogContextEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLogContextEntryVO.setParentVO(this.getClass(), this);
        _logContextEntryVOList.insertElementAt(vLogContextEntryVO, index);
    } //-- void addLogContextEntryVO(int, edit.common.vo.LogContextEntryVO) 

    /**
     * Method deleteId
     */
    public void deleteId()
    {
        this._has_id= false;
    } //-- void deleteId() 

    /**
     * Method enumerateExceptionMessageTrace
     */
    public java.util.Enumeration enumerateExceptionMessageTrace()
    {
        return _exceptionMessageTraceList.elements();
    } //-- java.util.Enumeration enumerateExceptionMessageTrace() 

    /**
     * Method enumerateExceptionStackTrace
     */
    public java.util.Enumeration enumerateExceptionStackTrace()
    {
        return _exceptionStackTraceList.elements();
    } //-- java.util.Enumeration enumerateExceptionStackTrace() 

    /**
     * Method enumerateLogContextEntryVO
     */
    public java.util.Enumeration enumerateLogContextEntryVO()
    {
        return _logContextEntryVOList.elements();
    } //-- java.util.Enumeration enumerateLogContextEntryVO() 

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
        
        if (obj instanceof LogEntryVO) {
        
            LogEntryVO temp = (LogEntryVO)obj;
            if (this._id != temp._id)
                return false;
            if (this._has_id != temp._has_id)
                return false;
            if (this._time != null) {
                if (temp._time == null) return false;
                else if (!(this._time.equals(temp._time))) 
                    return false;
            }
            else if (temp._time != null)
                return false;
            if (this._type != null) {
                if (temp._type == null) return false;
                else if (!(this._type.equals(temp._type))) 
                    return false;
            }
            else if (temp._type != null)
                return false;
            if (this._logName != null) {
                if (temp._logName == null) return false;
                else if (!(this._logName.equals(temp._logName))) 
                    return false;
            }
            else if (temp._logName != null)
                return false;
            if (this._contextName != null) {
                if (temp._contextName == null) return false;
                else if (!(this._contextName.equals(temp._contextName))) 
                    return false;
            }
            else if (temp._contextName != null)
                return false;
            if (this._logContextEntryVOList != null) {
                if (temp._logContextEntryVOList == null) return false;
                else if (!(this._logContextEntryVOList.equals(temp._logContextEntryVOList))) 
                    return false;
            }
            else if (temp._logContextEntryVOList != null)
                return false;
            if (this._message != null) {
                if (temp._message == null) return false;
                else if (!(this._message.equals(temp._message))) 
                    return false;
            }
            else if (temp._message != null)
                return false;
            if (this._exceptionMessageTraceList != null) {
                if (temp._exceptionMessageTraceList == null) return false;
                else if (!(this._exceptionMessageTraceList.equals(temp._exceptionMessageTraceList))) 
                    return false;
            }
            else if (temp._exceptionMessageTraceList != null)
                return false;
            if (this._exceptionStackTraceList != null) {
                if (temp._exceptionStackTraceList == null) return false;
                else if (!(this._exceptionStackTraceList.equals(temp._exceptionStackTraceList))) 
                    return false;
            }
            else if (temp._exceptionStackTraceList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContextNameReturns the value of field
     * 'contextName'.
     * 
     * @return the value of field 'contextName'.
     */
    public java.lang.String getContextName()
    {
        return this._contextName;
    } //-- java.lang.String getContextName() 

    /**
     * Method getExceptionMessageTrace
     * 
     * @param index
     */
    public java.lang.String getExceptionMessageTrace(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _exceptionMessageTraceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_exceptionMessageTraceList.elementAt(index);
    } //-- java.lang.String getExceptionMessageTrace(int) 

    /**
     * Method getExceptionMessageTrace
     */
    public java.lang.String[] getExceptionMessageTrace()
    {
        int size = _exceptionMessageTraceList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_exceptionMessageTraceList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getExceptionMessageTrace() 

    /**
     * Method getExceptionMessageTraceCount
     */
    public int getExceptionMessageTraceCount()
    {
        return _exceptionMessageTraceList.size();
    } //-- int getExceptionMessageTraceCount() 

    /**
     * Method getExceptionStackTrace
     * 
     * @param index
     */
    public java.lang.String getExceptionStackTrace(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _exceptionStackTraceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_exceptionStackTraceList.elementAt(index);
    } //-- java.lang.String getExceptionStackTrace(int) 

    /**
     * Method getExceptionStackTrace
     */
    public java.lang.String[] getExceptionStackTrace()
    {
        int size = _exceptionStackTraceList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_exceptionStackTraceList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getExceptionStackTrace() 

    /**
     * Method getExceptionStackTraceCount
     */
    public int getExceptionStackTraceCount()
    {
        return _exceptionStackTraceList.size();
    } //-- int getExceptionStackTraceCount() 

    /**
     * Method getIdReturns the value of field 'id'.
     * 
     * @return the value of field 'id'.
     */
    public long getId()
    {
        return this._id;
    } //-- long getId() 

    /**
     * Method getLogContextEntryVO
     * 
     * @param index
     */
    public edit.common.vo.LogContextEntryVO getLogContextEntryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _logContextEntryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.LogContextEntryVO) _logContextEntryVOList.elementAt(index);
    } //-- edit.common.vo.LogContextEntryVO getLogContextEntryVO(int) 

    /**
     * Method getLogContextEntryVO
     */
    public edit.common.vo.LogContextEntryVO[] getLogContextEntryVO()
    {
        int size = _logContextEntryVOList.size();
        edit.common.vo.LogContextEntryVO[] mArray = new edit.common.vo.LogContextEntryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.LogContextEntryVO) _logContextEntryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.LogContextEntryVO[] getLogContextEntryVO() 

    /**
     * Method getLogContextEntryVOCount
     */
    public int getLogContextEntryVOCount()
    {
        return _logContextEntryVOList.size();
    } //-- int getLogContextEntryVOCount() 

    /**
     * Method getLogNameReturns the value of field 'logName'.
     * 
     * @return the value of field 'logName'.
     */
    public java.lang.String getLogName()
    {
        return this._logName;
    } //-- java.lang.String getLogName() 

    /**
     * Method getMessageReturns the value of field 'message'.
     * 
     * @return the value of field 'message'.
     */
    public java.lang.String getMessage()
    {
        return this._message;
    } //-- java.lang.String getMessage() 

    /**
     * Method getTimeReturns the value of field 'time'.
     * 
     * @return the value of field 'time'.
     */
    public java.lang.String getTime()
    {
        return this._time;
    } //-- java.lang.String getTime() 

    /**
     * Method getTypeReturns the value of field 'type'.
     * 
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

    /**
     * Method hasId
     */
    public boolean hasId()
    {
        return this._has_id;
    } //-- boolean hasId() 

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
     * Method removeAllExceptionMessageTrace
     */
    public void removeAllExceptionMessageTrace()
    {
        _exceptionMessageTraceList.removeAllElements();
    } //-- void removeAllExceptionMessageTrace() 

    /**
     * Method removeAllExceptionStackTrace
     */
    public void removeAllExceptionStackTrace()
    {
        _exceptionStackTraceList.removeAllElements();
    } //-- void removeAllExceptionStackTrace() 

    /**
     * Method removeAllLogContextEntryVO
     */
    public void removeAllLogContextEntryVO()
    {
        _logContextEntryVOList.removeAllElements();
    } //-- void removeAllLogContextEntryVO() 

    /**
     * Method removeExceptionMessageTrace
     * 
     * @param index
     */
    public java.lang.String removeExceptionMessageTrace(int index)
    {
        java.lang.Object obj = _exceptionMessageTraceList.elementAt(index);
        _exceptionMessageTraceList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeExceptionMessageTrace(int) 

    /**
     * Method removeExceptionStackTrace
     * 
     * @param index
     */
    public java.lang.String removeExceptionStackTrace(int index)
    {
        java.lang.Object obj = _exceptionStackTraceList.elementAt(index);
        _exceptionStackTraceList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeExceptionStackTrace(int) 

    /**
     * Method removeLogContextEntryVO
     * 
     * @param index
     */
    public edit.common.vo.LogContextEntryVO removeLogContextEntryVO(int index)
    {
        java.lang.Object obj = _logContextEntryVOList.elementAt(index);
        _logContextEntryVOList.removeElementAt(index);
        return (edit.common.vo.LogContextEntryVO) obj;
    } //-- edit.common.vo.LogContextEntryVO removeLogContextEntryVO(int) 

    /**
     * Method setContextNameSets the value of field 'contextName'.
     * 
     * @param contextName the value of field 'contextName'.
     */
    public void setContextName(java.lang.String contextName)
    {
        this._contextName = contextName;
        
        super.setVoChanged(true);
    } //-- void setContextName(java.lang.String) 

    /**
     * Method setExceptionMessageTrace
     * 
     * @param index
     * @param vExceptionMessageTrace
     */
    public void setExceptionMessageTrace(int index, java.lang.String vExceptionMessageTrace)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _exceptionMessageTraceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _exceptionMessageTraceList.setElementAt(vExceptionMessageTrace, index);
    } //-- void setExceptionMessageTrace(int, java.lang.String) 

    /**
     * Method setExceptionMessageTrace
     * 
     * @param exceptionMessageTraceArray
     */
    public void setExceptionMessageTrace(java.lang.String[] exceptionMessageTraceArray)
    {
        //-- copy array
        _exceptionMessageTraceList.removeAllElements();
        for (int i = 0; i < exceptionMessageTraceArray.length; i++) {
            _exceptionMessageTraceList.addElement(exceptionMessageTraceArray[i]);
        }
    } //-- void setExceptionMessageTrace(java.lang.String) 

    /**
     * Method setExceptionStackTrace
     * 
     * @param index
     * @param vExceptionStackTrace
     */
    public void setExceptionStackTrace(int index, java.lang.String vExceptionStackTrace)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _exceptionStackTraceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _exceptionStackTraceList.setElementAt(vExceptionStackTrace, index);
    } //-- void setExceptionStackTrace(int, java.lang.String) 

    /**
     * Method setExceptionStackTrace
     * 
     * @param exceptionStackTraceArray
     */
    public void setExceptionStackTrace(java.lang.String[] exceptionStackTraceArray)
    {
        //-- copy array
        _exceptionStackTraceList.removeAllElements();
        for (int i = 0; i < exceptionStackTraceArray.length; i++) {
            _exceptionStackTraceList.addElement(exceptionStackTraceArray[i]);
        }
    } //-- void setExceptionStackTrace(java.lang.String) 

    /**
     * Method setIdSets the value of field 'id'.
     * 
     * @param id the value of field 'id'.
     */
    public void setId(long id)
    {
        this._id = id;
        
        super.setVoChanged(true);
        this._has_id = true;
    } //-- void setId(long) 

    /**
     * Method setLogContextEntryVO
     * 
     * @param index
     * @param vLogContextEntryVO
     */
    public void setLogContextEntryVO(int index, edit.common.vo.LogContextEntryVO vLogContextEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _logContextEntryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vLogContextEntryVO.setParentVO(this.getClass(), this);
        _logContextEntryVOList.setElementAt(vLogContextEntryVO, index);
    } //-- void setLogContextEntryVO(int, edit.common.vo.LogContextEntryVO) 

    /**
     * Method setLogContextEntryVO
     * 
     * @param logContextEntryVOArray
     */
    public void setLogContextEntryVO(edit.common.vo.LogContextEntryVO[] logContextEntryVOArray)
    {
        //-- copy array
        _logContextEntryVOList.removeAllElements();
        for (int i = 0; i < logContextEntryVOArray.length; i++) {
            logContextEntryVOArray[i].setParentVO(this.getClass(), this);
            _logContextEntryVOList.addElement(logContextEntryVOArray[i]);
        }
    } //-- void setLogContextEntryVO(edit.common.vo.LogContextEntryVO) 

    /**
     * Method setLogNameSets the value of field 'logName'.
     * 
     * @param logName the value of field 'logName'.
     */
    public void setLogName(java.lang.String logName)
    {
        this._logName = logName;
        
        super.setVoChanged(true);
    } //-- void setLogName(java.lang.String) 

    /**
     * Method setMessageSets the value of field 'message'.
     * 
     * @param message the value of field 'message'.
     */
    public void setMessage(java.lang.String message)
    {
        this._message = message;
        
        super.setVoChanged(true);
    } //-- void setMessage(java.lang.String) 

    /**
     * Method setTimeSets the value of field 'time'.
     * 
     * @param time the value of field 'time'.
     */
    public void setTime(java.lang.String time)
    {
        this._time = time;
        
        super.setVoChanged(true);
    } //-- void setTime(java.lang.String) 

    /**
     * Method setTypeSets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
        
        super.setVoChanged(true);
    } //-- void setType(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LogEntryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LogEntryVO) Unmarshaller.unmarshal(edit.common.vo.LogEntryVO.class, reader);
    } //-- edit.common.vo.LogEntryVO unmarshal(java.io.Reader) 

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
