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
 * Class RedirectVO.
 * 
 * @version $Revision$ $Date$
 */
public class RedirectVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _redirectPK
     */
    private long _redirectPK;

    /**
     * keeps track of state for field: _redirectPK
     */
    private boolean _has_redirectPK;

    /**
     * Field _agentFK
     */
    private long _agentFK;

    /**
     * keeps track of state for field: _agentFK
     */
    private boolean _has_agentFK;

    /**
     * Field _redirectTypeCT
     */
    private java.lang.String _redirectTypeCT;

    /**
     * Field _startDate
     */
    private java.lang.String _startDate;

    /**
     * Field _stopDate
     */
    private java.lang.String _stopDate;

    /**
     * Field _clientRoleFK
     */
    private long _clientRoleFK;

    /**
     * keeps track of state for field: _clientRoleFK
     */
    private boolean _has_clientRoleFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public RedirectVO() {
        super();
    } //-- edit.common.vo.RedirectVO()


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
        
        if (obj instanceof RedirectVO) {
        
            RedirectVO temp = (RedirectVO)obj;
            if (this._redirectPK != temp._redirectPK)
                return false;
            if (this._has_redirectPK != temp._has_redirectPK)
                return false;
            if (this._agentFK != temp._agentFK)
                return false;
            if (this._has_agentFK != temp._has_agentFK)
                return false;
            if (this._redirectTypeCT != null) {
                if (temp._redirectTypeCT == null) return false;
                else if (!(this._redirectTypeCT.equals(temp._redirectTypeCT))) 
                    return false;
            }
            else if (temp._redirectTypeCT != null)
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
            if (this._clientRoleFK != temp._clientRoleFK)
                return false;
            if (this._has_clientRoleFK != temp._has_clientRoleFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

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
     * Method getRedirectPKReturns the value of field 'redirectPK'.
     * 
     * @return the value of field 'redirectPK'.
     */
    public long getRedirectPK()
    {
        return this._redirectPK;
    } //-- long getRedirectPK() 

    /**
     * Method getRedirectTypeCTReturns the value of field
     * 'redirectTypeCT'.
     * 
     * @return the value of field 'redirectTypeCT'.
     */
    public java.lang.String getRedirectTypeCT()
    {
        return this._redirectTypeCT;
    } //-- java.lang.String getRedirectTypeCT() 

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
     * Method hasAgentFK
     */
    public boolean hasAgentFK()
    {
        return this._has_agentFK;
    } //-- boolean hasAgentFK() 

    /**
     * Method hasClientRoleFK
     */
    public boolean hasClientRoleFK()
    {
        return this._has_clientRoleFK;
    } //-- boolean hasClientRoleFK() 

    /**
     * Method hasRedirectPK
     */
    public boolean hasRedirectPK()
    {
        return this._has_redirectPK;
    } //-- boolean hasRedirectPK() 

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
     * Method setRedirectPKSets the value of field 'redirectPK'.
     * 
     * @param redirectPK the value of field 'redirectPK'.
     */
    public void setRedirectPK(long redirectPK)
    {
        this._redirectPK = redirectPK;
        
        super.setVoChanged(true);
        this._has_redirectPK = true;
    } //-- void setRedirectPK(long) 

    /**
     * Method setRedirectTypeCTSets the value of field
     * 'redirectTypeCT'.
     * 
     * @param redirectTypeCT the value of field 'redirectTypeCT'.
     */
    public void setRedirectTypeCT(java.lang.String redirectTypeCT)
    {
        this._redirectTypeCT = redirectTypeCT;
        
        super.setVoChanged(true);
    } //-- void setRedirectTypeCT(java.lang.String) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.RedirectVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.RedirectVO) Unmarshaller.unmarshal(edit.common.vo.RedirectVO.class, reader);
    } //-- edit.common.vo.RedirectVO unmarshal(java.io.Reader) 

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
