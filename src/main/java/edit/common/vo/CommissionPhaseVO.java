/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id$
 */

package edit.common.vo;

  import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class CommissionPhaseVO.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class CommissionPhaseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionPhasePK
     */
    private long _commissionPhasePK;

    /**
     * keeps track of state for field: _commissionPhasePK
     */
    private boolean _has_commissionPhasePK;

    /**
     * Field _premiumDueFK
     */
    private long _premiumDueFK;

    /**
     * keeps track of state for field: _premiumDueFK
     */
    private boolean _has_premiumDueFK;

    /**
     * Field _commissionPhaseID
     */
    private int _commissionPhaseID;

    /**
     * keeps track of state for field: _commissionPhaseID
     */
    private boolean _has_commissionPhaseID;

    /**
     * Field _expectedMonthlyPremium
     */
    private java.math.BigDecimal _expectedMonthlyPremium;

    private java.math.BigDecimal _commissionTarget;

    /**
     * Field _prevCumExpectedMonthlyPrem
     */
    private java.math.BigDecimal _prevCumExpectedMonthlyPrem;

    /**
     * Field _priorExpectedMonthlyPremium
     */
    private java.math.BigDecimal _priorExpectedMonthlyPremium;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _commissionablePremiumHistoryVOList
     */
    private java.util.Vector _commissionablePremiumHistoryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionPhaseVO() {
        super();
        _commissionablePremiumHistoryVOList = new Vector();
    } //-- edit.common.vo.CommissionPhaseVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addCommissionablePremiumHistoryVO
     * 
     * @param vCommissionablePremiumHistoryVO
     */
    public void addCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO vCommissionablePremiumHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionablePremiumHistoryVO.setParentVO(this.getClass(), this);
        _commissionablePremiumHistoryVOList.addElement(vCommissionablePremiumHistoryVO);
    } //-- void addCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method addCommissionablePremiumHistoryVO
     * 
     * @param index
     * @param vCommissionablePremiumHistoryVO
     */
    public void addCommissionablePremiumHistoryVO(int index, edit.common.vo.CommissionablePremiumHistoryVO vCommissionablePremiumHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionablePremiumHistoryVO.setParentVO(this.getClass(), this);
        _commissionablePremiumHistoryVOList.insertElementAt(vCommissionablePremiumHistoryVO, index);
    } //-- void addCommissionablePremiumHistoryVO(int, edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method enumerateCommissionablePremiumHistoryVO
     */
    public java.util.Enumeration enumerateCommissionablePremiumHistoryVO()
    {
        return _commissionablePremiumHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionablePremiumHistoryVO() 

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
        
        if (obj instanceof CommissionPhaseVO) {
        
            CommissionPhaseVO temp = (CommissionPhaseVO)obj;
            if (this._commissionPhasePK != temp._commissionPhasePK)
                return false;
            if (this._has_commissionPhasePK != temp._has_commissionPhasePK)
                return false;
            if (this._premiumDueFK != temp._premiumDueFK)
                return false;
            if (this._has_premiumDueFK != temp._has_premiumDueFK)
                return false;
            if (this._commissionPhaseID != temp._commissionPhaseID)
                return false;
            if (this._has_commissionPhaseID != temp._has_commissionPhaseID)
                return false;

            if (this._expectedMonthlyPremium != null) {
                if (temp._expectedMonthlyPremium == null) return false;
                else if (!(this._expectedMonthlyPremium.equals(temp._expectedMonthlyPremium))) 
                    return false;
            }
            else if (temp._expectedMonthlyPremium != null)
                return false;

            if (this._commissionTarget != null) {
                if (temp._commissionTarget == null) return false;
                else if (!(this._commissionTarget.equals(temp._commissionTarget))) 
                    return false;
            }
            else if (temp._commissionTarget != null)
                return false;

            if (this._prevCumExpectedMonthlyPrem != null) {
                if (temp._prevCumExpectedMonthlyPrem == null) return false;
                else if (!(this._prevCumExpectedMonthlyPrem.equals(temp._prevCumExpectedMonthlyPrem))) 
                    return false;
            }
            else if (temp._prevCumExpectedMonthlyPrem != null)
                return false;
            if (this._priorExpectedMonthlyPremium != null) {
                if (temp._priorExpectedMonthlyPremium == null) return false;
                else if (!(this._priorExpectedMonthlyPremium.equals(temp._priorExpectedMonthlyPremium))) 
                    return false;
            }
            else if (temp._priorExpectedMonthlyPremium != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._commissionablePremiumHistoryVOList != null) {
                if (temp._commissionablePremiumHistoryVOList == null) return false;
                else if (!(this._commissionablePremiumHistoryVOList.equals(temp._commissionablePremiumHistoryVOList))) 
                    return false;
            }
            else if (temp._commissionablePremiumHistoryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCommissionPhaseIDReturns the value of field
     * 'commissionPhaseID'.
     * 
     * @return the value of field 'commissionPhaseID'.
     */
    public int getCommissionPhaseID()
    {
        return this._commissionPhaseID;
    } //-- int getCommissionPhaseID() 

    /**
     * Method getCommissionPhasePKReturns the value of field
     * 'commissionPhasePK'.
     * 
     * @return the value of field 'commissionPhasePK'.
     */
    public long getCommissionPhasePK()
    {
        return this._commissionPhasePK;
    } //-- long getCommissionPhasePK() 

    /**
     * Method getCommissionablePremiumHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionablePremiumHistoryVO getCommissionablePremiumHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionablePremiumHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionablePremiumHistoryVO) _commissionablePremiumHistoryVOList.elementAt(index);
    } //-- edit.common.vo.CommissionablePremiumHistoryVO getCommissionablePremiumHistoryVO(int) 

    /**
     * Method getCommissionablePremiumHistoryVO
     */
    public edit.common.vo.CommissionablePremiumHistoryVO[] getCommissionablePremiumHistoryVO()
    {
        int size = _commissionablePremiumHistoryVOList.size();
        edit.common.vo.CommissionablePremiumHistoryVO[] mArray = new edit.common.vo.CommissionablePremiumHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionablePremiumHistoryVO) _commissionablePremiumHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionablePremiumHistoryVO[] getCommissionablePremiumHistoryVO() 

    /**
     * Method getCommissionablePremiumHistoryVOCount
     */
    public int getCommissionablePremiumHistoryVOCount()
    {
        return _commissionablePremiumHistoryVOList.size();
    } //-- int getCommissionablePremiumHistoryVOCount() 

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
     * Method getExpectedMonthlyPremiumReturns the value of field
     * 'expectedMonthlyPremium'.
     * 
     * @return the value of field 'expectedMonthlyPremium'.
     */
    public java.math.BigDecimal getExpectedMonthlyPremium()
    {
        return this._expectedMonthlyPremium;
    } //-- java.math.BigDecimal getExpectedMonthlyPremium() 

    public java.math.BigDecimal getCommissionTarget()
    {
        return this._commissionTarget;
    } //-- java.math.BigDecimal getCommissionTarget() 

    /**
     * Method getPremiumDueFKReturns the value of field
     * 'premiumDueFK'.
     * 
     * @return the value of field 'premiumDueFK'.
     */
    public long getPremiumDueFK()
    {
        return this._premiumDueFK;
    } //-- long getPremiumDueFK() 

    /**
     * Method getPrevCumExpectedMonthlyPremReturns the value of
     * field 'prevCumExpectedMonthlyPrem'.
     * 
     * @return the value of field 'prevCumExpectedMonthlyPrem'.
     */
    public java.math.BigDecimal getPrevCumExpectedMonthlyPrem()
    {
        return this._prevCumExpectedMonthlyPrem;
    } //-- java.math.BigDecimal getPrevCumExpectedMonthlyPrem() 

    /**
     * Method getPriorExpectedMonthlyPremiumReturns the value of
     * field 'priorExpectedMonthlyPremium'.
     * 
     * @return the value of field 'priorExpectedMonthlyPremium'.
     */
    public java.math.BigDecimal getPriorExpectedMonthlyPremium()
    {
        return this._priorExpectedMonthlyPremium;
    } //-- java.math.BigDecimal getPriorExpectedMonthlyPremium() 

    /**
     * Method hasCommissionPhaseID
     */
    public boolean hasCommissionPhaseID()
    {
        return this._has_commissionPhaseID;
    } //-- boolean hasCommissionPhaseID() 

    /**
     * Method hasCommissionPhasePK
     */
    public boolean hasCommissionPhasePK()
    {
        return this._has_commissionPhasePK;
    } //-- boolean hasCommissionPhasePK() 

    /**
     * Method hasPremiumDueFK
     */
    public boolean hasPremiumDueFK()
    {
        return this._has_premiumDueFK;
    } //-- boolean hasPremiumDueFK() 

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
     * Method removeAllCommissionablePremiumHistoryVO
     */
    public void removeAllCommissionablePremiumHistoryVO()
    {
        _commissionablePremiumHistoryVOList.removeAllElements();
    } //-- void removeAllCommissionablePremiumHistoryVO() 

    /**
     * Method removeCommissionablePremiumHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionablePremiumHistoryVO removeCommissionablePremiumHistoryVO(int index)
    {
        java.lang.Object obj = _commissionablePremiumHistoryVOList.elementAt(index);
        _commissionablePremiumHistoryVOList.removeElementAt(index);
        return (edit.common.vo.CommissionablePremiumHistoryVO) obj;
    } //-- edit.common.vo.CommissionablePremiumHistoryVO removeCommissionablePremiumHistoryVO(int) 

    /**
     * Method setCommissionPhaseIDSets the value of field
     * 'commissionPhaseID'.
     * 
     * @param commissionPhaseID the value of field
     * 'commissionPhaseID'.
     */
    public void setCommissionPhaseID(int commissionPhaseID)
    {
        this._commissionPhaseID = commissionPhaseID;
        
        super.setVoChanged(true);
        this._has_commissionPhaseID = true;
    } //-- void setCommissionPhaseID(int) 

    /**
     * Method setCommissionPhasePKSets the value of field
     * 'commissionPhasePK'.
     * 
     * @param commissionPhasePK the value of field
     * 'commissionPhasePK'.
     */
    public void setCommissionPhasePK(long commissionPhasePK)
    {
        this._commissionPhasePK = commissionPhasePK;
        
        super.setVoChanged(true);
        this._has_commissionPhasePK = true;
    } //-- void setCommissionPhasePK(long) 

    /**
     * Method setCommissionablePremiumHistoryVO
     * 
     * @param index
     * @param vCommissionablePremiumHistoryVO
     */
    public void setCommissionablePremiumHistoryVO(int index, edit.common.vo.CommissionablePremiumHistoryVO vCommissionablePremiumHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionablePremiumHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionablePremiumHistoryVO.setParentVO(this.getClass(), this);
        _commissionablePremiumHistoryVOList.setElementAt(vCommissionablePremiumHistoryVO, index);
    } //-- void setCommissionablePremiumHistoryVO(int, edit.common.vo.CommissionablePremiumHistoryVO) 

    /**
     * Method setCommissionablePremiumHistoryVO
     * 
     * @param commissionablePremiumHistoryVOArray
     */
    public void setCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO[] commissionablePremiumHistoryVOArray)
    {
        //-- copy array
        _commissionablePremiumHistoryVOList.removeAllElements();
        for (int i = 0; i < commissionablePremiumHistoryVOArray.length; i++) {
            commissionablePremiumHistoryVOArray[i].setParentVO(this.getClass(), this);
            _commissionablePremiumHistoryVOList.addElement(commissionablePremiumHistoryVOArray[i]);
        }
    } //-- void setCommissionablePremiumHistoryVO(edit.common.vo.CommissionablePremiumHistoryVO) 

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
     * Method setExpectedMonthlyPremiumSets the value of field
     * 'expectedMonthlyPremium'.
     * 
     * @param expectedMonthlyPremium the value of field
     * 'expectedMonthlyPremium'.
     */
    public void setExpectedMonthlyPremium(java.math.BigDecimal expectedMonthlyPremium)
    {
        this._expectedMonthlyPremium = expectedMonthlyPremium;
        
        super.setVoChanged(true);
    } //-- void setExpectedMonthlyPremium(java.math.BigDecimal) 

    public void setCommissionTarget(java.math.BigDecimal commissionTarget)
    {
        this._commissionTarget = commissionTarget;
        
        super.setVoChanged(true);
    } //-- void setExpectedMonthlyPremium(java.math.BigDecimal) 

    /**
     * Method setPremiumDueFKSets the value of field
     * 'premiumDueFK'.
     * 
     * @param premiumDueFK the value of field 'premiumDueFK'.
     */
    public void setPremiumDueFK(long premiumDueFK)
    {
        this._premiumDueFK = premiumDueFK;
        
        super.setVoChanged(true);
        this._has_premiumDueFK = true;
    } //-- void setPremiumDueFK(long) 

    /**
     * Method setPrevCumExpectedMonthlyPremSets the value of field
     * 'prevCumExpectedMonthlyPrem'.
     * 
     * @param prevCumExpectedMonthlyPrem the value of field
     * 'prevCumExpectedMonthlyPrem'.
     */
    public void setPrevCumExpectedMonthlyPrem(java.math.BigDecimal prevCumExpectedMonthlyPrem)
    {
        this._prevCumExpectedMonthlyPrem = prevCumExpectedMonthlyPrem;
        
        super.setVoChanged(true);
    } //-- void setPrevCumExpectedMonthlyPrem(java.math.BigDecimal) 

    /**
     * Method setPriorExpectedMonthlyPremiumSets the value of field
     * 'priorExpectedMonthlyPremium'.
     * 
     * @param priorExpectedMonthlyPremium the value of field
     * 'priorExpectedMonthlyPremium'.
     */
    public void setPriorExpectedMonthlyPremium(java.math.BigDecimal priorExpectedMonthlyPremium)
    {
        this._priorExpectedMonthlyPremium = priorExpectedMonthlyPremium;
        
        super.setVoChanged(true);
    } //-- void setPriorExpectedMonthlyPremium(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionPhaseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionPhaseVO) Unmarshaller.unmarshal(edit.common.vo.CommissionPhaseVO.class, reader);
    } //-- edit.common.vo.CommissionPhaseVO unmarshal(java.io.Reader) 

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
