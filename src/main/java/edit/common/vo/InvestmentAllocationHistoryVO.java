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
 * Class InvestmentAllocationHistoryVO.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentAllocationHistoryVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentAllocationHistoryPK
     */
    private long _investmentAllocationHistoryPK;

    /**
     * keeps track of state for field: _investmentAllocationHistoryP
     */
    private boolean _has_investmentAllocationHistoryPK;

    /**
     * Field _EDITTrxHistoryFK
     */
    private long _EDITTrxHistoryFK;

    /**
     * keeps track of state for field: _EDITTrxHistoryFK
     */
    private boolean _has_EDITTrxHistoryFK;

    /**
     * Field _investmentAllocationFK
     */
    private long _investmentAllocationFK;

    /**
     * keeps track of state for field: _investmentAllocationFK
     */
    private boolean _has_investmentAllocationFK;

    /**
     * Field _previousValue
     */
    private double _previousValue;

    /**
     * keeps track of state for field: _previousValue
     */
    private boolean _has_previousValue;

    /**
     * Field _voChanged
     */
    private boolean _voChanged;

    /**
     * keeps track of state for field: _voChanged
     */
    private boolean _has_voChanged;

    /**
     * Field _voShouldBeDeleted
     */
    private boolean _voShouldBeDeleted;

    /**
     * keeps track of state for field: _voShouldBeDeleted
     */
    private boolean _has_voShouldBeDeleted;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentAllocationHistoryVO() {
        super();
    } //-- edit.common.vo.InvestmentAllocationHistoryVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method deleteVoChanged
     */
    public void deleteVoChanged()
    {
        this._has_voChanged= false;
    } //-- void deleteVoChanged() 

    /**
     * Method deleteVoShouldBeDeleted
     */
    public void deleteVoShouldBeDeleted()
    {
        this._has_voShouldBeDeleted= false;
    } //-- void deleteVoShouldBeDeleted() 

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
        
        if (obj instanceof InvestmentAllocationHistoryVO) {
        
            InvestmentAllocationHistoryVO temp = (InvestmentAllocationHistoryVO)obj;
            if (this._investmentAllocationHistoryPK != temp._investmentAllocationHistoryPK)
                return false;
            if (this._has_investmentAllocationHistoryPK != temp._has_investmentAllocationHistoryPK)
                return false;
            if (this._EDITTrxHistoryFK != temp._EDITTrxHistoryFK)
                return false;
            if (this._has_EDITTrxHistoryFK != temp._has_EDITTrxHistoryFK)
                return false;
            if (this._investmentAllocationFK != temp._investmentAllocationFK)
                return false;
            if (this._has_investmentAllocationFK != temp._has_investmentAllocationFK)
                return false;
            if (this._previousValue != temp._previousValue)
                return false;
            if (this._has_previousValue != temp._has_previousValue)
                return false;
            if (this._voChanged != temp._voChanged)
                return false;
            if (this._has_voChanged != temp._has_voChanged)
                return false;
            if (this._voShouldBeDeleted != temp._voShouldBeDeleted)
                return false;
            if (this._has_voShouldBeDeleted != temp._has_voShouldBeDeleted)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getEDITTrxHistoryFKReturns the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @return the value of field 'EDITTrxHistoryFK'.
     */
    public long getEDITTrxHistoryFK()
    {
        return this._EDITTrxHistoryFK;
    } //-- long getEDITTrxHistoryFK() 

    /**
     * Method getInvestmentAllocationFKReturns the value of field
     * 'investmentAllocationFK'.
     * 
     * @return the value of field 'investmentAllocationFK'.
     */
    public long getInvestmentAllocationFK()
    {
        return this._investmentAllocationFK;
    } //-- long getInvestmentAllocationFK() 

    /**
     * Method getInvestmentAllocationHistoryPKReturns the value of
     * field 'investmentAllocationHistoryPK'.
     * 
     * @return the value of field 'investmentAllocationHistoryPK'.
     */
    public long getInvestmentAllocationHistoryPK()
    {
        return this._investmentAllocationHistoryPK;
    } //-- long getInvestmentAllocationHistoryPK() 

    /**
     * Method getPreviousValueReturns the value of field
     * 'previousValue'.
     * 
     * @return the value of field 'previousValue'.
     */
    public double getPreviousValue()
    {
        return this._previousValue;
    } //-- double getPreviousValue() 

    /**
     * Method getVoChangedReturns the value of field 'voChanged'.
     * 
     * @return the value of field 'voChanged'.
     */
    public boolean getVoChanged()
    {
        return this._voChanged;
    } //-- boolean getVoChanged() 

    /**
     * Method getVoShouldBeDeletedReturns the value of field
     * 'voShouldBeDeleted'.
     * 
     * @return the value of field 'voShouldBeDeleted'.
     */
    public boolean getVoShouldBeDeleted()
    {
        return this._voShouldBeDeleted;
    } //-- boolean getVoShouldBeDeleted() 

    /**
     * Method hasEDITTrxHistoryFK
     */
    public boolean hasEDITTrxHistoryFK()
    {
        return this._has_EDITTrxHistoryFK;
    } //-- boolean hasEDITTrxHistoryFK() 

    /**
     * Method hasInvestmentAllocationFK
     */
    public boolean hasInvestmentAllocationFK()
    {
        return this._has_investmentAllocationFK;
    } //-- boolean hasInvestmentAllocationFK() 

    /**
     * Method hasInvestmentAllocationHistoryPK
     */
    public boolean hasInvestmentAllocationHistoryPK()
    {
        return this._has_investmentAllocationHistoryPK;
    } //-- boolean hasInvestmentAllocationHistoryPK() 

    /**
     * Method hasPreviousValue
     */
    public boolean hasPreviousValue()
    {
        return this._has_previousValue;
    } //-- boolean hasPreviousValue() 

    /**
     * Method hasVoChanged
     */
    public boolean hasVoChanged()
    {
        return this._has_voChanged;
    } //-- boolean hasVoChanged() 

    /**
     * Method hasVoShouldBeDeleted
     */
    public boolean hasVoShouldBeDeleted()
    {
        return this._has_voShouldBeDeleted;
    } //-- boolean hasVoShouldBeDeleted() 

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
     * Method setEDITTrxHistoryFKSets the value of field
     * 'EDITTrxHistoryFK'.
     * 
     * @param EDITTrxHistoryFK the value of field 'EDITTrxHistoryFK'
     */
    public void setEDITTrxHistoryFK(long EDITTrxHistoryFK)
    {
        this._EDITTrxHistoryFK = EDITTrxHistoryFK;
        
        this._voChanged = true;
        this._has_EDITTrxHistoryFK = true;
    } //-- void setEDITTrxHistoryFK(long) 

    /**
     * Method setInvestmentAllocationFKSets the value of field
     * 'investmentAllocationFK'.
     * 
     * @param investmentAllocationFK the value of field
     * 'investmentAllocationFK'.
     */
    public void setInvestmentAllocationFK(long investmentAllocationFK)
    {
        this._investmentAllocationFK = investmentAllocationFK;
        
        this._voChanged = true;
        this._has_investmentAllocationFK = true;
    } //-- void setInvestmentAllocationFK(long) 

    /**
     * Method setInvestmentAllocationHistoryPKSets the value of
     * field 'investmentAllocationHistoryPK'.
     * 
     * @param investmentAllocationHistoryPK the value of field
     * 'investmentAllocationHistoryPK'.
     */
    public void setInvestmentAllocationHistoryPK(long investmentAllocationHistoryPK)
    {
        this._investmentAllocationHistoryPK = investmentAllocationHistoryPK;
        
        this._voChanged = true;
        this._has_investmentAllocationHistoryPK = true;
    } //-- void setInvestmentAllocationHistoryPK(long) 

    /**
     * Method setPreviousValueSets the value of field
     * 'previousValue'.
     * 
     * @param previousValue the value of field 'previousValue'.
     */
    public void setPreviousValue(double previousValue)
    {
        this._previousValue = previousValue;
        
        this._voChanged = true;
        this._has_previousValue = true;
    } //-- void setPreviousValue(double) 

    /**
     * Method setVoChangedSets the value of field 'voChanged'.
     * 
     * @param voChanged the value of field 'voChanged'.
     */
    public void setVoChanged(boolean voChanged)
    {
        this._voChanged = voChanged;
        this._has_voChanged = true;
    } //-- void setVoChanged(boolean) 

    /**
     * Method setVoShouldBeDeletedSets the value of field
     * 'voShouldBeDeleted'.
     * 
     * @param voShouldBeDeleted the value of field
     * 'voShouldBeDeleted'.
     */
    public void setVoShouldBeDeleted(boolean voShouldBeDeleted)
    {
        this._voShouldBeDeleted = voShouldBeDeleted;
        this._has_voShouldBeDeleted = true;
    } //-- void setVoShouldBeDeleted(boolean) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentAllocationHistoryVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentAllocationHistoryVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentAllocationHistoryVO.class, reader);
    } //-- edit.common.vo.InvestmentAllocationHistoryVO unmarshal(java.io.Reader) 

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
