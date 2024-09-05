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
public class HedgeFundNotificationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Contains the information required for generating the hedge
     * fund notification report
     */
    private java.util.Vector _hedgeFundNotificationDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public HedgeFundNotificationVO() {
        super();
        _hedgeFundNotificationDetailVOList = new Vector();
    } //-- edit.common.vo.HedgeFundNotificationVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addHedgeFundNotificationDetailVO
     * 
     * @param vHedgeFundNotificationDetailVO
     */
    public void addHedgeFundNotificationDetailVO(edit.common.vo.HedgeFundNotificationDetailVO vHedgeFundNotificationDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vHedgeFundNotificationDetailVO.setParentVO(this.getClass(), this);
        _hedgeFundNotificationDetailVOList.addElement(vHedgeFundNotificationDetailVO);
    } //-- void addHedgeFundNotificationDetailVO(edit.common.vo.HedgeFundNotificationDetailVO) 

    /**
     * Method addHedgeFundNotificationDetailVO
     * 
     * @param index
     * @param vHedgeFundNotificationDetailVO
     */
    public void addHedgeFundNotificationDetailVO(int index, edit.common.vo.HedgeFundNotificationDetailVO vHedgeFundNotificationDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vHedgeFundNotificationDetailVO.setParentVO(this.getClass(), this);
        _hedgeFundNotificationDetailVOList.insertElementAt(vHedgeFundNotificationDetailVO, index);
    } //-- void addHedgeFundNotificationDetailVO(int, edit.common.vo.HedgeFundNotificationDetailVO) 

    /**
     * Method enumerateHedgeFundNotificationDetailVO
     */
    public java.util.Enumeration enumerateHedgeFundNotificationDetailVO()
    {
        return _hedgeFundNotificationDetailVOList.elements();
    } //-- java.util.Enumeration enumerateHedgeFundNotificationDetailVO() 

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
        
        if (obj instanceof HedgeFundNotificationVO) {
        
            HedgeFundNotificationVO temp = (HedgeFundNotificationVO)obj;
            if (this._hedgeFundNotificationDetailVOList != null) {
                if (temp._hedgeFundNotificationDetailVOList == null) return false;
                else if (!(this._hedgeFundNotificationDetailVOList.equals(temp._hedgeFundNotificationDetailVOList))) 
                    return false;
            }
            else if (temp._hedgeFundNotificationDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getHedgeFundNotificationDetailVO
     * 
     * @param index
     */
    public edit.common.vo.HedgeFundNotificationDetailVO getHedgeFundNotificationDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _hedgeFundNotificationDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.HedgeFundNotificationDetailVO) _hedgeFundNotificationDetailVOList.elementAt(index);
    } //-- edit.common.vo.HedgeFundNotificationDetailVO getHedgeFundNotificationDetailVO(int) 

    /**
     * Method getHedgeFundNotificationDetailVO
     */
    public edit.common.vo.HedgeFundNotificationDetailVO[] getHedgeFundNotificationDetailVO()
    {
        int size = _hedgeFundNotificationDetailVOList.size();
        edit.common.vo.HedgeFundNotificationDetailVO[] mArray = new edit.common.vo.HedgeFundNotificationDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.HedgeFundNotificationDetailVO) _hedgeFundNotificationDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.HedgeFundNotificationDetailVO[] getHedgeFundNotificationDetailVO() 

    /**
     * Method getHedgeFundNotificationDetailVOCount
     */
    public int getHedgeFundNotificationDetailVOCount()
    {
        return _hedgeFundNotificationDetailVOList.size();
    } //-- int getHedgeFundNotificationDetailVOCount() 

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
     * Method removeAllHedgeFundNotificationDetailVO
     */
    public void removeAllHedgeFundNotificationDetailVO()
    {
        _hedgeFundNotificationDetailVOList.removeAllElements();
    } //-- void removeAllHedgeFundNotificationDetailVO() 

    /**
     * Method removeHedgeFundNotificationDetailVO
     * 
     * @param index
     */
    public edit.common.vo.HedgeFundNotificationDetailVO removeHedgeFundNotificationDetailVO(int index)
    {
        java.lang.Object obj = _hedgeFundNotificationDetailVOList.elementAt(index);
        _hedgeFundNotificationDetailVOList.removeElementAt(index);
        return (edit.common.vo.HedgeFundNotificationDetailVO) obj;
    } //-- edit.common.vo.HedgeFundNotificationDetailVO removeHedgeFundNotificationDetailVO(int) 

    /**
     * Method setHedgeFundNotificationDetailVO
     * 
     * @param index
     * @param vHedgeFundNotificationDetailVO
     */
    public void setHedgeFundNotificationDetailVO(int index, edit.common.vo.HedgeFundNotificationDetailVO vHedgeFundNotificationDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _hedgeFundNotificationDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vHedgeFundNotificationDetailVO.setParentVO(this.getClass(), this);
        _hedgeFundNotificationDetailVOList.setElementAt(vHedgeFundNotificationDetailVO, index);
    } //-- void setHedgeFundNotificationDetailVO(int, edit.common.vo.HedgeFundNotificationDetailVO) 

    /**
     * Method setHedgeFundNotificationDetailVO
     * 
     * @param hedgeFundNotificationDetailVOArray
     */
    public void setHedgeFundNotificationDetailVO(edit.common.vo.HedgeFundNotificationDetailVO[] hedgeFundNotificationDetailVOArray)
    {
        //-- copy array
        _hedgeFundNotificationDetailVOList.removeAllElements();
        for (int i = 0; i < hedgeFundNotificationDetailVOArray.length; i++) {
            hedgeFundNotificationDetailVOArray[i].setParentVO(this.getClass(), this);
            _hedgeFundNotificationDetailVOList.addElement(hedgeFundNotificationDetailVOArray[i]);
        }
    } //-- void setHedgeFundNotificationDetailVO(edit.common.vo.HedgeFundNotificationDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.HedgeFundNotificationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.HedgeFundNotificationVO) Unmarshaller.unmarshal(edit.common.vo.HedgeFundNotificationVO.class, reader);
    } //-- edit.common.vo.HedgeFundNotificationVO unmarshal(java.io.Reader) 

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
