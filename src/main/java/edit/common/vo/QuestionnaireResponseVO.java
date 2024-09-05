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
 * Class QuestionnaireResponseVO.
 * 
 * @version $Revision$ $Date$
 */
public class QuestionnaireResponseVO extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _questionnaireResponsePK
     */
    private long _questionnaireResponsePK;

    /**
     * keeps track of state for field: _questionnaireResponsePK
     */
    private boolean _has_questionnaireResponsePK;

    /**
     * Field _responseCT
     */
    private java.lang.String _responseCT;

    /**
     * Field _effectiveDate
     */
    private java.lang.String _effectiveDate;

    /**
     * Field _followupDate
     */
    private java.lang.String _followupDate;

    /**
     * Field _filteredQuestionnaireFK
     */
    private long _filteredQuestionnaireFK;

    /**
     * keeps track of state for field: _filteredQuestionnaireFK
     */
    private boolean _has_filteredQuestionnaireFK;

    /**
     * Field _contractClientFK
     */
    private long _contractClientFK;

    /**
     * keeps track of state for field: _contractClientFK
     */
    private boolean _has_contractClientFK;


      //----------------/
     //- Constructors -/
    //----------------/

    public QuestionnaireResponseVO() {
        super();
    } //-- edit.common.vo.QuestionnaireResponseVO()


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
        
        if (obj instanceof QuestionnaireResponseVO) {
        
            QuestionnaireResponseVO temp = (QuestionnaireResponseVO)obj;
            if (this._questionnaireResponsePK != temp._questionnaireResponsePK)
                return false;
            if (this._has_questionnaireResponsePK != temp._has_questionnaireResponsePK)
                return false;
            if (this._responseCT != null) {
                if (temp._responseCT == null) return false;
                else if (!(this._responseCT.equals(temp._responseCT))) 
                    return false;
            }
            else if (temp._responseCT != null)
                return false;
            if (this._effectiveDate != null) {
                if (temp._effectiveDate == null) return false;
                else if (!(this._effectiveDate.equals(temp._effectiveDate))) 
                    return false;
            }
            else if (temp._effectiveDate != null)
                return false;
            if (this._followupDate != null) {
                if (temp._followupDate == null) return false;
                else if (!(this._followupDate.equals(temp._followupDate))) 
                    return false;
            }
            else if (temp._followupDate != null)
                return false;
            if (this._filteredQuestionnaireFK != temp._filteredQuestionnaireFK)
                return false;
            if (this._has_filteredQuestionnaireFK != temp._has_filteredQuestionnaireFK)
                return false;
            if (this._contractClientFK != temp._contractClientFK)
                return false;
            if (this._has_contractClientFK != temp._has_contractClientFK)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getContractClientFKReturns the value of field
     * 'contractClientFK'.
     * 
     * @return the value of field 'contractClientFK'.
     */
    public long getContractClientFK()
    {
        return this._contractClientFK;
    } //-- long getContractClientFK() 

    /**
     * Method getEffectiveDateReturns the value of field
     * 'effectiveDate'.
     * 
     * @return the value of field 'effectiveDate'.
     */
    public java.lang.String getEffectiveDate()
    {
        return this._effectiveDate;
    } //-- java.lang.String getEffectiveDate() 

    /**
     * Method getFilteredQuestionnaireFKReturns the value of field
     * 'filteredQuestionnaireFK'.
     * 
     * @return the value of field 'filteredQuestionnaireFK'.
     */
    public long getFilteredQuestionnaireFK()
    {
        return this._filteredQuestionnaireFK;
    } //-- long getFilteredQuestionnaireFK() 

    /**
     * Method getFollowupDateReturns the value of field
     * 'followupDate'.
     * 
     * @return the value of field 'followupDate'.
     */
    public java.lang.String getFollowupDate()
    {
        return this._followupDate;
    } //-- java.lang.String getFollowupDate() 

    /**
     * Method getQuestionnaireResponsePKReturns the value of field
     * 'questionnaireResponsePK'.
     * 
     * @return the value of field 'questionnaireResponsePK'.
     */
    public long getQuestionnaireResponsePK()
    {
        return this._questionnaireResponsePK;
    } //-- long getQuestionnaireResponsePK() 

    /**
     * Method getResponseCTReturns the value of field 'responseCT'.
     * 
     * @return the value of field 'responseCT'.
     */
    public java.lang.String getResponseCT()
    {
        return this._responseCT;
    } //-- java.lang.String getResponseCT() 

    /**
     * Method hasContractClientFK
     */
    public boolean hasContractClientFK()
    {
        return this._has_contractClientFK;
    } //-- boolean hasContractClientFK() 

    /**
     * Method hasFilteredQuestionnaireFK
     */
    public boolean hasFilteredQuestionnaireFK()
    {
        return this._has_filteredQuestionnaireFK;
    } //-- boolean hasFilteredQuestionnaireFK() 

    /**
     * Method hasQuestionnaireResponsePK
     */
    public boolean hasQuestionnaireResponsePK()
    {
        return this._has_questionnaireResponsePK;
    } //-- boolean hasQuestionnaireResponsePK() 

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
     * Method setContractClientFKSets the value of field
     * 'contractClientFK'.
     * 
     * @param contractClientFK the value of field 'contractClientFK'
     */
    public void setContractClientFK(long contractClientFK)
    {
        this._contractClientFK = contractClientFK;
        
        super.setVoChanged(true);
        this._has_contractClientFK = true;
    } //-- void setContractClientFK(long) 

    /**
     * Method setEffectiveDateSets the value of field
     * 'effectiveDate'.
     * 
     * @param effectiveDate the value of field 'effectiveDate'.
     */
    public void setEffectiveDate(java.lang.String effectiveDate)
    {
        this._effectiveDate = effectiveDate;
        
        super.setVoChanged(true);
    } //-- void setEffectiveDate(java.lang.String) 

    /**
     * Method setFilteredQuestionnaireFKSets the value of field
     * 'filteredQuestionnaireFK'.
     * 
     * @param filteredQuestionnaireFK the value of field
     * 'filteredQuestionnaireFK'.
     */
    public void setFilteredQuestionnaireFK(long filteredQuestionnaireFK)
    {
        this._filteredQuestionnaireFK = filteredQuestionnaireFK;
        
        super.setVoChanged(true);
        this._has_filteredQuestionnaireFK = true;
    } //-- void setFilteredQuestionnaireFK(long) 

    /**
     * Method setFollowupDateSets the value of field
     * 'followupDate'.
     * 
     * @param followupDate the value of field 'followupDate'.
     */
    public void setFollowupDate(java.lang.String followupDate)
    {
        this._followupDate = followupDate;
        
        super.setVoChanged(true);
    } //-- void setFollowupDate(java.lang.String) 

    /**
     * Method setQuestionnaireResponsePKSets the value of field
     * 'questionnaireResponsePK'.
     * 
     * @param questionnaireResponsePK the value of field
     * 'questionnaireResponsePK'.
     */
    public void setQuestionnaireResponsePK(long questionnaireResponsePK)
    {
        this._questionnaireResponsePK = questionnaireResponsePK;
        
        super.setVoChanged(true);
        this._has_questionnaireResponsePK = true;
    } //-- void setQuestionnaireResponsePK(long) 

    /**
     * Method setResponseCTSets the value of field 'responseCT'.
     * 
     * @param responseCT the value of field 'responseCT'.
     */
    public void setResponseCT(java.lang.String responseCT)
    {
        this._responseCT = responseCT;
        
        super.setVoChanged(true);
    } //-- void setResponseCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.QuestionnaireResponseVO unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.QuestionnaireResponseVO) Unmarshaller.unmarshal(edit.common.vo.QuestionnaireResponseVO.class, reader);
    } //-- edit.common.vo.QuestionnaireResponseVO unmarshal(java.io.Reader) 

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
