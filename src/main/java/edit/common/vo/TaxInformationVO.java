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
 * Class TaxInformationVO.
 * 
 * @version $Revision$ $Date$
 */
public class TaxInformationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _taxInformationPK
     */
    private long _taxInformationPK;

    /**
     * keeps track of state for field: _taxInformationPK
     */
    private boolean _has_taxInformationPK;

    /**
     * Field _clientDetailFK
     */
    private long _clientDetailFK;

    /**
     * keeps track of state for field: _clientDetailFK
     */
    private boolean _has_clientDetailFK;

    /**
     * Field _taxIdTypeCT
     */
    private java.lang.String _taxIdTypeCT;

    /**
     * Field _proofOfAgeIndCT
     */
    private java.lang.String _proofOfAgeIndCT;

    /**
     * Field _maritalStatusCT
     */
    private java.lang.String _maritalStatusCT;

    /**
     * Field _stateOfBirthCT
     */
    private java.lang.String _stateOfBirthCT;

    /**
     * Field _countryOfBirthCT
     */
    private java.lang.String _countryOfBirthCT;

    /**
     * Field _citizenshipIndCT
     */
    private java.lang.String _citizenshipIndCT;

    /**
     * Field _taxProfileVOList
     */
    private java.util.Vector _taxProfileVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TaxInformationVO() {
        super();
        _taxProfileVOList = new Vector();
    } //-- edit.common.vo.TaxInformationVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addTaxProfileVO
     * 
     * @param vTaxProfileVO
     */
    public void addTaxProfileVO(edit.common.vo.TaxProfileVO vTaxProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxProfileVO.setParentVO(this.getClass(), this);
        _taxProfileVOList.addElement(vTaxProfileVO);
    } //-- void addTaxProfileVO(edit.common.vo.TaxProfileVO) 

    /**
     * Method addTaxProfileVO
     * 
     * @param index
     * @param vTaxProfileVO
     */
    public void addTaxProfileVO(int index, edit.common.vo.TaxProfileVO vTaxProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vTaxProfileVO.setParentVO(this.getClass(), this);
        _taxProfileVOList.insertElementAt(vTaxProfileVO, index);
    } //-- void addTaxProfileVO(int, edit.common.vo.TaxProfileVO) 

    /**
     * Method enumerateTaxProfileVO
     */
    public java.util.Enumeration enumerateTaxProfileVO()
    {
        return _taxProfileVOList.elements();
    } //-- java.util.Enumeration enumerateTaxProfileVO() 

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
        
        if (obj instanceof TaxInformationVO) {
        
            TaxInformationVO temp = (TaxInformationVO)obj;
            if (this._taxInformationPK != temp._taxInformationPK)
                return false;
            if (this._has_taxInformationPK != temp._has_taxInformationPK)
                return false;
            if (this._clientDetailFK != temp._clientDetailFK)
                return false;
            if (this._has_clientDetailFK != temp._has_clientDetailFK)
                return false;
            if (this._taxIdTypeCT != null) {
                if (temp._taxIdTypeCT == null) return false;
                else if (!(this._taxIdTypeCT.equals(temp._taxIdTypeCT))) 
                    return false;
            }
            else if (temp._taxIdTypeCT != null)
                return false;
            if (this._proofOfAgeIndCT != null) {
                if (temp._proofOfAgeIndCT == null) return false;
                else if (!(this._proofOfAgeIndCT.equals(temp._proofOfAgeIndCT))) 
                    return false;
            }
            else if (temp._proofOfAgeIndCT != null)
                return false;
            if (this._maritalStatusCT != null) {
                if (temp._maritalStatusCT == null) return false;
                else if (!(this._maritalStatusCT.equals(temp._maritalStatusCT))) 
                    return false;
            }
            else if (temp._maritalStatusCT != null)
                return false;
            if (this._stateOfBirthCT != null) {
                if (temp._stateOfBirthCT == null) return false;
                else if (!(this._stateOfBirthCT.equals(temp._stateOfBirthCT))) 
                    return false;
            }
            else if (temp._stateOfBirthCT != null)
                return false;
            if (this._countryOfBirthCT != null) {
                if (temp._countryOfBirthCT == null) return false;
                else if (!(this._countryOfBirthCT.equals(temp._countryOfBirthCT))) 
                    return false;
            }
            else if (temp._countryOfBirthCT != null)
                return false;
            if (this._citizenshipIndCT != null) {
                if (temp._citizenshipIndCT == null) return false;
                else if (!(this._citizenshipIndCT.equals(temp._citizenshipIndCT))) 
                    return false;
            }
            else if (temp._citizenshipIndCT != null)
                return false;
            if (this._taxProfileVOList != null) {
                if (temp._taxProfileVOList == null) return false;
                else if (!(this._taxProfileVOList.equals(temp._taxProfileVOList))) 
                    return false;
            }
            else if (temp._taxProfileVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCitizenshipIndCTReturns the value of field
     * 'citizenshipIndCT'.
     * 
     * @return the value of field 'citizenshipIndCT'.
     */
    public java.lang.String getCitizenshipIndCT()
    {
        return this._citizenshipIndCT;
    } //-- java.lang.String getCitizenshipIndCT() 

    /**
     * Method getClientDetailFKReturns the value of field
     * 'clientDetailFK'.
     * 
     * @return the value of field 'clientDetailFK'.
     */
    public long getClientDetailFK()
    {
        return this._clientDetailFK;
    } //-- long getClientDetailFK() 

    /**
     * Method getCountryOfBirthCTReturns the value of field
     * 'countryOfBirthCT'.
     * 
     * @return the value of field 'countryOfBirthCT'.
     */
    public java.lang.String getCountryOfBirthCT()
    {
        return this._countryOfBirthCT;
    } //-- java.lang.String getCountryOfBirthCT() 

    /**
     * Method getMaritalStatusCTReturns the value of field
     * 'maritalStatusCT'.
     * 
     * @return the value of field 'maritalStatusCT'.
     */
    public java.lang.String getMaritalStatusCT()
    {
        return this._maritalStatusCT;
    } //-- java.lang.String getMaritalStatusCT() 

    /**
     * Method getProofOfAgeIndCTReturns the value of field
     * 'proofOfAgeIndCT'.
     * 
     * @return the value of field 'proofOfAgeIndCT'.
     */
    public java.lang.String getProofOfAgeIndCT()
    {
        return this._proofOfAgeIndCT;
    } //-- java.lang.String getProofOfAgeIndCT() 

    /**
     * Method getStateOfBirthCTReturns the value of field
     * 'stateOfBirthCT'.
     * 
     * @return the value of field 'stateOfBirthCT'.
     */
    public java.lang.String getStateOfBirthCT()
    {
        return this._stateOfBirthCT;
    } //-- java.lang.String getStateOfBirthCT() 

    /**
     * Method getTaxIdTypeCTReturns the value of field
     * 'taxIdTypeCT'.
     * 
     * @return the value of field 'taxIdTypeCT'.
     */
    public java.lang.String getTaxIdTypeCT()
    {
        return this._taxIdTypeCT;
    } //-- java.lang.String getTaxIdTypeCT() 

    /**
     * Method getTaxInformationPKReturns the value of field
     * 'taxInformationPK'.
     * 
     * @return the value of field 'taxInformationPK'.
     */
    public long getTaxInformationPK()
    {
        return this._taxInformationPK;
    } //-- long getTaxInformationPK() 

    /**
     * Method getTaxProfileVO
     * 
     * @param index
     */
    public edit.common.vo.TaxProfileVO getTaxProfileVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.TaxProfileVO) _taxProfileVOList.elementAt(index);
    } //-- edit.common.vo.TaxProfileVO getTaxProfileVO(int) 

    /**
     * Method getTaxProfileVO
     */
    public edit.common.vo.TaxProfileVO[] getTaxProfileVO()
    {
        int size = _taxProfileVOList.size();
        edit.common.vo.TaxProfileVO[] mArray = new edit.common.vo.TaxProfileVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.TaxProfileVO) _taxProfileVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.TaxProfileVO[] getTaxProfileVO() 

    /**
     * Method getTaxProfileVOCount
     */
    public int getTaxProfileVOCount()
    {
        return _taxProfileVOList.size();
    } //-- int getTaxProfileVOCount() 

    /**
     * Method hasClientDetailFK
     */
    public boolean hasClientDetailFK()
    {
        return this._has_clientDetailFK;
    } //-- boolean hasClientDetailFK() 

    /**
     * Method hasTaxInformationPK
     */
    public boolean hasTaxInformationPK()
    {
        return this._has_taxInformationPK;
    } //-- boolean hasTaxInformationPK() 

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
     * Method removeAllTaxProfileVO
     */
    public void removeAllTaxProfileVO()
    {
        _taxProfileVOList.removeAllElements();
    } //-- void removeAllTaxProfileVO() 

    /**
     * Method removeTaxProfileVO
     * 
     * @param index
     */
    public edit.common.vo.TaxProfileVO removeTaxProfileVO(int index)
    {
        java.lang.Object obj = _taxProfileVOList.elementAt(index);
        _taxProfileVOList.removeElementAt(index);
        return (edit.common.vo.TaxProfileVO) obj;
    } //-- edit.common.vo.TaxProfileVO removeTaxProfileVO(int) 

    /**
     * Method setCitizenshipIndCTSets the value of field
     * 'citizenshipIndCT'.
     * 
     * @param citizenshipIndCT the value of field 'citizenshipIndCT'
     */
    public void setCitizenshipIndCT(java.lang.String citizenshipIndCT)
    {
        this._citizenshipIndCT = citizenshipIndCT;
        
        super.setVoChanged(true);
    } //-- void setCitizenshipIndCT(java.lang.String) 

    /**
     * Method setClientDetailFKSets the value of field
     * 'clientDetailFK'.
     * 
     * @param clientDetailFK the value of field 'clientDetailFK'.
     */
    public void setClientDetailFK(long clientDetailFK)
    {
        this._clientDetailFK = clientDetailFK;
        
        super.setVoChanged(true);
        this._has_clientDetailFK = true;
    } //-- void setClientDetailFK(long) 

    /**
     * Method setCountryOfBirthCTSets the value of field
     * 'countryOfBirthCT'.
     * 
     * @param countryOfBirthCT the value of field 'countryOfBirthCT'
     */
    public void setCountryOfBirthCT(java.lang.String countryOfBirthCT)
    {
        this._countryOfBirthCT = countryOfBirthCT;
        
        super.setVoChanged(true);
    } //-- void setCountryOfBirthCT(java.lang.String) 

    /**
     * Method setMaritalStatusCTSets the value of field
     * 'maritalStatusCT'.
     * 
     * @param maritalStatusCT the value of field 'maritalStatusCT'.
     */
    public void setMaritalStatusCT(java.lang.String maritalStatusCT)
    {
        this._maritalStatusCT = maritalStatusCT;
        
        super.setVoChanged(true);
    } //-- void setMaritalStatusCT(java.lang.String) 

    /**
     * Method setProofOfAgeIndCTSets the value of field
     * 'proofOfAgeIndCT'.
     * 
     * @param proofOfAgeIndCT the value of field 'proofOfAgeIndCT'.
     */
    public void setProofOfAgeIndCT(java.lang.String proofOfAgeIndCT)
    {
        this._proofOfAgeIndCT = proofOfAgeIndCT;
        
        super.setVoChanged(true);
    } //-- void setProofOfAgeIndCT(java.lang.String) 

    /**
     * Method setStateOfBirthCTSets the value of field
     * 'stateOfBirthCT'.
     * 
     * @param stateOfBirthCT the value of field 'stateOfBirthCT'.
     */
    public void setStateOfBirthCT(java.lang.String stateOfBirthCT)
    {
        this._stateOfBirthCT = stateOfBirthCT;
        
        super.setVoChanged(true);
    } //-- void setStateOfBirthCT(java.lang.String) 

    /**
     * Method setTaxIdTypeCTSets the value of field 'taxIdTypeCT'.
     * 
     * @param taxIdTypeCT the value of field 'taxIdTypeCT'.
     */
    public void setTaxIdTypeCT(java.lang.String taxIdTypeCT)
    {
        this._taxIdTypeCT = taxIdTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTaxIdTypeCT(java.lang.String) 

    /**
     * Method setTaxInformationPKSets the value of field
     * 'taxInformationPK'.
     * 
     * @param taxInformationPK the value of field 'taxInformationPK'
     */
    public void setTaxInformationPK(long taxInformationPK)
    {
        this._taxInformationPK = taxInformationPK;
        
        super.setVoChanged(true);
        this._has_taxInformationPK = true;
    } //-- void setTaxInformationPK(long) 

    /**
     * Method setTaxProfileVO
     * 
     * @param index
     * @param vTaxProfileVO
     */
    public void setTaxProfileVO(int index, edit.common.vo.TaxProfileVO vTaxProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _taxProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vTaxProfileVO.setParentVO(this.getClass(), this);
        _taxProfileVOList.setElementAt(vTaxProfileVO, index);
    } //-- void setTaxProfileVO(int, edit.common.vo.TaxProfileVO) 

    /**
     * Method setTaxProfileVO
     * 
     * @param taxProfileVOArray
     */
    public void setTaxProfileVO(edit.common.vo.TaxProfileVO[] taxProfileVOArray)
    {
        //-- copy array
        _taxProfileVOList.removeAllElements();
        for (int i = 0; i < taxProfileVOArray.length; i++) {
            taxProfileVOArray[i].setParentVO(this.getClass(), this);
            _taxProfileVOList.addElement(taxProfileVOArray[i]);
        }
    } //-- void setTaxProfileVO(edit.common.vo.TaxProfileVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TaxInformationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TaxInformationVO) Unmarshaller.unmarshal(edit.common.vo.TaxInformationVO.class, reader);
    } //-- edit.common.vo.TaxInformationVO unmarshal(java.io.Reader) 

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
