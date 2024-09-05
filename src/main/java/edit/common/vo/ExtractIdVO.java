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
public class ExtractIdVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _extractId
     */
    private long _extractId;

    /**
     * keeps track of state for field: _extractId
     */
    private boolean _has_extractId;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _reservesVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ExtractIdVO() {
        super();
        _reservesVOList = new Vector();
    } //-- edit.common.vo.ExtractIdVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addReservesVO
     * 
     * @param vReservesVO
     */
    public void addReservesVO(edit.common.vo.ReservesVO vReservesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReservesVO.setParentVO(this.getClass(), this);
        _reservesVOList.addElement(vReservesVO);
    } //-- void addReservesVO(edit.common.vo.ReservesVO) 

    /**
     * Method addReservesVO
     * 
     * @param index
     * @param vReservesVO
     */
    public void addReservesVO(int index, edit.common.vo.ReservesVO vReservesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReservesVO.setParentVO(this.getClass(), this);
        _reservesVOList.insertElementAt(vReservesVO, index);
    } //-- void addReservesVO(int, edit.common.vo.ReservesVO) 

    /**
     * Method enumerateReservesVO
     */
    public java.util.Enumeration enumerateReservesVO()
    {
        return _reservesVOList.elements();
    } //-- java.util.Enumeration enumerateReservesVO() 

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
        
        if (obj instanceof ExtractIdVO) {
        
            ExtractIdVO temp = (ExtractIdVO)obj;
            if (this._extractId != temp._extractId)
                return false;
            if (this._has_extractId != temp._has_extractId)
                return false;
            if (this._reservesVOList != null) {
                if (temp._reservesVOList == null) return false;
                else if (!(this._reservesVOList.equals(temp._reservesVOList))) 
                    return false;
            }
            else if (temp._reservesVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getExtractIdReturns the value of field 'extractId'.
     * 
     * @return the value of field 'extractId'.
     */
    public long getExtractId()
    {
        return this._extractId;
    } //-- long getExtractId() 

    /**
     * Method getReservesVO
     * 
     * @param index
     */
    public edit.common.vo.ReservesVO getReservesVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reservesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ReservesVO) _reservesVOList.elementAt(index);
    } //-- edit.common.vo.ReservesVO getReservesVO(int) 

    /**
     * Method getReservesVO
     */
    public edit.common.vo.ReservesVO[] getReservesVO()
    {
        int size = _reservesVOList.size();
        edit.common.vo.ReservesVO[] mArray = new edit.common.vo.ReservesVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ReservesVO) _reservesVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ReservesVO[] getReservesVO() 

    /**
     * Method getReservesVOCount
     */
    public int getReservesVOCount()
    {
        return _reservesVOList.size();
    } //-- int getReservesVOCount() 

    /**
     * Method hasExtractId
     */
    public boolean hasExtractId()
    {
        return this._has_extractId;
    } //-- boolean hasExtractId() 

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
     * Method removeAllReservesVO
     */
    public void removeAllReservesVO()
    {
        _reservesVOList.removeAllElements();
    } //-- void removeAllReservesVO() 

    /**
     * Method removeReservesVO
     * 
     * @param index
     */
    public edit.common.vo.ReservesVO removeReservesVO(int index)
    {
        java.lang.Object obj = _reservesVOList.elementAt(index);
        _reservesVOList.removeElementAt(index);
        return (edit.common.vo.ReservesVO) obj;
    } //-- edit.common.vo.ReservesVO removeReservesVO(int) 

    /**
     * Method setExtractIdSets the value of field 'extractId'.
     * 
     * @param extractId the value of field 'extractId'.
     */
    public void setExtractId(long extractId)
    {
        this._extractId = extractId;
        
        super.setVoChanged(true);
        this._has_extractId = true;
    } //-- void setExtractId(long) 

    /**
     * Method setReservesVO
     * 
     * @param index
     * @param vReservesVO
     */
    public void setReservesVO(int index, edit.common.vo.ReservesVO vReservesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reservesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vReservesVO.setParentVO(this.getClass(), this);
        _reservesVOList.setElementAt(vReservesVO, index);
    } //-- void setReservesVO(int, edit.common.vo.ReservesVO) 

    /**
     * Method setReservesVO
     * 
     * @param reservesVOArray
     */
    public void setReservesVO(edit.common.vo.ReservesVO[] reservesVOArray)
    {
        //-- copy array
        _reservesVOList.removeAllElements();
        for (int i = 0; i < reservesVOArray.length; i++) {
            reservesVOArray[i].setParentVO(this.getClass(), this);
            _reservesVOList.addElement(reservesVOArray[i]);
        }
    } //-- void setReservesVO(edit.common.vo.ReservesVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ExtractIdVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ExtractIdVO) Unmarshaller.unmarshal(edit.common.vo.ExtractIdVO.class, reader);
    } //-- edit.common.vo.ExtractIdVO unmarshal(java.io.Reader) 

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
