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
 * Class ScriptVO.
 * 
 * @version $Revision$ $Date$
 */
public class ScriptVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scriptPK
     */
    private long _scriptPK;

    /**
     * keeps track of state for field: _scriptPK
     */
    private boolean _has_scriptPK;

    /**
     * Field _scriptName
     */
    private java.lang.String _scriptName;

    /**
     * Field _operator
     */
    private java.lang.String _operator;

    /**
     * Field _maintDateTime
     */
    private java.lang.String _maintDateTime;

    /**
     * Field _scriptTypeCT
     */
    private java.lang.String _scriptTypeCT;

    /**
     * Field _scriptStatusCT
     */
    private java.lang.String _scriptStatusCT;

    /**
     * Field _rulesVOList
     */
    private java.util.Vector _rulesVOList;

    /**
     * Field _scriptLineVOList
     */
    private java.util.Vector _scriptLineVOList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ScriptVO() {
        super();
        _rulesVOList = new Vector();
        _scriptLineVOList = new Vector();
    } //-- edit.common.vo.ScriptVO()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addRulesVO
     * 
     * @param vRulesVO
     */
    public void addRulesVO(edit.common.vo.RulesVO vRulesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRulesVO.setParentVO(this.getClass(), this);
        _rulesVOList.addElement(vRulesVO);
    } //-- void addRulesVO(edit.common.vo.RulesVO) 

    /**
     * Method addRulesVO
     * 
     * @param index
     * @param vRulesVO
     */
    public void addRulesVO(int index, edit.common.vo.RulesVO vRulesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vRulesVO.setParentVO(this.getClass(), this);
        _rulesVOList.insertElementAt(vRulesVO, index);
    } //-- void addRulesVO(int, edit.common.vo.RulesVO) 

    /**
     * Method addScriptLineVO
     * 
     * @param vScriptLineVO
     */
    public void addScriptLineVO(edit.common.vo.ScriptLineVO vScriptLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vScriptLineVO.setParentVO(this.getClass(), this);
        _scriptLineVOList.addElement(vScriptLineVO);
    } //-- void addScriptLineVO(edit.common.vo.ScriptLineVO) 

    /**
     * Method addScriptLineVO
     * 
     * @param index
     * @param vScriptLineVO
     */
    public void addScriptLineVO(int index, edit.common.vo.ScriptLineVO vScriptLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        vScriptLineVO.setParentVO(this.getClass(), this);
        _scriptLineVOList.insertElementAt(vScriptLineVO, index);
    } //-- void addScriptLineVO(int, edit.common.vo.ScriptLineVO) 

    /**
     * Method enumerateRulesVO
     */
    public java.util.Enumeration enumerateRulesVO()
    {
        return _rulesVOList.elements();
    } //-- java.util.Enumeration enumerateRulesVO() 

    /**
     * Method enumerateScriptLineVO
     */
    public java.util.Enumeration enumerateScriptLineVO()
    {
        return _scriptLineVOList.elements();
    } //-- java.util.Enumeration enumerateScriptLineVO() 

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
        
        if (obj instanceof ScriptVO) {
        
            ScriptVO temp = (ScriptVO)obj;
            if (this._scriptPK != temp._scriptPK)
                return false;
            if (this._has_scriptPK != temp._has_scriptPK)
                return false;
            if (this._scriptName != null) {
                if (temp._scriptName == null) return false;
                else if (!(this._scriptName.equals(temp._scriptName))) 
                    return false;
            }
            else if (temp._scriptName != null)
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
            if (this._scriptTypeCT != null) {
                if (temp._scriptTypeCT == null) return false;
                else if (!(this._scriptTypeCT.equals(temp._scriptTypeCT))) 
                    return false;
            }
            else if (temp._scriptTypeCT != null)
                return false;
            if (this._scriptStatusCT != null) {
                if (temp._scriptStatusCT == null) return false;
                else if (!(this._scriptStatusCT.equals(temp._scriptStatusCT))) 
                    return false;
            }
            else if (temp._scriptStatusCT != null)
                return false;
            if (this._rulesVOList != null) {
                if (temp._rulesVOList == null) return false;
                else if (!(this._rulesVOList.equals(temp._rulesVOList))) 
                    return false;
            }
            else if (temp._rulesVOList != null)
                return false;
            if (this._scriptLineVOList != null) {
                if (temp._scriptLineVOList == null) return false;
                else if (!(this._scriptLineVOList.equals(temp._scriptLineVOList))) 
                    return false;
            }
            else if (temp._scriptLineVOList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getOperatorReturns the value of field 'operator'.
     * 
     * @return the value of field 'operator'.
     */
    public java.lang.String getOperator()
    {
        return this._operator;
    } //-- java.lang.String getOperator() 

    /**
     * Method getRulesVO
     * 
     * @param index
     */
    public edit.common.vo.RulesVO getRulesVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rulesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.RulesVO) _rulesVOList.elementAt(index);
    } //-- edit.common.vo.RulesVO getRulesVO(int) 

    /**
     * Method getRulesVO
     */
    public edit.common.vo.RulesVO[] getRulesVO()
    {
        int size = _rulesVOList.size();
        edit.common.vo.RulesVO[] mArray = new edit.common.vo.RulesVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.RulesVO) _rulesVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.RulesVO[] getRulesVO() 

    /**
     * Method getRulesVOCount
     */
    public int getRulesVOCount()
    {
        return _rulesVOList.size();
    } //-- int getRulesVOCount() 

    /**
     * Method getScriptLineVO
     * 
     * @param index
     */
    public edit.common.vo.ScriptLineVO getScriptLineVO(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _scriptLineVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.ScriptLineVO) _scriptLineVOList.elementAt(index);
    } //-- edit.common.vo.ScriptLineVO getScriptLineVO(int) 

    /**
     * Method getScriptLineVO
     */
    public edit.common.vo.ScriptLineVO[] getScriptLineVO()
    {
        int size = _scriptLineVOList.size();
        edit.common.vo.ScriptLineVO[] mArray = new edit.common.vo.ScriptLineVO[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.ScriptLineVO) _scriptLineVOList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.ScriptLineVO[] getScriptLineVO() 

    /**
     * Method getScriptLineVOCount
     */
    public int getScriptLineVOCount()
    {
        return _scriptLineVOList.size();
    } //-- int getScriptLineVOCount() 

    /**
     * Method getScriptNameReturns the value of field 'scriptName'.
     * 
     * @return the value of field 'scriptName'.
     */
    public java.lang.String getScriptName()
    {
        return this._scriptName;
    } //-- java.lang.String getScriptName() 

    /**
     * Method getScriptPKReturns the value of field 'scriptPK'.
     * 
     * @return the value of field 'scriptPK'.
     */
    public long getScriptPK()
    {
        return this._scriptPK;
    } //-- long getScriptPK() 

    /**
     * Method getScriptStatusCTReturns the value of field
     * 'scriptStatusCT'.
     * 
     * @return the value of field 'scriptStatusCT'.
     */
    public java.lang.String getScriptStatusCT()
    {
        return this._scriptStatusCT;
    } //-- java.lang.String getScriptStatusCT() 

    /**
     * Method getScriptTypeCTReturns the value of field
     * 'scriptTypeCT'.
     * 
     * @return the value of field 'scriptTypeCT'.
     */
    public java.lang.String getScriptTypeCT()
    {
        return this._scriptTypeCT;
    } //-- java.lang.String getScriptTypeCT() 

    /**
     * Method hasScriptPK
     */
    public boolean hasScriptPK()
    {
        return this._has_scriptPK;
    } //-- boolean hasScriptPK() 

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
     * Method removeAllRulesVO
     */
    public void removeAllRulesVO()
    {
        _rulesVOList.removeAllElements();
    } //-- void removeAllRulesVO() 

    /**
     * Method removeAllScriptLineVO
     */
    public void removeAllScriptLineVO()
    {
        _scriptLineVOList.removeAllElements();
    } //-- void removeAllScriptLineVO() 

    /**
     * Method removeRulesVO
     * 
     * @param index
     */
    public edit.common.vo.RulesVO removeRulesVO(int index)
    {
        java.lang.Object obj = _rulesVOList.elementAt(index);
        _rulesVOList.removeElementAt(index);
        return (edit.common.vo.RulesVO) obj;
    } //-- edit.common.vo.RulesVO removeRulesVO(int) 

    /**
     * Method removeScriptLineVO
     * 
     * @param index
     */
    public edit.common.vo.ScriptLineVO removeScriptLineVO(int index)
    {
        java.lang.Object obj = _scriptLineVOList.elementAt(index);
        _scriptLineVOList.removeElementAt(index);
        return (edit.common.vo.ScriptLineVO) obj;
    } //-- edit.common.vo.ScriptLineVO removeScriptLineVO(int) 

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
     * Method setRulesVO
     * 
     * @param index
     * @param vRulesVO
     */
    public void setRulesVO(int index, edit.common.vo.RulesVO vRulesVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _rulesVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vRulesVO.setParentVO(this.getClass(), this);
        _rulesVOList.setElementAt(vRulesVO, index);
    } //-- void setRulesVO(int, edit.common.vo.RulesVO) 

    /**
     * Method setRulesVO
     * 
     * @param rulesVOArray
     */
    public void setRulesVO(edit.common.vo.RulesVO[] rulesVOArray)
    {
        //-- copy array
        _rulesVOList.removeAllElements();
        for (int i = 0; i < rulesVOArray.length; i++) {
            rulesVOArray[i].setParentVO(this.getClass(), this);
            _rulesVOList.addElement(rulesVOArray[i]);
        }
    } //-- void setRulesVO(edit.common.vo.RulesVO) 

    /**
     * Method setScriptLineVO
     * 
     * @param index
     * @param vScriptLineVO
     */
    public void setScriptLineVO(int index, edit.common.vo.ScriptLineVO vScriptLineVO)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _scriptLineVOList.size())) {
            throw new IndexOutOfBoundsException();
        }
        vScriptLineVO.setParentVO(this.getClass(), this);
        _scriptLineVOList.setElementAt(vScriptLineVO, index);
    } //-- void setScriptLineVO(int, edit.common.vo.ScriptLineVO) 

    /**
     * Method setScriptLineVO
     * 
     * @param scriptLineVOArray
     */
    public void setScriptLineVO(edit.common.vo.ScriptLineVO[] scriptLineVOArray)
    {
        //-- copy array
        _scriptLineVOList.removeAllElements();
        for (int i = 0; i < scriptLineVOArray.length; i++) {
            scriptLineVOArray[i].setParentVO(this.getClass(), this);
            _scriptLineVOList.addElement(scriptLineVOArray[i]);
        }
    } //-- void setScriptLineVO(edit.common.vo.ScriptLineVO) 

    /**
     * Method setScriptNameSets the value of field 'scriptName'.
     * 
     * @param scriptName the value of field 'scriptName'.
     */
    public void setScriptName(java.lang.String scriptName)
    {
        this._scriptName = scriptName;
        
        super.setVoChanged(true);
    } //-- void setScriptName(java.lang.String) 

    /**
     * Method setScriptPKSets the value of field 'scriptPK'.
     * 
     * @param scriptPK the value of field 'scriptPK'.
     */
    public void setScriptPK(long scriptPK)
    {
        this._scriptPK = scriptPK;
        
        super.setVoChanged(true);
        this._has_scriptPK = true;
    } //-- void setScriptPK(long) 

    /**
     * Method setScriptStatusCTSets the value of field
     * 'scriptStatusCT'.
     * 
     * @param scriptStatusCT the value of field 'scriptStatusCT'.
     */
    public void setScriptStatusCT(java.lang.String scriptStatusCT)
    {
        this._scriptStatusCT = scriptStatusCT;
        
        super.setVoChanged(true);
    } //-- void setScriptStatusCT(java.lang.String) 

    /**
     * Method setScriptTypeCTSets the value of field
     * 'scriptTypeCT'.
     * 
     * @param scriptTypeCT the value of field 'scriptTypeCT'.
     */
    public void setScriptTypeCT(java.lang.String scriptTypeCT)
    {
        this._scriptTypeCT = scriptTypeCT;
        
        super.setVoChanged(true);
    } //-- void setScriptTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ScriptVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ScriptVO) Unmarshaller.unmarshal(edit.common.vo.ScriptVO.class, reader);
    } //-- edit.common.vo.ScriptVO unmarshal(java.io.Reader) 

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
