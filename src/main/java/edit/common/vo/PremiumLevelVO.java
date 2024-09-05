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
 * Class PremiumLevelVO.
 * 
 * @version $Revision$ $Date$
 */
public class PremiumLevelVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _premiumLevelPK
     */
    private long _premiumLevelPK;

    /**
     * keeps track of state for field: _premiumLevelPK
     */
    private boolean _has_premiumLevelPK;

    /**
     * Field _bonusProgramFK
     */
    private long _bonusProgramFK;

    /**
     * keeps track of state for field: _bonusProgramFK
     */
    private boolean _has_bonusProgramFK;

    /**
     * Field _participatingAgentFK
     */
    private long _participatingAgentFK;

    /**
     * keeps track of state for field: _participatingAgentFK
     */
    private boolean _has_participatingAgentFK;

    /**
     * Field _issuePremiumLevel
     */
    private java.math.BigDecimal _issuePremiumLevel;

    /**
     * Field _productLevelIncreasePercent
     */
    private java.math.BigDecimal _productLevelIncreasePercent;

    /**
     * Field _productLevelIncreaseAmount
     */
    private java.math.BigDecimal _productLevelIncreaseAmount;

    /**
     * Field _increaseStopAmount
     */
    private java.math.BigDecimal _increaseStopAmount;

    /**
     * Field _bonusCriteriaVOList
     */
    private java.util.Vector _bonusCriteriaVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PremiumLevelVO() {
        super();
        _bonusCriteriaVOList = new Vector();
    } //-- edit.common.vo.PremiumLevelVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusCriteriaVO
     * 
     * @param vBonusCriteriaVO
     */
    public void addBonusCriteriaVO(edit.common.vo.BonusCriteriaVO vBonusCriteriaVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCriteriaVO.setParentVO(this.getClass(), this);
        _bonusCriteriaVOList.addElement(vBonusCriteriaVO);
    } //-- void addBonusCriteriaVO(edit.common.vo.BonusCriteriaVO) 

    /**
     * Method addBonusCriteriaVO
     * 
     * @param index
     * @param vBonusCriteriaVO
     */
    public void addBonusCriteriaVO(int index, edit.common.vo.BonusCriteriaVO vBonusCriteriaVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCriteriaVO.setParentVO(this.getClass(), this);
        _bonusCriteriaVOList.insertElementAt(vBonusCriteriaVO, index);
    } //-- void addBonusCriteriaVO(int, edit.common.vo.BonusCriteriaVO) 

    /**
     * Method enumerateBonusCriteriaVO
     */
    public java.util.Enumeration enumerateBonusCriteriaVO()
    {
        return _bonusCriteriaVOList.elements();
    } //-- java.util.Enumeration enumerateBonusCriteriaVO() 

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
        
        if (obj instanceof PremiumLevelVO) {
        
            PremiumLevelVO temp = (PremiumLevelVO)obj;
            if (this._premiumLevelPK != temp._premiumLevelPK)
                return false;
            if (this._has_premiumLevelPK != temp._has_premiumLevelPK)
                return false;
            if (this._bonusProgramFK != temp._bonusProgramFK)
                return false;
            if (this._has_bonusProgramFK != temp._has_bonusProgramFK)
                return false;
            if (this._participatingAgentFK != temp._participatingAgentFK)
                return false;
            if (this._has_participatingAgentFK != temp._has_participatingAgentFK)
                return false;
            if (this._issuePremiumLevel != null) {
                if (temp._issuePremiumLevel == null) return false;
                else if (!(this._issuePremiumLevel.equals(temp._issuePremiumLevel))) 
                    return false;
            }
            else if (temp._issuePremiumLevel != null)
                return false;
            if (this._productLevelIncreasePercent != null) {
                if (temp._productLevelIncreasePercent == null) return false;
                else if (!(this._productLevelIncreasePercent.equals(temp._productLevelIncreasePercent))) 
                    return false;
            }
            else if (temp._productLevelIncreasePercent != null)
                return false;
            if (this._productLevelIncreaseAmount != null) {
                if (temp._productLevelIncreaseAmount == null) return false;
                else if (!(this._productLevelIncreaseAmount.equals(temp._productLevelIncreaseAmount))) 
                    return false;
            }
            else if (temp._productLevelIncreaseAmount != null)
                return false;
            if (this._increaseStopAmount != null) {
                if (temp._increaseStopAmount == null) return false;
                else if (!(this._increaseStopAmount.equals(temp._increaseStopAmount))) 
                    return false;
            }
            else if (temp._increaseStopAmount != null)
                return false;
            if (this._bonusCriteriaVOList != null) {
                if (temp._bonusCriteriaVOList == null) return false;
                else if (!(this._bonusCriteriaVOList.equals(temp._bonusCriteriaVOList))) 
                    return false;
            }
            else if (temp._bonusCriteriaVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusCriteriaVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCriteriaVO getBonusCriteriaVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCriteriaVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusCriteriaVO) _bonusCriteriaVOList.elementAt(index);
    } //-- edit.common.vo.BonusCriteriaVO getBonusCriteriaVO(int) 

    /**
     * Method getBonusCriteriaVO
     */
    public edit.common.vo.BonusCriteriaVO[] getBonusCriteriaVO()
    {
        int size = _bonusCriteriaVOList.size();
        edit.common.vo.BonusCriteriaVO[] mArray = new edit.common.vo.BonusCriteriaVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusCriteriaVO) _bonusCriteriaVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusCriteriaVO[] getBonusCriteriaVO() 

    /**
     * Method getBonusCriteriaVOCount
     */
    public int getBonusCriteriaVOCount()
    {
        return _bonusCriteriaVOList.size();
    } //-- int getBonusCriteriaVOCount() 

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
     * Method getIncreaseStopAmountReturns the value of field
     * 'increaseStopAmount'.
     * 
     * @return the value of field 'increaseStopAmount'.
     */
    public java.math.BigDecimal getIncreaseStopAmount()
    {
        return this._increaseStopAmount;
    } //-- java.math.BigDecimal getIncreaseStopAmount() 

    /**
     * Method getIssuePremiumLevelReturns the value of field
     * 'issuePremiumLevel'.
     * 
     * @return the value of field 'issuePremiumLevel'.
     */
    public java.math.BigDecimal getIssuePremiumLevel()
    {
        return this._issuePremiumLevel;
    } //-- java.math.BigDecimal getIssuePremiumLevel() 

    /**
     * Method getParticipatingAgentFKReturns the value of field
     * 'participatingAgentFK'.
     * 
     * @return the value of field 'participatingAgentFK'.
     */
    public long getParticipatingAgentFK()
    {
        return this._participatingAgentFK;
    } //-- long getParticipatingAgentFK() 

    /**
     * Method getPremiumLevelPKReturns the value of field
     * 'premiumLevelPK'.
     * 
     * @return the value of field 'premiumLevelPK'.
     */
    public long getPremiumLevelPK()
    {
        return this._premiumLevelPK;
    } //-- long getPremiumLevelPK() 

    /**
     * Method getProductLevelIncreaseAmountReturns the value of
     * field 'productLevelIncreaseAmount'.
     * 
     * @return the value of field 'productLevelIncreaseAmount'.
     */
    public java.math.BigDecimal getProductLevelIncreaseAmount()
    {
        return this._productLevelIncreaseAmount;
    } //-- java.math.BigDecimal getProductLevelIncreaseAmount() 

    /**
     * Method getProductLevelIncreasePercentReturns the value of
     * field 'productLevelIncreasePercent'.
     * 
     * @return the value of field 'productLevelIncreasePercent'.
     */
    public java.math.BigDecimal getProductLevelIncreasePercent()
    {
        return this._productLevelIncreasePercent;
    } //-- java.math.BigDecimal getProductLevelIncreasePercent() 

    /**
     * Method hasBonusProgramFK
     */
    public boolean hasBonusProgramFK()
    {
        return this._has_bonusProgramFK;
    } //-- boolean hasBonusProgramFK() 

    /**
     * Method hasParticipatingAgentFK
     */
    public boolean hasParticipatingAgentFK()
    {
        return this._has_participatingAgentFK;
    } //-- boolean hasParticipatingAgentFK() 

    /**
     * Method hasPremiumLevelPK
     */
    public boolean hasPremiumLevelPK()
    {
        return this._has_premiumLevelPK;
    } //-- boolean hasPremiumLevelPK() 

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
     * Method removeAllBonusCriteriaVO
     */
    public void removeAllBonusCriteriaVO()
    {
        _bonusCriteriaVOList.removeAllElements();
    } //-- void removeAllBonusCriteriaVO() 

    /**
     * Method removeBonusCriteriaVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCriteriaVO removeBonusCriteriaVO(int index)
    {
        java.lang.Object obj = _bonusCriteriaVOList.elementAt(index);
        _bonusCriteriaVOList.removeElementAt(index);
        return (edit.common.vo.BonusCriteriaVO) obj;
    } //-- edit.common.vo.BonusCriteriaVO removeBonusCriteriaVO(int) 

    /**
     * Method setBonusCriteriaVO
     * 
     * @param index
     * @param vBonusCriteriaVO
     */
    public void setBonusCriteriaVO(int index, edit.common.vo.BonusCriteriaVO vBonusCriteriaVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCriteriaVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusCriteriaVO.setParentVO(this.getClass(), this);
        _bonusCriteriaVOList.setElementAt(vBonusCriteriaVO, index);
    } //-- void setBonusCriteriaVO(int, edit.common.vo.BonusCriteriaVO) 

    /**
     * Method setBonusCriteriaVO
     * 
     * @param bonusCriteriaVOArray
     */
    public void setBonusCriteriaVO(edit.common.vo.BonusCriteriaVO[] bonusCriteriaVOArray)
    {
        //-- copy array
        _bonusCriteriaVOList.removeAllElements();
        for (int i = 0; i < bonusCriteriaVOArray.length; i++) {
            bonusCriteriaVOArray[i].setParentVO(this.getClass(), this);
            _bonusCriteriaVOList.addElement(bonusCriteriaVOArray[i]);
        }
    } //-- void setBonusCriteriaVO(edit.common.vo.BonusCriteriaVO) 

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
     * Method setIncreaseStopAmountSets the value of field
     * 'increaseStopAmount'.
     * 
     * @param increaseStopAmount the value of field
     * 'increaseStopAmount'.
     */
    public void setIncreaseStopAmount(java.math.BigDecimal increaseStopAmount)
    {
        this._increaseStopAmount = increaseStopAmount;
        
        super.setVoChanged(true);
    } //-- void setIncreaseStopAmount(java.math.BigDecimal) 

    /**
     * Method setIssuePremiumLevelSets the value of field
     * 'issuePremiumLevel'.
     * 
     * @param issuePremiumLevel the value of field
     * 'issuePremiumLevel'.
     */
    public void setIssuePremiumLevel(java.math.BigDecimal issuePremiumLevel)
    {
        this._issuePremiumLevel = issuePremiumLevel;
        
        super.setVoChanged(true);
    } //-- void setIssuePremiumLevel(java.math.BigDecimal) 

    /**
     * Method setParticipatingAgentFKSets the value of field
     * 'participatingAgentFK'.
     * 
     * @param participatingAgentFK the value of field
     * 'participatingAgentFK'.
     */
    public void setParticipatingAgentFK(long participatingAgentFK)
    {
        this._participatingAgentFK = participatingAgentFK;
        
        super.setVoChanged(true);
        this._has_participatingAgentFK = true;
    } //-- void setParticipatingAgentFK(long) 

    /**
     * Method setPremiumLevelPKSets the value of field
     * 'premiumLevelPK'.
     * 
     * @param premiumLevelPK the value of field 'premiumLevelPK'.
     */
    public void setPremiumLevelPK(long premiumLevelPK)
    {
        this._premiumLevelPK = premiumLevelPK;
        
        super.setVoChanged(true);
        this._has_premiumLevelPK = true;
    } //-- void setPremiumLevelPK(long) 

    /**
     * Method setProductLevelIncreaseAmountSets the value of field
     * 'productLevelIncreaseAmount'.
     * 
     * @param productLevelIncreaseAmount the value of field
     * 'productLevelIncreaseAmount'.
     */
    public void setProductLevelIncreaseAmount(java.math.BigDecimal productLevelIncreaseAmount)
    {
        this._productLevelIncreaseAmount = productLevelIncreaseAmount;
        
        super.setVoChanged(true);
    } //-- void setProductLevelIncreaseAmount(java.math.BigDecimal) 

    /**
     * Method setProductLevelIncreasePercentSets the value of field
     * 'productLevelIncreasePercent'.
     * 
     * @param productLevelIncreasePercent the value of field
     * 'productLevelIncreasePercent'.
     */
    public void setProductLevelIncreasePercent(java.math.BigDecimal productLevelIncreasePercent)
    {
        this._productLevelIncreasePercent = productLevelIncreasePercent;
        
        super.setVoChanged(true);
    } //-- void setProductLevelIncreasePercent(java.math.BigDecimal) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PremiumLevelVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PremiumLevelVO) Unmarshaller.unmarshal(edit.common.vo.PremiumLevelVO.class, reader);
    } //-- edit.common.vo.PremiumLevelVO unmarshal(java.io.Reader) 

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
