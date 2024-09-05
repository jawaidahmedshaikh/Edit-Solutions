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
 * Class AgentContractVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentContractVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentContractPK
     */
    private long _agentContractPK;

    /**
     * keeps track of state for field: _agentContractPK
     */
    private boolean _has_agentContractPK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _contractEffectiveDate
     */
    private java.lang.String _contractEffectiveDate;

    /**
     * Field _contractStopDate
     */
    private java.lang.String _contractStopDate;

    /**
     * Field _commissionProcessCT
     */
    private java.lang.String _commissionProcessCT;

    /**
     * Field _contractCodeCT
     */
    private java.lang.String _contractCodeCT;

    /**
     * Field _additionalCompensationVOList
     */
    private java.util.Vector _additionalCompensationVOList;

    /**
     * Field _placedAgentVOList
     */
    private java.util.Vector _placedAgentVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentContractVO() {
        super();
        _additionalCompensationVOList = new Vector();
        _placedAgentVOList = new Vector();
    } //-- edit.common.vo.AgentContractVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAdditionalCompensationVO
     * 
     * @param vAdditionalCompensationVO
     */
    public void addAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO vAdditionalCompensationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAdditionalCompensationVO.setParentVO(this.getClass(), this);
        _additionalCompensationVOList.addElement(vAdditionalCompensationVO);
    } //-- void addAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method addAdditionalCompensationVO
     * 
     * @param index
     * @param vAdditionalCompensationVO
     */
    public void addAdditionalCompensationVO(int index, edit.common.vo.AdditionalCompensationVO vAdditionalCompensationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAdditionalCompensationVO.setParentVO(this.getClass(), this);
        _additionalCompensationVOList.insertElementAt(vAdditionalCompensationVO, index);
    } //-- void addAdditionalCompensationVO(int, edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method addPlacedAgentVO
     * 
     * @param vPlacedAgentVO
     */
    public void addPlacedAgentVO(edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.addElement(vPlacedAgentVO);
    } //-- void addPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method addPlacedAgentVO
     * 
     * @param index
     * @param vPlacedAgentVO
     */
    public void addPlacedAgentVO(int index, edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.insertElementAt(vPlacedAgentVO, index);
    } //-- void addPlacedAgentVO(int, edit.common.vo.PlacedAgentVO) 

    /**
     * Method enumerateAdditionalCompensationVO
     */
    public java.util.Enumeration enumerateAdditionalCompensationVO()
    {
        return _additionalCompensationVOList.elements();
    } //-- java.util.Enumeration enumerateAdditionalCompensationVO() 

    /**
     * Method enumeratePlacedAgentVO
     */
    public java.util.Enumeration enumeratePlacedAgentVO()
    {
        return _placedAgentVOList.elements();
    } //-- java.util.Enumeration enumeratePlacedAgentVO() 

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
        
        if (obj instanceof AgentContractVO) {
        
            AgentContractVO temp = (AgentContractVO)obj;
            if (this._agentContractPK != temp._agentContractPK)
                return false;
            if (this._has_agentContractPK != temp._has_agentContractPK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;
            if (this._contractEffectiveDate != null) {
                if (temp._contractEffectiveDate == null) return false;
                else if (!(this._contractEffectiveDate.equals(temp._contractEffectiveDate))) 
                    return false;
            }
            else if (temp._contractEffectiveDate != null)
                return false;
            if (this._contractStopDate != null) {
                if (temp._contractStopDate == null) return false;
                else if (!(this._contractStopDate.equals(temp._contractStopDate))) 
                    return false;
            }
            else if (temp._contractStopDate != null)
                return false;
            if (this._commissionProcessCT != null) {
                if (temp._commissionProcessCT == null) return false;
                else if (!(this._commissionProcessCT.equals(temp._commissionProcessCT))) 
                    return false;
            }
            else if (temp._commissionProcessCT != null)
                return false;
            if (this._contractCodeCT != null) {
                if (temp._contractCodeCT == null) return false;
                else if (!(this._contractCodeCT.equals(temp._contractCodeCT))) 
                    return false;
            }
            else if (temp._contractCodeCT != null)
                return false;
            if (this._additionalCompensationVOList != null) {
                if (temp._additionalCompensationVOList == null) return false;
                else if (!(this._additionalCompensationVOList.equals(temp._additionalCompensationVOList))) 
                    return false;
            }
            else if (temp._additionalCompensationVOList != null)
                return false;
            if (this._placedAgentVOList != null) {
                if (temp._placedAgentVOList == null) return false;
                else if (!(this._placedAgentVOList.equals(temp._placedAgentVOList))) 
                    return false;
            }
            else if (temp._placedAgentVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAdditionalCompensationVO
     * 
     * @param index
     */
    public edit.common.vo.AdditionalCompensationVO getAdditionalCompensationVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _additionalCompensationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AdditionalCompensationVO) _additionalCompensationVOList.elementAt(index);
    } //-- edit.common.vo.AdditionalCompensationVO getAdditionalCompensationVO(int) 

    /**
     * Method getAdditionalCompensationVO
     */
    public edit.common.vo.AdditionalCompensationVO[] getAdditionalCompensationVO()
    {
        int size = _additionalCompensationVOList.size();
        edit.common.vo.AdditionalCompensationVO[] mArray = new edit.common.vo.AdditionalCompensationVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AdditionalCompensationVO) _additionalCompensationVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AdditionalCompensationVO[] getAdditionalCompensationVO() 

    /**
     * Method getAdditionalCompensationVOCount
     */
    public int getAdditionalCompensationVOCount()
    {
        return _additionalCompensationVOList.size();
    } //-- int getAdditionalCompensationVOCount() 

    /**
     * Method getAgentContractPKReturns the value of field
     * 'agentContractPK'.
     * 
     * @return the value of field 'agentContractPK'.
     */
    public long getAgentContractPK()
    {
        return this._agentContractPK;
    } //-- long getAgentContractPK() 

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
     * Method getCommissionProcessCTReturns the value of field
     * 'commissionProcessCT'.
     * 
     * @return the value of field 'commissionProcessCT'.
     */
    public java.lang.String getCommissionProcessCT()
    {
        return this._commissionProcessCT;
    } //-- java.lang.String getCommissionProcessCT() 

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
     * Method getContractEffectiveDateReturns the value of field
     * 'contractEffectiveDate'.
     * 
     * @return the value of field 'contractEffectiveDate'.
     */
    public java.lang.String getContractEffectiveDate()
    {
        return this._contractEffectiveDate;
    } //-- java.lang.String getContractEffectiveDate() 

    /**
     * Method getContractStopDateReturns the value of field
     * 'contractStopDate'.
     * 
     * @return the value of field 'contractStopDate'.
     */
    public java.lang.String getContractStopDate()
    {
        return this._contractStopDate;
    } //-- java.lang.String getContractStopDate() 

    /**
     * Method getPlacedAgentVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentVO getPlacedAgentVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.PlacedAgentVO) _placedAgentVOList.elementAt(index);
    } //-- edit.common.vo.PlacedAgentVO getPlacedAgentVO(int) 

    /**
     * Method getPlacedAgentVO
     */
    public edit.common.vo.PlacedAgentVO[] getPlacedAgentVO()
    {
        int size = _placedAgentVOList.size();
        edit.common.vo.PlacedAgentVO[] mArray = new edit.common.vo.PlacedAgentVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.PlacedAgentVO) _placedAgentVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.PlacedAgentVO[] getPlacedAgentVO() 

    /**
     * Method getPlacedAgentVOCount
     */
    public int getPlacedAgentVOCount()
    {
        return _placedAgentVOList.size();
    } //-- int getPlacedAgentVOCount() 

    /**
     * Method hasAgentContractPK
     */
    public boolean hasAgentContractPK()
    {
        return this._has_agentContractPK;
    } //-- boolean hasAgentContractPK() 

    /**
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

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
     * Method removeAdditionalCompensationVO
     * 
     * @param index
     */
    public edit.common.vo.AdditionalCompensationVO removeAdditionalCompensationVO(int index)
    {
        java.lang.Object obj = _additionalCompensationVOList.elementAt(index);
        _additionalCompensationVOList.removeElementAt(index);
        return (edit.common.vo.AdditionalCompensationVO) obj;
    } //-- edit.common.vo.AdditionalCompensationVO removeAdditionalCompensationVO(int) 

    /**
     * Method removeAllAdditionalCompensationVO
     */
    public void removeAllAdditionalCompensationVO()
    {
        _additionalCompensationVOList.removeAllElements();
    } //-- void removeAllAdditionalCompensationVO() 

    /**
     * Method removeAllPlacedAgentVO
     */
    public void removeAllPlacedAgentVO()
    {
        _placedAgentVOList.removeAllElements();
    } //-- void removeAllPlacedAgentVO() 

    /**
     * Method removePlacedAgentVO
     * 
     * @param index
     */
    public edit.common.vo.PlacedAgentVO removePlacedAgentVO(int index)
    {
        java.lang.Object obj = _placedAgentVOList.elementAt(index);
        _placedAgentVOList.removeElementAt(index);
        return (edit.common.vo.PlacedAgentVO) obj;
    } //-- edit.common.vo.PlacedAgentVO removePlacedAgentVO(int) 

    /**
     * Method setAdditionalCompensationVO
     * 
     * @param index
     * @param vAdditionalCompensationVO
     */
    public void setAdditionalCompensationVO(int index, edit.common.vo.AdditionalCompensationVO vAdditionalCompensationVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _additionalCompensationVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAdditionalCompensationVO.setParentVO(this.getClass(), this);
        _additionalCompensationVOList.setElementAt(vAdditionalCompensationVO, index);
    } //-- void setAdditionalCompensationVO(int, edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method setAdditionalCompensationVO
     * 
     * @param additionalCompensationVOArray
     */
    public void setAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO[] additionalCompensationVOArray)
    {
        //-- copy array
        _additionalCompensationVOList.removeAllElements();
        for (int i = 0; i < additionalCompensationVOArray.length; i++) {
            additionalCompensationVOArray[i].setParentVO(this.getClass(), this);
            _additionalCompensationVOList.addElement(additionalCompensationVOArray[i]);
        }
    } //-- void setAdditionalCompensationVO(edit.common.vo.AdditionalCompensationVO) 

    /**
     * Method setAgentContractPKSets the value of field
     * 'agentContractPK'.
     * 
     * @param agentContractPK the value of field 'agentContractPK'.
     */
    public void setAgentContractPK(long agentContractPK)
    {
        this._agentContractPK = agentContractPK;
        
        super.setVoChanged(true);
        this._has_agentContractPK = true;
    } //-- void setAgentContractPK(long) 

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
     * Method setCommissionProcessCTSets the value of field
     * 'commissionProcessCT'.
     * 
     * @param commissionProcessCT the value of field
     * 'commissionProcessCT'.
     */
    public void setCommissionProcessCT(java.lang.String commissionProcessCT)
    {
        this._commissionProcessCT = commissionProcessCT;
        
        super.setVoChanged(true);
    } //-- void setCommissionProcessCT(java.lang.String) 

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
     * Method setContractEffectiveDateSets the value of field
     * 'contractEffectiveDate'.
     * 
     * @param contractEffectiveDate the value of field
     * 'contractEffectiveDate'.
     */
    public void setContractEffectiveDate(java.lang.String contractEffectiveDate)
    {
        this._contractEffectiveDate = contractEffectiveDate;
        
        super.setVoChanged(true);
    } //-- void setContractEffectiveDate(java.lang.String) 

    /**
     * Method setContractStopDateSets the value of field
     * 'contractStopDate'.
     * 
     * @param contractStopDate the value of field 'contractStopDate'
     */
    public void setContractStopDate(java.lang.String contractStopDate)
    {
        this._contractStopDate = contractStopDate;
        
        super.setVoChanged(true);
    } //-- void setContractStopDate(java.lang.String) 

    /**
     * Method setPlacedAgentVO
     * 
     * @param index
     * @param vPlacedAgentVO
     */
    public void setPlacedAgentVO(int index, edit.common.vo.PlacedAgentVO vPlacedAgentVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _placedAgentVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vPlacedAgentVO.setParentVO(this.getClass(), this);
        _placedAgentVOList.setElementAt(vPlacedAgentVO, index);
    } //-- void setPlacedAgentVO(int, edit.common.vo.PlacedAgentVO) 

    /**
     * Method setPlacedAgentVO
     * 
     * @param placedAgentVOArray
     */
    public void setPlacedAgentVO(edit.common.vo.PlacedAgentVO[] placedAgentVOArray)
    {
        //-- copy array
        _placedAgentVOList.removeAllElements();
        for (int i = 0; i < placedAgentVOArray.length; i++) {
            placedAgentVOArray[i].setParentVO(this.getClass(), this);
            _placedAgentVOList.addElement(placedAgentVOArray[i]);
        }
    } //-- void setPlacedAgentVO(edit.common.vo.PlacedAgentVO) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentContractVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentContractVO) Unmarshaller.unmarshal(edit.common.vo.AgentContractVO.class, reader);
    } //-- edit.common.vo.AgentContractVO unmarshal(java.io.Reader) 

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
