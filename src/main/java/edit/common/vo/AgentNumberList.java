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
 * Class AgentNumberList.
 * 
 * @version $Revision$ $Date$
 */
public class AgentNumberList extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _agentNumber
     */
    private java.lang.String _agentNumber;


      //----------------/
     //- Constructors -/
    //----------------/

    public AgentNumberList() {
        super();
    } //-- edit.common.vo.AgentNumberList()


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
        
        if (obj instanceof AgentNumberList) {
        
            AgentNumberList temp = (AgentNumberList)obj;
            if (this._agentNumber != null) {
                if (temp._agentNumber == null) return false;
                else if (!(this._agentNumber.equals(temp._agentNumber))) 
                    return false;
            }
            else if (temp._agentNumber != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAgentNumberReturns the value of field
     * 'agentNumber'.
     * 
     * @return the value of field 'agentNumber'.
     */
    public java.lang.String getAgentNumber()
    {
        return this._agentNumber;
    } //-- java.lang.String getAgentNumber() 

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
     * Method setAgentNumberSets the value of field 'agentNumber'.
     * 
     * @param agentNumber the value of field 'agentNumber'.
     */
    public void setAgentNumber(java.lang.String agentNumber)
    {
        this._agentNumber = agentNumber;
        
        super.setVoChanged(true);
    } //-- void setAgentNumber(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.AgentNumberList unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.AgentNumberList) Unmarshaller.unmarshal(edit.common.vo.AgentNumberList.class, reader);
    } //-- edit.common.vo.AgentNumberList unmarshal(java.io.Reader) 

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
