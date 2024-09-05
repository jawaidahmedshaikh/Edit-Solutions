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
 * Class ParticipatingAgentVO.
 * 
 * @version $Revision$ $Date$
 */
public class ParticipatingAgentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _participatingAgentPK
     */
    private long _participatingAgentPK;

    /**
     * keeps track of state for field: _participatingAgentPK
     */
    private boolean _has_participatingAgentPK;

    /**
     * Field _bonusProgramFK
     */
    private long _bonusProgramFK;

    /**
     * keeps track of state for field: _bonusProgramFK
     */
    private boolean _has_bonusProgramFK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;

    /**
     * Field _produceCheckInd
     */
    private java.lang.String _produceCheckInd;

    /**
     * Field _bonusProgramOverrideInd
     */
    private java.lang.String _bonusProgramOverrideInd;

    /**
     * Field _bonusTaxableAmount
     */
    private java.math.BigDecimal _bonusTaxableAmount;

    /**
     * Field _lastStatementDateTime
     */
    private java.lang.String _lastStatementDateTime;

    /**
     * Field _lastCheckDateTime
     */
    private java.lang.String _lastCheckDateTime;

    /**
     * Field _lastCheckAmount
     */
    private java.math.BigDecimal _lastCheckAmount;

    /**
     * Field _bonusCommissionHistoryVOList
     */
    private java.util.Vector _bonusCommissionHistoryVOList;

    /**
     * Field _premiumLevelVOList
     */
    private java.util.Vector _premiumLevelVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ParticipatingAgentVO() {
        super();
        _bonusCommissionHistoryVOList = new Vector();
        _premiumLevelVOList = new Vector();
    } //-- edit.common.vo.ParticipatingAgentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusCommissionHistoryVO
     * 
     * @param vBonusCommissionHistoryVO
     */
    public void addBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO vBonusCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCommissionHistoryVO.setParentVO(this.getClass(), this);
        _bonusCommissionHistoryVOList.addElement(vBonusCommissionHistoryVO);
    } //-- void addBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method addBonusCommissionHistoryVO
     * 
     * @param index
     * @param vBonusCommissionHistoryVO
     */
    public void addBonusCommissionHistoryVO(int index, edit.common.vo.BonusCommissionHistoryVO vBonusCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCommissionHistoryVO.setParentVO(this.getClass(), this);
        _bonusCommissionHistoryVOList.insertElementAt(vBonusCommissionHistoryVO, index);
    } //-- void addBonusCommissionHistoryVO(int, edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method addPremiumLevelVO
     * 
     * @param vPremiumLevelVO
     */
    public void addPremiumLevelVO(edit.common.vo.PremiumLevelVO vPremiumLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPremiumLevelVO.setParentVO(this.getClass(), this);
        _premiumLevelVOList.addElement(vPremiumLevelVO);
    } //-- void addPremiumLevelVO(edit.common.vo.PremiumLevelVO) 

    /**
     * Method addPremiumLevelVO
     * 
     * @param index
     * @param vPremiumLevelVO
     */
    public void addPremiumLevelVO(int index, edit.common.vo.PremiumLevelVO vPremiumLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPremiumLevelVO.setParentVO(this.getClass(), this);
        _premiumLevelVOList.insertElementAt(vPremiumLevelVO, index);
    } //-- void addPremiumLevelVO(int, edit.common.vo.PremiumLevelVO) 

    /**
     * Method enumerateBonusCommissionHistoryVO
     */
    public java.util.Enumeration enumerateBonusCommissionHistoryVO()
    {
        return _bonusCommissionHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateBonusCommissionHistoryVO() 

    /**
     * Method enumeratePremiumLevelVO
     */
    public java.util.Enumeration enumeratePremiumLevelVO()
    {
        return _premiumLevelVOList.elements();
    } //-- java.util.Enumeration enumeratePremiumLevelVO() 

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
        
        if (obj instanceof ParticipatingAgentVO) {
        
            ParticipatingAgentVO temp = (ParticipatingAgentVO)obj;
            if (this._participatingAgentPK != temp._participatingAgentPK)
                return false;
            if (this._has_participatingAgentPK != temp._has_participatingAgentPK)
                return false;
            if (this._bonusProgramFK != temp._bonusProgramFK)
                return false;
            if (this._has_bonusProgramFK != temp._has_bonusProgramFK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            if (this._produceCheckInd != null) {
                if (temp._produceCheckInd == null) return false;
                else if (!(this._produceCheckInd.equals(temp._produceCheckInd))) 
                    return false;
            }
            else if (temp._produceCheckInd != null)
                return false;
            if (this._bonusProgramOverrideInd != null) {
                if (temp._bonusProgramOverrideInd == null) return false;
                else if (!(this._bonusProgramOverrideInd.equals(temp._bonusProgramOverrideInd))) 
                    return false;
            }
            else if (temp._bonusProgramOverrideInd != null)
                return false;
            if (this._bonusTaxableAmount != null) {
                if (temp._bonusTaxableAmount == null) return false;
                else if (!(this._bonusTaxableAmount.equals(temp._bonusTaxableAmount))) 
                    return false;
            }
            else if (temp._bonusTaxableAmount != null)
                return false;
            if (this._lastStatementDateTime != null) {
                if (temp._lastStatementDateTime == null) return false;
                else if (!(this._lastStatementDateTime.equals(temp._lastStatementDateTime))) 
                    return false;
            }
            else if (temp._lastStatementDateTime != null)
                return false;
            if (this._lastCheckDateTime != null) {
                if (temp._lastCheckDateTime == null) return false;
                else if (!(this._lastCheckDateTime.equals(temp._lastCheckDateTime))) 
                    return false;
            }
            else if (temp._lastCheckDateTime != null)
                return false;
            if (this._lastCheckAmount != null) {
                if (temp._lastCheckAmount == null) return false;
                else if (!(this._lastCheckAmount.equals(temp._lastCheckAmount))) 
                    return false;
            }
            else if (temp._lastCheckAmount != null)
                return false;
            if (this._bonusCommissionHistoryVOList != null) {
                if (temp._bonusCommissionHistoryVOList == null) return false;
                else if (!(this._bonusCommissionHistoryVOList.equals(temp._bonusCommissionHistoryVOList))) 
                    return false;
            }
            else if (temp._bonusCommissionHistoryVOList != null)
                return false;
            if (this._premiumLevelVOList != null) {
                if (temp._premiumLevelVOList == null) return false;
                else if (!(this._premiumLevelVOList.equals(temp._premiumLevelVOList))) 
                    return false;
            }
            else if (temp._premiumLevelVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCommissionHistoryVO getBonusCommissionHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCommissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusCommissionHistoryVO) _bonusCommissionHistoryVOList.elementAt(index);
    } //-- edit.common.vo.BonusCommissionHistoryVO getBonusCommissionHistoryVO(int) 

    /**
     * Method getBonusCommissionHistoryVO
     */
    public edit.common.vo.BonusCommissionHistoryVO[] getBonusCommissionHistoryVO()
    {
        int size = _bonusCommissionHistoryVOList.size();
        edit.common.vo.BonusCommissionHistoryVO[] mArray = new edit.common.vo.BonusCommissionHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusCommissionHistoryVO) _bonusCommissionHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusCommissionHistoryVO[] getBonusCommissionHistoryVO() 

    /**
     * Method getBonusCommissionHistoryVOCount
     */
    public int getBonusCommissionHistoryVOCount()
    {
        return _bonusCommissionHistoryVOList.size();
    } //-- int getBonusCommissionHistoryVOCount() 

    /**
     * Method getBonusProgramFKReturns the value of field
     * 'bonusProgramFK'.
     * 
     * @return the value of field 'bonusProgramFK'.
     */
    public long getBonusProgramFK()
    {
        return this._bonusProgramFK;
    } //-- long getBonusProgramFK() 

    /**
     * Method getBonusProgramOverrideIndReturns the value of field
     * 'bonusProgramOverrideInd'.
     * 
     * @return the value of field 'bonusProgramOverrideInd'.
     */
    public java.lang.String getBonusProgramOverrideInd()
    {
        return this._bonusProgramOverrideInd;
    } //-- java.lang.String getBonusProgramOverrideInd() 

    /**
     * Method getBonusTaxableAmountReturns the value of field
     * 'bonusTaxableAmount'.
     * 
     * @return the value of field 'bonusTaxableAmount'.
     */
    public java.math.BigDecimal getBonusTaxableAmount()
    {
        return this._bonusTaxableAmount;
    } //-- java.math.BigDecimal getBonusTaxableAmount() 

    /**
     * Method getLastCheckAmountReturns the value of field
     * 'lastCheckAmount'.
     * 
     * @return the value of field 'lastCheckAmount'.
     */
    public java.math.BigDecimal getLastCheckAmount()
    {
        return this._lastCheckAmount;
    } //-- java.math.BigDecimal getLastCheckAmount() 

    /**
     * Method getLastCheckDateTimeReturns the value of field
     * 'lastCheckDateTime'.
     * 
     * @return the value of field 'lastCheckDateTime'.
     */
    public java.lang.String getLastCheckDateTime()
    {
        return this._lastCheckDateTime;
    } //-- java.lang.String getLastCheckDateTime() 

    /**
     * Method getLastStatementDateTimeReturns the value of field
     * 'lastStatementDateTime'.
     * 
     * @return the value of field 'lastStatementDateTime'.
     */
    public java.lang.String getLastStatementDateTime()
    {
        return this._lastStatementDateTime;
    } //-- java.lang.String getLastStatementDateTime() 

    /**
     * Method getParticipatingAgentPKReturns the value of field
     * 'participatingAgentPK'.
     * 
     * @return the value of field 'participatingAgentPK'.
     */
    public long getParticipatingAgentPK()
    {
        return this._participatingAgentPK;
    } //-- long getParticipatingAgentPK() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

    /**
     * Method getPremiumLevelVO
     * 
     * @param index
     */
    public edit.common.vo.PremiumLevelVO getPremiumLevelVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _premiumLevelVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PremiumLevelVO) _premiumLevelVOList.elementAt(index);
    } //-- edit.common.vo.PremiumLevelVO getPremiumLevelVO(int) 

    /**
     * Method getPremiumLevelVO
     */
    public edit.common.vo.PremiumLevelVO[] getPremiumLevelVO()
    {
        int size = _premiumLevelVOList.size();
        edit.common.vo.PremiumLevelVO[] mArray = new edit.common.vo.PremiumLevelVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PremiumLevelVO) _premiumLevelVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PremiumLevelVO[] getPremiumLevelVO() 

    /**
     * Method getPremiumLevelVOCount
     */
    public int getPremiumLevelVOCount()
    {
        return _premiumLevelVOList.size();
    } //-- int getPremiumLevelVOCount() 

    /**
     * Method getProduceCheckIndReturns the value of field
     * 'produceCheckInd'.
     * 
     * @return the value of field 'produceCheckInd'.
     */
    public java.lang.String getProduceCheckInd()
    {
        return this._produceCheckInd;
    } //-- java.lang.String getProduceCheckInd() 

    /**
     * Method hasBonusProgramFK
     */
    public boolean hasBonusProgramFK()
    {
        return this._has_bonusProgramFK;
    } //-- boolean hasBonusProgramFK() 

    /**
     * Method hasParticipatingAgentPK
     */
    public boolean hasParticipatingAgentPK()
    {
        return this._has_participatingAgentPK;
    } //-- boolean hasParticipatingAgentPK() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method removeAllBonusCommissionHistoryVO
     */
    public void removeAllBonusCommissionHistoryVO()
    {
        _bonusCommissionHistoryVOList.removeAllElements();
    } //-- void removeAllBonusCommissionHistoryVO() 

    /**
     * Method removeAllPremiumLevelVO
     */
    public void removeAllPremiumLevelVO()
    {
        _premiumLevelVOList.removeAllElements();
    } //-- void removeAllPremiumLevelVO() 

    /**
     * Method removeBonusCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCommissionHistoryVO removeBonusCommissionHistoryVO(int index)
    {
        java.lang.Object obj = _bonusCommissionHistoryVOList.elementAt(index);
        _bonusCommissionHistoryVOList.removeElementAt(index);
        return (edit.common.vo.BonusCommissionHistoryVO) obj;
    } //-- edit.common.vo.BonusCommissionHistoryVO removeBonusCommissionHistoryVO(int) 

    /**
     * Method removePremiumLevelVO
     * 
     * @param index
     */
    public edit.common.vo.PremiumLevelVO removePremiumLevelVO(int index)
    {
        java.lang.Object obj = _premiumLevelVOList.elementAt(index);
        _premiumLevelVOList.removeElementAt(index);
        return (edit.common.vo.PremiumLevelVO) obj;
    } //-- edit.common.vo.PremiumLevelVO removePremiumLevelVO(int) 

    /**
     * Method setBonusCommissionHistoryVO
     * 
     * @param index
     * @param vBonusCommissionHistoryVO
     */
    public void setBonusCommissionHistoryVO(int index, edit.common.vo.BonusCommissionHistoryVO vBonusCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCommissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusCommissionHistoryVO.setParentVO(this.getClass(), this);
        _bonusCommissionHistoryVOList.setElementAt(vBonusCommissionHistoryVO, index);
    } //-- void setBonusCommissionHistoryVO(int, edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method setBonusCommissionHistoryVO
     * 
     * @param bonusCommissionHistoryVOArray
     */
    public void setBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO[] bonusCommissionHistoryVOArray)
    {
        //-- copy array
        _bonusCommissionHistoryVOList.removeAllElements();
        for (int i = 0; i < bonusCommissionHistoryVOArray.length; i++) {
            bonusCommissionHistoryVOArray[i].setParentVO(this.getClass(), this);
            _bonusCommissionHistoryVOList.addElement(bonusCommissionHistoryVOArray[i]);
        }
    } //-- void setBonusCommissionHistoryVO(edit.common.vo.BonusCommissionHistoryVO) 

    /**
     * Method setBonusProgramFKSets the value of field
     * 'bonusProgramFK'.
     * 
     * @param bonusProgramFK the value of field 'bonusProgramFK'.
     */
    public void setBonusProgramFK(long bonusProgramFK)
    {
        this._bonusProgramFK = bonusProgramFK;
        
        super.setVoChanged(true);
        this._has_bonusProgramFK = true;
    } //-- void setBonusProgramFK(long) 

    /**
     * Method setBonusProgramOverrideIndSets the value of field
     * 'bonusProgramOverrideInd'.
     * 
     * @param bonusProgramOverrideInd the value of field
     * 'bonusProgramOverrideInd'.
     */
    public void setBonusProgramOverrideInd(java.lang.String bonusProgramOverrideInd)
    {
        this._bonusProgramOverrideInd = bonusProgramOverrideInd;
        
        super.setVoChanged(true);
    } //-- void setBonusProgramOverrideInd(java.lang.String) 

    /**
     * Method setBonusTaxableAmountSets the value of field
     * 'bonusTaxableAmount'.
     * 
     * @param bonusTaxableAmount the value of field
     * 'bonusTaxableAmount'.
     */
    public void setBonusTaxableAmount(java.math.BigDecimal bonusTaxableAmount)
    {
        this._bonusTaxableAmount = bonusTaxableAmount;
        
        super.setVoChanged(true);
    } //-- void setBonusTaxableAmount(java.math.BigDecimal) 

    /**
     * Method setLastCheckAmountSets the value of field
     * 'lastCheckAmount'.
     * 
     * @param lastCheckAmount the value of field 'lastCheckAmount'.
     */
    public void setLastCheckAmount(java.math.BigDecimal lastCheckAmount)
    {
        this._lastCheckAmount = lastCheckAmount;
        
        super.setVoChanged(true);
    } //-- void setLastCheckAmount(java.math.BigDecimal) 

    /**
     * Method setLastCheckDateTimeSets the value of field
     * 'lastCheckDateTime'.
     * 
     * @param lastCheckDateTime the value of field
     * 'lastCheckDateTime'.
     */
    public void setLastCheckDateTime(java.lang.String lastCheckDateTime)
    {
        this._lastCheckDateTime = lastCheckDateTime;
        
        super.setVoChanged(true);
    } //-- void setLastCheckDateTime(java.lang.String) 

    /**
     * Method setLastStatementDateTimeSets the value of field
     * 'lastStatementDateTime'.
     * 
     * @param lastStatementDateTime the value of field
     * 'lastStatementDateTime'.
     */
    public void setLastStatementDateTime(java.lang.String lastStatementDateTime)
    {
        this._lastStatementDateTime = lastStatementDateTime;
        
        super.setVoChanged(true);
    } //-- void setLastStatementDateTime(java.lang.String) 

    /**
     * Method setParticipatingAgentPKSets the value of field
     * 'participatingAgentPK'.
     * 
     * @param participatingAgentPK the value of field
     * 'participatingAgentPK'.
     */
    public void setParticipatingAgentPK(long participatingAgentPK)
    {
        this._participatingAgentPK = participatingAgentPK;
        
        super.setVoChanged(true);
        this._has_participatingAgentPK = true;
    } //-- void setParticipatingAgentPK(long) 

    /**
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

    /**
     * Method setPremiumLevelVO
     * 
     * @param index
     * @param vPremiumLevelVO
     */
    public void setPremiumLevelVO(int index, edit.common.vo.PremiumLevelVO vPremiumLevelVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _premiumLevelVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPremiumLevelVO.setParentVO(this.getClass(), this);
        _premiumLevelVOList.setElementAt(vPremiumLevelVO, index);
    } //-- void setPremiumLevelVO(int, edit.common.vo.PremiumLevelVO) 

    /**
     * Method setPremiumLevelVO
     * 
     * @param premiumLevelVOArray
     */
    public void setPremiumLevelVO(edit.common.vo.PremiumLevelVO[] premiumLevelVOArray)
    {
        //-- copy array
        _premiumLevelVOList.removeAllElements();
        for (int i = 0; i < premiumLevelVOArray.length; i++) {
            premiumLevelVOArray[i].setParentVO(this.getClass(), this);
            _premiumLevelVOList.addElement(premiumLevelVOArray[i]);
        }
    } //-- void setPremiumLevelVO(edit.common.vo.PremiumLevelVO) 

    /**
     * Method setProduceCheckIndSets the value of field
     * 'produceCheckInd'.
     * 
     * @param produceCheckInd the value of field 'produceCheckInd'.
     */
    public void setProduceCheckInd(java.lang.String produceCheckInd)
    {
        this._produceCheckInd = produceCheckInd;
        
        super.setVoChanged(true);
    } //-- void setProduceCheckInd(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ParticipatingAgentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ParticipatingAgentVO) Unmarshaller.unmarshal(edit.common.vo.ParticipatingAgentVO.class, reader);
    } //-- edit.common.vo.ParticipatingAgentVO unmarshal(java.io.Reader) 

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
