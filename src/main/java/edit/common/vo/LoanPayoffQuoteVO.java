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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class LoanPayoffQuoteVO.
 * 
 * @version $Revision$ $Date$
 */
public class LoanPayoffQuoteVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _loanPayoffQuotePK
     */
    private double _loanPayoffQuotePK;

    /**
     * keeps track of state for field: _loanPayoffQuotePK
     */
    private boolean _has_loanPayoffQuotePK;

    /**
     * Field _segmentVO
     */
    private edit.common.vo.SegmentVO _segmentVO;

    /**
     * Field _quoteDate
     */
    private java.lang.String _quoteDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public LoanPayoffQuoteVO() {
        super();
    } //-- edit.common.vo.LoanPayoffQuoteVO()


      //-----------/
     //- Methods -/
    //-----------/

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
        
        if (obj instanceof LoanPayoffQuoteVO) {
        
            LoanPayoffQuoteVO temp = (LoanPayoffQuoteVO)obj;
            if (this._loanPayoffQuotePK != temp._loanPayoffQuotePK)
                return false;
            if (this._has_loanPayoffQuotePK != temp._has_loanPayoffQuotePK)
                return false;
            if (this._segmentVO != null) {
                if (temp._segmentVO == null) return false;
                else if (!(this._segmentVO.equals(temp._segmentVO))) 
                    return false;
            }
            else if (temp._segmentVO != null)
                return false;
            if (this._quoteDate != null) {
                if (temp._quoteDate == null) return false;
                else if (!(this._quoteDate.equals(temp._quoteDate))) 
                    return false;
            }
            else if (temp._quoteDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getLoanPayoffQuotePKReturns the value of field
     * 'loanPayoffQuotePK'.
     * 
     * @return the value of field 'loanPayoffQuotePK'.
     */
    public double getLoanPayoffQuotePK()
    {
        return this._loanPayoffQuotePK;
    } //-- double getLoanPayoffQuotePK() 

    /**
     * Method getQuoteDateReturns the value of field 'quoteDate'.
     * 
     * @return the value of field 'quoteDate'.
     */
    public java.lang.String getQuoteDate()
    {
        return this._quoteDate;
    } //-- java.lang.String getQuoteDate() 

    /**
     * Method getSegmentVOReturns the value of field 'segmentVO'.
     * 
     * @return the value of field 'segmentVO'.
     */
    public edit.common.vo.SegmentVO getSegmentVO()
    {
        return this._segmentVO;
    } //-- edit.common.vo.SegmentVO getSegmentVO() 

    /**
     * Method hasLoanPayoffQuotePK
     */
    public boolean hasLoanPayoffQuotePK()
    {
        return this._has_loanPayoffQuotePK;
    } //-- boolean hasLoanPayoffQuotePK() 

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
     * Method setLoanPayoffQuotePKSets the value of field
     * 'loanPayoffQuotePK'.
     * 
     * @param loanPayoffQuotePK the value of field
     * 'loanPayoffQuotePK'.
     */
    public void setLoanPayoffQuotePK(double loanPayoffQuotePK)
    {
        this._loanPayoffQuotePK = loanPayoffQuotePK;
        
        super.setVoChanged(true);
        this._has_loanPayoffQuotePK = true;
    } //-- void setLoanPayoffQuotePK(double) 

    /**
     * Method setQuoteDateSets the value of field 'quoteDate'.
     * 
     * @param quoteDate the value of field 'quoteDate'.
     */
    public void setQuoteDate(java.lang.String quoteDate)
    {
        this._quoteDate = quoteDate;
        
        super.setVoChanged(true);
    } //-- void setQuoteDate(java.lang.String) 

    /**
     * Method setSegmentVOSets the value of field 'segmentVO'.
     * 
     * @param segmentVO the value of field 'segmentVO'.
     */
    public void setSegmentVO(edit.common.vo.SegmentVO segmentVO)
    {
        this._segmentVO = segmentVO;
    } //-- void setSegmentVO(edit.common.vo.SegmentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.LoanPayoffQuoteVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.LoanPayoffQuoteVO) Unmarshaller.unmarshal(edit.common.vo.LoanPayoffQuoteVO.class, reader);
    } //-- edit.common.vo.LoanPayoffQuoteVO unmarshal(java.io.Reader) 

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
