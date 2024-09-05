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
 * Class EventCycleConsequencesCoverageVO.
 * 
 * @version $Revision$ $Date$
 */
public class EventCycleConsequencesCoverageVO extends edit.common.vo.VOObject implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private java.lang.String _GUID;
    private java.lang.String _productCode;
    private java.lang.String _effectiveDate;
    private java.lang.Boolean _qualifiedBenefit;
    private java.math.BigDecimal _commissionTarget;
    private java.math.BigDecimal _minimumPremiumTarget;
    private java.lang.String _sourceCoverageID;
    private java.math.BigDecimal _faceAmount;

    /*
     	<eventCycleConsequencesCoverage>
                    <productCode>AHL_Base_HighFace</productCode>
                    <effectiveDate>2020-01-01Z</effectiveDate>
                    <qualifiedBenefit>true</qualifiedBenefit>
                    <commissionTarget>395.0</commissionTarget>
                    <minimumPremiumTarget>31.6</minimumPremiumTarget>
    	</eventCycleConsequencesCoverage> 
     */
    
    public EventCycleConsequencesCoverageVO() {
        super();
    }

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
        
        if (obj instanceof EventCycleConsequencesCoverageVO) {
        
            EventCycleConsequencesCoverageVO temp = (EventCycleConsequencesCoverageVO)obj;
            
            if (this._commissionTarget != null) {
                if (temp._commissionTarget == null) return false;
                else if (!(this._commissionTarget.equals(temp._commissionTarget))) 
                    return false;
            }
            else if (temp._commissionTarget != null)
                return false;
            
            if (this._faceAmount != null) {
                if (temp._faceAmount == null) return false;
                else if (!(this._faceAmount.equals(temp._faceAmount))) 
                    return false;
            }
            else if (temp._faceAmount != null)
                return false;
            
            if (this._minimumPremiumTarget != null) {
                if (temp._minimumPremiumTarget == null) return false;
                else if (!(this._minimumPremiumTarget.equals(temp._minimumPremiumTarget))) 
                    return false;
            }
            else if (temp._minimumPremiumTarget != null)
                return false;
            
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            
            if (this._GUID != null) {
                if (temp._GUID == null) return false;
                else if (!(this._GUID.equals(temp._GUID))) 
                    return false;
            }
            else if (temp._GUID != null)
                return false;

            if (this._sourceCoverageID != null) {
                if (temp._sourceCoverageID == null) return false;
                else if (!(this._sourceCoverageID.equals(temp._sourceCoverageID))) 
                    return false;
            }
            else if (temp._sourceCoverageID != null)
                return false;
            
            if (this._productCode != null) {
                if (temp._productCode == null) return false;
                else if (!(this._productCode.equals(temp._productCode))) 
                    return false;
            }
            else if (temp._productCode != null)
                return false;
            
            if (this._qualifiedBenefit != null) {
                if (temp._qualifiedBenefit == null) return false;
                else if (!(this._qualifiedBenefit.equals(temp._qualifiedBenefit))) 
                    return false;
            }
            else if (temp._qualifiedBenefit != null)
                return false;

            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 
    
    
    

    public java.lang.String getSourceCoverageID() {
		return _sourceCoverageID;
	}

	public void setSourceCoverageID(java.lang.String _sourceCoverageID) {
		this._sourceCoverageID = _sourceCoverageID;
	}

	public java.math.BigDecimal getCommissionTarget()
    {
        return this._commissionTarget;
    } 
	
	public java.math.BigDecimal getFaceAmount()
    {
        return this._faceAmount;
    }

    public java.math.BigDecimal getMinimumPremiumTarget()
    {
        return this._minimumPremiumTarget;
    }
    
    public java.lang.String getGUID()
    {
        return this._GUID;
    } 

    public java.lang.String getProductCode()
    {
        return this._productCode;
    } 

    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    }
    
    public java.lang.Boolean getQualifiedBenefit()
    {
        return this._qualifiedBenefit;
    }
    
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

    
    public void setCommissionTarget(java.math.BigDecimal commissionTarget)
    {
        this._commissionTarget = commissionTarget;
        
        super.setVoChanged(true);
    }
    
    public void setFaceAmount(java.math.BigDecimal faceAmount)
    {
        this._faceAmount= faceAmount;
        
        super.setVoChanged(true);
    }
    
    public void setMinimumPremiumTarget(java.math.BigDecimal minimumPremiumTarget)
    {
        this._minimumPremiumTarget = minimumPremiumTarget;
        
        super.setVoChanged(true);
    }

    public void setGUID(java.lang.String GUID)
    {
        this._GUID = GUID;
        
        super.setVoChanged(true);
    } 
    
    public void setProductCode(java.lang.String productCode)
    {
        this._productCode = productCode;
        
        super.setVoChanged(true);
    } 
    
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } 
    
    public void setQualifiedBenefit(java.lang.Boolean qualifiedBenefit)
    {
        this._qualifiedBenefit = qualifiedBenefit;
        
        super.setVoChanged(true);
    } 
   
    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EventCycleConsequencesCoverageVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EventCycleConsequencesCoverageVO) Unmarshaller.unmarshal(edit.common.vo.EventCycleConsequencesCoverageVO.class, reader);
    } //-- edit.common.vo.EventCycleConsequencesCoverageVO unmarshal(java.io.Reader) 

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
