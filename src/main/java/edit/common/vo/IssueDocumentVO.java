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
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Document to be fed into PRASE for the generation of Issue
 * Reports
 * 
 * @version $Revision$ $Date$
 */
public class IssueDocumentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The set of 4 clients (1. lowest level agent, 2. annuitant,
     * 3. owner, and 4. secondary owner) and their primary addresses
     */
    private java.util.Vector _issueClientVOList;

    /**
     * Field _segmentVO
     */
    private edit.common.vo.SegmentVO _segmentVO;

    /**
     * Field _productStructureVO
     */
    private edit.common.vo.ProductStructureVO _productStructureVO;

    /**
     * Field _freeLookDays
     */
    private int _freeLookDays;

    /**
     * keeps track of state for field: _freeLookDays
     */
    private boolean _has_freeLookDays;

    /**
     * Field _guarMinCashSurrenderRate
     */
    private java.math.BigDecimal _guarMinCashSurrenderRate;

    /**
     * The Investments at issue for a given contract.
     */
    private java.util.Vector _issueInvestmentVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _reinsuranceVOList;

    /**
     * Field _ownersPremiumTaxState
     */
    private java.lang.String _ownersPremiumTaxState;

    /**
     * Field _insuredResidenceState
     */
    private java.lang.String _insuredResidenceState;

    /**
     * Field _insuredGender
     */
    private java.lang.String _insuredGender;

    /**
     * Field _insuredDateOfBirth
     */
    private java.lang.String _insuredDateOfBirth;

    /**
     * Field _insuredIssueAge
     */
    private int _insuredIssueAge;

    /**
     * keeps track of state for field: _insuredIssueAge
     */
    private boolean _has_insuredIssueAge;

    /**
     * Field _insuredClass
     */
    private java.lang.String _insuredClass;

    /**
     * Field _insuredUnderwritingClass
     */
    private java.lang.String _insuredUnderwritingClass;

    /**
     * Field _sevenPayRate
     */
    private java.math.BigDecimal _sevenPayRate;


      //----------------/
     //- Constructors -/
    //----------------/

    public IssueDocumentVO() {
        super();
        _issueClientVOList = new Vector();
        _issueInvestmentVOList = new Vector();
        _reinsuranceVOList = new Vector();
    } //-- edit.common.vo.IssueDocumentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addIssueClientVO
     * 
     * @param vIssueClientVO
     */
    public void addIssueClientVO(edit.common.vo.IssueClientVO vIssueClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vIssueClientVO.setParentVO(this.getClass(), this);
        _issueClientVOList.addElement(vIssueClientVO);
    } //-- void addIssueClientVO(edit.common.vo.IssueClientVO) 

    /**
     * Method addIssueClientVO
     * 
     * @param index
     * @param vIssueClientVO
     */
    public void addIssueClientVO(int index, edit.common.vo.IssueClientVO vIssueClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vIssueClientVO.setParentVO(this.getClass(), this);
        _issueClientVOList.insertElementAt(vIssueClientVO, index);
    } //-- void addIssueClientVO(int, edit.common.vo.IssueClientVO) 

    /**
     * Method addIssueInvestmentVO
     * 
     * @param vIssueInvestmentVO
     */
    public void addIssueInvestmentVO(edit.common.vo.IssueInvestmentVO vIssueInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vIssueInvestmentVO.setParentVO(this.getClass(), this);
        _issueInvestmentVOList.addElement(vIssueInvestmentVO);
    } //-- void addIssueInvestmentVO(edit.common.vo.IssueInvestmentVO) 

    /**
     * Method addIssueInvestmentVO
     * 
     * @param index
     * @param vIssueInvestmentVO
     */
    public void addIssueInvestmentVO(int index, edit.common.vo.IssueInvestmentVO vIssueInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vIssueInvestmentVO.setParentVO(this.getClass(), this);
        _issueInvestmentVOList.insertElementAt(vIssueInvestmentVO, index);
    } //-- void addIssueInvestmentVO(int, edit.common.vo.IssueInvestmentVO) 

    /**
     * Method addReinsuranceVO
     * 
     * @param vReinsuranceVO
     */
    public void addReinsuranceVO(edit.common.vo.ReinsuranceVO vReinsuranceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceVO.setParentVO(this.getClass(), this);
        _reinsuranceVOList.addElement(vReinsuranceVO);
    } //-- void addReinsuranceVO(edit.common.vo.ReinsuranceVO) 

    /**
     * Method addReinsuranceVO
     * 
     * @param index
     * @param vReinsuranceVO
     */
    public void addReinsuranceVO(int index, edit.common.vo.ReinsuranceVO vReinsuranceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceVO.setParentVO(this.getClass(), this);
        _reinsuranceVOList.insertElementAt(vReinsuranceVO, index);
    } //-- void addReinsuranceVO(int, edit.common.vo.ReinsuranceVO) 

    /**
     * Method enumerateIssueClientVO
     */
    public java.util.Enumeration enumerateIssueClientVO()
    {
        return _issueClientVOList.elements();
    } //-- java.util.Enumeration enumerateIssueClientVO() 

    /**
     * Method enumerateIssueInvestmentVO
     */
    public java.util.Enumeration enumerateIssueInvestmentVO()
    {
        return _issueInvestmentVOList.elements();
    } //-- java.util.Enumeration enumerateIssueInvestmentVO() 

    /**
     * Method enumerateReinsuranceVO
     */
    public java.util.Enumeration enumerateReinsuranceVO()
    {
        return _reinsuranceVOList.elements();
    } //-- java.util.Enumeration enumerateReinsuranceVO() 

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
        
        if (obj instanceof IssueDocumentVO) {
        
            IssueDocumentVO temp = (IssueDocumentVO)obj;
            if (this._issueClientVOList != null) {
                if (temp._issueClientVOList == null) return false;
                else if (!(this._issueClientVOList.equals(temp._issueClientVOList))) 
                    return false;
            }
            else if (temp._issueClientVOList != null)
                return false;
            if (this._segmentVO != null) {
                if (temp._segmentVO == null) return false;
                else if (!(this._segmentVO.equals(temp._segmentVO))) 
                    return false;
            }
            else if (temp._segmentVO != null)
                return false;
            if (this._productStructureVO != null) {
                if (temp._productStructureVO == null) return false;
                else if (!(this._productStructureVO.equals(temp._productStructureVO))) 
                    return false;
            }
            else if (temp._productStructureVO != null)
                return false;
            if (this._freeLookDays != temp._freeLookDays)
                return false;
            if (this._has_freeLookDays != temp._has_freeLookDays)
                return false;
            if (this._guarMinCashSurrenderRate != null) {
                if (temp._guarMinCashSurrenderRate == null) return false;
                else if (!(this._guarMinCashSurrenderRate.equals(temp._guarMinCashSurrenderRate))) 
                    return false;
            }
            else if (temp._guarMinCashSurrenderRate != null)
                return false;
            if (this._issueInvestmentVOList != null) {
                if (temp._issueInvestmentVOList == null) return false;
                else if (!(this._issueInvestmentVOList.equals(temp._issueInvestmentVOList))) 
                    return false;
            }
            else if (temp._issueInvestmentVOList != null)
                return false;
            if (this._reinsuranceVOList != null) {
                if (temp._reinsuranceVOList == null) return false;
                else if (!(this._reinsuranceVOList.equals(temp._reinsuranceVOList))) 
                    return false;
            }
            else if (temp._reinsuranceVOList != null)
                return false;
            if (this._ownersPremiumTaxState != null) {
                if (temp._ownersPremiumTaxState == null) return false;
                else if (!(this._ownersPremiumTaxState.equals(temp._ownersPremiumTaxState))) 
                    return false;
            }
            else if (temp._ownersPremiumTaxState != null)
                return false;
            if (this._insuredResidenceState != null) {
                if (temp._insuredResidenceState == null) return false;
                else if (!(this._insuredResidenceState.equals(temp._insuredResidenceState))) 
                    return false;
            }
            else if (temp._insuredResidenceState != null)
                return false;
            if (this._insuredGender != null) {
                if (temp._insuredGender == null) return false;
                else if (!(this._insuredGender.equals(temp._insuredGender))) 
                    return false;
            }
            else if (temp._insuredGender != null)
                return false;
            if (this._insuredDateOfBirth != null) {
                if (temp._insuredDateOfBirth == null) return false;
                else if (!(this._insuredDateOfBirth.equals(temp._insuredDateOfBirth))) 
                    return false;
            }
            else if (temp._insuredDateOfBirth != null)
                return false;
            if (this._insuredIssueAge != temp._insuredIssueAge)
                return false;
            if (this._has_insuredIssueAge != temp._has_insuredIssueAge)
                return false;
            if (this._insuredClass != null) {
                if (temp._insuredClass == null) return false;
                else if (!(this._insuredClass.equals(temp._insuredClass))) 
                    return false;
            }
            else if (temp._insuredClass != null)
                return false;
            if (this._insuredUnderwritingClass != null) {
                if (temp._insuredUnderwritingClass == null) return false;
                else if (!(this._insuredUnderwritingClass.equals(temp._insuredUnderwritingClass))) 
                    return false;
            }
            else if (temp._insuredUnderwritingClass != null)
                return false;
            if (this._sevenPayRate != null) {
                if (temp._sevenPayRate == null) return false;
                else if (!(this._sevenPayRate.equals(temp._sevenPayRate))) 
                    return false;
            }
            else if (temp._sevenPayRate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFreeLookDaysReturns the value of field
     * 'freeLookDays'.
     * 
     * @return the value of field 'freeLookDays'.
     */
    public int getFreeLookDays()
    {
        return this._freeLookDays;
    } //-- int getFreeLookDays() 

    /**
     * Method getGuarMinCashSurrenderRateReturns the value of field
     * 'guarMinCashSurrenderRate'.
     * 
     * @return the value of field 'guarMinCashSurrenderRate'.
     */
    public java.math.BigDecimal getGuarMinCashSurrenderRate()
    {
        return this._guarMinCashSurrenderRate;
    } //-- java.math.BigDecimal getGuarMinCashSurrenderRate() 

    /**
     * Method getInsuredClassReturns the value of field
     * 'insuredClass'.
     * 
     * @return the value of field 'insuredClass'.
     */
    public java.lang.String getInsuredClass()
    {
        return this._insuredClass;
    } //-- java.lang.String getInsuredClass() 

    /**
     * Method getInsuredDateOfBirthReturns the value of field
     * 'insuredDateOfBirth'.
     * 
     * @return the value of field 'insuredDateOfBirth'.
     */
    public java.lang.String getInsuredDateOfBirth()
    {
        return this._insuredDateOfBirth;
    } //-- java.lang.String getInsuredDateOfBirth() 

    /**
     * Method getInsuredGenderReturns the value of field
     * 'insuredGender'.
     * 
     * @return the value of field 'insuredGender'.
     */
    public java.lang.String getInsuredGender()
    {
        return this._insuredGender;
    } //-- java.lang.String getInsuredGender() 

    /**
     * Method getInsuredIssueAgeReturns the value of field
     * 'insuredIssueAge'.
     * 
     * @return the value of field 'insuredIssueAge'.
     */
    public int getInsuredIssueAge()
    {
        return this._insuredIssueAge;
    } //-- int getInsuredIssueAge() 

    /**
     * Method getInsuredResidenceStateReturns the value of field
     * 'insuredResidenceState'.
     * 
     * @return the value of field 'insuredResidenceState'.
     */
    public java.lang.String getInsuredResidenceState()
    {
        return this._insuredResidenceState;
    } //-- java.lang.String getInsuredResidenceState() 

    /**
     * Method getInsuredUnderwritingClassReturns the value of field
     * 'insuredUnderwritingClass'.
     * 
     * @return the value of field 'insuredUnderwritingClass'.
     */
    public java.lang.String getInsuredUnderwritingClass()
    {
        return this._insuredUnderwritingClass;
    } //-- java.lang.String getInsuredUnderwritingClass() 

    /**
     * Method getIssueClientVO
     * 
     * @param index
     */
    public edit.common.vo.IssueClientVO getIssueClientVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _issueClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.IssueClientVO) _issueClientVOList.elementAt(index);
    } //-- edit.common.vo.IssueClientVO getIssueClientVO(int) 

    /**
     * Method getIssueClientVO
     */
    public edit.common.vo.IssueClientVO[] getIssueClientVO()
    {
        int size = _issueClientVOList.size();
        edit.common.vo.IssueClientVO[] mArray = new edit.common.vo.IssueClientVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.IssueClientVO) _issueClientVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.IssueClientVO[] getIssueClientVO() 

    /**
     * Method getIssueClientVOCount
     */
    public int getIssueClientVOCount()
    {
        return _issueClientVOList.size();
    } //-- int getIssueClientVOCount() 

    /**
     * Method getIssueInvestmentVO
     * 
     * @param index
     */
    public edit.common.vo.IssueInvestmentVO getIssueInvestmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _issueInvestmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.IssueInvestmentVO) _issueInvestmentVOList.elementAt(index);
    } //-- edit.common.vo.IssueInvestmentVO getIssueInvestmentVO(int) 

    /**
     * Method getIssueInvestmentVO
     */
    public edit.common.vo.IssueInvestmentVO[] getIssueInvestmentVO()
    {
        int size = _issueInvestmentVOList.size();
        edit.common.vo.IssueInvestmentVO[] mArray = new edit.common.vo.IssueInvestmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.IssueInvestmentVO) _issueInvestmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.IssueInvestmentVO[] getIssueInvestmentVO() 

    /**
     * Method getIssueInvestmentVOCount
     */
    public int getIssueInvestmentVOCount()
    {
        return _issueInvestmentVOList.size();
    } //-- int getIssueInvestmentVOCount() 

    /**
     * Method getOwnersPremiumTaxStateReturns the value of field
     * 'ownersPremiumTaxState'.
     * 
     * @return the value of field 'ownersPremiumTaxState'.
     */
    public java.lang.String getOwnersPremiumTaxState()
    {
        return this._ownersPremiumTaxState;
    } //-- java.lang.String getOwnersPremiumTaxState() 

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
     * Method getReinsuranceVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceVO getReinsuranceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ReinsuranceVO) _reinsuranceVOList.elementAt(index);
    } //-- edit.common.vo.ReinsuranceVO getReinsuranceVO(int) 

    /**
     * Method getReinsuranceVO
     */
    public edit.common.vo.ReinsuranceVO[] getReinsuranceVO()
    {
        int size = _reinsuranceVOList.size();
        edit.common.vo.ReinsuranceVO[] mArray = new edit.common.vo.ReinsuranceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ReinsuranceVO) _reinsuranceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ReinsuranceVO[] getReinsuranceVO() 

    /**
     * Method getReinsuranceVOCount
     */
    public int getReinsuranceVOCount()
    {
        return _reinsuranceVOList.size();
    } //-- int getReinsuranceVOCount() 

    /**
     * Method getSegmentVOReturns the value of field 'segmentVO'.
     * 
     * @return the value of field 'segmentVO'.
     */
    public edit.common.vo.SegmentVO getSegmentVO()
    {
        return this._segmentVO;
    } //-- edit.common.vo.SegmentVO getSegmentVO() 

    /**
     * Method getSevenPayRateReturns the value of field
     * 'sevenPayRate'.
     * 
     * @return the value of field 'sevenPayRate'.
     */
    public java.math.BigDecimal getSevenPayRate()
    {
        return this._sevenPayRate;
    } //-- java.math.BigDecimal getSevenPayRate() 

    /**
     * Method hasFreeLookDays
     */
    public boolean hasFreeLookDays()
    {
        return this._has_freeLookDays;
    } //-- boolean hasFreeLookDays() 

    /**
     * Method hasInsuredIssueAge
     */
    public boolean hasInsuredIssueAge()
    {
        return this._has_insuredIssueAge;
    } //-- boolean hasInsuredIssueAge() 

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
     * Method removeAllIssueClientVO
     */
    public void removeAllIssueClientVO()
    {
        _issueClientVOList.removeAllElements();
    } //-- void removeAllIssueClientVO() 

    /**
     * Method removeAllIssueInvestmentVO
     */
    public void removeAllIssueInvestmentVO()
    {
        _issueInvestmentVOList.removeAllElements();
    } //-- void removeAllIssueInvestmentVO() 

    /**
     * Method removeAllReinsuranceVO
     */
    public void removeAllReinsuranceVO()
    {
        _reinsuranceVOList.removeAllElements();
    } //-- void removeAllReinsuranceVO() 

    /**
     * Method removeIssueClientVO
     * 
     * @param index
     */
    public edit.common.vo.IssueClientVO removeIssueClientVO(int index)
    {
        java.lang.Object obj = _issueClientVOList.elementAt(index);
        _issueClientVOList.removeElementAt(index);
        return (edit.common.vo.IssueClientVO) obj;
    } //-- edit.common.vo.IssueClientVO removeIssueClientVO(int) 

    /**
     * Method removeIssueInvestmentVO
     * 
     * @param index
     */
    public edit.common.vo.IssueInvestmentVO removeIssueInvestmentVO(int index)
    {
        java.lang.Object obj = _issueInvestmentVOList.elementAt(index);
        _issueInvestmentVOList.removeElementAt(index);
        return (edit.common.vo.IssueInvestmentVO) obj;
    } //-- edit.common.vo.IssueInvestmentVO removeIssueInvestmentVO(int) 

    /**
     * Method removeReinsuranceVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceVO removeReinsuranceVO(int index)
    {
        java.lang.Object obj = _reinsuranceVOList.elementAt(index);
        _reinsuranceVOList.removeElementAt(index);
        return (edit.common.vo.ReinsuranceVO) obj;
    } //-- edit.common.vo.ReinsuranceVO removeReinsuranceVO(int) 

    /**
     * Method setFreeLookDaysSets the value of field
     * 'freeLookDays'.
     * 
     * @param freeLookDays the value of field 'freeLookDays'.
     */
    public void setFreeLookDays(int freeLookDays)
    {
        this._freeLookDays = freeLookDays;
        
        super.setVoChanged(true);
        this._has_freeLookDays = true;
    } //-- void setFreeLookDays(int) 

    /**
     * Method setGuarMinCashSurrenderRateSets the value of field
     * 'guarMinCashSurrenderRate'.
     * 
     * @param guarMinCashSurrenderRate the value of field
     * 'guarMinCashSurrenderRate'.
     */
    public void setGuarMinCashSurrenderRate(java.math.BigDecimal guarMinCashSurrenderRate)
    {
        this._guarMinCashSurrenderRate = guarMinCashSurrenderRate;
        
        super.setVoChanged(true);
    } //-- void setGuarMinCashSurrenderRate(java.math.BigDecimal) 

    /**
     * Method setInsuredClassSets the value of field
     * 'insuredClass'.
     * 
     * @param insuredClass the value of field 'insuredClass'.
     */
    public void setInsuredClass(java.lang.String insuredClass)
    {
        this._insuredClass = insuredClass;
        
        super.setVoChanged(true);
    } //-- void setInsuredClass(java.lang.String) 

    /**
     * Method setInsuredDateOfBirthSets the value of field
     * 'insuredDateOfBirth'.
     * 
     * @param insuredDateOfBirth the value of field
     * 'insuredDateOfBirth'.
     */
    public void setInsuredDateOfBirth(java.lang.String insuredDateOfBirth)
    {
        this._insuredDateOfBirth = insuredDateOfBirth;
        
        super.setVoChanged(true);
    } //-- void setInsuredDateOfBirth(java.lang.String) 

    /**
     * Method setInsuredGenderSets the value of field
     * 'insuredGender'.
     * 
     * @param insuredGender the value of field 'insuredGender'.
     */
    public void setInsuredGender(java.lang.String insuredGender)
    {
        this._insuredGender = insuredGender;
        
        super.setVoChanged(true);
    } //-- void setInsuredGender(java.lang.String) 

    /**
     * Method setInsuredIssueAgeSets the value of field
     * 'insuredIssueAge'.
     * 
     * @param insuredIssueAge the value of field 'insuredIssueAge'.
     */
    public void setInsuredIssueAge(int insuredIssueAge)
    {
        this._insuredIssueAge = insuredIssueAge;
        
        super.setVoChanged(true);
        this._has_insuredIssueAge = true;
    } //-- void setInsuredIssueAge(int) 

    /**
     * Method setInsuredResidenceStateSets the value of field
     * 'insuredResidenceState'.
     * 
     * @param insuredResidenceState the value of field
     * 'insuredResidenceState'.
     */
    public void setInsuredResidenceState(java.lang.String insuredResidenceState)
    {
        this._insuredResidenceState = insuredResidenceState;
        
        super.setVoChanged(true);
    } //-- void setInsuredResidenceState(java.lang.String) 

    /**
     * Method setInsuredUnderwritingClassSets the value of field
     * 'insuredUnderwritingClass'.
     * 
     * @param insuredUnderwritingClass the value of field
     * 'insuredUnderwritingClass'.
     */
    public void setInsuredUnderwritingClass(java.lang.String insuredUnderwritingClass)
    {
        this._insuredUnderwritingClass = insuredUnderwritingClass;
        
        super.setVoChanged(true);
    } //-- void setInsuredUnderwritingClass(java.lang.String) 

    /**
     * Method setIssueClientVO
     * 
     * @param index
     * @param vIssueClientVO
     */
    public void setIssueClientVO(int index, edit.common.vo.IssueClientVO vIssueClientVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _issueClientVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vIssueClientVO.setParentVO(this.getClass(), this);
        _issueClientVOList.setElementAt(vIssueClientVO, index);
    } //-- void setIssueClientVO(int, edit.common.vo.IssueClientVO) 

    /**
     * Method setIssueClientVO
     * 
     * @param issueClientVOArray
     */
    public void setIssueClientVO(edit.common.vo.IssueClientVO[] issueClientVOArray)
    {
        //-- copy array
        _issueClientVOList.removeAllElements();
        for (int i = 0; i < issueClientVOArray.length; i++) {
            issueClientVOArray[i].setParentVO(this.getClass(), this);
            _issueClientVOList.addElement(issueClientVOArray[i]);
        }
    } //-- void setIssueClientVO(edit.common.vo.IssueClientVO) 

    /**
     * Method setIssueInvestmentVO
     * 
     * @param index
     * @param vIssueInvestmentVO
     */
    public void setIssueInvestmentVO(int index, edit.common.vo.IssueInvestmentVO vIssueInvestmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _issueInvestmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vIssueInvestmentVO.setParentVO(this.getClass(), this);
        _issueInvestmentVOList.setElementAt(vIssueInvestmentVO, index);
    } //-- void setIssueInvestmentVO(int, edit.common.vo.IssueInvestmentVO) 

    /**
     * Method setIssueInvestmentVO
     * 
     * @param issueInvestmentVOArray
     */
    public void setIssueInvestmentVO(edit.common.vo.IssueInvestmentVO[] issueInvestmentVOArray)
    {
        //-- copy array
        _issueInvestmentVOList.removeAllElements();
        for (int i = 0; i < issueInvestmentVOArray.length; i++) {
            issueInvestmentVOArray[i].setParentVO(this.getClass(), this);
            _issueInvestmentVOList.addElement(issueInvestmentVOArray[i]);
        }
    } //-- void setIssueInvestmentVO(edit.common.vo.IssueInvestmentVO) 

    /**
     * Method setOwnersPremiumTaxStateSets the value of field
     * 'ownersPremiumTaxState'.
     * 
     * @param ownersPremiumTaxState the value of field
     * 'ownersPremiumTaxState'.
     */
    public void setOwnersPremiumTaxState(java.lang.String ownersPremiumTaxState)
    {
        this._ownersPremiumTaxState = ownersPremiumTaxState;
        
        super.setVoChanged(true);
    } //-- void setOwnersPremiumTaxState(java.lang.String) 

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
     * Method setReinsuranceVO
     * 
     * @param index
     * @param vReinsuranceVO
     */
    public void setReinsuranceVO(int index, edit.common.vo.ReinsuranceVO vReinsuranceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vReinsuranceVO.setParentVO(this.getClass(), this);
        _reinsuranceVOList.setElementAt(vReinsuranceVO, index);
    } //-- void setReinsuranceVO(int, edit.common.vo.ReinsuranceVO) 

    /**
     * Method setReinsuranceVO
     * 
     * @param reinsuranceVOArray
     */
    public void setReinsuranceVO(edit.common.vo.ReinsuranceVO[] reinsuranceVOArray)
    {
        //-- copy array
        _reinsuranceVOList.removeAllElements();
        for (int i = 0; i < reinsuranceVOArray.length; i++) {
            reinsuranceVOArray[i].setParentVO(this.getClass(), this);
            _reinsuranceVOList.addElement(reinsuranceVOArray[i]);
        }
    } //-- void setReinsuranceVO(edit.common.vo.ReinsuranceVO) 

    /**
     * Method setSegmentVOSets the value of field 'segmentVO'.
     * 
     * @param segmentVO the value of field 'segmentVO'.
     */
    public void setSegmentVO(edit.common.vo.SegmentVO segmentVO)
    {
        this._segmentVO = segmentVO;
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method setSevenPayRateSets the value of field
     * 'sevenPayRate'.
     * 
     * @param sevenPayRate the value of field 'sevenPayRate'.
     */
    public void setSevenPayRate(java.math.BigDecimal sevenPayRate)
    {
        this._sevenPayRate = sevenPayRate;
        
        super.setVoChanged(true);
    } //-- void setSevenPayRate(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.IssueDocumentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.IssueDocumentVO) Unmarshaller.unmarshal(edit.common.vo.IssueDocumentVO.class, reader);
    } //-- edit.common.vo.IssueDocumentVO unmarshal(java.io.Reader) 

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
