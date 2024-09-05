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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Used for Correspondence projections reports
 * 
 * @version $Revision$ $Date$
 */
public class ProjectionsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _projectionsPK
     */
    private long _projectionsPK;

    /**
     * keeps track of state for field: _projectionsPK
     */
    private boolean _has_projectionsPK;

    /**
     * Field _type
     */
    private java.lang.String _type;

    /**
     * Field _age
     */
    private int _age;

    /**
     * keeps track of state for field: _age
     */
    private boolean _has_age;

    /**
     * Field _duration
     */
    private int _duration;

    /**
     * keeps track of state for field: _duration
     */
    private boolean _has_duration;

    /**
     * Field _amount
     */
    private java.math.BigDecimal _amount;

    /**
     * Field _guarNetPay
     */
    private java.math.BigDecimal _guarNetPay;

    /**
     * Field _nonGuarNetPay
     */
    private java.math.BigDecimal _nonGuarNetPay;

    /**
     * Field _guarSurrender
     */
    private java.math.BigDecimal _guarSurrender;

    /**
     * Field _nonGuarSurrender
     */
    private java.math.BigDecimal _nonGuarSurrender;

    /**
     * Field _guarPaidUpTerm
     */
    private java.math.BigDecimal _guarPaidUpTerm;

    /**
     * Field _nonGuarPaidUpTerm
     */
    private java.math.BigDecimal _nonGuarPaidUpTerm;

    /**
     * Field _oneYearTerm
     */
    private java.math.BigDecimal _oneYearTerm;

    /**
     * Field _paidUpTermVested
     */
    private java.math.BigDecimal _paidUpTermVested;

    /**
     * Field _vestingPeriod
     */
    private int _vestingPeriod;

    /**
     * keeps track of state for field: _vestingPeriod
     */
    private boolean _has_vestingPeriod;

    /**
     * Field _premiumLoad
     */
    private java.math.BigDecimal _premiumLoad;

    /**
     * Field _anniversaryDate
     */
    private java.lang.String _anniversaryDate;

    /**
     * Field _attainedAge
     */
    private int _attainedAge;

    /**
     * keeps track of state for field: _attainedAge
     */
    private boolean _has_attainedAge;

    /**
     * Field _midPointPUT
     */
    private java.math.BigDecimal _midPointPUT;

    /**
     * Field _midPointPremTerm
     */
    private java.math.BigDecimal _midPointPremTerm;

    /**
     * Field _paidUpAge
     */
    private int _paidUpAge;

    /**
     * keeps track of state for field: _paidUpAge
     */
    private boolean _has_paidUpAge;

    /**
     * Field _deathBenefit
     */
    private java.math.BigDecimal _deathBenefit;

    /**
     * Field _RPU
     */
    private java.math.BigDecimal _RPU;

    /**
     * Field _netPremiumCostIndex10
     */
    private java.math.BigDecimal _netPremiumCostIndex10;

    /**
     * Field _netPremiumCostIndex20
     */
    private java.math.BigDecimal _netPremiumCostIndex20;

    /**
     * Field _surrenderCostIndex10
     */
    private java.math.BigDecimal _surrenderCostIndex10;

    /**
     * Field _surrenderCostIndex20
     */
    private java.math.BigDecimal _surrenderCostIndex20;

    /**
     * Field _nonGuarDeathBenefit
     */
    private java.math.BigDecimal _nonGuarDeathBenefit;

    /**
     * Field _guarNetPremiumCostIndex10
     */
    private java.math.BigDecimal _guarNetPremiumCostIndex10;

    /**
     * Field _guarNetPremiumCostIndex20
     */
    private java.math.BigDecimal _guarNetPremiumCostIndex20;

    /**
     * Field _guarSurrenderCostIndex10
     */
    private java.math.BigDecimal _guarSurrenderCostIndex10;

    /**
     * Field _guarSurrenderCostIndex20
     */
    private java.math.BigDecimal _guarSurrenderCostIndex20;

    /**
     * Field _guarModalPremium
     */
    private java.math.BigDecimal _guarModalPremium;

    /**
     * Field _guarLTCPremium
     */
    private java.math.BigDecimal _guarLTCPremium;

    /**
     * Field _guarDecreaseTermDB
     */
    private java.math.BigDecimal _guarDecreaseTermDB;

    /**
     * Field _paidUpTermDate
     */
    private java.lang.String _paidUpTermDate;

    /**
     * Field _guaranteedEOBPremium
     */
    private java.math.BigDecimal _guaranteedEOBPremium;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProjectionsVO() {
        super();
    } //-- edit.common.vo.ProjectionsVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof ProjectionsVO) {
        
            ProjectionsVO temp = (ProjectionsVO)obj;
            if (this._projectionsPK != temp._projectionsPK)
                return false;
            if (this._has_projectionsPK != temp._has_projectionsPK)
                return false;
            if (this._type != null) {
                if (temp._type == null) return false;
                else if (!(this._type.equals(temp._type))) 
                    return false;
            }
            else if (temp._type != null)
                return false;
            if (this._age != temp._age)
                return false;
            if (this._has_age != temp._has_age)
                return false;
            if (this._duration != temp._duration)
                return false;
            if (this._has_duration != temp._has_duration)
                return false;
            if (this._amount != null) {
                if (temp._amount == null) return false;
                else if (!(this._amount.equals(temp._amount))) 
                    return false;
            }
            else if (temp._amount != null)
                return false;
            if (this._guarNetPay != null) {
                if (temp._guarNetPay == null) return false;
                else if (!(this._guarNetPay.equals(temp._guarNetPay))) 
                    return false;
            }
            else if (temp._guarNetPay != null)
                return false;
            if (this._nonGuarNetPay != null) {
                if (temp._nonGuarNetPay == null) return false;
                else if (!(this._nonGuarNetPay.equals(temp._nonGuarNetPay))) 
                    return false;
            }
            else if (temp._nonGuarNetPay != null)
                return false;
            if (this._guarSurrender != null) {
                if (temp._guarSurrender == null) return false;
                else if (!(this._guarSurrender.equals(temp._guarSurrender))) 
                    return false;
            }
            else if (temp._guarSurrender != null)
                return false;
            if (this._nonGuarSurrender != null) {
                if (temp._nonGuarSurrender == null) return false;
                else if (!(this._nonGuarSurrender.equals(temp._nonGuarSurrender))) 
                    return false;
            }
            else if (temp._nonGuarSurrender != null)
                return false;
            if (this._guarPaidUpTerm != null) {
                if (temp._guarPaidUpTerm == null) return false;
                else if (!(this._guarPaidUpTerm.equals(temp._guarPaidUpTerm))) 
                    return false;
            }
            else if (temp._guarPaidUpTerm != null)
                return false;
            if (this._nonGuarPaidUpTerm != null) {
                if (temp._nonGuarPaidUpTerm == null) return false;
                else if (!(this._nonGuarPaidUpTerm.equals(temp._nonGuarPaidUpTerm))) 
                    return false;
            }
            else if (temp._nonGuarPaidUpTerm != null)
                return false;
            if (this._oneYearTerm != null) {
                if (temp._oneYearTerm == null) return false;
                else if (!(this._oneYearTerm.equals(temp._oneYearTerm))) 
                    return false;
            }
            else if (temp._oneYearTerm != null)
                return false;
            if (this._paidUpTermVested != null) {
                if (temp._paidUpTermVested == null) return false;
                else if (!(this._paidUpTermVested.equals(temp._paidUpTermVested))) 
                    return false;
            }
            else if (temp._paidUpTermVested != null)
                return false;
            if (this._vestingPeriod != temp._vestingPeriod)
                return false;
            if (this._has_vestingPeriod != temp._has_vestingPeriod)
                return false;
            if (this._premiumLoad != null) {
                if (temp._premiumLoad == null) return false;
                else if (!(this._premiumLoad.equals(temp._premiumLoad))) 
                    return false;
            }
            else if (temp._premiumLoad != null)
                return false;
            if (this._anniversaryDate != null) {
                if (temp._anniversaryDate == null) return false;
                else if (!(this._anniversaryDate.equals(temp._anniversaryDate))) 
                    return false;
            }
            else if (temp._anniversaryDate != null)
                return false;
            if (this._attainedAge != temp._attainedAge)
                return false;
            if (this._has_attainedAge != temp._has_attainedAge)
                return false;
            if (this._midPointPUT != null) {
                if (temp._midPointPUT == null) return false;
                else if (!(this._midPointPUT.equals(temp._midPointPUT))) 
                    return false;
            }
            else if (temp._midPointPUT != null)
                return false;
            if (this._midPointPremTerm != null) {
                if (temp._midPointPremTerm == null) return false;
                else if (!(this._midPointPremTerm.equals(temp._midPointPremTerm))) 
                    return false;
            }
            else if (temp._midPointPremTerm != null)
                return false;
            if (this._paidUpAge != temp._paidUpAge)
                return false;
            if (this._has_paidUpAge != temp._has_paidUpAge)
                return false;
            if (this._deathBenefit != null) {
                if (temp._deathBenefit == null) return false;
                else if (!(this._deathBenefit.equals(temp._deathBenefit))) 
                    return false;
            }
            else if (temp._deathBenefit != null)
                return false;
            if (this._RPU != null) {
                if (temp._RPU == null) return false;
                else if (!(this._RPU.equals(temp._RPU))) 
                    return false;
            }
            else if (temp._RPU != null)
                return false;
            if (this._netPremiumCostIndex10 != null) {
                if (temp._netPremiumCostIndex10 == null) return false;
                else if (!(this._netPremiumCostIndex10.equals(temp._netPremiumCostIndex10))) 
                    return false;
            }
            else if (temp._netPremiumCostIndex10 != null)
                return false;
            if (this._netPremiumCostIndex20 != null) {
                if (temp._netPremiumCostIndex20 == null) return false;
                else if (!(this._netPremiumCostIndex20.equals(temp._netPremiumCostIndex20))) 
                    return false;
            }
            else if (temp._netPremiumCostIndex20 != null)
                return false;
            if (this._surrenderCostIndex10 != null) {
                if (temp._surrenderCostIndex10 == null) return false;
                else if (!(this._surrenderCostIndex10.equals(temp._surrenderCostIndex10))) 
                    return false;
            }
            else if (temp._surrenderCostIndex10 != null)
                return false;
            if (this._surrenderCostIndex20 != null) {
                if (temp._surrenderCostIndex20 == null) return false;
                else if (!(this._surrenderCostIndex20.equals(temp._surrenderCostIndex20))) 
                    return false;
            }
            else if (temp._surrenderCostIndex20 != null)
                return false;
            if (this._nonGuarDeathBenefit != null) {
                if (temp._nonGuarDeathBenefit == null) return false;
                else if (!(this._nonGuarDeathBenefit.equals(temp._nonGuarDeathBenefit))) 
                    return false;
            }
            else if (temp._nonGuarDeathBenefit != null)
                return false;
            if (this._guarNetPremiumCostIndex10 != null) {
                if (temp._guarNetPremiumCostIndex10 == null) return false;
                else if (!(this._guarNetPremiumCostIndex10.equals(temp._guarNetPremiumCostIndex10))) 
                    return false;
            }
            else if (temp._guarNetPremiumCostIndex10 != null)
                return false;
            if (this._guarNetPremiumCostIndex20 != null) {
                if (temp._guarNetPremiumCostIndex20 == null) return false;
                else if (!(this._guarNetPremiumCostIndex20.equals(temp._guarNetPremiumCostIndex20))) 
                    return false;
            }
            else if (temp._guarNetPremiumCostIndex20 != null)
                return false;
            if (this._guarSurrenderCostIndex10 != null) {
                if (temp._guarSurrenderCostIndex10 == null) return false;
                else if (!(this._guarSurrenderCostIndex10.equals(temp._guarSurrenderCostIndex10))) 
                    return false;
            }
            else if (temp._guarSurrenderCostIndex10 != null)
                return false;
            if (this._guarSurrenderCostIndex20 != null) {
                if (temp._guarSurrenderCostIndex20 == null) return false;
                else if (!(this._guarSurrenderCostIndex20.equals(temp._guarSurrenderCostIndex20))) 
                    return false;
            }
            else if (temp._guarSurrenderCostIndex20 != null)
                return false;
            if (this._guarModalPremium != null) {
                if (temp._guarModalPremium == null) return false;
                else if (!(this._guarModalPremium.equals(temp._guarModalPremium))) 
                    return false;
            }
            else if (temp._guarModalPremium != null)
                return false;
            if (this._guarLTCPremium != null) {
                if (temp._guarLTCPremium == null) return false;
                else if (!(this._guarLTCPremium.equals(temp._guarLTCPremium))) 
                    return false;
            }
            else if (temp._guarLTCPremium != null)
                return false;
            if (this._guarDecreaseTermDB != null) {
                if (temp._guarDecreaseTermDB == null) return false;
                else if (!(this._guarDecreaseTermDB.equals(temp._guarDecreaseTermDB))) 
                    return false;
            }
            else if (temp._guarDecreaseTermDB != null)
                return false;
            if (this._paidUpTermDate != null) {
                if (temp._paidUpTermDate == null) return false;
                else if (!(this._paidUpTermDate.equals(temp._paidUpTermDate))) 
                    return false;
            }
            else if (temp._paidUpTermDate != null)
                return false;
            if (this._guaranteedEOBPremium != null) {
                if (temp._guaranteedEOBPremium == null) return false;
                else if (!(this._guaranteedEOBPremium.equals(temp._guaranteedEOBPremium))) 
                    return false;
            }
            else if (temp._guaranteedEOBPremium != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgeReturns the value of field 'age'.
     * 
     * @return the value of field 'age'.
     */
    public int getAge()
    {
        return this._age;
    } //-- int getAge() 

    /**
     * Method getAmountReturns the value of field 'amount'.
     * 
     * @return the value of field 'amount'.
     */
    public java.math.BigDecimal getAmount()
    {
        return this._amount;
    } //-- java.math.BigDecimal getAmount() 

    /**
     * Method getAnniversaryDateReturns the value of field
     * 'anniversaryDate'.
     * 
     * @return the value of field 'anniversaryDate'.
     */
    public java.lang.String getAnniversaryDate()
    {
        return this._anniversaryDate;
    } //-- java.lang.String getAnniversaryDate() 

    /**
     * Method getAttainedAgeReturns the value of field
     * 'attainedAge'.
     * 
     * @return the value of field 'attainedAge'.
     */
    public int getAttainedAge()
    {
        return this._attainedAge;
    } //-- int getAttainedAge() 

    /**
     * Method getDeathBenefitReturns the value of field
     * 'deathBenefit'.
     * 
     * @return the value of field 'deathBenefit'.
     */
    public java.math.BigDecimal getDeathBenefit()
    {
        return this._deathBenefit;
    } //-- java.math.BigDecimal getDeathBenefit() 

    /**
     * Method getDurationReturns the value of field 'duration'.
     * 
     * @return the value of field 'duration'.
     */
    public int getDuration()
    {
        return this._duration;
    } //-- int getDuration() 

    /**
     * Method getGuarDecreaseTermDBReturns the value of field
     * 'guarDecreaseTermDB'.
     * 
     * @return the value of field 'guarDecreaseTermDB'.
     */
    public java.math.BigDecimal getGuarDecreaseTermDB()
    {
        return this._guarDecreaseTermDB;
    } //-- java.math.BigDecimal getGuarDecreaseTermDB() 

    /**
     * Method getGuarLTCPremiumReturns the value of field
     * 'guarLTCPremium'.
     * 
     * @return the value of field 'guarLTCPremium'.
     */
    public java.math.BigDecimal getGuarLTCPremium()
    {
        return this._guarLTCPremium;
    } //-- java.math.BigDecimal getGuarLTCPremium() 

    /**
     * Method getGuarModalPremiumReturns the value of field
     * 'guarModalPremium'.
     * 
     * @return the value of field 'guarModalPremium'.
     */
    public java.math.BigDecimal getGuarModalPremium()
    {
        return this._guarModalPremium;
    } //-- java.math.BigDecimal getGuarModalPremium() 

    /**
     * Method getGuarNetPayReturns the value of field 'guarNetPay'.
     * 
     * @return the value of field 'guarNetPay'.
     */
    public java.math.BigDecimal getGuarNetPay()
    {
        return this._guarNetPay;
    } //-- java.math.BigDecimal getGuarNetPay() 

    /**
     * Method getGuarNetPremiumCostIndex10Returns the value of
     * field 'guarNetPremiumCostIndex10'.
     * 
     * @return the value of field 'guarNetPremiumCostIndex10'.
     */
    public java.math.BigDecimal getGuarNetPremiumCostIndex10()
    {
        return this._guarNetPremiumCostIndex10;
    } //-- java.math.BigDecimal getGuarNetPremiumCostIndex10() 

    /**
     * Method getGuarNetPremiumCostIndex20Returns the value of
     * field 'guarNetPremiumCostIndex20'.
     * 
     * @return the value of field 'guarNetPremiumCostIndex20'.
     */
    public java.math.BigDecimal getGuarNetPremiumCostIndex20()
    {
        return this._guarNetPremiumCostIndex20;
    } //-- java.math.BigDecimal getGuarNetPremiumCostIndex20() 

    /**
     * Method getGuarPaidUpTermReturns the value of field
     * 'guarPaidUpTerm'.
     * 
     * @return the value of field 'guarPaidUpTerm'.
     */
    public java.math.BigDecimal getGuarPaidUpTerm()
    {
        return this._guarPaidUpTerm;
    } //-- java.math.BigDecimal getGuarPaidUpTerm() 

    /**
     * Method getGuarSurrenderReturns the value of field
     * 'guarSurrender'.
     * 
     * @return the value of field 'guarSurrender'.
     */
    public java.math.BigDecimal getGuarSurrender()
    {
        return this._guarSurrender;
    } //-- java.math.BigDecimal getGuarSurrender() 

    /**
     * Method getGuarSurrenderCostIndex10Returns the value of field
     * 'guarSurrenderCostIndex10'.
     * 
     * @return the value of field 'guarSurrenderCostIndex10'.
     */
    public java.math.BigDecimal getGuarSurrenderCostIndex10()
    {
        return this._guarSurrenderCostIndex10;
    } //-- java.math.BigDecimal getGuarSurrenderCostIndex10() 

    /**
     * Method getGuarSurrenderCostIndex20Returns the value of field
     * 'guarSurrenderCostIndex20'.
     * 
     * @return the value of field 'guarSurrenderCostIndex20'.
     */
    public java.math.BigDecimal getGuarSurrenderCostIndex20()
    {
        return this._guarSurrenderCostIndex20;
    } //-- java.math.BigDecimal getGuarSurrenderCostIndex20() 

    /**
     * Method getGuaranteedEOBPremiumReturns the value of field
     * 'guaranteedEOBPremium'.
     * 
     * @return the value of field 'guaranteedEOBPremium'.
     */
    public java.math.BigDecimal getGuaranteedEOBPremium()
    {
        return this._guaranteedEOBPremium;
    } //-- java.math.BigDecimal getGuaranteedEOBPremium() 

    /**
     * Method getMidPointPUTReturns the value of field
     * 'midPointPUT'.
     * 
     * @return the value of field 'midPointPUT'.
     */
    public java.math.BigDecimal getMidPointPUT()
    {
        return this._midPointPUT;
    } //-- java.math.BigDecimal getMidPointPUT() 

    /**
     * Method getMidPointPremTermReturns the value of field
     * 'midPointPremTerm'.
     * 
     * @return the value of field 'midPointPremTerm'.
     */
    public java.math.BigDecimal getMidPointPremTerm()
    {
        return this._midPointPremTerm;
    } //-- java.math.BigDecimal getMidPointPremTerm() 

    /**
     * Method getNetPremiumCostIndex10Returns the value of field
     * 'netPremiumCostIndex10'.
     * 
     * @return the value of field 'netPremiumCostIndex10'.
     */
    public java.math.BigDecimal getNetPremiumCostIndex10()
    {
        return this._netPremiumCostIndex10;
    } //-- java.math.BigDecimal getNetPremiumCostIndex10() 

    /**
     * Method getNetPremiumCostIndex20Returns the value of field
     * 'netPremiumCostIndex20'.
     * 
     * @return the value of field 'netPremiumCostIndex20'.
     */
    public java.math.BigDecimal getNetPremiumCostIndex20()
    {
        return this._netPremiumCostIndex20;
    } //-- java.math.BigDecimal getNetPremiumCostIndex20() 

    /**
     * Method getNonGuarDeathBenefitReturns the value of field
     * 'nonGuarDeathBenefit'.
     * 
     * @return the value of field 'nonGuarDeathBenefit'.
     */
    public java.math.BigDecimal getNonGuarDeathBenefit()
    {
        return this._nonGuarDeathBenefit;
    } //-- java.math.BigDecimal getNonGuarDeathBenefit() 

    /**
     * Method getNonGuarNetPayReturns the value of field
     * 'nonGuarNetPay'.
     * 
     * @return the value of field 'nonGuarNetPay'.
     */
    public java.math.BigDecimal getNonGuarNetPay()
    {
        return this._nonGuarNetPay;
    } //-- java.math.BigDecimal getNonGuarNetPay() 

    /**
     * Method getNonGuarPaidUpTermReturns the value of field
     * 'nonGuarPaidUpTerm'.
     * 
     * @return the value of field 'nonGuarPaidUpTerm'.
     */
    public java.math.BigDecimal getNonGuarPaidUpTerm()
    {
        return this._nonGuarPaidUpTerm;
    } //-- java.math.BigDecimal getNonGuarPaidUpTerm() 

    /**
     * Method getNonGuarSurrenderReturns the value of field
     * 'nonGuarSurrender'.
     * 
     * @return the value of field 'nonGuarSurrender'.
     */
    public java.math.BigDecimal getNonGuarSurrender()
    {
        return this._nonGuarSurrender;
    } //-- java.math.BigDecimal getNonGuarSurrender() 

    /**
     * Method getOneYearTermReturns the value of field
     * 'oneYearTerm'.
     * 
     * @return the value of field 'oneYearTerm'.
     */
    public java.math.BigDecimal getOneYearTerm()
    {
        return this._oneYearTerm;
    } //-- java.math.BigDecimal getOneYearTerm() 

    /**
     * Method getPaidUpAgeReturns the value of field 'paidUpAge'.
     * 
     * @return the value of field 'paidUpAge'.
     */
    public int getPaidUpAge()
    {
        return this._paidUpAge;
    } //-- int getPaidUpAge() 

    /**
     * Method getPaidUpTermDateReturns the value of field
     * 'paidUpTermDate'.
     * 
     * @return the value of field 'paidUpTermDate'.
     */
    public java.lang.String getPaidUpTermDate()
    {
        return this._paidUpTermDate;
    } //-- java.lang.String getPaidUpTermDate() 

    /**
     * Method getPaidUpTermVestedReturns the value of field
     * 'paidUpTermVested'.
     * 
     * @return the value of field 'paidUpTermVested'.
     */
    public java.math.BigDecimal getPaidUpTermVested()
    {
        return this._paidUpTermVested;
    } //-- java.math.BigDecimal getPaidUpTermVested() 

    /**
     * Method getPremiumLoadReturns the value of field
     * 'premiumLoad'.
     * 
     * @return the value of field 'premiumLoad'.
     */
    public java.math.BigDecimal getPremiumLoad()
    {
        return this._premiumLoad;
    } //-- java.math.BigDecimal getPremiumLoad() 

    /**
     * Method getProjectionsPKReturns the value of field
     * 'projectionsPK'.
     * 
     * @return the value of field 'projectionsPK'.
     */
    public long getProjectionsPK()
    {
        return this._projectionsPK;
    } //-- long getProjectionsPK() 

    /**
     * Method getRPUReturns the value of field 'RPU'.
     * 
     * @return the value of field 'RPU'.
     */
    public java.math.BigDecimal getRPU()
    {
        return this._RPU;
    } //-- java.math.BigDecimal getRPU() 

    /**
     * Method getSurrenderCostIndex10Returns the value of field
     * 'surrenderCostIndex10'.
     * 
     * @return the value of field 'surrenderCostIndex10'.
     */
    public java.math.BigDecimal getSurrenderCostIndex10()
    {
        return this._surrenderCostIndex10;
    } //-- java.math.BigDecimal getSurrenderCostIndex10() 

    /**
     * Method getSurrenderCostIndex20Returns the value of field
     * 'surrenderCostIndex20'.
     * 
     * @return the value of field 'surrenderCostIndex20'.
     */
    public java.math.BigDecimal getSurrenderCostIndex20()
    {
        return this._surrenderCostIndex20;
    } //-- java.math.BigDecimal getSurrenderCostIndex20() 

    /**
     * Method getTypeReturns the value of field 'type'.
     * 
     * @return the value of field 'type'.
     */
    public java.lang.String getType()
    {
        return this._type;
    } //-- java.lang.String getType() 

    /**
     * Method getVestingPeriodReturns the value of field
     * 'vestingPeriod'.
     * 
     * @return the value of field 'vestingPeriod'.
     */
    public int getVestingPeriod()
    {
        return this._vestingPeriod;
    } //-- int getVestingPeriod() 

    /**
     * Method hasAge
     */
    public boolean hasAge()
    {
        return this._has_age;
    } //-- boolean hasAge() 

    /**
     * Method hasAttainedAge
     */
    public boolean hasAttainedAge()
    {
        return this._has_attainedAge;
    } //-- boolean hasAttainedAge() 

    /**
     * Method hasDuration
     */
    public boolean hasDuration()
    {
        return this._has_duration;
    } //-- boolean hasDuration() 

    /**
     * Method hasPaidUpAge
     */
    public boolean hasPaidUpAge()
    {
        return this._has_paidUpAge;
    } //-- boolean hasPaidUpAge() 

    /**
     * Method hasProjectionsPK
     */
    public boolean hasProjectionsPK()
    {
        return this._has_projectionsPK;
    } //-- boolean hasProjectionsPK() 

    /**
     * Method hasVestingPeriod
     */
    public boolean hasVestingPeriod()
    {
        return this._has_vestingPeriod;
    } //-- boolean hasVestingPeriod() 

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
     * Method setAgeSets the value of field 'age'.
     * 
     * @param age the value of field 'age'.
     */
    public void setAge(int age)
    {
        this._age = age;
        
        super.setVoChanged(true);
        this._has_age = true;
    } //-- void setAge(int) 

    /**
     * Method setAmountSets the value of field 'amount'.
     * 
     * @param amount the value of field 'amount'.
     */
    public void setAmount(java.math.BigDecimal amount)
    {
        this._amount = amount;
        
        super.setVoChanged(true);
    } //-- void setAmount(java.math.BigDecimal) 

    /**
     * Method setAnniversaryDateSets the value of field
     * 'anniversaryDate'.
     * 
     * @param anniversaryDate the value of field 'anniversaryDate'.
     */
    public void setAnniversaryDate(java.lang.String anniversaryDate)
    {
        this._anniversaryDate = anniversaryDate;
        
        super.setVoChanged(true);
    } //-- void setAnniversaryDate(java.lang.String) 

    /**
     * Method setAttainedAgeSets the value of field 'attainedAge'.
     * 
     * @param attainedAge the value of field 'attainedAge'.
     */
    public void setAttainedAge(int attainedAge)
    {
        this._attainedAge = attainedAge;
        
        super.setVoChanged(true);
        this._has_attainedAge = true;
    } //-- void setAttainedAge(int) 

    /**
     * Method setDeathBenefitSets the value of field
     * 'deathBenefit'.
     * 
     * @param deathBenefit the value of field 'deathBenefit'.
     */
    public void setDeathBenefit(java.math.BigDecimal deathBenefit)
    {
        this._deathBenefit = deathBenefit;
        
        super.setVoChanged(true);
    } //-- void setDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setDurationSets the value of field 'duration'.
     * 
     * @param duration the value of field 'duration'.
     */
    public void setDuration(int duration)
    {
        this._duration = duration;
        
        super.setVoChanged(true);
        this._has_duration = true;
    } //-- void setDuration(int) 

    /**
     * Method setGuarDecreaseTermDBSets the value of field
     * 'guarDecreaseTermDB'.
     * 
     * @param guarDecreaseTermDB the value of field
     * 'guarDecreaseTermDB'.
     */
    public void setGuarDecreaseTermDB(java.math.BigDecimal guarDecreaseTermDB)
    {
        this._guarDecreaseTermDB = guarDecreaseTermDB;
        
        super.setVoChanged(true);
    } //-- void setGuarDecreaseTermDB(java.math.BigDecimal) 

    /**
     * Method setGuarLTCPremiumSets the value of field
     * 'guarLTCPremium'.
     * 
     * @param guarLTCPremium the value of field 'guarLTCPremium'.
     */
    public void setGuarLTCPremium(java.math.BigDecimal guarLTCPremium)
    {
        this._guarLTCPremium = guarLTCPremium;
        
        super.setVoChanged(true);
    } //-- void setGuarLTCPremium(java.math.BigDecimal) 

    /**
     * Method setGuarModalPremiumSets the value of field
     * 'guarModalPremium'.
     * 
     * @param guarModalPremium the value of field 'guarModalPremium'
     */
    public void setGuarModalPremium(java.math.BigDecimal guarModalPremium)
    {
        this._guarModalPremium = guarModalPremium;
        
        super.setVoChanged(true);
    } //-- void setGuarModalPremium(java.math.BigDecimal) 

    /**
     * Method setGuarNetPaySets the value of field 'guarNetPay'.
     * 
     * @param guarNetPay the value of field 'guarNetPay'.
     */
    public void setGuarNetPay(java.math.BigDecimal guarNetPay)
    {
        this._guarNetPay = guarNetPay;
        
        super.setVoChanged(true);
    } //-- void setGuarNetPay(java.math.BigDecimal) 

    /**
     * Method setGuarNetPremiumCostIndex10Sets the value of field
     * 'guarNetPremiumCostIndex10'.
     * 
     * @param guarNetPremiumCostIndex10 the value of field
     * 'guarNetPremiumCostIndex10'.
     */
    public void setGuarNetPremiumCostIndex10(java.math.BigDecimal guarNetPremiumCostIndex10)
    {
        this._guarNetPremiumCostIndex10 = guarNetPremiumCostIndex10;
        
        super.setVoChanged(true);
    } //-- void setGuarNetPremiumCostIndex10(java.math.BigDecimal) 

    /**
     * Method setGuarNetPremiumCostIndex20Sets the value of field
     * 'guarNetPremiumCostIndex20'.
     * 
     * @param guarNetPremiumCostIndex20 the value of field
     * 'guarNetPremiumCostIndex20'.
     */
    public void setGuarNetPremiumCostIndex20(java.math.BigDecimal guarNetPremiumCostIndex20)
    {
        this._guarNetPremiumCostIndex20 = guarNetPremiumCostIndex20;
        
        super.setVoChanged(true);
    } //-- void setGuarNetPremiumCostIndex20(java.math.BigDecimal) 

    /**
     * Method setGuarPaidUpTermSets the value of field
     * 'guarPaidUpTerm'.
     * 
     * @param guarPaidUpTerm the value of field 'guarPaidUpTerm'.
     */
    public void setGuarPaidUpTerm(java.math.BigDecimal guarPaidUpTerm)
    {
        this._guarPaidUpTerm = guarPaidUpTerm;
        
        super.setVoChanged(true);
    } //-- void setGuarPaidUpTerm(java.math.BigDecimal) 

    /**
     * Method setGuarSurrenderSets the value of field
     * 'guarSurrender'.
     * 
     * @param guarSurrender the value of field 'guarSurrender'.
     */
    public void setGuarSurrender(java.math.BigDecimal guarSurrender)
    {
        this._guarSurrender = guarSurrender;
        
        super.setVoChanged(true);
    } //-- void setGuarSurrender(java.math.BigDecimal) 

    /**
     * Method setGuarSurrenderCostIndex10Sets the value of field
     * 'guarSurrenderCostIndex10'.
     * 
     * @param guarSurrenderCostIndex10 the value of field
     * 'guarSurrenderCostIndex10'.
     */
    public void setGuarSurrenderCostIndex10(java.math.BigDecimal guarSurrenderCostIndex10)
    {
        this._guarSurrenderCostIndex10 = guarSurrenderCostIndex10;
        
        super.setVoChanged(true);
    } //-- void setGuarSurrenderCostIndex10(java.math.BigDecimal) 

    /**
     * Method setGuarSurrenderCostIndex20Sets the value of field
     * 'guarSurrenderCostIndex20'.
     * 
     * @param guarSurrenderCostIndex20 the value of field
     * 'guarSurrenderCostIndex20'.
     */
    public void setGuarSurrenderCostIndex20(java.math.BigDecimal guarSurrenderCostIndex20)
    {
        this._guarSurrenderCostIndex20 = guarSurrenderCostIndex20;
        
        super.setVoChanged(true);
    } //-- void setGuarSurrenderCostIndex20(java.math.BigDecimal) 

    /**
     * Method setGuaranteedEOBPremiumSets the value of field
     * 'guaranteedEOBPremium'.
     * 
     * @param guaranteedEOBPremium the value of field
     * 'guaranteedEOBPremium'.
     */
    public void setGuaranteedEOBPremium(java.math.BigDecimal guaranteedEOBPremium)
    {
        this._guaranteedEOBPremium = guaranteedEOBPremium;
        
        super.setVoChanged(true);
    } //-- void setGuaranteedEOBPremium(java.math.BigDecimal) 

    /**
     * Method setMidPointPUTSets the value of field 'midPointPUT'.
     * 
     * @param midPointPUT the value of field 'midPointPUT'.
     */
    public void setMidPointPUT(java.math.BigDecimal midPointPUT)
    {
        this._midPointPUT = midPointPUT;
        
        super.setVoChanged(true);
    } //-- void setMidPointPUT(java.math.BigDecimal) 

    /**
     * Method setMidPointPremTermSets the value of field
     * 'midPointPremTerm'.
     * 
     * @param midPointPremTerm the value of field 'midPointPremTerm'
     */
    public void setMidPointPremTerm(java.math.BigDecimal midPointPremTerm)
    {
        this._midPointPremTerm = midPointPremTerm;
        
        super.setVoChanged(true);
    } //-- void setMidPointPremTerm(java.math.BigDecimal) 

    /**
     * Method setNetPremiumCostIndex10Sets the value of field
     * 'netPremiumCostIndex10'.
     * 
     * @param netPremiumCostIndex10 the value of field
     * 'netPremiumCostIndex10'.
     */
    public void setNetPremiumCostIndex10(java.math.BigDecimal netPremiumCostIndex10)
    {
        this._netPremiumCostIndex10 = netPremiumCostIndex10;
        
        super.setVoChanged(true);
    } //-- void setNetPremiumCostIndex10(java.math.BigDecimal) 

    /**
     * Method setNetPremiumCostIndex20Sets the value of field
     * 'netPremiumCostIndex20'.
     * 
     * @param netPremiumCostIndex20 the value of field
     * 'netPremiumCostIndex20'.
     */
    public void setNetPremiumCostIndex20(java.math.BigDecimal netPremiumCostIndex20)
    {
        this._netPremiumCostIndex20 = netPremiumCostIndex20;
        
        super.setVoChanged(true);
    } //-- void setNetPremiumCostIndex20(java.math.BigDecimal) 

    /**
     * Method setNonGuarDeathBenefitSets the value of field
     * 'nonGuarDeathBenefit'.
     * 
     * @param nonGuarDeathBenefit the value of field
     * 'nonGuarDeathBenefit'.
     */
    public void setNonGuarDeathBenefit(java.math.BigDecimal nonGuarDeathBenefit)
    {
        this._nonGuarDeathBenefit = nonGuarDeathBenefit;
        
        super.setVoChanged(true);
    } //-- void setNonGuarDeathBenefit(java.math.BigDecimal) 

    /**
     * Method setNonGuarNetPaySets the value of field
     * 'nonGuarNetPay'.
     * 
     * @param nonGuarNetPay the value of field 'nonGuarNetPay'.
     */
    public void setNonGuarNetPay(java.math.BigDecimal nonGuarNetPay)
    {
        this._nonGuarNetPay = nonGuarNetPay;
        
        super.setVoChanged(true);
    } //-- void setNonGuarNetPay(java.math.BigDecimal) 

    /**
     * Method setNonGuarPaidUpTermSets the value of field
     * 'nonGuarPaidUpTerm'.
     * 
     * @param nonGuarPaidUpTerm the value of field
     * 'nonGuarPaidUpTerm'.
     */
    public void setNonGuarPaidUpTerm(java.math.BigDecimal nonGuarPaidUpTerm)
    {
        this._nonGuarPaidUpTerm = nonGuarPaidUpTerm;
        
        super.setVoChanged(true);
    } //-- void setNonGuarPaidUpTerm(java.math.BigDecimal) 

    /**
     * Method setNonGuarSurrenderSets the value of field
     * 'nonGuarSurrender'.
     * 
     * @param nonGuarSurrender the value of field 'nonGuarSurrender'
     */
    public void setNonGuarSurrender(java.math.BigDecimal nonGuarSurrender)
    {
        this._nonGuarSurrender = nonGuarSurrender;
        
        super.setVoChanged(true);
    } //-- void setNonGuarSurrender(java.math.BigDecimal) 

    /**
     * Method setOneYearTermSets the value of field 'oneYearTerm'.
     * 
     * @param oneYearTerm the value of field 'oneYearTerm'.
     */
    public void setOneYearTerm(java.math.BigDecimal oneYearTerm)
    {
        this._oneYearTerm = oneYearTerm;
        
        super.setVoChanged(true);
    } //-- void setOneYearTerm(java.math.BigDecimal) 

    /**
     * Method setPaidUpAgeSets the value of field 'paidUpAge'.
     * 
     * @param paidUpAge the value of field 'paidUpAge'.
     */
    public void setPaidUpAge(int paidUpAge)
    {
        this._paidUpAge = paidUpAge;
        
        super.setVoChanged(true);
        this._has_paidUpAge = true;
    } //-- void setPaidUpAge(int) 

    /**
     * Method setPaidUpTermDateSets the value of field
     * 'paidUpTermDate'.
     * 
     * @param paidUpTermDate the value of field 'paidUpTermDate'.
     */
    public void setPaidUpTermDate(java.lang.String paidUpTermDate)
    {
        this._paidUpTermDate = paidUpTermDate;
        
        super.setVoChanged(true);
    } //-- void setPaidUpTermDate(java.lang.String) 

    /**
     * Method setPaidUpTermVestedSets the value of field
     * 'paidUpTermVested'.
     * 
     * @param paidUpTermVested the value of field 'paidUpTermVested'
     */
    public void setPaidUpTermVested(java.math.BigDecimal paidUpTermVested)
    {
        this._paidUpTermVested = paidUpTermVested;
        
        super.setVoChanged(true);
    } //-- void setPaidUpTermVested(java.math.BigDecimal) 

    /**
     * Method setPremiumLoadSets the value of field 'premiumLoad'.
     * 
     * @param premiumLoad the value of field 'premiumLoad'.
     */
    public void setPremiumLoad(java.math.BigDecimal premiumLoad)
    {
        this._premiumLoad = premiumLoad;
        
        super.setVoChanged(true);
    } //-- void setPremiumLoad(java.math.BigDecimal) 

    /**
     * Method setProjectionsPKSets the value of field
     * 'projectionsPK'.
     * 
     * @param projectionsPK the value of field 'projectionsPK'.
     */
    public void setProjectionsPK(long projectionsPK)
    {
        this._projectionsPK = projectionsPK;
        
        super.setVoChanged(true);
        this._has_projectionsPK = true;
    } //-- void setProjectionsPK(long) 

    /**
     * Method setRPUSets the value of field 'RPU'.
     * 
     * @param RPU the value of field 'RPU'.
     */
    public void setRPU(java.math.BigDecimal RPU)
    {
        this._RPU = RPU;
        
        super.setVoChanged(true);
    } //-- void setRPU(java.math.BigDecimal) 

    /**
     * Method setSurrenderCostIndex10Sets the value of field
     * 'surrenderCostIndex10'.
     * 
     * @param surrenderCostIndex10 the value of field
     * 'surrenderCostIndex10'.
     */
    public void setSurrenderCostIndex10(java.math.BigDecimal surrenderCostIndex10)
    {
        this._surrenderCostIndex10 = surrenderCostIndex10;
        
        super.setVoChanged(true);
    } //-- void setSurrenderCostIndex10(java.math.BigDecimal) 

    /**
     * Method setSurrenderCostIndex20Sets the value of field
     * 'surrenderCostIndex20'.
     * 
     * @param surrenderCostIndex20 the value of field
     * 'surrenderCostIndex20'.
     */
    public void setSurrenderCostIndex20(java.math.BigDecimal surrenderCostIndex20)
    {
        this._surrenderCostIndex20 = surrenderCostIndex20;
        
        super.setVoChanged(true);
    } //-- void setSurrenderCostIndex20(java.math.BigDecimal) 

    /**
     * Method setTypeSets the value of field 'type'.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(java.lang.String type)
    {
        this._type = type;
        
        super.setVoChanged(true);
    } //-- void setType(java.lang.String) 

    /**
     * Method setVestingPeriodSets the value of field
     * 'vestingPeriod'.
     * 
     * @param vestingPeriod the value of field 'vestingPeriod'.
     */
    public void setVestingPeriod(int vestingPeriod)
    {
        this._vestingPeriod = vestingPeriod;
        
        super.setVoChanged(true);
        this._has_vestingPeriod = true;
    } //-- void setVestingPeriod(int) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ProjectionsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProjectionsVO) Unmarshaller.unmarshal(edit.common.vo.ProjectionsVO.class, reader);
    } //-- edit.common.vo.ProjectionsVO unmarshal(java.io.Reader) 

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
