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
 * Class DateCalculations.
 * 
 * @version $Revision$ $Date$
 */
public class DateCalculations extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _businessContract
     */
    private java.lang.String _businessContract;

    /**
     * Field _initialPremiumDate
     */
    private java.lang.String _initialPremiumDate;

    /**
     * Field _incomeMaturityDateList
     */
    private java.util.Vector _incomeMaturityDateList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DateCalculations() {
        super();
        _incomeMaturityDateList = new Vector();
    } //-- edit.common.vo.DateCalculations()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addIncomeMaturityDate
     * 
     * @param vIncomeMaturityDate
     */
    public void addIncomeMaturityDate(edit.common.vo.IncomeMaturityDate vIncomeMaturityDate)
        throws java.lang.IndexOutOfBoundsException
    {
        _incomeMaturityDateList.addElement(vIncomeMaturityDate);
    } //-- void addIncomeMaturityDate(edit.common.vo.IncomeMaturityDate) 

    /**
     * Method addIncomeMaturityDate
     * 
     * @param index
     * @param vIncomeMaturityDate
     */
    public void addIncomeMaturityDate(int index, edit.common.vo.IncomeMaturityDate vIncomeMaturityDate)
        throws java.lang.IndexOutOfBoundsException
    {
        _incomeMaturityDateList.insertElementAt(vIncomeMaturityDate, index);
    } //-- void addIncomeMaturityDate(int, edit.common.vo.IncomeMaturityDate) 

    /**
     * Method enumerateIncomeMaturityDate
     */
    public java.util.Enumeration enumerateIncomeMaturityDate()
    {
        return _incomeMaturityDateList.elements();
    } //-- java.util.Enumeration enumerateIncomeMaturityDate() 

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
        
        if (obj instanceof DateCalculations) {
        
            DateCalculations temp = (DateCalculations)obj;
            if (this._businessContract != null) {
                if (temp._businessContract == null) return false;
                else if (!(this._businessContract.equals(temp._businessContract))) 
                    return false;
            }
            else if (temp._businessContract != null)
                return false;
            if (this._initialPremiumDate != null) {
                if (temp._initialPremiumDate == null) return false;
                else if (!(this._initialPremiumDate.equals(temp._initialPremiumDate))) 
                    return false;
            }
            else if (temp._initialPremiumDate != null)
                return false;
            if (this._incomeMaturityDateList != null) {
                if (temp._incomeMaturityDateList == null) return false;
                else if (!(this._incomeMaturityDateList.equals(temp._incomeMaturityDateList))) 
                    return false;
            }
            else if (temp._incomeMaturityDateList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBusinessContractReturns the value of field
     * 'businessContract'.
     * 
     * @return the value of field 'businessContract'.
     */
    public java.lang.String getBusinessContract()
    {
        return this._businessContract;
    } //-- java.lang.String getBusinessContract() 

    /**
     * Method getIncomeMaturityDate
     * 
     * @param index
     */
    public edit.common.vo.IncomeMaturityDate getIncomeMaturityDate(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _incomeMaturityDateList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.IncomeMaturityDate) _incomeMaturityDateList.elementAt(index);
    } //-- edit.common.vo.IncomeMaturityDate getIncomeMaturityDate(int) 

    /**
     * Method getIncomeMaturityDate
     */
    public edit.common.vo.IncomeMaturityDate[] getIncomeMaturityDate()
    {
        int size = _incomeMaturityDateList.size();
        edit.common.vo.IncomeMaturityDate[] mArray = new edit.common.vo.IncomeMaturityDate[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.IncomeMaturityDate) _incomeMaturityDateList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.IncomeMaturityDate[] getIncomeMaturityDate() 

    /**
     * Method getIncomeMaturityDateCount
     */
    public int getIncomeMaturityDateCount()
    {
        return _incomeMaturityDateList.size();
    } //-- int getIncomeMaturityDateCount() 

    /**
     * Method getInitialPremiumDateReturns the value of field
     * 'initialPremiumDate'.
     * 
     * @return the value of field 'initialPremiumDate'.
     */
    public java.lang.String getInitialPremiumDate()
    {
        return this._initialPremiumDate;
    } //-- java.lang.String getInitialPremiumDate() 

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
     * Method removeAllIncomeMaturityDate
     */
    public void removeAllIncomeMaturityDate()
    {
        _incomeMaturityDateList.removeAllElements();
    } //-- void removeAllIncomeMaturityDate() 

    /**
     * Method removeIncomeMaturityDate
     * 
     * @param index
     */
    public edit.common.vo.IncomeMaturityDate removeIncomeMaturityDate(int index)
    {
        java.lang.Object obj = _incomeMaturityDateList.elementAt(index);
        _incomeMaturityDateList.removeElementAt(index);
        return (edit.common.vo.IncomeMaturityDate) obj;
    } //-- edit.common.vo.IncomeMaturityDate removeIncomeMaturityDate(int) 

    /**
     * Method setBusinessContractSets the value of field
     * 'businessContract'.
     * 
     * @param businessContract the value of field 'businessContract'
     */
    public void setBusinessContract(java.lang.String businessContract)
    {
        this._businessContract = businessContract;
        
        super.setVoChanged(true);
    } //-- void setBusinessContract(java.lang.String) 

    /**
     * Method setIncomeMaturityDate
     * 
     * @param index
     * @param vIncomeMaturityDate
     */
    public void setIncomeMaturityDate(int index, edit.common.vo.IncomeMaturityDate vIncomeMaturityDate)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _incomeMaturityDateList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _incomeMaturityDateList.setElementAt(vIncomeMaturityDate, index);
    } //-- void setIncomeMaturityDate(int, edit.common.vo.IncomeMaturityDate) 

    /**
     * Method setIncomeMaturityDate
     * 
     * @param incomeMaturityDateArray
     */
    public void setIncomeMaturityDate(edit.common.vo.IncomeMaturityDate[] incomeMaturityDateArray)
    {
        //-- copy array
        _incomeMaturityDateList.removeAllElements();
        for (int i = 0; i < incomeMaturityDateArray.length; i++) {
            _incomeMaturityDateList.addElement(incomeMaturityDateArray[i]);
        }
    } //-- void setIncomeMaturityDate(edit.common.vo.IncomeMaturityDate) 

    /**
     * Method setInitialPremiumDateSets the value of field
     * 'initialPremiumDate'.
     * 
     * @param initialPremiumDate the value of field
     * 'initialPremiumDate'.
     */
    public void setInitialPremiumDate(java.lang.String initialPremiumDate)
    {
        this._initialPremiumDate = initialPremiumDate;
        
        super.setVoChanged(true);
    } //-- void setInitialPremiumDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.DateCalculations unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.DateCalculations) Unmarshaller.unmarshal(edit.common.vo.DateCalculations.class, reader);
    } //-- edit.common.vo.DateCalculations unmarshal(java.io.Reader) 

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
