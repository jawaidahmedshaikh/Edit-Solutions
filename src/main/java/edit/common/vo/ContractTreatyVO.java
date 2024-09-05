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
 * Class ContractTreatyVO.
 * 
 * @version $Revision$ $Date$
 */
public class ContractTreatyVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _contractTreatyPK
     */
    private long _contractTreatyPK;

    /**
     * keeps track of state for field: _contractTreatyPK
     */
    private boolean _has_contractTreatyPK;

    /**
     * Field _segmentFK
     */
    private long _segmentFK;

    /**
     * keeps track of state for field: _segmentFK
     */
    private boolean _has_segmentFK;

    /**
     * Field _treatyFK
     */
    private long _treatyFK;

    /**
     * keeps track of state for field: _treatyFK
     */
    private boolean _has_treatyFK;

    /**
     * Field _contractGroupFK
     */
    private long _contractGroupFK;

    /**
     * keeps track of state for field: _contractGroupFK
     */
    private boolean _has_contractGroupFK;

    /**
     * Field _reinsuranceIndicatorCT
     */
    private java.lang.String _reinsuranceIndicatorCT;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _reinsuranceClassCT
     */
    private java.lang.String _reinsuranceClassCT;

    /**
     * Field _treatyTypeCT
     */
    private java.lang.String _treatyTypeCT;

    /**
     * Field _retentionAmount
     */
    private java.math.BigDecimal _retentionAmount;

    /**
     * Field _poolPercentage
     */
    private java.math.BigDecimal _poolPercentage;

    /**
     * Field _reinsuranceTypeCT
     */
    private java.lang.String _reinsuranceTypeCT;

    /**
     * Field _tableRatingCT
     */
    private java.lang.String _tableRatingCT;

    /**
     * Field _flatExtra
     */
    private java.math.BigDecimal _flatExtra;

    /**
     * Field _flatExtraAge
     */
    private int _flatExtraAge;

    /**
     * keeps track of state for field: _flatExtraAge
     */
    private boolean _has_flatExtraAge;

    /**
     * Field _flatExtraDuration
     */
    private int _flatExtraDuration;

    /**
     * keeps track of state for field: _flatExtraDuration
     */
    private boolean _has_flatExtraDuration;

    /**
     * Field _percentExtra
     */
    private java.math.BigDecimal _percentExtra;

    /**
     * Field _percentExtraAge
     */
    private int _percentExtraAge;

    /**
     * keeps track of state for field: _percentExtraAge
     */
    private boolean _has_percentExtraAge;

    /**
     * Field _percentExtraDuration
     */
    private int _percentExtraDuration;

    /**
     * keeps track of state for field: _percentExtraDuration
     */
    private boolean _has_percentExtraDuration;

    /**
     * Field _maxReinsuranceAmount
     */
    private java.math.BigDecimal _maxReinsuranceAmount;

    /**
     * Field _treatyOverrideInd
     */
    private java.lang.String _treatyOverrideInd;

    /**
     * Field _policyOverrideInd
     */
    private java.lang.String _policyOverrideInd;

    /**
     * Field _status
     */
    private java.lang.String _status;

    /**
     * Field _reinsuranceHistoryVOList
     */
    private java.util.Vector _reinsuranceHistoryVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ContractTreatyVO() {
        super();
        _reinsuranceHistoryVOList = new Vector();
    } //-- edit.common.vo.ContractTreatyVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addReinsuranceHistoryVO
     * 
     * @param vReinsuranceHistoryVO
     */
    public void addReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO vReinsuranceHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceHistoryVO.setParentVO(this.getClass(), this);
        _reinsuranceHistoryVOList.addElement(vReinsuranceHistoryVO);
    } //-- void addReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method addReinsuranceHistoryVO
     * 
     * @param index
     * @param vReinsuranceHistoryVO
     */
    public void addReinsuranceHistoryVO(int index, edit.common.vo.ReinsuranceHistoryVO vReinsuranceHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vReinsuranceHistoryVO.setParentVO(this.getClass(), this);
        _reinsuranceHistoryVOList.insertElementAt(vReinsuranceHistoryVO, index);
    } //-- void addReinsuranceHistoryVO(int, edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method enumerateReinsuranceHistoryVO
     */
    public java.util.Enumeration enumerateReinsuranceHistoryVO()
    {
        return _reinsuranceHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateReinsuranceHistoryVO() 

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
        
        if (obj instanceof ContractTreatyVO) {
        
            ContractTreatyVO temp = (ContractTreatyVO)obj;
            if (this._contractTreatyPK != temp._contractTreatyPK)
                return false;
            if (this._has_contractTreatyPK != temp._has_contractTreatyPK)
                return false;
            if (this._segmentFK != temp._segmentFK)
                return false;
            if (this._has_segmentFK != temp._has_segmentFK)
                return false;
            if (this._treatyFK != temp._treatyFK)
                return false;
            if (this._has_treatyFK != temp._has_treatyFK)
                return false;
            if (this._contractGroupFK != temp._contractGroupFK)
                return false;
            if (this._has_contractGroupFK != temp._has_contractGroupFK)
                return false;
            if (this._reinsuranceIndicatorCT != null) {
                if (temp._reinsuranceIndicatorCT == null) return false;
                else if (!(this._reinsuranceIndicatorCT.equals(temp._reinsuranceIndicatorCT))) 
                    return false;
            }
            else if (temp._reinsuranceIndicatorCT != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._reinsuranceClassCT != null) {
                if (temp._reinsuranceClassCT == null) return false;
                else if (!(this._reinsuranceClassCT.equals(temp._reinsuranceClassCT))) 
                    return false;
            }
            else if (temp._reinsuranceClassCT != null)
                return false;
            if (this._treatyTypeCT != null) {
                if (temp._treatyTypeCT == null) return false;
                else if (!(this._treatyTypeCT.equals(temp._treatyTypeCT))) 
                    return false;
            }
            else if (temp._treatyTypeCT != null)
                return false;
            if (this._retentionAmount != null) {
                if (temp._retentionAmount == null) return false;
                else if (!(this._retentionAmount.equals(temp._retentionAmount))) 
                    return false;
            }
            else if (temp._retentionAmount != null)
                return false;
            if (this._poolPercentage != null) {
                if (temp._poolPercentage == null) return false;
                else if (!(this._poolPercentage.equals(temp._poolPercentage))) 
                    return false;
            }
            else if (temp._poolPercentage != null)
                return false;
            if (this._reinsuranceTypeCT != null) {
                if (temp._reinsuranceTypeCT == null) return false;
                else if (!(this._reinsuranceTypeCT.equals(temp._reinsuranceTypeCT))) 
                    return false;
            }
            else if (temp._reinsuranceTypeCT != null)
                return false;
            if (this._tableRatingCT != null) {
                if (temp._tableRatingCT == null) return false;
                else if (!(this._tableRatingCT.equals(temp._tableRatingCT))) 
                    return false;
            }
            else if (temp._tableRatingCT != null)
                return false;
            if (this._flatExtra != null) {
                if (temp._flatExtra == null) return false;
                else if (!(this._flatExtra.equals(temp._flatExtra))) 
                    return false;
            }
            else if (temp._flatExtra != null)
                return false;
            if (this._flatExtraAge != temp._flatExtraAge)
                return false;
            if (this._has_flatExtraAge != temp._has_flatExtraAge)
                return false;
            if (this._flatExtraDuration != temp._flatExtraDuration)
                return false;
            if (this._has_flatExtraDuration != temp._has_flatExtraDuration)
                return false;
            if (this._percentExtra != null) {
                if (temp._percentExtra == null) return false;
                else if (!(this._percentExtra.equals(temp._percentExtra))) 
                    return false;
            }
            else if (temp._percentExtra != null)
                return false;
            if (this._percentExtraAge != temp._percentExtraAge)
                return false;
            if (this._has_percentExtraAge != temp._has_percentExtraAge)
                return false;
            if (this._percentExtraDuration != temp._percentExtraDuration)
                return false;
            if (this._has_percentExtraDuration != temp._has_percentExtraDuration)
                return false;
            if (this._maxReinsuranceAmount != null) {
                if (temp._maxReinsuranceAmount == null) return false;
                else if (!(this._maxReinsuranceAmount.equals(temp._maxReinsuranceAmount))) 
                    return false;
            }
            else if (temp._maxReinsuranceAmount != null)
                return false;
            if (this._treatyOverrideInd != null) {
                if (temp._treatyOverrideInd == null) return false;
                else if (!(this._treatyOverrideInd.equals(temp._treatyOverrideInd))) 
                    return false;
            }
            else if (temp._treatyOverrideInd != null)
                return false;
            if (this._policyOverrideInd != null) {
                if (temp._policyOverrideInd == null) return false;
                else if (!(this._policyOverrideInd.equals(temp._policyOverrideInd))) 
                    return false;
            }
            else if (temp._policyOverrideInd != null)
                return false;
            if (this._status != null) {
                if (temp._status == null) return false;
                else if (!(this._status.equals(temp._status))) 
                    return false;
            }
            else if (temp._status != null)
                return false;
            if (this._reinsuranceHistoryVOList != null) {
                if (temp._reinsuranceHistoryVOList == null) return false;
                else if (!(this._reinsuranceHistoryVOList.equals(temp._reinsuranceHistoryVOList))) 
                    return false;
            }
            else if (temp._reinsuranceHistoryVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractGroupFKReturns the value of field
     * 'contractGroupFK'.
     * 
     * @return the value of field 'contractGroupFK'.
     */
    public long getContractGroupFK()
    {
        return this._contractGroupFK;
    } //-- long getContractGroupFK() 

    /**
     * Method getContractTreatyPKReturns the value of field
     * 'contractTreatyPK'.
     * 
     * @return the value of field 'contractTreatyPK'.
     */
    public long getContractTreatyPK()
    {
        return this._contractTreatyPK;
    } //-- long getContractTreatyPK() 

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
     * Method getFlatExtraReturns the value of field 'flatExtra'.
     * 
     * @return the value of field 'flatExtra'.
     */
    public java.math.BigDecimal getFlatExtra()
    {
        return this._flatExtra;
    } //-- java.math.BigDecimal getFlatExtra() 

    /**
     * Method getFlatExtraAgeReturns the value of field
     * 'flatExtraAge'.
     * 
     * @return the value of field 'flatExtraAge'.
     */
    public int getFlatExtraAge()
    {
        return this._flatExtraAge;
    } //-- int getFlatExtraAge() 

    /**
     * Method getFlatExtraDurationReturns the value of field
     * 'flatExtraDuration'.
     * 
     * @return the value of field 'flatExtraDuration'.
     */
    public int getFlatExtraDuration()
    {
        return this._flatExtraDuration;
    } //-- int getFlatExtraDuration() 

    /**
     * Method getMaxReinsuranceAmountReturns the value of field
     * 'maxReinsuranceAmount'.
     * 
     * @return the value of field 'maxReinsuranceAmount'.
     */
    public java.math.BigDecimal getMaxReinsuranceAmount()
    {
        return this._maxReinsuranceAmount;
    } //-- java.math.BigDecimal getMaxReinsuranceAmount() 

    /**
     * Method getPercentExtraReturns the value of field
     * 'percentExtra'.
     * 
     * @return the value of field 'percentExtra'.
     */
    public java.math.BigDecimal getPercentExtra()
    {
        return this._percentExtra;
    } //-- java.math.BigDecimal getPercentExtra() 

    /**
     * Method getPercentExtraAgeReturns the value of field
     * 'percentExtraAge'.
     * 
     * @return the value of field 'percentExtraAge'.
     */
    public int getPercentExtraAge()
    {
        return this._percentExtraAge;
    } //-- int getPercentExtraAge() 

    /**
     * Method getPercentExtraDurationReturns the value of field
     * 'percentExtraDuration'.
     * 
     * @return the value of field 'percentExtraDuration'.
     */
    public int getPercentExtraDuration()
    {
        return this._percentExtraDuration;
    } //-- int getPercentExtraDuration() 

    /**
     * Method getPolicyOverrideIndReturns the value of field
     * 'policyOverrideInd'.
     * 
     * @return the value of field 'policyOverrideInd'.
     */
    public java.lang.String getPolicyOverrideInd()
    {
        return this._policyOverrideInd;
    } //-- java.lang.String getPolicyOverrideInd() 

    /**
     * Method getPoolPercentageReturns the value of field
     * 'poolPercentage'.
     * 
     * @return the value of field 'poolPercentage'.
     */
    public java.math.BigDecimal getPoolPercentage()
    {
        return this._poolPercentage;
    } //-- java.math.BigDecimal getPoolPercentage() 

    /**
     * Method getReinsuranceClassCTReturns the value of field
     * 'reinsuranceClassCT'.
     * 
     * @return the value of field 'reinsuranceClassCT'.
     */
    public java.lang.String getReinsuranceClassCT()
    {
        return this._reinsuranceClassCT;
    } //-- java.lang.String getReinsuranceClassCT() 

    /**
     * Method getReinsuranceHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceHistoryVO getReinsuranceHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ReinsuranceHistoryVO) _reinsuranceHistoryVOList.elementAt(index);
    } //-- edit.common.vo.ReinsuranceHistoryVO getReinsuranceHistoryVO(int) 

    /**
     * Method getReinsuranceHistoryVO
     */
    public edit.common.vo.ReinsuranceHistoryVO[] getReinsuranceHistoryVO()
    {
        int size = _reinsuranceHistoryVOList.size();
        edit.common.vo.ReinsuranceHistoryVO[] mArray = new edit.common.vo.ReinsuranceHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ReinsuranceHistoryVO) _reinsuranceHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ReinsuranceHistoryVO[] getReinsuranceHistoryVO() 

    /**
     * Method getReinsuranceHistoryVOCount
     */
    public int getReinsuranceHistoryVOCount()
    {
        return _reinsuranceHistoryVOList.size();
    } //-- int getReinsuranceHistoryVOCount() 

    /**
     * Method getReinsuranceIndicatorCTReturns the value of field
     * 'reinsuranceIndicatorCT'.
     * 
     * @return the value of field 'reinsuranceIndicatorCT'.
     */
    public java.lang.String getReinsuranceIndicatorCT()
    {
        return this._reinsuranceIndicatorCT;
    } //-- java.lang.String getReinsuranceIndicatorCT() 

    /**
     * Method getReinsuranceTypeCTReturns the value of field
     * 'reinsuranceTypeCT'.
     * 
     * @return the value of field 'reinsuranceTypeCT'.
     */
    public java.lang.String getReinsuranceTypeCT()
    {
        return this._reinsuranceTypeCT;
    } //-- java.lang.String getReinsuranceTypeCT() 

    /**
     * Method getRetentionAmountReturns the value of field
     * 'retentionAmount'.
     * 
     * @return the value of field 'retentionAmount'.
     */
    public java.math.BigDecimal getRetentionAmount()
    {
        return this._retentionAmount;
    } //-- java.math.BigDecimal getRetentionAmount() 

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
     * Method getStatusReturns the value of field 'status'.
     * 
     * @return the value of field 'status'.
     */
    public java.lang.String getStatus()
    {
        return this._status;
    } //-- java.lang.String getStatus() 

    /**
     * Method getTableRatingCTReturns the value of field
     * 'tableRatingCT'.
     * 
     * @return the value of field 'tableRatingCT'.
     */
    public java.lang.String getTableRatingCT()
    {
        return this._tableRatingCT;
    } //-- java.lang.String getTableRatingCT() 

    /**
     * Method getTreatyFKReturns the value of field 'treatyFK'.
     * 
     * @return the value of field 'treatyFK'.
     */
    public long getTreatyFK()
    {
        return this._treatyFK;
    } //-- long getTreatyFK() 

    /**
     * Method getTreatyOverrideIndReturns the value of field
     * 'treatyOverrideInd'.
     * 
     * @return the value of field 'treatyOverrideInd'.
     */
    public java.lang.String getTreatyOverrideInd()
    {
        return this._treatyOverrideInd;
    } //-- java.lang.String getTreatyOverrideInd() 

    /**
     * Method getTreatyTypeCTReturns the value of field
     * 'treatyTypeCT'.
     * 
     * @return the value of field 'treatyTypeCT'.
     */
    public java.lang.String getTreatyTypeCT()
    {
        return this._treatyTypeCT;
    } //-- java.lang.String getTreatyTypeCT() 

    /**
     * Method hasContractGroupFK
     */
    public boolean hasContractGroupFK()
    {
        return this._has_contractGroupFK;
    } //-- boolean hasContractGroupFK() 

    /**
     * Method hasContractTreatyPK
     */
    public boolean hasContractTreatyPK()
    {
        return this._has_contractTreatyPK;
    } //-- boolean hasContractTreatyPK() 

    /**
     * Method hasFlatExtraAge
     */
    public boolean hasFlatExtraAge()
    {
        return this._has_flatExtraAge;
    } //-- boolean hasFlatExtraAge() 

    /**
     * Method hasFlatExtraDuration
     */
    public boolean hasFlatExtraDuration()
    {
        return this._has_flatExtraDuration;
    } //-- boolean hasFlatExtraDuration() 

    /**
     * Method hasPercentExtraAge
     */
    public boolean hasPercentExtraAge()
    {
        return this._has_percentExtraAge;
    } //-- boolean hasPercentExtraAge() 

    /**
     * Method hasPercentExtraDuration
     */
    public boolean hasPercentExtraDuration()
    {
        return this._has_percentExtraDuration;
    } //-- boolean hasPercentExtraDuration() 

    /**
     * Method hasSegmentFK
     */
    public boolean hasSegmentFK()
    {
        return this._has_segmentFK;
    } //-- boolean hasSegmentFK() 

    /**
     * Method hasTreatyFK
     */
    public boolean hasTreatyFK()
    {
        return this._has_treatyFK;
    } //-- boolean hasTreatyFK() 

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
     * Method removeAllReinsuranceHistoryVO
     */
    public void removeAllReinsuranceHistoryVO()
    {
        _reinsuranceHistoryVOList.removeAllElements();
    } //-- void removeAllReinsuranceHistoryVO() 

    /**
     * Method removeReinsuranceHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.ReinsuranceHistoryVO removeReinsuranceHistoryVO(int index)
    {
        java.lang.Object obj = _reinsuranceHistoryVOList.elementAt(index);
        _reinsuranceHistoryVOList.removeElementAt(index);
        return (edit.common.vo.ReinsuranceHistoryVO) obj;
    } //-- edit.common.vo.ReinsuranceHistoryVO removeReinsuranceHistoryVO(int) 

    /**
     * Method setContractGroupFKSets the value of field
     * 'contractGroupFK'.
     * 
     * @param contractGroupFK the value of field 'contractGroupFK'.
     */
    public void setContractGroupFK(long contractGroupFK)
    {
        this._contractGroupFK = contractGroupFK;
        
        super.setVoChanged(true);
        this._has_contractGroupFK = true;
    } //-- void setContractGroupFK(long) 

    /**
     * Method setContractTreatyPKSets the value of field
     * 'contractTreatyPK'.
     * 
     * @param contractTreatyPK the value of field 'contractTreatyPK'
     */
    public void setContractTreatyPK(long contractTreatyPK)
    {
        this._contractTreatyPK = contractTreatyPK;
        
        super.setVoChanged(true);
        this._has_contractTreatyPK = true;
    } //-- void setContractTreatyPK(long) 

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
     * Method setFlatExtraSets the value of field 'flatExtra'.
     * 
     * @param flatExtra the value of field 'flatExtra'.
     */
    public void setFlatExtra(java.math.BigDecimal flatExtra)
    {
        this._flatExtra = flatExtra;
        
        super.setVoChanged(true);
    } //-- void setFlatExtra(java.math.BigDecimal) 

    /**
     * Method setFlatExtraAgeSets the value of field
     * 'flatExtraAge'.
     * 
     * @param flatExtraAge the value of field 'flatExtraAge'.
     */
    public void setFlatExtraAge(int flatExtraAge)
    {
        this._flatExtraAge = flatExtraAge;
        
        super.setVoChanged(true);
        this._has_flatExtraAge = true;
    } //-- void setFlatExtraAge(int) 

    /**
     * Method setFlatExtraDurationSets the value of field
     * 'flatExtraDuration'.
     * 
     * @param flatExtraDuration the value of field
     * 'flatExtraDuration'.
     */
    public void setFlatExtraDuration(int flatExtraDuration)
    {
        this._flatExtraDuration = flatExtraDuration;
        
        super.setVoChanged(true);
        this._has_flatExtraDuration = true;
    } //-- void setFlatExtraDuration(int) 

    /**
     * Method setMaxReinsuranceAmountSets the value of field
     * 'maxReinsuranceAmount'.
     * 
     * @param maxReinsuranceAmount the value of field
     * 'maxReinsuranceAmount'.
     */
    public void setMaxReinsuranceAmount(java.math.BigDecimal maxReinsuranceAmount)
    {
        this._maxReinsuranceAmount = maxReinsuranceAmount;
        
        super.setVoChanged(true);
    } //-- void setMaxReinsuranceAmount(java.math.BigDecimal) 

    /**
     * Method setPercentExtraSets the value of field
     * 'percentExtra'.
     * 
     * @param percentExtra the value of field 'percentExtra'.
     */
    public void setPercentExtra(java.math.BigDecimal percentExtra)
    {
        this._percentExtra = percentExtra;
        
        super.setVoChanged(true);
    } //-- void setPercentExtra(java.math.BigDecimal) 

    /**
     * Method setPercentExtraAgeSets the value of field
     * 'percentExtraAge'.
     * 
     * @param percentExtraAge the value of field 'percentExtraAge'.
     */
    public void setPercentExtraAge(int percentExtraAge)
    {
        this._percentExtraAge = percentExtraAge;
        
        super.setVoChanged(true);
        this._has_percentExtraAge = true;
    } //-- void setPercentExtraAge(int) 

    /**
     * Method setPercentExtraDurationSets the value of field
     * 'percentExtraDuration'.
     * 
     * @param percentExtraDuration the value of field
     * 'percentExtraDuration'.
     */
    public void setPercentExtraDuration(int percentExtraDuration)
    {
        this._percentExtraDuration = percentExtraDuration;
        
        super.setVoChanged(true);
        this._has_percentExtraDuration = true;
    } //-- void setPercentExtraDuration(int) 

    /**
     * Method setPolicyOverrideIndSets the value of field
     * 'policyOverrideInd'.
     * 
     * @param policyOverrideInd the value of field
     * 'policyOverrideInd'.
     */
    public void setPolicyOverrideInd(java.lang.String policyOverrideInd)
    {
        this._policyOverrideInd = policyOverrideInd;
        
        super.setVoChanged(true);
    } //-- void setPolicyOverrideInd(java.lang.String) 

    /**
     * Method setPoolPercentageSets the value of field
     * 'poolPercentage'.
     * 
     * @param poolPercentage the value of field 'poolPercentage'.
     */
    public void setPoolPercentage(java.math.BigDecimal poolPercentage)
    {
        this._poolPercentage = poolPercentage;
        
        super.setVoChanged(true);
    } //-- void setPoolPercentage(java.math.BigDecimal) 

    /**
     * Method setReinsuranceClassCTSets the value of field
     * 'reinsuranceClassCT'.
     * 
     * @param reinsuranceClassCT the value of field
     * 'reinsuranceClassCT'.
     */
    public void setReinsuranceClassCT(java.lang.String reinsuranceClassCT)
    {
        this._reinsuranceClassCT = reinsuranceClassCT;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceClassCT(java.lang.String) 

    /**
     * Method setReinsuranceHistoryVO
     * 
     * @param index
     * @param vReinsuranceHistoryVO
     */
    public void setReinsuranceHistoryVO(int index, edit.common.vo.ReinsuranceHistoryVO vReinsuranceHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _reinsuranceHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vReinsuranceHistoryVO.setParentVO(this.getClass(), this);
        _reinsuranceHistoryVOList.setElementAt(vReinsuranceHistoryVO, index);
    } //-- void setReinsuranceHistoryVO(int, edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method setReinsuranceHistoryVO
     * 
     * @param reinsuranceHistoryVOArray
     */
    public void setReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO[] reinsuranceHistoryVOArray)
    {
        //-- copy array
        _reinsuranceHistoryVOList.removeAllElements();
        for (int i = 0; i < reinsuranceHistoryVOArray.length; i++) {
            reinsuranceHistoryVOArray[i].setParentVO(this.getClass(), this);
            _reinsuranceHistoryVOList.addElement(reinsuranceHistoryVOArray[i]);
        }
    } //-- void setReinsuranceHistoryVO(edit.common.vo.ReinsuranceHistoryVO) 

    /**
     * Method setReinsuranceIndicatorCTSets the value of field
     * 'reinsuranceIndicatorCT'.
     * 
     * @param reinsuranceIndicatorCT the value of field
     * 'reinsuranceIndicatorCT'.
     */
    public void setReinsuranceIndicatorCT(java.lang.String reinsuranceIndicatorCT)
    {
        this._reinsuranceIndicatorCT = reinsuranceIndicatorCT;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceIndicatorCT(java.lang.String) 

    /**
     * Method setReinsuranceTypeCTSets the value of field
     * 'reinsuranceTypeCT'.
     * 
     * @param reinsuranceTypeCT the value of field
     * 'reinsuranceTypeCT'.
     */
    public void setReinsuranceTypeCT(java.lang.String reinsuranceTypeCT)
    {
        this._reinsuranceTypeCT = reinsuranceTypeCT;
        
        super.setVoChanged(true);
    } //-- void setReinsuranceTypeCT(java.lang.String) 

    /**
     * Method setRetentionAmountSets the value of field
     * 'retentionAmount'.
     * 
     * @param retentionAmount the value of field 'retentionAmount'.
     */
    public void setRetentionAmount(java.math.BigDecimal retentionAmount)
    {
        this._retentionAmount = retentionAmount;
        
        super.setVoChanged(true);
    } //-- void setRetentionAmount(java.math.BigDecimal) 

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
     * Method setStatusSets the value of field 'status'.
     * 
     * @param status the value of field 'status'.
     */
    public void setStatus(java.lang.String status)
    {
        this._status = status;
        
        super.setVoChanged(true);
    } //-- void setStatus(java.lang.String) 

    /**
     * Method setTableRatingCTSets the value of field
     * 'tableRatingCT'.
     * 
     * @param tableRatingCT the value of field 'tableRatingCT'.
     */
    public void setTableRatingCT(java.lang.String tableRatingCT)
    {
        this._tableRatingCT = tableRatingCT;
        
        super.setVoChanged(true);
    } //-- void setTableRatingCT(java.lang.String) 

    /**
     * Method setTreatyFKSets the value of field 'treatyFK'.
     * 
     * @param treatyFK the value of field 'treatyFK'.
     */
    public void setTreatyFK(long treatyFK)
    {
        this._treatyFK = treatyFK;
        
        super.setVoChanged(true);
        this._has_treatyFK = true;
    } //-- void setTreatyFK(long) 

    /**
     * Method setTreatyOverrideIndSets the value of field
     * 'treatyOverrideInd'.
     * 
     * @param treatyOverrideInd the value of field
     * 'treatyOverrideInd'.
     */
    public void setTreatyOverrideInd(java.lang.String treatyOverrideInd)
    {
        this._treatyOverrideInd = treatyOverrideInd;
        
        super.setVoChanged(true);
    } //-- void setTreatyOverrideInd(java.lang.String) 

    /**
     * Method setTreatyTypeCTSets the value of field
     * 'treatyTypeCT'.
     * 
     * @param treatyTypeCT the value of field 'treatyTypeCT'.
     */
    public void setTreatyTypeCT(java.lang.String treatyTypeCT)
    {
        this._treatyTypeCT = treatyTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTreatyTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ContractTreatyVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ContractTreatyVO) Unmarshaller.unmarshal(edit.common.vo.ContractTreatyVO.class, reader);
    } //-- edit.common.vo.ContractTreatyVO unmarshal(java.io.Reader) 

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
