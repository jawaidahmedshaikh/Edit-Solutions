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
 * Class AreaTableVO.
 * 
 * @version $Revision$ $Date$
 */
public class AreaTableVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _areaTablePK
     */
    private long _areaTablePK;

    /**
     * keeps track of state for field: _areaTablePK
     */
    private boolean _has_areaTablePK;

    /**
     * Field _areaStructureFK
     */
    private long _areaStructureFK;

    /**
     * keeps track of state for field: _areaStructureFK
     */
    private boolean _has_areaStructureFK;

    /**
     * Field _payoutLeadDaysCheck
     */
    private int _payoutLeadDaysCheck;

    /**
     * keeps track of state for field: _payoutLeadDaysCheck
     */
    private boolean _has_payoutLeadDaysCheck;

    /**
     * Field _payoutLeadDaysEFT
     */
    private int _payoutLeadDaysEFT;

    /**
     * keeps track of state for field: _payoutLeadDaysEFT
     */
    private boolean _has_payoutLeadDaysEFT;

    /**
     * Field _fundFK
     */
    private long _fundFK;

    /**
     * keeps track of state for field: _fundFK
     */
    private boolean _has_fundFK;

    /**
     * Field _freeLookDays
     */
    private int _freeLookDays;

    /**
     * keeps track of state for field: _freeLookDays
     */
    private boolean _has_freeLookDays;

    /**
     * Field _statementMode
     */
    private java.lang.String _statementMode;

    /**
     * Field _lookBackDays
     */
    private int _lookBackDays;

    /**
     * keeps track of state for field: _lookBackDays
     */
    private boolean _has_lookBackDays;


      //----------------/
     //- Constructors -/
    //----------------/

    public AreaTableVO() {
        super();
    } //-- edit.common.vo.AreaTableVO()


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
        
        if (obj instanceof AreaTableVO) {
        
            AreaTableVO temp = (AreaTableVO)obj;
            if (this._areaTablePK != temp._areaTablePK)
                return false;
            if (this._has_areaTablePK != temp._has_areaTablePK)
                return false;
            if (this._areaStructureFK != temp._areaStructureFK)
                return false;
            if (this._has_areaStructureFK != temp._has_areaStructureFK)
                return false;
            if (this._payoutLeadDaysCheck != temp._payoutLeadDaysCheck)
                return false;
            if (this._has_payoutLeadDaysCheck != temp._has_payoutLeadDaysCheck)
                return false;
            if (this._payoutLeadDaysEFT != temp._payoutLeadDaysEFT)
                return false;
            if (this._has_payoutLeadDaysEFT != temp._has_payoutLeadDaysEFT)
                return false;
            if (this._fundFK != temp._fundFK)
                return false;
            if (this._has_fundFK != temp._has_fundFK)
                return false;
            if (this._freeLookDays != temp._freeLookDays)
                return false;
            if (this._has_freeLookDays != temp._has_freeLookDays)
                return false;
            if (this._statementMode != null) {
                if (temp._statementMode == null) return false;
                else if (!(this._statementMode.equals(temp._statementMode))) 
                    return false;
            }
            else if (temp._statementMode != null)
                return false;
            if (this._lookBackDays != temp._lookBackDays)
                return false;
            if (this._has_lookBackDays != temp._has_lookBackDays)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAreaStructureFKReturns the value of field
     * 'areaStructureFK'.
     * 
     * @return the value of field 'areaStructureFK'.
     */
    public long getAreaStructureFK()
    {
        return this._areaStructureFK;
    } //-- long getAreaStructureFK() 

    /**
     * Method getAreaTablePKReturns the value of field
     * 'areaTablePK'.
     * 
     * @return the value of field 'areaTablePK'.
     */
    public long getAreaTablePK()
    {
        return this._areaTablePK;
    } //-- long getAreaTablePK() 

    /**
     * Method getFreeLookDaysReturns the value of field
     * 'freeLookDays'.
     * 
     * @return the value of field 'freeLookDays'.
     */
    public int getFreeLookDays()
    {
        return this._freeLookDays;
    } //-- int getFreeLookDays() 

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
     * Method getLookBackDaysReturns the value of field
     * 'lookBackDays'.
     * 
     * @return the value of field 'lookBackDays'.
     */
    public int getLookBackDays()
    {
        return this._lookBackDays;
    } //-- int getLookBackDays() 

    /**
     * Method getPayoutLeadDaysCheckReturns the value of field
     * 'payoutLeadDaysCheck'.
     * 
     * @return the value of field 'payoutLeadDaysCheck'.
     */
    public int getPayoutLeadDaysCheck()
    {
        return this._payoutLeadDaysCheck;
    } //-- int getPayoutLeadDaysCheck() 

    /**
     * Method getPayoutLeadDaysEFTReturns the value of field
     * 'payoutLeadDaysEFT'.
     * 
     * @return the value of field 'payoutLeadDaysEFT'.
     */
    public int getPayoutLeadDaysEFT()
    {
        return this._payoutLeadDaysEFT;
    } //-- int getPayoutLeadDaysEFT() 

    /**
     * Method getStatementModeReturns the value of field
     * 'statementMode'.
     * 
     * @return the value of field 'statementMode'.
     */
    public java.lang.String getStatementMode()
    {
        return this._statementMode;
    } //-- java.lang.String getStatementMode() 

    /**
     * Method hasAreaStructureFK
     */
    public boolean hasAreaStructureFK()
    {
        return this._has_areaStructureFK;
    } //-- boolean hasAreaStructureFK() 

    /**
     * Method hasAreaTablePK
     */
    public boolean hasAreaTablePK()
    {
        return this._has_areaTablePK;
    } //-- boolean hasAreaTablePK() 

    /**
     * Method hasFreeLookDays
     */
    public boolean hasFreeLookDays()
    {
        return this._has_freeLookDays;
    } //-- boolean hasFreeLookDays() 

    /**
     * Method hasFundFK
     */
    public boolean hasFundFK()
    {
        return this._has_fundFK;
    } //-- boolean hasFundFK() 

    /**
     * Method hasLookBackDays
     */
    public boolean hasLookBackDays()
    {
        return this._has_lookBackDays;
    } //-- boolean hasLookBackDays() 

    /**
     * Method hasPayoutLeadDaysCheck
     */
    public boolean hasPayoutLeadDaysCheck()
    {
        return this._has_payoutLeadDaysCheck;
    } //-- boolean hasPayoutLeadDaysCheck() 

    /**
     * Method hasPayoutLeadDaysEFT
     */
    public boolean hasPayoutLeadDaysEFT()
    {
        return this._has_payoutLeadDaysEFT;
    } //-- boolean hasPayoutLeadDaysEFT() 

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
     * Method setAreaStructureFKSets the value of field
     * 'areaStructureFK'.
     * 
     * @param areaStructureFK the value of field 'areaStructureFK'.
     */
    public void setAreaStructureFK(long areaStructureFK)
    {
        this._areaStructureFK = areaStructureFK;
        
        super.setVoChanged(true);
        this._has_areaStructureFK = true;
    } //-- void setAreaStructureFK(long) 

    /**
     * Method setAreaTablePKSets the value of field 'areaTablePK'.
     * 
     * @param areaTablePK the value of field 'areaTablePK'.
     */
    public void setAreaTablePK(long areaTablePK)
    {
        this._areaTablePK = areaTablePK;
        
        super.setVoChanged(true);
        this._has_areaTablePK = true;
    } //-- void setAreaTablePK(long) 

    /**
     * Method setFreeLookDaysSets the value of field
     * 'freeLookDays'.
     * 
     * @param freeLookDays the value of field 'freeLookDays'.
     */
    public void setFreeLookDays(int freeLookDays)
    {
        this._freeLookDays = freeLookDays;
        
        super.setVoChanged(true);
        this._has_freeLookDays = true;
    } //-- void setFreeLookDays(int) 

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
     * Method setLookBackDaysSets the value of field
     * 'lookBackDays'.
     * 
     * @param lookBackDays the value of field 'lookBackDays'.
     */
    public void setLookBackDays(int lookBackDays)
    {
        this._lookBackDays = lookBackDays;
        
        super.setVoChanged(true);
        this._has_lookBackDays = true;
    } //-- void setLookBackDays(int) 

    /**
     * Method setPayoutLeadDaysCheckSets the value of field
     * 'payoutLeadDaysCheck'.
     * 
     * @param payoutLeadDaysCheck the value of field
     * 'payoutLeadDaysCheck'.
     */
    public void setPayoutLeadDaysCheck(int payoutLeadDaysCheck)
    {
        this._payoutLeadDaysCheck = payoutLeadDaysCheck;
        
        super.setVoChanged(true);
        this._has_payoutLeadDaysCheck = true;
    } //-- void setPayoutLeadDaysCheck(int) 

    /**
     * Method setPayoutLeadDaysEFTSets the value of field
     * 'payoutLeadDaysEFT'.
     * 
     * @param payoutLeadDaysEFT the value of field
     * 'payoutLeadDaysEFT'.
     */
    public void setPayoutLeadDaysEFT(int payoutLeadDaysEFT)
    {
        this._payoutLeadDaysEFT = payoutLeadDaysEFT;
        
        super.setVoChanged(true);
        this._has_payoutLeadDaysEFT = true;
    } //-- void setPayoutLeadDaysEFT(int) 

    /**
     * Method setStatementModeSets the value of field
     * 'statementMode'.
     * 
     * @param statementMode the value of field 'statementMode'.
     */
    public void setStatementMode(java.lang.String statementMode)
    {
        this._statementMode = statementMode;
        
        super.setVoChanged(true);
    } //-- void setStatementMode(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AreaTableVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AreaTableVO) Unmarshaller.unmarshal(edit.common.vo.AreaTableVO.class, reader);
    } //-- edit.common.vo.AreaTableVO unmarshal(java.io.Reader) 

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
