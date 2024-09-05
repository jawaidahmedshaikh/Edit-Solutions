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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class TamraRetestVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _tamraStartDate
     */
    private java.lang.String _tamraStartDate;

    /**
     * Field _accumulatedValue
     */
    private java.math.BigDecimal _accumulatedValue;

    /**
     * Comment describing your root element
     */
    private java.util.Vector _historyVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TamraRetestVO() {
        super();
        _historyVOList = new Vector();
    } //-- edit.common.vo.TamraRetestVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addHistoryVO
     * 
     * @param vHistoryVO
     */
    public void addHistoryVO(edit.common.vo.HistoryVO vHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vHistoryVO.setParentVO(this.getClass(), this);
        _historyVOList.addElement(vHistoryVO);
    } //-- void addHistoryVO(edit.common.vo.HistoryVO) 

    /**
     * Method addHistoryVO
     * 
     * @param index
     * @param vHistoryVO
     */
    public void addHistoryVO(int index, edit.common.vo.HistoryVO vHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vHistoryVO.setParentVO(this.getClass(), this);
        _historyVOList.insertElementAt(vHistoryVO, index);
    } //-- void addHistoryVO(int, edit.common.vo.HistoryVO) 

    /**
     * Method enumerateHistoryVO
     */
    public java.util.Enumeration enumerateHistoryVO()
    {
        return _historyVOList.elements();
    } //-- java.util.Enumeration enumerateHistoryVO() 

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
        
        if (obj instanceof TamraRetestVO) {
        
            TamraRetestVO temp = (TamraRetestVO)obj;
            if (this._tamraStartDate != null) {
                if (temp._tamraStartDate == null) return false;
                else if (!(this._tamraStartDate.equals(temp._tamraStartDate))) 
                    return false;
            }
            else if (temp._tamraStartDate != null)
                return false;
            if (this._accumulatedValue != null) {
                if (temp._accumulatedValue == null) return false;
                else if (!(this._accumulatedValue.equals(temp._accumulatedValue))) 
                    return false;
            }
            else if (temp._accumulatedValue != null)
                return false;
            if (this._historyVOList != null) {
                if (temp._historyVOList == null) return false;
                else if (!(this._historyVOList.equals(temp._historyVOList))) 
                    return false;
            }
            else if (temp._historyVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAccumulatedValueReturns the value of field
     * 'accumulatedValue'.
     * 
     * @return the value of field 'accumulatedValue'.
     */
    public java.math.BigDecimal getAccumulatedValue()
    {
        return this._accumulatedValue;
    } //-- java.math.BigDecimal getAccumulatedValue() 

    /**
     * Method getHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.HistoryVO getHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _historyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.HistoryVO) _historyVOList.elementAt(index);
    } //-- edit.common.vo.HistoryVO getHistoryVO(int) 

    /**
     * Method getHistoryVO
     */
    public edit.common.vo.HistoryVO[] getHistoryVO()
    {
        int size = _historyVOList.size();
        edit.common.vo.HistoryVO[] mArray = new edit.common.vo.HistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.HistoryVO) _historyVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.HistoryVO[] getHistoryVO() 

    /**
     * Method getHistoryVOCount
     */
    public int getHistoryVOCount()
    {
        return _historyVOList.size();
    } //-- int getHistoryVOCount() 

    /**
     * Method getTamraStartDateReturns the value of field
     * 'tamraStartDate'.
     * 
     * @return the value of field 'tamraStartDate'.
     */
    public java.lang.String getTamraStartDate()
    {
        return this._tamraStartDate;
    } //-- java.lang.String getTamraStartDate() 

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
     * Method removeAllHistoryVO
     */
    public void removeAllHistoryVO()
    {
        _historyVOList.removeAllElements();
    } //-- void removeAllHistoryVO() 

    /**
     * Method removeHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.HistoryVO removeHistoryVO(int index)
    {
        java.lang.Object obj = _historyVOList.elementAt(index);
        _historyVOList.removeElementAt(index);
        return (edit.common.vo.HistoryVO) obj;
    } //-- edit.common.vo.HistoryVO removeHistoryVO(int) 

    /**
     * Method setAccumulatedValueSets the value of field
     * 'accumulatedValue'.
     * 
     * @param accumulatedValue the value of field 'accumulatedValue'
     */
    public void setAccumulatedValue(java.math.BigDecimal accumulatedValue)
    {
        this._accumulatedValue = accumulatedValue;
        
        super.setVoChanged(true);
    } //-- void setAccumulatedValue(java.math.BigDecimal) 

    /**
     * Method setHistoryVO
     * 
     * @param index
     * @param vHistoryVO
     */
    public void setHistoryVO(int index, edit.common.vo.HistoryVO vHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _historyVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vHistoryVO.setParentVO(this.getClass(), this);
        _historyVOList.setElementAt(vHistoryVO, index);
    } //-- void setHistoryVO(int, edit.common.vo.HistoryVO) 

    /**
     * Method setHistoryVO
     * 
     * @param historyVOArray
     */
    public void setHistoryVO(edit.common.vo.HistoryVO[] historyVOArray)
    {
        //-- copy array
        _historyVOList.removeAllElements();
        for (int i = 0; i < historyVOArray.length; i++) {
            historyVOArray[i].setParentVO(this.getClass(), this);
            _historyVOList.addElement(historyVOArray[i]);
        }
    } //-- void setHistoryVO(edit.common.vo.HistoryVO) 

    /**
     * Method setTamraStartDateSets the value of field
     * 'tamraStartDate'.
     * 
     * @param tamraStartDate the value of field 'tamraStartDate'.
     */
    public void setTamraStartDate(java.lang.String tamraStartDate)
    {
        this._tamraStartDate = tamraStartDate;
        
        super.setVoChanged(true);
    } //-- void setTamraStartDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.TamraRetestVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.TamraRetestVO) Unmarshaller.unmarshal(edit.common.vo.TamraRetestVO.class, reader);
    } //-- edit.common.vo.TamraRetestVO unmarshal(java.io.Reader) 

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
