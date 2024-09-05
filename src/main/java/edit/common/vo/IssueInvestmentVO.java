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
 * The Investments at issue for a given contract.
 * 
 * @version $Revision$ $Date$
 */
public class IssueInvestmentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentVO
     */
    private edit.common.vo.InvestmentVO _investmentVO;

    /**
     * The rates retrieved from PRASE for a given investment.
     */
    private edit.common.vo.RatesVO _ratesVO;


      //----------------/
     //- Constructors -/
    //----------------/

    public IssueInvestmentVO() {
        super();
    } //-- edit.common.vo.IssueInvestmentVO()


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
        
        if (obj instanceof IssueInvestmentVO) {
        
            IssueInvestmentVO temp = (IssueInvestmentVO)obj;
            if (this._investmentVO != null) {
                if (temp._investmentVO == null) return false;
                else if (!(this._investmentVO.equals(temp._investmentVO))) 
                    return false;
            }
            else if (temp._investmentVO != null)
                return false;
            if (this._ratesVO != null) {
                if (temp._ratesVO == null) return false;
                else if (!(this._ratesVO.equals(temp._ratesVO))) 
                    return false;
            }
            else if (temp._ratesVO != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getInvestmentVOReturns the value of field
     * 'investmentVO'.
     * 
     * @return the value of field 'investmentVO'.
     */
    public edit.common.vo.InvestmentVO getInvestmentVO()
    {
        return this._investmentVO;
    } //-- edit.common.vo.InvestmentVO getInvestmentVO() 

    /**
     * Method getRatesVOReturns the value of field 'ratesVO'. The
     * field 'ratesVO' has the following description: The rates
     * retrieved from PRASE for a given investment.
     * 
     * @return the value of field 'ratesVO'.
     */
    public edit.common.vo.RatesVO getRatesVO()
    {
        return this._ratesVO;
    } //-- edit.common.vo.RatesVO getRatesVO() 

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
     * Method setInvestmentVOSets the value of field
     * 'investmentVO'.
     * 
     * @param investmentVO the value of field 'investmentVO'.
     */
    public void setInvestmentVO(edit.common.vo.InvestmentVO investmentVO)
    {
        this._investmentVO = investmentVO;
    } //-- void setInvestmentVO(edit.common.vo.InvestmentVO) 

    /**
     * Method setRatesVOSets the value of field 'ratesVO'. The
     * field 'ratesVO' has the following description: The rates
     * retrieved from PRASE for a given investment.
     * 
     * @param ratesVO the value of field 'ratesVO'.
     */
    public void setRatesVO(edit.common.vo.RatesVO ratesVO)
    {
        this._ratesVO = ratesVO;
    } //-- void setRatesVO(edit.common.vo.RatesVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.IssueInvestmentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.IssueInvestmentVO) Unmarshaller.unmarshal(edit.common.vo.IssueInvestmentVO.class, reader);
    } //-- edit.common.vo.IssueInvestmentVO unmarshal(java.io.Reader) 

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
