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
 * Class ScriptInstructionVO.
 * 
 * @version $Revision$ $Date$
 */
public class ScriptInstructionVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _scriptInstructionPK
     */
    private long _scriptInstructionPK;

    /**
     * keeps track of state for field: _scriptInstructionPK
     */
    private boolean _has_scriptInstructionPK;

    /**
     * Field _instruction
     */
    private java.lang.String _instruction;


      //----------------/
     //- Constructors -/
    //----------------/

    public ScriptInstructionVO() {
        super();
    } //-- edit.common.vo.ScriptInstructionVO()


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
        
        if (obj instanceof ScriptInstructionVO) {
        
            ScriptInstructionVO temp = (ScriptInstructionVO)obj;
            if (this._scriptInstructionPK != temp._scriptInstructionPK)
                return false;
            if (this._has_scriptInstructionPK != temp._has_scriptInstructionPK)
                return false;
            if (this._instruction != null) {
                if (temp._instruction == null) return false;
                else if (!(this._instruction.equals(temp._instruction))) 
                    return false;
            }
            else if (temp._instruction != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getInstructionReturns the value of field
     * 'instruction'.
     * 
     * @return the value of field 'instruction'.
     */
    public java.lang.String getInstruction()
    {
        return this._instruction;
    } //-- java.lang.String getInstruction() 

    /**
     * Method getScriptInstructionPKReturns the value of field
     * 'scriptInstructionPK'.
     * 
     * @return the value of field 'scriptInstructionPK'.
     */
    public long getScriptInstructionPK()
    {
        return this._scriptInstructionPK;
    } //-- long getScriptInstructionPK() 

    /**
     * Method hasScriptInstructionPK
     */
    public boolean hasScriptInstructionPK()
    {
        return this._has_scriptInstructionPK;
    } //-- boolean hasScriptInstructionPK() 

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
     * Method setInstructionSets the value of field 'instruction'.
     * 
     * @param instruction the value of field 'instruction'.
     */
    public void setInstruction(java.lang.String instruction)
    {
        this._instruction = instruction;
        
        super.setVoChanged(true);
    } //-- void setInstruction(java.lang.String) 

    /**
     * Method setScriptInstructionPKSets the value of field
     * 'scriptInstructionPK'.
     * 
     * @param scriptInstructionPK the value of field
     * 'scriptInstructionPK'.
     */
    public void setScriptInstructionPK(long scriptInstructionPK)
    {
        this._scriptInstructionPK = scriptInstructionPK;
        
        super.setVoChanged(true);
        this._has_scriptInstructionPK = true;
    } //-- void setScriptInstructionPK(long) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ScriptInstructionVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ScriptInstructionVO) Unmarshaller.unmarshal(edit.common.vo.ScriptInstructionVO.class, reader);
    } //-- edit.common.vo.ScriptInstructionVO unmarshal(java.io.Reader) 

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
