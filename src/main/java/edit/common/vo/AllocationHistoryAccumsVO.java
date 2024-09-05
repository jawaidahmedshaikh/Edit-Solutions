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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class AllocationHistoryAccumsVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _fundFK
     */
    private long _fundFK;

    /**
     * keeps track of state for field: _fundFK
     */
    private boolean _has_fundFK;

    /**
     * Field _cumulativeUnits
     */
    private double _cumulativeUnits;

    /**
     * keeps track of state for field: _cumulativeUnits
     */
    private boolean _has_cumulativeUnits;

    /**
     * Field _cumulativeDollars
     */
    private double _cumulativeDollars;

    /**
     * keeps track of state for field: _cumulativeDollars
     */
    private boolean _has_cumulativeDollars;


      //----------------/
     //- Constructors -/
    //----------------/

    public AllocationHistoryAccumsVO() {
        super();
    } //-- edit.common.vo.AllocationHistoryAccumsVO()


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
        
        if (obj instanceof AllocationHistoryAccumsVO) {
        
            AllocationHistoryAccumsVO temp = (AllocationHistoryAccumsVO)obj;
            if (this._fundFK != temp._fundFK)
                return false;
            if (this._has_fundFK != temp._has_fundFK)
                return false;
            if (this._cumulativeUnits != temp._cumulativeUnits)
                return false;
            if (this._has_cumulativeUnits != temp._has_cumulativeUnits)
                return false;
            if (this._cumulativeDollars != temp._cumulativeDollars)
                return false;
            if (this._has_cumulativeDollars != temp._has_cumulativeDollars)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCumulativeDollarsReturns the value of field
     * 'cumulativeDollars'.
     * 
     * @return the value of field 'cumulativeDollars'.
     */
    public double getCumulativeDollars()
    {
        return this._cumulativeDollars;
    } //-- double getCumulativeDollars() 

    /**
     * Method getCumulativeUnitsReturns the value of field
     * 'cumulativeUnits'.
     * 
     * @return the value of field 'cumulativeUnits'.
     */
    public double getCumulativeUnits()
    {
        return this._cumulativeUnits;
    } //-- double getCumulativeUnits() 

    /**
     * Method getFundFKReturns the value of field 'fundFK'.
     * 
     * @return the value of field 'fundFK'.
     */
    public long getFundFK()
    {
        return this._fundFK;
    } //-- long getFundFK() 

    /**
     * Method hasCumulativeDollars
     */
    public boolean hasCumulativeDollars()
    {
        return this._has_cumulativeDollars;
    } //-- boolean hasCumulativeDollars() 

    /**
     * Method hasCumulativeUnits
     */
    public boolean hasCumulativeUnits()
    {
        return this._has_cumulativeUnits;
    } //-- boolean hasCumulativeUnits() 

    /**
     * Method hasFundFK
     */
    public boolean hasFundFK()
    {
        return this._has_fundFK;
    } //-- boolean hasFundFK() 

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
     * Method setCumulativeDollarsSets the value of field
     * 'cumulativeDollars'.
     * 
     * @param cumulativeDollars the value of field
     * 'cumulativeDollars'.
     */
    public void setCumulativeDollars(double cumulativeDollars)
    {
        this._cumulativeDollars = cumulativeDollars;
        
        super.setVoChanged(true);
        this._has_cumulativeDollars = true;
    } //-- void setCumulativeDollars(double) 

    /**
     * Method setCumulativeUnitsSets the value of field
     * 'cumulativeUnits'.
     * 
     * @param cumulativeUnits the value of field 'cumulativeUnits'.
     */
    public void setCumulativeUnits(double cumulativeUnits)
    {
        this._cumulativeUnits = cumulativeUnits;
        
        super.setVoChanged(true);
        this._has_cumulativeUnits = true;
    } //-- void setCumulativeUnits(double) 

    /**
     * Method setFundFKSets the value of field 'fundFK'.
     * 
     * @param fundFK the value of field 'fundFK'.
     */
    public void setFundFK(long fundFK)
    {
        this._fundFK = fundFK;
        
        super.setVoChanged(true);
        this._has_fundFK = true;
    } //-- void setFundFK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AllocationHistoryAccumsVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AllocationHistoryAccumsVO) Unmarshaller.unmarshal(edit.common.vo.AllocationHistoryAccumsVO.class, reader);
    } //-- edit.common.vo.AllocationHistoryAccumsVO unmarshal(java.io.Reader) 

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
