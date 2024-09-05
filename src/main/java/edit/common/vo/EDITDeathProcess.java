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
 * Class EDITDeathProcess.
 * 
 * @version $Revision$ $Date$
 */
public class EDITDeathProcess extends edit.common.vo.VOObject  
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
     * Field _allowableTransactionList
     */
    private java.util.Vector _allowableTransactionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public EDITDeathProcess() {
        super();
        _allowableTransactionList = new Vector();
    } //-- edit.common.vo.EDITDeathProcess()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addAllowableTransaction
     * 
     * @param vAllowableTransaction
     */
    public void addAllowableTransaction(edit.common.vo.AllowableTransaction vAllowableTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _allowableTransactionList.addElement(vAllowableTransaction);
    } //-- void addAllowableTransaction(edit.common.vo.AllowableTransaction) 

    /**
     * Method addAllowableTransaction
     * 
     * @param index
     * @param vAllowableTransaction
     */
    public void addAllowableTransaction(int index, edit.common.vo.AllowableTransaction vAllowableTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _allowableTransactionList.insertElementAt(vAllowableTransaction, index);
    } //-- void addAllowableTransaction(int, edit.common.vo.AllowableTransaction) 

    /**
     * Method enumerateAllowableTransaction
     */
    public java.util.Enumeration enumerateAllowableTransaction()
    {
        return _allowableTransactionList.elements();
    } //-- java.util.Enumeration enumerateAllowableTransaction() 

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
        
        if (obj instanceof EDITDeathProcess) {
        
            EDITDeathProcess temp = (EDITDeathProcess)obj;
            if (this._companyStructurePK != null) {
                if (temp._companyStructurePK == null) return false;
                else if (!(this._companyStructurePK.equals(temp._companyStructurePK))) 
                    return false;
            }
            else if (temp._companyStructurePK != null)
                return false;
            if (this._allowableTransactionList != null) {
                if (temp._allowableTransactionList == null) return false;
                else if (!(this._allowableTransactionList.equals(temp._allowableTransactionList))) 
                    return false;
            }
            else if (temp._allowableTransactionList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getAllowableTransaction
     * 
     * @param index
     */
    public edit.common.vo.AllowableTransaction getAllowableTransaction(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _allowableTransactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.AllowableTransaction) _allowableTransactionList.elementAt(index);
    } //-- edit.common.vo.AllowableTransaction getAllowableTransaction(int) 

    /**
     * Method getAllowableTransaction
     */
    public edit.common.vo.AllowableTransaction[] getAllowableTransaction()
    {
        int size = _allowableTransactionList.size();
        edit.common.vo.AllowableTransaction[] mArray = new edit.common.vo.AllowableTransaction[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.AllowableTransaction) _allowableTransactionList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.AllowableTransaction[] getAllowableTransaction() 

    /**
     * Method getAllowableTransactionCount
     */
    public int getAllowableTransactionCount()
    {
        return _allowableTransactionList.size();
    } //-- int getAllowableTransactionCount() 

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
     * Method removeAllAllowableTransaction
     */
    public void removeAllAllowableTransaction()
    {
        _allowableTransactionList.removeAllElements();
    } //-- void removeAllAllowableTransaction() 

    /**
     * Method removeAllowableTransaction
     * 
     * @param index
     */
    public edit.common.vo.AllowableTransaction removeAllowableTransaction(int index)
    {
        java.lang.Object obj = _allowableTransactionList.elementAt(index);
        _allowableTransactionList.removeElementAt(index);
        return (edit.common.vo.AllowableTransaction) obj;
    } //-- edit.common.vo.AllowableTransaction removeAllowableTransaction(int) 

    /**
     * Method setAllowableTransaction
     * 
     * @param index
     * @param vAllowableTransaction
     */
    public void setAllowableTransaction(int index, edit.common.vo.AllowableTransaction vAllowableTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _allowableTransactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _allowableTransactionList.setElementAt(vAllowableTransaction, index);
    } //-- void setAllowableTransaction(int, edit.common.vo.AllowableTransaction) 

    /**
     * Method setAllowableTransaction
     * 
     * @param allowableTransactionArray
     */
    public void setAllowableTransaction(edit.common.vo.AllowableTransaction[] allowableTransactionArray)
    {
        //-- copy array
        _allowableTransactionList.removeAllElements();
        for (int i = 0; i < allowableTransactionArray.length; i++) {
            _allowableTransactionList.addElement(allowableTransactionArray[i]);
        }
    } //-- void setAllowableTransaction(edit.common.vo.AllowableTransaction) 

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
     * Method unmarshal
     * 
     * @param reader
     */
    public static edit.common.vo.EDITDeathProcess unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.EDITDeathProcess) Unmarshaller.unmarshal(edit.common.vo.EDITDeathProcess.class, reader);
    } //-- edit.common.vo.EDITDeathProcess unmarshal(java.io.Reader) 

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
