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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class UIInvestmentOverrideVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _filteredFundFK
     */
    private long _filteredFundFK;

    /**
     * keeps track of state for field: _filteredFundFK
     */
    private boolean _has_filteredFundFK;

    /**
     * Field _investmentAllocationVOList
     */
    private java.util.Vector _investmentAllocationVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public UIInvestmentOverrideVO() {
        super();
        _investmentAllocationVOList = new Vector();
    } //-- edit.common.vo.UIInvestmentOverrideVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addInvestmentAllocationVO
     * 
     * @param vInvestmentAllocationVO
     */
    public void addInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO vInvestmentAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationVO.setParentVO(this.getClass(), this);
        _investmentAllocationVOList.addElement(vInvestmentAllocationVO);
    } //-- void addInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method addInvestmentAllocationVO
     * 
     * @param index
     * @param vInvestmentAllocationVO
     */
    public void addInvestmentAllocationVO(int index, edit.common.vo.InvestmentAllocationVO vInvestmentAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vInvestmentAllocationVO.setParentVO(this.getClass(), this);
        _investmentAllocationVOList.insertElementAt(vInvestmentAllocationVO, index);
    } //-- void addInvestmentAllocationVO(int, edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method enumerateInvestmentAllocationVO
     */
    public java.util.Enumeration enumerateInvestmentAllocationVO()
    {
        return _investmentAllocationVOList.elements();
    } //-- java.util.Enumeration enumerateInvestmentAllocationVO() 

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
        
        if (obj instanceof UIInvestmentOverrideVO) {
        
            UIInvestmentOverrideVO temp = (UIInvestmentOverrideVO)obj;
            if (this._filteredFundFK != temp._filteredFundFK)
                return false;
            if (this._has_filteredFundFK != temp._has_filteredFundFK)
                return false;
            if (this._investmentAllocationVOList != null) {
                if (temp._investmentAllocationVOList == null) return false;
                else if (!(this._investmentAllocationVOList.equals(temp._investmentAllocationVOList))) 
                    return false;
            }
            else if (temp._investmentAllocationVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getInvestmentAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationVO getInvestmentAllocationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.InvestmentAllocationVO) _investmentAllocationVOList.elementAt(index);
    } //-- edit.common.vo.InvestmentAllocationVO getInvestmentAllocationVO(int) 

    /**
     * Method getInvestmentAllocationVO
     */
    public edit.common.vo.InvestmentAllocationVO[] getInvestmentAllocationVO()
    {
        int size = _investmentAllocationVOList.size();
        edit.common.vo.InvestmentAllocationVO[] mArray = new edit.common.vo.InvestmentAllocationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.InvestmentAllocationVO) _investmentAllocationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.InvestmentAllocationVO[] getInvestmentAllocationVO() 

    /**
     * Method getInvestmentAllocationVOCount
     */
    public int getInvestmentAllocationVOCount()
    {
        return _investmentAllocationVOList.size();
    } //-- int getInvestmentAllocationVOCount() 

    /**
     * Method hasFilteredFundFK
     */
    public boolean hasFilteredFundFK()
    {
        return this._has_filteredFundFK;
    } //-- boolean hasFilteredFundFK() 

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
     * Method removeAllInvestmentAllocationVO
     */
    public void removeAllInvestmentAllocationVO()
    {
        _investmentAllocationVOList.removeAllElements();
    } //-- void removeAllInvestmentAllocationVO() 

    /**
     * Method removeInvestmentAllocationVO
     * 
     * @param index
     */
    public edit.common.vo.InvestmentAllocationVO removeInvestmentAllocationVO(int index)
    {
        java.lang.Object obj = _investmentAllocationVOList.elementAt(index);
        _investmentAllocationVOList.removeElementAt(index);
        return (edit.common.vo.InvestmentAllocationVO) obj;
    } //-- edit.common.vo.InvestmentAllocationVO removeInvestmentAllocationVO(int) 

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
     * Method setInvestmentAllocationVO
     * 
     * @param index
     * @param vInvestmentAllocationVO
     */
    public void setInvestmentAllocationVO(int index, edit.common.vo.InvestmentAllocationVO vInvestmentAllocationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _investmentAllocationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vInvestmentAllocationVO.setParentVO(this.getClass(), this);
        _investmentAllocationVOList.setElementAt(vInvestmentAllocationVO, index);
    } //-- void setInvestmentAllocationVO(int, edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method setInvestmentAllocationVO
     * 
     * @param investmentAllocationVOArray
     */
    public void setInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO[] investmentAllocationVOArray)
    {
        //-- copy array
        _investmentAllocationVOList.removeAllElements();
        for (int i = 0; i < investmentAllocationVOArray.length; i++) {
            investmentAllocationVOArray[i].setParentVO(this.getClass(), this);
            _investmentAllocationVOList.addElement(investmentAllocationVOArray[i]);
        }
    } //-- void setInvestmentAllocationVO(edit.common.vo.InvestmentAllocationVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UIInvestmentOverrideVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UIInvestmentOverrideVO) Unmarshaller.unmarshal(edit.common.vo.UIInvestmentOverrideVO.class, reader);
    } //-- edit.common.vo.UIInvestmentOverrideVO unmarshal(java.io.Reader) 

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
