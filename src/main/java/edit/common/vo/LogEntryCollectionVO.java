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
 * Class LogEntryCollectionVO.
 * 
 * @version $Revision$ $Date$
 */
public class LogEntryCollectionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Comment describing your root element
     */
    private java.util.Vector _logEntryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public LogEntryCollectionVO() {
        super();
        _logEntryVOList = new Vector();
    } //-- edit.common.vo.LogEntryCollectionVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addLogEntryVO
     * 
     * @param vLogEntryVO
     */
    public void addLogEntryVO(edit.common.vo.LogEntryVO vLogEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLogEntryVO.setParentVO(this.getClass(), this);
        _logEntryVOList.addElement(vLogEntryVO);
    } //-- void addLogEntryVO(edit.common.vo.LogEntryVO) 

    /**
     * Method addLogEntryVO
     * 
     * @param index
     * @param vLogEntryVO
     */
    public void addLogEntryVO(int index, edit.common.vo.LogEntryVO vLogEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vLogEntryVO.setParentVO(this.getClass(), this);
        _logEntryVOList.insertElementAt(vLogEntryVO, index);
    } //-- void addLogEntryVO(int, edit.common.vo.LogEntryVO) 

    /**
     * Method enumerateLogEntryVO
     */
    public java.util.Enumeration enumerateLogEntryVO()
    {
        return _logEntryVOList.elements();
    } //-- java.util.Enumeration enumerateLogEntryVO() 

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
        
        if (obj instanceof LogEntryCollectionVO) {
        
            LogEntryCollectionVO temp = (LogEntryCollectionVO)obj;
            if (this._logEntryVOList != null) {
                if (temp._logEntryVOList == null) return false;
                else if (!(this._logEntryVOList.equals(temp._logEntryVOList))) 
                    return false;
            }
            else if (temp._logEntryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getLogEntryVO
     * 
     * @param index
     */
    public edit.common.vo.LogEntryVO getLogEntryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _logEntryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.LogEntryVO) _logEntryVOList.elementAt(index);
    } //-- edit.common.vo.LogEntryVO getLogEntryVO(int) 

    /**
     * Method getLogEntryVO
     */
    public edit.common.vo.LogEntryVO[] getLogEntryVO()
    {
        int size = _logEntryVOList.size();
        edit.common.vo.LogEntryVO[] mArray = new edit.common.vo.LogEntryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.LogEntryVO) _logEntryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.LogEntryVO[] getLogEntryVO() 

    /**
     * Method getLogEntryVOCount
     */
    public int getLogEntryVOCount()
    {
        return _logEntryVOList.size();
    } //-- int getLogEntryVOCount() 

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
     * Method removeAllLogEntryVO
     */
    public void removeAllLogEntryVO()
    {
        _logEntryVOList.removeAllElements();
    } //-- void removeAllLogEntryVO() 

    /**
     * Method removeLogEntryVO
     * 
     * @param index
     */
    public edit.common.vo.LogEntryVO removeLogEntryVO(int index)
    {
        java.lang.Object obj = _logEntryVOList.elementAt(index);
        _logEntryVOList.removeElementAt(index);
        return (edit.common.vo.LogEntryVO) obj;
    } //-- edit.common.vo.LogEntryVO removeLogEntryVO(int) 

    /**
     * Method setLogEntryVO
     * 
     * @param index
     * @param vLogEntryVO
     */
    public void setLogEntryVO(int index, edit.common.vo.LogEntryVO vLogEntryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _logEntryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vLogEntryVO.setParentVO(this.getClass(), this);
        _logEntryVOList.setElementAt(vLogEntryVO, index);
    } //-- void setLogEntryVO(int, edit.common.vo.LogEntryVO) 

    /**
     * Method setLogEntryVO
     * 
     * @param logEntryVOArray
     */
    public void setLogEntryVO(edit.common.vo.LogEntryVO[] logEntryVOArray)
    {
        //-- copy array
        _logEntryVOList.removeAllElements();
        for (int i = 0; i < logEntryVOArray.length; i++) {
            logEntryVOArray[i].setParentVO(this.getClass(), this);
            _logEntryVOList.addElement(logEntryVOArray[i]);
        }
    } //-- void setLogEntryVO(edit.common.vo.LogEntryVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LogEntryCollectionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LogEntryCollectionVO) Unmarshaller.unmarshal(edit.common.vo.LogEntryCollectionVO.class, reader);
    } //-- edit.common.vo.LogEntryCollectionVO unmarshal(java.io.Reader) 

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
