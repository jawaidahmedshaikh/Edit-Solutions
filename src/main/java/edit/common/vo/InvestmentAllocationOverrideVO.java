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
 * Class InvestmentAllocationOverrideVO.
 * 
 * @version $Revision$ $Date$
 */
public class InvestmentAllocationOverrideVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _investmentAllocationOverridePK
     */
    private long _investmentAllocationOverridePK;

    /**
     * keeps track of state for field:
     * _investmentAllocationOverridePK
     */
    private boolean _has_investmentAllocationOverridePK;

    /**
     * Field _contractSetupFK
     */
    private long _contractSetupFK;

    /**
     * keeps track of state for field: _contractSetupFK
     */
    private boolean _has_contractSetupFK;

    /**
     * Field _investmentFK
     */
    private long _investmentFK;

    /**
     * keeps track of state for field: _investmentFK
     */
    private boolean _has_investmentFK;

    /**
     * Field _investmentAllocationFK
     */
    private long _investmentAllocationFK;

    /**
     * keeps track of state for field: _investmentAllocationFK
     */
    private boolean _has_investmentAllocationFK;

    /**
     * Field _toFromStatus
     */
    private java.lang.String _toFromStatus;

    /**
     * Field _HFStatus
     */
    private java.lang.String _HFStatus;

    /**
     * Field _HFIAIndicator
     */
    private java.lang.String _HFIAIndicator;

    /**
     * Field _bucketFK
     */
    private long _bucketFK;

    /**
     * keeps track of state for field: _bucketFK
     */
    private boolean _has_bucketFK;

    /**
     * Field _hedgeFundInvestmentFK
     */
    private long _hedgeFundInvestmentFK;

    /**
     * keeps track of state for field: _hedgeFundInvestmentFK
     */
    private boolean _has_hedgeFundInvestmentFK;

    /**
     * Field _holdingAccountIndicator
     */
    private java.lang.String _holdingAccountIndicator;


      //----------------/
     //- Constructors -/
    //----------------/

    public InvestmentAllocationOverrideVO() {
        super();
    } //-- edit.common.vo.InvestmentAllocationOverrideVO()


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
        
        if (obj instanceof InvestmentAllocationOverrideVO) {
        
            InvestmentAllocationOverrideVO temp = (InvestmentAllocationOverrideVO)obj;
            if (this._investmentAllocationOverridePK != temp._investmentAllocationOverridePK)
                return false;
            if (this._has_investmentAllocationOverridePK != temp._has_investmentAllocationOverridePK)
                return false;
            if (this._contractSetupFK != temp._contractSetupFK)
                return false;
            if (this._has_contractSetupFK != temp._has_contractSetupFK)
                return false;
            if (this._investmentFK != temp._investmentFK)
                return false;
            if (this._has_investmentFK != temp._has_investmentFK)
                return false;
            if (this._investmentAllocationFK != temp._investmentAllocationFK)
                return false;
            if (this._has_investmentAllocationFK != temp._has_investmentAllocationFK)
                return false;
            if (this._toFromStatus != null) {
                if (temp._toFromStatus == null) return false;
                else if (!(this._toFromStatus.equals(temp._toFromStatus))) 
                    return false;
            }
            else if (temp._toFromStatus != null)
                return false;
            if (this._HFStatus != null) {
                if (temp._HFStatus == null) return false;
                else if (!(this._HFStatus.equals(temp._HFStatus))) 
                    return false;
            }
            else if (temp._HFStatus != null)
                return false;
            if (this._HFIAIndicator != null) {
                if (temp._HFIAIndicator == null) return false;
                else if (!(this._HFIAIndicator.equals(temp._HFIAIndicator))) 
                    return false;
            }
            else if (temp._HFIAIndicator != null)
                return false;
            if (this._bucketFK != temp._bucketFK)
                return false;
            if (this._has_bucketFK != temp._has_bucketFK)
                return false;
            if (this._hedgeFundInvestmentFK != temp._hedgeFundInvestmentFK)
                return false;
            if (this._has_hedgeFundInvestmentFK != temp._has_hedgeFundInvestmentFK)
                return false;
            if (this._holdingAccountIndicator != null) {
                if (temp._holdingAccountIndicator == null) return false;
                else if (!(this._holdingAccountIndicator.equals(temp._holdingAccountIndicator))) 
                    return false;
            }
            else if (temp._holdingAccountIndicator != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getBucketFKReturns the value of field 'bucketFK'.
     * 
     * @return the value of field 'bucketFK'.
     */
    public long getBucketFK()
    {
        return this._bucketFK;
    } //-- long getBucketFK() 

    /**
     * Method getContractSetupFKReturns the value of field
     * 'contractSetupFK'.
     * 
     * @return the value of field 'contractSetupFK'.
     */
    public long getContractSetupFK()
    {
        return this._contractSetupFK;
    } //-- long getContractSetupFK() 

    /**
     * Method getHFIAIndicatorReturns the value of field
     * 'HFIAIndicator'.
     * 
     * @return the value of field 'HFIAIndicator'.
     */
    public java.lang.String getHFIAIndicator()
    {
        return this._HFIAIndicator;
    } //-- java.lang.String getHFIAIndicator() 

    /**
     * Method getHFStatusReturns the value of field 'HFStatus'.
     * 
     * @return the value of field 'HFStatus'.
     */
    public java.lang.String getHFStatus()
    {
        return this._HFStatus;
    } //-- java.lang.String getHFStatus() 

    /**
     * Method getHedgeFundInvestmentFKReturns the value of field
     * 'hedgeFundInvestmentFK'.
     * 
     * @return the value of field 'hedgeFundInvestmentFK'.
     */
    public long getHedgeFundInvestmentFK()
    {
        return this._hedgeFundInvestmentFK;
    } //-- long getHedgeFundInvestmentFK() 

    /**
     * Method getHoldingAccountIndicatorReturns the value of field
     * 'holdingAccountIndicator'.
     * 
     * @return the value of field 'holdingAccountIndicator'.
     */
    public java.lang.String getHoldingAccountIndicator()
    {
        return this._holdingAccountIndicator;
    } //-- java.lang.String getHoldingAccountIndicator() 

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
     * Method getInvestmentAllocationOverridePKReturns the value of
     * field 'investmentAllocationOverridePK'.
     * 
     * @return the value of field 'investmentAllocationOverridePK'.
     */
    public long getInvestmentAllocationOverridePK()
    {
        return this._investmentAllocationOverridePK;
    } //-- long getInvestmentAllocationOverridePK() 

    /**
     * Method getInvestmentFKReturns the value of field
     * 'investmentFK'.
     * 
     * @return the value of field 'investmentFK'.
     */
    public long getInvestmentFK()
    {
        return this._investmentFK;
    } //-- long getInvestmentFK() 

    /**
     * Method getToFromStatusReturns the value of field
     * 'toFromStatus'.
     * 
     * @return the value of field 'toFromStatus'.
     */
    public java.lang.String getToFromStatus()
    {
        return this._toFromStatus;
    } //-- java.lang.String getToFromStatus() 

    /**
     * Method hasBucketFK
     */
    public boolean hasBucketFK()
    {
        return this._has_bucketFK;
    } //-- boolean hasBucketFK() 

    /**
     * Method hasContractSetupFK
     */
    public boolean hasContractSetupFK()
    {
        return this._has_contractSetupFK;
    } //-- boolean hasContractSetupFK() 

    /**
     * Method hasHedgeFundInvestmentFK
     */
    public boolean hasHedgeFundInvestmentFK()
    {
        return this._has_hedgeFundInvestmentFK;
    } //-- boolean hasHedgeFundInvestmentFK() 

    /**
     * Method hasInvestmentAllocationFK
     */
    public boolean hasInvestmentAllocationFK()
    {
        return this._has_investmentAllocationFK;
    } //-- boolean hasInvestmentAllocationFK() 

    /**
     * Method hasInvestmentAllocationOverridePK
     */
    public boolean hasInvestmentAllocationOverridePK()
    {
        return this._has_investmentAllocationOverridePK;
    } //-- boolean hasInvestmentAllocationOverridePK() 

    /**
     * Method hasInvestmentFK
     */
    public boolean hasInvestmentFK()
    {
        return this._has_investmentFK;
    } //-- boolean hasInvestmentFK() 

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
     * Method setBucketFKSets the value of field 'bucketFK'.
     * 
     * @param bucketFK the value of field 'bucketFK'.
     */
    public void setBucketFK(long bucketFK)
    {
        this._bucketFK = bucketFK;
        
        super.setVoChanged(true);
        this._has_bucketFK = true;
    } //-- void setBucketFK(long) 

    /**
     * Method setContractSetupFKSets the value of field
     * 'contractSetupFK'.
     * 
     * @param contractSetupFK the value of field 'contractSetupFK'.
     */
    public void setContractSetupFK(long contractSetupFK)
    {
        this._contractSetupFK = contractSetupFK;
        
        super.setVoChanged(true);
        this._has_contractSetupFK = true;
    } //-- void setContractSetupFK(long) 

    /**
     * Method setHFIAIndicatorSets the value of field
     * 'HFIAIndicator'.
     * 
     * @param HFIAIndicator the value of field 'HFIAIndicator'.
     */
    public void setHFIAIndicator(java.lang.String HFIAIndicator)
    {
        this._HFIAIndicator = HFIAIndicator;
        
        super.setVoChanged(true);
    } //-- void setHFIAIndicator(java.lang.String) 

    /**
     * Method setHFStatusSets the value of field 'HFStatus'.
     * 
     * @param HFStatus the value of field 'HFStatus'.
     */
    public void setHFStatus(java.lang.String HFStatus)
    {
        this._HFStatus = HFStatus;
        
        super.setVoChanged(true);
    } //-- void setHFStatus(java.lang.String) 

    /**
     * Method setHedgeFundInvestmentFKSets the value of field
     * 'hedgeFundInvestmentFK'.
     * 
     * @param hedgeFundInvestmentFK the value of field
     * 'hedgeFundInvestmentFK'.
     */
    public void setHedgeFundInvestmentFK(long hedgeFundInvestmentFK)
    {
        this._hedgeFundInvestmentFK = hedgeFundInvestmentFK;
        
        super.setVoChanged(true);
        this._has_hedgeFundInvestmentFK = true;
    } //-- void setHedgeFundInvestmentFK(long) 

    /**
     * Method setHoldingAccountIndicatorSets the value of field
     * 'holdingAccountIndicator'.
     * 
     * @param holdingAccountIndicator the value of field
     * 'holdingAccountIndicator'.
     */
    public void setHoldingAccountIndicator(java.lang.String holdingAccountIndicator)
    {
        this._holdingAccountIndicator = holdingAccountIndicator;
        
        super.setVoChanged(true);
    } //-- void setHoldingAccountIndicator(java.lang.String) 

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
        
        super.setVoChanged(true);
        this._has_investmentAllocationFK = true;
    } //-- void setInvestmentAllocationFK(long) 

    /**
     * Method setInvestmentAllocationOverridePKSets the value of
     * field 'investmentAllocationOverridePK'.
     * 
     * @param investmentAllocationOverridePK the value of field
     * 'investmentAllocationOverridePK'.
     */
    public void setInvestmentAllocationOverridePK(long investmentAllocationOverridePK)
    {
        this._investmentAllocationOverridePK = investmentAllocationOverridePK;
        
        super.setVoChanged(true);
        this._has_investmentAllocationOverridePK = true;
    } //-- void setInvestmentAllocationOverridePK(long) 

    /**
     * Method setInvestmentFKSets the value of field
     * 'investmentFK'.
     * 
     * @param investmentFK the value of field 'investmentFK'.
     */
    public void setInvestmentFK(long investmentFK)
    {
        this._investmentFK = investmentFK;
        
        super.setVoChanged(true);
        this._has_investmentFK = true;
    } //-- void setInvestmentFK(long) 

    /**
     * Method setToFromStatusSets the value of field
     * 'toFromStatus'.
     * 
     * @param toFromStatus the value of field 'toFromStatus'.
     */
    public void setToFromStatus(java.lang.String toFromStatus)
    {
        this._toFromStatus = toFromStatus;
        
        super.setVoChanged(true);
    } //-- void setToFromStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.InvestmentAllocationOverrideVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.InvestmentAllocationOverrideVO) Unmarshaller.unmarshal(edit.common.vo.InvestmentAllocationOverrideVO.class, reader);
    } //-- edit.common.vo.InvestmentAllocationOverrideVO unmarshal(java.io.Reader) 

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
