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
 * Class SpawningTransaction.
 * 
 * @version $Revision$ $Date$
 */
public class SpawningTransaction extends edit.common.vo.VOObject  
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
     * Field _spawnedTransactionList
     */
    private java.util.Vector _spawnedTransactionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SpawningTransaction() {
        super();
        _spawnedTransactionList = new Vector();
    } //-- edit.common.vo.SpawningTransaction()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addSpawnedTransaction
     * 
     * @param vSpawnedTransaction
     */
    public void addSpawnedTransaction(edit.common.vo.SpawnedTransaction vSpawnedTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _spawnedTransactionList.addElement(vSpawnedTransaction);
    } //-- void addSpawnedTransaction(edit.common.vo.SpawnedTransaction) 

    /**
     * Method addSpawnedTransaction
     * 
     * @param index
     * @param vSpawnedTransaction
     */
    public void addSpawnedTransaction(int index, edit.common.vo.SpawnedTransaction vSpawnedTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        _spawnedTransactionList.insertElementAt(vSpawnedTransaction, index);
    } //-- void addSpawnedTransaction(int, edit.common.vo.SpawnedTransaction) 

    /**
     * Method enumerateSpawnedTransaction
     */
    public java.util.Enumeration enumerateSpawnedTransaction()
    {
        return _spawnedTransactionList.elements();
    } //-- java.util.Enumeration enumerateSpawnedTransaction() 

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
        
        if (obj instanceof SpawningTransaction) {
        
            SpawningTransaction temp = (SpawningTransaction)obj;
            if (this._transactionTypeCT != null) {
                if (temp._transactionTypeCT == null) return false;
                else if (!(this._transactionTypeCT.equals(temp._transactionTypeCT))) 
                    return false;
            }
            else if (temp._transactionTypeCT != null)
                return false;
            if (this._spawnedTransactionList != null) {
                if (temp._spawnedTransactionList == null) return false;
                else if (!(this._spawnedTransactionList.equals(temp._spawnedTransactionList))) 
                    return false;
            }
            else if (temp._spawnedTransactionList != null)
                return false;
            return true;
        }
        return false;
    } //-- boolean equals(java.lang.Object) 

    /**
     * Method getSpawnedTransaction
     * 
     * @param index
     */
    public edit.common.vo.SpawnedTransaction getSpawnedTransaction(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _spawnedTransactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return (edit.common.vo.SpawnedTransaction) _spawnedTransactionList.elementAt(index);
    } //-- edit.common.vo.SpawnedTransaction getSpawnedTransaction(int) 

    /**
     * Method getSpawnedTransaction
     */
    public edit.common.vo.SpawnedTransaction[] getSpawnedTransaction()
    {
        int size = _spawnedTransactionList.size();
        edit.common.vo.SpawnedTransaction[] mArray = new edit.common.vo.SpawnedTransaction[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (edit.common.vo.SpawnedTransaction) _spawnedTransactionList.elementAt(index);
        }
        return mArray;
    } //-- edit.common.vo.SpawnedTransaction[] getSpawnedTransaction() 

    /**
     * Method getSpawnedTransactionCount
     */
    public int getSpawnedTransactionCount()
    {
        return _spawnedTransactionList.size();
    } //-- int getSpawnedTransactionCount() 

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
     * Method removeAllSpawnedTransaction
     */
    public void removeAllSpawnedTransaction()
    {
        _spawnedTransactionList.removeAllElements();
    } //-- void removeAllSpawnedTransaction() 

    /**
     * Method removeSpawnedTransaction
     * 
     * @param index
     */
    public edit.common.vo.SpawnedTransaction removeSpawnedTransaction(int index)
    {
        java.lang.Object obj = _spawnedTransactionList.elementAt(index);
        _spawnedTransactionList.removeElementAt(index);
        return (edit.common.vo.SpawnedTransaction) obj;
    } //-- edit.common.vo.SpawnedTransaction removeSpawnedTransaction(int) 

    /**
     * Method setSpawnedTransaction
     * 
     * @param index
     * @param vSpawnedTransaction
     */
    public void setSpawnedTransaction(int index, edit.common.vo.SpawnedTransaction vSpawnedTransaction)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _spawnedTransactionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _spawnedTransactionList.setElementAt(vSpawnedTransaction, index);
    } //-- void setSpawnedTransaction(int, edit.common.vo.SpawnedTransaction) 

    /**
     * Method setSpawnedTransaction
     * 
     * @param spawnedTransactionArray
     */
    public void setSpawnedTransaction(edit.common.vo.SpawnedTransaction[] spawnedTransactionArray)
    {
        //-- copy array
        _spawnedTransactionList.removeAllElements();
        for (int i = 0; i < spawnedTransactionArray.length; i++) {
            _spawnedTransactionList.addElement(spawnedTransactionArray[i]);
        }
    } //-- void setSpawnedTransaction(edit.common.vo.SpawnedTransaction) 

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
    public static edit.common.vo.SpawningTransaction unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (edit.common.vo.SpawningTransaction) Unmarshaller.unmarshal(edit.common.vo.SpawningTransaction.class, reader);
    } //-- edit.common.vo.SpawningTransaction unmarshal(java.io.Reader) 

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
