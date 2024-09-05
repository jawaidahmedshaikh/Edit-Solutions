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
 * Class BonusCriteriaStatementVO.
 * 
 * @version $Revision$ $Date$
 */
public class BonusCriteriaStatementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _bonusAmount
     */
    private java.math.BigDecimal _bonusAmount;

    /**
     * Field _bonusBasisPoint
     */
    private java.math.BigDecimal _bonusBasisPoint;

    /**
     * Field _excessBonusAmount
     */
    private java.math.BigDecimal _excessBonusAmount;

    /**
     * Field _excessBonusBasisPoint
     */
    private java.math.BigDecimal _excessBonusBasisPoint;

    /**
     * Field _excessPremiumLevel
     */
    private java.math.BigDecimal _excessPremiumLevel;

    /**
     * Field _bonusStatementLineVOList
     */
    private java.util.Vector _bonusStatementLineVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public BonusCriteriaStatementVO() {
        super();
        _bonusStatementLineVOList = new Vector();
    } //-- edit.common.vo.BonusCriteriaStatementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusStatementLineVO
     * 
     * @param vBonusStatementLineVO
     */
    public void addBonusStatementLineVO(edit.common.vo.BonusStatementLineVO vBonusStatementLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusStatementLineVO.setParentVO(this.getClass(), this);
        _bonusStatementLineVOList.addElement(vBonusStatementLineVO);
    } //-- void addBonusStatementLineVO(edit.common.vo.BonusStatementLineVO) 

    /**
     * Method addBonusStatementLineVO
     * 
     * @param index
     * @param vBonusStatementLineVO
     */
    public void addBonusStatementLineVO(int index, edit.common.vo.BonusStatementLineVO vBonusStatementLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusStatementLineVO.setParentVO(this.getClass(), this);
        _bonusStatementLineVOList.insertElementAt(vBonusStatementLineVO, index);
    } //-- void addBonusStatementLineVO(int, edit.common.vo.BonusStatementLineVO) 

    /**
     * Method enumerateBonusStatementLineVO
     */
    public java.util.Enumeration enumerateBonusStatementLineVO()
    {
        return _bonusStatementLineVOList.elements();
    } //-- java.util.Enumeration enumerateBonusStatementLineVO() 

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
        
        if (obj instanceof BonusCriteriaStatementVO) {
        
            BonusCriteriaStatementVO temp = (BonusCriteriaStatementVO)obj;
            if (this._bonusAmount != null) {
                if (temp._bonusAmount == null) return false;
                else if (!(this._bonusAmount.equals(temp._bonusAmount))) 
                    return false;
            }
            else if (temp._bonusAmount != null)
                return false;
            if (this._bonusBasisPoint != null) {
                if (temp._bonusBasisPoint == null) return false;
                else if (!(this._bonusBasisPoint.equals(temp._bonusBasisPoint))) 
                    return false;
            }
            else if (temp._bonusBasisPoint != null)
                return false;
            if (this._excessBonusAmount != null) {
                if (temp._excessBonusAmount == null) return false;
                else if (!(this._excessBonusAmount.equals(temp._excessBonusAmount))) 
                    return false;
            }
            else if (temp._excessBonusAmount != null)
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
            if (this._bonusStatementLineVOList != null) {
                if (temp._bonusStatementLineVOList == null) return false;
                else if (!(this._bonusStatementLineVOList.equals(temp._bonusStatementLineVOList))) 
                    return false;
            }
            else if (temp._bonusStatementLineVOList != null)
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
     * Method getBonusStatementLineVO
     * 
     * @param index
     */
    public edit.common.vo.BonusStatementLineVO getBonusStatementLineVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusStatementLineVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusStatementLineVO) _bonusStatementLineVOList.elementAt(index);
    } //-- edit.common.vo.BonusStatementLineVO getBonusStatementLineVO(int) 

    /**
     * Method getBonusStatementLineVO
     */
    public edit.common.vo.BonusStatementLineVO[] getBonusStatementLineVO()
    {
        int size = _bonusStatementLineVOList.size();
        edit.common.vo.BonusStatementLineVO[] mArray = new edit.common.vo.BonusStatementLineVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusStatementLineVO) _bonusStatementLineVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusStatementLineVO[] getBonusStatementLineVO() 

    /**
     * Method getBonusStatementLineVOCount
     */
    public int getBonusStatementLineVOCount()
    {
        return _bonusStatementLineVOList.size();
    } //-- int getBonusStatementLineVOCount() 

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
     * Method removeAllBonusStatementLineVO
     */
    public void removeAllBonusStatementLineVO()
    {
        _bonusStatementLineVOList.removeAllElements();
    } //-- void removeAllBonusStatementLineVO() 

    /**
     * Method removeBonusStatementLineVO
     * 
     * @param index
     */
    public edit.common.vo.BonusStatementLineVO removeBonusStatementLineVO(int index)
    {
        java.lang.Object obj = _bonusStatementLineVOList.elementAt(index);
        _bonusStatementLineVOList.removeElementAt(index);
        return (edit.common.vo.BonusStatementLineVO) obj;
    } //-- edit.common.vo.BonusStatementLineVO removeBonusStatementLineVO(int) 

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
     * Method setBonusStatementLineVO
     * 
     * @param index
     * @param vBonusStatementLineVO
     */
    public void setBonusStatementLineVO(int index, edit.common.vo.BonusStatementLineVO vBonusStatementLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusStatementLineVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusStatementLineVO.setParentVO(this.getClass(), this);
        _bonusStatementLineVOList.setElementAt(vBonusStatementLineVO, index);
    } //-- void setBonusStatementLineVO(int, edit.common.vo.BonusStatementLineVO) 

    /**
     * Method setBonusStatementLineVO
     * 
     * @param bonusStatementLineVOArray
     */
    public void setBonusStatementLineVO(edit.common.vo.BonusStatementLineVO[] bonusStatementLineVOArray)
    {
        //-- copy array
        _bonusStatementLineVOList.removeAllElements();
        for (int i = 0; i < bonusStatementLineVOArray.length; i++) {
            bonusStatementLineVOArray[i].setParentVO(this.getClass(), this);
            _bonusStatementLineVOList.addElement(bonusStatementLineVOArray[i]);
        }
    } //-- void setBonusStatementLineVO(edit.common.vo.BonusStatementLineVO) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.BonusCriteriaStatementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.BonusCriteriaStatementVO) Unmarshaller.unmarshal(edit.common.vo.BonusCriteriaStatementVO.class, reader);
    } //-- edit.common.vo.BonusCriteriaStatementVO unmarshal(java.io.Reader) 

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
