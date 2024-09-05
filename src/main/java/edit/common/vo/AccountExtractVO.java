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
public class AccountExtractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _accountExtractPK
     */
    private long _accountExtractPK;

    /**
     * keeps track of state for field: _accountExtractPK
     */
    private boolean _has_accountExtractPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _accountEffectVOList
     */
    private java.util.Vector _accountEffectVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AccountExtractVO() {
        super();
        _accountEffectVOList = new Vector();
    } //-- edit.common.vo.AccountExtractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAccountEffectVO
     * 
     * @param vAccountEffectVO
     */
    public void addAccountEffectVO(edit.common.vo.AccountEffectVO vAccountEffectVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAccountEffectVO.setParentVO(this.getClass(), this);
        _accountEffectVOList.addElement(vAccountEffectVO);
    } //-- void addAccountEffectVO(edit.common.vo.AccountEffectVO) 

    /**
     * Method addAccountEffectVO
     * 
     * @param index
     * @param vAccountEffectVO
     */
    public void addAccountEffectVO(int index, edit.common.vo.AccountEffectVO vAccountEffectVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAccountEffectVO.setParentVO(this.getClass(), this);
        _accountEffectVOList.insertElementAt(vAccountEffectVO, index);
    } //-- void addAccountEffectVO(int, edit.common.vo.AccountEffectVO) 

    /**
     * Method enumerateAccountEffectVO
     */
    public java.util.Enumeration enumerateAccountEffectVO()
    {
        return _accountEffectVOList.elements();
    } //-- java.util.Enumeration enumerateAccountEffectVO() 

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
        
        if (obj instanceof AccountExtractVO) {
        
            AccountExtractVO temp = (AccountExtractVO)obj;
            if (this._accountExtractPK != temp._accountExtractPK)
                return false;
            if (this._has_accountExtractPK != temp._has_accountExtractPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._accountEffectVOList != null) {
                if (temp._accountEffectVOList == null) return false;
                else if (!(this._accountEffectVOList.equals(temp._accountEffectVOList))) 
                    return false;
            }
            else if (temp._accountEffectVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountEffectVO
     * 
     * @param index
     */
    public edit.common.vo.AccountEffectVO getAccountEffectVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _accountEffectVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AccountEffectVO) _accountEffectVOList.elementAt(index);
    } //-- edit.common.vo.AccountEffectVO getAccountEffectVO(int) 

    /**
     * Method getAccountEffectVO
     */
    public edit.common.vo.AccountEffectVO[] getAccountEffectVO()
    {
        int size = _accountEffectVOList.size();
        edit.common.vo.AccountEffectVO[] mArray = new edit.common.vo.AccountEffectVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AccountEffectVO) _accountEffectVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AccountEffectVO[] getAccountEffectVO() 

    /**
     * Method getAccountEffectVOCount
     */
    public int getAccountEffectVOCount()
    {
        return _accountEffectVOList.size();
    } //-- int getAccountEffectVOCount() 

    /**
     * Method getAccountExtractPKReturns the value of field
     * 'accountExtractPK'.
     * 
     * @return the value of field 'accountExtractPK'.
     */
    public long getAccountExtractPK()
    {
        return this._accountExtractPK;
    } //-- long getAccountExtractPK() 

    /**
     * Method getCompanyNameReturns the value of field
     * 'companyName'.
     * 
     * @return the value of field 'companyName'.
     */
    public java.lang.String getCompanyName()
    {
        return this._companyName;
    } //-- java.lang.String getCompanyName() 

    /**
     * Method getSegmentFKReturns the value of field 'segmentFK'.
     * 
     * @return the value of field 'segmentFK'.
     */
    public long getSegmentFK()
    {
        return this._segmentFK;
    } //-- long getSegmentFK() 

    /**
     * Method hasAccountExtractPK
     */
    public boolean hasAccountExtractPK()
    {
        return this._has_accountExtractPK;
    } //-- boolean hasAccountExtractPK() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

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
     * Method removeAccountEffectVO
     * 
     * @param index
     */
    public edit.common.vo.AccountEffectVO removeAccountEffectVO(int index)
    {
        java.lang.Object obj = _accountEffectVOList.elementAt(index);
        _accountEffectVOList.removeElementAt(index);
        return (edit.common.vo.AccountEffectVO) obj;
    } //-- edit.common.vo.AccountEffectVO removeAccountEffectVO(int) 

    /**
     * Method removeAllAccountEffectVO
     */
    public void removeAllAccountEffectVO()
    {
        _accountEffectVOList.removeAllElements();
    } //-- void removeAllAccountEffectVO() 

    /**
     * Method setAccountEffectVO
     * 
     * @param index
     * @param vAccountEffectVO
     */
    public void setAccountEffectVO(int index, edit.common.vo.AccountEffectVO vAccountEffectVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _accountEffectVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAccountEffectVO.setParentVO(this.getClass(), this);
        _accountEffectVOList.setElementAt(vAccountEffectVO, index);
    } //-- void setAccountEffectVO(int, edit.common.vo.AccountEffectVO) 

    /**
     * Method setAccountEffectVO
     * 
     * @param accountEffectVOArray
     */
    public void setAccountEffectVO(edit.common.vo.AccountEffectVO[] accountEffectVOArray)
    {
        //-- copy array
        _accountEffectVOList.removeAllElements();
        for (int i = 0; i < accountEffectVOArray.length; i++) {
            accountEffectVOArray[i].setParentVO(this.getClass(), this);
            _accountEffectVOList.addElement(accountEffectVOArray[i]);
        }
    } //-- void setAccountEffectVO(edit.common.vo.AccountEffectVO) 

    /**
     * Method setAccountExtractPKSets the value of field
     * 'accountExtractPK'.
     * 
     * @param accountExtractPK the value of field 'accountExtractPK'
     */
    public void setAccountExtractPK(long accountExtractPK)
    {
        this._accountExtractPK = accountExtractPK;
        
        super.setVoChanged(true);
        this._has_accountExtractPK = true;
    } //-- void setAccountExtractPK(long) 

    /**
     * Method setCompanyNameSets the value of field 'companyName'.
     * 
     * @param companyName the value of field 'companyName'.
     */
    public void setCompanyName(java.lang.String companyName)
    {
        this._companyName = companyName;
        
        super.setVoChanged(true);
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method setSegmentFKSets the value of field 'segmentFK'.
     * 
     * @param segmentFK the value of field 'segmentFK'.
     */
    public void setSegmentFK(long segmentFK)
    {
        this._segmentFK = segmentFK;
        
        super.setVoChanged(true);
        this._has_segmentFK = true;
    } //-- void setSegmentFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AccountExtractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AccountExtractVO) Unmarshaller.unmarshal(edit.common.vo.AccountExtractVO.class, reader);
    } //-- edit.common.vo.AccountExtractVO unmarshal(java.io.Reader) 

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
