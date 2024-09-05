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
public class CoiReplenishmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Comment describing your root element
     */
    private edit.common.vo.BaseSegmentVO _baseSegmentVO;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _riderSegmentVOList;

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
     * Comment describing your root element
     */
    private java.util.Vector _investmentTransferValueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CoiReplenishmentVO() {
        super();
        _riderSegmentVOList = new Vector();
        _investmentTransferValueVOList = new Vector();
    } //-- edit.common.vo.CoiReplenishmentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addInvestmentTransferValueVO
     * 
     * @param vInvestmentTransferValueVO
     */
    public void addInvestmentTransferValueVO(edit.common.vo.InvestmentTransferValueVO vInvestmentTransferValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentTransferValueVO.setParentVO(this.getClass(), this);
        _investmentTransferValueVOList.addElement(vInvestmentTransferValueVO);
    } //-- void addInvestmentTransferValueVO(edit.common.vo.InvestmentTransferValueVO) 

    /**
     * Method addInvestmentTransferValueVO
     * 
     * @param index
     * @param vInvestmentTransferValueVO
     */
    public void addInvestmentTransferValueVO(int index, edit.common.vo.InvestmentTransferValueVO vInvestmentTransferValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentTransferValueVO.setParentVO(this.getClass(), this);
        _investmentTransferValueVOList.insertElementAt(vInvestmentTransferValueVO, index);
    } //-- void addInvestmentTransferValueVO(int, edit.common.vo.InvestmentTransferValueVO) 

    /**
     * Method addRiderSegmentVO
     * 
     * @param vRiderSegmentVO
     */
    public void addRiderSegmentVO(edit.common.vo.RiderSegmentVO vRiderSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRiderSegmentVO.setParentVO(this.getClass(), this);
        _riderSegmentVOList.addElement(vRiderSegmentVO);
    } //-- void addRiderSegmentVO(edit.common.vo.RiderSegmentVO) 

    /**
     * Method addRiderSegmentVO
     * 
     * @param index
     * @param vRiderSegmentVO
     */
    public void addRiderSegmentVO(int index, edit.common.vo.RiderSegmentVO vRiderSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRiderSegmentVO.setParentVO(this.getClass(), this);
        _riderSegmentVOList.insertElementAt(vRiderSegmentVO, index);
    } //-- void addRiderSegmentVO(int, edit.common.vo.RiderSegmentVO) 

    /**
     * Method enumerateInvestmentTransferValueVO
     */
    public java.util.Enumeration enumerateInvestmentTransferValueVO()
    {
        return _investmentTransferValueVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentTransferValueVO() 

    /**
     * Method enumerateRiderSegmentVO
     */
    public java.util.Enumeration enumerateRiderSegmentVO()
    {
        return _riderSegmentVOList.elements();
    } //-- java.util.Enumeration enumerateRiderSegmentVO() 

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
        
        if (obj instanceof CoiReplenishmentVO) {
        
            CoiReplenishmentVO temp = (CoiReplenishmentVO)obj;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._baseSegmentVO != null) {
                if (temp._baseSegmentVO == null) return false;
                else if (!(this._baseSegmentVO.equals(temp._baseSegmentVO))) 
                    return false;
            }
            else if (temp._baseSegmentVO != null)
                return false;
            if (this._riderSegmentVOList != null) {
                if (temp._riderSegmentVOList == null) return false;
                else if (!(this._riderSegmentVOList.equals(temp._riderSegmentVOList))) 
                    return false;
            }
            else if (temp._riderSegmentVOList != null)
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
            if (this._investmentTransferValueVOList != null) {
                if (temp._investmentTransferValueVOList == null) return false;
                else if (!(this._investmentTransferValueVOList.equals(temp._investmentTransferValueVOList))) 
                    return false;
            }
            else if (temp._investmentTransferValueVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBaseSegmentVOReturns the value of field
     * 'baseSegmentVO'. The field 'baseSegmentVO' has the following
     * description: Comment describing your root element
     * 
     * @return the value of field 'baseSegmentVO'.
     */
    public edit.common.vo.BaseSegmentVO getBaseSegmentVO()
    {
        return this._baseSegmentVO;
    } //-- edit.common.vo.BaseSegmentVO getBaseSegmentVO() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

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
     * Method getInvestmentTransferValueVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentTransferValueVO getInvestmentTransferValueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentTransferValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentTransferValueVO) _investmentTransferValueVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentTransferValueVO getInvestmentTransferValueVO(int) 

    /**
     * Method getInvestmentTransferValueVO
     */
    public edit.common.vo.InvestmentTransferValueVO[] getInvestmentTransferValueVO()
    {
        int size = _investmentTransferValueVOList.size();
        edit.common.vo.InvestmentTransferValueVO[] mArray = new edit.common.vo.InvestmentTransferValueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentTransferValueVO) _investmentTransferValueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentTransferValueVO[] getInvestmentTransferValueVO() 

    /**
     * Method getInvestmentTransferValueVOCount
     */
    public int getInvestmentTransferValueVOCount()
    {
        return _investmentTransferValueVOList.size();
    } //-- int getInvestmentTransferValueVOCount() 

    /**
     * Method getRiderSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.RiderSegmentVO getRiderSegmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _riderSegmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RiderSegmentVO) _riderSegmentVOList.elementAt(index);
    } //-- edit.common.vo.RiderSegmentVO getRiderSegmentVO(int) 

    /**
     * Method getRiderSegmentVO
     */
    public edit.common.vo.RiderSegmentVO[] getRiderSegmentVO()
    {
        int size = _riderSegmentVOList.size();
        edit.common.vo.RiderSegmentVO[] mArray = new edit.common.vo.RiderSegmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RiderSegmentVO) _riderSegmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RiderSegmentVO[] getRiderSegmentVO() 

    /**
     * Method getRiderSegmentVOCount
     */
    public int getRiderSegmentVOCount()
    {
        return _riderSegmentVOList.size();
    } //-- int getRiderSegmentVOCount() 

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
     * Method removeAllInvestmentTransferValueVO
     */
    public void removeAllInvestmentTransferValueVO()
    {
        _investmentTransferValueVOList.removeAllElements();
    } //-- void removeAllInvestmentTransferValueVO() 

    /**
     * Method removeAllRiderSegmentVO
     */
    public void removeAllRiderSegmentVO()
    {
        _riderSegmentVOList.removeAllElements();
    } //-- void removeAllRiderSegmentVO() 

    /**
     * Method removeInvestmentTransferValueVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentTransferValueVO removeInvestmentTransferValueVO(int index)
    {
        java.lang.Object obj = _investmentTransferValueVOList.elementAt(index);
        _investmentTransferValueVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentTransferValueVO) obj;
    } //-- edit.common.vo.InvestmentTransferValueVO removeInvestmentTransferValueVO(int) 

    /**
     * Method removeRiderSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.RiderSegmentVO removeRiderSegmentVO(int index)
    {
        java.lang.Object obj = _riderSegmentVOList.elementAt(index);
        _riderSegmentVOList.removeElementAt(index);
        return (edit.common.vo.RiderSegmentVO) obj;
    } //-- edit.common.vo.RiderSegmentVO removeRiderSegmentVO(int) 

    /**
     * Method setBaseSegmentVOSets the value of field
     * 'baseSegmentVO'. The field 'baseSegmentVO' has the following
     * description: Comment describing your root element
     * 
     * @param baseSegmentVO the value of field 'baseSegmentVO'.
     */
    public void setBaseSegmentVO(edit.common.vo.BaseSegmentVO baseSegmentVO)
    {
        this._baseSegmentVO = baseSegmentVO;
    } //-- void setBaseSegmentVO(edit.common.vo.BaseSegmentVO) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

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
     * Method setInvestmentTransferValueVO
     * 
     * @param index
     * @param vInvestmentTransferValueVO
     */
    public void setInvestmentTransferValueVO(int index, edit.common.vo.InvestmentTransferValueVO vInvestmentTransferValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentTransferValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentTransferValueVO.setParentVO(this.getClass(), this);
        _investmentTransferValueVOList.setElementAt(vInvestmentTransferValueVO, index);
    } //-- void setInvestmentTransferValueVO(int, edit.common.vo.InvestmentTransferValueVO) 

    /**
     * Method setInvestmentTransferValueVO
     * 
     * @param investmentTransferValueVOArray
     */
    public void setInvestmentTransferValueVO(edit.common.vo.InvestmentTransferValueVO[] investmentTransferValueVOArray)
    {
        //-- copy array
        _investmentTransferValueVOList.removeAllElements();
        for (int i = 0; i < investmentTransferValueVOArray.length; i++) {
            investmentTransferValueVOArray[i].setParentVO(this.getClass(), this);
            _investmentTransferValueVOList.addElement(investmentTransferValueVOArray[i]);
        }
    } //-- void setInvestmentTransferValueVO(edit.common.vo.InvestmentTransferValueVO) 

    /**
     * Method setRiderSegmentVO
     * 
     * @param index
     * @param vRiderSegmentVO
     */
    public void setRiderSegmentVO(int index, edit.common.vo.RiderSegmentVO vRiderSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _riderSegmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRiderSegmentVO.setParentVO(this.getClass(), this);
        _riderSegmentVOList.setElementAt(vRiderSegmentVO, index);
    } //-- void setRiderSegmentVO(int, edit.common.vo.RiderSegmentVO) 

    /**
     * Method setRiderSegmentVO
     * 
     * @param riderSegmentVOArray
     */
    public void setRiderSegmentVO(edit.common.vo.RiderSegmentVO[] riderSegmentVOArray)
    {
        //-- copy array
        _riderSegmentVOList.removeAllElements();
        for (int i = 0; i < riderSegmentVOArray.length; i++) {
            riderSegmentVOArray[i].setParentVO(this.getClass(), this);
            _riderSegmentVOList.addElement(riderSegmentVOArray[i]);
        }
    } //-- void setRiderSegmentVO(edit.common.vo.RiderSegmentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CoiReplenishmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CoiReplenishmentVO) Unmarshaller.unmarshal(edit.common.vo.CoiReplenishmentVO.class, reader);
    } //-- edit.common.vo.CoiReplenishmentVO unmarshal(java.io.Reader) 

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
