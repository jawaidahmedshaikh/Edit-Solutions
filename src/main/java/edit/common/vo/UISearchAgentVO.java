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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class UISearchAgentVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentPK
     */
    private long _agentPK;

    /**
     * keeps track of state for field: _agentPK
     */
    private boolean _has_agentPK;

    /**
     * Field _agentTypeCT
     */
    private java.lang.String _agentTypeCT;

    /**
     * Field _agentStatusCT
     */
    private java.lang.String _agentStatusCT;

    /**
     * Field _companyName
     */
    private java.lang.String _companyName;

    /**
     * Field _agentNumberListList
     */
    private java.util.Vector _agentNumberListList;


      //----------------/
     //- Constructors -/
    //----------------/

    public UISearchAgentVO() {
        super();
        _agentNumberListList = new Vector();
    } //-- edit.common.vo.UISearchAgentVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAgentNumberList
     * 
     * @param vAgentNumberList
     */
    public void addAgentNumberList(edit.common.vo.AgentNumberList vAgentNumberList)
        throws java.lang.IndexOutOfBoundsException
    {
        _agentNumberListList.addElement(vAgentNumberList);
    } //-- void addAgentNumberList(edit.common.vo.AgentNumberList) 

    /**
     * Method addAgentNumberList
     * 
     * @param index
     * @param vAgentNumberList
     */
    public void addAgentNumberList(int index, edit.common.vo.AgentNumberList vAgentNumberList)
        throws java.lang.IndexOutOfBoundsException
    {
        _agentNumberListList.insertElementAt(vAgentNumberList, index);
    } //-- void addAgentNumberList(int, edit.common.vo.AgentNumberList) 

    /**
     * Method enumerateAgentNumberList
     */
    public java.util.Enumeration enumerateAgentNumberList()
    {
        return _agentNumberListList.elements();
    } //-- java.util.Enumeration enumerateAgentNumberList() 

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
        
        if (obj instanceof UISearchAgentVO) {
        
            UISearchAgentVO temp = (UISearchAgentVO)obj;
            if (this._agentPK != temp._agentPK)
                return false;
            if (this._has_agentPK != temp._has_agentPK)
                return false;
            if (this._agentTypeCT != null) {
                if (temp._agentTypeCT == null) return false;
                else if (!(this._agentTypeCT.equals(temp._agentTypeCT))) 
                    return false;
            }
            else if (temp._agentTypeCT != null)
                return false;
            if (this._agentStatusCT != null) {
                if (temp._agentStatusCT == null) return false;
                else if (!(this._agentStatusCT.equals(temp._agentStatusCT))) 
                    return false;
            }
            else if (temp._agentStatusCT != null)
                return false;
            if (this._companyName != null) {
                if (temp._companyName == null) return false;
                else if (!(this._companyName.equals(temp._companyName))) 
                    return false;
            }
            else if (temp._companyName != null)
                return false;
            if (this._agentNumberListList != null) {
                if (temp._agentNumberListList == null) return false;
                else if (!(this._agentNumberListList.equals(temp._agentNumberListList))) 
                    return false;
            }
            else if (temp._agentNumberListList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentNumberList
     * 
     * @param index
     */
    public edit.common.vo.AgentNumberList getAgentNumberList(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentNumberListList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AgentNumberList) _agentNumberListList.elementAt(index);
    } //-- edit.common.vo.AgentNumberList getAgentNumberList(int) 

    /**
     * Method getAgentNumberList
     */
    public edit.common.vo.AgentNumberList[] getAgentNumberList()
    {
        int size = _agentNumberListList.size();
        edit.common.vo.AgentNumberList[] mArray = new edit.common.vo.AgentNumberList[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AgentNumberList) _agentNumberListList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AgentNumberList[] getAgentNumberList() 

    /**
     * Method getAgentNumberListCount
     */
    public int getAgentNumberListCount()
    {
        return _agentNumberListList.size();
    } //-- int getAgentNumberListCount() 

    /**
     * Method getAgentPKReturns the value of field 'agentPK'.
     * 
     * @return the value of field 'agentPK'.
     */
    public long getAgentPK()
    {
        return this._agentPK;
    } //-- long getAgentPK() 

    /**
     * Method getAgentStatusCTReturns the value of field
     * 'agentStatusCT'.
     * 
     * @return the value of field 'agentStatusCT'.
     */
    public java.lang.String getAgentStatusCT()
    {
        return this._agentStatusCT;
    } //-- java.lang.String getAgentStatusCT() 

    /**
     * Method getAgentTypeCTReturns the value of field
     * 'agentTypeCT'.
     * 
     * @return the value of field 'agentTypeCT'.
     */
    public java.lang.String getAgentTypeCT()
    {
        return this._agentTypeCT;
    } //-- java.lang.String getAgentTypeCT() 

    /**
     * Method getCompanyNameReturns the value of field
     * 'companyName'.
     * 
     * @return the value of field 'companyName'.
     */
    public java.lang.String getCompanyName()
    {
        return this._companyName;
    } //-- java.lang.String getCompanyName() 

    /**
     * Method hasAgentPK
     */
    public boolean hasAgentPK()
    {
        return this._has_agentPK;
    } //-- boolean hasAgentPK() 

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
     * Method removeAgentNumberList
     * 
     * @param index
     */
    public edit.common.vo.AgentNumberList removeAgentNumberList(int index)
    {
        java.lang.Object obj = _agentNumberListList.elementAt(index);
        _agentNumberListList.removeElementAt(index);
        return (edit.common.vo.AgentNumberList) obj;
    } //-- edit.common.vo.AgentNumberList removeAgentNumberList(int) 

    /**
     * Method removeAllAgentNumberList
     */
    public void removeAllAgentNumberList()
    {
        _agentNumberListList.removeAllElements();
    } //-- void removeAllAgentNumberList() 

    /**
     * Method setAgentNumberList
     * 
     * @param index
     * @param vAgentNumberList
     */
    public void setAgentNumberList(int index, edit.common.vo.AgentNumberList vAgentNumberList)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _agentNumberListList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _agentNumberListList.setElementAt(vAgentNumberList, index);
    } //-- void setAgentNumberList(int, edit.common.vo.AgentNumberList) 

    /**
     * Method setAgentNumberList
     * 
     * @param agentNumberListArray
     */
    public void setAgentNumberList(edit.common.vo.AgentNumberList[] agentNumberListArray)
    {
        //-- copy array
        _agentNumberListList.removeAllElements();
        for (int i = 0; i < agentNumberListArray.length; i++) {
            _agentNumberListList.addElement(agentNumberListArray[i]);
        }
    } //-- void setAgentNumberList(edit.common.vo.AgentNumberList) 

    /**
     * Method setAgentPKSets the value of field 'agentPK'.
     * 
     * @param agentPK the value of field 'agentPK'.
     */
    public void setAgentPK(long agentPK)
    {
        this._agentPK = agentPK;
        
        super.setVoChanged(true);
        this._has_agentPK = true;
    } //-- void setAgentPK(long) 

    /**
     * Method setAgentStatusCTSets the value of field
     * 'agentStatusCT'.
     * 
     * @param agentStatusCT the value of field 'agentStatusCT'.
     */
    public void setAgentStatusCT(java.lang.String agentStatusCT)
    {
        this._agentStatusCT = agentStatusCT;
        
        super.setVoChanged(true);
    } //-- void setAgentStatusCT(java.lang.String) 

    /**
     * Method setAgentTypeCTSets the value of field 'agentTypeCT'.
     * 
     * @param agentTypeCT the value of field 'agentTypeCT'.
     */
    public void setAgentTypeCT(java.lang.String agentTypeCT)
    {
        this._agentTypeCT = agentTypeCT;
        
        super.setVoChanged(true);
    } //-- void setAgentTypeCT(java.lang.String) 

    /**
     * Method setCompanyNameSets the value of field 'companyName'.
     * 
     * @param companyName the value of field 'companyName'.
     */
    public void setCompanyName(java.lang.String companyName)
    {
        this._companyName = companyName;
        
        super.setVoChanged(true);
    } //-- void setCompanyName(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.UISearchAgentVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.UISearchAgentVO) Unmarshaller.unmarshal(edit.common.vo.UISearchAgentVO.class, reader);
    } //-- edit.common.vo.UISearchAgentVO unmarshal(java.io.Reader) 

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
