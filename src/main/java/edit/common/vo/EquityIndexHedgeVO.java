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
 * Class EquityIndexHedgeVO.
 * 
 * @version $Revision$ $Date$
 */
public class EquityIndexHedgeVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _equityIndexHedgeDetailVOList
     */
    private java.util.Vector _equityIndexHedgeDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EquityIndexHedgeVO() {
        super();
        _equityIndexHedgeDetailVOList = new Vector();
    } //-- edit.common.vo.EquityIndexHedgeVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addEquityIndexHedgeDetailVO
     * 
     * @param vEquityIndexHedgeDetailVO
     */
    public void addEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO vEquityIndexHedgeDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEquityIndexHedgeDetailVO.setParentVO(this.getClass(), this);
        _equityIndexHedgeDetailVOList.addElement(vEquityIndexHedgeDetailVO);
    } //-- void addEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method addEquityIndexHedgeDetailVO
     * 
     * @param index
     * @param vEquityIndexHedgeDetailVO
     */
    public void addEquityIndexHedgeDetailVO(int index, edit.common.vo.EquityIndexHedgeDetailVO vEquityIndexHedgeDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEquityIndexHedgeDetailVO.setParentVO(this.getClass(), this);
        _equityIndexHedgeDetailVOList.insertElementAt(vEquityIndexHedgeDetailVO, index);
    } //-- void addEquityIndexHedgeDetailVO(int, edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method enumerateEquityIndexHedgeDetailVO
     */
    public java.util.Enumeration enumerateEquityIndexHedgeDetailVO()
    {
        return _equityIndexHedgeDetailVOList.elements();
    } //-- java.util.Enumeration enumerateEquityIndexHedgeDetailVO() 

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
        
        if (obj instanceof EquityIndexHedgeVO) {
        
            EquityIndexHedgeVO temp = (EquityIndexHedgeVO)obj;
            if (this._equityIndexHedgeDetailVOList != null) {
                if (temp._equityIndexHedgeDetailVOList == null) return false;
                else if (!(this._equityIndexHedgeDetailVOList.equals(temp._equityIndexHedgeDetailVOList))) 
                    return false;
            }
            else if (temp._equityIndexHedgeDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEquityIndexHedgeDetailVO
     * 
     * @param index
     */
    public edit.common.vo.EquityIndexHedgeDetailVO getEquityIndexHedgeDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _equityIndexHedgeDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EquityIndexHedgeDetailVO) _equityIndexHedgeDetailVOList.elementAt(index);
    } //-- edit.common.vo.EquityIndexHedgeDetailVO getEquityIndexHedgeDetailVO(int) 

    /**
     * Method getEquityIndexHedgeDetailVO
     */
    public edit.common.vo.EquityIndexHedgeDetailVO[] getEquityIndexHedgeDetailVO()
    {
        int size = _equityIndexHedgeDetailVOList.size();
        edit.common.vo.EquityIndexHedgeDetailVO[] mArray = new edit.common.vo.EquityIndexHedgeDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EquityIndexHedgeDetailVO) _equityIndexHedgeDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EquityIndexHedgeDetailVO[] getEquityIndexHedgeDetailVO() 

    /**
     * Method getEquityIndexHedgeDetailVOCount
     */
    public int getEquityIndexHedgeDetailVOCount()
    {
        return _equityIndexHedgeDetailVOList.size();
    } //-- int getEquityIndexHedgeDetailVOCount() 

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
     * Method removeAllEquityIndexHedgeDetailVO
     */
    public void removeAllEquityIndexHedgeDetailVO()
    {
        _equityIndexHedgeDetailVOList.removeAllElements();
    } //-- void removeAllEquityIndexHedgeDetailVO() 

    /**
     * Method removeEquityIndexHedgeDetailVO
     * 
     * @param index
     */
    public edit.common.vo.EquityIndexHedgeDetailVO removeEquityIndexHedgeDetailVO(int index)
    {
        java.lang.Object obj = _equityIndexHedgeDetailVOList.elementAt(index);
        _equityIndexHedgeDetailVOList.removeElementAt(index);
        return (edit.common.vo.EquityIndexHedgeDetailVO) obj;
    } //-- edit.common.vo.EquityIndexHedgeDetailVO removeEquityIndexHedgeDetailVO(int) 

    /**
     * Method setEquityIndexHedgeDetailVO
     * 
     * @param index
     * @param vEquityIndexHedgeDetailVO
     */
    public void setEquityIndexHedgeDetailVO(int index, edit.common.vo.EquityIndexHedgeDetailVO vEquityIndexHedgeDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _equityIndexHedgeDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEquityIndexHedgeDetailVO.setParentVO(this.getClass(), this);
        _equityIndexHedgeDetailVOList.setElementAt(vEquityIndexHedgeDetailVO, index);
    } //-- void setEquityIndexHedgeDetailVO(int, edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method setEquityIndexHedgeDetailVO
     * 
     * @param equityIndexHedgeDetailVOArray
     */
    public void setEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO[] equityIndexHedgeDetailVOArray)
    {
        //-- copy array
        _equityIndexHedgeDetailVOList.removeAllElements();
        for (int i = 0; i < equityIndexHedgeDetailVOArray.length; i++) {
            equityIndexHedgeDetailVOArray[i].setParentVO(this.getClass(), this);
            _equityIndexHedgeDetailVOList.addElement(equityIndexHedgeDetailVOArray[i]);
        }
    } //-- void setEquityIndexHedgeDetailVO(edit.common.vo.EquityIndexHedgeDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EquityIndexHedgeVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EquityIndexHedgeVO) Unmarshaller.unmarshal(edit.common.vo.EquityIndexHedgeVO.class, reader);
    } //-- edit.common.vo.EquityIndexHedgeVO unmarshal(java.io.Reader) 

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
