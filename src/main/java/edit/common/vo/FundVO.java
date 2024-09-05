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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class FundVO.
 * 
 * @version $Revision$ $Date$
 */
public class FundVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _fundPK
     */
    private long _fundPK;

    /**
     * keeps track of state for field: _fundPK
     */
    private boolean _has_fundPK;

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _fundType
     */
    private java.lang.String _fundType;

    /**
     * Field _portfolioNewMoneyStatusCT
     */
    private java.lang.String _portfolioNewMoneyStatusCT;

    /**
     * Field _shortName
     */
    private java.lang.String _shortName;

    /**
     * Field _excludeFromActivityFileInd
     */
    private java.lang.String _excludeFromActivityFileInd;

    /**
     * Field _typeCodeCT
     */
    private java.lang.String _typeCodeCT;

    /**
     * Field _reportingFundName
     */
    private java.lang.String _reportingFundName;

    /**
     * Field _loanQualifierCT
     */
    private java.lang.String _loanQualifierCT;

    /**
     * Field _filteredFundVOList
     */
    private java.util.Vector _filteredFundVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FundVO() {
        super();
        _filteredFundVOList = new Vector();
    } //-- edit.common.vo.FundVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredFundVO
     * 
     * @param vFilteredFundVO
     */
    public void addFilteredFundVO(edit.common.vo.FilteredFundVO vFilteredFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredFundVO.setParentVO(this.getClass(), this);
        _filteredFundVOList.addElement(vFilteredFundVO);
    } //-- void addFilteredFundVO(edit.common.vo.FilteredFundVO) 

    /**
     * Method addFilteredFundVO
     * 
     * @param index
     * @param vFilteredFundVO
     */
    public void addFilteredFundVO(int index, edit.common.vo.FilteredFundVO vFilteredFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredFundVO.setParentVO(this.getClass(), this);
        _filteredFundVOList.insertElementAt(vFilteredFundVO, index);
    } //-- void addFilteredFundVO(int, edit.common.vo.FilteredFundVO) 

    /**
     * Method enumerateFilteredFundVO
     */
    public java.util.Enumeration enumerateFilteredFundVO()
    {
        return _filteredFundVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredFundVO() 

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
        
        if (obj instanceof FundVO) {
        
            FundVO temp = (FundVO)obj;
            if (this._fundPK != temp._fundPK)
                return false;
            if (this._has_fundPK != temp._has_fundPK)
                return false;
            if (this._name != null) {
                if (temp._name == null) return false;
                else if (!(this._name.equals(temp._name))) 
                    return false;
            }
            else if (temp._name != null)
                return false;
            if (this._fundType != null) {
                if (temp._fundType == null) return false;
                else if (!(this._fundType.equals(temp._fundType))) 
                    return false;
            }
            else if (temp._fundType != null)
                return false;
            if (this._portfolioNewMoneyStatusCT != null) {
                if (temp._portfolioNewMoneyStatusCT == null) return false;
                else if (!(this._portfolioNewMoneyStatusCT.equals(temp._portfolioNewMoneyStatusCT))) 
                    return false;
            }
            else if (temp._portfolioNewMoneyStatusCT != null)
                return false;
            if (this._shortName != null) {
                if (temp._shortName == null) return false;
                else if (!(this._shortName.equals(temp._shortName))) 
                    return false;
            }
            else if (temp._shortName != null)
                return false;
            if (this._excludeFromActivityFileInd != null) {
                if (temp._excludeFromActivityFileInd == null) return false;
                else if (!(this._excludeFromActivityFileInd.equals(temp._excludeFromActivityFileInd))) 
                    return false;
            }
            else if (temp._excludeFromActivityFileInd != null)
                return false;
            if (this._typeCodeCT != null) {
                if (temp._typeCodeCT == null) return false;
                else if (!(this._typeCodeCT.equals(temp._typeCodeCT))) 
                    return false;
            }
            else if (temp._typeCodeCT != null)
                return false;
            if (this._reportingFundName != null) {
                if (temp._reportingFundName == null) return false;
                else if (!(this._reportingFundName.equals(temp._reportingFundName))) 
                    return false;
            }
            else if (temp._reportingFundName != null)
                return false;
            if (this._loanQualifierCT != null) {
                if (temp._loanQualifierCT == null) return false;
                else if (!(this._loanQualifierCT.equals(temp._loanQualifierCT))) 
                    return false;
            }
            else if (temp._loanQualifierCT != null)
                return false;
            if (this._filteredFundVOList != null) {
                if (temp._filteredFundVOList == null) return false;
                else if (!(this._filteredFundVOList.equals(temp._filteredFundVOList))) 
                    return false;
            }
            else if (temp._filteredFundVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getExcludeFromActivityFileIndReturns the value of
     * field 'excludeFromActivityFileInd'.
     * 
     * @return the value of field 'excludeFromActivityFileInd'.
     */
    public java.lang.String getExcludeFromActivityFileInd()
    {
        return this._excludeFromActivityFileInd;
    } //-- java.lang.String getExcludeFromActivityFileInd() 

    /**
     * Method getFilteredFundVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredFundVO getFilteredFundVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredFundVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredFundVO) _filteredFundVOList.elementAt(index);
    } //-- edit.common.vo.FilteredFundVO getFilteredFundVO(int) 

    /**
     * Method getFilteredFundVO
     */
    public edit.common.vo.FilteredFundVO[] getFilteredFundVO()
    {
        int size = _filteredFundVOList.size();
        edit.common.vo.FilteredFundVO[] mArray = new edit.common.vo.FilteredFundVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredFundVO) _filteredFundVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredFundVO[] getFilteredFundVO() 

    /**
     * Method getFilteredFundVOCount
     */
    public int getFilteredFundVOCount()
    {
        return _filteredFundVOList.size();
    } //-- int getFilteredFundVOCount() 

    /**
     * Method getFundPKReturns the value of field 'fundPK'.
     * 
     * @return the value of field 'fundPK'.
     */
    public long getFundPK()
    {
        return this._fundPK;
    } //-- long getFundPK() 

    /**
     * Method getFundTypeReturns the value of field 'fundType'.
     * 
     * @return the value of field 'fundType'.
     */
    public java.lang.String getFundType()
    {
        return this._fundType;
    } //-- java.lang.String getFundType() 

    /**
     * Method getLoanQualifierCTReturns the value of field
     * 'loanQualifierCT'.
     * 
     * @return the value of field 'loanQualifierCT'.
     */
    public java.lang.String getLoanQualifierCT()
    {
        return this._loanQualifierCT;
    } //-- java.lang.String getLoanQualifierCT() 

    /**
     * Method getNameReturns the value of field 'name'.
     * 
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName() 

    /**
     * Method getPortfolioNewMoneyStatusCTReturns the value of
     * field 'portfolioNewMoneyStatusCT'.
     * 
     * @return the value of field 'portfolioNewMoneyStatusCT'.
     */
    public java.lang.String getPortfolioNewMoneyStatusCT()
    {
        return this._portfolioNewMoneyStatusCT;
    } //-- java.lang.String getPortfolioNewMoneyStatusCT() 

    /**
     * Method getReportingFundNameReturns the value of field
     * 'reportingFundName'.
     * 
     * @return the value of field 'reportingFundName'.
     */
    public java.lang.String getReportingFundName()
    {
        return this._reportingFundName;
    } //-- java.lang.String getReportingFundName() 

    /**
     * Method getShortNameReturns the value of field 'shortName'.
     * 
     * @return the value of field 'shortName'.
     */
    public java.lang.String getShortName()
    {
        return this._shortName;
    } //-- java.lang.String getShortName() 

    /**
     * Method getTypeCodeCTReturns the value of field 'typeCodeCT'.
     * 
     * @return the value of field 'typeCodeCT'.
     */
    public java.lang.String getTypeCodeCT()
    {
        return this._typeCodeCT;
    } //-- java.lang.String getTypeCodeCT() 

    /**
     * Method hasFundPK
     */
    public boolean hasFundPK()
    {
        return this._has_fundPK;
    } //-- boolean hasFundPK() 

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
     * Method removeAllFilteredFundVO
     */
    public void removeAllFilteredFundVO()
    {
        _filteredFundVOList.removeAllElements();
    } //-- void removeAllFilteredFundVO() 

    /**
     * Method removeFilteredFundVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredFundVO removeFilteredFundVO(int index)
    {
        java.lang.Object obj = _filteredFundVOList.elementAt(index);
        _filteredFundVOList.removeElementAt(index);
        return (edit.common.vo.FilteredFundVO) obj;
    } //-- edit.common.vo.FilteredFundVO removeFilteredFundVO(int) 

    /**
     * Method setExcludeFromActivityFileIndSets the value of field
     * 'excludeFromActivityFileInd'.
     * 
     * @param excludeFromActivityFileInd the value of field
     * 'excludeFromActivityFileInd'.
     */
    public void setExcludeFromActivityFileInd(java.lang.String excludeFromActivityFileInd)
    {
        this._excludeFromActivityFileInd = excludeFromActivityFileInd;
        
        super.setVoChanged(true);
    } //-- void setExcludeFromActivityFileInd(java.lang.String) 

    /**
     * Method setFilteredFundVO
     * 
     * @param index
     * @param vFilteredFundVO
     */
    public void setFilteredFundVO(int index, edit.common.vo.FilteredFundVO vFilteredFundVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredFundVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredFundVO.setParentVO(this.getClass(), this);
        _filteredFundVOList.setElementAt(vFilteredFundVO, index);
    } //-- void setFilteredFundVO(int, edit.common.vo.FilteredFundVO) 

    /**
     * Method setFilteredFundVO
     * 
     * @param filteredFundVOArray
     */
    public void setFilteredFundVO(edit.common.vo.FilteredFundVO[] filteredFundVOArray)
    {
        //-- copy array
        _filteredFundVOList.removeAllElements();
        for (int i = 0; i < filteredFundVOArray.length; i++) {
            filteredFundVOArray[i].setParentVO(this.getClass(), this);
            _filteredFundVOList.addElement(filteredFundVOArray[i]);
        }
    } //-- void setFilteredFundVO(edit.common.vo.FilteredFundVO) 

    /**
     * Method setFundPKSets the value of field 'fundPK'.
     * 
     * @param fundPK the value of field 'fundPK'.
     */
    public void setFundPK(long fundPK)
    {
        this._fundPK = fundPK;
        
        super.setVoChanged(true);
        this._has_fundPK = true;
    } //-- void setFundPK(long) 

    /**
     * Method setFundTypeSets the value of field 'fundType'.
     * 
     * @param fundType the value of field 'fundType'.
     */
    public void setFundType(java.lang.String fundType)
    {
        this._fundType = fundType;
        
        super.setVoChanged(true);
    } //-- void setFundType(java.lang.String) 

    /**
     * Method setLoanQualifierCTSets the value of field
     * 'loanQualifierCT'.
     * 
     * @param loanQualifierCT the value of field 'loanQualifierCT'.
     */
    public void setLoanQualifierCT(java.lang.String loanQualifierCT)
    {
        this._loanQualifierCT = loanQualifierCT;
        
        super.setVoChanged(true);
    } //-- void setLoanQualifierCT(java.lang.String) 

    /**
     * Method setNameSets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
        
        super.setVoChanged(true);
    } //-- void setName(java.lang.String) 

    /**
     * Method setPortfolioNewMoneyStatusCTSets the value of field
     * 'portfolioNewMoneyStatusCT'.
     * 
     * @param portfolioNewMoneyStatusCT the value of field
     * 'portfolioNewMoneyStatusCT'.
     */
    public void setPortfolioNewMoneyStatusCT(java.lang.String portfolioNewMoneyStatusCT)
    {
        this._portfolioNewMoneyStatusCT = portfolioNewMoneyStatusCT;
        
        super.setVoChanged(true);
    } //-- void setPortfolioNewMoneyStatusCT(java.lang.String) 

    /**
     * Method setReportingFundNameSets the value of field
     * 'reportingFundName'.
     * 
     * @param reportingFundName the value of field
     * 'reportingFundName'.
     */
    public void setReportingFundName(java.lang.String reportingFundName)
    {
        this._reportingFundName = reportingFundName;
        
        super.setVoChanged(true);
    } //-- void setReportingFundName(java.lang.String) 

    /**
     * Method setShortNameSets the value of field 'shortName'.
     * 
     * @param shortName the value of field 'shortName'.
     */
    public void setShortName(java.lang.String shortName)
    {
        this._shortName = shortName;
        
        super.setVoChanged(true);
    } //-- void setShortName(java.lang.String) 

    /**
     * Method setTypeCodeCTSets the value of field 'typeCodeCT'.
     * 
     * @param typeCodeCT the value of field 'typeCodeCT'.
     */
    public void setTypeCodeCT(java.lang.String typeCodeCT)
    {
        this._typeCodeCT = typeCodeCT;
        
        super.setVoChanged(true);
    } //-- void setTypeCodeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FundVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FundVO) Unmarshaller.unmarshal(edit.common.vo.FundVO.class, reader);
    } //-- edit.common.vo.FundVO unmarshal(java.io.Reader) 

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
