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
public class AccountingExtractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _accountingDetailVOList
     */
    private java.util.Vector _accountingDetailVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AccountingExtractVO() {
        super();
        _accountingDetailVOList = new Vector();
    } //-- edit.common.vo.AccountingExtractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAccountingDetailVO
     * 
     * @param vAccountingDetailVO
     */
    public void addAccountingDetailVO(edit.common.vo.AccountingDetailVO vAccountingDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAccountingDetailVO.setParentVO(this.getClass(), this);
        _accountingDetailVOList.addElement(vAccountingDetailVO);
    } //-- void addAccountingDetailVO(edit.common.vo.AccountingDetailVO) 

    /**
     * Method addAccountingDetailVO
     * 
     * @param index
     * @param vAccountingDetailVO
     */
    public void addAccountingDetailVO(int index, edit.common.vo.AccountingDetailVO vAccountingDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAccountingDetailVO.setParentVO(this.getClass(), this);
        _accountingDetailVOList.insertElementAt(vAccountingDetailVO, index);
    } //-- void addAccountingDetailVO(int, edit.common.vo.AccountingDetailVO) 

    /**
     * Method enumerateAccountingDetailVO
     */
    public java.util.Enumeration enumerateAccountingDetailVO()
    {
        return _accountingDetailVOList.elements();
    } //-- java.util.Enumeration enumerateAccountingDetailVO() 

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
        
        if (obj instanceof AccountingExtractVO) {
        
            AccountingExtractVO temp = (AccountingExtractVO)obj;
            if (this._accountingDetailVOList != null) {
                if (temp._accountingDetailVOList == null) return false;
                else if (!(this._accountingDetailVOList.equals(temp._accountingDetailVOList))) 
                    return false;
            }
            else if (temp._accountingDetailVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountingDetailVO
     * 
     * @param index
     */
    public edit.common.vo.AccountingDetailVO getAccountingDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _accountingDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AccountingDetailVO) _accountingDetailVOList.elementAt(index);
    } //-- edit.common.vo.AccountingDetailVO getAccountingDetailVO(int) 

    /**
     * Method getAccountingDetailVO
     */
    public edit.common.vo.AccountingDetailVO[] getAccountingDetailVO()
    {
        int size = _accountingDetailVOList.size();
        edit.common.vo.AccountingDetailVO[] mArray = new edit.common.vo.AccountingDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AccountingDetailVO) _accountingDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AccountingDetailVO[] getAccountingDetailVO() 

    /**
     * Method getAccountingDetailVOCount
     */
    public int getAccountingDetailVOCount()
    {
        return _accountingDetailVOList.size();
    } //-- int getAccountingDetailVOCount() 

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
     * Method removeAccountingDetailVO
     * 
     * @param index
     */
    public edit.common.vo.AccountingDetailVO removeAccountingDetailVO(int index)
    {
        java.lang.Object obj = _accountingDetailVOList.elementAt(index);
        _accountingDetailVOList.removeElementAt(index);
        return (edit.common.vo.AccountingDetailVO) obj;
    } //-- edit.common.vo.AccountingDetailVO removeAccountingDetailVO(int) 

    /**
     * Method removeAllAccountingDetailVO
     */
    public void removeAllAccountingDetailVO()
    {
        _accountingDetailVOList.removeAllElements();
    } //-- void removeAllAccountingDetailVO() 

    /**
     * Method setAccountingDetailVO
     * 
     * @param index
     * @param vAccountingDetailVO
     */
    public void setAccountingDetailVO(int index, edit.common.vo.AccountingDetailVO vAccountingDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _accountingDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAccountingDetailVO.setParentVO(this.getClass(), this);
        _accountingDetailVOList.setElementAt(vAccountingDetailVO, index);
    } //-- void setAccountingDetailVO(int, edit.common.vo.AccountingDetailVO) 

    /**
     * Method setAccountingDetailVO
     * 
     * @param accountingDetailVOArray
     */
    public void setAccountingDetailVO(edit.common.vo.AccountingDetailVO[] accountingDetailVOArray)
    {
        //-- copy array
        _accountingDetailVOList.removeAllElements();
        for (int i = 0; i < accountingDetailVOArray.length; i++) {
            accountingDetailVOArray[i].setParentVO(this.getClass(), this);
            _accountingDetailVOList.addElement(accountingDetailVOArray[i]);
        }
    } //-- void setAccountingDetailVO(edit.common.vo.AccountingDetailVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AccountingExtractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AccountingExtractVO) Unmarshaller.unmarshal(edit.common.vo.AccountingExtractVO.class, reader);
    } //-- edit.common.vo.AccountingExtractVO unmarshal(java.io.Reader) 

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
