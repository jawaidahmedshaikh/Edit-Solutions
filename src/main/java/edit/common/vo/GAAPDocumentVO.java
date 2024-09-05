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
 * Document to be fed into PRASE for the generation of GAAP
 * Reserves 
 * 
 * @version $Revision$ $Date$
 */
public class GAAPDocumentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _GAAPDocumentPK
     */
    private long _GAAPDocumentPK;

    /**
     * keeps track of state for field: _GAAPDocumentPK
     */
    private boolean _has_GAAPDocumentPK;

    /**
     * Field _productStructureVO
     */
    private edit.common.vo.ProductStructureVO _productStructureVO;

    /**
     * Field _policyEffectiveDate
     */
    private java.lang.String _policyEffectiveDate;

    /**
     * Field _trxEffectiveDate
     */
    private java.lang.String _trxEffectiveDate;

    /**
     * Field _ownerIssueAge
     */
    private int _ownerIssueAge;

    /**
     * keeps track of state for field: _ownerIssueAge
     */
    private boolean _has_ownerIssueAge;

    /**
     * Field _ownerGender
     */
    private java.lang.String _ownerGender;

    /**
     * Field _issueState
     */
    private java.lang.String _issueState;

    /**
     * Field _fundTypeIDVOList
     */
    private java.util.Vector _fundTypeIDVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public GAAPDocumentVO() {
        super();
        _fundTypeIDVOList = new Vector();
    } //-- edit.common.vo.GAAPDocumentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFundTypeIDVO
     * 
     * @param vFundTypeIDVO
     */
    public void addFundTypeIDVO(edit.common.vo.FundTypeIDVO vFundTypeIDVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFundTypeIDVO.setParentVO(this.getClass(), this);
        _fundTypeIDVOList.addElement(vFundTypeIDVO);
    } //-- void addFundTypeIDVO(edit.common.vo.FundTypeIDVO) 

    /**
     * Method addFundTypeIDVO
     * 
     * @param index
     * @param vFundTypeIDVO
     */
    public void addFundTypeIDVO(int index, edit.common.vo.FundTypeIDVO vFundTypeIDVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFundTypeIDVO.setParentVO(this.getClass(), this);
        _fundTypeIDVOList.insertElementAt(vFundTypeIDVO, index);
    } //-- void addFundTypeIDVO(int, edit.common.vo.FundTypeIDVO) 

    /**
     * Method enumerateFundTypeIDVO
     */
    public java.util.Enumeration enumerateFundTypeIDVO()
    {
        return _fundTypeIDVOList.elements();
    } //-- java.util.Enumeration enumerateFundTypeIDVO() 

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
        
        if (obj instanceof GAAPDocumentVO) {
        
            GAAPDocumentVO temp = (GAAPDocumentVO)obj;
            if (this._GAAPDocumentPK != temp._GAAPDocumentPK)
                return false;
            if (this._has_GAAPDocumentPK != temp._has_GAAPDocumentPK)
                return false;
            if (this._productStructureVO != null) {
                if (temp._productStructureVO == null) return false;
                else if (!(this._productStructureVO.equals(temp._productStructureVO))) 
                    return false;
            }
            else if (temp._productStructureVO != null)
                return false;
            if (this._policyEffectiveDate != null) {
                if (temp._policyEffectiveDate == null) return false;
                else if (!(this._policyEffectiveDate.equals(temp._policyEffectiveDate))) 
                    return false;
            }
            else if (temp._policyEffectiveDate != null)
                return false;
            if (this._trxEffectiveDate != null) {
                if (temp._trxEffectiveDate == null) return false;
                else if (!(this._trxEffectiveDate.equals(temp._trxEffectiveDate))) 
                    return false;
            }
            else if (temp._trxEffectiveDate != null)
                return false;
            if (this._ownerIssueAge != temp._ownerIssueAge)
                return false;
            if (this._has_ownerIssueAge != temp._has_ownerIssueAge)
                return false;
            if (this._ownerGender != null) {
                if (temp._ownerGender == null) return false;
                else if (!(this._ownerGender.equals(temp._ownerGender))) 
                    return false;
            }
            else if (temp._ownerGender != null)
                return false;
            if (this._issueState != null) {
                if (temp._issueState == null) return false;
                else if (!(this._issueState.equals(temp._issueState))) 
                    return false;
            }
            else if (temp._issueState != null)
                return false;
            if (this._fundTypeIDVOList != null) {
                if (temp._fundTypeIDVOList == null) return false;
                else if (!(this._fundTypeIDVOList.equals(temp._fundTypeIDVOList))) 
                    return false;
            }
            else if (temp._fundTypeIDVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFundTypeIDVO
     * 
     * @param index
     */
    public edit.common.vo.FundTypeIDVO getFundTypeIDVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _fundTypeIDVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FundTypeIDVO) _fundTypeIDVOList.elementAt(index);
    } //-- edit.common.vo.FundTypeIDVO getFundTypeIDVO(int) 

    /**
     * Method getFundTypeIDVO
     */
    public edit.common.vo.FundTypeIDVO[] getFundTypeIDVO()
    {
        int size = _fundTypeIDVOList.size();
        edit.common.vo.FundTypeIDVO[] mArray = new edit.common.vo.FundTypeIDVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FundTypeIDVO) _fundTypeIDVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FundTypeIDVO[] getFundTypeIDVO() 

    /**
     * Method getFundTypeIDVOCount
     */
    public int getFundTypeIDVOCount()
    {
        return _fundTypeIDVOList.size();
    } //-- int getFundTypeIDVOCount() 

    /**
     * Method getGAAPDocumentPKReturns the value of field
     * 'GAAPDocumentPK'.
     * 
     * @return the value of field 'GAAPDocumentPK'.
     */
    public long getGAAPDocumentPK()
    {
        return this._GAAPDocumentPK;
    } //-- long getGAAPDocumentPK() 

    /**
     * Method getIssueStateReturns the value of field 'issueState'.
     * 
     * @return the value of field 'issueState'.
     */
    public java.lang.String getIssueState()
    {
        return this._issueState;
    } //-- java.lang.String getIssueState() 

    /**
     * Method getOwnerGenderReturns the value of field
     * 'ownerGender'.
     * 
     * @return the value of field 'ownerGender'.
     */
    public java.lang.String getOwnerGender()
    {
        return this._ownerGender;
    } //-- java.lang.String getOwnerGender() 

    /**
     * Method getOwnerIssueAgeReturns the value of field
     * 'ownerIssueAge'.
     * 
     * @return the value of field 'ownerIssueAge'.
     */
    public int getOwnerIssueAge()
    {
        return this._ownerIssueAge;
    } //-- int getOwnerIssueAge() 

    /**
     * Method getPolicyEffectiveDateReturns the value of field
     * 'policyEffectiveDate'.
     * 
     * @return the value of field 'policyEffectiveDate'.
     */
    public java.lang.String getPolicyEffectiveDate()
    {
        return this._policyEffectiveDate;
    } //-- java.lang.String getPolicyEffectiveDate() 

    /**
     * Method getProductStructureVOReturns the value of field
     * 'productStructureVO'.
     * 
     * @return the value of field 'productStructureVO'.
     */
    public edit.common.vo.ProductStructureVO getProductStructureVO()
    {
        return this._productStructureVO;
    } //-- edit.common.vo.ProductStructureVO getProductStructureVO() 

    /**
     * Method getTrxEffectiveDateReturns the value of field
     * 'trxEffectiveDate'.
     * 
     * @return the value of field 'trxEffectiveDate'.
     */
    public java.lang.String getTrxEffectiveDate()
    {
        return this._trxEffectiveDate;
    } //-- java.lang.String getTrxEffectiveDate() 

    /**
     * Method hasGAAPDocumentPK
     */
    public boolean hasGAAPDocumentPK()
    {
        return this._has_GAAPDocumentPK;
    } //-- boolean hasGAAPDocumentPK() 

    /**
     * Method hasOwnerIssueAge
     */
    public boolean hasOwnerIssueAge()
    {
        return this._has_ownerIssueAge;
    } //-- boolean hasOwnerIssueAge() 

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
     * Method removeAllFundTypeIDVO
     */
    public void removeAllFundTypeIDVO()
    {
        _fundTypeIDVOList.removeAllElements();
    } //-- void removeAllFundTypeIDVO() 

    /**
     * Method removeFundTypeIDVO
     * 
     * @param index
     */
    public edit.common.vo.FundTypeIDVO removeFundTypeIDVO(int index)
    {
        java.lang.Object obj = _fundTypeIDVOList.elementAt(index);
        _fundTypeIDVOList.removeElementAt(index);
        return (edit.common.vo.FundTypeIDVO) obj;
    } //-- edit.common.vo.FundTypeIDVO removeFundTypeIDVO(int) 

    /**
     * Method setFundTypeIDVO
     * 
     * @param index
     * @param vFundTypeIDVO
     */
    public void setFundTypeIDVO(int index, edit.common.vo.FundTypeIDVO vFundTypeIDVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _fundTypeIDVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFundTypeIDVO.setParentVO(this.getClass(), this);
        _fundTypeIDVOList.setElementAt(vFundTypeIDVO, index);
    } //-- void setFundTypeIDVO(int, edit.common.vo.FundTypeIDVO) 

    /**
     * Method setFundTypeIDVO
     * 
     * @param fundTypeIDVOArray
     */
    public void setFundTypeIDVO(edit.common.vo.FundTypeIDVO[] fundTypeIDVOArray)
    {
        //-- copy array
        _fundTypeIDVOList.removeAllElements();
        for (int i = 0; i < fundTypeIDVOArray.length; i++) {
            fundTypeIDVOArray[i].setParentVO(this.getClass(), this);
            _fundTypeIDVOList.addElement(fundTypeIDVOArray[i]);
        }
    } //-- void setFundTypeIDVO(edit.common.vo.FundTypeIDVO) 

    /**
     * Method setGAAPDocumentPKSets the value of field
     * 'GAAPDocumentPK'.
     * 
     * @param GAAPDocumentPK the value of field 'GAAPDocumentPK'.
     */
    public void setGAAPDocumentPK(long GAAPDocumentPK)
    {
        this._GAAPDocumentPK = GAAPDocumentPK;
        
        super.setVoChanged(true);
        this._has_GAAPDocumentPK = true;
    } //-- void setGAAPDocumentPK(long) 

    /**
     * Method setIssueStateSets the value of field 'issueState'.
     * 
     * @param issueState the value of field 'issueState'.
     */
    public void setIssueState(java.lang.String issueState)
    {
        this._issueState = issueState;
        
        super.setVoChanged(true);
    } //-- void setIssueState(java.lang.String) 

    /**
     * Method setOwnerGenderSets the value of field 'ownerGender'.
     * 
     * @param ownerGender the value of field 'ownerGender'.
     */
    public void setOwnerGender(java.lang.String ownerGender)
    {
        this._ownerGender = ownerGender;
        
        super.setVoChanged(true);
    } //-- void setOwnerGender(java.lang.String) 

    /**
     * Method setOwnerIssueAgeSets the value of field
     * 'ownerIssueAge'.
     * 
     * @param ownerIssueAge the value of field 'ownerIssueAge'.
     */
    public void setOwnerIssueAge(int ownerIssueAge)
    {
        this._ownerIssueAge = ownerIssueAge;
        
        super.setVoChanged(true);
        this._has_ownerIssueAge = true;
    } //-- void setOwnerIssueAge(int) 

    /**
     * Method setPolicyEffectiveDateSets the value of field
     * 'policyEffectiveDate'.
     * 
     * @param policyEffectiveDate the value of field
     * 'policyEffectiveDate'.
     */
    public void setPolicyEffectiveDate(java.lang.String policyEffectiveDate)
    {
        this._policyEffectiveDate = policyEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setPolicyEffectiveDate(java.lang.String) 

    /**
     * Method setProductStructureVOSets the value of field
     * 'productStructureVO'.
     * 
     * @param productStructureVO the value of field
     * 'productStructureVO'.
     */
    public void setProductStructureVO(edit.common.vo.ProductStructureVO productStructureVO)
    {
        this._productStructureVO = productStructureVO;
    } //-- void setProductStructureVO(edit.common.vo.ProductStructureVO) 

    /**
     * Method setTrxEffectiveDateSets the value of field
     * 'trxEffectiveDate'.
     * 
     * @param trxEffectiveDate the value of field 'trxEffectiveDate'
     */
    public void setTrxEffectiveDate(java.lang.String trxEffectiveDate)
    {
        this._trxEffectiveDate = trxEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setTrxEffectiveDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.GAAPDocumentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.GAAPDocumentVO) Unmarshaller.unmarshal(edit.common.vo.GAAPDocumentVO.class, reader);
    } //-- edit.common.vo.GAAPDocumentVO unmarshal(java.io.Reader) 

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
