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
 * Class CommissionProfileVO.
 * 
 * @version $Revision$ $Date$
 */
public class CommissionProfileVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _commissionProfilePK
     */
    private long _commissionProfilePK;

    /**
     * keeps track of state for field: _commissionProfilePK
     */
    private boolean _has_commissionProfilePK;

    /**
     * Field _trailStatus
     */
    private java.lang.String _trailStatus;

    /**
     * Field _contractCodeCT
     */
    private java.lang.String _contractCodeCT;

    /**
     * Field _commissionLevelCT
     */
    private java.lang.String _commissionLevelCT;

    /**
     * Field _commissionOptionCT
     */
    private java.lang.String _commissionOptionCT;

    /**
     * Field _contributingProfileVOList
     */
    private java.util.Vector _contributingProfileVOList;

    /**
     * Field _placedAgentCommissionProfileVOList
     */
    private java.util.Vector _placedAgentCommissionProfileVOList;

    /**
     * Field _agentSnapshotVOList
     */
    private java.util.Vector _agentSnapshotVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public CommissionProfileVO() {
        super();
        _contributingProfileVOList = new Vector();
        _placedAgentCommissionProfileVOList = new Vector();
        _agentSnapshotVOList = new Vector();
    } //-- edit.common.vo.CommissionProfileVO()


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
     * Method addContributingProfileVO
     * 
     * @param vContributingProfileVO
     */
    public void addContributingProfileVO(edit.common.vo.ContributingProfileVO vContributingProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContributingProfileVO.setParentVO(this.getClass(), this);
        _contributingProfileVOList.addElement(vContributingProfileVO);
    } //-- void addContributingProfileVO(edit.common.vo.ContributingProfileVO) 

    /**
     * Method addContributingProfileVO
     * 
     * @param index
     * @param vContributingProfileVO
     */
    public void addContributingProfileVO(int index, edit.common.vo.ContributingProfileVO vContributingProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vContributingProfileVO.setParentVO(this.getClass(), this);
        _contributingProfileVOList.insertElementAt(vContributingProfileVO, index);
    } //-- void addContributingProfileVO(int, edit.common.vo.ContributingProfileVO) 

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
     * Method enumerateContributingProfileVO
     */
    public java.util.Enumeration enumerateContributingProfileVO()
    {
        return _contributingProfileVOList.elements();
    } //-- java.util.Enumeration enumerateContributingProfileVO() 

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
        
        if (obj instanceof CommissionProfileVO) {
        
            CommissionProfileVO temp = (CommissionProfileVO)obj;
            if (this._commissionProfilePK != temp._commissionProfilePK)
                return false;
            if (this._has_commissionProfilePK != temp._has_commissionProfilePK)
                return false;
            if (this._trailStatus != null) {
                if (temp._trailStatus == null) return false;
                else if (!(this._trailStatus.equals(temp._trailStatus))) 
                    return false;
            }
            else if (temp._trailStatus != null)
                return false;
            if (this._contractCodeCT != null) {
                if (temp._contractCodeCT == null) return false;
                else if (!(this._contractCodeCT.equals(temp._contractCodeCT))) 
                    return false;
            }
            else if (temp._contractCodeCT != null)
                return false;
            if (this._commissionLevelCT != null) {
                if (temp._commissionLevelCT == null) return false;
                else if (!(this._commissionLevelCT.equals(temp._commissionLevelCT))) 
                    return false;
            }
            else if (temp._commissionLevelCT != null)
                return false;
            if (this._commissionOptionCT != null) {
                if (temp._commissionOptionCT == null) return false;
                else if (!(this._commissionOptionCT.equals(temp._commissionOptionCT))) 
                    return false;
            }
            else if (temp._commissionOptionCT != null)
                return false;
            if (this._contributingProfileVOList != null) {
                if (temp._contributingProfileVOList == null) return false;
                else if (!(this._contributingProfileVOList.equals(temp._contributingProfileVOList))) 
                    return false;
            }
            else if (temp._contributingProfileVOList != null)
                return false;
            if (this._placedAgentCommissionProfileVOList != null) {
                if (temp._placedAgentCommissionProfileVOList == null) return false;
                else if (!(this._placedAgentCommissionProfileVOList.equals(temp._placedAgentCommissionProfileVOList))) 
                    return false;
            }
            else if (temp._placedAgentCommissionProfileVOList != null)
                return false;
            if (this._agentSnapshotVOList != null) {
                if (temp._agentSnapshotVOList == null) return false;
                else if (!(this._agentSnapshotVOList.equals(temp._agentSnapshotVOList))) 
                    return false;
            }
            else if (temp._agentSnapshotVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getCommissionLevelCTReturns the value of field
     * 'commissionLevelCT'.
     * 
     * @return the value of field 'commissionLevelCT'.
     */
    public java.lang.String getCommissionLevelCT()
    {
        return this._commissionLevelCT;
    } //-- java.lang.String getCommissionLevelCT() 

    /**
     * Method getCommissionOptionCTReturns the value of field
     * 'commissionOptionCT'.
     * 
     * @return the value of field 'commissionOptionCT'.
     */
    public java.lang.String getCommissionOptionCT()
    {
        return this._commissionOptionCT;
    } //-- java.lang.String getCommissionOptionCT() 

    /**
     * Method getCommissionProfilePKReturns the value of field
     * 'commissionProfilePK'.
     * 
     * @return the value of field 'commissionProfilePK'.
     */
    public long getCommissionProfilePK()
    {
        return this._commissionProfilePK;
    } //-- long getCommissionProfilePK() 

    /**
     * Method getContractCodeCTReturns the value of field
     * 'contractCodeCT'.
     * 
     * @return the value of field 'contractCodeCT'.
     */
    public java.lang.String getContractCodeCT()
    {
        return this._contractCodeCT;
    } //-- java.lang.String getContractCodeCT() 

    /**
     * Method getContributingProfileVO
     * 
     * @param index
     */
    public edit.common.vo.ContributingProfileVO getContributingProfileVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contributingProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ContributingProfileVO) _contributingProfileVOList.elementAt(index);
    } //-- edit.common.vo.ContributingProfileVO getContributingProfileVO(int) 

    /**
     * Method getContributingProfileVO
     */
    public edit.common.vo.ContributingProfileVO[] getContributingProfileVO()
    {
        int size = _contributingProfileVOList.size();
        edit.common.vo.ContributingProfileVO[] mArray = new edit.common.vo.ContributingProfileVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ContributingProfileVO) _contributingProfileVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ContributingProfileVO[] getContributingProfileVO() 

    /**
     * Method getContributingProfileVOCount
     */
    public int getContributingProfileVOCount()
    {
        return _contributingProfileVOList.size();
    } //-- int getContributingProfileVOCount() 

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
     * Method getTrailStatusReturns the value of field
     * 'trailStatus'.
     * 
     * @return the value of field 'trailStatus'.
     */
    public java.lang.String getTrailStatus()
    {
        return this._trailStatus;
    } //-- java.lang.String getTrailStatus() 

    /**
     * Method hasCommissionProfilePK
     */
    public boolean hasCommissionProfilePK()
    {
        return this._has_commissionProfilePK;
    } //-- boolean hasCommissionProfilePK() 

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
     * Method removeAllContributingProfileVO
     */
    public void removeAllContributingProfileVO()
    {
        _contributingProfileVOList.removeAllElements();
    } //-- void removeAllContributingProfileVO() 

    /**
     * Method removeAllPlacedAgentCommissionProfileVO
     */
    public void removeAllPlacedAgentCommissionProfileVO()
    {
        _placedAgentCommissionProfileVOList.removeAllElements();
    } //-- void removeAllPlacedAgentCommissionProfileVO() 

    /**
     * Method removeContributingProfileVO
     * 
     * @param index
     */
    public edit.common.vo.ContributingProfileVO removeContributingProfileVO(int index)
    {
        java.lang.Object obj = _contributingProfileVOList.elementAt(index);
        _contributingProfileVOList.removeElementAt(index);
        return (edit.common.vo.ContributingProfileVO) obj;
    } //-- edit.common.vo.ContributingProfileVO removeContributingProfileVO(int) 

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
     * Method setCommissionLevelCTSets the value of field
     * 'commissionLevelCT'.
     * 
     * @param commissionLevelCT the value of field
     * 'commissionLevelCT'.
     */
    public void setCommissionLevelCT(java.lang.String commissionLevelCT)
    {
        this._commissionLevelCT = commissionLevelCT;
        
        super.setVoChanged(true);
    } //-- void setCommissionLevelCT(java.lang.String) 

    /**
     * Method setCommissionOptionCTSets the value of field
     * 'commissionOptionCT'.
     * 
     * @param commissionOptionCT the value of field
     * 'commissionOptionCT'.
     */
    public void setCommissionOptionCT(java.lang.String commissionOptionCT)
    {
        this._commissionOptionCT = commissionOptionCT;
        
        super.setVoChanged(true);
    } //-- void setCommissionOptionCT(java.lang.String) 

    /**
     * Method setCommissionProfilePKSets the value of field
     * 'commissionProfilePK'.
     * 
     * @param commissionProfilePK the value of field
     * 'commissionProfilePK'.
     */
    public void setCommissionProfilePK(long commissionProfilePK)
    {
        this._commissionProfilePK = commissionProfilePK;
        
        super.setVoChanged(true);
        this._has_commissionProfilePK = true;
    } //-- void setCommissionProfilePK(long) 

    /**
     * Method setContractCodeCTSets the value of field
     * 'contractCodeCT'.
     * 
     * @param contractCodeCT the value of field 'contractCodeCT'.
     */
    public void setContractCodeCT(java.lang.String contractCodeCT)
    {
        this._contractCodeCT = contractCodeCT;
        
        super.setVoChanged(true);
    } //-- void setContractCodeCT(java.lang.String) 

    /**
     * Method setContributingProfileVO
     * 
     * @param index
     * @param vContributingProfileVO
     */
    public void setContributingProfileVO(int index, edit.common.vo.ContributingProfileVO vContributingProfileVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _contributingProfileVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vContributingProfileVO.setParentVO(this.getClass(), this);
        _contributingProfileVOList.setElementAt(vContributingProfileVO, index);
    } //-- void setContributingProfileVO(int, edit.common.vo.ContributingProfileVO) 

    /**
     * Method setContributingProfileVO
     * 
     * @param contributingProfileVOArray
     */
    public void setContributingProfileVO(edit.common.vo.ContributingProfileVO[] contributingProfileVOArray)
    {
        //-- copy array
        _contributingProfileVOList.removeAllElements();
        for (int i = 0; i < contributingProfileVOArray.length; i++) {
            contributingProfileVOArray[i].setParentVO(this.getClass(), this);
            _contributingProfileVOList.addElement(contributingProfileVOArray[i]);
        }
    } //-- void setContributingProfileVO(edit.common.vo.ContributingProfileVO) 

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
     * Method setTrailStatusSets the value of field 'trailStatus'.
     * 
     * @param trailStatus the value of field 'trailStatus'.
     */
    public void setTrailStatus(java.lang.String trailStatus)
    {
        this._trailStatus = trailStatus;
        
        super.setVoChanged(true);
    } //-- void setTrailStatus(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.CommissionProfileVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.CommissionProfileVO) Unmarshaller.unmarshal(edit.common.vo.CommissionProfileVO.class, reader);
    } //-- edit.common.vo.CommissionProfileVO unmarshal(java.io.Reader) 

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
