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
 * Class IncomeMaturityDate.
 * 
 * @version $Revision$ $Date$
 */
public class IncomeMaturityDate extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _qualNonQualInd
     */
    private java.lang.String _qualNonQualInd;

    /**
     * Field _years
     */
    private java.lang.String _years;

    /**
     * Field _months
     */
    private java.lang.String _months;


      //----------------/
     //- Constructors -/
    //----------------/

    public IncomeMaturityDate() {
        super();
    } //-- edit.common.vo.IncomeMaturityDate()


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
        
        if (obj instanceof IncomeMaturityDate) {
        
            IncomeMaturityDate temp = (IncomeMaturityDate)obj;
            if (this._qualNonQualInd != null) {
                if (temp._qualNonQualInd == null) return false;
                else if (!(this._qualNonQualInd.equals(temp._qualNonQualInd))) 
                    return false;
            }
            else if (temp._qualNonQualInd != null)
                return false;
            if (this._years != null) {
                if (temp._years == null) return false;
                else if (!(this._years.equals(temp._years))) 
                    return false;
            }
            else if (temp._years != null)
                return false;
            if (this._months != null) {
                if (temp._months == null) return false;
                else if (!(this._months.equals(temp._months))) 
                    return false;
            }
            else if (temp._months != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getMonthsReturns the value of field 'months'.
     * 
     * @return the value of field 'months'.
     */
    public java.lang.String getMonths()
    {
        return this._months;
    } //-- java.lang.String getMonths() 

    /**
     * Method getQualNonQualIndReturns the value of field
     * 'qualNonQualInd'.
     * 
     * @return the value of field 'qualNonQualInd'.
     */
    public java.lang.String getQualNonQualInd()
    {
        return this._qualNonQualInd;
    } //-- java.lang.String getQualNonQualInd() 

    /**
     * Method getYearsReturns the value of field 'years'.
     * 
     * @return the value of field 'years'.
     */
    public java.lang.String getYears()
    {
        return this._years;
    } //-- java.lang.String getYears() 

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
     * Method setMonthsSets the value of field 'months'.
     * 
     * @param months the value of field 'months'.
     */
    public void setMonths(java.lang.String months)
    {
        this._months = months;
        
        super.setVoChanged(true);
    } //-- void setMonths(java.lang.String) 

    /**
     * Method setQualNonQualIndSets the value of field
     * 'qualNonQualInd'.
     * 
     * @param qualNonQualInd the value of field 'qualNonQualInd'.
     */
    public void setQualNonQualInd(java.lang.String qualNonQualInd)
    {
        this._qualNonQualInd = qualNonQualInd;
        
        super.setVoChanged(true);
    } //-- void setQualNonQualInd(java.lang.String) 

    /**
     * Method setYearsSets the value of field 'years'.
     * 
     * @param years the value of field 'years'.
     */
    public void setYears(java.lang.String years)
    {
        this._years = years;
        
        super.setVoChanged(true);
    } //-- void setYears(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.IncomeMaturityDate unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.IncomeMaturityDate) Unmarshaller.unmarshal(edit.common.vo.IncomeMaturityDate.class, reader);
    } //-- edit.common.vo.IncomeMaturityDate unmarshal(java.io.Reader) 

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
