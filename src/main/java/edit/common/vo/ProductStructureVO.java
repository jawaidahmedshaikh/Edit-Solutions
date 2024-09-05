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
 * Class ProductStructureVO.
 * 
 * @version $Revision$ $Date$
 */
public class ProductStructureVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _productStructurePK
     */
    private long _productStructurePK;

    /**
     * keeps track of state for field: _productStructurePK
     */
    private boolean _has_productStructurePK;

    /**
     * Field _marketingPackageName
     */
    private java.lang.String _marketingPackageName;

    /**
     * Field _groupProductName
     */
    private java.lang.String _groupProductName;

    /**
     * Field _areaName
     */
    private java.lang.String _areaName;

    /**
     * Field _businessContractName
     */
    private java.lang.String _businessContractName;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _accountingClosePeriod
     */
    private java.lang.String _accountingClosePeriod;

    /**
     * Field _typeCodeCT
     */
    private java.lang.String _typeCodeCT;

    /**
     * Field _externalProductName
     */
    private java.lang.String _externalProductName;

    /**
     * Field _groupTypeCT
     */
    private java.lang.String _groupTypeCT;

    /**
     * Field _hedgeFundInterimAccountFK
     */
    private long _hedgeFundInterimAccountFK;

    /**
     * keeps track of state for field: _hedgeFundInterimAccountFK
     */
    private boolean _has_hedgeFundInterimAccountFK;

    /**
     * Field _companyFK
     */
    private long _companyFK;

    /**
     * keeps track of state for field: _companyFK
     */
    private boolean _has_companyFK;

    /**
     * Field _productTypeCT
     */
    private java.lang.String _productTypeCT;

    /**
     * Field _productFilteredFundStructureVOList
     */
    private java.util.Vector _productFilteredFundStructureVOList;

    /**
     * Field _productRuleStructureVOList
     */
    private java.util.Vector _productRuleStructureVOList;

    /**
     * Field _filteredAreaValueVOList
     */
    private java.util.Vector _filteredAreaValueVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProductStructureVO() {
        super();
        _productFilteredFundStructureVOList = new Vector();
        _productRuleStructureVOList = new Vector();
        _filteredAreaValueVOList = new Vector();
    } //-- edit.common.vo.ProductStructureVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addFilteredAreaValueVO
     * 
     * @param vFilteredAreaValueVO
     */
    public void addFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO vFilteredAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredAreaValueVO.setParentVO(this.getClass(), this);
        _filteredAreaValueVOList.addElement(vFilteredAreaValueVO);
    } //-- void addFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method addFilteredAreaValueVO
     * 
     * @param index
     * @param vFilteredAreaValueVO
     */
    public void addFilteredAreaValueVO(int index, edit.common.vo.FilteredAreaValueVO vFilteredAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vFilteredAreaValueVO.setParentVO(this.getClass(), this);
        _filteredAreaValueVOList.insertElementAt(vFilteredAreaValueVO, index);
    } //-- void addFilteredAreaValueVO(int, edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method addProductFilteredFundStructureVO
     * 
     * @param vProductFilteredFundStructureVO
     */
    public void addProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO vProductFilteredFundStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductFilteredFundStructureVO.setParentVO(this.getClass(), this);
        _productFilteredFundStructureVOList.addElement(vProductFilteredFundStructureVO);
    } //-- void addProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method addProductFilteredFundStructureVO
     * 
     * @param index
     * @param vProductFilteredFundStructureVO
     */
    public void addProductFilteredFundStructureVO(int index, edit.common.vo.ProductFilteredFundStructureVO vProductFilteredFundStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductFilteredFundStructureVO.setParentVO(this.getClass(), this);
        _productFilteredFundStructureVOList.insertElementAt(vProductFilteredFundStructureVO, index);
    } //-- void addProductFilteredFundStructureVO(int, edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method addProductRuleStructureVO
     * 
     * @param vProductRuleStructureVO
     */
    public void addProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO vProductRuleStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductRuleStructureVO.setParentVO(this.getClass(), this);
        _productRuleStructureVOList.addElement(vProductRuleStructureVO);
    } //-- void addProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method addProductRuleStructureVO
     * 
     * @param index
     * @param vProductRuleStructureVO
     */
    public void addProductRuleStructureVO(int index, edit.common.vo.ProductRuleStructureVO vProductRuleStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vProductRuleStructureVO.setParentVO(this.getClass(), this);
        _productRuleStructureVOList.insertElementAt(vProductRuleStructureVO, index);
    } //-- void addProductRuleStructureVO(int, edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method deleteHedgeFundInterimAccountFK
     */
    public void deleteHedgeFundInterimAccountFK()
    {
        this._has_hedgeFundInterimAccountFK= false;
    } //-- void deleteHedgeFundInterimAccountFK() 

    /**
     * Method enumerateFilteredAreaValueVO
     */
    public java.util.Enumeration enumerateFilteredAreaValueVO()
    {
        return _filteredAreaValueVOList.elements();
    } //-- java.util.Enumeration enumerateFilteredAreaValueVO() 

    /**
     * Method enumerateProductFilteredFundStructureVO
     */
    public java.util.Enumeration enumerateProductFilteredFundStructureVO()
    {
        return _productFilteredFundStructureVOList.elements();
    } //-- java.util.Enumeration enumerateProductFilteredFundStructureVO() 

    /**
     * Method enumerateProductRuleStructureVO
     */
    public java.util.Enumeration enumerateProductRuleStructureVO()
    {
        return _productRuleStructureVOList.elements();
    } //-- java.util.Enumeration enumerateProductRuleStructureVO() 

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
        
        if (obj instanceof ProductStructureVO) {
        
            ProductStructureVO temp = (ProductStructureVO)obj;
            if (this._productStructurePK != temp._productStructurePK)
                return false;
            if (this._has_productStructurePK != temp._has_productStructurePK)
                return false;
            if (this._marketingPackageName != null) {
                if (temp._marketingPackageName == null) return false;
                else if (!(this._marketingPackageName.equals(temp._marketingPackageName))) 
                    return false;
            }
            else if (temp._marketingPackageName != null)
                return false;
            if (this._groupProductName != null) {
                if (temp._groupProductName == null) return false;
                else if (!(this._groupProductName.equals(temp._groupProductName))) 
                    return false;
            }
            else if (temp._groupProductName != null)
                return false;
            if (this._areaName != null) {
                if (temp._areaName == null) return false;
                else if (!(this._areaName.equals(temp._areaName))) 
                    return false;
            }
            else if (temp._areaName != null)
                return false;
            if (this._businessContractName != null) {
                if (temp._businessContractName == null) return false;
                else if (!(this._businessContractName.equals(temp._businessContractName))) 
                    return false;
            }
            else if (temp._businessContractName != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._accountingClosePeriod != null) {
                if (temp._accountingClosePeriod == null) return false;
                else if (!(this._accountingClosePeriod.equals(temp._accountingClosePeriod))) 
                    return false;
            }
            else if (temp._accountingClosePeriod != null)
                return false;
            if (this._typeCodeCT != null) {
                if (temp._typeCodeCT == null) return false;
                else if (!(this._typeCodeCT.equals(temp._typeCodeCT))) 
                    return false;
            }
            else if (temp._typeCodeCT != null)
                return false;
            if (this._externalProductName != null) {
                if (temp._externalProductName == null) return false;
                else if (!(this._externalProductName.equals(temp._externalProductName))) 
                    return false;
            }
            else if (temp._externalProductName != null)
                return false;
            if (this._groupTypeCT != null) {
                if (temp._groupTypeCT == null) return false;
                else if (!(this._groupTypeCT.equals(temp._groupTypeCT))) 
                    return false;
            }
            else if (temp._groupTypeCT != null)
                return false;
            if (this._hedgeFundInterimAccountFK != temp._hedgeFundInterimAccountFK)
                return false;
            if (this._has_hedgeFundInterimAccountFK != temp._has_hedgeFundInterimAccountFK)
                return false;
            if (this._companyFK != temp._companyFK)
                return false;
            if (this._has_companyFK != temp._has_companyFK)
                return false;
            if (this._productTypeCT != null) {
                if (temp._productTypeCT == null) return false;
                else if (!(this._productTypeCT.equals(temp._productTypeCT))) 
                    return false;
            }
            else if (temp._productTypeCT != null)
                return false;
            if (this._productFilteredFundStructureVOList != null) {
                if (temp._productFilteredFundStructureVOList == null) return false;
                else if (!(this._productFilteredFundStructureVOList.equals(temp._productFilteredFundStructureVOList))) 
                    return false;
            }
            else if (temp._productFilteredFundStructureVOList != null)
                return false;
            if (this._productRuleStructureVOList != null) {
                if (temp._productRuleStructureVOList == null) return false;
                else if (!(this._productRuleStructureVOList.equals(temp._productRuleStructureVOList))) 
                    return false;
            }
            else if (temp._productRuleStructureVOList != null)
                return false;
            if (this._filteredAreaValueVOList != null) {
                if (temp._filteredAreaValueVOList == null) return false;
                else if (!(this._filteredAreaValueVOList.equals(temp._filteredAreaValueVOList))) 
                    return false;
            }
            else if (temp._filteredAreaValueVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccountingClosePeriodReturns the value of field
     * 'accountingClosePeriod'.
     * 
     * @return the value of field 'accountingClosePeriod'.
     */
    public java.lang.String getAccountingClosePeriod()
    {
        return this._accountingClosePeriod;
    } //-- java.lang.String getAccountingClosePeriod() 

    /**
     * Method getAreaNameReturns the value of field 'areaName'.
     * 
     * @return the value of field 'areaName'.
     */
    public java.lang.String getAreaName()
    {
        return this._areaName;
    } //-- java.lang.String getAreaName() 

    /**
     * Method getBusinessContractNameReturns the value of field
     * 'businessContractName'.
     * 
     * @return the value of field 'businessContractName'.
     */
    public java.lang.String getBusinessContractName()
    {
        return this._businessContractName;
    } //-- java.lang.String getBusinessContractName() 

    /**
     * Method getCompanyFKReturns the value of field 'companyFK'.
     * 
     * @return the value of field 'companyFK'.
     */
    public long getCompanyFK()
    {
        return this._companyFK;
    } //-- long getCompanyFK() 

    /**
     * Method getExternalProductNameReturns the value of field
     * 'externalProductName'.
     * 
     * @return the value of field 'externalProductName'.
     */
    public java.lang.String getExternalProductName()
    {
        return this._externalProductName;
    } //-- java.lang.String getExternalProductName() 

    /**
     * Method getFilteredAreaValueVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredAreaValueVO getFilteredAreaValueVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredAreaValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.FilteredAreaValueVO) _filteredAreaValueVOList.elementAt(index);
    } //-- edit.common.vo.FilteredAreaValueVO getFilteredAreaValueVO(int) 

    /**
     * Method getFilteredAreaValueVO
     */
    public edit.common.vo.FilteredAreaValueVO[] getFilteredAreaValueVO()
    {
        int size = _filteredAreaValueVOList.size();
        edit.common.vo.FilteredAreaValueVO[] mArray = new edit.common.vo.FilteredAreaValueVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.FilteredAreaValueVO) _filteredAreaValueVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.FilteredAreaValueVO[] getFilteredAreaValueVO() 

    /**
     * Method getFilteredAreaValueVOCount
     */
    public int getFilteredAreaValueVOCount()
    {
        return _filteredAreaValueVOList.size();
    } //-- int getFilteredAreaValueVOCount() 

    /**
     * Method getGroupProductNameReturns the value of field
     * 'groupProductName'.
     * 
     * @return the value of field 'groupProductName'.
     */
    public java.lang.String getGroupProductName()
    {
        return this._groupProductName;
    } //-- java.lang.String getGroupProductName() 

    /**
     * Method getGroupTypeCTReturns the value of field
     * 'groupTypeCT'.
     * 
     * @return the value of field 'groupTypeCT'.
     */
    public java.lang.String getGroupTypeCT()
    {
        return this._groupTypeCT;
    } //-- java.lang.String getGroupTypeCT() 

    /**
     * Method getHedgeFundInterimAccountFKReturns the value of
     * field 'hedgeFundInterimAccountFK'.
     * 
     * @return the value of field 'hedgeFundInterimAccountFK'.
     */
    public long getHedgeFundInterimAccountFK()
    {
        return this._hedgeFundInterimAccountFK;
    } //-- long getHedgeFundInterimAccountFK() 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getMarketingPackageNameReturns the value of field
     * 'marketingPackageName'.
     * 
     * @return the value of field 'marketingPackageName'.
     */
    public java.lang.String getMarketingPackageName()
    {
        return this._marketingPackageName;
    } //-- java.lang.String getMarketingPackageName() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getProductFilteredFundStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductFilteredFundStructureVO getProductFilteredFundStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productFilteredFundStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProductFilteredFundStructureVO) _productFilteredFundStructureVOList.elementAt(index);
    } //-- edit.common.vo.ProductFilteredFundStructureVO getProductFilteredFundStructureVO(int) 

    /**
     * Method getProductFilteredFundStructureVO
     */
    public edit.common.vo.ProductFilteredFundStructureVO[] getProductFilteredFundStructureVO()
    {
        int size = _productFilteredFundStructureVOList.size();
        edit.common.vo.ProductFilteredFundStructureVO[] mArray = new edit.common.vo.ProductFilteredFundStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProductFilteredFundStructureVO) _productFilteredFundStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProductFilteredFundStructureVO[] getProductFilteredFundStructureVO() 

    /**
     * Method getProductFilteredFundStructureVOCount
     */
    public int getProductFilteredFundStructureVOCount()
    {
        return _productFilteredFundStructureVOList.size();
    } //-- int getProductFilteredFundStructureVOCount() 

    /**
     * Method getProductRuleStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductRuleStructureVO getProductRuleStructureVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productRuleStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ProductRuleStructureVO) _productRuleStructureVOList.elementAt(index);
    } //-- edit.common.vo.ProductRuleStructureVO getProductRuleStructureVO(int) 

    /**
     * Method getProductRuleStructureVO
     */
    public edit.common.vo.ProductRuleStructureVO[] getProductRuleStructureVO()
    {
        int size = _productRuleStructureVOList.size();
        edit.common.vo.ProductRuleStructureVO[] mArray = new edit.common.vo.ProductRuleStructureVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ProductRuleStructureVO) _productRuleStructureVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ProductRuleStructureVO[] getProductRuleStructureVO() 

    /**
     * Method getProductRuleStructureVOCount
     */
    public int getProductRuleStructureVOCount()
    {
        return _productRuleStructureVOList.size();
    } //-- int getProductRuleStructureVOCount() 

    /**
     * Method getProductStructurePKReturns the value of field
     * 'productStructurePK'.
     * 
     * @return the value of field 'productStructurePK'.
     */
    public long getProductStructurePK()
    {
        return this._productStructurePK;
    } //-- long getProductStructurePK() 

    /**
     * Method getProductTypeCTReturns the value of field
     * 'productTypeCT'.
     * 
     * @return the value of field 'productTypeCT'.
     */
    public java.lang.String getProductTypeCT()
    {
        return this._productTypeCT;
    } //-- java.lang.String getProductTypeCT() 

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
     * Method hasCompanyFK
     */
    public boolean hasCompanyFK()
    {
        return this._has_companyFK;
    } //-- boolean hasCompanyFK() 

    /**
     * Method hasHedgeFundInterimAccountFK
     */
    public boolean hasHedgeFundInterimAccountFK()
    {
        return this._has_hedgeFundInterimAccountFK;
    } //-- boolean hasHedgeFundInterimAccountFK() 

    /**
     * Method hasProductStructurePK
     */
    public boolean hasProductStructurePK()
    {
        return this._has_productStructurePK;
    } //-- boolean hasProductStructurePK() 

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
     * Method removeAllFilteredAreaValueVO
     */
    public void removeAllFilteredAreaValueVO()
    {
        _filteredAreaValueVOList.removeAllElements();
    } //-- void removeAllFilteredAreaValueVO() 

    /**
     * Method removeAllProductFilteredFundStructureVO
     */
    public void removeAllProductFilteredFundStructureVO()
    {
        _productFilteredFundStructureVOList.removeAllElements();
    } //-- void removeAllProductFilteredFundStructureVO() 

    /**
     * Method removeAllProductRuleStructureVO
     */
    public void removeAllProductRuleStructureVO()
    {
        _productRuleStructureVOList.removeAllElements();
    } //-- void removeAllProductRuleStructureVO() 

    /**
     * Method removeFilteredAreaValueVO
     * 
     * @param index
     */
    public edit.common.vo.FilteredAreaValueVO removeFilteredAreaValueVO(int index)
    {
        java.lang.Object obj = _filteredAreaValueVOList.elementAt(index);
        _filteredAreaValueVOList.removeElementAt(index);
        return (edit.common.vo.FilteredAreaValueVO) obj;
    } //-- edit.common.vo.FilteredAreaValueVO removeFilteredAreaValueVO(int) 

    /**
     * Method removeProductFilteredFundStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductFilteredFundStructureVO removeProductFilteredFundStructureVO(int index)
    {
        java.lang.Object obj = _productFilteredFundStructureVOList.elementAt(index);
        _productFilteredFundStructureVOList.removeElementAt(index);
        return (edit.common.vo.ProductFilteredFundStructureVO) obj;
    } //-- edit.common.vo.ProductFilteredFundStructureVO removeProductFilteredFundStructureVO(int) 

    /**
     * Method removeProductRuleStructureVO
     * 
     * @param index
     */
    public edit.common.vo.ProductRuleStructureVO removeProductRuleStructureVO(int index)
    {
        java.lang.Object obj = _productRuleStructureVOList.elementAt(index);
        _productRuleStructureVOList.removeElementAt(index);
        return (edit.common.vo.ProductRuleStructureVO) obj;
    } //-- edit.common.vo.ProductRuleStructureVO removeProductRuleStructureVO(int) 

    /**
     * Method setAccountingClosePeriodSets the value of field
     * 'accountingClosePeriod'.
     * 
     * @param accountingClosePeriod the value of field
     * 'accountingClosePeriod'.
     */
    public void setAccountingClosePeriod(java.lang.String accountingClosePeriod)
    {
        this._accountingClosePeriod = accountingClosePeriod;
        
        super.setVoChanged(true);
    } //-- void setAccountingClosePeriod(java.lang.String) 

    /**
     * Method setAreaNameSets the value of field 'areaName'.
     * 
     * @param areaName the value of field 'areaName'.
     */
    public void setAreaName(java.lang.String areaName)
    {
        this._areaName = areaName;
        
        super.setVoChanged(true);
    } //-- void setAreaName(java.lang.String) 

    /**
     * Method setBusinessContractNameSets the value of field
     * 'businessContractName'.
     * 
     * @param businessContractName the value of field
     * 'businessContractName'.
     */
    public void setBusinessContractName(java.lang.String businessContractName)
    {
        this._businessContractName = businessContractName;
        
        super.setVoChanged(true);
    } //-- void setBusinessContractName(java.lang.String) 

    /**
     * Method setCompanyFKSets the value of field 'companyFK'.
     * 
     * @param companyFK the value of field 'companyFK'.
     */
    public void setCompanyFK(long companyFK)
    {
        this._companyFK = companyFK;
        
        super.setVoChanged(true);
        this._has_companyFK = true;
    } //-- void setCompanyFK(long) 

    /**
     * Method setExternalProductNameSets the value of field
     * 'externalProductName'.
     * 
     * @param externalProductName the value of field
     * 'externalProductName'.
     */
    public void setExternalProductName(java.lang.String externalProductName)
    {
        this._externalProductName = externalProductName;
        
        super.setVoChanged(true);
    } //-- void setExternalProductName(java.lang.String) 

    /**
     * Method setFilteredAreaValueVO
     * 
     * @param index
     * @param vFilteredAreaValueVO
     */
    public void setFilteredAreaValueVO(int index, edit.common.vo.FilteredAreaValueVO vFilteredAreaValueVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _filteredAreaValueVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vFilteredAreaValueVO.setParentVO(this.getClass(), this);
        _filteredAreaValueVOList.setElementAt(vFilteredAreaValueVO, index);
    } //-- void setFilteredAreaValueVO(int, edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method setFilteredAreaValueVO
     * 
     * @param filteredAreaValueVOArray
     */
    public void setFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO[] filteredAreaValueVOArray)
    {
        //-- copy array
        _filteredAreaValueVOList.removeAllElements();
        for (int i = 0; i < filteredAreaValueVOArray.length; i++) {
            filteredAreaValueVOArray[i].setParentVO(this.getClass(), this);
            _filteredAreaValueVOList.addElement(filteredAreaValueVOArray[i]);
        }
    } //-- void setFilteredAreaValueVO(edit.common.vo.FilteredAreaValueVO) 

    /**
     * Method setGroupProductNameSets the value of field
     * 'groupProductName'.
     * 
     * @param groupProductName the value of field 'groupProductName'
     */
    public void setGroupProductName(java.lang.String groupProductName)
    {
        this._groupProductName = groupProductName;
        
        super.setVoChanged(true);
    } //-- void setGroupProductName(java.lang.String) 

    /**
     * Method setGroupTypeCTSets the value of field 'groupTypeCT'.
     * 
     * @param groupTypeCT the value of field 'groupTypeCT'.
     */
    public void setGroupTypeCT(java.lang.String groupTypeCT)
    {
        this._groupTypeCT = groupTypeCT;
        
        super.setVoChanged(true);
    } //-- void setGroupTypeCT(java.lang.String) 

    /**
     * Method setHedgeFundInterimAccountFKSets the value of field
     * 'hedgeFundInterimAccountFK'.
     * 
     * @param hedgeFundInterimAccountFK the value of field
     * 'hedgeFundInterimAccountFK'.
     */
    public void setHedgeFundInterimAccountFK(long hedgeFundInterimAccountFK)
    {
        this._hedgeFundInterimAccountFK = hedgeFundInterimAccountFK;
        
        super.setVoChanged(true);
        this._has_hedgeFundInterimAccountFK = true;
    } //-- void setHedgeFundInterimAccountFK(long) 

    /**
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setMarketingPackageNameSets the value of field
     * 'marketingPackageName'.
     * 
     * @param marketingPackageName the value of field
     * 'marketingPackageName'.
     */
    public void setMarketingPackageName(java.lang.String marketingPackageName)
    {
        this._marketingPackageName = marketingPackageName;
        
        super.setVoChanged(true);
    } //-- void setMarketingPackageName(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setProductFilteredFundStructureVO
     * 
     * @param index
     * @param vProductFilteredFundStructureVO
     */
    public void setProductFilteredFundStructureVO(int index, edit.common.vo.ProductFilteredFundStructureVO vProductFilteredFundStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productFilteredFundStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProductFilteredFundStructureVO.setParentVO(this.getClass(), this);
        _productFilteredFundStructureVOList.setElementAt(vProductFilteredFundStructureVO, index);
    } //-- void setProductFilteredFundStructureVO(int, edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method setProductFilteredFundStructureVO
     * 
     * @param productFilteredFundStructureVOArray
     */
    public void setProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO[] productFilteredFundStructureVOArray)
    {
        //-- copy array
        _productFilteredFundStructureVOList.removeAllElements();
        for (int i = 0; i < productFilteredFundStructureVOArray.length; i++) {
            productFilteredFundStructureVOArray[i].setParentVO(this.getClass(), this);
            _productFilteredFundStructureVOList.addElement(productFilteredFundStructureVOArray[i]);
        }
    } //-- void setProductFilteredFundStructureVO(edit.common.vo.ProductFilteredFundStructureVO) 

    /**
     * Method setProductRuleStructureVO
     * 
     * @param index
     * @param vProductRuleStructureVO
     */
    public void setProductRuleStructureVO(int index, edit.common.vo.ProductRuleStructureVO vProductRuleStructureVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _productRuleStructureVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vProductRuleStructureVO.setParentVO(this.getClass(), this);
        _productRuleStructureVOList.setElementAt(vProductRuleStructureVO, index);
    } //-- void setProductRuleStructureVO(int, edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method setProductRuleStructureVO
     * 
     * @param productRuleStructureVOArray
     */
    public void setProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO[] productRuleStructureVOArray)
    {
        //-- copy array
        _productRuleStructureVOList.removeAllElements();
        for (int i = 0; i < productRuleStructureVOArray.length; i++) {
            productRuleStructureVOArray[i].setParentVO(this.getClass(), this);
            _productRuleStructureVOList.addElement(productRuleStructureVOArray[i]);
        }
    } //-- void setProductRuleStructureVO(edit.common.vo.ProductRuleStructureVO) 

    /**
     * Method setProductStructurePKSets the value of field
     * 'productStructurePK'.
     * 
     * @param productStructurePK the value of field
     * 'productStructurePK'.
     */
    public void setProductStructurePK(long productStructurePK)
    {
        this._productStructurePK = productStructurePK;
        
        super.setVoChanged(true);
        this._has_productStructurePK = true;
    } //-- void setProductStructurePK(long) 

    /**
     * Method setProductTypeCTSets the value of field
     * 'productTypeCT'.
     * 
     * @param productTypeCT the value of field 'productTypeCT'.
     */
    public void setProductTypeCT(java.lang.String productTypeCT)
    {
        this._productTypeCT = productTypeCT;
        
        super.setVoChanged(true);
    } //-- void setProductTypeCT(java.lang.String) 

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
    public static edit.common.vo.ProductStructureVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProductStructureVO) Unmarshaller.unmarshal(edit.common.vo.ProductStructureVO.class, reader);
    } //-- edit.common.vo.ProductStructureVO unmarshal(java.io.Reader) 

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
