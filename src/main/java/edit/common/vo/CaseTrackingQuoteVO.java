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
 * Quote processor
 * 
 * @version $Revision$ $Date$
 */
public class CaseTrackingQuoteVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _segmentVOList
     */
    private java.util.Vector _segmentVOList;

    /**
     * Field _clientDetailVOList
     */
    private java.util.Vector _clientDetailVOList;

    /**
     * Field _beneAllocationPct
     */
    private java.math.BigDecimal _beneAllocationPct;

    /**
     * Field _disbursementDate
     */
    private java.lang.String _disbursementDate;

    /**
     * Field _deathValue
     */
    private java.math.BigDecimal _deathValue;

    /**
     * Field _interest
     */
    private java.math.BigDecimal _interest;


      //----------------/
     //- Constructors -/
    //----------------/

    public CaseTrackingQuoteVO() {
        super();
        _segmentVOList = new Vector();
        _clientDetailVOList = new Vector();
    } //-- edit.common.vo.CaseTrackingQuoteVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addClientDetailVO
     * 
     * @param vClientDetailVO
     */
    public void addClientDetailVO(edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.addElement(vClientDetailVO);
    } //-- void addClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method addClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void addClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.insertElementAt(vClientDetailVO, index);
    } //-- void addClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method addSegmentVO
     * 
     * @param vSegmentVO
     */
    public void addSegmentVO(edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.addElement(vSegmentVO);
    } //-- void addSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method addSegmentVO
     * 
     * @param index
     * @param vSegmentVO
     */
    public void addSegmentVO(int index, edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.insertElementAt(vSegmentVO, index);
    } //-- void addSegmentVO(int, edit.common.vo.SegmentVO) 

    /**
     * Method enumerateClientDetailVO
     */
    public java.util.Enumeration enumerateClientDetailVO()
    {
        return _clientDetailVOList.elements();
    } //-- java.util.Enumeration enumerateClientDetailVO() 

    /**
     * Method enumerateSegmentVO
     */
    public java.util.Enumeration enumerateSegmentVO()
    {
        return _segmentVOList.elements();
    } //-- java.util.Enumeration enumerateSegmentVO() 

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
        
        if (obj instanceof CaseTrackingQuoteVO) {
        
            CaseTrackingQuoteVO temp = (CaseTrackingQuoteVO)obj;
            if (this._segmentVOList != null) {
                if (temp._segmentVOList == null) return false;
                else if (!(this._segmentVOList.equals(temp._segmentVOList))) 
                    return false;
            }
            else if (temp._segmentVOList != null)
                return false;
            if (this._clientDetailVOList != null) {
                if (temp._clientDetailVOList == null) return false;
                else if (!(this._clientDetailVOList.equals(temp._clientDetailVOList))) 
                    return false;
            }
            else if (temp._clientDetailVOList != null)
                return false;
            if (this._beneAllocationPct != null) {
                if (temp._beneAllocationPct == null) return false;
                else if (!(this._beneAllocationPct.equals(temp._beneAllocationPct))) 
                    return false;
            }
            else if (temp._beneAllocationPct != null)
                return false;
            if (this._disbursementDate != null) {
                if (temp._disbursementDate == null) return false;
                else if (!(this._disbursementDate.equals(temp._disbursementDate))) 
                    return false;
            }
            else if (temp._disbursementDate != null)
                return false;
            if (this._deathValue != null) {
                if (temp._deathValue == null) return false;
                else if (!(this._deathValue.equals(temp._deathValue))) 
                    return false;
            }
            else if (temp._deathValue != null)
                return false;
            if (this._interest != null) {
                if (temp._interest == null) return false;
                else if (!(this._interest.equals(temp._interest))) 
                    return false;
            }
            else if (temp._interest != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBeneAllocationPctReturns the value of field
     * 'beneAllocationPct'.
     * 
     * @return the value of field 'beneAllocationPct'.
     */
    public java.math.BigDecimal getBeneAllocationPct()
    {
        return this._beneAllocationPct;
    } //-- java.math.BigDecimal getBeneAllocationPct() 

    /**
     * Method getClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO getClientDetailVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
    } //-- edit.common.vo.ClientDetailVO getClientDetailVO(int) 

    /**
     * Method getClientDetailVO
     */
    public edit.common.vo.ClientDetailVO[] getClientDetailVO()
    {
        int size = _clientDetailVOList.size();
        edit.common.vo.ClientDetailVO[] mArray = new edit.common.vo.ClientDetailVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ClientDetailVO) _clientDetailVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ClientDetailVO[] getClientDetailVO() 

    /**
     * Method getClientDetailVOCount
     */
    public int getClientDetailVOCount()
    {
        return _clientDetailVOList.size();
    } //-- int getClientDetailVOCount() 

    /**
     * Method getDeathValueReturns the value of field 'deathValue'.
     * 
     * @return the value of field 'deathValue'.
     */
    public java.math.BigDecimal getDeathValue()
    {
        return this._deathValue;
    } //-- java.math.BigDecimal getDeathValue() 

    /**
     * Method getDisbursementDateReturns the value of field
     * 'disbursementDate'.
     * 
     * @return the value of field 'disbursementDate'.
     */
    public java.lang.String getDisbursementDate()
    {
        return this._disbursementDate;
    } //-- java.lang.String getDisbursementDate() 

    /**
     * Method getInterestReturns the value of field 'interest'.
     * 
     * @return the value of field 'interest'.
     */
    public java.math.BigDecimal getInterest()
    {
        return this._interest;
    } //-- java.math.BigDecimal getInterest() 

    /**
     * Method getSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentVO getSegmentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SegmentVO) _segmentVOList.elementAt(index);
    } //-- edit.common.vo.SegmentVO getSegmentVO(int) 

    /**
     * Method getSegmentVO
     */
    public edit.common.vo.SegmentVO[] getSegmentVO()
    {
        int size = _segmentVOList.size();
        edit.common.vo.SegmentVO[] mArray = new edit.common.vo.SegmentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SegmentVO) _segmentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SegmentVO[] getSegmentVO() 

    /**
     * Method getSegmentVOCount
     */
    public int getSegmentVOCount()
    {
        return _segmentVOList.size();
    } //-- int getSegmentVOCount() 

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
     * Method removeAllClientDetailVO
     */
    public void removeAllClientDetailVO()
    {
        _clientDetailVOList.removeAllElements();
    } //-- void removeAllClientDetailVO() 

    /**
     * Method removeAllSegmentVO
     */
    public void removeAllSegmentVO()
    {
        _segmentVOList.removeAllElements();
    } //-- void removeAllSegmentVO() 

    /**
     * Method removeClientDetailVO
     * 
     * @param index
     */
    public edit.common.vo.ClientDetailVO removeClientDetailVO(int index)
    {
        java.lang.Object obj = _clientDetailVOList.elementAt(index);
        _clientDetailVOList.removeElementAt(index);
        return (edit.common.vo.ClientDetailVO) obj;
    } //-- edit.common.vo.ClientDetailVO removeClientDetailVO(int) 

    /**
     * Method removeSegmentVO
     * 
     * @param index
     */
    public edit.common.vo.SegmentVO removeSegmentVO(int index)
    {
        java.lang.Object obj = _segmentVOList.elementAt(index);
        _segmentVOList.removeElementAt(index);
        return (edit.common.vo.SegmentVO) obj;
    } //-- edit.common.vo.SegmentVO removeSegmentVO(int) 

    /**
     * Method setBeneAllocationPctSets the value of field
     * 'beneAllocationPct'.
     * 
     * @param beneAllocationPct the value of field
     * 'beneAllocationPct'.
     */
    public void setBeneAllocationPct(java.math.BigDecimal beneAllocationPct)
    {
        this._beneAllocationPct = beneAllocationPct;
        
        super.setVoChanged(true);
    } //-- void setBeneAllocationPct(java.math.BigDecimal) 

    /**
     * Method setClientDetailVO
     * 
     * @param index
     * @param vClientDetailVO
     */
    public void setClientDetailVO(int index, edit.common.vo.ClientDetailVO vClientDetailVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _clientDetailVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vClientDetailVO.setParentVO(this.getClass(), this);
        _clientDetailVOList.setElementAt(vClientDetailVO, index);
    } //-- void setClientDetailVO(int, edit.common.vo.ClientDetailVO) 

    /**
     * Method setClientDetailVO
     * 
     * @param clientDetailVOArray
     */
    public void setClientDetailVO(edit.common.vo.ClientDetailVO[] clientDetailVOArray)
    {
        //-- copy array
        _clientDetailVOList.removeAllElements();
        for (int i = 0; i < clientDetailVOArray.length; i++) {
            clientDetailVOArray[i].setParentVO(this.getClass(), this);
            _clientDetailVOList.addElement(clientDetailVOArray[i]);
        }
    } //-- void setClientDetailVO(edit.common.vo.ClientDetailVO) 

    /**
     * Method setDeathValueSets the value of field 'deathValue'.
     * 
     * @param deathValue the value of field 'deathValue'.
     */
    public void setDeathValue(java.math.BigDecimal deathValue)
    {
        this._deathValue = deathValue;
        
        super.setVoChanged(true);
    } //-- void setDeathValue(java.math.BigDecimal) 

    /**
     * Method setDisbursementDateSets the value of field
     * 'disbursementDate'.
     * 
     * @param disbursementDate the value of field 'disbursementDate'
     */
    public void setDisbursementDate(java.lang.String disbursementDate)
    {
        this._disbursementDate = disbursementDate;
        
        super.setVoChanged(true);
    } //-- void setDisbursementDate(java.lang.String) 

    /**
     * Method setInterestSets the value of field 'interest'.
     * 
     * @param interest the value of field 'interest'.
     */
    public void setInterest(java.math.BigDecimal interest)
    {
        this._interest = interest;
        
        super.setVoChanged(true);
    } //-- void setInterest(java.math.BigDecimal) 

    /**
     * Method setSegmentVO
     * 
     * @param index
     * @param vSegmentVO
     */
    public void setSegmentVO(int index, edit.common.vo.SegmentVO vSegmentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _segmentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vSegmentVO.setParentVO(this.getClass(), this);
        _segmentVOList.setElementAt(vSegmentVO, index);
    } //-- void setSegmentVO(int, edit.common.vo.SegmentVO) 

    /**
     * Method setSegmentVO
     * 
     * @param segmentVOArray
     */
    public void setSegmentVO(edit.common.vo.SegmentVO[] segmentVOArray)
    {
        //-- copy array
        _segmentVOList.removeAllElements();
        for (int i = 0; i < segmentVOArray.length; i++) {
            segmentVOArray[i].setParentVO(this.getClass(), this);
            _segmentVOList.addElement(segmentVOArray[i]);
        }
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CaseTrackingQuoteVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CaseTrackingQuoteVO) Unmarshaller.unmarshal(edit.common.vo.CaseTrackingQuoteVO.class, reader);
    } //-- edit.common.vo.CaseTrackingQuoteVO unmarshal(java.io.Reader) 

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
