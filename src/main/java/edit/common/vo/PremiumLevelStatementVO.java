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
 * Class PremiumLevelStatementVO.
 * 
 * @version $Revision$ $Date$
 */
public class PremiumLevelStatementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _issueLevelPremium
     */
    private java.math.BigDecimal _issueLevelPremium;

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
     * Field _bonusCriteriaStatementVOList
     */
    private java.util.Vector _bonusCriteriaStatementVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PremiumLevelStatementVO() {
        super();
        _bonusCriteriaStatementVOList = new Vector();
    } //-- edit.common.vo.PremiumLevelStatementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addBonusCriteriaStatementVO
     * 
     * @param vBonusCriteriaStatementVO
     */
    public void addBonusCriteriaStatementVO(edit.common.vo.BonusCriteriaStatementVO vBonusCriteriaStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCriteriaStatementVO.setParentVO(this.getClass(), this);
        _bonusCriteriaStatementVOList.addElement(vBonusCriteriaStatementVO);
    } //-- void addBonusCriteriaStatementVO(edit.common.vo.BonusCriteriaStatementVO) 

    /**
     * Method addBonusCriteriaStatementVO
     * 
     * @param index
     * @param vBonusCriteriaStatementVO
     */
    public void addBonusCriteriaStatementVO(int index, edit.common.vo.BonusCriteriaStatementVO vBonusCriteriaStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vBonusCriteriaStatementVO.setParentVO(this.getClass(), this);
        _bonusCriteriaStatementVOList.insertElementAt(vBonusCriteriaStatementVO, index);
    } //-- void addBonusCriteriaStatementVO(int, edit.common.vo.BonusCriteriaStatementVO) 

    /**
     * Method enumerateBonusCriteriaStatementVO
     */
    public java.util.Enumeration enumerateBonusCriteriaStatementVO()
    {
        return _bonusCriteriaStatementVOList.elements();
    } //-- java.util.Enumeration enumerateBonusCriteriaStatementVO() 

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
        
        if (obj instanceof PremiumLevelStatementVO) {
        
            PremiumLevelStatementVO temp = (PremiumLevelStatementVO)obj;
            if (this._issueLevelPremium != null) {
                if (temp._issueLevelPremium == null) return false;
                else if (!(this._issueLevelPremium.equals(temp._issueLevelPremium))) 
                    return false;
            }
            else if (temp._issueLevelPremium != null)
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
            if (this._bonusCriteriaStatementVOList != null) {
                if (temp._bonusCriteriaStatementVOList == null) return false;
                else if (!(this._bonusCriteriaStatementVOList.equals(temp._bonusCriteriaStatementVOList))) 
                    return false;
            }
            else if (temp._bonusCriteriaStatementVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBonusCriteriaStatementVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCriteriaStatementVO getBonusCriteriaStatementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCriteriaStatementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.BonusCriteriaStatementVO) _bonusCriteriaStatementVOList.elementAt(index);
    } //-- edit.common.vo.BonusCriteriaStatementVO getBonusCriteriaStatementVO(int) 

    /**
     * Method getBonusCriteriaStatementVO
     */
    public edit.common.vo.BonusCriteriaStatementVO[] getBonusCriteriaStatementVO()
    {
        int size = _bonusCriteriaStatementVOList.size();
        edit.common.vo.BonusCriteriaStatementVO[] mArray = new edit.common.vo.BonusCriteriaStatementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.BonusCriteriaStatementVO) _bonusCriteriaStatementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.BonusCriteriaStatementVO[] getBonusCriteriaStatementVO() 

    /**
     * Method getBonusCriteriaStatementVOCount
     */
    public int getBonusCriteriaStatementVOCount()
    {
        return _bonusCriteriaStatementVOList.size();
    } //-- int getBonusCriteriaStatementVOCount() 

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
     * Method getIssueLevelPremiumReturns the value of field
     * 'issueLevelPremium'.
     * 
     * @return the value of field 'issueLevelPremium'.
     */
    public java.math.BigDecimal getIssueLevelPremium()
    {
        return this._issueLevelPremium;
    } //-- java.math.BigDecimal getIssueLevelPremium() 

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
     * Method removeAllBonusCriteriaStatementVO
     */
    public void removeAllBonusCriteriaStatementVO()
    {
        _bonusCriteriaStatementVOList.removeAllElements();
    } //-- void removeAllBonusCriteriaStatementVO() 

    /**
     * Method removeBonusCriteriaStatementVO
     * 
     * @param index
     */
    public edit.common.vo.BonusCriteriaStatementVO removeBonusCriteriaStatementVO(int index)
    {
        java.lang.Object obj = _bonusCriteriaStatementVOList.elementAt(index);
        _bonusCriteriaStatementVOList.removeElementAt(index);
        return (edit.common.vo.BonusCriteriaStatementVO) obj;
    } //-- edit.common.vo.BonusCriteriaStatementVO removeBonusCriteriaStatementVO(int) 

    /**
     * Method setBonusCriteriaStatementVO
     * 
     * @param index
     * @param vBonusCriteriaStatementVO
     */
    public void setBonusCriteriaStatementVO(int index, edit.common.vo.BonusCriteriaStatementVO vBonusCriteriaStatementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _bonusCriteriaStatementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vBonusCriteriaStatementVO.setParentVO(this.getClass(), this);
        _bonusCriteriaStatementVOList.setElementAt(vBonusCriteriaStatementVO, index);
    } //-- void setBonusCriteriaStatementVO(int, edit.common.vo.BonusCriteriaStatementVO) 

    /**
     * Method setBonusCriteriaStatementVO
     * 
     * @param bonusCriteriaStatementVOArray
     */
    public void setBonusCriteriaStatementVO(edit.common.vo.BonusCriteriaStatementVO[] bonusCriteriaStatementVOArray)
    {
        //-- copy array
        _bonusCriteriaStatementVOList.removeAllElements();
        for (int i = 0; i < bonusCriteriaStatementVOArray.length; i++) {
            bonusCriteriaStatementVOArray[i].setParentVO(this.getClass(), this);
            _bonusCriteriaStatementVOList.addElement(bonusCriteriaStatementVOArray[i]);
        }
    } //-- void setBonusCriteriaStatementVO(edit.common.vo.BonusCriteriaStatementVO) 

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
     * Method setIssueLevelPremiumSets the value of field
     * 'issueLevelPremium'.
     * 
     * @param issueLevelPremium the value of field
     * 'issueLevelPremium'.
     */
    public void setIssueLevelPremium(java.math.BigDecimal issueLevelPremium)
    {
        this._issueLevelPremium = issueLevelPremium;
        
        super.setVoChanged(true);
    } //-- void setIssueLevelPremium(java.math.BigDecimal) 

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
    public static edit.common.vo.PremiumLevelStatementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PremiumLevelStatementVO) Unmarshaller.unmarshal(edit.common.vo.PremiumLevelStatementVO.class, reader);
    } //-- edit.common.vo.PremiumLevelStatementVO unmarshal(java.io.Reader) 

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
