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
 * Class EventCycleConsequencesVO.
 * 
 * @version $Revision$ $Date$
 */
public class EventCycleConsequencesVO extends edit.common.vo.VOObject implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private java.lang.String _definitionOfLifeInsurance;
    private java.math.BigDecimal _commissionTarget;
    private java.math.BigDecimal _minimumPremiumTarget;
    private java.math.BigDecimal _surrenderChargeTarget;
    private java.math.BigDecimal _sevenPayPremium;
    private java.math.BigDecimal _guidelineLevelPremium;
    private java.math.BigDecimal _guidelineSinglePremium;
    private java.math.BigDecimal _statCRVM;
    private java.math.BigDecimal _fITCRVM;
    private java.math.BigDecimal _faceAmount;

    /*
	     <eventCycleConsequences>
	            <commissionTarget>395.0</commissionTarget>
	            <minimumPremiumTarget>31.6</minimumPremiumTarget>
	            <surrenderChargeTarget>14.0</surrenderChargeTarget>
	            <definitionOfLifeInsurance>GuidelinePremiumTest</definitionOfLifeInsurance>
	            <sevenPayPremium>1913.78</sevenPayPremium>
	            <guidelineLevelPremium>756.02</guidelineLevelPremium>
	            <guidelineSinglePremium>8932.75</guidelineSinglePremium>
	            <statCRVM>-27.108542124064424</statCRVM>
	            <fITCRVM>-27.108542124064424</fITCRVM>
	    </eventCycleConsequences>
     */
    
    public EventCycleConsequencesVO() {
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
        
        if (obj instanceof EventCycleConsequencesVO) {
        
            EventCycleConsequencesVO temp = (EventCycleConsequencesVO)obj;
                        
            if (this._commissionTarget != null) {
                if (temp._commissionTarget == null) return false;
                else if (!(this._commissionTarget.equals(temp._commissionTarget))) 
                    return false;
            }
            else if (temp._commissionTarget != null)
                return false;
            
            if (this._minimumPremiumTarget != null) {
                if (temp._minimumPremiumTarget == null) return false;
                else if (!(this._minimumPremiumTarget.equals(temp._minimumPremiumTarget))) 
                    return false;
            }
            else if (temp._minimumPremiumTarget != null)
                return false;
            
            if (this._surrenderChargeTarget != null) {
                if (temp._surrenderChargeTarget == null) return false;
                else if (!(this._surrenderChargeTarget.equals(temp._surrenderChargeTarget))) 
                    return false;
            }
            else if (temp._surrenderChargeTarget != null)
                return false;
            
            if (this._sevenPayPremium != null) {
                if (temp._sevenPayPremium == null) return false;
                else if (!(this._sevenPayPremium.equals(temp._sevenPayPremium))) 
                    return false;
            }
            else if (temp._sevenPayPremium != null)
                return false;
            
            if (this._guidelineLevelPremium != null) {
                if (temp._guidelineLevelPremium == null) return false;
                else if (!(this._guidelineLevelPremium.equals(temp._guidelineLevelPremium))) 
                    return false;
            }
            else if (temp._guidelineLevelPremium != null)
                return false;
            
            if (this._guidelineSinglePremium != null) {
                if (temp._guidelineSinglePremium == null) return false;
                else if (!(this._guidelineSinglePremium.equals(temp._guidelineSinglePremium))) 
                    return false;
            }
            else if (temp._guidelineSinglePremium != null)
                return false;

            if (this._statCRVM != null) {
                if (temp._statCRVM == null) return false;
                else if (!(this._statCRVM.equals(temp._statCRVM))) 
                    return false;
            }
            else if (temp._statCRVM != null)
                return false;
            
            if (this._fITCRVM != null) {
                if (temp._fITCRVM == null) return false;
                else if (!(this._fITCRVM.equals(temp._fITCRVM))) 
                    return false;
            }
            else if (temp._fITCRVM != null)
                return false;
            
            if (this._faceAmount != null) {
                if (temp._faceAmount == null) return false;
                else if (!(this._faceAmount.equals(temp._faceAmount))) 
                    return false;
            }
            else if (temp._faceAmount != null)
                return false;
            
            if (this._definitionOfLifeInsurance != null) {
                if (temp._definitionOfLifeInsurance == null) return false;
                else if (!(this._definitionOfLifeInsurance.equals(temp._definitionOfLifeInsurance))) 
                    return false;
            }
            else if (temp._definitionOfLifeInsurance != null)
                return false;
            
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 
    
    public java.math.BigDecimal getCommissionTarget()
    {
        return this._commissionTarget;
    } 
    
    public java.math.BigDecimal getMinimumPremiumTarget()
    {
        return this._minimumPremiumTarget;
    } 
    
    public java.math.BigDecimal getSurrenderChargeTarget()
    {
        return this._surrenderChargeTarget;
    } 
    
    public java.math.BigDecimal getSevenPayPremium()
    {
        return this._sevenPayPremium;
    } 
    
    public java.math.BigDecimal getGuidelineLevelPremium()
    {
        return this._guidelineLevelPremium;
    } 
    
    public java.math.BigDecimal getGuidelineSinglePremium()
    {
        return this._guidelineSinglePremium;
    } 
    
    public java.math.BigDecimal getStatCRVM()
    {
        return this._statCRVM;
    } 
    
    public java.math.BigDecimal getFitCRVM()
    {
        return this._fITCRVM;
    } 
    
    public java.math.BigDecimal getFaceAmount()
    {
        return this._faceAmount;
    } 

    /**
     * Method getDefinitionOfLifeInsurance Returns the value of field
     * 'definitionOfLifeInsurance'.
     * 
     * @return the value of field 'definitionOfLifeInsurance'.
     */
    public java.lang.String getDefinitionOfLifeInsurance()
    {
        return this._definitionOfLifeInsurance;
    } //-- java.lang.String getDefinitionOfLifeInsurance() 

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
    
    public void setMinimumPremiumTarget(java.math.BigDecimal minimumPremiumTarget)
    {
        this._minimumPremiumTarget = minimumPremiumTarget;
        
        super.setVoChanged(true);
    }
    
    public void setSurrenderChargeTarget(java.math.BigDecimal surrenderChargeTarget)
    {
        this._surrenderChargeTarget = surrenderChargeTarget;
        
        super.setVoChanged(true);
    }
    
    public void setSevenPayPremium(java.math.BigDecimal sevenPayPremium)
    {
        this._sevenPayPremium = sevenPayPremium;
        
        super.setVoChanged(true);
    }
    
    public void setGuidelineLevelPremium(java.math.BigDecimal guidelineLevelPremium)
    {
        this._guidelineLevelPremium = guidelineLevelPremium;
        
        super.setVoChanged(true);
    }
    
    public void setGuidelineSinglePremium(java.math.BigDecimal guidelineSinglePremium)
    {
        this._guidelineSinglePremium = guidelineSinglePremium;
        
        super.setVoChanged(true);
    }
    
    public void setStatCRVM(java.math.BigDecimal statCRVM)
    {
        this._statCRVM = statCRVM;
        
        super.setVoChanged(true);
    }
    
    public void setFitCRVM(java.math.BigDecimal fITCRVM)
    {
        this._fITCRVM = fITCRVM;
        
        super.setVoChanged(true);
    }
    
    public void setFaceAmount(java.math.BigDecimal faceAmount)
    {
        this._faceAmount = faceAmount;
        
        super.setVoChanged(true);
    }

    /**
     * Method setDefinitionOfLifeInsurance Sets the value of field
     * 'definitionOfLifeInsurance'.
     * 
     * @param definitionOfLifeInsurance the value of field
     * 'definitionOfLifeInsurance'.
     */
    public void setDefinitionOfLifeInsurance(java.lang.String definitionOfLifeInsurance)
    {
        this._definitionOfLifeInsurance = definitionOfLifeInsurance;
        
        super.setVoChanged(true);
    } //-- void setDefinitionOfLifeInsurance(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EventCycleConsequencesVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EventCycleConsequencesVO) Unmarshaller.unmarshal(edit.common.vo.EventCycleConsequencesVO.class, reader);
    } //-- edit.common.vo.EventCycleConsequencesVO unmarshal(java.io.Reader) 

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
