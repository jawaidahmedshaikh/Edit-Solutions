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
 * Class UnitValuesVO.
 * 
 * @version $Revision$ $Date$
 */
public class UnitValuesVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _unitValuesPK
     */
    private long _unitValuesPK;

    /**
     * keeps track of state for field: _unitValuesPK
     */
    private boolean _has_unitValuesPK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _unitValue
     */
    private java.math.BigDecimal _unitValue;

    /**
     * Field _annuityUnitValue
     */
    private java.math.BigDecimal _annuityUnitValue;

    /**
     * Field _updateStatus
     */
    private java.lang.String _updateStatus;

    /**
     * Field _netAssetValue1
     */
    private java.math.BigDecimal _netAssetValue1;

    /**
     * Field _netAssetValue2
     */
    private java.math.BigDecimal _netAssetValue2;

    /**
     * Field _mutualFundShares
     */
    private java.math.BigDecimal _mutualFundShares;

    /**
     * Field _participantUnits
     */
    private java.math.BigDecimal _participantUnits;

    /**
     * Field _mutualFundAssets
     */
    private java.math.BigDecimal _mutualFundAssets;

    /**
     * Field _dividendRate
     */
    private java.math.BigDecimal _dividendRate;

    /**
     * Field _chargeCodeFK
     */
    private long _chargeCodeFK;

    /**
     * keeps track of state for field: _chargeCodeFK
     */
    private boolean _has_chargeCodeFK;

    /**
     * Field _UVALAssets
     */
    private java.math.BigDecimal _UVALAssets;

    /**
     * Field _NAV1Assets
     */
    private java.math.BigDecimal _NAV1Assets;

    /**
     * Field _NAV2Assets
     */
    private java.math.BigDecimal _NAV2Assets;


      //----------------/
     //- Constructors -/
    //----------------/

    public UnitValuesVO() {
        super();
    } //-- edit.common.vo.UnitValuesVO()


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
        
        if (obj instanceof UnitValuesVO) {
        
            UnitValuesVO temp = (UnitValuesVO)obj;
            if (this._unitValuesPK != temp._unitValuesPK)
                return false;
            if (this._has_unitValuesPK != temp._has_unitValuesPK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._unitValue != null) {
                if (temp._unitValue == null) return false;
                else if (!(this._unitValue.equals(temp._unitValue))) 
                    return false;
            }
            else if (temp._unitValue != null)
                return false;
            if (this._annuityUnitValue != null) {
                if (temp._annuityUnitValue == null) return false;
                else if (!(this._annuityUnitValue.equals(temp._annuityUnitValue))) 
                    return false;
            }
            else if (temp._annuityUnitValue != null)
                return false;
            if (this._updateStatus != null) {
                if (temp._updateStatus == null) return false;
                else if (!(this._updateStatus.equals(temp._updateStatus))) 
                    return false;
            }
            else if (temp._updateStatus != null)
                return false;
            if (this._netAssetValue1 != null) {
                if (temp._netAssetValue1 == null) return false;
                else if (!(this._netAssetValue1.equals(temp._netAssetValue1))) 
                    return false;
            }
            else if (temp._netAssetValue1 != null)
                return false;
            if (this._netAssetValue2 != null) {
                if (temp._netAssetValue2 == null) return false;
                else if (!(this._netAssetValue2.equals(temp._netAssetValue2))) 
                    return false;
            }
            else if (temp._netAssetValue2 != null)
                return false;
            if (this._mutualFundShares != null) {
                if (temp._mutualFundShares == null) return false;
                else if (!(this._mutualFundShares.equals(temp._mutualFundShares))) 
                    return false;
            }
            else if (temp._mutualFundShares != null)
                return false;
            if (this._participantUnits != null) {
                if (temp._participantUnits == null) return false;
                else if (!(this._participantUnits.equals(temp._participantUnits))) 
                    return false;
            }
            else if (temp._participantUnits != null)
                return false;
            if (this._mutualFundAssets != null) {
                if (temp._mutualFundAssets == null) return false;
                else if (!(this._mutualFundAssets.equals(temp._mutualFundAssets))) 
                    return false;
            }
            else if (temp._mutualFundAssets != null)
                return false;
            if (this._dividendRate != null) {
                if (temp._dividendRate == null) return false;
                else if (!(this._dividendRate.equals(temp._dividendRate))) 
                    return false;
            }
            else if (temp._dividendRate != null)
                return false;
            if (this._chargeCodeFK != temp._chargeCodeFK)
                return false;
            if (this._has_chargeCodeFK != temp._has_chargeCodeFK)
                return false;
            if (this._UVALAssets != null) {
                if (temp._UVALAssets == null) return false;
                else if (!(this._UVALAssets.equals(temp._UVALAssets))) 
                    return false;
            }
            else if (temp._UVALAssets != null)
                return false;
            if (this._NAV1Assets != null) {
                if (temp._NAV1Assets == null) return false;
                else if (!(this._NAV1Assets.equals(temp._NAV1Assets))) 
                    return false;
            }
            else if (temp._NAV1Assets != null)
                return false;
            if (this._NAV2Assets != null) {
                if (temp._NAV2Assets == null) return false;
                else if (!(this._NAV2Assets.equals(temp._NAV2Assets))) 
                    return false;
            }
            else if (temp._NAV2Assets != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAnnuityUnitValueReturns the value of field
     * 'annuityUnitValue'.
     * 
     * @return the value of field 'annuityUnitValue'.
     */
    public java.math.BigDecimal getAnnuityUnitValue()
    {
        return this._annuityUnitValue;
    } //-- java.math.BigDecimal getAnnuityUnitValue() 

    /**
     * Method getChargeCodeFKReturns the value of field
     * 'chargeCodeFK'.
     * 
     * @return the value of field 'chargeCodeFK'.
     */
    public long getChargeCodeFK()
    {
        return this._chargeCodeFK;
    } //-- long getChargeCodeFK() 

    /**
     * Method getDividendRateReturns the value of field
     * 'dividendRate'.
     * 
     * @return the value of field 'dividendRate'.
     */
    public java.math.BigDecimal getDividendRate()
    {
        return this._dividendRate;
    } //-- java.math.BigDecimal getDividendRate() 

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
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getMutualFundAssetsReturns the value of field
     * 'mutualFundAssets'.
     * 
     * @return the value of field 'mutualFundAssets'.
     */
    public java.math.BigDecimal getMutualFundAssets()
    {
        return this._mutualFundAssets;
    } //-- java.math.BigDecimal getMutualFundAssets() 

    /**
     * Method getMutualFundSharesReturns the value of field
     * 'mutualFundShares'.
     * 
     * @return the value of field 'mutualFundShares'.
     */
    public java.math.BigDecimal getMutualFundShares()
    {
        return this._mutualFundShares;
    } //-- java.math.BigDecimal getMutualFundShares() 

    /**
     * Method getNAV1AssetsReturns the value of field 'NAV1Assets'.
     * 
     * @return the value of field 'NAV1Assets'.
     */
    public java.math.BigDecimal getNAV1Assets()
    {
        return this._NAV1Assets;
    } //-- java.math.BigDecimal getNAV1Assets() 

    /**
     * Method getNAV2AssetsReturns the value of field 'NAV2Assets'.
     * 
     * @return the value of field 'NAV2Assets'.
     */
    public java.math.BigDecimal getNAV2Assets()
    {
        return this._NAV2Assets;
    } //-- java.math.BigDecimal getNAV2Assets() 

    /**
     * Method getNetAssetValue1Returns the value of field
     * 'netAssetValue1'.
     * 
     * @return the value of field 'netAssetValue1'.
     */
    public java.math.BigDecimal getNetAssetValue1()
    {
        return this._netAssetValue1;
    } //-- java.math.BigDecimal getNetAssetValue1() 

    /**
     * Method getNetAssetValue2Returns the value of field
     * 'netAssetValue2'.
     * 
     * @return the value of field 'netAssetValue2'.
     */
    public java.math.BigDecimal getNetAssetValue2()
    {
        return this._netAssetValue2;
    } //-- java.math.BigDecimal getNetAssetValue2() 

    /**
     * Method getParticipantUnitsReturns the value of field
     * 'participantUnits'.
     * 
     * @return the value of field 'participantUnits'.
     */
    public java.math.BigDecimal getParticipantUnits()
    {
        return this._participantUnits;
    } //-- java.math.BigDecimal getParticipantUnits() 

    /**
     * Method getUVALAssetsReturns the value of field 'UVALAssets'.
     * 
     * @return the value of field 'UVALAssets'.
     */
    public java.math.BigDecimal getUVALAssets()
    {
        return this._UVALAssets;
    } //-- java.math.BigDecimal getUVALAssets() 

    /**
     * Method getUnitValueReturns the value of field 'unitValue'.
     * 
     * @return the value of field 'unitValue'.
     */
    public java.math.BigDecimal getUnitValue()
    {
        return this._unitValue;
    } //-- java.math.BigDecimal getUnitValue() 

    /**
     * Method getUnitValuesPKReturns the value of field
     * 'unitValuesPK'.
     * 
     * @return the value of field 'unitValuesPK'.
     */
    public long getUnitValuesPK()
    {
        return this._unitValuesPK;
    } //-- long getUnitValuesPK() 

    /**
     * Method getUpdateStatusReturns the value of field
     * 'updateStatus'.
     * 
     * @return the value of field 'updateStatus'.
     */
    public java.lang.String getUpdateStatus()
    {
        return this._updateStatus;
    } //-- java.lang.String getUpdateStatus() 

    /**
     * Method hasChargeCodeFK
     */
    public boolean hasChargeCodeFK()
    {
        return this._has_chargeCodeFK;
    } //-- boolean hasChargeCodeFK() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasUnitValuesPK
     */
    public boolean hasUnitValuesPK()
    {
        return this._has_unitValuesPK;
    } //-- boolean hasUnitValuesPK() 

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
     * Method setAnnuityUnitValueSets the value of field
     * 'annuityUnitValue'.
     * 
     * @param annuityUnitValue the value of field 'annuityUnitValue'
     */
    public void setAnnuityUnitValue(java.math.BigDecimal annuityUnitValue)
    {
        this._annuityUnitValue = annuityUnitValue;
        
        super.setVoChanged(true);
    } //-- void setAnnuityUnitValue(java.math.BigDecimal) 

    /**
     * Method setChargeCodeFKSets the value of field
     * 'chargeCodeFK'.
     * 
     * @param chargeCodeFK the value of field 'chargeCodeFK'.
     */
    public void setChargeCodeFK(long chargeCodeFK)
    {
        this._chargeCodeFK = chargeCodeFK;
        
        super.setVoChanged(true);
        this._has_chargeCodeFK = true;
    } //-- void setChargeCodeFK(long) 

    /**
     * Method setDividendRateSets the value of field
     * 'dividendRate'.
     * 
     * @param dividendRate the value of field 'dividendRate'.
     */
    public void setDividendRate(java.math.BigDecimal dividendRate)
    {
        this._dividendRate = dividendRate;
        
        super.setVoChanged(true);
    } //-- void setDividendRate(java.math.BigDecimal) 

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
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setMutualFundAssetsSets the value of field
     * 'mutualFundAssets'.
     * 
     * @param mutualFundAssets the value of field 'mutualFundAssets'
     */
    public void setMutualFundAssets(java.math.BigDecimal mutualFundAssets)
    {
        this._mutualFundAssets = mutualFundAssets;
        
        super.setVoChanged(true);
    } //-- void setMutualFundAssets(java.math.BigDecimal) 

    /**
     * Method setMutualFundSharesSets the value of field
     * 'mutualFundShares'.
     * 
     * @param mutualFundShares the value of field 'mutualFundShares'
     */
    public void setMutualFundShares(java.math.BigDecimal mutualFundShares)
    {
        this._mutualFundShares = mutualFundShares;
        
        super.setVoChanged(true);
    } //-- void setMutualFundShares(java.math.BigDecimal) 

    /**
     * Method setNAV1AssetsSets the value of field 'NAV1Assets'.
     * 
     * @param NAV1Assets the value of field 'NAV1Assets'.
     */
    public void setNAV1Assets(java.math.BigDecimal NAV1Assets)
    {
        this._NAV1Assets = NAV1Assets;
        
        super.setVoChanged(true);
    } //-- void setNAV1Assets(java.math.BigDecimal) 

    /**
     * Method setNAV2AssetsSets the value of field 'NAV2Assets'.
     * 
     * @param NAV2Assets the value of field 'NAV2Assets'.
     */
    public void setNAV2Assets(java.math.BigDecimal NAV2Assets)
    {
        this._NAV2Assets = NAV2Assets;
        
        super.setVoChanged(true);
    } //-- void setNAV2Assets(java.math.BigDecimal) 

    /**
     * Method setNetAssetValue1Sets the value of field
     * 'netAssetValue1'.
     * 
     * @param netAssetValue1 the value of field 'netAssetValue1'.
     */
    public void setNetAssetValue1(java.math.BigDecimal netAssetValue1)
    {
        this._netAssetValue1 = netAssetValue1;
        
        super.setVoChanged(true);
    } //-- void setNetAssetValue1(java.math.BigDecimal) 

    /**
     * Method setNetAssetValue2Sets the value of field
     * 'netAssetValue2'.
     * 
     * @param netAssetValue2 the value of field 'netAssetValue2'.
     */
    public void setNetAssetValue2(java.math.BigDecimal netAssetValue2)
    {
        this._netAssetValue2 = netAssetValue2;
        
        super.setVoChanged(true);
    } //-- void setNetAssetValue2(java.math.BigDecimal) 

    /**
     * Method setParticipantUnitsSets the value of field
     * 'participantUnits'.
     * 
     * @param participantUnits the value of field 'participantUnits'
     */
    public void setParticipantUnits(java.math.BigDecimal participantUnits)
    {
        this._participantUnits = participantUnits;
        
        super.setVoChanged(true);
    } //-- void setParticipantUnits(java.math.BigDecimal) 

    /**
     * Method setUVALAssetsSets the value of field 'UVALAssets'.
     * 
     * @param UVALAssets the value of field 'UVALAssets'.
     */
    public void setUVALAssets(java.math.BigDecimal UVALAssets)
    {
        this._UVALAssets = UVALAssets;
        
        super.setVoChanged(true);
    } //-- void setUVALAssets(java.math.BigDecimal) 

    /**
     * Method setUnitValueSets the value of field 'unitValue'.
     * 
     * @param unitValue the value of field 'unitValue'.
     */
    public void setUnitValue(java.math.BigDecimal unitValue)
    {
        this._unitValue = unitValue;
        
        super.setVoChanged(true);
    } //-- void setUnitValue(java.math.BigDecimal) 

    /**
     * Method setUnitValuesPKSets the value of field
     * 'unitValuesPK'.
     * 
     * @param unitValuesPK the value of field 'unitValuesPK'.
     */
    public void setUnitValuesPK(long unitValuesPK)
    {
        this._unitValuesPK = unitValuesPK;
        
        super.setVoChanged(true);
        this._has_unitValuesPK = true;
    } //-- void setUnitValuesPK(long) 

    /**
     * Method setUpdateStatusSets the value of field
     * 'updateStatus'.
     * 
     * @param updateStatus the value of field 'updateStatus'.
     */
    public void setUpdateStatus(java.lang.String updateStatus)
    {
        this._updateStatus = updateStatus;
        
        super.setVoChanged(true);
    } //-- void setUpdateStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UnitValuesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UnitValuesVO) Unmarshaller.unmarshal(edit.common.vo.UnitValuesVO.class, reader);
    } //-- edit.common.vo.UnitValuesVO unmarshal(java.io.Reader) 

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
