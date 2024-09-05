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
 * Class GAAPReservesVO.
 * 
 * @version $Revision$ $Date$
 */
public class GAAPReservesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _GAAPReservesDetailVOList
     */
    private java.util.Vector _GAAPReservesDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public GAAPReservesVO() {
        super();
        _GAAPReservesDetailVOList = new Vector();
    } //-- edit.common.vo.GAAPReservesVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addGAAPReservesDetailVO
     * 
     * @param vGAAPReservesDetailVO
     */
    public void addGAAPReservesDetailVO(edit.common.vo.GAAPReservesDetailVO vGAAPReservesDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGAAPReservesDetailVO.setParentVO(this.getClass(), this);
        _GAAPReservesDetailVOList.addElement(vGAAPReservesDetailVO);
    } //-- void addGAAPReservesDetailVO(edit.common.vo.GAAPReservesDetailVO) 

    /**
     * Method addGAAPReservesDetailVO
     * 
     * @param index
     * @param vGAAPReservesDetailVO
     */
    public void addGAAPReservesDetailVO(int index, edit.common.vo.GAAPReservesDetailVO vGAAPReservesDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGAAPReservesDetailVO.setParentVO(this.getClass(), this);
        _GAAPReservesDetailVOList.insertElementAt(vGAAPReservesDetailVO, index);
    } //-- void addGAAPReservesDetailVO(int, edit.common.vo.GAAPReservesDetailVO) 

    /**
     * Method enumerateGAAPReservesDetailVO
     */
    public java.util.Enumeration enumerateGAAPReservesDetailVO()
    {
        return _GAAPReservesDetailVOList.elements();
    } //-- java.util.Enumeration enumerateGAAPReservesDetailVO() 

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
        
        if (obj instanceof GAAPReservesVO) {
        
            GAAPReservesVO temp = (GAAPReservesVO)obj;
            if (this._GAAPReservesDetailVOList != null) {
                if (temp._GAAPReservesDetailVOList == null) return false;
                else if (!(this._GAAPReservesDetailVOList.equals(temp._GAAPReservesDetailVOList))) 
                    return false;
            }
            else if (temp._GAAPReservesDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getGAAPReservesDetailVO
     * 
     * @param index
     */
    public edit.common.vo.GAAPReservesDetailVO getGAAPReservesDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _GAAPReservesDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.GAAPReservesDetailVO) _GAAPReservesDetailVOList.elementAt(index);
    } //-- edit.common.vo.GAAPReservesDetailVO getGAAPReservesDetailVO(int) 

    /**
     * Method getGAAPReservesDetailVO
     */
    public edit.common.vo.GAAPReservesDetailVO[] getGAAPReservesDetailVO()
    {
        int size = _GAAPReservesDetailVOList.size();
        edit.common.vo.GAAPReservesDetailVO[] mArray = new edit.common.vo.GAAPReservesDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.GAAPReservesDetailVO) _GAAPReservesDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.GAAPReservesDetailVO[] getGAAPReservesDetailVO() 

    /**
     * Method getGAAPReservesDetailVOCount
     */
    public int getGAAPReservesDetailVOCount()
    {
        return _GAAPReservesDetailVOList.size();
    } //-- int getGAAPReservesDetailVOCount() 

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
     * Method removeAllGAAPReservesDetailVO
     */
    public void removeAllGAAPReservesDetailVO()
    {
        _GAAPReservesDetailVOList.removeAllElements();
    } //-- void removeAllGAAPReservesDetailVO() 

    /**
     * Method removeGAAPReservesDetailVO
     * 
     * @param index
     */
    public edit.common.vo.GAAPReservesDetailVO removeGAAPReservesDetailVO(int index)
    {
        java.lang.Object obj = _GAAPReservesDetailVOList.elementAt(index);
        _GAAPReservesDetailVOList.removeElementAt(index);
        return (edit.common.vo.GAAPReservesDetailVO) obj;
    } //-- edit.common.vo.GAAPReservesDetailVO removeGAAPReservesDetailVO(int) 

    /**
     * Method setGAAPReservesDetailVO
     * 
     * @param index
     * @param vGAAPReservesDetailVO
     */
    public void setGAAPReservesDetailVO(int index, edit.common.vo.GAAPReservesDetailVO vGAAPReservesDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _GAAPReservesDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vGAAPReservesDetailVO.setParentVO(this.getClass(), this);
        _GAAPReservesDetailVOList.setElementAt(vGAAPReservesDetailVO, index);
    } //-- void setGAAPReservesDetailVO(int, edit.common.vo.GAAPReservesDetailVO) 

    /**
     * Method setGAAPReservesDetailVO
     * 
     * @param GAAPReservesDetailVOArray
     */
    public void setGAAPReservesDetailVO(edit.common.vo.GAAPReservesDetailVO[] GAAPReservesDetailVOArray)
    {
        //-- copy array
        _GAAPReservesDetailVOList.removeAllElements();
        for (int i = 0; i < GAAPReservesDetailVOArray.length; i++) {
            GAAPReservesDetailVOArray[i].setParentVO(this.getClass(), this);
            _GAAPReservesDetailVOList.addElement(GAAPReservesDetailVOArray[i]);
        }
    } //-- void setGAAPReservesDetailVO(edit.common.vo.GAAPReservesDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.GAAPReservesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.GAAPReservesVO) Unmarshaller.unmarshal(edit.common.vo.GAAPReservesVO.class, reader);
    } //-- edit.common.vo.GAAPReservesVO unmarshal(java.io.Reader) 

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
