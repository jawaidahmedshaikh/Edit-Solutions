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
 * Class Transaction.
 * 
 * @version $Revision$ $Date$
 */
public class Transaction extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _transactionTypeCT
     */
    private java.lang.String _transactionTypeCT;

    /**
     * Field _processingInd
     */
    private java.lang.String _processingInd;


      //----------------/
     //- Constructors -/
    //----------------/

    public Transaction() {
        super();
    } //-- edit.common.vo.Transaction()


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
        
        if (obj instanceof Transaction) {
        
            Transaction temp = (Transaction)obj;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            if (this._processingInd != null) {
                if (temp._processingInd == null) return false;
                else if (!(this._processingInd.equals(temp._processingInd))) 
                    return false;
            }
            else if (temp._processingInd != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getProcessingIndReturns the value of field
     * 'processingInd'.
     * 
     * @return the value of field 'processingInd'.
     */
    public java.lang.String getProcessingInd()
    {
        return this._processingInd;
    } //-- java.lang.String getProcessingInd() 

    /**
     * Method getTransactionTypeCTReturns the value of field
     * 'transactionTypeCT'.
     * 
     * @return the value of field 'transactionTypeCT'.
     */
    public java.lang.String getTransactionTypeCT()
    {
        return this._transactionTypeCT;
    } //-- java.lang.String getTransactionTypeCT() 

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
     * Method setProcessingIndSets the value of field
     * 'processingInd'.
     * 
     * @param processingInd the value of field 'processingInd'.
     */
    public void setProcessingInd(java.lang.String processingInd)
    {
        this._processingInd = processingInd;
        
        super.setVoChanged(true);
    } //-- void setProcessingInd(java.lang.String) 

    /**
     * Method setTransactionTypeCTSets the value of field
     * 'transactionTypeCT'.
     * 
     * @param transactionTypeCT the value of field
     * 'transactionTypeCT'.
     */
    public void setTransactionTypeCT(java.lang.String transactionTypeCT)
    {
        this._transactionTypeCT = transactionTypeCT;
        
        super.setVoChanged(true);
    } //-- void setTransactionTypeCT(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.Transaction unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.Transaction) Unmarshaller.unmarshal(edit.common.vo.Transaction.class, reader);
    } //-- edit.common.vo.Transaction unmarshal(java.io.Reader) 

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
