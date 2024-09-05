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
 * Class EDITIssueProcess.
 * 
 * @version $Revision$ $Date$
 */
public class EDITIssueProcess extends edit.common.vo.VOObject  
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _companyStructurePK
     */
    private java.lang.String _companyStructurePK;

    /**
     * Field _transactionList
     */
    private java.util.Vector _transactionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITIssueProcess() {
        super();
        _transactionList = new Vector();
    } //-- edit.common.vo.EDITIssueProcess()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addTransaction
     * 
     * @param vTransaction
     */
    public void addTransaction(java.lang.String vTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _transactionList.addElement(vTransaction);
    } //-- void addTransaction(java.lang.String) 

    /**
     * Method addTransaction
     * 
     * @param index
     * @param vTransaction
     */
    public void addTransaction(int index, java.lang.String vTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _transactionList.insertElementAt(vTransaction, index);
    } //-- void addTransaction(int, java.lang.String) 

    /**
     * Method enumerateTransaction
     */
    public java.util.Enumeration enumerateTransaction()
    {
        return _transactionList.elements();
    } //-- java.util.Enumeration enumerateTransaction() 

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
        
        if (obj instanceof EDITIssueProcess) {
        
            EDITIssueProcess temp = (EDITIssueProcess)obj;
            if (this._companyStructurePK != null) {
                if (temp._companyStructurePK == null) return false;
                else if (!(this._companyStructurePK.equals(temp._companyStructurePK))) 
                    return false;
            }
            else if (temp._companyStructurePK != null)
                return false;
            if (this._transactionList != null) {
                if (temp._transactionList == null) return false;
                else if (!(this._transactionList.equals(temp._transactionList))) 
                    return false;
            }
            else if (temp._transactionList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getCompanyStructurePKReturns the value of field
     * 'companyStructurePK'.
     * 
     * @return the value of field 'companyStructurePK'.
     */
    public java.lang.String getCompanyStructurePK()
    {
        return this._companyStructurePK;
    } //-- java.lang.String getCompanyStructurePK() 

    /**
     * Method getTransaction
     * 
     * @param index
     */
    public java.lang.String getTransaction(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _transactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (String)_transactionList.elementAt(index);
    } //-- java.lang.String getTransaction(int) 

    /**
     * Method getTransaction
     */
    public java.lang.String[] getTransaction()
    {
        int size = _transactionList.size();
        java.lang.String[] mArray = new java.lang.String[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (String)_transactionList.elementAt(index);
        }
        return mArray;
    } //-- java.lang.String[] getTransaction() 

    /**
     * Method getTransactionCount
     */
    public int getTransactionCount()
    {
        return _transactionList.size();
    } //-- int getTransactionCount() 

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
     * Method removeAllTransaction
     */
    public void removeAllTransaction()
    {
        _transactionList.removeAllElements();
    } //-- void removeAllTransaction() 

    /**
     * Method removeTransaction
     * 
     * @param index
     */
    public java.lang.String removeTransaction(int index)
    {
        java.lang.Object obj = _transactionList.elementAt(index);
        _transactionList.removeElementAt(index);
        return (String)obj;
    } //-- java.lang.String removeTransaction(int) 

    /**
     * Method setCompanyStructurePKSets the value of field
     * 'companyStructurePK'.
     * 
     * @param companyStructurePK the value of field
     * 'companyStructurePK'.
     */
    public void setCompanyStructurePK(java.lang.String companyStructurePK)
    {
        this._companyStructurePK = companyStructurePK;
        
        super.setVoChanged(true);
    } //-- void setCompanyStructurePK(java.lang.String) 

    /**
     * Method setTransaction
     * 
     * @param index
     * @param vTransaction
     */
    public void setTransaction(int index, java.lang.String vTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _transactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _transactionList.setElementAt(vTransaction, index);
    } //-- void setTransaction(int, java.lang.String) 

    /**
     * Method setTransaction
     * 
     * @param transactionArray
     */
    public void setTransaction(java.lang.String[] transactionArray)
    {
        //-- copy array
        _transactionList.removeAllElements();
        for (int i = 0; i < transactionArray.length; i++) {
            _transactionList.addElement(transactionArray[i]);
        }
    } //-- void setTransaction(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITIssueProcess unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITIssueProcess) Unmarshaller.unmarshal(edit.common.vo.EDITIssueProcess.class, reader);
    } //-- edit.common.vo.EDITIssueProcess unmarshal(java.io.Reader) 

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
