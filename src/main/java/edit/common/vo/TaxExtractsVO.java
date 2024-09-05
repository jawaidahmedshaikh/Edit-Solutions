/*
 * Copyright (c) 2004 Your Corporation. All Rights Reserved.
 */

package edit.common.vo;

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
 * Class TaxExtractsVO.
 * 
 * @version $Revision$ $Date$
 */
public class TaxExtractsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Document to be fed into PRASE for the generation of GAAP
     * Reserves 
     */
    private java.util.Vector _taxExtractDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaxExtractsVO() {
        super();
        _taxExtractDetailVOList = new Vector();
    } //-- edit.common.vo.TaxExtractsVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addTaxExtractDetailVO
     * 
     * @param vTaxExtractDetailVO
     */
    public void addTaxExtractDetailVO(edit.common.vo.TaxExtractDetailVO vTaxExtractDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxExtractDetailVO.setParentVO(this.getClass(), this);
        _taxExtractDetailVOList.addElement(vTaxExtractDetailVO);
    } //-- void addTaxExtractDetailVO(edit.common.vo.TaxExtractDetailVO) 

    /**
     * Method addTaxExtractDetailVO
     * 
     * @param index
     * @param vTaxExtractDetailVO
     */
    public void addTaxExtractDetailVO(int index, edit.common.vo.TaxExtractDetailVO vTaxExtractDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxExtractDetailVO.setParentVO(this.getClass(), this);
        _taxExtractDetailVOList.insertElementAt(vTaxExtractDetailVO, index);
    } //-- void addTaxExtractDetailVO(int, edit.common.vo.TaxExtractDetailVO) 

    /**
     * Method enumerateTaxExtractDetailVO
     */
    public java.util.Enumeration enumerateTaxExtractDetailVO()
    {
        return _taxExtractDetailVOList.elements();
    } //-- java.util.Enumeration enumerateTaxExtractDetailVO() 

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
        
        if (obj instanceof TaxExtractsVO) {
        
            TaxExtractsVO temp = (TaxExtractsVO)obj;
            if (this._taxExtractDetailVOList != null) {
                if (temp._taxExtractDetailVOList == null) return false;
                else if (!(this._taxExtractDetailVOList.equals(temp._taxExtractDetailVOList))) 
                    return false;
            }
            else if (temp._taxExtractDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getTaxExtractDetailVO
     * 
     * @param index
     */
    public edit.common.vo.TaxExtractDetailVO getTaxExtractDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxExtractDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TaxExtractDetailVO) _taxExtractDetailVOList.elementAt(index);
    } //-- edit.common.vo.TaxExtractDetailVO getTaxExtractDetailVO(int) 

    /**
     * Method getTaxExtractDetailVO
     */
    public edit.common.vo.TaxExtractDetailVO[] getTaxExtractDetailVO()
    {
        int size = _taxExtractDetailVOList.size();
        edit.common.vo.TaxExtractDetailVO[] mArray = new edit.common.vo.TaxExtractDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TaxExtractDetailVO) _taxExtractDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TaxExtractDetailVO[] getTaxExtractDetailVO() 

    /**
     * Method getTaxExtractDetailVOCount
     */
    public int getTaxExtractDetailVOCount()
    {
        return _taxExtractDetailVOList.size();
    } //-- int getTaxExtractDetailVOCount() 

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
     * Method removeAllTaxExtractDetailVO
     */
    public void removeAllTaxExtractDetailVO()
    {
        _taxExtractDetailVOList.removeAllElements();
    } //-- void removeAllTaxExtractDetailVO() 

    /**
     * Method removeTaxExtractDetailVO
     * 
     * @param index
     */
    public edit.common.vo.TaxExtractDetailVO removeTaxExtractDetailVO(int index)
    {
        java.lang.Object obj = _taxExtractDetailVOList.elementAt(index);
        _taxExtractDetailVOList.removeElementAt(index);
        return (edit.common.vo.TaxExtractDetailVO) obj;
    } //-- edit.common.vo.TaxExtractDetailVO removeTaxExtractDetailVO(int) 

    /**
     * Method setTaxExtractDetailVO
     * 
     * @param index
     * @param vTaxExtractDetailVO
     */
    public void setTaxExtractDetailVO(int index, edit.common.vo.TaxExtractDetailVO vTaxExtractDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxExtractDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTaxExtractDetailVO.setParentVO(this.getClass(), this);
        _taxExtractDetailVOList.setElementAt(vTaxExtractDetailVO, index);
    } //-- void setTaxExtractDetailVO(int, edit.common.vo.TaxExtractDetailVO) 

    /**
     * Method setTaxExtractDetailVO
     * 
     * @param taxExtractDetailVOArray
     */
    public void setTaxExtractDetailVO(edit.common.vo.TaxExtractDetailVO[] taxExtractDetailVOArray)
    {
        //-- copy array
        _taxExtractDetailVOList.removeAllElements();
        for (int i = 0; i < taxExtractDetailVOArray.length; i++) {
            taxExtractDetailVOArray[i].setParentVO(this.getClass(), this);
            _taxExtractDetailVOList.addElement(taxExtractDetailVOArray[i]);
        }
    } //-- void setTaxExtractDetailVO(edit.common.vo.TaxExtractDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TaxExtractsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TaxExtractsVO) Unmarshaller.unmarshal(edit.common.vo.TaxExtractsVO.class, reader);
    } //-- edit.common.vo.TaxExtractsVO unmarshal(java.io.Reader) 

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
