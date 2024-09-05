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
 * Class FilteredRequirementVO.
 * 
 * @version $Revision$ $Date$
 */
public class FilteredRequirementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredRequirementPK
     */
    private long _filteredRequirementPK;

    /**
     * keeps track of state for field: _filteredRequirementPK
     */
    private boolean _has_filteredRequirementPK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _requirementFK
     */
    private long _requirementFK;

    /**
     * keeps track of state for field: _requirementFK
     */
    private boolean _has_requirementFK;

    /**
     * Field _contractRequirementVOList
     */
    private java.util.Vector _contractRequirementVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FilteredRequirementVO() {
        super();
        _contractRequirementVOList = new Vector();
    } //-- edit.common.vo.FilteredRequirementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addContractRequirementVO
     * 
     * @param vContractRequirementVO
     */
    public void addContractRequirementVO(edit.common.vo.ContractRequirementVO vContractRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractRequirementVO.setParentVO(this.getClass(), this);
        _contractRequirementVOList.addElement(vContractRequirementVO);
    } //-- void addContractRequirementVO(edit.common.vo.ContractRequirementVO) 

    /**
     * Method addContractRequirementVO
     * 
     * @param index
     * @param vContractRequirementVO
     */
    public void addContractRequirementVO(int index, edit.common.vo.ContractRequirementVO vContractRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContractRequirementVO.setParentVO(this.getClass(), this);
        _contractRequirementVOList.insertElementAt(vContractRequirementVO, index);
    } //-- void addContractRequirementVO(int, edit.common.vo.ContractRequirementVO) 

    /**
     * Method enumerateContractRequirementVO
     */
    public java.util.Enumeration enumerateContractRequirementVO()
    {
        return _contractRequirementVOList.elements();
    } //-- java.util.Enumeration enumerateContractRequirementVO() 

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
        
        if (obj instanceof FilteredRequirementVO) {
        
            FilteredRequirementVO temp = (FilteredRequirementVO)obj;
            if (this._filteredRequirementPK != temp._filteredRequirementPK)
                return false;
            if (this._has_filteredRequirementPK != temp._has_filteredRequirementPK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._requirementFK != temp._requirementFK)
                return false;
            if (this._has_requirementFK != temp._has_requirementFK)
                return false;
            if (this._contractRequirementVOList != null) {
                if (temp._contractRequirementVOList == null) return false;
                else if (!(this._contractRequirementVOList.equals(temp._contractRequirementVOList))) 
                    return false;
            }
            else if (temp._contractRequirementVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.ContractRequirementVO getContractRequirementVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContractRequirementVO) _contractRequirementVOList.elementAt(index);
    } //-- edit.common.vo.ContractRequirementVO getContractRequirementVO(int) 

    /**
     * Method getContractRequirementVO
     */
    public edit.common.vo.ContractRequirementVO[] getContractRequirementVO()
    {
        int size = _contractRequirementVOList.size();
        edit.common.vo.ContractRequirementVO[] mArray = new edit.common.vo.ContractRequirementVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContractRequirementVO) _contractRequirementVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContractRequirementVO[] getContractRequirementVO() 

    /**
     * Method getContractRequirementVOCount
     */
    public int getContractRequirementVOCount()
    {
        return _contractRequirementVOList.size();
    } //-- int getContractRequirementVOCount() 

    /**
     * Method getFilteredRequirementPKReturns the value of field
     * 'filteredRequirementPK'.
     * 
     * @return the value of field 'filteredRequirementPK'.
     */
    public long getFilteredRequirementPK()
    {
        return this._filteredRequirementPK;
    } //-- long getFilteredRequirementPK() 

    /**
     * Method getProductStructureFKReturns the value of field
     * 'productStructureFK'.
     * 
     * @return the value of field 'productStructureFK'.
     */
    public long getProductStructureFK()
    {
        return this._productStructureFK;
    } //-- long getProductStructureFK() 

    /**
     * Method getRequirementFKReturns the value of field
     * 'requirementFK'.
     * 
     * @return the value of field 'requirementFK'.
     */
    public long getRequirementFK()
    {
        return this._requirementFK;
    } //-- long getRequirementFK() 

    /**
     * Method hasFilteredRequirementPK
     */
    public boolean hasFilteredRequirementPK()
    {
        return this._has_filteredRequirementPK;
    } //-- boolean hasFilteredRequirementPK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

    /**
     * Method hasRequirementFK
     */
    public boolean hasRequirementFK()
    {
        return this._has_requirementFK;
    } //-- boolean hasRequirementFK() 

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
     * Method removeAllContractRequirementVO
     */
    public void removeAllContractRequirementVO()
    {
        _contractRequirementVOList.removeAllElements();
    } //-- void removeAllContractRequirementVO() 

    /**
     * Method removeContractRequirementVO
     * 
     * @param index
     */
    public edit.common.vo.ContractRequirementVO removeContractRequirementVO(int index)
    {
        java.lang.Object obj = _contractRequirementVOList.elementAt(index);
        _contractRequirementVOList.removeElementAt(index);
        return (edit.common.vo.ContractRequirementVO) obj;
    } //-- edit.common.vo.ContractRequirementVO removeContractRequirementVO(int) 

    /**
     * Method setContractRequirementVO
     * 
     * @param index
     * @param vContractRequirementVO
     */
    public void setContractRequirementVO(int index, edit.common.vo.ContractRequirementVO vContractRequirementVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contractRequirementVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContractRequirementVO.setParentVO(this.getClass(), this);
        _contractRequirementVOList.setElementAt(vContractRequirementVO, index);
    } //-- void setContractRequirementVO(int, edit.common.vo.ContractRequirementVO) 

    /**
     * Method setContractRequirementVO
     * 
     * @param contractRequirementVOArray
     */
    public void setContractRequirementVO(edit.common.vo.ContractRequirementVO[] contractRequirementVOArray)
    {
        //-- copy array
        _contractRequirementVOList.removeAllElements();
        for (int i = 0; i < contractRequirementVOArray.length; i++) {
            contractRequirementVOArray[i].setParentVO(this.getClass(), this);
            _contractRequirementVOList.addElement(contractRequirementVOArray[i]);
        }
    } //-- void setContractRequirementVO(edit.common.vo.ContractRequirementVO) 

    /**
     * Method setFilteredRequirementPKSets the value of field
     * 'filteredRequirementPK'.
     * 
     * @param filteredRequirementPK the value of field
     * 'filteredRequirementPK'.
     */
    public void setFilteredRequirementPK(long filteredRequirementPK)
    {
        this._filteredRequirementPK = filteredRequirementPK;
        
        super.setVoChanged(true);
        this._has_filteredRequirementPK = true;
    } //-- void setFilteredRequirementPK(long) 

    /**
     * Method setProductStructureFKSets the value of field
     * 'productStructureFK'.
     * 
     * @param productStructureFK the value of field
     * 'productStructureFK'.
     */
    public void setProductStructureFK(long productStructureFK)
    {
        this._productStructureFK = productStructureFK;
        
        super.setVoChanged(true);
        this._has_productStructureFK = true;
    } //-- void setProductStructureFK(long) 

    /**
     * Method setRequirementFKSets the value of field
     * 'requirementFK'.
     * 
     * @param requirementFK the value of field 'requirementFK'.
     */
    public void setRequirementFK(long requirementFK)
    {
        this._requirementFK = requirementFK;
        
        super.setVoChanged(true);
        this._has_requirementFK = true;
    } //-- void setRequirementFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.FilteredRequirementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.FilteredRequirementVO) Unmarshaller.unmarshal(edit.common.vo.FilteredRequirementVO.class, reader);
    } //-- edit.common.vo.FilteredRequirementVO unmarshal(java.io.Reader) 

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
