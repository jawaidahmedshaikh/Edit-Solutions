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
 * Class AgentSnapshotVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentSnapshotVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentSnapshotPK
     */
    private long _agentSnapshotPK;

    /**
     * keeps track of state for field: _agentSnapshotPK
     */
    private boolean _has_agentSnapshotPK;

    /**
     * Field _agentHierarchyFK
     */
    private long _agentHierarchyFK;

    /**
     * keeps track of state for field: _agentHierarchyFK
     */
    private boolean _has_agentHierarchyFK;

    /**
     * Field _placedAgentFK
     */
    private long _placedAgentFK;

    /**
     * keeps track of state for field: _placedAgentFK
     */
    private boolean _has_placedAgentFK;

    /**
     * Field _hierarchyLevel
     */
    private int _hierarchyLevel;

    /**
     * keeps track of state for field: _hierarchyLevel
     */
    private boolean _has_hierarchyLevel;

    /**
     * Field _commissionOverrideAmount
     */
    private java.math.BigDecimal _commissionOverrideAmount;

    /**
     * Field _commissionOverridePercent
     */
    private java.math.BigDecimal _commissionOverridePercent;

    /**
     * Field _commHoldAmountOverride
     */
    private java.math.BigDecimal _commHoldAmountOverride;

    /**
     * Field _commHoldReleaseDateOverride
     */
    private java.lang.String _commHoldReleaseDateOverride;

    /**
     * Field _commissionProfileFK
     */
    private long _commissionProfileFK;

    /**
     * keeps track of state for field: _commissionProfileFK
     */
    private boolean _has_commissionProfileFK;

    /**
     * Field _advanceAmount
     */
    private java.math.BigDecimal _advanceAmount;

    /**
     * Field _advanceRecovery
     */
    private java.math.BigDecimal _advanceRecovery;

    /**
     * Field _servicingAgentIndicator
     */
    private java.lang.String _servicingAgentIndicator;

    /**
     * Field _advancePercent
     */
    private java.math.BigDecimal _advancePercent;

    /**
     * Field _recoveryPercent
     */
    private java.math.BigDecimal _recoveryPercent;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentSnapshotVO() {
        super();
    } //-- edit.common.vo.AgentSnapshotVO()


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
        
        if (obj instanceof AgentSnapshotVO) {
        
            AgentSnapshotVO temp = (AgentSnapshotVO)obj;
            if (this._agentSnapshotPK != temp._agentSnapshotPK)
                return false;
            if (this._has_agentSnapshotPK != temp._has_agentSnapshotPK)
                return false;
            if (this._agentHierarchyFK != temp._agentHierarchyFK)
                return false;
            if (this._has_agentHierarchyFK != temp._has_agentHierarchyFK)
                return false;
            if (this._placedAgentFK != temp._placedAgentFK)
                return false;
            if (this._has_placedAgentFK != temp._has_placedAgentFK)
                return false;
            if (this._hierarchyLevel != temp._hierarchyLevel)
                return false;
            if (this._has_hierarchyLevel != temp._has_hierarchyLevel)
                return false;
            if (this._commissionOverrideAmount != null) {
                if (temp._commissionOverrideAmount == null) return false;
                else if (!(this._commissionOverrideAmount.equals(temp._commissionOverrideAmount))) 
                    return false;
            }
            else if (temp._commissionOverrideAmount != null)
                return false;
            if (this._commissionOverridePercent != null) {
                if (temp._commissionOverridePercent == null) return false;
                else if (!(this._commissionOverridePercent.equals(temp._commissionOverridePercent))) 
                    return false;
            }
            else if (temp._commissionOverridePercent != null)
                return false;
            if (this._commHoldAmountOverride != null) {
                if (temp._commHoldAmountOverride == null) return false;
                else if (!(this._commHoldAmountOverride.equals(temp._commHoldAmountOverride))) 
                    return false;
            }
            else if (temp._commHoldAmountOverride != null)
                return false;
            if (this._commHoldReleaseDateOverride != null) {
                if (temp._commHoldReleaseDateOverride == null) return false;
                else if (!(this._commHoldReleaseDateOverride.equals(temp._commHoldReleaseDateOverride))) 
                    return false;
            }
            else if (temp._commHoldReleaseDateOverride != null)
                return false;
            if (this._commissionProfileFK != temp._commissionProfileFK)
                return false;
            if (this._has_commissionProfileFK != temp._has_commissionProfileFK)
                return false;
            if (this._advanceAmount != null) {
                if (temp._advanceAmount == null) return false;
                else if (!(this._advanceAmount.equals(temp._advanceAmount))) 
                    return false;
            }
            else if (temp._advanceAmount != null)
                return false;
            if (this._advanceRecovery != null) {
                if (temp._advanceRecovery == null) return false;
                else if (!(this._advanceRecovery.equals(temp._advanceRecovery))) 
                    return false;
            }
            else if (temp._advanceRecovery != null)
                return false;
            if (this._servicingAgentIndicator != null) {
                if (temp._servicingAgentIndicator == null) return false;
                else if (!(this._servicingAgentIndicator.equals(temp._servicingAgentIndicator))) 
                    return false;
            }
            else if (temp._servicingAgentIndicator != null)
                return false;
            if (this._advancePercent != null) {
                if (temp._advancePercent == null) return false;
                else if (!(this._advancePercent.equals(temp._advancePercent))) 
                    return false;
            }
            else if (temp._advancePercent != null)
                return false;
            if (this._recoveryPercent != null) {
                if (temp._recoveryPercent == null) return false;
                else if (!(this._recoveryPercent.equals(temp._recoveryPercent))) 
                    return false;
            }
            else if (temp._recoveryPercent != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdvanceAmountReturns the value of field
     * 'advanceAmount'.
     * 
     * @return the value of field 'advanceAmount'.
     */
    public java.math.BigDecimal getAdvanceAmount()
    {
        return this._advanceAmount;
    } //-- java.math.BigDecimal getAdvanceAmount() 

    /**
     * Method getAdvancePercentReturns the value of field
     * 'advancePercent'.
     * 
     * @return the value of field 'advancePercent'.
     */
    public java.math.BigDecimal getAdvancePercent()
    {
        return this._advancePercent;
    } //-- java.math.BigDecimal getAdvancePercent() 

    /**
     * Method getAdvanceRecoveryReturns the value of field
     * 'advanceRecovery'.
     * 
     * @return the value of field 'advanceRecovery'.
     */
    public java.math.BigDecimal getAdvanceRecovery()
    {
        return this._advanceRecovery;
    } //-- java.math.BigDecimal getAdvanceRecovery() 

    /**
     * Method getAgentHierarchyFKReturns the value of field
     * 'agentHierarchyFK'.
     * 
     * @return the value of field 'agentHierarchyFK'.
     */
    public long getAgentHierarchyFK()
    {
        return this._agentHierarchyFK;
    } //-- long getAgentHierarchyFK() 

    /**
     * Method getAgentSnapshotPKReturns the value of field
     * 'agentSnapshotPK'.
     * 
     * @return the value of field 'agentSnapshotPK'.
     */
    public long getAgentSnapshotPK()
    {
        return this._agentSnapshotPK;
    } //-- long getAgentSnapshotPK() 

    /**
     * Method getCommHoldAmountOverrideReturns the value of field
     * 'commHoldAmountOverride'.
     * 
     * @return the value of field 'commHoldAmountOverride'.
     */
    public java.math.BigDecimal getCommHoldAmountOverride()
    {
        return this._commHoldAmountOverride;
    } //-- java.math.BigDecimal getCommHoldAmountOverride() 

    /**
     * Method getCommHoldReleaseDateOverrideReturns the value of
     * field 'commHoldReleaseDateOverride'.
     * 
     * @return the value of field 'commHoldReleaseDateOverride'.
     */
    public java.lang.String getCommHoldReleaseDateOverride()
    {
        return this._commHoldReleaseDateOverride;
    } //-- java.lang.String getCommHoldReleaseDateOverride() 

    /**
     * Method getCommissionOverrideAmountReturns the value of field
     * 'commissionOverrideAmount'.
     * 
     * @return the value of field 'commissionOverrideAmount'.
     */
    public java.math.BigDecimal getCommissionOverrideAmount()
    {
        return this._commissionOverrideAmount;
    } //-- java.math.BigDecimal getCommissionOverrideAmount() 

    /**
     * Method getCommissionOverridePercentReturns the value of
     * field 'commissionOverridePercent'.
     * 
     * @return the value of field 'commissionOverridePercent'.
     */
    public java.math.BigDecimal getCommissionOverridePercent()
    {
        return this._commissionOverridePercent;
    } //-- java.math.BigDecimal getCommissionOverridePercent() 

    /**
     * Method getCommissionProfileFKReturns the value of field
     * 'commissionProfileFK'.
     * 
     * @return the value of field 'commissionProfileFK'.
     */
    public long getCommissionProfileFK()
    {
        return this._commissionProfileFK;
    } //-- long getCommissionProfileFK() 

    /**
     * Method getHierarchyLevelReturns the value of field
     * 'hierarchyLevel'.
     * 
     * @return the value of field 'hierarchyLevel'.
     */
    public int getHierarchyLevel()
    {
        return this._hierarchyLevel;
    } //-- int getHierarchyLevel() 

    /**
     * Method getPlacedAgentFKReturns the value of field
     * 'placedAgentFK'.
     * 
     * @return the value of field 'placedAgentFK'.
     */
    public long getPlacedAgentFK()
    {
        return this._placedAgentFK;
    } //-- long getPlacedAgentFK() 

    /**
     * Method getRecoveryPercentReturns the value of field
     * 'recoveryPercent'.
     * 
     * @return the value of field 'recoveryPercent'.
     */
    public java.math.BigDecimal getRecoveryPercent()
    {
        return this._recoveryPercent;
    } //-- java.math.BigDecimal getRecoveryPercent() 

    /**
     * Method getServicingAgentIndicatorReturns the value of field
     * 'servicingAgentIndicator'.
     * 
     * @return the value of field 'servicingAgentIndicator'.
     */
    public java.lang.String getServicingAgentIndicator()
    {
        return this._servicingAgentIndicator;
    } //-- java.lang.String getServicingAgentIndicator() 

    /**
     * Method hasAgentHierarchyFK
     */
    public boolean hasAgentHierarchyFK()
    {
        return this._has_agentHierarchyFK;
    } //-- boolean hasAgentHierarchyFK() 

    /**
     * Method hasAgentSnapshotPK
     */
    public boolean hasAgentSnapshotPK()
    {
        return this._has_agentSnapshotPK;
    } //-- boolean hasAgentSnapshotPK() 

    /**
     * Method hasCommissionProfileFK
     */
    public boolean hasCommissionProfileFK()
    {
        return this._has_commissionProfileFK;
    } //-- boolean hasCommissionProfileFK() 

    /**
     * Method hasHierarchyLevel
     */
    public boolean hasHierarchyLevel()
    {
        return this._has_hierarchyLevel;
    } //-- boolean hasHierarchyLevel() 

    /**
     * Method hasPlacedAgentFK
     */
    public boolean hasPlacedAgentFK()
    {
        return this._has_placedAgentFK;
    } //-- boolean hasPlacedAgentFK() 

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
     * Method setAdvanceAmountSets the value of field
     * 'advanceAmount'.
     * 
     * @param advanceAmount the value of field 'advanceAmount'.
     */
    public void setAdvanceAmount(java.math.BigDecimal advanceAmount)
    {
        this._advanceAmount = advanceAmount;
        
        super.setVoChanged(true);
    } //-- void setAdvanceAmount(java.math.BigDecimal) 

    /**
     * Method setAdvancePercentSets the value of field
     * 'advancePercent'.
     * 
     * @param advancePercent the value of field 'advancePercent'.
     */
    public void setAdvancePercent(java.math.BigDecimal advancePercent)
    {
        this._advancePercent = advancePercent;
        
        super.setVoChanged(true);
    } //-- void setAdvancePercent(java.math.BigDecimal) 

    /**
     * Method setAdvanceRecoverySets the value of field
     * 'advanceRecovery'.
     * 
     * @param advanceRecovery the value of field 'advanceRecovery'.
     */
    public void setAdvanceRecovery(java.math.BigDecimal advanceRecovery)
    {
        this._advanceRecovery = advanceRecovery;
        
        super.setVoChanged(true);
    } //-- void setAdvanceRecovery(java.math.BigDecimal) 

    /**
     * Method setAgentHierarchyFKSets the value of field
     * 'agentHierarchyFK'.
     * 
     * @param agentHierarchyFK the value of field 'agentHierarchyFK'
     */
    public void setAgentHierarchyFK(long agentHierarchyFK)
    {
        this._agentHierarchyFK = agentHierarchyFK;
        
        super.setVoChanged(true);
        this._has_agentHierarchyFK = true;
    } //-- void setAgentHierarchyFK(long) 

    /**
     * Method setAgentSnapshotPKSets the value of field
     * 'agentSnapshotPK'.
     * 
     * @param agentSnapshotPK the value of field 'agentSnapshotPK'.
     */
    public void setAgentSnapshotPK(long agentSnapshotPK)
    {
        this._agentSnapshotPK = agentSnapshotPK;
        
        super.setVoChanged(true);
        this._has_agentSnapshotPK = true;
    } //-- void setAgentSnapshotPK(long) 

    /**
     * Method setCommHoldAmountOverrideSets the value of field
     * 'commHoldAmountOverride'.
     * 
     * @param commHoldAmountOverride the value of field
     * 'commHoldAmountOverride'.
     */
    public void setCommHoldAmountOverride(java.math.BigDecimal commHoldAmountOverride)
    {
        this._commHoldAmountOverride = commHoldAmountOverride;
        
        super.setVoChanged(true);
    } //-- void setCommHoldAmountOverride(java.math.BigDecimal) 

    /**
     * Method setCommHoldReleaseDateOverrideSets the value of field
     * 'commHoldReleaseDateOverride'.
     * 
     * @param commHoldReleaseDateOverride the value of field
     * 'commHoldReleaseDateOverride'.
     */
    public void setCommHoldReleaseDateOverride(java.lang.String commHoldReleaseDateOverride)
    {
        this._commHoldReleaseDateOverride = commHoldReleaseDateOverride;
        
        super.setVoChanged(true);
    } //-- void setCommHoldReleaseDateOverride(java.lang.String) 

    /**
     * Method setCommissionOverrideAmountSets the value of field
     * 'commissionOverrideAmount'.
     * 
     * @param commissionOverrideAmount the value of field
     * 'commissionOverrideAmount'.
     */
    public void setCommissionOverrideAmount(java.math.BigDecimal commissionOverrideAmount)
    {
        this._commissionOverrideAmount = commissionOverrideAmount;
        
        super.setVoChanged(true);
    } //-- void setCommissionOverrideAmount(java.math.BigDecimal) 

    /**
     * Method setCommissionOverridePercentSets the value of field
     * 'commissionOverridePercent'.
     * 
     * @param commissionOverridePercent the value of field
     * 'commissionOverridePercent'.
     */
    public void setCommissionOverridePercent(java.math.BigDecimal commissionOverridePercent)
    {
        this._commissionOverridePercent = commissionOverridePercent;
        
        super.setVoChanged(true);
    } //-- void setCommissionOverridePercent(java.math.BigDecimal) 

    /**
     * Method setCommissionProfileFKSets the value of field
     * 'commissionProfileFK'.
     * 
     * @param commissionProfileFK the value of field
     * 'commissionProfileFK'.
     */
    public void setCommissionProfileFK(long commissionProfileFK)
    {
        this._commissionProfileFK = commissionProfileFK;
        
        super.setVoChanged(true);
        this._has_commissionProfileFK = true;
    } //-- void setCommissionProfileFK(long) 

    /**
     * Method setHierarchyLevelSets the value of field
     * 'hierarchyLevel'.
     * 
     * @param hierarchyLevel the value of field 'hierarchyLevel'.
     */
    public void setHierarchyLevel(int hierarchyLevel)
    {
        this._hierarchyLevel = hierarchyLevel;
        
        super.setVoChanged(true);
        this._has_hierarchyLevel = true;
    } //-- void setHierarchyLevel(int) 

    /**
     * Method setPlacedAgentFKSets the value of field
     * 'placedAgentFK'.
     * 
     * @param placedAgentFK the value of field 'placedAgentFK'.
     */
    public void setPlacedAgentFK(long placedAgentFK)
    {
        this._placedAgentFK = placedAgentFK;
        
        super.setVoChanged(true);
        this._has_placedAgentFK = true;
    } //-- void setPlacedAgentFK(long) 

    /**
     * Method setRecoveryPercentSets the value of field
     * 'recoveryPercent'.
     * 
     * @param recoveryPercent the value of field 'recoveryPercent'.
     */
    public void setRecoveryPercent(java.math.BigDecimal recoveryPercent)
    {
        this._recoveryPercent = recoveryPercent;
        
        super.setVoChanged(true);
    } //-- void setRecoveryPercent(java.math.BigDecimal) 

    /**
     * Method setServicingAgentIndicatorSets the value of field
     * 'servicingAgentIndicator'.
     * 
     * @param servicingAgentIndicator the value of field
     * 'servicingAgentIndicator'.
     */
    public void setServicingAgentIndicator(java.lang.String servicingAgentIndicator)
    {
        this._servicingAgentIndicator = servicingAgentIndicator;
        
        super.setVoChanged(true);
    } //-- void setServicingAgentIndicator(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentSnapshotVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentSnapshotVO) Unmarshaller.unmarshal(edit.common.vo.AgentSnapshotVO.class, reader);
    } //-- edit.common.vo.AgentSnapshotVO unmarshal(java.io.Reader) 

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
