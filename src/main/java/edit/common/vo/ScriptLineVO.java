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
 * Class ScriptLineVO.
 * 
 * @version $Revision$ $Date$
 */
public class ScriptLineVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scriptLinePK
     */
    private long _scriptLinePK;

    /**
     * keeps track of state for field: _scriptLinePK
     */
    private boolean _has_scriptLinePK;

    /**
     * Field _scriptFK
     */
    private long _scriptFK;

    /**
     * keeps track of state for field: _scriptFK
     */
    private boolean _has_scriptFK;

    /**
     * Field _lineNumber
     */
    private int _lineNumber;

    /**
     * keeps track of state for field: _lineNumber
     */
    private boolean _has_lineNumber;

    /**
     * Field _scriptLine
     */
    private java.lang.String _scriptLine;


      //----------------/
     //- Constructors -/
    //----------------/

    public ScriptLineVO() {
        super();
    } //-- edit.common.vo.ScriptLineVO()


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
        
        if (obj instanceof ScriptLineVO) {
        
            ScriptLineVO temp = (ScriptLineVO)obj;
            if (this._scriptLinePK != temp._scriptLinePK)
                return false;
            if (this._has_scriptLinePK != temp._has_scriptLinePK)
                return false;
            if (this._scriptFK != temp._scriptFK)
                return false;
            if (this._has_scriptFK != temp._has_scriptFK)
                return false;
            if (this._lineNumber != temp._lineNumber)
                return false;
            if (this._has_lineNumber != temp._has_lineNumber)
                return false;
            if (this._scriptLine != null) {
                if (temp._scriptLine == null) return false;
                else if (!(this._scriptLine.equals(temp._scriptLine))) 
                    return false;
            }
            else if (temp._scriptLine != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getLineNumberReturns the value of field 'lineNumber'.
     * 
     * @return the value of field 'lineNumber'.
     */
    public int getLineNumber()
    {
        return this._lineNumber;
    } //-- int getLineNumber() 

    /**
     * Method getScriptFKReturns the value of field 'scriptFK'.
     * 
     * @return the value of field 'scriptFK'.
     */
    public long getScriptFK()
    {
        return this._scriptFK;
    } //-- long getScriptFK() 

    /**
     * Method getScriptLineReturns the value of field 'scriptLine'.
     * 
     * @return the value of field 'scriptLine'.
     */
    public java.lang.String getScriptLine()
    {
        return this._scriptLine;
    } //-- java.lang.String getScriptLine() 

    /**
     * Method getScriptLinePKReturns the value of field
     * 'scriptLinePK'.
     * 
     * @return the value of field 'scriptLinePK'.
     */
    public long getScriptLinePK()
    {
        return this._scriptLinePK;
    } //-- long getScriptLinePK() 

    /**
     * Method hasLineNumber
     */
    public boolean hasLineNumber()
    {
        return this._has_lineNumber;
    } //-- boolean hasLineNumber() 

    /**
     * Method hasScriptFK
     */
    public boolean hasScriptFK()
    {
        return this._has_scriptFK;
    } //-- boolean hasScriptFK() 

    /**
     * Method hasScriptLinePK
     */
    public boolean hasScriptLinePK()
    {
        return this._has_scriptLinePK;
    } //-- boolean hasScriptLinePK() 

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
     * Method setLineNumberSets the value of field 'lineNumber'.
     * 
     * @param lineNumber the value of field 'lineNumber'.
     */
    public void setLineNumber(int lineNumber)
    {
        this._lineNumber = lineNumber;
        
        super.setVoChanged(true);
        this._has_lineNumber = true;
    } //-- void setLineNumber(int) 

    /**
     * Method setScriptFKSets the value of field 'scriptFK'.
     * 
     * @param scriptFK the value of field 'scriptFK'.
     */
    public void setScriptFK(long scriptFK)
    {
        this._scriptFK = scriptFK;
        
        super.setVoChanged(true);
        this._has_scriptFK = true;
    } //-- void setScriptFK(long) 

    /**
     * Method setScriptLineSets the value of field 'scriptLine'.
     * 
     * @param scriptLine the value of field 'scriptLine'.
     */
    public void setScriptLine(java.lang.String scriptLine)
    {
        this._scriptLine = scriptLine;
        
        super.setVoChanged(true);
    } //-- void setScriptLine(java.lang.String) 

    /**
     * Method setScriptLinePKSets the value of field
     * 'scriptLinePK'.
     * 
     * @param scriptLinePK the value of field 'scriptLinePK'.
     */
    public void setScriptLinePK(long scriptLinePK)
    {
        this._scriptLinePK = scriptLinePK;
        
        super.setVoChanged(true);
        this._has_scriptLinePK = true;
    } //-- void setScriptLinePK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ScriptLineVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ScriptLineVO) Unmarshaller.unmarshal(edit.common.vo.ScriptLineVO.class, reader);
    } //-- edit.common.vo.ScriptLineVO unmarshal(java.io.Reader) 

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
