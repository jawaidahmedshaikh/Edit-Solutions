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
 * Class InvestmentInformation.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentInformation extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _quoteInvestmentVO
     */
    private edit.common.vo.QuoteInvestmentVO _quoteInvestmentVO;

    /**
     * Field _fundName
     */
    private java.lang.String _fundName;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentInformation() {
        super();
    } //-- edit.common.vo.InvestmentInformation()


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
        
        if (obj instanceof InvestmentInformation) {
        
            InvestmentInformation temp = (InvestmentInformation)obj;
            if (this._quoteInvestmentVO != null) {
                if (temp._quoteInvestmentVO == null) return false;
                else if (!(this._quoteInvestmentVO.equals(temp._quoteInvestmentVO))) 
                    return false;
            }
            else if (temp._quoteInvestmentVO != null)
                return false;
            if (this._fundName != null) {
                if (temp._fundName == null) return false;
                else if (!(this._fundName.equals(temp._fundName))) 
                    return false;
            }
            else if (temp._fundName != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getFundNameReturns the value of field 'fundName'.
     * 
     * @return the value of field 'fundName'.
     */
    public java.lang.String getFundName()
    {
        return this._fundName;
    } //-- java.lang.String getFundName() 

    /**
     * Method getQuoteInvestmentVOReturns the value of field
     * 'quoteInvestmentVO'.
     * 
     * @return the value of field 'quoteInvestmentVO'.
     */
    public edit.common.vo.QuoteInvestmentVO getQuoteInvestmentVO()
    {
        return this._quoteInvestmentVO;
    } //-- edit.common.vo.QuoteInvestmentVO getQuoteInvestmentVO() 

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
     * Method setFundNameSets the value of field 'fundName'.
     * 
     * @param fundName the value of field 'fundName'.
     */
    public void setFundName(java.lang.String fundName)
    {
        this._fundName = fundName;
        
        super.setVoChanged(true);
    } //-- void setFundName(java.lang.String) 

    /**
     * Method setQuoteInvestmentVOSets the value of field
     * 'quoteInvestmentVO'.
     * 
     * @param quoteInvestmentVO the value of field
     * 'quoteInvestmentVO'.
     */
    public void setQuoteInvestmentVO(edit.common.vo.QuoteInvestmentVO quoteInvestmentVO)
    {
        this._quoteInvestmentVO = quoteInvestmentVO;
    } //-- void setQuoteInvestmentVO(edit.common.vo.QuoteInvestmentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentInformation unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentInformation) Unmarshaller.unmarshal(edit.common.vo.InvestmentInformation.class, reader);
    } //-- edit.common.vo.InvestmentInformation unmarshal(java.io.Reader) 

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
