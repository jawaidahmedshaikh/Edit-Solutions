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
 * Class ProductFilteredFundStructureVO.
 * 
 * @version $Revision$ $Date$
 */
public class ProductFilteredFundStructureVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _productFilteredFundStructurePK
     */
    private long _productFilteredFundStructurePK;

    /**
     * keeps track of state for field:
     * _productFilteredFundStructurePK
     */
    private boolean _has_productFilteredFundStructurePK;

    /**
     * Field _productStructureFK
     */
    private long _productStructureFK;

    /**
     * keeps track of state for field: _productStructureFK
     */
    private boolean _has_productStructureFK;

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _controlBalanceVOList
     */
    private java.util.Vector _controlBalanceVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ProductFilteredFundStructureVO() {
        super();
        _controlBalanceVOList = new Vector();
    } //-- edit.common.vo.ProductFilteredFundStructureVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addControlBalanceVO
     * 
     * @param vControlBalanceVO
     */
    public void addControlBalanceVO(edit.common.vo.ControlBalanceVO vControlBalanceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vControlBalanceVO.setParentVO(this.getClass(), this);
        _controlBalanceVOList.addElement(vControlBalanceVO);
    } //-- void addControlBalanceVO(edit.common.vo.ControlBalanceVO) 

    /**
     * Method addControlBalanceVO
     * 
     * @param index
     * @param vControlBalanceVO
     */
    public void addControlBalanceVO(int index, edit.common.vo.ControlBalanceVO vControlBalanceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vControlBalanceVO.setParentVO(this.getClass(), this);
        _controlBalanceVOList.insertElementAt(vControlBalanceVO, index);
    } //-- void addControlBalanceVO(int, edit.common.vo.ControlBalanceVO) 

    /**
     * Method enumerateControlBalanceVO
     */
    public java.util.Enumeration enumerateControlBalanceVO()
    {
        return _controlBalanceVOList.elements();
    } //-- java.util.Enumeration enumerateControlBalanceVO() 

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
        
        if (obj instanceof ProductFilteredFundStructureVO) {
        
            ProductFilteredFundStructureVO temp = (ProductFilteredFundStructureVO)obj;
            if (this._productFilteredFundStructurePK != temp._productFilteredFundStructurePK)
                return false;
            if (this._has_productFilteredFundStructurePK != temp._has_productFilteredFundStructurePK)
                return false;
            if (this._productStructureFK != temp._productStructureFK)
                return false;
            if (this._has_productStructureFK != temp._has_productStructureFK)
                return false;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._controlBalanceVOList != null) {
                if (temp._controlBalanceVOList == null) return false;
                else if (!(this._controlBalanceVOList.equals(temp._controlBalanceVOList))) 
                    return false;
            }
            else if (temp._controlBalanceVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getControlBalanceVO
     * 
     * @param index
     */
    public edit.common.vo.ControlBalanceVO getControlBalanceVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _controlBalanceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ControlBalanceVO) _controlBalanceVOList.elementAt(index);
    } //-- edit.common.vo.ControlBalanceVO getControlBalanceVO(int) 

    /**
     * Method getControlBalanceVO
     */
    public edit.common.vo.ControlBalanceVO[] getControlBalanceVO()
    {
        int size = _controlBalanceVOList.size();
        edit.common.vo.ControlBalanceVO[] mArray = new edit.common.vo.ControlBalanceVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ControlBalanceVO) _controlBalanceVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ControlBalanceVO[] getControlBalanceVO() 

    /**
     * Method getControlBalanceVOCount
     */
    public int getControlBalanceVOCount()
    {
        return _controlBalanceVOList.size();
    } //-- int getControlBalanceVOCount() 

    /**
     * Method getFilteredFundFKReturns the value of field
     * 'filteredFundFK'.
     * 
     * @return the value of field 'filteredFundFK'.
     */
    public long getFilteredFundFK()
    {
        return this._filteredFundFK;
    } //-- long getFilteredFundFK() 

    /**
     * Method getProductFilteredFundStructurePKReturns the value of
     * field 'productFilteredFundStructurePK'.
     * 
     * @return the value of field 'productFilteredFundStructurePK'.
     */
    public long getProductFilteredFundStructurePK()
    {
        return this._productFilteredFundStructurePK;
    } //-- long getProductFilteredFundStructurePK() 

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
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

    /**
     * Method hasProductFilteredFundStructurePK
     */
    public boolean hasProductFilteredFundStructurePK()
    {
        return this._has_productFilteredFundStructurePK;
    } //-- boolean hasProductFilteredFundStructurePK() 

    /**
     * Method hasProductStructureFK
     */
    public boolean hasProductStructureFK()
    {
        return this._has_productStructureFK;
    } //-- boolean hasProductStructureFK() 

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
     * Method removeAllControlBalanceVO
     */
    public void removeAllControlBalanceVO()
    {
        _controlBalanceVOList.removeAllElements();
    } //-- void removeAllControlBalanceVO() 

    /**
     * Method removeControlBalanceVO
     * 
     * @param index
     */
    public edit.common.vo.ControlBalanceVO removeControlBalanceVO(int index)
    {
        java.lang.Object obj = _controlBalanceVOList.elementAt(index);
        _controlBalanceVOList.removeElementAt(index);
        return (edit.common.vo.ControlBalanceVO) obj;
    } //-- edit.common.vo.ControlBalanceVO removeControlBalanceVO(int) 

    /**
     * Method setControlBalanceVO
     * 
     * @param index
     * @param vControlBalanceVO
     */
    public void setControlBalanceVO(int index, edit.common.vo.ControlBalanceVO vControlBalanceVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _controlBalanceVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vControlBalanceVO.setParentVO(this.getClass(), this);
        _controlBalanceVOList.setElementAt(vControlBalanceVO, index);
    } //-- void setControlBalanceVO(int, edit.common.vo.ControlBalanceVO) 

    /**
     * Method setControlBalanceVO
     * 
     * @param controlBalanceVOArray
     */
    public void setControlBalanceVO(edit.common.vo.ControlBalanceVO[] controlBalanceVOArray)
    {
        //-- copy array
        _controlBalanceVOList.removeAllElements();
        for (int i = 0; i < controlBalanceVOArray.length; i++) {
            controlBalanceVOArray[i].setParentVO(this.getClass(), this);
            _controlBalanceVOList.addElement(controlBalanceVOArray[i]);
        }
    } //-- void setControlBalanceVO(edit.common.vo.ControlBalanceVO) 

    /**
     * Method setFilteredFundFKSets the value of field
     * 'filteredFundFK'.
     * 
     * @param filteredFundFK the value of field 'filteredFundFK'.
     */
    public void setFilteredFundFK(long filteredFundFK)
    {
        this._filteredFundFK = filteredFundFK;
        
        super.setVoChanged(true);
        this._has_filteredFundFK = true;
    } //-- void setFilteredFundFK(long) 

    /**
     * Method setProductFilteredFundStructurePKSets the value of
     * field 'productFilteredFundStructurePK'.
     * 
     * @param productFilteredFundStructurePK the value of field
     * 'productFilteredFundStructurePK'.
     */
    public void setProductFilteredFundStructurePK(long productFilteredFundStructurePK)
    {
        this._productFilteredFundStructurePK = productFilteredFundStructurePK;
        
        super.setVoChanged(true);
        this._has_productFilteredFundStructurePK = true;
    } //-- void setProductFilteredFundStructurePK(long) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ProductFilteredFundStructureVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ProductFilteredFundStructureVO) Unmarshaller.unmarshal(edit.common.vo.ProductFilteredFundStructureVO.class, reader);
    } //-- edit.common.vo.ProductFilteredFundStructureVO unmarshal(java.io.Reader) 

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
