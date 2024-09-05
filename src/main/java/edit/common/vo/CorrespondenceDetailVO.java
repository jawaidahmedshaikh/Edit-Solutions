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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class CorrespondenceDetailVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _correspondenceDetailPK
     */
    private long _correspondenceDetailPK;

    /**
     * keeps track of state for field: _correspondenceDetailPK
     */
    private boolean _has_correspondenceDetailPK;

    /**
     * Field _correspondenceType
     */
    private java.lang.String _correspondenceType;

    /**
     * Field _guaranteedRate
     */
    private double _guaranteedRate;

    /**
     * keeps track of state for field: _guaranteedRate
     */
    private boolean _has_guaranteedRate;

    /**
     * Field _currentRate
     */
    private double _currentRate;

    /**
     * keeps track of state for field: _currentRate
     */
    private boolean _has_currentRate;

    /**
     * Field _bonusRate
     */
    private double _bonusRate;

    /**
     * keeps track of state for field: _bonusRate
     */
    private boolean _has_bonusRate;

    /**
     * Field _freeLookDays
     */
    private int _freeLookDays;

    /**
     * keeps track of state for field: _freeLookDays
     */
    private boolean _has_freeLookDays;

    /**
     * Field _initialGuaranteePeriod
     */
    private int _initialGuaranteePeriod;

    /**
     * keeps track of state for field: _initialGuaranteePeriod
     */
    private boolean _has_initialGuaranteePeriod;

    /**
     * Field _currentIntRateDate
     */
    private java.lang.String _currentIntRateDate;

    /**
     * Field _RMDOption
     */
    private java.lang.String _RMDOption;

    /**
     * Field _RMDAmount
     */
    private java.math.BigDecimal _RMDAmount;

    /**
     * Field _seventyAndHalfDate
     */
    private java.lang.String _seventyAndHalfDate;

    /**
     * Field _fixedGuaranteedMinimumInterestRate
     */
    private java.math.BigDecimal _fixedGuaranteedMinimumInterestRate;

    /**
     * Field _premiumBonusPercentage
     */
    private java.math.BigDecimal _premiumBonusPercentage;

    /**
     * Field _naturalDocVOList
     */
    private java.util.Vector _naturalDocVOList;

    /**
     * Field _productStructureVOList
     */
    private java.util.Vector _productStructureVOList;

    /**
     * Used for Correspondence projections reports
     */
    private java.util.Vector _projectionsVOList;

    /**
     * Used for Correspondence projections reports
     */
    private java.util.Vector _projectionSurrenderChargeVOList;

    /**
     * Used for Statement Correspondence
     */
    private java.util.Vector _activitySummaryVOList;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _investmentRateVOList;

    /**
     * Used for Correspondence calculated values
     */
    private java.util.Vector _calculatedValuesVOList;

    /**
     * Field _GIOOptionValueVOList
     */
    private java.util.Vector _GIOOptionValueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CorrespondenceDetailVO() {
        super();
        _naturalDocVOList = new Vector();
        _productStructureVOList = new Vector();
        _projectionsVOList = new Vector();
        _projectionSurrenderChargeVOList = new Vector();
        _activitySummaryVOList = new Vector();
        _investmentRateVOList = new Vector();
        _calculatedValuesVOList = new Vector();
        _GIOOptionValueVOList = new Vector();
    } //-- edit.common.vo.CorrespondenceDetailVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addActivitySummaryVO
     * 
     * @param vActivitySummaryVO
     */
    public void addActivitySummaryVO(edit.common.vo.ActivitySummaryVO vActivitySummaryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vActivitySummaryVO.setParentVO(this.getClass(), this);
        _activitySummaryVOList.addElement(vActivitySummaryVO);
    } //-- void addActivitySummaryVO(edit.common.vo.ActivitySummaryVO) 

    /**
     * Method addActivitySummaryVO
     * 
     * @param index
     * @param vActivitySummaryVO
     */
    public void addActivitySummaryVO(int index, edit.common.vo.ActivitySummaryVO vActivitySummaryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vActivitySummaryVO.setParentVO(this.getClass(), this);
        _activitySummaryVOList.insertElementAt(vActivitySummaryVO, index);
    } //-- void addActivitySummaryVO(int, edit.common.vo.ActivitySummaryVO) 

    /**
     * Method addCalculatedValuesVO
     * 
     * @param vCalculatedValuesVO
     */
    public void addCalculatedValuesVO(edit.common.vo.CalculatedValuesVO vCalculatedValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCalculatedValuesVO.setParentVO(this.getClass(), this);
        _calculatedValuesVOList.addElement(vCalculatedValuesVO);
    } //-- void addCalculatedValuesVO(edit.common.vo.CalculatedValuesVO) 

    /**
     * Method addCalculatedValuesVO
     * 
     * @param index
     * @param vCalculatedValuesVO
     */
    public void addCalculatedValuesVO(int index, edit.common.vo.CalculatedValuesVO vCalculatedValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCalculatedValuesVO.setParentVO(this.getClass(), this);
        _calculatedValuesVOList.insertElementAt(vCalculatedValuesVO, index);
    } //-- void addCalculatedValuesVO(int, edit.common.vo.CalculatedValuesVO) 

    /**
     * Method addGIOOptionValueVO
     * 
     * @param vGIOOptionValueVO
     */
    public void addGIOOptionValueVO(edit.common.vo.GIOOptionValueVO vGIOOptionValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGIOOptionValueVO.setParentVO(this.getClass(), this);
        _GIOOptionValueVOList.addElement(vGIOOptionValueVO);
    } //-- void addGIOOptionValueVO(edit.common.vo.GIOOptionValueVO) 

    /**
     * Method addGIOOptionValueVO
     * 
     * @param index
     * @param vGIOOptionValueVO
     */
    public void addGIOOptionValueVO(int index, edit.common.vo.GIOOptionValueVO vGIOOptionValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vGIOOptionValueVO.setParentVO(this.getClass(), this);
        _GIOOptionValueVOList.insertElementAt(vGIOOptionValueVO, index);
    } //-- void addGIOOptionValueVO(int, edit.common.vo.GIOOptionValueVO) 

    /**
     * Method addInvestmentRateVO
     * 
     * @param vInvestmentRateVO
     */
    public void addInvestmentRateVO(edit.common.vo.InvestmentRateVO vInvestmentRateVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentRateVO.setParentVO(this.getClass(), this);
        _investmentRateVOList.addElement(vInvestmentRateVO);
    } //-- void addInvestmentRateVO(edit.common.vo.InvestmentRateVO) 

    /**
     * Method addInvestmentRateVO
     * 
     * @param index
     * @param vInvestmentRateVO
     */
    public void addInvestmentRateVO(int index, edit.common.vo.InvestmentRateVO vInvestmentRateVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentRateVO.setParentVO(this.getClass(), this);
        _investmentRateVOList.insertElementAt(vInvestmentRateVO, index);
    } //-- void addInvestmentRateVO(int, edit.common.vo.InvestmentRateVO) 

    /**
     * Method addNaturalDocVO
     * 
     * @param vNaturalDocVO
     */
    public void addNaturalDocVO(edit.common.vo.NaturalDocVO vNaturalDocVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vNaturalDocVO.setParentVO(this.getClass(), this);
        _naturalDocVOList.addElement(vNaturalDocVO);
    } //-- void addNaturalDocVO(edit.common.vo.NaturalDocVO) 

    /**
     * Method addNaturalDocVO
     * 
     * @param index
     * @param vNaturalDocVO
     */
    public void addNaturalDocVO(int index, edit.common.vo.NaturalDocVO vNaturalDocVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vNaturalDocVO.setParentVO(this.getClass(), this);
        _naturalDocVOList.insertElementAt(vNaturalDocVO, index);
    } //-- void addNaturalDocVO(int, edit.common.vo.NaturalDocVO) 

    /**
     * Method addProductStructureVO
     * 
     * @param vProductStructureVO
     */
    public void addProductStructureVO(edit.common.vo.ProductStructureVO vProductStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductStructureVO.setParentVO(this.getClass(), this);
        _productStructureVOList.addElement(vProductStructureVO);
    } //-- void addProductStructureVO(edit.common.vo.ProductStructureVO) 

    /**
     * Method addProductStructureVO
     * 
     * @param index
     * @param vProductStructureVO
     */
    public void addProductStructureVO(int index, edit.common.vo.ProductStructureVO vProductStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductStructureVO.setParentVO(this.getClass(), this);
        _productStructureVOList.insertElementAt(vProductStructureVO, index);
    } //-- void addProductStructureVO(int, edit.common.vo.ProductStructureVO) 

    /**
     * Method addProjectionSurrenderChargeVO
     * 
     * @param vProjectionSurrenderChargeVO
     */
    public void addProjectionSurrenderChargeVO(edit.common.vo.ProjectionSurrenderChargeVO vProjectionSurrenderChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProjectionSurrenderChargeVO.setParentVO(this.getClass(), this);
        _projectionSurrenderChargeVOList.addElement(vProjectionSurrenderChargeVO);
    } //-- void addProjectionSurrenderChargeVO(edit.common.vo.ProjectionSurrenderChargeVO) 

    /**
     * Method addProjectionSurrenderChargeVO
     * 
     * @param index
     * @param vProjectionSurrenderChargeVO
     */
    public void addProjectionSurrenderChargeVO(int index, edit.common.vo.ProjectionSurrenderChargeVO vProjectionSurrenderChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProjectionSurrenderChargeVO.setParentVO(this.getClass(), this);
        _projectionSurrenderChargeVOList.insertElementAt(vProjectionSurrenderChargeVO, index);
    } //-- void addProjectionSurrenderChargeVO(int, edit.common.vo.ProjectionSurrenderChargeVO) 

    /**
     * Method addProjectionsVO
     * 
     * @param vProjectionsVO
     */
    public void addProjectionsVO(edit.common.vo.ProjectionsVO vProjectionsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProjectionsVO.setParentVO(this.getClass(), this);
        _projectionsVOList.addElement(vProjectionsVO);
    } //-- void addProjectionsVO(edit.common.vo.ProjectionsVO) 

    /**
     * Method addProjectionsVO
     * 
     * @param index
     * @param vProjectionsVO
     */
    public void addProjectionsVO(int index, edit.common.vo.ProjectionsVO vProjectionsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProjectionsVO.setParentVO(this.getClass(), this);
        _projectionsVOList.insertElementAt(vProjectionsVO, index);
    } //-- void addProjectionsVO(int, edit.common.vo.ProjectionsVO) 

    /**
     * Method enumerateActivitySummaryVO
     */
    public java.util.Enumeration enumerateActivitySummaryVO()
    {
        return _activitySummaryVOList.elements();
    } //-- java.util.Enumeration enumerateActivitySummaryVO() 

    /**
     * Method enumerateCalculatedValuesVO
     */
    public java.util.Enumeration enumerateCalculatedValuesVO()
    {
        return _calculatedValuesVOList.elements();
    } //-- java.util.Enumeration enumerateCalculatedValuesVO() 

    /**
     * Method enumerateGIOOptionValueVO
     */
    public java.util.Enumeration enumerateGIOOptionValueVO()
    {
        return _GIOOptionValueVOList.elements();
    } //-- java.util.Enumeration enumerateGIOOptionValueVO() 

    /**
     * Method enumerateInvestmentRateVO
     */
    public java.util.Enumeration enumerateInvestmentRateVO()
    {
        return _investmentRateVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentRateVO() 

    /**
     * Method enumerateNaturalDocVO
     */
    public java.util.Enumeration enumerateNaturalDocVO()
    {
        return _naturalDocVOList.elements();
    } //-- java.util.Enumeration enumerateNaturalDocVO() 

    /**
     * Method enumerateProductStructureVO
     */
    public java.util.Enumeration enumerateProductStructureVO()
    {
        return _productStructureVOList.elements();
    } //-- java.util.Enumeration enumerateProductStructureVO() 

    /**
     * Method enumerateProjectionSurrenderChargeVO
     */
    public java.util.Enumeration enumerateProjectionSurrenderChargeVO()
    {
        return _projectionSurrenderChargeVOList.elements();
    } //-- java.util.Enumeration enumerateProjectionSurrenderChargeVO() 

    /**
     * Method enumerateProjectionsVO
     */
    public java.util.Enumeration enumerateProjectionsVO()
    {
        return _projectionsVOList.elements();
    } //-- java.util.Enumeration enumerateProjectionsVO() 

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
        
        if (obj instanceof CorrespondenceDetailVO) {
        
            CorrespondenceDetailVO temp = (CorrespondenceDetailVO)obj;
            if (this._correspondenceDetailPK != temp._correspondenceDetailPK)
                return false;
            if (this._has_correspondenceDetailPK != temp._has_correspondenceDetailPK)
                return false;
            if (this._correspondenceType != null) {
                if (temp._correspondenceType == null) return false;
                else if (!(this._correspondenceType.equals(temp._correspondenceType))) 
                    return false;
            }
            else if (temp._correspondenceType != null)
                return false;
            if (this._guaranteedRate != temp._guaranteedRate)
                return false;
            if (this._has_guaranteedRate != temp._has_guaranteedRate)
                return false;
            if (this._currentRate != temp._currentRate)
                return false;
            if (this._has_currentRate != temp._has_currentRate)
                return false;
            if (this._bonusRate != temp._bonusRate)
                return false;
            if (this._has_bonusRate != temp._has_bonusRate)
                return false;
            if (this._freeLookDays != temp._freeLookDays)
                return false;
            if (this._has_freeLookDays != temp._has_freeLookDays)
                return false;
            if (this._initialGuaranteePeriod != temp._initialGuaranteePeriod)
                return false;
            if (this._has_initialGuaranteePeriod != temp._has_initialGuaranteePeriod)
                return false;
            if (this._currentIntRateDate != null) {
                if (temp._currentIntRateDate == null) return false;
                else if (!(this._currentIntRateDate.equals(temp._currentIntRateDate))) 
                    return false;
            }
            else if (temp._currentIntRateDate != null)
                return false;
            if (this._RMDOption != null) {
                if (temp._RMDOption == null) return false;
                else if (!(this._RMDOption.equals(temp._RMDOption))) 
                    return false;
            }
            else if (temp._RMDOption != null)
                return false;
            if (this._RMDAmount != null) {
                if (temp._RMDAmount == null) return false;
                else if (!(this._RMDAmount.equals(temp._RMDAmount))) 
                    return false;
            }
            else if (temp._RMDAmount != null)
                return false;
            if (this._seventyAndHalfDate != null) {
                if (temp._seventyAndHalfDate == null) return false;
                else if (!(this._seventyAndHalfDate.equals(temp._seventyAndHalfDate))) 
                    return false;
            }
            else if (temp._seventyAndHalfDate != null)
                return false;
            if (this._fixedGuaranteedMinimumInterestRate != null) {
                if (temp._fixedGuaranteedMinimumInterestRate == null) return false;
                else if (!(this._fixedGuaranteedMinimumInterestRate.equals(temp._fixedGuaranteedMinimumInterestRate))) 
                    return false;
            }
            else if (temp._fixedGuaranteedMinimumInterestRate != null)
                return false;
            if (this._premiumBonusPercentage != null) {
                if (temp._premiumBonusPercentage == null) return false;
                else if (!(this._premiumBonusPercentage.equals(temp._premiumBonusPercentage))) 
                    return false;
            }
            else if (temp._premiumBonusPercentage != null)
                return false;
            if (this._naturalDocVOList != null) {
                if (temp._naturalDocVOList == null) return false;
                else if (!(this._naturalDocVOList.equals(temp._naturalDocVOList))) 
                    return false;
            }
            else if (temp._naturalDocVOList != null)
                return false;
            if (this._productStructureVOList != null) {
                if (temp._productStructureVOList == null) return false;
                else if (!(this._productStructureVOList.equals(temp._productStructureVOList))) 
                    return false;
            }
            else if (temp._productStructureVOList != null)
                return false;
            if (this._projectionsVOList != null) {
                if (temp._projectionsVOList == null) return false;
                else if (!(this._projectionsVOList.equals(temp._projectionsVOList))) 
                    return false;
            }
            else if (temp._projectionsVOList != null)
                return false;
            if (this._projectionSurrenderChargeVOList != null) {
                if (temp._projectionSurrenderChargeVOList == null) return false;
                else if (!(this._projectionSurrenderChargeVOList.equals(temp._projectionSurrenderChargeVOList))) 
                    return false;
            }
            else if (temp._projectionSurrenderChargeVOList != null)
                return false;
            if (this._activitySummaryVOList != null) {
                if (temp._activitySummaryVOList == null) return false;
                else if (!(this._activitySummaryVOList.equals(temp._activitySummaryVOList))) 
                    return false;
            }
            else if (temp._activitySummaryVOList != null)
                return false;
            if (this._investmentRateVOList != null) {
                if (temp._investmentRateVOList == null) return false;
                else if (!(this._investmentRateVOList.equals(temp._investmentRateVOList))) 
                    return false;
            }
            else if (temp._investmentRateVOList != null)
                return false;
            if (this._calculatedValuesVOList != null) {
                if (temp._calculatedValuesVOList == null) return false;
                else if (!(this._calculatedValuesVOList.equals(temp._calculatedValuesVOList))) 
                    return false;
            }
            else if (temp._calculatedValuesVOList != null)
                return false;
            if (this._GIOOptionValueVOList != null) {
                if (temp._GIOOptionValueVOList == null) return false;
                else if (!(this._GIOOptionValueVOList.equals(temp._GIOOptionValueVOList))) 
                    return false;
            }
            else if (temp._GIOOptionValueVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getActivitySummaryVO
     * 
     * @param index
     */
    public edit.common.vo.ActivitySummaryVO getActivitySummaryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _activitySummaryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ActivitySummaryVO) _activitySummaryVOList.elementAt(index);
    } //-- edit.common.vo.ActivitySummaryVO getActivitySummaryVO(int) 

    /**
     * Method getActivitySummaryVO
     */
    public edit.common.vo.ActivitySummaryVO[] getActivitySummaryVO()
    {
        int size = _activitySummaryVOList.size();
        edit.common.vo.ActivitySummaryVO[] mArray = new edit.common.vo.ActivitySummaryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ActivitySummaryVO) _activitySummaryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ActivitySummaryVO[] getActivitySummaryVO() 

    /**
     * Method getActivitySummaryVOCount
     */
    public int getActivitySummaryVOCount()
    {
        return _activitySummaryVOList.size();
    } //-- int getActivitySummaryVOCount() 

    /**
     * Method getBonusRateReturns the value of field 'bonusRate'.
     * 
     * @return the value of field 'bonusRate'.
     */
    public double getBonusRate()
    {
        return this._bonusRate;
    } //-- double getBonusRate() 

    /**
     * Method getCalculatedValuesVO
     * 
     * @param index
     */
    public edit.common.vo.CalculatedValuesVO getCalculatedValuesVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _calculatedValuesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CalculatedValuesVO) _calculatedValuesVOList.elementAt(index);
    } //-- edit.common.vo.CalculatedValuesVO getCalculatedValuesVO(int) 

    /**
     * Method getCalculatedValuesVO
     */
    public edit.common.vo.CalculatedValuesVO[] getCalculatedValuesVO()
    {
        int size = _calculatedValuesVOList.size();
        edit.common.vo.CalculatedValuesVO[] mArray = new edit.common.vo.CalculatedValuesVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CalculatedValuesVO) _calculatedValuesVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CalculatedValuesVO[] getCalculatedValuesVO() 

    /**
     * Method getCalculatedValuesVOCount
     */
    public int getCalculatedValuesVOCount()
    {
        return _calculatedValuesVOList.size();
    } //-- int getCalculatedValuesVOCount() 

    /**
     * Method getCorrespondenceDetailPKReturns the value of field
     * 'correspondenceDetailPK'.
     * 
     * @return the value of field 'correspondenceDetailPK'.
     */
    public long getCorrespondenceDetailPK()
    {
        return this._correspondenceDetailPK;
    } //-- long getCorrespondenceDetailPK() 

    /**
     * Method getCorrespondenceTypeReturns the value of field
     * 'correspondenceType'.
     * 
     * @return the value of field 'correspondenceType'.
     */
    public java.lang.String getCorrespondenceType()
    {
        return this._correspondenceType;
    } //-- java.lang.String getCorrespondenceType() 

    /**
     * Method getCurrentIntRateDateReturns the value of field
     * 'currentIntRateDate'.
     * 
     * @return the value of field 'currentIntRateDate'.
     */
    public java.lang.String getCurrentIntRateDate()
    {
        return this._currentIntRateDate;
    } //-- java.lang.String getCurrentIntRateDate() 

    /**
     * Method getCurrentRateReturns the value of field
     * 'currentRate'.
     * 
     * @return the value of field 'currentRate'.
     */
    public double getCurrentRate()
    {
        return this._currentRate;
    } //-- double getCurrentRate() 

    /**
     * Method getFixedGuaranteedMinimumInterestRateReturns the
     * value of field 'fixedGuaranteedMinimumInterestRate'.
     * 
     * @return the value of field
     * 'fixedGuaranteedMinimumInterestRate'.
     */
    public java.math.BigDecimal getFixedGuaranteedMinimumInterestRate()
    {
        return this._fixedGuaranteedMinimumInterestRate;
    } //-- java.math.BigDecimal getFixedGuaranteedMinimumInterestRate() 

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
     * Method getGIOOptionValueVO
     * 
     * @param index
     */
    public edit.common.vo.GIOOptionValueVO getGIOOptionValueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _GIOOptionValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.GIOOptionValueVO) _GIOOptionValueVOList.elementAt(index);
    } //-- edit.common.vo.GIOOptionValueVO getGIOOptionValueVO(int) 

    /**
     * Method getGIOOptionValueVO
     */
    public edit.common.vo.GIOOptionValueVO[] getGIOOptionValueVO()
    {
        int size = _GIOOptionValueVOList.size();
        edit.common.vo.GIOOptionValueVO[] mArray = new edit.common.vo.GIOOptionValueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.GIOOptionValueVO) _GIOOptionValueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.GIOOptionValueVO[] getGIOOptionValueVO() 

    /**
     * Method getGIOOptionValueVOCount
     */
    public int getGIOOptionValueVOCount()
    {
        return _GIOOptionValueVOList.size();
    } //-- int getGIOOptionValueVOCount() 

    /**
     * Method getGuaranteedRateReturns the value of field
     * 'guaranteedRate'.
     * 
     * @return the value of field 'guaranteedRate'.
     */
    public double getGuaranteedRate()
    {
        return this._guaranteedRate;
    } //-- double getGuaranteedRate() 

    /**
     * Method getInitialGuaranteePeriodReturns the value of field
     * 'initialGuaranteePeriod'.
     * 
     * @return the value of field 'initialGuaranteePeriod'.
     */
    public int getInitialGuaranteePeriod()
    {
        return this._initialGuaranteePeriod;
    } //-- int getInitialGuaranteePeriod() 

    /**
     * Method getInvestmentRateVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentRateVO getInvestmentRateVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentRateVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentRateVO) _investmentRateVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentRateVO getInvestmentRateVO(int) 

    /**
     * Method getInvestmentRateVO
     */
    public edit.common.vo.InvestmentRateVO[] getInvestmentRateVO()
    {
        int size = _investmentRateVOList.size();
        edit.common.vo.InvestmentRateVO[] mArray = new edit.common.vo.InvestmentRateVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentRateVO) _investmentRateVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentRateVO[] getInvestmentRateVO() 

    /**
     * Method getInvestmentRateVOCount
     */
    public int getInvestmentRateVOCount()
    {
        return _investmentRateVOList.size();
    } //-- int getInvestmentRateVOCount() 

    /**
     * Method getNaturalDocVO
     * 
     * @param index
     */
    public edit.common.vo.NaturalDocVO getNaturalDocVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _naturalDocVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.NaturalDocVO) _naturalDocVOList.elementAt(index);
    } //-- edit.common.vo.NaturalDocVO getNaturalDocVO(int) 

    /**
     * Method getNaturalDocVO
     */
    public edit.common.vo.NaturalDocVO[] getNaturalDocVO()
    {
        int size = _naturalDocVOList.size();
        edit.common.vo.NaturalDocVO[] mArray = new edit.common.vo.NaturalDocVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.NaturalDocVO) _naturalDocVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.NaturalDocVO[] getNaturalDocVO() 

    /**
     * Method getNaturalDocVOCount
     */
    public int getNaturalDocVOCount()
    {
        return _naturalDocVOList.size();
    } //-- int getNaturalDocVOCount() 

    /**
     * Method getPremiumBonusPercentageReturns the value of field
     * 'premiumBonusPercentage'.
     * 
     * @return the value of field 'premiumBonusPercentage'.
     */
    public java.math.BigDecimal getPremiumBonusPercentage()
    {
        return this._premiumBonusPercentage;
    } //-- java.math.BigDecimal getPremiumBonusPercentage() 

    /**
     * Method getProductStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductStructureVO getProductStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProductStructureVO) _productStructureVOList.elementAt(index);
    } //-- edit.common.vo.ProductStructureVO getProductStructureVO(int) 

    /**
     * Method getProductStructureVO
     */
    public edit.common.vo.ProductStructureVO[] getProductStructureVO()
    {
        int size = _productStructureVOList.size();
        edit.common.vo.ProductStructureVO[] mArray = new edit.common.vo.ProductStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProductStructureVO) _productStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProductStructureVO[] getProductStructureVO() 

    /**
     * Method getProductStructureVOCount
     */
    public int getProductStructureVOCount()
    {
        return _productStructureVOList.size();
    } //-- int getProductStructureVOCount() 

    /**
     * Method getProjectionSurrenderChargeVO
     * 
     * @param index
     */
    public edit.common.vo.ProjectionSurrenderChargeVO getProjectionSurrenderChargeVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _projectionSurrenderChargeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProjectionSurrenderChargeVO) _projectionSurrenderChargeVOList.elementAt(index);
    } //-- edit.common.vo.ProjectionSurrenderChargeVO getProjectionSurrenderChargeVO(int) 

    /**
     * Method getProjectionSurrenderChargeVO
     */
    public edit.common.vo.ProjectionSurrenderChargeVO[] getProjectionSurrenderChargeVO()
    {
        int size = _projectionSurrenderChargeVOList.size();
        edit.common.vo.ProjectionSurrenderChargeVO[] mArray = new edit.common.vo.ProjectionSurrenderChargeVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProjectionSurrenderChargeVO) _projectionSurrenderChargeVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProjectionSurrenderChargeVO[] getProjectionSurrenderChargeVO() 

    /**
     * Method getProjectionSurrenderChargeVOCount
     */
    public int getProjectionSurrenderChargeVOCount()
    {
        return _projectionSurrenderChargeVOList.size();
    } //-- int getProjectionSurrenderChargeVOCount() 

    /**
     * Method getProjectionsVO
     * 
     * @param index
     */
    public edit.common.vo.ProjectionsVO getProjectionsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _projectionsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProjectionsVO) _projectionsVOList.elementAt(index);
    } //-- edit.common.vo.ProjectionsVO getProjectionsVO(int) 

    /**
     * Method getProjectionsVO
     */
    public edit.common.vo.ProjectionsVO[] getProjectionsVO()
    {
        int size = _projectionsVOList.size();
        edit.common.vo.ProjectionsVO[] mArray = new edit.common.vo.ProjectionsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProjectionsVO) _projectionsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProjectionsVO[] getProjectionsVO() 

    /**
     * Method getProjectionsVOCount
     */
    public int getProjectionsVOCount()
    {
        return _projectionsVOList.size();
    } //-- int getProjectionsVOCount() 

    /**
     * Method getRMDAmountReturns the value of field 'RMDAmount'.
     * 
     * @return the value of field 'RMDAmount'.
     */
    public java.math.BigDecimal getRMDAmount()
    {
        return this._RMDAmount;
    } //-- java.math.BigDecimal getRMDAmount() 

    /**
     * Method getRMDOptionReturns the value of field 'RMDOption'.
     * 
     * @return the value of field 'RMDOption'.
     */
    public java.lang.String getRMDOption()
    {
        return this._RMDOption;
    } //-- java.lang.String getRMDOption() 

    /**
     * Method getSeventyAndHalfDateReturns the value of field
     * 'seventyAndHalfDate'.
     * 
     * @return the value of field 'seventyAndHalfDate'.
     */
    public java.lang.String getSeventyAndHalfDate()
    {
        return this._seventyAndHalfDate;
    } //-- java.lang.String getSeventyAndHalfDate() 

    /**
     * Method hasBonusRate
     */
    public boolean hasBonusRate()
    {
        return this._has_bonusRate;
    } //-- boolean hasBonusRate() 

    /**
     * Method hasCorrespondenceDetailPK
     */
    public boolean hasCorrespondenceDetailPK()
    {
        return this._has_correspondenceDetailPK;
    } //-- boolean hasCorrespondenceDetailPK() 

    /**
     * Method hasCurrentRate
     */
    public boolean hasCurrentRate()
    {
        return this._has_currentRate;
    } //-- boolean hasCurrentRate() 

    /**
     * Method hasFreeLookDays
     */
    public boolean hasFreeLookDays()
    {
        return this._has_freeLookDays;
    } //-- boolean hasFreeLookDays() 

    /**
     * Method hasGuaranteedRate
     */
    public boolean hasGuaranteedRate()
    {
        return this._has_guaranteedRate;
    } //-- boolean hasGuaranteedRate() 

    /**
     * Method hasInitialGuaranteePeriod
     */
    public boolean hasInitialGuaranteePeriod()
    {
        return this._has_initialGuaranteePeriod;
    } //-- boolean hasInitialGuaranteePeriod() 

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
     * Method removeActivitySummaryVO
     * 
     * @param index
     */
    public edit.common.vo.ActivitySummaryVO removeActivitySummaryVO(int index)
    {
        java.lang.Object obj = _activitySummaryVOList.elementAt(index);
        _activitySummaryVOList.removeElementAt(index);
        return (edit.common.vo.ActivitySummaryVO) obj;
    } //-- edit.common.vo.ActivitySummaryVO removeActivitySummaryVO(int) 

    /**
     * Method removeAllActivitySummaryVO
     */
    public void removeAllActivitySummaryVO()
    {
        _activitySummaryVOList.removeAllElements();
    } //-- void removeAllActivitySummaryVO() 

    /**
     * Method removeAllCalculatedValuesVO
     */
    public void removeAllCalculatedValuesVO()
    {
        _calculatedValuesVOList.removeAllElements();
    } //-- void removeAllCalculatedValuesVO() 

    /**
     * Method removeAllGIOOptionValueVO
     */
    public void removeAllGIOOptionValueVO()
    {
        _GIOOptionValueVOList.removeAllElements();
    } //-- void removeAllGIOOptionValueVO() 

    /**
     * Method removeAllInvestmentRateVO
     */
    public void removeAllInvestmentRateVO()
    {
        _investmentRateVOList.removeAllElements();
    } //-- void removeAllInvestmentRateVO() 

    /**
     * Method removeAllNaturalDocVO
     */
    public void removeAllNaturalDocVO()
    {
        _naturalDocVOList.removeAllElements();
    } //-- void removeAllNaturalDocVO() 

    /**
     * Method removeAllProductStructureVO
     */
    public void removeAllProductStructureVO()
    {
        _productStructureVOList.removeAllElements();
    } //-- void removeAllProductStructureVO() 

    /**
     * Method removeAllProjectionSurrenderChargeVO
     */
    public void removeAllProjectionSurrenderChargeVO()
    {
        _projectionSurrenderChargeVOList.removeAllElements();
    } //-- void removeAllProjectionSurrenderChargeVO() 

    /**
     * Method removeAllProjectionsVO
     */
    public void removeAllProjectionsVO()
    {
        _projectionsVOList.removeAllElements();
    } //-- void removeAllProjectionsVO() 

    /**
     * Method removeCalculatedValuesVO
     * 
     * @param index
     */
    public edit.common.vo.CalculatedValuesVO removeCalculatedValuesVO(int index)
    {
        java.lang.Object obj = _calculatedValuesVOList.elementAt(index);
        _calculatedValuesVOList.removeElementAt(index);
        return (edit.common.vo.CalculatedValuesVO) obj;
    } //-- edit.common.vo.CalculatedValuesVO removeCalculatedValuesVO(int) 

    /**
     * Method removeGIOOptionValueVO
     * 
     * @param index
     */
    public edit.common.vo.GIOOptionValueVO removeGIOOptionValueVO(int index)
    {
        java.lang.Object obj = _GIOOptionValueVOList.elementAt(index);
        _GIOOptionValueVOList.removeElementAt(index);
        return (edit.common.vo.GIOOptionValueVO) obj;
    } //-- edit.common.vo.GIOOptionValueVO removeGIOOptionValueVO(int) 

    /**
     * Method removeInvestmentRateVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentRateVO removeInvestmentRateVO(int index)
    {
        java.lang.Object obj = _investmentRateVOList.elementAt(index);
        _investmentRateVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentRateVO) obj;
    } //-- edit.common.vo.InvestmentRateVO removeInvestmentRateVO(int) 

    /**
     * Method removeNaturalDocVO
     * 
     * @param index
     */
    public edit.common.vo.NaturalDocVO removeNaturalDocVO(int index)
    {
        java.lang.Object obj = _naturalDocVOList.elementAt(index);
        _naturalDocVOList.removeElementAt(index);
        return (edit.common.vo.NaturalDocVO) obj;
    } //-- edit.common.vo.NaturalDocVO removeNaturalDocVO(int) 

    /**
     * Method removeProductStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductStructureVO removeProductStructureVO(int index)
    {
        java.lang.Object obj = _productStructureVOList.elementAt(index);
        _productStructureVOList.removeElementAt(index);
        return (edit.common.vo.ProductStructureVO) obj;
    } //-- edit.common.vo.ProductStructureVO removeProductStructureVO(int) 

    /**
     * Method removeProjectionSurrenderChargeVO
     * 
     * @param index
     */
    public edit.common.vo.ProjectionSurrenderChargeVO removeProjectionSurrenderChargeVO(int index)
    {
        java.lang.Object obj = _projectionSurrenderChargeVOList.elementAt(index);
        _projectionSurrenderChargeVOList.removeElementAt(index);
        return (edit.common.vo.ProjectionSurrenderChargeVO) obj;
    } //-- edit.common.vo.ProjectionSurrenderChargeVO removeProjectionSurrenderChargeVO(int) 

    /**
     * Method removeProjectionsVO
     * 
     * @param index
     */
    public edit.common.vo.ProjectionsVO removeProjectionsVO(int index)
    {
        java.lang.Object obj = _projectionsVOList.elementAt(index);
        _projectionsVOList.removeElementAt(index);
        return (edit.common.vo.ProjectionsVO) obj;
    } //-- edit.common.vo.ProjectionsVO removeProjectionsVO(int) 

    /**
     * Method setActivitySummaryVO
     * 
     * @param index
     * @param vActivitySummaryVO
     */
    public void setActivitySummaryVO(int index, edit.common.vo.ActivitySummaryVO vActivitySummaryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _activitySummaryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vActivitySummaryVO.setParentVO(this.getClass(), this);
        _activitySummaryVOList.setElementAt(vActivitySummaryVO, index);
    } //-- void setActivitySummaryVO(int, edit.common.vo.ActivitySummaryVO) 

    /**
     * Method setActivitySummaryVO
     * 
     * @param activitySummaryVOArray
     */
    public void setActivitySummaryVO(edit.common.vo.ActivitySummaryVO[] activitySummaryVOArray)
    {
        //-- copy array
        _activitySummaryVOList.removeAllElements();
        for (int i = 0; i < activitySummaryVOArray.length; i++) {
            activitySummaryVOArray[i].setParentVO(this.getClass(), this);
            _activitySummaryVOList.addElement(activitySummaryVOArray[i]);
        }
    } //-- void setActivitySummaryVO(edit.common.vo.ActivitySummaryVO) 

    /**
     * Method setBonusRateSets the value of field 'bonusRate'.
     * 
     * @param bonusRate the value of field 'bonusRate'.
     */
    public void setBonusRate(double bonusRate)
    {
        this._bonusRate = bonusRate;
        
        super.setVoChanged(true);
        this._has_bonusRate = true;
    } //-- void setBonusRate(double) 

    /**
     * Method setCalculatedValuesVO
     * 
     * @param index
     * @param vCalculatedValuesVO
     */
    public void setCalculatedValuesVO(int index, edit.common.vo.CalculatedValuesVO vCalculatedValuesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _calculatedValuesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCalculatedValuesVO.setParentVO(this.getClass(), this);
        _calculatedValuesVOList.setElementAt(vCalculatedValuesVO, index);
    } //-- void setCalculatedValuesVO(int, edit.common.vo.CalculatedValuesVO) 

    /**
     * Method setCalculatedValuesVO
     * 
     * @param calculatedValuesVOArray
     */
    public void setCalculatedValuesVO(edit.common.vo.CalculatedValuesVO[] calculatedValuesVOArray)
    {
        //-- copy array
        _calculatedValuesVOList.removeAllElements();
        for (int i = 0; i < calculatedValuesVOArray.length; i++) {
            calculatedValuesVOArray[i].setParentVO(this.getClass(), this);
            _calculatedValuesVOList.addElement(calculatedValuesVOArray[i]);
        }
    } //-- void setCalculatedValuesVO(edit.common.vo.CalculatedValuesVO) 

    /**
     * Method setCorrespondenceDetailPKSets the value of field
     * 'correspondenceDetailPK'.
     * 
     * @param correspondenceDetailPK the value of field
     * 'correspondenceDetailPK'.
     */
    public void setCorrespondenceDetailPK(long correspondenceDetailPK)
    {
        this._correspondenceDetailPK = correspondenceDetailPK;
        
        super.setVoChanged(true);
        this._has_correspondenceDetailPK = true;
    } //-- void setCorrespondenceDetailPK(long) 

    /**
     * Method setCorrespondenceTypeSets the value of field
     * 'correspondenceType'.
     * 
     * @param correspondenceType the value of field
     * 'correspondenceType'.
     */
    public void setCorrespondenceType(java.lang.String correspondenceType)
    {
        this._correspondenceType = correspondenceType;
        
        super.setVoChanged(true);
    } //-- void setCorrespondenceType(java.lang.String) 

    /**
     * Method setCurrentIntRateDateSets the value of field
     * 'currentIntRateDate'.
     * 
     * @param currentIntRateDate the value of field
     * 'currentIntRateDate'.
     */
    public void setCurrentIntRateDate(java.lang.String currentIntRateDate)
    {
        this._currentIntRateDate = currentIntRateDate;
        
        super.setVoChanged(true);
    } //-- void setCurrentIntRateDate(java.lang.String) 

    /**
     * Method setCurrentRateSets the value of field 'currentRate'.
     * 
     * @param currentRate the value of field 'currentRate'.
     */
    public void setCurrentRate(double currentRate)
    {
        this._currentRate = currentRate;
        
        super.setVoChanged(true);
        this._has_currentRate = true;
    } //-- void setCurrentRate(double) 

    /**
     * Method setFixedGuaranteedMinimumInterestRateSets the value
     * of field 'fixedGuaranteedMinimumInterestRate'.
     * 
     * @param fixedGuaranteedMinimumInterestRate the value of field
     * 'fixedGuaranteedMinimumInterestRate'.
     */
    public void setFixedGuaranteedMinimumInterestRate(java.math.BigDecimal fixedGuaranteedMinimumInterestRate)
    {
        this._fixedGuaranteedMinimumInterestRate = fixedGuaranteedMinimumInterestRate;
        
        super.setVoChanged(true);
    } //-- void setFixedGuaranteedMinimumInterestRate(java.math.BigDecimal) 

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
     * Method setGIOOptionValueVO
     * 
     * @param index
     * @param vGIOOptionValueVO
     */
    public void setGIOOptionValueVO(int index, edit.common.vo.GIOOptionValueVO vGIOOptionValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _GIOOptionValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vGIOOptionValueVO.setParentVO(this.getClass(), this);
        _GIOOptionValueVOList.setElementAt(vGIOOptionValueVO, index);
    } //-- void setGIOOptionValueVO(int, edit.common.vo.GIOOptionValueVO) 

    /**
     * Method setGIOOptionValueVO
     * 
     * @param GIOOptionValueVOArray
     */
    public void setGIOOptionValueVO(edit.common.vo.GIOOptionValueVO[] GIOOptionValueVOArray)
    {
        //-- copy array
        _GIOOptionValueVOList.removeAllElements();
        for (int i = 0; i < GIOOptionValueVOArray.length; i++) {
            GIOOptionValueVOArray[i].setParentVO(this.getClass(), this);
            _GIOOptionValueVOList.addElement(GIOOptionValueVOArray[i]);
        }
    } //-- void setGIOOptionValueVO(edit.common.vo.GIOOptionValueVO) 

    /**
     * Method setGuaranteedRateSets the value of field
     * 'guaranteedRate'.
     * 
     * @param guaranteedRate the value of field 'guaranteedRate'.
     */
    public void setGuaranteedRate(double guaranteedRate)
    {
        this._guaranteedRate = guaranteedRate;
        
        super.setVoChanged(true);
        this._has_guaranteedRate = true;
    } //-- void setGuaranteedRate(double) 

    /**
     * Method setInitialGuaranteePeriodSets the value of field
     * 'initialGuaranteePeriod'.
     * 
     * @param initialGuaranteePeriod the value of field
     * 'initialGuaranteePeriod'.
     */
    public void setInitialGuaranteePeriod(int initialGuaranteePeriod)
    {
        this._initialGuaranteePeriod = initialGuaranteePeriod;
        
        super.setVoChanged(true);
        this._has_initialGuaranteePeriod = true;
    } //-- void setInitialGuaranteePeriod(int) 

    /**
     * Method setInvestmentRateVO
     * 
     * @param index
     * @param vInvestmentRateVO
     */
    public void setInvestmentRateVO(int index, edit.common.vo.InvestmentRateVO vInvestmentRateVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentRateVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentRateVO.setParentVO(this.getClass(), this);
        _investmentRateVOList.setElementAt(vInvestmentRateVO, index);
    } //-- void setInvestmentRateVO(int, edit.common.vo.InvestmentRateVO) 

    /**
     * Method setInvestmentRateVO
     * 
     * @param investmentRateVOArray
     */
    public void setInvestmentRateVO(edit.common.vo.InvestmentRateVO[] investmentRateVOArray)
    {
        //-- copy array
        _investmentRateVOList.removeAllElements();
        for (int i = 0; i < investmentRateVOArray.length; i++) {
            investmentRateVOArray[i].setParentVO(this.getClass(), this);
            _investmentRateVOList.addElement(investmentRateVOArray[i]);
        }
    } //-- void setInvestmentRateVO(edit.common.vo.InvestmentRateVO) 

    /**
     * Method setNaturalDocVO
     * 
     * @param index
     * @param vNaturalDocVO
     */
    public void setNaturalDocVO(int index, edit.common.vo.NaturalDocVO vNaturalDocVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _naturalDocVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vNaturalDocVO.setParentVO(this.getClass(), this);
        _naturalDocVOList.setElementAt(vNaturalDocVO, index);
    } //-- void setNaturalDocVO(int, edit.common.vo.NaturalDocVO) 

    /**
     * Method setNaturalDocVO
     * 
     * @param naturalDocVOArray
     */
    public void setNaturalDocVO(edit.common.vo.NaturalDocVO[] naturalDocVOArray)
    {
        //-- copy array
        _naturalDocVOList.removeAllElements();
        for (int i = 0; i < naturalDocVOArray.length; i++) {
            naturalDocVOArray[i].setParentVO(this.getClass(), this);
            _naturalDocVOList.addElement(naturalDocVOArray[i]);
        }
    } //-- void setNaturalDocVO(edit.common.vo.NaturalDocVO) 

    /**
     * Method setPremiumBonusPercentageSets the value of field
     * 'premiumBonusPercentage'.
     * 
     * @param premiumBonusPercentage the value of field
     * 'premiumBonusPercentage'.
     */
    public void setPremiumBonusPercentage(java.math.BigDecimal premiumBonusPercentage)
    {
        this._premiumBonusPercentage = premiumBonusPercentage;
        
        super.setVoChanged(true);
    } //-- void setPremiumBonusPercentage(java.math.BigDecimal) 

    /**
     * Method setProductStructureVO
     * 
     * @param index
     * @param vProductStructureVO
     */
    public void setProductStructureVO(int index, edit.common.vo.ProductStructureVO vProductStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProductStructureVO.setParentVO(this.getClass(), this);
        _productStructureVOList.setElementAt(vProductStructureVO, index);
    } //-- void setProductStructureVO(int, edit.common.vo.ProductStructureVO) 

    /**
     * Method setProductStructureVO
     * 
     * @param productStructureVOArray
     */
    public void setProductStructureVO(edit.common.vo.ProductStructureVO[] productStructureVOArray)
    {
        //-- copy array
        _productStructureVOList.removeAllElements();
        for (int i = 0; i < productStructureVOArray.length; i++) {
            productStructureVOArray[i].setParentVO(this.getClass(), this);
            _productStructureVOList.addElement(productStructureVOArray[i]);
        }
    } //-- void setProductStructureVO(edit.common.vo.ProductStructureVO) 

    /**
     * Method setProjectionSurrenderChargeVO
     * 
     * @param index
     * @param vProjectionSurrenderChargeVO
     */
    public void setProjectionSurrenderChargeVO(int index, edit.common.vo.ProjectionSurrenderChargeVO vProjectionSurrenderChargeVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _projectionSurrenderChargeVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProjectionSurrenderChargeVO.setParentVO(this.getClass(), this);
        _projectionSurrenderChargeVOList.setElementAt(vProjectionSurrenderChargeVO, index);
    } //-- void setProjectionSurrenderChargeVO(int, edit.common.vo.ProjectionSurrenderChargeVO) 

    /**
     * Method setProjectionSurrenderChargeVO
     * 
     * @param projectionSurrenderChargeVOArray
     */
    public void setProjectionSurrenderChargeVO(edit.common.vo.ProjectionSurrenderChargeVO[] projectionSurrenderChargeVOArray)
    {
        //-- copy array
        _projectionSurrenderChargeVOList.removeAllElements();
        for (int i = 0; i < projectionSurrenderChargeVOArray.length; i++) {
            projectionSurrenderChargeVOArray[i].setParentVO(this.getClass(), this);
            _projectionSurrenderChargeVOList.addElement(projectionSurrenderChargeVOArray[i]);
        }
    } //-- void setProjectionSurrenderChargeVO(edit.common.vo.ProjectionSurrenderChargeVO) 

    /**
     * Method setProjectionsVO
     * 
     * @param index
     * @param vProjectionsVO
     */
    public void setProjectionsVO(int index, edit.common.vo.ProjectionsVO vProjectionsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _projectionsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProjectionsVO.setParentVO(this.getClass(), this);
        _projectionsVOList.setElementAt(vProjectionsVO, index);
    } //-- void setProjectionsVO(int, edit.common.vo.ProjectionsVO) 

    /**
     * Method setProjectionsVO
     * 
     * @param projectionsVOArray
     */
    public void setProjectionsVO(edit.common.vo.ProjectionsVO[] projectionsVOArray)
    {
        //-- copy array
        _projectionsVOList.removeAllElements();
        for (int i = 0; i < projectionsVOArray.length; i++) {
            projectionsVOArray[i].setParentVO(this.getClass(), this);
            _projectionsVOList.addElement(projectionsVOArray[i]);
        }
    } //-- void setProjectionsVO(edit.common.vo.ProjectionsVO) 

    /**
     * Method setRMDAmountSets the value of field 'RMDAmount'.
     * 
     * @param RMDAmount the value of field 'RMDAmount'.
     */
    public void setRMDAmount(java.math.BigDecimal RMDAmount)
    {
        this._RMDAmount = RMDAmount;
        
        super.setVoChanged(true);
    } //-- void setRMDAmount(java.math.BigDecimal) 

    /**
     * Method setRMDOptionSets the value of field 'RMDOption'.
     * 
     * @param RMDOption the value of field 'RMDOption'.
     */
    public void setRMDOption(java.lang.String RMDOption)
    {
        this._RMDOption = RMDOption;
        
        super.setVoChanged(true);
    } //-- void setRMDOption(java.lang.String) 

    /**
     * Method setSeventyAndHalfDateSets the value of field
     * 'seventyAndHalfDate'.
     * 
     * @param seventyAndHalfDate the value of field
     * 'seventyAndHalfDate'.
     */
    public void setSeventyAndHalfDate(java.lang.String seventyAndHalfDate)
    {
        this._seventyAndHalfDate = seventyAndHalfDate;
        
        super.setVoChanged(true);
    } //-- void setSeventyAndHalfDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CorrespondenceDetailVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CorrespondenceDetailVO) Unmarshaller.unmarshal(edit.common.vo.CorrespondenceDetailVO.class, reader);
    } //-- edit.common.vo.CorrespondenceDetailVO unmarshal(java.io.Reader) 

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
