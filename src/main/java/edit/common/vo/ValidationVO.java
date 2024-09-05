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
 * Comment describing your root element
 * 
 * @version $Revision$ $Date$
 */
public class ValidationVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _instructionName
     */
    private java.lang.String _instructionName;

    /**
     * Field _message
     */
    private java.lang.String _message;

    /**
     * Field _severity
     */
    private java.lang.String _severity;

    /**
     * Field _reporting
     */
    private java.lang.String _reporting;

    /**
     * Field _stack
     */
    private java.lang.String _stack;

    /**
     * Field _myDate
     */
    private org.exolab.castor.types.Date _myDate;


      //----------------/
     //- Constructors -/
    //----------------/

    public ValidationVO() {
        super();
    } //-- edit.common.vo.ValidationVO()


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
        
        if (obj instanceof ValidationVO) {
        
            ValidationVO temp = (ValidationVO)obj;
            if (this._instructionName != null) {
                if (temp._instructionName == null) return false;
                else if (!(this._instructionName.equals(temp._instructionName))) 
                    return false;
            }
            else if (temp._instructionName != null)
                return false;
            if (this._message != null) {
                if (temp._message == null) return false;
                else if (!(this._message.equals(temp._message))) 
                    return false;
            }
            else if (temp._message != null)
                return false;
            if (this._severity != null) {
                if (temp._severity == null) return false;
                else if (!(this._severity.equals(temp._severity))) 
                    return false;
            }
            else if (temp._severity != null)
                return false;
            if (this._reporting != null) {
                if (temp._reporting == null) return false;
                else if (!(this._reporting.equals(temp._reporting))) 
                    return false;
            }
            else if (temp._reporting != null)
                return false;
            if (this._stack != null) {
                if (temp._stack == null) return false;
                else if (!(this._stack.equals(temp._stack))) 
                    return false;
            }
            else if (temp._stack != null)
                return false;
            if (this._myDate != null) {
                if (temp._myDate == null) return false;
                else if (!(this._myDate.equals(temp._myDate))) 
                    return false;
            }
            else if (temp._myDate != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getInstructionNameReturns the value of field
     * 'instructionName'.
     * 
     * @return the value of field 'instructionName'.
     */
    public java.lang.String getInstructionName()
    {
        return this._instructionName;
    } //-- java.lang.String getInstructionName() 

    /**
     * Method getMessageReturns the value of field 'message'.
     * 
     * @return the value of field 'message'.
     */
    public java.lang.String getMessage()
    {
        return this._message;
    } //-- java.lang.String getMessage() 

    /**
     * Method getMyDateReturns the value of field 'myDate'.
     * 
     * @return the value of field 'myDate'.
     */
    public org.exolab.castor.types.Date getMyDate()
    {
        return this._myDate;
    } //-- org.exolab.castor.types.Date getMyDate() 

    /**
     * Method getReportingReturns the value of field 'reporting'.
     * 
     * @return the value of field 'reporting'.
     */
    public java.lang.String getReporting()
    {
        return this._reporting;
    } //-- java.lang.String getReporting() 

    /**
     * Method getSeverityReturns the value of field 'severity'.
     * 
     * @return the value of field 'severity'.
     */
    public java.lang.String getSeverity()
    {
        return this._severity;
    } //-- java.lang.String getSeverity() 

    /**
     * Method getStackReturns the value of field 'stack'.
     * 
     * @return the value of field 'stack'.
     */
    public java.lang.String getStack()
    {
        return this._stack;
    } //-- java.lang.String getStack() 

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
     * Method setInstructionNameSets the value of field
     * 'instructionName'.
     * 
     * @param instructionName the value of field 'instructionName'.
     */
    public void setInstructionName(java.lang.String instructionName)
    {
        this._instructionName = instructionName;
        
        super.setVoChanged(true);
    } //-- void setInstructionName(java.lang.String) 

    /**
     * Method setMessageSets the value of field 'message'.
     * 
     * @param message the value of field 'message'.
     */
    public void setMessage(java.lang.String message)
    {
        this._message = message;
        
        super.setVoChanged(true);
    } //-- void setMessage(java.lang.String) 

    /**
     * Method setMyDateSets the value of field 'myDate'.
     * 
     * @param myDate the value of field 'myDate'.
     */
    public void setMyDate(org.exolab.castor.types.Date myDate)
    {
        this._myDate = myDate;
        
        super.setVoChanged(true);
    } //-- void setMyDate(org.exolab.castor.types.Date) 

    /**
     * Method setReportingSets the value of field 'reporting'.
     * 
     * @param reporting the value of field 'reporting'.
     */
    public void setReporting(java.lang.String reporting)
    {
        this._reporting = reporting;
        
        super.setVoChanged(true);
    } //-- void setReporting(java.lang.String) 

    /**
     * Method setSeveritySets the value of field 'severity'.
     * 
     * @param severity the value of field 'severity'.
     */
    public void setSeverity(java.lang.String severity)
    {
        this._severity = severity;
        
        super.setVoChanged(true);
    } //-- void setSeverity(java.lang.String) 

    /**
     * Method setStackSets the value of field 'stack'.
     * 
     * @param stack the value of field 'stack'.
     */
    public void setStack(java.lang.String stack)
    {
        this._stack = stack;
        
        super.setVoChanged(true);
    } //-- void setStack(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.ValidationVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.ValidationVO) Unmarshaller.unmarshal(edit.common.vo.ValidationVO.class, reader);
    } //-- edit.common.vo.ValidationVO unmarshal(java.io.Reader) 

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
