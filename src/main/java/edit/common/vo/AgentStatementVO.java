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
import java.util.Enumeration;
import java.util.Vector;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class AgentStatementVO.
 * 
 * @version $Revision$ $Date$
 */
public class AgentStatementVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _statementDate
     */
    private java.lang.String _statementDate;

    /**
     * Field _agentInfoVOList
     */
    private java.util.Vector _agentInfoVOList;

    /**
     * Field _agentStatementLineVOList
     */
    private java.util.Vector _agentStatementLineVOList;

    /**
     * Field _agentEarningsVOList
     */
    private java.util.Vector _agentEarningsVOList;

    /**
     * Field _lastStatementAmount
     */
    private java.math.BigDecimal _lastStatementAmount;

    /**
     * Field _lastStatementDate
     */
    private java.lang.String _lastStatementDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentStatementVO() {
        super();
        _agentInfoVOList = new Vector();
        _agentStatementLineVOList = new Vector();
        _agentEarningsVOList = new Vector();
    } //-- edit.common.vo.AgentStatementVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentEarningsVO
     * 
     * @param vAgentEarningsVO
     */
    public void addAgentEarningsVO(edit.common.vo.AgentEarningsVO vAgentEarningsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentEarningsVO.setParentVO(this.getClass(), this);
        _agentEarningsVOList.addElement(vAgentEarningsVO);
    } //-- void addAgentEarningsVO(edit.common.vo.AgentEarningsVO) 

    /**
     * Method addAgentEarningsVO
     * 
     * @param index
     * @param vAgentEarningsVO
     */
    public void addAgentEarningsVO(int index, edit.common.vo.AgentEarningsVO vAgentEarningsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentEarningsVO.setParentVO(this.getClass(), this);
        _agentEarningsVOList.insertElementAt(vAgentEarningsVO, index);
    } //-- void addAgentEarningsVO(int, edit.common.vo.AgentEarningsVO) 

    /**
     * Method addAgentInfoVO
     * 
     * @param vAgentInfoVO
     */
    public void addAgentInfoVO(edit.common.vo.AgentInfoVO vAgentInfoVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentInfoVO.setParentVO(this.getClass(), this);
        _agentInfoVOList.addElement(vAgentInfoVO);
    } //-- void addAgentInfoVO(edit.common.vo.AgentInfoVO) 

    /**
     * Method addAgentInfoVO
     * 
     * @param index
     * @param vAgentInfoVO
     */
    public void addAgentInfoVO(int index, edit.common.vo.AgentInfoVO vAgentInfoVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentInfoVO.setParentVO(this.getClass(), this);
        _agentInfoVOList.insertElementAt(vAgentInfoVO, index);
    } //-- void addAgentInfoVO(int, edit.common.vo.AgentInfoVO) 

    /**
     * Method addAgentStatementLineVO
     * 
     * @param vAgentStatementLineVO
     */
    public void addAgentStatementLineVO(edit.common.vo.AgentStatementLineVO vAgentStatementLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentStatementLineVO.setParentVO(this.getClass(), this);
        _agentStatementLineVOList.addElement(vAgentStatementLineVO);
    } //-- void addAgentStatementLineVO(edit.common.vo.AgentStatementLineVO) 

    /**
     * Method addAgentStatementLineVO
     * 
     * @param index
     * @param vAgentStatementLineVO
     */
    public void addAgentStatementLineVO(int index, edit.common.vo.AgentStatementLineVO vAgentStatementLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vAgentStatementLineVO.setParentVO(this.getClass(), this);
        _agentStatementLineVOList.insertElementAt(vAgentStatementLineVO, index);
    } //-- void addAgentStatementLineVO(int, edit.common.vo.AgentStatementLineVO) 

    /**
     * Method enumerateAgentEarningsVO
     */
    public java.util.Enumeration enumerateAgentEarningsVO()
    {
        return _agentEarningsVOList.elements();
    } //-- java.util.Enumeration enumerateAgentEarningsVO() 

    /**
     * Method enumerateAgentInfoVO
     */
    public java.util.Enumeration enumerateAgentInfoVO()
    {
        return _agentInfoVOList.elements();
    } //-- java.util.Enumeration enumerateAgentInfoVO() 

    /**
     * Method enumerateAgentStatementLineVO
     */
    public java.util.Enumeration enumerateAgentStatementLineVO()
    {
        return _agentStatementLineVOList.elements();
    } //-- java.util.Enumeration enumerateAgentStatementLineVO() 

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
        
        if (obj instanceof AgentStatementVO) {
        
            AgentStatementVO temp = (AgentStatementVO)obj;
            if (this._statementDate != null) {
                if (temp._statementDate == null) return false;
                else if (!(this._statementDate.equals(temp._statementDate))) 
                    return false;
            }
            else if (temp._statementDate != null)
                return false;
            if (this._agentInfoVOList != null) {
                if (temp._agentInfoVOList == null) return false;
                else if (!(this._agentInfoVOList.equals(temp._agentInfoVOList))) 
                    return false;
            }
            else if (temp._agentInfoVOList != null)
                return false;
            if (this._agentStatementLineVOList != null) {
                if (temp._agentStatementLineVOList == null) return false;
                else if (!(this._agentStatementLineVOList.equals(temp._agentStatementLineVOList))) 
                    return false;
            }
            else if (temp._agentStatementLineVOList != null)
                return false;
            if (this._agentEarningsVOList != null) {
                if (temp._agentEarningsVOList == null) return false;
                else if (!(this._agentEarningsVOList.equals(temp._agentEarningsVOList))) 
                    return false;
            }
            else if (temp._agentEarningsVOList != null)
                return false;
            if (this._lastStatementAmount != null) {
                if (temp._lastStatementAmount == null) return false;
                else if (!(this._lastStatementAmount.equals(temp._lastStatementAmount))) 
                    return false;
            }
            else if (temp._lastStatementAmount != null)
                return false;
            if (this._lastStatementDate != null) {
                if (temp._lastStatementDate == null) return false;
                else if (!(this._lastStatementDate.equals(temp._lastStatementDate))) 
                    return false;
            }
            else if (temp._lastStatementDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentEarningsVO
     * 
     * @param index
     */
    public edit.common.vo.AgentEarningsVO getAgentEarningsVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentEarningsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentEarningsVO) _agentEarningsVOList.elementAt(index);
    } //-- edit.common.vo.AgentEarningsVO getAgentEarningsVO(int) 

    /**
     * Method getAgentEarningsVO
     */
    public edit.common.vo.AgentEarningsVO[] getAgentEarningsVO()
    {
        int size = _agentEarningsVOList.size();
        edit.common.vo.AgentEarningsVO[] mArray = new edit.common.vo.AgentEarningsVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentEarningsVO) _agentEarningsVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentEarningsVO[] getAgentEarningsVO() 

    /**
     * Method getAgentEarningsVOCount
     */
    public int getAgentEarningsVOCount()
    {
        return _agentEarningsVOList.size();
    } //-- int getAgentEarningsVOCount() 

    /**
     * Method getAgentInfoVO
     * 
     * @param index
     */
    public edit.common.vo.AgentInfoVO getAgentInfoVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentInfoVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentInfoVO) _agentInfoVOList.elementAt(index);
    } //-- edit.common.vo.AgentInfoVO getAgentInfoVO(int) 

    /**
     * Method getAgentInfoVO
     */
    public edit.common.vo.AgentInfoVO[] getAgentInfoVO()
    {
        int size = _agentInfoVOList.size();
        edit.common.vo.AgentInfoVO[] mArray = new edit.common.vo.AgentInfoVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentInfoVO) _agentInfoVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentInfoVO[] getAgentInfoVO() 

    /**
     * Method getAgentInfoVOCount
     */
    public int getAgentInfoVOCount()
    {
        return _agentInfoVOList.size();
    } //-- int getAgentInfoVOCount() 

    /**
     * Method getAgentStatementLineVO
     * 
     * @param index
     */
    public edit.common.vo.AgentStatementLineVO getAgentStatementLineVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentStatementLineVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentStatementLineVO) _agentStatementLineVOList.elementAt(index);
    } //-- edit.common.vo.AgentStatementLineVO getAgentStatementLineVO(int) 

    /**
     * Method getAgentStatementLineVO
     */
    public edit.common.vo.AgentStatementLineVO[] getAgentStatementLineVO()
    {
        int size = _agentStatementLineVOList.size();
        edit.common.vo.AgentStatementLineVO[] mArray = new edit.common.vo.AgentStatementLineVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentStatementLineVO) _agentStatementLineVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentStatementLineVO[] getAgentStatementLineVO() 

    /**
     * Method getAgentStatementLineVOCount
     */
    public int getAgentStatementLineVOCount()
    {
        return _agentStatementLineVOList.size();
    } //-- int getAgentStatementLineVOCount() 

    /**
     * Method getLastStatementAmountReturns the value of field
     * 'lastStatementAmount'.
     * 
     * @return the value of field 'lastStatementAmount'.
     */
    public java.math.BigDecimal getLastStatementAmount()
    {
        return this._lastStatementAmount;
    } //-- java.math.BigDecimal getLastStatementAmount() 

    /**
     * Method getLastStatementDateReturns the value of field
     * 'lastStatementDate'.
     * 
     * @return the value of field 'lastStatementDate'.
     */
    public java.lang.String getLastStatementDate()
    {
        return this._lastStatementDate;
    } //-- java.lang.String getLastStatementDate() 

    /**
     * Method getStatementDateReturns the value of field
     * 'statementDate'.
     * 
     * @return the value of field 'statementDate'.
     */
    public java.lang.String getStatementDate()
    {
        return this._statementDate;
    } //-- java.lang.String getStatementDate() 

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
     * Method removeAgentEarningsVO
     * 
     * @param index
     */
    public edit.common.vo.AgentEarningsVO removeAgentEarningsVO(int index)
    {
        java.lang.Object obj = _agentEarningsVOList.elementAt(index);
        _agentEarningsVOList.removeElementAt(index);
        return (edit.common.vo.AgentEarningsVO) obj;
    } //-- edit.common.vo.AgentEarningsVO removeAgentEarningsVO(int) 

    /**
     * Method removeAgentInfoVO
     * 
     * @param index
     */
    public edit.common.vo.AgentInfoVO removeAgentInfoVO(int index)
    {
        java.lang.Object obj = _agentInfoVOList.elementAt(index);
        _agentInfoVOList.removeElementAt(index);
        return (edit.common.vo.AgentInfoVO) obj;
    } //-- edit.common.vo.AgentInfoVO removeAgentInfoVO(int) 

    /**
     * Method removeAgentStatementLineVO
     * 
     * @param index
     */
    public edit.common.vo.AgentStatementLineVO removeAgentStatementLineVO(int index)
    {
        java.lang.Object obj = _agentStatementLineVOList.elementAt(index);
        _agentStatementLineVOList.removeElementAt(index);
        return (edit.common.vo.AgentStatementLineVO) obj;
    } //-- edit.common.vo.AgentStatementLineVO removeAgentStatementLineVO(int) 

    /**
     * Method removeAllAgentEarningsVO
     */
    public void removeAllAgentEarningsVO()
    {
        _agentEarningsVOList.removeAllElements();
    } //-- void removeAllAgentEarningsVO() 

    /**
     * Method removeAllAgentInfoVO
     */
    public void removeAllAgentInfoVO()
    {
        _agentInfoVOList.removeAllElements();
    } //-- void removeAllAgentInfoVO() 

    /**
     * Method removeAllAgentStatementLineVO
     */
    public void removeAllAgentStatementLineVO()
    {
        _agentStatementLineVOList.removeAllElements();
    } //-- void removeAllAgentStatementLineVO() 

    /**
     * Method setAgentEarningsVO
     * 
     * @param index
     * @param vAgentEarningsVO
     */
    public void setAgentEarningsVO(int index, edit.common.vo.AgentEarningsVO vAgentEarningsVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentEarningsVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentEarningsVO.setParentVO(this.getClass(), this);
        _agentEarningsVOList.setElementAt(vAgentEarningsVO, index);
    } //-- void setAgentEarningsVO(int, edit.common.vo.AgentEarningsVO) 

    /**
     * Method setAgentEarningsVO
     * 
     * @param agentEarningsVOArray
     */
    public void setAgentEarningsVO(edit.common.vo.AgentEarningsVO[] agentEarningsVOArray)
    {
        //-- copy array
        _agentEarningsVOList.removeAllElements();
        for (int i = 0; i < agentEarningsVOArray.length; i++) {
            agentEarningsVOArray[i].setParentVO(this.getClass(), this);
            _agentEarningsVOList.addElement(agentEarningsVOArray[i]);
        }
    } //-- void setAgentEarningsVO(edit.common.vo.AgentEarningsVO) 

    /**
     * Method setAgentInfoVO
     * 
     * @param index
     * @param vAgentInfoVO
     */
    public void setAgentInfoVO(int index, edit.common.vo.AgentInfoVO vAgentInfoVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentInfoVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentInfoVO.setParentVO(this.getClass(), this);
        _agentInfoVOList.setElementAt(vAgentInfoVO, index);
    } //-- void setAgentInfoVO(int, edit.common.vo.AgentInfoVO) 

    /**
     * Method setAgentInfoVO
     * 
     * @param agentInfoVOArray
     */
    public void setAgentInfoVO(edit.common.vo.AgentInfoVO[] agentInfoVOArray)
    {
        //-- copy array
        _agentInfoVOList.removeAllElements();
        for (int i = 0; i < agentInfoVOArray.length; i++) {
            agentInfoVOArray[i].setParentVO(this.getClass(), this);
            _agentInfoVOList.addElement(agentInfoVOArray[i]);
        }
    } //-- void setAgentInfoVO(edit.common.vo.AgentInfoVO) 

    /**
     * Method setAgentStatementLineVO
     * 
     * @param index
     * @param vAgentStatementLineVO
     */
    public void setAgentStatementLineVO(int index, edit.common.vo.AgentStatementLineVO vAgentStatementLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentStatementLineVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vAgentStatementLineVO.setParentVO(this.getClass(), this);
        _agentStatementLineVOList.setElementAt(vAgentStatementLineVO, index);
    } //-- void setAgentStatementLineVO(int, edit.common.vo.AgentStatementLineVO) 

    /**
     * Method setAgentStatementLineVO
     * 
     * @param agentStatementLineVOArray
     */
    public void setAgentStatementLineVO(edit.common.vo.AgentStatementLineVO[] agentStatementLineVOArray)
    {
        //-- copy array
        _agentStatementLineVOList.removeAllElements();
        for (int i = 0; i < agentStatementLineVOArray.length; i++) {
            agentStatementLineVOArray[i].setParentVO(this.getClass(), this);
            _agentStatementLineVOList.addElement(agentStatementLineVOArray[i]);
        }
    } //-- void setAgentStatementLineVO(edit.common.vo.AgentStatementLineVO) 

    /**
     * Method setLastStatementAmountSets the value of field
     * 'lastStatementAmount'.
     * 
     * @param lastStatementAmount the value of field
     * 'lastStatementAmount'.
     */
    public void setLastStatementAmount(java.math.BigDecimal lastStatementAmount)
    {
        this._lastStatementAmount = lastStatementAmount;
        
        super.setVoChanged(true);
    } //-- void setLastStatementAmount(java.math.BigDecimal) 

    /**
     * Method setLastStatementDateSets the value of field
     * 'lastStatementDate'.
     * 
     * @param lastStatementDate the value of field
     * 'lastStatementDate'.
     */
    public void setLastStatementDate(java.lang.String lastStatementDate)
    {
        this._lastStatementDate = lastStatementDate;
        
        super.setVoChanged(true);
    } //-- void setLastStatementDate(java.lang.String) 

    /**
     * Method setStatementDateSets the value of field
     * 'statementDate'.
     * 
     * @param statementDate the value of field 'statementDate'.
     */
    public void setStatementDate(java.lang.String statementDate)
    {
        this._statementDate = statementDate;
        
        super.setVoChanged(true);
    } //-- void setStatementDate(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentStatementVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentStatementVO) Unmarshaller.unmarshal(edit.common.vo.AgentStatementVO.class, reader);
    } //-- edit.common.vo.AgentStatementVO unmarshal(java.io.Reader) 

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
