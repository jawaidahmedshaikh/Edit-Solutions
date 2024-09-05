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
 * Contains the information needed to create the Separate Account
 * Investment Values By Division Report
 * 
 * @version $Revision$ $Date$
 */
public class SeparateAccountValuesReportVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Contains the information needed to create the Separate
     * Account Investment Values By Division Report
     */
    private java.util.Vector _separateAccountValueDetailsVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SeparateAccountValuesReportVO() {
        super();
        _separateAccountValueDetailsVOList = new Vector();
    } //-- edit.common.vo.SeparateAccountValuesReportVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSeparateAccountValueDetailsVO
     * 
     * @param vSeparateAccountValueDetailsVO
     */
    public void addSeparateAccountValueDetailsVO(edit.common.vo.SeparateAccountValueDetailsVO vSeparateAccountValueDetailsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSeparateAccountValueDetailsVO.setParentVO(this.getClass(), this);
        _separateAccountValueDetailsVOList.addElement(vSeparateAccountValueDetailsVO);
    } //-- void addSeparateAccountValueDetailsVO(edit.common.vo.SeparateAccountValueDetailsVO) 

    /**
     * Method addSeparateAccountValueDetailsVO
     * 
     * @param index
     * @param vSeparateAccountValueDetailsVO
     */
    public void addSeparateAccountValueDetailsVO(int index, edit.common.vo.SeparateAccountValueDetailsVO vSeparateAccountValueDetailsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSeparateAccountValueDetailsVO.setParentVO(this.getClass(), this);
        _separateAccountValueDetailsVOList.insertElementAt(vSeparateAccountValueDetailsVO, index);
    } //-- void addSeparateAccountValueDetailsVO(int, edit.common.vo.SeparateAccountValueDetailsVO) 

    /**
     * Method enumerateSeparateAccountValueDetailsVO
     */
    public java.util.Enumeration enumerateSeparateAccountValueDetailsVO()
    {
        return _separateAccountValueDetailsVOList.elements();
    } //-- java.util.Enumeration enumerateSeparateAccountValueDetailsVO() 

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
        
        if (obj instanceof SeparateAccountValuesReportVO) {
        
            SeparateAccountValuesReportVO temp = (SeparateAccountValuesReportVO)obj;
            if (this._separateAccountValueDetailsVOList != null) {
                if (temp._separateAccountValueDetailsVOList == null) return false;
                else if (!(this._separateAccountValueDetailsVOList.equals(temp._separateAccountValueDetailsVOList))) 
                    return false;
            }
            else if (temp._separateAccountValueDetailsVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getSeparateAccountValueDetailsVO
     * 
     * @param index
     */
    public edit.common.vo.SeparateAccountValueDetailsVO getSeparateAccountValueDetailsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _separateAccountValueDetailsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SeparateAccountValueDetailsVO) _separateAccountValueDetailsVOList.elementAt(index);
    } //-- edit.common.vo.SeparateAccountValueDetailsVO getSeparateAccountValueDetailsVO(int) 

    /**
     * Method getSeparateAccountValueDetailsVO
     */
    public edit.common.vo.SeparateAccountValueDetailsVO[] getSeparateAccountValueDetailsVO()
    {
        int size = _separateAccountValueDetailsVOList.size();
        edit.common.vo.SeparateAccountValueDetailsVO[] mArray = new edit.common.vo.SeparateAccountValueDetailsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SeparateAccountValueDetailsVO) _separateAccountValueDetailsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SeparateAccountValueDetailsVO[] getSeparateAccountValueDetailsVO() 

    /**
     * Method getSeparateAccountValueDetailsVOCount
     */
    public int getSeparateAccountValueDetailsVOCount()
    {
        return _separateAccountValueDetailsVOList.size();
    } //-- int getSeparateAccountValueDetailsVOCount() 

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
     * Method removeAllSeparateAccountValueDetailsVO
     */
    public void removeAllSeparateAccountValueDetailsVO()
    {
        _separateAccountValueDetailsVOList.removeAllElements();
    } //-- void removeAllSeparateAccountValueDetailsVO() 

    /**
     * Method removeSeparateAccountValueDetailsVO
     * 
     * @param index
     */
    public edit.common.vo.SeparateAccountValueDetailsVO removeSeparateAccountValueDetailsVO(int index)
    {
        java.lang.Object obj = _separateAccountValueDetailsVOList.elementAt(index);
        _separateAccountValueDetailsVOList.removeElementAt(index);
        return (edit.common.vo.SeparateAccountValueDetailsVO) obj;
    } //-- edit.common.vo.SeparateAccountValueDetailsVO removeSeparateAccountValueDetailsVO(int) 

    /**
     * Method setSeparateAccountValueDetailsVO
     * 
     * @param index
     * @param vSeparateAccountValueDetailsVO
     */
    public void setSeparateAccountValueDetailsVO(int index, edit.common.vo.SeparateAccountValueDetailsVO vSeparateAccountValueDetailsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _separateAccountValueDetailsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSeparateAccountValueDetailsVO.setParentVO(this.getClass(), this);
        _separateAccountValueDetailsVOList.setElementAt(vSeparateAccountValueDetailsVO, index);
    } //-- void setSeparateAccountValueDetailsVO(int, edit.common.vo.SeparateAccountValueDetailsVO) 

    /**
     * Method setSeparateAccountValueDetailsVO
     * 
     * @param separateAccountValueDetailsVOArray
     */
    public void setSeparateAccountValueDetailsVO(edit.common.vo.SeparateAccountValueDetailsVO[] separateAccountValueDetailsVOArray)
    {
        //-- copy array
        _separateAccountValueDetailsVOList.removeAllElements();
        for (int i = 0; i < separateAccountValueDetailsVOArray.length; i++) {
            separateAccountValueDetailsVOArray[i].setParentVO(this.getClass(), this);
            _separateAccountValueDetailsVOList.addElement(separateAccountValueDetailsVOArray[i]);
        }
    } //-- void setSeparateAccountValueDetailsVO(edit.common.vo.SeparateAccountValueDetailsVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.SeparateAccountValuesReportVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SeparateAccountValuesReportVO) Unmarshaller.unmarshal(edit.common.vo.SeparateAccountValuesReportVO.class, reader);
    } //-- edit.common.vo.SeparateAccountValuesReportVO unmarshal(java.io.Reader) 

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
