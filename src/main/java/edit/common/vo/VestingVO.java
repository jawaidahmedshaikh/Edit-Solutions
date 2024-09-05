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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class VestingVO.
 * 
 * @version $Revision$ $Date$
 */
public class VestingVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _vestingPK
     */
    private long _vestingPK;

    /**
     * keeps track of state for field: _vestingPK
     */
    private boolean _has_vestingPK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _termVestingBasisCT
     */
    private java.lang.String _termVestingBasisCT;

    /**
     * Field _termVestingStatusCT
     */
    private java.lang.String _termVestingStatusCT;

    /**
     * Field _termVestingPercent
     */
    private java.math.BigDecimal _termVestingPercent;

    /**
     * Field _vestingDuration
     */
    private int _vestingDuration;

    /**
     * keeps track of state for field: _vestingDuration
     */
    private boolean _has_vestingDuration;


      //----------------/
     //- Constructors -/
    //----------------/

    public VestingVO() {
        super();
    } //-- edit.common.vo.VestingVO()


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
        
        if (obj instanceof VestingVO) {
        
            VestingVO temp = (VestingVO)obj;
            if (this._vestingPK != temp._vestingPK)
                return false;
            if (this._has_vestingPK != temp._has_vestingPK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;
            if (this._termVestingBasisCT != null) {
                if (temp._termVestingBasisCT == null) return false;
                else if (!(this._termVestingBasisCT.equals(temp._termVestingBasisCT))) 
                    return false;
            }
            else if (temp._termVestingBasisCT != null)
                return false;
            if (this._termVestingStatusCT != null) {
                if (temp._termVestingStatusCT == null) return false;
                else if (!(this._termVestingStatusCT.equals(temp._termVestingStatusCT))) 
                    return false;
            }
            else if (temp._termVestingStatusCT != null)
                return false;
            if (this._termVestingPercent != null) {
                if (temp._termVestingPercent == null) return false;
                else if (!(this._termVestingPercent.equals(temp._termVestingPercent))) 
                    return false;
            }
            else if (temp._termVestingPercent != null)
                return false;
            if (this._vestingDuration != temp._vestingDuration)
                return false;
            if (this._has_vestingDuration != temp._has_vestingDuration)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentFKReturns the value of field 'agentFK'.
     * 
     * @return the value of field 'agentFK'.
     */
    public long getAgentFK()
    {
        return this._agentFK;
    } //-- long getAgentFK() 

    /**
     * Method getTermVestingBasisCTReturns the value of field
     * 'termVestingBasisCT'.
     * 
     * @return the value of field 'termVestingBasisCT'.
     */
    public java.lang.String getTermVestingBasisCT()
    {
        return this._termVestingBasisCT;
    } //-- java.lang.String getTermVestingBasisCT() 

    /**
     * Method getTermVestingPercentReturns the value of field
     * 'termVestingPercent'.
     * 
     * @return the value of field 'termVestingPercent'.
     */
    public java.math.BigDecimal getTermVestingPercent()
    {
        return this._termVestingPercent;
    } //-- java.math.BigDecimal getTermVestingPercent() 

    /**
     * Method getTermVestingStatusCTReturns the value of field
     * 'termVestingStatusCT'.
     * 
     * @return the value of field 'termVestingStatusCT'.
     */
    public java.lang.String getTermVestingStatusCT()
    {
        return this._termVestingStatusCT;
    } //-- java.lang.String getTermVestingStatusCT() 

    /**
     * Method getVestingDurationReturns the value of field
     * 'vestingDuration'.
     * 
     * @return the value of field 'vestingDuration'.
     */
    public int getVestingDuration()
    {
        return this._vestingDuration;
    } //-- int getVestingDuration() 

    /**
     * Method getVestingPKReturns the value of field 'vestingPK'.
     * 
     * @return the value of field 'vestingPK'.
     */
    public long getVestingPK()
    {
        return this._vestingPK;
    } //-- long getVestingPK() 

    /**
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasVestingDuration
     */
    public boolean hasVestingDuration()
    {
        return this._has_vestingDuration;
    } //-- boolean hasVestingDuration() 

    /**
     * Method hasVestingPK
     */
    public boolean hasVestingPK()
    {
        return this._has_vestingPK;
    } //-- boolean hasVestingPK() 

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
     * Method setAgentFKSets the value of field 'agentFK'.
     * 
     * @param agentFK the value of field 'agentFK'.
     */
    public void setAgentFK(long agentFK)
    {
        this._agentFK = agentFK;
        
        super.setVoChanged(true);
        this._has_agentFK = true;
    } //-- void setAgentFK(long) 

    /**
     * Method setTermVestingBasisCTSets the value of field
     * 'termVestingBasisCT'.
     * 
     * @param termVestingBasisCT the value of field
     * 'termVestingBasisCT'.
     */
    public void setTermVestingBasisCT(java.lang.String termVestingBasisCT)
    {
        this._termVestingBasisCT = termVestingBasisCT;
        
        super.setVoChanged(true);
    } //-- void setTermVestingBasisCT(java.lang.String) 

    /**
     * Method setTermVestingPercentSets the value of field
     * 'termVestingPercent'.
     * 
     * @param termVestingPercent the value of field
     * 'termVestingPercent'.
     */
    public void setTermVestingPercent(java.math.BigDecimal termVestingPercent)
    {
        this._termVestingPercent = termVestingPercent;
        
        super.setVoChanged(true);
    } //-- void setTermVestingPercent(java.math.BigDecimal) 

    /**
     * Method setTermVestingStatusCTSets the value of field
     * 'termVestingStatusCT'.
     * 
     * @param termVestingStatusCT the value of field
     * 'termVestingStatusCT'.
     */
    public void setTermVestingStatusCT(java.lang.String termVestingStatusCT)
    {
        this._termVestingStatusCT = termVestingStatusCT;
        
        super.setVoChanged(true);
    } //-- void setTermVestingStatusCT(java.lang.String) 

    /**
     * Method setVestingDurationSets the value of field
     * 'vestingDuration'.
     * 
     * @param vestingDuration the value of field 'vestingDuration'.
     */
    public void setVestingDuration(int vestingDuration)
    {
        this._vestingDuration = vestingDuration;
        
        super.setVoChanged(true);
        this._has_vestingDuration = true;
    } //-- void setVestingDuration(int) 

    /**
     * Method setVestingPKSets the value of field 'vestingPK'.
     * 
     * @param vestingPK the value of field 'vestingPK'.
     */
    public void setVestingPK(long vestingPK)
    {
        this._vestingPK = vestingPK;
        
        super.setVoChanged(true);
        this._has_vestingPK = true;
    } //-- void setVestingPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.VestingVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.VestingVO) Unmarshaller.unmarshal(edit.common.vo.VestingVO.class, reader);
    } //-- edit.common.vo.VestingVO unmarshal(java.io.Reader) 

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
