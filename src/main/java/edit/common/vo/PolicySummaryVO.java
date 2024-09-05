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
 * Class PolicySummaryVO.
 * 
 * @version $Revision$ $Date$
 */
public class PolicySummaryVO extends edit.common.vo.VOObject implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private java.lang.String _GUID;
    private java.lang.String _policyNumber;
    private java.lang.String _effectiveDate;
    private EventCycleConsequencesVO _eventCycleConsequencesVO;
    private java.util.Vector<EventCycleConsequencesCoverageVO> _eventCycleConsequencesCoverageList;


    /*
     <policySummary>
                <effectiveDate>2020-01-01Z</effectiveDate>
                <policyNumber>06U012345</policyNumber>
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
                <eventCycleConsequencesCoverage>
                                <productCode>AHL_Base_HighFace</productCode>
                                <effectiveDate>2020-01-01Z</effectiveDate>
                                <qualifiedBenefit>true</qualifiedBenefit>
                                <commissionTarget>395.0</commissionTarget>
                                <minimumPremiumTarget>31.6</minimumPremiumTarget>
                </eventCycleConsequencesCoverage>
</policySummary>
 
     */
    
    public PolicySummaryVO() {
        super();
        _eventCycleConsequencesCoverageList = new Vector<EventCycleConsequencesCoverageVO>();
    }

    /**
     * Method addEventCycleConsequencesCoverageVO
     * 
     * @param vEventCycleConsequencesCoverageVO
     */
    public void addEventCycleConsequencesCoverageVO(edit.common.vo.EventCycleConsequencesCoverageVO vEventCycleConsequencesCoverageVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEventCycleConsequencesCoverageVO.setParentVO(this.getClass(), this);
        _eventCycleConsequencesCoverageList.addElement(vEventCycleConsequencesCoverageVO);
    } //-- void addEventCycleConsequencesCoverageVO(edit.common.vo.EventCycleConsequencesCoverageVO) 

    /**
     * Method addEventCycleConsequencesCoverageVO
     * 
     * @param index
     * @param vEventCycleConsequencesCoverageVO
     */
    public void addEventCycleConsequencesCoverageVO(int index, edit.common.vo.EventCycleConsequencesCoverageVO vEventCycleConsequencesCoverageVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEventCycleConsequencesCoverageVO.setParentVO(this.getClass(), this);
        _eventCycleConsequencesCoverageList.insertElementAt(vEventCycleConsequencesCoverageVO, index);
    } //-- void addEventCycleConsequencesCoverageVO(int, edit.common.vo.EventCycleConsequencesCoverageVO) 

    /**
     * Method enumerateEventCycleConsequencesCoverageVO
     */
    public java.util.Enumeration<EventCycleConsequencesCoverageVO> enumerateEventCycleConsequencesCoverageVO()
    {
        return _eventCycleConsequencesCoverageList.elements();
    } //-- java.util.Enumeration enumerateEventCycleConsequencesCoverageVO() 

    
    public void setEventCycleConsequencesVO(edit.common.vo.EventCycleConsequencesVO vEventCycleConsequencesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vEventCycleConsequencesVO.setParentVO(this.getClass(), this);
        this._eventCycleConsequencesVO = vEventCycleConsequencesVO;
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
        
        if (obj instanceof PolicySummaryVO) {
        
            PolicySummaryVO temp = (PolicySummaryVO) obj;

            if (this._GUID != null) {
                if (temp._GUID == null) return false;
                else if (!(this._GUID.equals(temp._GUID))) 
                    return false;
            }
            else if (temp._GUID != null)
                return false;
            
            
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            
            if (this._policyNumber != null) {
                if (temp._policyNumber == null) return false;
                else if (!(this._policyNumber.equals(temp._policyNumber))) 
                    return false;
            }
            else if (temp._policyNumber != null)
                return false;
            
            // _eventCycleConsequencesCoverageList
            if (this._eventCycleConsequencesCoverageList != null) {
                if (temp._eventCycleConsequencesCoverageList == null) return false;
                else if (!(this._eventCycleConsequencesCoverageList.equals(temp._eventCycleConsequencesCoverageList))) 
                    return false;
            }
            else if (temp._eventCycleConsequencesCoverageList != null)
                return false;
            
            // _eventCycleConsequencesList
            if (this._eventCycleConsequencesVO != null) {
                if (temp._eventCycleConsequencesVO == null) return false;
                else if (!(this._eventCycleConsequencesVO.equals(temp._eventCycleConsequencesVO))) 
                    return false;
            }
            else if (temp._eventCycleConsequencesVO != null)
                return false;
            return true;
            
        }
        return false;
    } //-- boolean equals(java.lang.Object) 
    
    /**
     * Method getEventCycleConsequencesCoverageVO
     * 
     * @param index
     */
    public edit.common.vo.EventCycleConsequencesCoverageVO getEventCycleConsequencesCoverageVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventCycleConsequencesCoverageList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.EventCycleConsequencesCoverageVO) _eventCycleConsequencesCoverageList.elementAt(index);
    } //-- edit.common.vo.EventCycleConsequencesCoverageVO getEventCycleConsequencesCoverageVO(int) 

    /**
     * Method getEventCycleConsequencesCoverageVO
     */
    public edit.common.vo.EventCycleConsequencesCoverageVO[] getEventCycleConsequencesCoverageVO()
    {
        int size = _eventCycleConsequencesCoverageList.size();
        edit.common.vo.EventCycleConsequencesCoverageVO[] mArray = new edit.common.vo.EventCycleConsequencesCoverageVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.EventCycleConsequencesCoverageVO) _eventCycleConsequencesCoverageList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.EventCycleConsequencesCoverageVO[] getEventCycleConsequencesCoverageVO() 

    /**
     * Method getEventCycleConsequencesCoverageVOCount
     */
    public int getEventCycleConsequencesCoverageVOCount()
    {
        return _eventCycleConsequencesCoverageList.size();
    } //-- int getEventCycleConsequencesCoverageVOCount() 
   
    /**
     * Method getEventCycleConsequencesVO
     */
    public edit.common.vo.EventCycleConsequencesVO getEventCycleConsequencesVO()
    {
        return this._eventCycleConsequencesVO;
    } 
    
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } 
    
    public java.lang.String getPolicyNumber()
    {
        return this._policyNumber;
    } 

    public java.lang.String getGUID()
    {
        return this._GUID;
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

    /**
     * Method removeAllEventCycleConsequencesCoverageVO
     */
    public void removeAllEventCycleConsequencesCoverageVO()
    {
        _eventCycleConsequencesCoverageList.removeAllElements();
    } //-- void removeAllEventCycleConsequencesCoverageVO() 

    /**
     * Method removeEventCycleConsequencesCoverageVO
     * 
     * @param index
     */
    public edit.common.vo.EventCycleConsequencesCoverageVO removeEventCycleConsequencesCoverageVO(int index)
    {
        java.lang.Object obj = _eventCycleConsequencesCoverageList.elementAt(index);
        _eventCycleConsequencesCoverageList.removeElementAt(index);
        return (edit.common.vo.EventCycleConsequencesCoverageVO) obj;
    } //-- edit.common.vo.EventCycleConsequencesCoverageVO removeEventCycleConsequencesCoverageVO(int) 

    /**
     * Method setEventCycleConsequencesCoverageVO
     * 
     * @param index
     * @param vEventCycleConsequencesCoverageVO
     */
    public void setEventCycleConsequencesCoverageVO(int index, edit.common.vo.EventCycleConsequencesCoverageVO vEventCycleConsequencesCoverageVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _eventCycleConsequencesCoverageList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vEventCycleConsequencesCoverageVO.setParentVO(this.getClass(), this);
        _eventCycleConsequencesCoverageList.setElementAt(vEventCycleConsequencesCoverageVO, index);
    } //-- void setEventCycleConsequencesCoverageVO(int, edit.common.vo.EventCycleConsequencesCoverageVO) 

    /**
     * Method setEventCycleConsequencesCoverageVO
     * 
     * @param EventCycleConsequencesCoverageVOArray
     */
    public void setEventCycleConsequencesCoverageVO(edit.common.vo.EventCycleConsequencesCoverageVO[] EventCycleConsequencesCoverageVOArray)
    {
        //-- copy array
        _eventCycleConsequencesCoverageList.removeAllElements();
        for (int i = 0; i < EventCycleConsequencesCoverageVOArray.length; i++) {
            EventCycleConsequencesCoverageVOArray[i].setParentVO(this.getClass(), this);
            _eventCycleConsequencesCoverageList.addElement(EventCycleConsequencesCoverageVOArray[i]);
        }
    } //-- void setEventCycleConsequencesCoverageVO(edit.common.vo.EventCycleConsequencesCoverageVO) 

    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    }
    
    public void setGUID(java.lang.String GUID)
    {
        this._GUID = GUID;
        
        super.setVoChanged(true);
    }

    public void setPolicyNumber(java.lang.String policyNumber)
    {
        this._policyNumber = policyNumber;
        
        super.setVoChanged(true);
    }

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PolicySummaryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PolicySummaryVO) Unmarshaller.unmarshal(edit.common.vo.PolicySummaryVO.class, reader);
    } 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
