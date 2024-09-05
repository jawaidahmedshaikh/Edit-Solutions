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
 * Class PlacedAgentVO.
 * 
 * @version $Revision$ $Date$
 */
public class PlacedAgentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _placedAgentPK
     */
    private long _placedAgentPK;

    /**
     * keeps track of state for field: _placedAgentPK
     */
    private boolean _has_placedAgentPK;

    /**
     * Field _agentContractFK
     */
    private long _agentContractFK;

    /**
     * keeps track of state for field: _agentContractFK
     */
    private boolean _has_agentContractFK;

    /**
     * Field _hierarchyLevel
     */
    private int _hierarchyLevel;

    /**
     * keeps track of state for field: _hierarchyLevel
     */
    private boolean _has_hierarchyLevel;

    /**
     * Field _leftBoundary
     */
    private long _leftBoundary;

    /**
     * keeps track of state for field: _leftBoundary
     */
    private boolean _has_leftBoundary;

    /**
     * Field _rightBoundary
     */
    private long _rightBoundary;

    /**
     * keeps track of state for field: _rightBoundary
     */
    private boolean _has_rightBoundary;

    /**
     * Field _modifyingEvent
     */
    private java.lang.String _modifyingEvent;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _inactiveIndicator
     */
    private java.lang.String _inactiveIndicator;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _situationCode
     */
    private java.lang.String _situationCode;

    /**
     * Field _stopDateReasonCT
     */
    private java.lang.String _stopDateReasonCT;

    /**
     * Field _clientRoleFK
     */
    private long _clientRoleFK;

    /**
     * keeps track of state for field: _clientRoleFK
     */
    private boolean _has_clientRoleFK;

    /**
     * Field _agentSnapshotVOList
     */
    private java.util.Vector _agentSnapshotVOList;

    /**
     * Field _commissionHistoryVOList
     */
    private java.util.Vector _commissionHistoryVOList;

    /**
     * Field _participatingAgentVOList
     */
    private java.util.Vector _participatingAgentVOList;

    /**
     * Field _placedAgentCommissionProfileVOList
     */
    private java.util.Vector _placedAgentCommissionProfileVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PlacedAgentVO() {
        super();
        _agentSnapshotVOList = new Vector();
        _commissionHistoryVOList = new Vector();
        _participatingAgentVOList = new Vector();
        _placedAgentCommissionProfileVOList = new Vector();
    } //-- edit.common.vo.PlacedAgentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentSnapshotVO
     * 
     * @param vAgentSnapshotVO
     */
    public void addAgentSnapshotVO(edit.common.vo.AgentSnapshotVO vAgentSnapshotVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentSnapshotVO.setParentVO(this.getClass(), this);
        _agentSnapshotVOList.addElement(vAgentSnapshotVO);
    } //-- void addAgentSnapshotVO(edit.common.vo.AgentSnapshotVO) 

    /**
     * Method addAgentSnapshotVO
     * 
     * @param index
     * @param vAgentSnapshotVO
     */
    public void addAgentSnapshotVO(int index, edit.common.vo.AgentSnapshotVO vAgentSnapshotVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentSnapshotVO.setParentVO(this.getClass(), this);
        _agentSnapshotVOList.insertElementAt(vAgentSnapshotVO, index);
    } //-- void addAgentSnapshotVO(int, edit.common.vo.AgentSnapshotVO) 

    /**
     * Method addCommissionHistoryVO
     * 
     * @param vCommissionHistoryVO
     */
    public void addCommissionHistoryVO(edit.common.vo.CommissionHistoryVO vCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionHistoryVO.setParentVO(this.getClass(), this);
        _commissionHistoryVOList.addElement(vCommissionHistoryVO);
    } //-- void addCommissionHistoryVO(edit.common.vo.CommissionHistoryVO) 

    /**
     * Method addCommissionHistoryVO
     * 
     * @param index
     * @param vCommissionHistoryVO
     */
    public void addCommissionHistoryVO(int index, edit.common.vo.CommissionHistoryVO vCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vCommissionHistoryVO.setParentVO(this.getClass(), this);
        _commissionHistoryVOList.insertElementAt(vCommissionHistoryVO, index);
    } //-- void addCommissionHistoryVO(int, edit.common.vo.CommissionHistoryVO) 

    /**
     * Method addParticipatingAgentVO
     * 
     * @param vParticipatingAgentVO
     */
    public void addParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO vParticipatingAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vParticipatingAgentVO.setParentVO(this.getClass(), this);
        _participatingAgentVOList.addElement(vParticipatingAgentVO);
    } //-- void addParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method addParticipatingAgentVO
     * 
     * @param index
     * @param vParticipatingAgentVO
     */
    public void addParticipatingAgentVO(int index, edit.common.vo.ParticipatingAgentVO vParticipatingAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vParticipatingAgentVO.setParentVO(this.getClass(), this);
        _participatingAgentVOList.insertElementAt(vParticipatingAgentVO, index);
    } //-- void addParticipatingAgentVO(int, edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method addPlacedAgentCommissionProfileVO
     * 
     * @param vPlacedAgentCommissionProfileVO
     */
    public void addPlacedAgentCommissionProfileVO(edit.common.vo.PlacedAgentCommissionProfileVO vPlacedAgentCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentCommissionProfileVO.setParentVO(this.getClass(), this);
        _placedAgentCommissionProfileVOList.addElement(vPlacedAgentCommissionProfileVO);
    } //-- void addPlacedAgentCommissionProfileVO(edit.common.vo.PlacedAgentCommissionProfileVO) 

    /**
     * Method addPlacedAgentCommissionProfileVO
     * 
     * @param index
     * @param vPlacedAgentCommissionProfileVO
     */
    public void addPlacedAgentCommissionProfileVO(int index, edit.common.vo.PlacedAgentCommissionProfileVO vPlacedAgentCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentCommissionProfileVO.setParentVO(this.getClass(), this);
        _placedAgentCommissionProfileVOList.insertElementAt(vPlacedAgentCommissionProfileVO, index);
    } //-- void addPlacedAgentCommissionProfileVO(int, edit.common.vo.PlacedAgentCommissionProfileVO) 

    /**
     * Method enumerateAgentSnapshotVO
     */
    public java.util.Enumeration enumerateAgentSnapshotVO()
    {
        return _agentSnapshotVOList.elements();
    } //-- java.util.Enumeration enumerateAgentSnapshotVO() 

    /**
     * Method enumerateCommissionHistoryVO
     */
    public java.util.Enumeration enumerateCommissionHistoryVO()
    {
        return _commissionHistoryVOList.elements();
    } //-- java.util.Enumeration enumerateCommissionHistoryVO() 

    /**
     * Method enumerateParticipatingAgentVO
     */
    public java.util.Enumeration enumerateParticipatingAgentVO()
    {
        return _participatingAgentVOList.elements();
    } //-- java.util.Enumeration enumerateParticipatingAgentVO() 

    /**
     * Method enumeratePlacedAgentCommissionProfileVO
     */
    public java.util.Enumeration enumeratePlacedAgentCommissionProfileVO()
    {
        return _placedAgentCommissionProfileVOList.elements();
    } //-- java.util.Enumeration enumeratePlacedAgentCommissionProfileVO() 

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
        
        if (obj instanceof PlacedAgentVO) {
        
            PlacedAgentVO temp = (PlacedAgentVO)obj;
            if (this._placedAgentPK != temp._placedAgentPK)
                return false;
            if (this._has_placedAgentPK != temp._has_placedAgentPK)
                return false;
            if (this._agentContractFK != temp._agentContractFK)
                return false;
            if (this._has_agentContractFK != temp._has_agentContractFK)
                return false;
            if (this._hierarchyLevel != temp._hierarchyLevel)
                return false;
            if (this._has_hierarchyLevel != temp._has_hierarchyLevel)
                return false;
            if (this._leftBoundary != temp._leftBoundary)
                return false;
            if (this._has_leftBoundary != temp._has_leftBoundary)
                return false;
            if (this._rightBoundary != temp._rightBoundary)
                return false;
            if (this._has_rightBoundary != temp._has_rightBoundary)
                return false;
            if (this._modifyingEvent != null) {
                if (temp._modifyingEvent == null) return false;
                else if (!(this._modifyingEvent.equals(temp._modifyingEvent))) 
                    return false;
            }
            else if (temp._modifyingEvent != null)
                return false;
            if (this._operator != null) {
                if (temp._operator == null) return false;
                else if (!(this._operator.equals(temp._operator))) 
                    return false;
            }
            else if (temp._operator != null)
                return false;
            if (this._maintDateTime != null) {
                if (temp._maintDateTime == null) return false;
                else if (!(this._maintDateTime.equals(temp._maintDateTime))) 
                    return false;
            }
            else if (temp._maintDateTime != null)
                return false;
            if (this._inactiveIndicator != null) {
                if (temp._inactiveIndicator == null) return false;
                else if (!(this._inactiveIndicator.equals(temp._inactiveIndicator))) 
                    return false;
            }
            else if (temp._inactiveIndicator != null)
                return false;
            if (this._startDate != null) {
                if (temp._startDate == null) return false;
                else if (!(this._startDate.equals(temp._startDate))) 
                    return false;
            }
            else if (temp._startDate != null)
                return false;
            if (this._stopDate != null) {
                if (temp._stopDate == null) return false;
                else if (!(this._stopDate.equals(temp._stopDate))) 
                    return false;
            }
            else if (temp._stopDate != null)
                return false;
            if (this._situationCode != null) {
                if (temp._situationCode == null) return false;
                else if (!(this._situationCode.equals(temp._situationCode))) 
                    return false;
            }
            else if (temp._situationCode != null)
                return false;
            if (this._stopDateReasonCT != null) {
                if (temp._stopDateReasonCT == null) return false;
                else if (!(this._stopDateReasonCT.equals(temp._stopDateReasonCT))) 
                    return false;
            }
            else if (temp._stopDateReasonCT != null)
                return false;
            if (this._clientRoleFK != temp._clientRoleFK)
                return false;
            if (this._has_clientRoleFK != temp._has_clientRoleFK)
                return false;
            if (this._agentSnapshotVOList != null) {
                if (temp._agentSnapshotVOList == null) return false;
                else if (!(this._agentSnapshotVOList.equals(temp._agentSnapshotVOList))) 
                    return false;
            }
            else if (temp._agentSnapshotVOList != null)
                return false;
            if (this._commissionHistoryVOList != null) {
                if (temp._commissionHistoryVOList == null) return false;
                else if (!(this._commissionHistoryVOList.equals(temp._commissionHistoryVOList))) 
                    return false;
            }
            else if (temp._commissionHistoryVOList != null)
                return false;
            if (this._participatingAgentVOList != null) {
                if (temp._participatingAgentVOList == null) return false;
                else if (!(this._participatingAgentVOList.equals(temp._participatingAgentVOList))) 
                    return false;
            }
            else if (temp._participatingAgentVOList != null)
                return false;
            if (this._placedAgentCommissionProfileVOList != null) {
                if (temp._placedAgentCommissionProfileVOList == null) return false;
                else if (!(this._placedAgentCommissionProfileVOList.equals(temp._placedAgentCommissionProfileVOList))) 
                    return false;
            }
            else if (temp._placedAgentCommissionProfileVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentContractFKReturns the value of field
     * 'agentContractFK'.
     * 
     * @return the value of field 'agentContractFK'.
     */
    public long getAgentContractFK()
    {
        return this._agentContractFK;
    } //-- long getAgentContractFK() 

    /**
     * Method getAgentSnapshotVO
     * 
     * @param index
     */
    public edit.common.vo.AgentSnapshotVO getAgentSnapshotVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentSnapshotVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentSnapshotVO) _agentSnapshotVOList.elementAt(index);
    } //-- edit.common.vo.AgentSnapshotVO getAgentSnapshotVO(int) 

    /**
     * Method getAgentSnapshotVO
     */
    public edit.common.vo.AgentSnapshotVO[] getAgentSnapshotVO()
    {
        int size = _agentSnapshotVOList.size();
        edit.common.vo.AgentSnapshotVO[] mArray = new edit.common.vo.AgentSnapshotVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentSnapshotVO) _agentSnapshotVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentSnapshotVO[] getAgentSnapshotVO() 

    /**
     * Method getAgentSnapshotVOCount
     */
    public int getAgentSnapshotVOCount()
    {
        return _agentSnapshotVOList.size();
    } //-- int getAgentSnapshotVOCount() 

    /**
     * Method getClientRoleFKReturns the value of field
     * 'clientRoleFK'.
     * 
     * @return the value of field 'clientRoleFK'.
     */
    public long getClientRoleFK()
    {
        return this._clientRoleFK;
    } //-- long getClientRoleFK() 

    /**
     * Method getCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionHistoryVO getCommissionHistoryVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.CommissionHistoryVO) _commissionHistoryVOList.elementAt(index);
    } //-- edit.common.vo.CommissionHistoryVO getCommissionHistoryVO(int) 

    /**
     * Method getCommissionHistoryVO
     */
    public edit.common.vo.CommissionHistoryVO[] getCommissionHistoryVO()
    {
        int size = _commissionHistoryVOList.size();
        edit.common.vo.CommissionHistoryVO[] mArray = new edit.common.vo.CommissionHistoryVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.CommissionHistoryVO) _commissionHistoryVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.CommissionHistoryVO[] getCommissionHistoryVO() 

    /**
     * Method getCommissionHistoryVOCount
     */
    public int getCommissionHistoryVOCount()
    {
        return _commissionHistoryVOList.size();
    } //-- int getCommissionHistoryVOCount() 

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
     * Method getInactiveIndicatorReturns the value of field
     * 'inactiveIndicator'.
     * 
     * @return the value of field 'inactiveIndicator'.
     */
    public java.lang.String getInactiveIndicator()
    {
        return this._inactiveIndicator;
    } //-- java.lang.String getInactiveIndicator() 

    /**
     * Method getLeftBoundaryReturns the value of field
     * 'leftBoundary'.
     * 
     * @return the value of field 'leftBoundary'.
     */
    public long getLeftBoundary()
    {
        return this._leftBoundary;
    } //-- long getLeftBoundary() 

    /**
     * Method getMaintDateTimeReturns the value of field
     * 'maintDateTime'.
     * 
     * @return the value of field 'maintDateTime'.
     */
    public java.lang.String getMaintDateTime()
    {
        return this._maintDateTime;
    } //-- java.lang.String getMaintDateTime() 

    /**
     * Method getModifyingEventReturns the value of field
     * 'modifyingEvent'.
     * 
     * @return the value of field 'modifyingEvent'.
     */
    public java.lang.String getModifyingEvent()
    {
        return this._modifyingEvent;
    } //-- java.lang.String getModifyingEvent() 

    /**
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getParticipatingAgentVO
     * 
     * @param index
     */
    public edit.common.vo.ParticipatingAgentVO getParticipatingAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _participatingAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ParticipatingAgentVO) _participatingAgentVOList.elementAt(index);
    } //-- edit.common.vo.ParticipatingAgentVO getParticipatingAgentVO(int) 

    /**
     * Method getParticipatingAgentVO
     */
    public edit.common.vo.ParticipatingAgentVO[] getParticipatingAgentVO()
    {
        int size = _participatingAgentVOList.size();
        edit.common.vo.ParticipatingAgentVO[] mArray = new edit.common.vo.ParticipatingAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ParticipatingAgentVO) _participatingAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ParticipatingAgentVO[] getParticipatingAgentVO() 

    /**
     * Method getParticipatingAgentVOCount
     */
    public int getParticipatingAgentVOCount()
    {
        return _participatingAgentVOList.size();
    } //-- int getParticipatingAgentVOCount() 

    /**
     * Method getPlacedAgentCommissionProfileVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentCommissionProfileVO getPlacedAgentCommissionProfileVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentCommissionProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PlacedAgentCommissionProfileVO) _placedAgentCommissionProfileVOList.elementAt(index);
    } //-- edit.common.vo.PlacedAgentCommissionProfileVO getPlacedAgentCommissionProfileVO(int) 

    /**
     * Method getPlacedAgentCommissionProfileVO
     */
    public edit.common.vo.PlacedAgentCommissionProfileVO[] getPlacedAgentCommissionProfileVO()
    {
        int size = _placedAgentCommissionProfileVOList.size();
        edit.common.vo.PlacedAgentCommissionProfileVO[] mArray = new edit.common.vo.PlacedAgentCommissionProfileVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PlacedAgentCommissionProfileVO) _placedAgentCommissionProfileVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PlacedAgentCommissionProfileVO[] getPlacedAgentCommissionProfileVO() 

    /**
     * Method getPlacedAgentCommissionProfileVOCount
     */
    public int getPlacedAgentCommissionProfileVOCount()
    {
        return _placedAgentCommissionProfileVOList.size();
    } //-- int getPlacedAgentCommissionProfileVOCount() 

    /**
     * Method getPlacedAgentPKReturns the value of field
     * 'placedAgentPK'.
     * 
     * @return the value of field 'placedAgentPK'.
     */
    public long getPlacedAgentPK()
    {
        return this._placedAgentPK;
    } //-- long getPlacedAgentPK() 

    /**
     * Method getRightBoundaryReturns the value of field
     * 'rightBoundary'.
     * 
     * @return the value of field 'rightBoundary'.
     */
    public long getRightBoundary()
    {
        return this._rightBoundary;
    } //-- long getRightBoundary() 

    /**
     * Method getSituationCodeReturns the value of field
     * 'situationCode'.
     * 
     * @return the value of field 'situationCode'.
     */
    public java.lang.String getSituationCode()
    {
        return this._situationCode;
    } //-- java.lang.String getSituationCode() 

    /**
     * Method getStartDateReturns the value of field 'startDate'.
     * 
     * @return the value of field 'startDate'.
     */
    public java.lang.String getStartDate()
    {
        return this._startDate;
    } //-- java.lang.String getStartDate() 

    /**
     * Method getStopDateReturns the value of field 'stopDate'.
     * 
     * @return the value of field 'stopDate'.
     */
    public java.lang.String getStopDate()
    {
        return this._stopDate;
    } //-- java.lang.String getStopDate() 

    /**
     * Method getStopDateReasonCTReturns the value of field
     * 'stopDateReasonCT'.
     * 
     * @return the value of field 'stopDateReasonCT'.
     */
    public java.lang.String getStopDateReasonCT()
    {
        return this._stopDateReasonCT;
    } //-- java.lang.String getStopDateReasonCT() 

    /**
     * Method hasAgentContractFK
     */
    public boolean hasAgentContractFK()
    {
        return this._has_agentContractFK;
    } //-- boolean hasAgentContractFK() 

    /**
     * Method hasClientRoleFK
     */
    public boolean hasClientRoleFK()
    {
        return this._has_clientRoleFK;
    } //-- boolean hasClientRoleFK() 

    /**
     * Method hasHierarchyLevel
     */
    public boolean hasHierarchyLevel()
    {
        return this._has_hierarchyLevel;
    } //-- boolean hasHierarchyLevel() 

    /**
     * Method hasLeftBoundary
     */
    public boolean hasLeftBoundary()
    {
        return this._has_leftBoundary;
    } //-- boolean hasLeftBoundary() 

    /**
     * Method hasPlacedAgentPK
     */
    public boolean hasPlacedAgentPK()
    {
        return this._has_placedAgentPK;
    } //-- boolean hasPlacedAgentPK() 

    /**
     * Method hasRightBoundary
     */
    public boolean hasRightBoundary()
    {
        return this._has_rightBoundary;
    } //-- boolean hasRightBoundary() 

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
     * Method removeAgentSnapshotVO
     * 
     * @param index
     */
    public edit.common.vo.AgentSnapshotVO removeAgentSnapshotVO(int index)
    {
        java.lang.Object obj = _agentSnapshotVOList.elementAt(index);
        _agentSnapshotVOList.removeElementAt(index);
        return (edit.common.vo.AgentSnapshotVO) obj;
    } //-- edit.common.vo.AgentSnapshotVO removeAgentSnapshotVO(int) 

    /**
     * Method removeAllAgentSnapshotVO
     */
    public void removeAllAgentSnapshotVO()
    {
        _agentSnapshotVOList.removeAllElements();
    } //-- void removeAllAgentSnapshotVO() 

    /**
     * Method removeAllCommissionHistoryVO
     */
    public void removeAllCommissionHistoryVO()
    {
        _commissionHistoryVOList.removeAllElements();
    } //-- void removeAllCommissionHistoryVO() 

    /**
     * Method removeAllParticipatingAgentVO
     */
    public void removeAllParticipatingAgentVO()
    {
        _participatingAgentVOList.removeAllElements();
    } //-- void removeAllParticipatingAgentVO() 

    /**
     * Method removeAllPlacedAgentCommissionProfileVO
     */
    public void removeAllPlacedAgentCommissionProfileVO()
    {
        _placedAgentCommissionProfileVOList.removeAllElements();
    } //-- void removeAllPlacedAgentCommissionProfileVO() 

    /**
     * Method removeCommissionHistoryVO
     * 
     * @param index
     */
    public edit.common.vo.CommissionHistoryVO removeCommissionHistoryVO(int index)
    {
        java.lang.Object obj = _commissionHistoryVOList.elementAt(index);
        _commissionHistoryVOList.removeElementAt(index);
        return (edit.common.vo.CommissionHistoryVO) obj;
    } //-- edit.common.vo.CommissionHistoryVO removeCommissionHistoryVO(int) 

    /**
     * Method removeParticipatingAgentVO
     * 
     * @param index
     */
    public edit.common.vo.ParticipatingAgentVO removeParticipatingAgentVO(int index)
    {
        java.lang.Object obj = _participatingAgentVOList.elementAt(index);
        _participatingAgentVOList.removeElementAt(index);
        return (edit.common.vo.ParticipatingAgentVO) obj;
    } //-- edit.common.vo.ParticipatingAgentVO removeParticipatingAgentVO(int) 

    /**
     * Method removePlacedAgentCommissionProfileVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentCommissionProfileVO removePlacedAgentCommissionProfileVO(int index)
    {
        java.lang.Object obj = _placedAgentCommissionProfileVOList.elementAt(index);
        _placedAgentCommissionProfileVOList.removeElementAt(index);
        return (edit.common.vo.PlacedAgentCommissionProfileVO) obj;
    } //-- edit.common.vo.PlacedAgentCommissionProfileVO removePlacedAgentCommissionProfileVO(int) 

    /**
     * Method setAgentContractFKSets the value of field
     * 'agentContractFK'.
     * 
     * @param agentContractFK the value of field 'agentContractFK'.
     */
    public void setAgentContractFK(long agentContractFK)
    {
        this._agentContractFK = agentContractFK;
        
        super.setVoChanged(true);
        this._has_agentContractFK = true;
    } //-- void setAgentContractFK(long) 

    /**
     * Method setAgentSnapshotVO
     * 
     * @param index
     * @param vAgentSnapshotVO
     */
    public void setAgentSnapshotVO(int index, edit.common.vo.AgentSnapshotVO vAgentSnapshotVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentSnapshotVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentSnapshotVO.setParentVO(this.getClass(), this);
        _agentSnapshotVOList.setElementAt(vAgentSnapshotVO, index);
    } //-- void setAgentSnapshotVO(int, edit.common.vo.AgentSnapshotVO) 

    /**
     * Method setAgentSnapshotVO
     * 
     * @param agentSnapshotVOArray
     */
    public void setAgentSnapshotVO(edit.common.vo.AgentSnapshotVO[] agentSnapshotVOArray)
    {
        //-- copy array
        _agentSnapshotVOList.removeAllElements();
        for (int i = 0; i < agentSnapshotVOArray.length; i++) {
            agentSnapshotVOArray[i].setParentVO(this.getClass(), this);
            _agentSnapshotVOList.addElement(agentSnapshotVOArray[i]);
        }
    } //-- void setAgentSnapshotVO(edit.common.vo.AgentSnapshotVO) 

    /**
     * Method setClientRoleFKSets the value of field
     * 'clientRoleFK'.
     * 
     * @param clientRoleFK the value of field 'clientRoleFK'.
     */
    public void setClientRoleFK(long clientRoleFK)
    {
        this._clientRoleFK = clientRoleFK;
        
        super.setVoChanged(true);
        this._has_clientRoleFK = true;
    } //-- void setClientRoleFK(long) 

    /**
     * Method setCommissionHistoryVO
     * 
     * @param index
     * @param vCommissionHistoryVO
     */
    public void setCommissionHistoryVO(int index, edit.common.vo.CommissionHistoryVO vCommissionHistoryVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _commissionHistoryVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vCommissionHistoryVO.setParentVO(this.getClass(), this);
        _commissionHistoryVOList.setElementAt(vCommissionHistoryVO, index);
    } //-- void setCommissionHistoryVO(int, edit.common.vo.CommissionHistoryVO) 

    /**
     * Method setCommissionHistoryVO
     * 
     * @param commissionHistoryVOArray
     */
    public void setCommissionHistoryVO(edit.common.vo.CommissionHistoryVO[] commissionHistoryVOArray)
    {
        //-- copy array
        _commissionHistoryVOList.removeAllElements();
        for (int i = 0; i < commissionHistoryVOArray.length; i++) {
            commissionHistoryVOArray[i].setParentVO(this.getClass(), this);
            _commissionHistoryVOList.addElement(commissionHistoryVOArray[i]);
        }
    } //-- void setCommissionHistoryVO(edit.common.vo.CommissionHistoryVO) 

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
     * Method setInactiveIndicatorSets the value of field
     * 'inactiveIndicator'.
     * 
     * @param inactiveIndicator the value of field
     * 'inactiveIndicator'.
     */
    public void setInactiveIndicator(java.lang.String inactiveIndicator)
    {
        this._inactiveIndicator = inactiveIndicator;
        
        super.setVoChanged(true);
    } //-- void setInactiveIndicator(java.lang.String) 

    /**
     * Method setLeftBoundarySets the value of field
     * 'leftBoundary'.
     * 
     * @param leftBoundary the value of field 'leftBoundary'.
     */
    public void setLeftBoundary(long leftBoundary)
    {
        this._leftBoundary = leftBoundary;
        
        super.setVoChanged(true);
        this._has_leftBoundary = true;
    } //-- void setLeftBoundary(long) 

    /**
     * Method setMaintDateTimeSets the value of field
     * 'maintDateTime'.
     * 
     * @param maintDateTime the value of field 'maintDateTime'.
     */
    public void setMaintDateTime(java.lang.String maintDateTime)
    {
        this._maintDateTime = maintDateTime;
        
        super.setVoChanged(true);
    } //-- void setMaintDateTime(java.lang.String) 

    /**
     * Method setModifyingEventSets the value of field
     * 'modifyingEvent'.
     * 
     * @param modifyingEvent the value of field 'modifyingEvent'.
     */
    public void setModifyingEvent(java.lang.String modifyingEvent)
    {
        this._modifyingEvent = modifyingEvent;
        
        super.setVoChanged(true);
    } //-- void setModifyingEvent(java.lang.String) 

    /**
     * Method setOperatorSets the value of field 'operator'.
     * 
     * @param operator the value of field 'operator'.
     */
    public void setOperator(java.lang.String operator)
    {
        this._operator = operator;
        
        super.setVoChanged(true);
    } //-- void setOperator(java.lang.String) 

    /**
     * Method setParticipatingAgentVO
     * 
     * @param index
     * @param vParticipatingAgentVO
     */
    public void setParticipatingAgentVO(int index, edit.common.vo.ParticipatingAgentVO vParticipatingAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _participatingAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vParticipatingAgentVO.setParentVO(this.getClass(), this);
        _participatingAgentVOList.setElementAt(vParticipatingAgentVO, index);
    } //-- void setParticipatingAgentVO(int, edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method setParticipatingAgentVO
     * 
     * @param participatingAgentVOArray
     */
    public void setParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO[] participatingAgentVOArray)
    {
        //-- copy array
        _participatingAgentVOList.removeAllElements();
        for (int i = 0; i < participatingAgentVOArray.length; i++) {
            participatingAgentVOArray[i].setParentVO(this.getClass(), this);
            _participatingAgentVOList.addElement(participatingAgentVOArray[i]);
        }
    } //-- void setParticipatingAgentVO(edit.common.vo.ParticipatingAgentVO) 

    /**
     * Method setPlacedAgentCommissionProfileVO
     * 
     * @param index
     * @param vPlacedAgentCommissionProfileVO
     */
    public void setPlacedAgentCommissionProfileVO(int index, edit.common.vo.PlacedAgentCommissionProfileVO vPlacedAgentCommissionProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentCommissionProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPlacedAgentCommissionProfileVO.setParentVO(this.getClass(), this);
        _placedAgentCommissionProfileVOList.setElementAt(vPlacedAgentCommissionProfileVO, index);
    } //-- void setPlacedAgentCommissionProfileVO(int, edit.common.vo.PlacedAgentCommissionProfileVO) 

    /**
     * Method setPlacedAgentCommissionProfileVO
     * 
     * @param placedAgentCommissionProfileVOArray
     */
    public void setPlacedAgentCommissionProfileVO(edit.common.vo.PlacedAgentCommissionProfileVO[] placedAgentCommissionProfileVOArray)
    {
        //-- copy array
        _placedAgentCommissionProfileVOList.removeAllElements();
        for (int i = 0; i < placedAgentCommissionProfileVOArray.length; i++) {
            placedAgentCommissionProfileVOArray[i].setParentVO(this.getClass(), this);
            _placedAgentCommissionProfileVOList.addElement(placedAgentCommissionProfileVOArray[i]);
        }
    } //-- void setPlacedAgentCommissionProfileVO(edit.common.vo.PlacedAgentCommissionProfileVO) 

    /**
     * Method setPlacedAgentPKSets the value of field
     * 'placedAgentPK'.
     * 
     * @param placedAgentPK the value of field 'placedAgentPK'.
     */
    public void setPlacedAgentPK(long placedAgentPK)
    {
        this._placedAgentPK = placedAgentPK;
        
        super.setVoChanged(true);
        this._has_placedAgentPK = true;
    } //-- void setPlacedAgentPK(long) 

    /**
     * Method setRightBoundarySets the value of field
     * 'rightBoundary'.
     * 
     * @param rightBoundary the value of field 'rightBoundary'.
     */
    public void setRightBoundary(long rightBoundary)
    {
        this._rightBoundary = rightBoundary;
        
        super.setVoChanged(true);
        this._has_rightBoundary = true;
    } //-- void setRightBoundary(long) 

    /**
     * Method setSituationCodeSets the value of field
     * 'situationCode'.
     * 
     * @param situationCode the value of field 'situationCode'.
     */
    public void setSituationCode(java.lang.String situationCode)
    {
        this._situationCode = situationCode;
        
        super.setVoChanged(true);
    } //-- void setSituationCode(java.lang.String) 

    /**
     * Method setStartDateSets the value of field 'startDate'.
     * 
     * @param startDate the value of field 'startDate'.
     */
    public void setStartDate(java.lang.String startDate)
    {
        this._startDate = startDate;
        
        super.setVoChanged(true);
    } //-- void setStartDate(java.lang.String) 

    /**
     * Method setStopDateSets the value of field 'stopDate'.
     * 
     * @param stopDate the value of field 'stopDate'.
     */
    public void setStopDate(java.lang.String stopDate)
    {
        this._stopDate = stopDate;
        
        super.setVoChanged(true);
    } //-- void setStopDate(java.lang.String) 

    /**
     * Method setStopDateReasonCTSets the value of field
     * 'stopDateReasonCT'.
     * 
     * @param stopDateReasonCT the value of field 'stopDateReasonCT'
     */
    public void setStopDateReasonCT(java.lang.String stopDateReasonCT)
    {
        this._stopDateReasonCT = stopDateReasonCT;
        
        super.setVoChanged(true);
    } //-- void setStopDateReasonCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.PlacedAgentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.PlacedAgentVO) Unmarshaller.unmarshal(edit.common.vo.PlacedAgentVO.class, reader);
    } //-- edit.common.vo.PlacedAgentVO unmarshal(java.io.Reader) 

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
