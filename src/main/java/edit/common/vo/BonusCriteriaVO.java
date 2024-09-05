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
 * Class BonusCriteriaVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusCriteriaVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bonusCriteriaPK
     */
    private long _bonusCriteriaPK;

    /**
     * keeps track of state for field: _bonusCriteriaPK
     */
    private boolean _has_bonusCriteriaPK;

    /**
     * Field _premiumLevelFK
     */
    private long _premiumLevelFK;

    /**
     * keeps track of state for field: _premiumLevelFK
     */
    private boolean _has_premiumLevelFK;

    /**
     * Field _bonusAmount
     */
    private java.math.BigDecimal _bonusAmount;

    /**
     * Field _excessBonusAmount
     */
    private java.math.BigDecimal _excessBonusAmount;

    /**
     * Field _bonusBasisPoint
     */
    private java.math.BigDecimal _bonusBasisPoint;

    /**
     * Field _excessBonusBasisPoint
     */
    private java.math.BigDecimal _excessBonusBasisPoint;

    /**
     * Field _excessPremiumLevel
     */
    private java.math.BigDecimal _excessPremiumLevel;

    /**
     * Field _bonusContributingProductVOList
     */
    private java.util.Vector _bonusContributingProductVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusCriteriaVO() {
        super();
        _bonusContributingProductVOList = new Vector();
    } //-- edit.common.vo.BonusCriteriaVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusContributingProductVO
     * 
     * @param vBonusContributingProductVO
     */
    public void addBonusContributingProductVO(edit.common.vo.BonusContributingProductVO vBonusContributingProductVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusContributingProductVO.setParentVO(this.getClass(), this);
        _bonusContributingProductVOList.addElement(vBonusContributingProductVO);
    } //-- void addBonusContributingProductVO(edit.common.vo.BonusContributingProductVO) 

    /**
     * Method addBonusContributingProductVO
     * 
     * @param index
     * @param vBonusContributingProductVO
     */
    public void addBonusContributingProductVO(int index, edit.common.vo.BonusContributingProductVO vBonusContributingProductVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusContributingProductVO.setParentVO(this.getClass(), this);
        _bonusContributingProductVOList.insertElementAt(vBonusContributingProductVO, index);
    } //-- void addBonusContributingProductVO(int, edit.common.vo.BonusContributingProductVO) 

    /**
     * Method enumerateBonusContributingProductVO
     */
    public java.util.Enumeration enumerateBonusContributingProductVO()
    {
        return _bonusContributingProductVOList.elements();
    } //-- java.util.Enumeration enumerateBonusContributingProductVO() 

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
        
        if (obj instanceof BonusCriteriaVO) {
        
            BonusCriteriaVO temp = (BonusCriteriaVO)obj;
            if (this._bonusCriteriaPK != temp._bonusCriteriaPK)
                return false;
            if (this._has_bonusCriteriaPK != temp._has_bonusCriteriaPK)
                return false;
            if (this._premiumLevelFK != temp._premiumLevelFK)
                return false;
            if (this._has_premiumLevelFK != temp._has_premiumLevelFK)
                return false;
            if (this._bonusAmount != null) {
                if (temp._bonusAmount == null) return false;
                else if (!(this._bonusAmount.equals(temp._bonusAmount))) 
                    return false;
            }
            else if (temp._bonusAmount != null)
                return false;
            if (this._excessBonusAmount != null) {
                if (temp._excessBonusAmount == null) return false;
                else if (!(this._excessBonusAmount.equals(temp._excessBonusAmount))) 
                    return false;
            }
            else if (temp._excessBonusAmount != null)
                return false;
            if (this._bonusBasisPoint != null) {
                if (temp._bonusBasisPoint == null) return false;
                else if (!(this._bonusBasisPoint.equals(temp._bonusBasisPoint))) 
                    return false;
            }
            else if (temp._bonusBasisPoint != null)
                return false;
            if (this._excessBonusBasisPoint != null) {
                if (temp._excessBonusBasisPoint == null) return false;
                else if (!(this._excessBonusBasisPoint.equals(temp._excessBonusBasisPoint))) 
                    return false;
            }
            else if (temp._excessBonusBasisPoint != null)
                return false;
            if (this._excessPremiumLevel != null) {
                if (temp._excessPremiumLevel == null) return false;
                else if (!(this._excessPremiumLevel.equals(temp._excessPremiumLevel))) 
                    return false;
            }
            else if (temp._excessPremiumLevel != null)
                return false;
            if (this._bonusContributingProductVOList != null) {
                if (temp._bonusContributingProductVOList == null) return false;
                else if (!(this._bonusContributingProductVOList.equals(temp._bonusContributingProductVOList))) 
                    return false;
            }
            else if (temp._bonusContributingProductVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusAmountReturns the value of field
     * 'bonusAmount'.
     * 
     * @return the value of field 'bonusAmount'.
     */
    public java.math.BigDecimal getBonusAmount()
    {
        return this._bonusAmount;
    } //-- java.math.BigDecimal getBonusAmount() 

    /**
     * Method getBonusBasisPointReturns the value of field
     * 'bonusBasisPoint'.
     * 
     * @return the value of field 'bonusBasisPoint'.
     */
    public java.math.BigDecimal getBonusBasisPoint()
    {
        return this._bonusBasisPoint;
    } //-- java.math.BigDecimal getBonusBasisPoint() 

    /**
     * Method getBonusContributingProductVO
     * 
     * @param index
     */
    public edit.common.vo.BonusContributingProductVO getBonusContributingProductVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusContributingProductVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusContributingProductVO) _bonusContributingProductVOList.elementAt(index);
    } //-- edit.common.vo.BonusContributingProductVO getBonusContributingProductVO(int) 

    /**
     * Method getBonusContributingProductVO
     */
    public edit.common.vo.BonusContributingProductVO[] getBonusContributingProductVO()
    {
        int size = _bonusContributingProductVOList.size();
        edit.common.vo.BonusContributingProductVO[] mArray = new edit.common.vo.BonusContributingProductVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusContributingProductVO) _bonusContributingProductVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusContributingProductVO[] getBonusContributingProductVO() 

    /**
     * Method getBonusContributingProductVOCount
     */
    public int getBonusContributingProductVOCount()
    {
        return _bonusContributingProductVOList.size();
    } //-- int getBonusContributingProductVOCount() 

    /**
     * Method getBonusCriteriaPKReturns the value of field
     * 'bonusCriteriaPK'.
     * 
     * @return the value of field 'bonusCriteriaPK'.
     */
    public long getBonusCriteriaPK()
    {
        return this._bonusCriteriaPK;
    } //-- long getBonusCriteriaPK() 

    /**
     * Method getExcessBonusAmountReturns the value of field
     * 'excessBonusAmount'.
     * 
     * @return the value of field 'excessBonusAmount'.
     */
    public java.math.BigDecimal getExcessBonusAmount()
    {
        return this._excessBonusAmount;
    } //-- java.math.BigDecimal getExcessBonusAmount() 

    /**
     * Method getExcessBonusBasisPointReturns the value of field
     * 'excessBonusBasisPoint'.
     * 
     * @return the value of field 'excessBonusBasisPoint'.
     */
    public java.math.BigDecimal getExcessBonusBasisPoint()
    {
        return this._excessBonusBasisPoint;
    } //-- java.math.BigDecimal getExcessBonusBasisPoint() 

    /**
     * Method getExcessPremiumLevelReturns the value of field
     * 'excessPremiumLevel'.
     * 
     * @return the value of field 'excessPremiumLevel'.
     */
    public java.math.BigDecimal getExcessPremiumLevel()
    {
        return this._excessPremiumLevel;
    } //-- java.math.BigDecimal getExcessPremiumLevel() 

    /**
     * Method getPremiumLevelFKReturns the value of field
     * 'premiumLevelFK'.
     * 
     * @return the value of field 'premiumLevelFK'.
     */
    public long getPremiumLevelFK()
    {
        return this._premiumLevelFK;
    } //-- long getPremiumLevelFK() 

    /**
     * Method hasBonusCriteriaPK
     */
    public boolean hasBonusCriteriaPK()
    {
        return this._has_bonusCriteriaPK;
    } //-- boolean hasBonusCriteriaPK() 

    /**
     * Method hasPremiumLevelFK
     */
    public boolean hasPremiumLevelFK()
    {
        return this._has_premiumLevelFK;
    } //-- boolean hasPremiumLevelFK() 

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
     * Method removeAllBonusContributingProductVO
     */
    public void removeAllBonusContributingProductVO()
    {
        _bonusContributingProductVOList.removeAllElements();
    } //-- void removeAllBonusContributingProductVO() 

    /**
     * Method removeBonusContributingProductVO
     * 
     * @param index
     */
    public edit.common.vo.BonusContributingProductVO removeBonusContributingProductVO(int index)
    {
        java.lang.Object obj = _bonusContributingProductVOList.elementAt(index);
        _bonusContributingProductVOList.removeElementAt(index);
        return (edit.common.vo.BonusContributingProductVO) obj;
    } //-- edit.common.vo.BonusContributingProductVO removeBonusContributingProductVO(int) 

    /**
     * Method setBonusAmountSets the value of field 'bonusAmount'.
     * 
     * @param bonusAmount the value of field 'bonusAmount'.
     */
    public void setBonusAmount(java.math.BigDecimal bonusAmount)
    {
        this._bonusAmount = bonusAmount;
        
        super.setVoChanged(true);
    } //-- void setBonusAmount(java.math.BigDecimal) 

    /**
     * Method setBonusBasisPointSets the value of field
     * 'bonusBasisPoint'.
     * 
     * @param bonusBasisPoint the value of field 'bonusBasisPoint'.
     */
    public void setBonusBasisPoint(java.math.BigDecimal bonusBasisPoint)
    {
        this._bonusBasisPoint = bonusBasisPoint;
        
        super.setVoChanged(true);
    } //-- void setBonusBasisPoint(java.math.BigDecimal) 

    /**
     * Method setBonusContributingProductVO
     * 
     * @param index
     * @param vBonusContributingProductVO
     */
    public void setBonusContributingProductVO(int index, edit.common.vo.BonusContributingProductVO vBonusContributingProductVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusContributingProductVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusContributingProductVO.setParentVO(this.getClass(), this);
        _bonusContributingProductVOList.setElementAt(vBonusContributingProductVO, index);
    } //-- void setBonusContributingProductVO(int, edit.common.vo.BonusContributingProductVO) 

    /**
     * Method setBonusContributingProductVO
     * 
     * @param bonusContributingProductVOArray
     */
    public void setBonusContributingProductVO(edit.common.vo.BonusContributingProductVO[] bonusContributingProductVOArray)
    {
        //-- copy array
        _bonusContributingProductVOList.removeAllElements();
        for (int i = 0; i < bonusContributingProductVOArray.length; i++) {
            bonusContributingProductVOArray[i].setParentVO(this.getClass(), this);
            _bonusContributingProductVOList.addElement(bonusContributingProductVOArray[i]);
        }
    } //-- void setBonusContributingProductVO(edit.common.vo.BonusContributingProductVO) 

    /**
     * Method setBonusCriteriaPKSets the value of field
     * 'bonusCriteriaPK'.
     * 
     * @param bonusCriteriaPK the value of field 'bonusCriteriaPK'.
     */
    public void setBonusCriteriaPK(long bonusCriteriaPK)
    {
        this._bonusCriteriaPK = bonusCriteriaPK;
        
        super.setVoChanged(true);
        this._has_bonusCriteriaPK = true;
    } //-- void setBonusCriteriaPK(long) 

    /**
     * Method setExcessBonusAmountSets the value of field
     * 'excessBonusAmount'.
     * 
     * @param excessBonusAmount the value of field
     * 'excessBonusAmount'.
     */
    public void setExcessBonusAmount(java.math.BigDecimal excessBonusAmount)
    {
        this._excessBonusAmount = excessBonusAmount;
        
        super.setVoChanged(true);
    } //-- void setExcessBonusAmount(java.math.BigDecimal) 

    /**
     * Method setExcessBonusBasisPointSets the value of field
     * 'excessBonusBasisPoint'.
     * 
     * @param excessBonusBasisPoint the value of field
     * 'excessBonusBasisPoint'.
     */
    public void setExcessBonusBasisPoint(java.math.BigDecimal excessBonusBasisPoint)
    {
        this._excessBonusBasisPoint = excessBonusBasisPoint;
        
        super.setVoChanged(true);
    } //-- void setExcessBonusBasisPoint(java.math.BigDecimal) 

    /**
     * Method setExcessPremiumLevelSets the value of field
     * 'excessPremiumLevel'.
     * 
     * @param excessPremiumLevel the value of field
     * 'excessPremiumLevel'.
     */
    public void setExcessPremiumLevel(java.math.BigDecimal excessPremiumLevel)
    {
        this._excessPremiumLevel = excessPremiumLevel;
        
        super.setVoChanged(true);
    } //-- void setExcessPremiumLevel(java.math.BigDecimal) 

    /**
     * Method setPremiumLevelFKSets the value of field
     * 'premiumLevelFK'.
     * 
     * @param premiumLevelFK the value of field 'premiumLevelFK'.
     */
    public void setPremiumLevelFK(long premiumLevelFK)
    {
        this._premiumLevelFK = premiumLevelFK;
        
        super.setVoChanged(true);
        this._has_premiumLevelFK = true;
    } //-- void setPremiumLevelFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusCriteriaVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusCriteriaVO) Unmarshaller.unmarshal(edit.common.vo.BonusCriteriaVO.class, reader);
    } //-- edit.common.vo.BonusCriteriaVO unmarshal(java.io.Reader) 

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
